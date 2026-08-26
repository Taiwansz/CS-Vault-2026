package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Tarefa {
    private String id;
    private String descricao;
    private boolean concluido;
    private Categoria categoria;
    private Prioridade prioridade;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataConclusao;
    private String observacoes;

    public Tarefa(String descricao, Categoria categoria, Prioridade prioridade, String observacoes) {
        this.id = UUID.randomUUID().toString().substring(0, 8);
        this.descricao = descricao;
        this.concluido = false;
        this.categoria = categoria != null ? categoria : Categoria.OUTROS;
        this.prioridade = prioridade != null ? prioridade : Prioridade.MEDIA;
        this.dataCriacao = LocalDateTime.now();
        this.observacoes = observacoes != null ? observacoes : "";
    }

    public Tarefa(String id, String descricao, boolean concluido, Categoria categoria, Prioridade prioridade, LocalDateTime dataCriacao, LocalDateTime dataConclusao, String observacoes) {
        this.id = id;
        this.descricao = descricao;
        this.concluido = concluido;
        this.categoria = categoria;
        this.prioridade = prioridade;
        this.dataCriacao = dataCriacao;
        this.dataConclusao = dataConclusao;
        this.observacoes = observacoes;
    }

    public String getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isConcluido() {
        return concluido;
    }

    public void setConcluido(boolean concluido) {
        this.concluido = concluido;
        if (concluido) {
            this.dataConclusao = LocalDateTime.now();
        } else {
            this.dataConclusao = null;
        }
    }

    public void alternarStatus() {
        setConcluido(!this.concluido);
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public LocalDateTime getDataConclusao() {
        return dataConclusao;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getDataCriacaoFormatada() {
        if (dataCriacao == null) return "-";
        return dataCriacao.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    public String getDataConclusaoFormatada() {
        if (dataConclusao == null) return "-";
        return dataConclusao.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    public String toCsvLine() {
        // Escapa pipe '|' para evitar quebra na conversão de arquivo
        String descEscaped = descricao.replace("|", " ");
        String obsEscaped = observacoes.replace("|", " ").replace("\n", " ");
        String dataCriacaoStr = dataCriacao != null ? dataCriacao.toString() : "";
        String dataConclusaoStr = dataConclusao != null ? dataConclusao.toString() : "";
        return String.join("|",
                id,
                descEscaped,
                String.valueOf(concluido),
                categoria.name(),
                prioridade.name(),
                dataCriacaoStr,
                dataConclusaoStr,
                obsEscaped
        );
    }

    public static Tarefa fromCsvLine(String line) {
        try {
            String[] parts = line.split("\\|", -1);
            if (parts.length < 8) return null;
            String id = parts[0];
            String descricao = parts[1];
            boolean concluido = Boolean.parseBoolean(parts[2]);
            Categoria categoria = Categoria.valueOf(parts[3]);
            Prioridade prioridade = Prioridade.valueOf(parts[4]);
            LocalDateTime dataCriacao = parts[5].isEmpty() ? LocalDateTime.now() : LocalDateTime.parse(parts[5]);
            LocalDateTime dataConclusao = parts[6].isEmpty() ? null : LocalDateTime.parse(parts[6]);
            String observacoes = parts[7];
            return new Tarefa(id, descricao, concluido, categoria, prioridade, dataCriacao, dataConclusao, observacoes);
        } catch (Exception e) {
            return null;
        }
    }
}
