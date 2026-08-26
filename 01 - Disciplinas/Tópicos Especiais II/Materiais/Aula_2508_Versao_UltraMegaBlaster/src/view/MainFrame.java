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
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

public class MainFrame extends JFrame {
    private final GerenciadorTarefasService service;
    
    // UI Constants
    private static final Color BG_COLOR = new Color(248, 250, 252);
    private static final Color SIDEBAR_COLOR = new Color(15, 23, 42);
    private static final Color CARD_COLOR = Color.WHITE;
    private static final Color TEXT_PRIMARY = new Color(30, 41, 59);
    private static final Color TEXT_SECONDARY = new Color(100, 116, 139);
    private static final Color ACCENT_COLOR = new Color(37, 99, 235);
    private static final Color BORDER_COLOR = new Color(226, 232, 240);
    private static final Font FONT_REGULAR = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FONT_BOLD = new Font("Segoe UI", Font.BOLD, 14);
    private static final Font FONT_H1 = new Font("Segoe UI", Font.BOLD, 22);
    
    private JTable tableTarefas;
    private DefaultTableModel tableModel;
    private JTextField txtSearch;
    private JTextField txtQuickAdd;
    private JComboBox<Categoria> cbQuickCategory;
    private JComboBox<Prioridade> cbQuickPriority;
    
    private JLabel lblTotal;
    private JLabel lblConcluidas;
    private JLabel lblPendentes;
    private CustomProgressBar progressBar;

