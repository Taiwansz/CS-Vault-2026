package view;

import model.Categoria;
import model.Prioridade;
import model.Tarefa;
import service.GerenciadorTarefasService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

public class MainFrame extends JFrame {
    private final GerenciadorTarefasService service;
    
    private JTable tableTarefas;
    private DefaultTableModel tableModel;
    private JTextField txtQuickAdd;
    private JComboBox<Categoria> cbQuickCategory;
    private JComboBox<Prioridade> cbQuickPriority;
    
    private JTextField txtSearch;
    private JComboBox<String> cbFilterStatus;
    private JComboBox<String> cbFilterCategory;

    private JLabel lblTotal;
    private JLabel lblConcluidas;
    private JLabel lblPendentes;
    private JLabel lblAltaPrioridade;
    private JProgressBar progressBar;
    private JButton btnUndo;

    public MainFrame(GerenciadorTarefasService service) {
        this.service = service;
        setTitle("🚀 Gestor de Tarefas ULTRA MEGA BLASTER - Pro Edition");
        setSize(1100, 700);
        setMinimumSize(new Dimension(900, 550));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initTheme();
        initUI();
        carregarTabela();
    }

    private void initTheme() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(new Color(245, 247, 250));

        // --- TOP PANEL (Title + Quick Add) ---
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setOpaque(false);

