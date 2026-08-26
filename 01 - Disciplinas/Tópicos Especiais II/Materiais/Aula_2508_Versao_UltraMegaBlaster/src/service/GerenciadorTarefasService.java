package service;

import model.Categoria;
import model.Prioridade;
import model.Tarefa;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GerenciadorTarefasService {
    private final List<Tarefa> tarefas;
    private final List<Tarefa> lixeiraUndo;
    private final String caminhoArquivo;

    public GerenciadorTarefasService() {
        this.tarefas = new ArrayList<>();
        this.lixeiraUndo = new ArrayList<>();
        this.caminhoArquivo = System.getProperty("user.home") + File.separator + ".gestor_tarefas_ultramegablaster.csv";
        carregarDoArquivo();
    }

    public List<Tarefa> getTodas() {
        return new ArrayList<>(tarefas);
    }

    public synchronized Tarefa adicionar(String descricao, Categoria categoria, Prioridade prioridade, String observacoes) {
        if (descricao == null || descricao.trim().isEmpty()) {
            throw new IllegalArgumentException("A descrição da tarefa é obrigatória!");
        }
        Tarefa nova = new Tarefa(descricao.trim(), categoria, prioridade, observacoes);
        tarefas.add(nova);
        salvarNoArquivo();
        return nova;
    }

    public synchronized boolean removerPorId(String id) {
        Optional<Tarefa> opt = tarefas.stream().filter(t -> t.getId().equals(id)).findFirst();
        if (opt.isPresent()) {
            Tarefa removida = opt.get();
            tarefas.remove(removida);
            lixeiraUndo.add(removida);
            salvarNoArquivo();
            return true;
        }
        return false;
    }

    public synchronized boolean removerPorIndice(int indiceReal) {
        if (indiceReal >= 0 && indiceReal < tarefas.size()) {
            Tarefa removida = tarefas.remove(indiceReal);
            lixeiraUndo.add(removida);
            salvarNoArquivo();
            return true;
        }
        return false;
    }

    public synchronized int limparConcluidas() {
        List<Tarefa> concluidas = tarefas.stream().filter(Tarefa::isConcluido).collect(Collectors.toList());
        lixeiraUndo.addAll(concluidas);
        tarefas.removeAll(concluidas);
        salvarNoArquivo();
        return concluidas.size();
    }

    public synchronized boolean desfazerUltimaRemocao() {
        if (!lixeiraUndo.isEmpty()) {
            Tarefa restaurada = lixeiraUndo.remove(lixeiraUndo.size() - 1);
            tarefas.add(restaurada);
            salvarNoArquivo();
            return true;
        }
        return false;
    }

    public boolean temItemParaDesfazer() {
        return !lixeiraUndo.isEmpty();
    }

    public synchronized boolean alternarStatus(String id) {
        Optional<Tarefa> opt = tarefas.stream().filter(t -> t.getId().equals(id)).findFirst();
        if (opt.isPresent()) {
            opt.get().alternarStatus();
            salvarNoArquivo();
            return true;
        }
        return false;
    }

    // --- PESQUISA E FILTROS ---
    public List<Tarefa> pesquisar(String termo) {
        if (termo == null || termo.trim().isEmpty()) {
            return getTodas();
        }
        String termoLower = termo.trim().toLowerCase();
        return tarefas.stream()
                .filter(t -> t.getDescricao().toLowerCase().contains(termoLower) ||
                        (t.getObservacoes() != null && t.getObservacoes().toLowerCase().contains(termoLower)) ||
                        t.getCategoria().getDescricaoFormatada().toLowerCase().contains(termoLower))
                .collect(Collectors.toList());
    }

    public List<Tarefa> filtrar(String termoBusca, Categoria categoriaFiltro, String statusFiltro) {
        return tarefas.stream()
                .filter(t -> {
                    boolean matchBusca = termoBusca == null || termoBusca.trim().isEmpty() ||
                            t.getDescricao().toLowerCase().contains(termoBusca.toLowerCase()) ||
                            (t.getObservacoes() != null && t.getObservacoes().toLowerCase().contains(termoBusca.toLowerCase()));

                    boolean matchCategoria = categoriaFiltro == null || t.getCategoria() == categoriaFiltro;

                    boolean matchStatus = true;
                    if ("CONCLUIDAS".equalsIgnoreCase(statusFiltro)) {
                        matchStatus = t.isConcluido();
                    } else if ("PENDENTES".equalsIgnoreCase(statusFiltro)) {
                        matchStatus = !t.isConcluido();
                    }

                    return matchBusca && matchCategoria && matchStatus;
                })
                .collect(Collectors.toList());
    }

    // --- PAINEL DE ESTATÍSTICAS ---
    public Estatisticas getEstatisticas() {
        int total = tarefas.size();
        long concluidas = tarefas.stream().filter(Tarefa::isConcluido).count();
        long pendentes = total - concluidas;
        long altaPrioridadePendentes = tarefas.stream()
                .filter(t -> !t.isConcluido() && t.getPrioridade() == Prioridade.ALTA)
                .count();

        double porcentagemConclusao = total == 0 ? 0.0 : ((double) concluidas / total) * 100.0;
        return new Estatisticas(total, (int) concluidas, (int) pendentes, (int) altaPrioridadePendentes, porcentagemConclusao);
    }

    public record Estatisticas(int total, int concluidas, int pendentes, int altaPrioridadePendentes, double porcentagemConclusao) {
        @Override
        public String toString() {
            return String.format("Total: %d | Concluídas: %d | Pendentes: %d | Alta Prioridade Pendentes: %d | Progresso: %.1f%%",
                    total, concluidas, pendentes, altaPrioridadePendentes, porcentagemConclusao);
        }
    }

    // --- PERSISTÊNCIA ---
    private synchronized void salvarNoArquivo() {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(caminhoArquivo)))) {
            for (Tarefa t : tarefas) {
                writer.println(t.toCsvLine());
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar arquivo de tarefas: " + e.getMessage());
        }
    }

    private synchronized void carregarDoArquivo() {
        Path path = Paths.get(caminhoArquivo);
        if (!Files.exists(path)) return;

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            tarefas.clear();
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    Tarefa t = Tarefa.fromCsvLine(line);
                    if (t != null) {
                        tarefas.add(t);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar arquivo de tarefas: " + e.getMessage());
        }
    }

    public String exportarRelatorioMarkdown() {
        StringBuilder sb = new StringBuilder();
        sb.append("# 📊 Relatório Geral do Gestor de Tarefas\n\n");
        Estatisticas stats = getEstatisticas();
        sb.append(String.format("- **Total de Tarefas:** %d\n", stats.total()));
        sb.append(String.format("- **Concluídas:** %d (%.1f%%)\n", stats.concluidas(), stats.porcentagemConclusao()));
        sb.append(String.format("- **Pendentes:** %d\n\n", stats.pendentes()));
        sb.append("## 📜 Lista Completa de Tarefas\n\n");
        sb.append("| ID | Status | Descrição | Categoria | Prioridade | Criada em |\n");
        sb.append("|---|---|---|---|---|---|\n");
        for (Tarefa t : tarefas) {
            String statusSymbol = t.isConcluido() ? "✅ Concluída" : "⏳ Pendente";
            sb.append(String.format("| `%s` | %s | %s | %s | %s | %s |\n",
                    t.getId(), statusSymbol, t.getDescricao(), t.getCategoria().getDescricaoFormatada(),
                    t.getPrioridade().getDescricaoFormatada(), t.getDataCriacaoFormatada()));
        }
        return sb.toString();
    }
}