    public MainFrame(GerenciadorTarefasService service) {
        this.service = service;
        setTitle("Gestor de Tarefas Profissional");
        setSize(1200, 800);
        setMinimumSize(new Dimension(1000, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Remove default focus borders
        UIManager.put("Button.focus", new Color(0, 0, 0, 0));
        UIManager.put("ComboBox.focus", new Color(0, 0, 0, 0));
        UIManager.put("TextField.focus", new Color(0, 0, 0, 0));

        initUI();
        carregarTabela(null);
    }

    private void initUI() {
        JPanel mainContainer = new JPanel(new BorderLayout());
        mainContainer.setBackground(BG_COLOR);

        // Sidebar
        mainContainer.add(createSidebar(), BorderLayout.WEST);

        // Main Content Area
        JPanel contentPanel = new JPanel(new BorderLayout(0, 20));
        contentPanel.setBackground(BG_COLOR);
        contentPanel.setBorder(new EmptyBorder(30, 40, 30, 40));

        contentPanel.add(createHeaderPanel(), BorderLayout.NORTH);
        contentPanel.add(createTablePanel(), BorderLayout.CENTER);

        mainContainer.add(contentPanel, BorderLayout.CENTER);

        setContentPane(mainContainer);
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel(new BorderLayout());
        sidebar.setBackground(SIDEBAR_COLOR);
        sidebar.setPreferredSize(new Dimension(280, 0));
        sidebar.setBorder(new EmptyBorder(30, 20, 30, 20));

        // Brand
        JLabel lblBrand = new JLabel("TaskMaster");
        lblBrand.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblBrand.setForeground(Color.WHITE);
        
        JLabel lblSubtitle = new JLabel("Sistema de Produtividade");
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSubtitle.setForeground(new Color(148, 163, 184));
        
        JPanel brandPanel = new JPanel(new BorderLayout());
        brandPanel.setOpaque(false);
        brandPanel.add(lblBrand, BorderLayout.NORTH);
        brandPanel.add(lblSubtitle, BorderLayout.SOUTH);
        brandPanel.setBorder(new EmptyBorder(0, 0, 40, 0));

        // Stats Section
        JPanel statsPanel = new JPanel(new GridLayout(4, 1, 0, 15));
        statsPanel.setOpaque(false);
        
        lblTotal = createSidebarStatLabel("Total de Tarefas: 0");
        lblPendentes = createSidebarStatLabel("Pendentes: 0");
        lblConcluidas = createSidebarStatLabel("Concluídas: 0");
        
        progressBar = new CustomProgressBar();
        
        statsPanel.add(lblTotal);
        statsPanel.add(lblPendentes);
        statsPanel.add(lblConcluidas);
        statsPanel.add(progressBar);

        // Actions Section
        JPanel actionsPanel = new JPanel(new GridLayout(4, 1, 0, 10));
        actionsPanel.setOpaque(false);
        
        actionsPanel.add(createSidebarButton("Exportar Relatório", this::acaoExportar));
        actionsPanel.add(createSidebarButton("Limpar Concluídas", this::acaoLimparConcluidas));
        actionsPanel.add(createSidebarButton("Desfazer Remoção", this::acaoDesfazer));

        JPanel topContainer = new JPanel(new BorderLayout());
        topContainer.setOpaque(false);
        topContainer.add(brandPanel, BorderLayout.NORTH);
        topContainer.add(statsPanel, BorderLayout.CENTER);

        sidebar.add(topContainer, BorderLayout.NORTH);
        sidebar.add(actionsPanel, BorderLayout.SOUTH);

        return sidebar;
    }

    private JLabel createSidebarStatLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(FONT_REGULAR);
        lbl.setForeground(new Color(226, 232, 240));
        return lbl;
    }

    private JButton createSidebarButton(String text, Runnable action) {
        JButton btn = new JButton(text);
        btn.setFont(FONT_REGULAR);
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(30, 41, 59));
        btn.setBorder(new EmptyBorder(10, 15, 10, 15));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(new Color(51, 65, 85)); }
            public void mouseExited(MouseEvent e) { btn.setBackground(new Color(30, 41, 59)); }
        });
        btn.addActionListener(e -> action.run());
        return btn;
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout(20, 0));
        headerPanel.setOpaque(false);

        // Title
        JLabel lblTitle = new JLabel("Visão Geral");
        lblTitle.setFont(FONT_H1);
        lblTitle.setForeground(TEXT_PRIMARY);

        // Search Bar
        RoundedPanel searchContainer = new RoundedPanel(20, CARD_COLOR);
        searchContainer.setLayout(new BorderLayout());
        searchContainer.setBorder(new EmptyBorder(5, 15, 5, 15));
        searchContainer.setPreferredSize(new Dimension(300, 40));

        txtSearch = new JTextField(20);
        txtSearch.setBorder(null);
        txtSearch.setFont(FONT_REGULAR);
        txtSearch.setForeground(TEXT_PRIMARY);
        txtSearch.setBackground(CARD_COLOR);
        TextPrompt tpSearch = new TextPrompt("Pesquisar tarefas...", txtSearch);
        tpSearch.setForeground(TEXT_SECONDARY);

        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { carregarTabela(txtSearch.getText()); }
            public void removeUpdate(DocumentEvent e) { carregarTabela(txtSearch.getText()); }
            public void changedUpdate(DocumentEvent e) { carregarTabela(txtSearch.getText()); }
        });

        searchContainer.add(txtSearch, BorderLayout.CENTER);

        JPanel rightHeader = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        rightHeader.setOpaque(false);
        rightHeader.add(searchContainer);

        headerPanel.add(lblTitle, BorderLayout.WEST);
        headerPanel.add(rightHeader, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel createTablePanel() {
        RoundedPanel cardPanel = new RoundedPanel(16, CARD_COLOR);
        cardPanel.setLayout(new BorderLayout(0, 15));
        cardPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Quick Add Section
        JPanel quickAddPanel = new JPanel(new BorderLayout(10, 0));
        quickAddPanel.setOpaque(false);

        txtQuickAdd = new JTextField();
        txtQuickAdd.setFont(FONT_REGULAR);
        txtQuickAdd.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1, true),
            new EmptyBorder(10, 15, 10, 15)
        ));
        TextPrompt tpAdd = new TextPrompt("O que precisa ser feito?", txtQuickAdd);
        tpAdd.setForeground(TEXT_SECONDARY);

        JPanel quickAddOptions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        quickAddOptions.setOpaque(false);

        cbQuickCategory = new JComboBox<>(Categoria.values());
        cbQuickCategory.setFont(FONT_REGULAR);
        cbQuickCategory.setBackground(CARD_COLOR);
        
        cbQuickPriority = new JComboBox<>(Prioridade.values());
        cbQuickPriority.setFont(FONT_REGULAR);
        cbQuickPriority.setBackground(CARD_COLOR);

        JButton btnAdd = new JButton("Adicionar");
        btnAdd.setFont(FONT_BOLD);
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setBackground(ACCENT_COLOR);
        btnAdd.setBorder(new EmptyBorder(10, 25, 10, 25));
        btnAdd.setFocusPainted(false);
        btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAdd.addActionListener(e -> acaoQuickAdd());

        quickAddOptions.add(cbQuickCategory);
        quickAddOptions.add(cbQuickPriority);
        quickAddOptions.add(btnAdd);

        quickAddPanel.add(txtQuickAdd, BorderLayout.CENTER);
        quickAddPanel.add(quickAddOptions, BorderLayout.EAST);

        // Table Setup
        String[] columns = {"ID", "Tarefa", "Categoria", "Prioridade", "Status", "Ação"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tableTarefas = new JTable(tableModel);
        tableTarefas.setRowHeight(50);
        tableTarefas.setFont(FONT_REGULAR);
        tableTarefas.setForeground(TEXT_PRIMARY);
        tableTarefas.setSelectionBackground(new Color(241, 245, 249));
        tableTarefas.setSelectionForeground(TEXT_PRIMARY);
        tableTarefas.setShowVerticalLines(false);
        tableTarefas.setGridColor(BORDER_COLOR);
        tableTarefas.setIntercellSpacing(new Dimension(0, 0));
        tableTarefas.setBorder(null);

        // Header Styling
        JTableHeader header = tableTarefas.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(CARD_COLOR);
        header.setForeground(TEXT_SECONDARY);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, BORDER_COLOR));
        ((DefaultTableCellRenderer)header.getDefaultRenderer()).setHorizontalAlignment(JLabel.LEFT);
        header.setPreferredSize(new Dimension(header.getWidth(), 40));

        // Column Widths
        tableTarefas.getColumnModel().getColumn(0).setPreferredWidth(60);
        tableTarefas.getColumnModel().getColumn(0).setMaxWidth(80);
        tableTarefas.getColumnModel().getColumn(1).setPreferredWidth(300);
        tableTarefas.getColumnModel().getColumn(2).setPreferredWidth(100);
        tableTarefas.getColumnModel().getColumn(3).setPreferredWidth(100);
        tableTarefas.getColumnModel().getColumn(4).setPreferredWidth(100);
        tableTarefas.getColumnModel().getColumn(5).setPreferredWidth(120);

        // Custom Renderers
        tableTarefas.setDefaultRenderer(Object.class, new CustomCellRenderer());

        JScrollPane scrollPane = new JScrollPane(tableTarefas);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(CARD_COLOR);

        // Inline Actions Handle via MouseListener since buttons in cells in basic Swing are complex
        tableTarefas.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = tableTarefas.rowAtPoint(e.getPoint());
                int col = tableTarefas.columnAtPoint(e.getPoint());
                if (row >= 0) {
                    String id = (String) tableModel.getValueAt(row, 0);
                    if (col == 4) { // Status column clicked
                        service.alternarStatus(id);
                        carregarTabela(txtSearch.getText());
                    } else if (col == 5) { // Action column clicked
                        acaoRemoverId(id);
                    }
                }
            }
        });

        cardPanel.add(quickAddPanel, BorderLayout.NORTH);
        cardPanel.add(scrollPane, BorderLayout.CENTER);

        return cardPanel;
    }

    private void carregarTabela(String termoBusca) {
        tableModel.setRowCount(0);
        List<Tarefa> filtradas = service.pesquisar(termoBusca);

        for (Tarefa t : filtradas) {
            tableModel.addRow(new Object[]{
                    t.getId(),
                    t, // Pass full object for custom rendering of description based on status
                    t.getCategoria().getDescricaoFormatada(),
                    t.getPrioridade(), // Pass enum for custom rendering
                    t.isConcluido() ? "Concluída" : "Pendente",
                    "Remover"
            });
        }
        atualizarEstatisticas();
    }

    private void atualizarEstatisticas() {
        GerenciadorTarefasService.Estatisticas stats = service.getEstatisticas();
        lblTotal.setText("Total de Tarefas: " + stats.total());
        lblPendentes.setText("Pendentes: " + stats.pendentes());
        lblConcluidas.setText("Concluídas: " + stats.concluidas());
        progressBar.setProgress((int) stats.porcentagemConclusao());
    }

    private void acaoQuickAdd() {
        String desc = txtQuickAdd.getText().trim();
        if (desc.isEmpty()) return;
        
        Categoria cat = (Categoria) cbQuickCategory.getSelectedItem();
        Prioridade prio = (Prioridade) cbQuickPriority.getSelectedItem();
        
        service.adicionar(desc, cat, prio, "");
        txtQuickAdd.setText("");
        carregarTabela(txtSearch.getText());
    }

    private void acaoRemoverId(String id) {
        if (service.removerPorId(id)) {
            carregarTabela(txtSearch.getText());
        }
    }

    private void acaoLimparConcluidas() {
        service.limparConcluidas();
        carregarTabela(txtSearch.getText());
    }

    private void acaoDesfazer() {
        if (service.desfazerUltimaRemocao()) {
            carregarTabela(txtSearch.getText());
        }
    }

    private void acaoExportar() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Salvar Relatório");
        fileChooser.setSelectedFile(new File("relatorio_tarefas.md"));

        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try (PrintWriter out = new PrintWriter(new FileWriter(fileChooser.getSelectedFile()))) {
                out.print(service.exportarRelatorioMarkdown());
                JOptionPane.showMessageDialog(this, "Relatório exportado com sucesso.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao exportar arquivo.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // --- CUSTOM UI COMPONENTS ---

    class RoundedPanel extends JPanel {
        private final int radius;
        public RoundedPanel(int radius, Color bg) {
            this.radius = radius;
            setBackground(bg);
            setOpaque(false);
        }
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
            g2.setColor(BORDER_COLOR);
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    class CustomProgressBar extends JPanel {
        private int progress = 0;
        public CustomProgressBar() {
            setOpaque(false);
            setPreferredSize(new Dimension(200, 10));
        }
        public void setProgress(int progress) {
            this.progress = progress;
            repaint();
        }
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Background track
            g2.setColor(new Color(30, 41, 59));
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
            
            // Progress fill
            if (progress > 0) {
                int width = (int) (getWidth() * (progress / 100.0));
                g2.setColor(ACCENT_COLOR);
                g2.fillRoundRect(0, 0, width, getHeight(), 10, 10);
            }
            g2.dispose();
        }
    }

    class CustomCellRenderer extends DefaultTableCellRenderer {
        private final JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        private final JLabel label = new JLabel();

        public CustomCellRenderer() {
            panel.setOpaque(true);
            panel.add(label);
            label.setFont(FONT_REGULAR);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            panel.setBackground(isSelected ? table.getSelectionBackground() : CARD_COLOR);
            label.setForeground(isSelected ? table.getSelectionForeground() : TEXT_PRIMARY);
            
            if (value instanceof Tarefa) {
                Tarefa t = (Tarefa) value;
                label.setText(t.getDescricao());
                if (t.isConcluido()) {
                    label.setForeground(TEXT_SECONDARY);
                    label.setText("<html><strike>" + t.getDescricao() + "</strike></html>");
                }
            } else if (value instanceof Prioridade) {
                Prioridade p = (Prioridade) value;
                label.setText(p.getDescricaoFormatada());
                if (p == Prioridade.ALTA) label.setForeground(new Color(220, 38, 38));
                else if (p == Prioridade.MEDIA) label.setForeground(new Color(202, 138, 4));
                else label.setForeground(new Color(22, 163, 74));
            } else if (column == 4) { // Status
                String status = (String) value;
                label.setText(status);
                label.setFont(FONT_BOLD);
                label.setForeground(status.equals("Concluída") ? new Color(22, 163, 74) : new Color(234, 88, 12));
                panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            } else if (column == 5) { // Ação
                label.setText("Remover");
                label.setFont(FONT_BOLD);
                label.setForeground(new Color(220, 38, 38));
                panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            } else {
                label.setText(value != null ? value.toString() : "");
            }
            
            return panel;
        }
    }

    // Helper class for placeholder text in JTextField
    class TextPrompt extends JLabel implements DocumentListener {
        private JTextField component;
        public TextPrompt(String text, JTextField component) {
            this.component = component;
            setText(text);
            setFont(component.getFont());
            setBorder(new EmptyBorder(component.getInsets()));
            component.setLayout(new BorderLayout());
            component.add(this);
            component.getDocument().addDocumentListener(this);
        }
        public void insertUpdate(DocumentEvent e) { checkForPrompt(); }
        public void removeUpdate(DocumentEvent e) { checkForPrompt(); }
        public void changedUpdate(DocumentEvent e) { }
        private void checkForPrompt() { setVisible(component.getText().isEmpty()); }
    }
}