        // Header Title Panel
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(41, 128, 185));
        titlePanel.setBorder(new EmptyBorder(12, 15, 12, 15));
        
        JLabel lblTitle = new JLabel("⚡ GESTOR DE TAREFAS ULTRA MEGA BLASTER");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(Color.WHITE);

        JLabel lblSubtitle = new JLabel("Sistema Completo de Produtividade em Java 21");
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSubtitle.setForeground(new Color(220, 235, 252));

        titlePanel.add(lblTitle, BorderLayout.NORTH);
        titlePanel.add(lblSubtitle, BorderLayout.SOUTH);

        // Quick Add Bar
        JPanel quickAddPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        quickAddPanel.setBackground(Color.WHITE);
        quickAddPanel.setBorder(BorderFactory.createTitledBorder("➕ Adicionar Nova Tarefa Rápidamente"));

        txtQuickAdd = new JTextField(25);
        txtQuickAdd.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        cbQuickCategory = new JComboBox<>(Categoria.values());
        cbQuickPriority = new JComboBox<>(Prioridade.values());

        JButton btnQuickAdd = new JButton("Adicionar");
        btnQuickAdd.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnQuickAdd.setBackground(new Color(46, 204, 113));
        btnQuickAdd.setForeground(Color.BLACK);
        btnQuickAdd.setFocusPainted(false);
        btnQuickAdd.addActionListener(e -> acaoQuickAdd());

        quickAddPanel.add(new JLabel("Descrição:"));
        quickAddPanel.add(txtQuickAdd);
        quickAddPanel.add(new JLabel("Categoria:"));
        quickAddPanel.add(cbQuickCategory);
        quickAddPanel.add(new JLabel("Prioridade:"));
        quickAddPanel.add(cbQuickPriority);
        quickAddPanel.add(btnQuickAdd);

        topPanel.add(titlePanel, BorderLayout.NORTH);
        topPanel.add(quickAddPanel, BorderLayout.SOUTH);

        // --- CENTER PANEL (Search/Filters + Table + Stats Bar) ---
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setOpaque(false);

        // Filter Bar (Exercício 3 Anabolizado)
        JPanel filterBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        filterBar.setBackground(Color.WHITE);
        filterBar.setBorder(BorderFactory.createTitledBorder("🔍 Pesquisa em Tempo Real e Filtros Avançados"));

        txtSearch = new JTextField(15);
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { carregarTabela(); }
            public void removeUpdate(DocumentEvent e) { carregarTabela(); }
            public void changedUpdate(DocumentEvent e) { carregarTabela(); }
        });

        cbFilterStatus = new JComboBox<>(new String[]{"Todas", "Pendentes", "Concluídas"});
        cbFilterStatus.addActionListener(e -> carregarTabela());

        String[] catOptions = new String[Categoria.values().length + 1];
        catOptions[0] = "Todas as Categorias";
        for (int i = 0; i < Categoria.values().length; i++) {
            catOptions[i + 1] = Categoria.values()[i].getDescricaoFormatada();
        }
        cbFilterCategory = new JComboBox<>(catOptions);
        cbFilterCategory.addActionListener(e -> carregarTabela());

        JButton btnClearFilter = new JButton("Limpar Filtros");
        btnClearFilter.addActionListener(e -> {
            txtSearch.setText("");
            cbFilterStatus.setSelectedIndex(0);
            cbFilterCategory.setSelectedIndex(0);
            carregarTabela();
        });

        filterBar.add(new JLabel("Buscar (Exercício 3):"));
        filterBar.add(txtSearch);
        filterBar.add(new JLabel("Status:"));
        filterBar.add(cbFilterStatus);
        filterBar.add(new JLabel("Categoria:"));
        filterBar.add(cbFilterCategory);
        filterBar.add(btnClearFilter);

        // Table
        String[] columns = {"✓ Status", "ID", "Descrição", "Categoria", "Prioridade", "Criada Em"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) return Boolean.class;
                return String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0; // Apenas checkbox é clicável diretamente
            }
        };

        tableTarefas = new JTable(tableModel);
        tableTarefas.setRowHeight(28);
        tableTarefas.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tableTarefas.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tableTarefas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Cell Renderer para formatação visual
        tableTarefas.setDefaultRenderer(String.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                Boolean isDone = (Boolean) table.getModel().getValueAt(row, 0);
                if (isDone != null && isDone) {
                    c.setForeground(Color.GRAY);
                    setFont(getFont().deriveFont(Font.ITALIC));
                } else {
                    c.setForeground(Color.BLACK);
                    setFont(getFont().deriveFont(Font.PLAIN));
                }
                return c;
            }
        });

        // Alternar status ao clicar na checkbox
        tableModel.addTableModelListener(e -> {
            if (e.getColumn() == 0 && e.getFirstRow() >= 0) {
                int row = e.getFirstRow();
                String id = (String) tableModel.getValueAt(row, 1);
                Boolean newValue = (Boolean) tableModel.getValueAt(row, 0);
                
                List<Tarefa> atual = service.getTodas();
                atual.stream().filter(t -> t.getId().equals(id)).findFirst().ifPresent(t -> {
                    if (t.isConcluido() != newValue) {
                        service.alternarStatus(id);
                        atualizarEstatisticas();
                    }
                });
            }
        });

        JScrollPane scrollTable = new JScrollPane(tableTarefas);

        // Stats Dashboard Panel (Exercício 2 Anabolizado)
        JPanel statsPanel = new JPanel(new BorderLayout(10, 5));
        statsPanel.setBackground(Color.WHITE);
        statsPanel.setBorder(BorderFactory.createTitledBorder("📊 Painel de Estatísticas e Progresso (Exercício 2)"));

        JPanel labelsStats = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        labelsStats.setOpaque(false);

        lblTotal = new JLabel("Total: 0");
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 13));
        
        lblConcluidas = new JLabel("Concluídas: 0");
        lblConcluidas.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblConcluidas.setForeground(new Color(39, 174, 96));

        lblPendentes = new JLabel("Pendentes: 0");
        lblPendentes.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblPendentes.setForeground(new Color(230, 126, 34));

        lblAltaPrioridade = new JLabel("Alta Prioridade: 0");
        lblAltaPrioridade.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblAltaPrioridade.setForeground(new Color(192, 57, 43));

        labelsStats.add(lblTotal);
        labelsStats.add(lblConcluidas);
        labelsStats.add(lblPendentes);
        labelsStats.add(lblAltaPrioridade);

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        progressBar.setForeground(new Color(46, 204, 113));
        progressBar.setPreferredSize(new Dimension(200, 22));

        statsPanel.add(labelsStats, BorderLayout.CENTER);
        statsPanel.add(progressBar, BorderLayout.EAST);

        centerPanel.add(filterBar, BorderLayout.NORTH);
        centerPanel.add(scrollTable, BorderLayout.CENTER);
        centerPanel.add(statsPanel, BorderLayout.SOUTH);

        // --- RIGHT ACTION BUTTONS PANEL ---
        JPanel actionPanel = new JPanel(new GridLayout(7, 1, 5, 8));
        actionPanel.setOpaque(false);
        actionPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));

        JButton btnToggleStatus = new JButton("✅ Alternar Status");
        btnToggleStatus.addActionListener(e -> acaoAlternarStatus());

        JButton btnDelete = new JButton("🗑️ Remover (Ex 1)");
        btnDelete.setBackground(new Color(231, 76, 60));
        btnDelete.setForeground(Color.BLACK);
        btnDelete.addActionListener(e -> acaoRemover());

        JButton btnClearDone = new JButton("🧹 Limpar Concluídas");
        btnClearDone.addActionListener(e -> acaoLimparConcluidas());

        btnUndo = new JButton("↩️ Desfazer Removida");
        btnUndo.addActionListener(e -> acaoDesfazer());

        JButton btnFullStats = new JButton("📈 Relatório Completo");
        btnFullStats.addActionListener(e -> acaoRelatorioCompleto());

        JButton btnExport = new JButton("💾 Exportar Markdown");
        btnExport.addActionListener(e -> acaoExportar());

        JButton btnRefresh = new JButton("🔄 Recarregar");
        btnRefresh.addActionListener(e -> carregarTabela());

        actionPanel.add(btnToggleStatus);
        actionPanel.add(btnDelete);
        actionPanel.add(btnClearDone);
        actionPanel.add(btnUndo);
        actionPanel.add(btnFullStats);
        actionPanel.add(btnExport);
        actionPanel.add(btnRefresh);

        // Layout Assembly
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(actionPanel, BorderLayout.EAST);

        add(mainPanel);
    }

    private void acaoQuickAdd() {
        String desc = txtQuickAdd.getText().trim();
        if (desc.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, digite a descrição da tarefa!", "Campo Vazio", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Categoria cat = (Categoria) cbQuickCategory.getSelectedItem();
        Prioridade prio = (Prioridade) cbQuickPriority.getSelectedItem();

        service.adicionar(desc, cat, prio, "");
        txtQuickAdd.setText("");
        carregarTabela();
    }

    private void acaoAlternarStatus() {
        int selectedRow = tableTarefas.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Selecione uma tarefa na tabela primeiro!", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        String id = (String) tableModel.getValueAt(selectedRow, 1);
        service.alternarStatus(id);
        carregarTabela();
    }

    private void acaoRemover() {
        int selectedRow = tableTarefas.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Selecione a tarefa que deseja remover da tabela!", "Aviso (Exercício 1)", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String desc = (String) tableModel.getValueAt(selectedRow, 2);
        String id = (String) tableModel.getValueAt(selectedRow, 1);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja apagar a tarefa:\n\"" + desc + "\"?",
                "Confirmar Remoção (Exercício 1)", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            service.removerPorId(id);
            carregarTabela();
            JOptionPane.showMessageDialog(this, "Tarefa removida! Você pode clicar em 'Desfazer Removida' para restaurá-la.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void acaoLimparConcluidas() {
        int count = service.limparConcluidas();
        if (count == 0) {
            JOptionPane.showMessageDialog(this, "Nenhuma tarefa concluída para limpar.", "Informação", JOptionPane.INFORMATION_MESSAGE);
        } else {
            carregarTabela();
            JOptionPane.showMessageDialog(this, count + " tarefa(s) concluída(s) foram movidas para a lixeira!", "Limpeza Concluída", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void acaoDesfazer() {
        if (service.desfazerUltimaRemocao()) {
            carregarTabela();
            JOptionPane.showMessageDialog(this, "Última tarefa removida foi restaurada!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Não há tarefas na lixeira para desfazer.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void acaoRelatorioCompleto() {
        GerenciadorTarefasService.Estatisticas stats = service.getEstatisticas();
        String mensagem = String.format("""
                📊 PAINEL DETALHADO DE ESTATÍSTICAS (EXERCÍCIO 2)
                --------------------------------------------------
                • Total de Tarefas Registradas: %d
                • Tarefas Concluídas: %d
                • Tarefas Pendentes: %d
                • Tarefas de Alta Prioridade Pendentes: %d
                • Taxa de Conclusão / Produtividade: %.1f%%
                
                O progresso geral é recalculado automaticamente em tempo real!
                """, stats.total(), stats.concluidas(), stats.pendentes(), stats.altaPrioridadePendentes(), stats.porcentagemConclusao());

        JOptionPane.showMessageDialog(this, mensagem, "Estatísticas Avançadas", JOptionPane.INFORMATION_MESSAGE);
    }

    private void acaoExportar() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Salvar Relatório em Markdown");
        fileChooser.setSelectedFile(new File("relatorio_tarefas.md"));

        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try (PrintWriter out = new PrintWriter(new FileWriter(fileToSave))) {
                out.print(service.exportarRelatorioMarkdown());
                JOptionPane.showMessageDialog(this, "Relatório exportado com sucesso para:\n" + fileToSave.getAbsolutePath(), "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao exportar arquivo: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void carregarTabela() {
        tableModel.setRowCount(0);

        String termoBusca = txtSearch.getText().trim();
        String statusSel = (String) cbFilterStatus.getSelectedItem();
        int catIndex = cbFilterCategory.getSelectedIndex();
        Categoria catSel = catIndex > 0 ? Categoria.values()[catIndex - 1] : null;

        List<Tarefa> filtradas = service.filtrar(termoBusca, catSel, statusSel);

        for (Tarefa t : filtradas) {
            tableModel.addRow(new Object[]{
                    t.isConcluido(),
                    t.getId(),
                    t.getDescricao(),
                    t.getCategoria().getDescricaoFormatada(),
                    t.getPrioridade().getDescricaoFormatada(),
                    t.getDataCriacaoFormatada()
            });
        }

        atualizarEstatisticas();
    }

    private void atualizarEstatisticas() {
        GerenciadorTarefasService.Estatisticas stats = service.getEstatisticas();
        lblTotal.setText("Total: " + stats.total());
        lblConcluidas.setText("Concluídas: " + stats.concluidas());
        lblPendentes.setText("Pendentes: " + stats.pendentes());
        lblAltaPrioridade.setText("Alta Prioridade: " + stats.altaPrioridadePendentes());

        int perc = (int) Math.round(stats.porcentagemConclusao());
        progressBar.setValue(perc);
        progressBar.setString(perc + "% Concluído");

        btnUndo.setEnabled(service.temItemParaDesfazer());
    }
}
