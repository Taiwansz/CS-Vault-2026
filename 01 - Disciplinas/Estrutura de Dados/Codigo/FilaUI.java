import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FilaUI {
    private int senha;
    private JFrame frame;
    private JLabel lbl1, lblNome;
    private JTextField edtSenha, edtNome, edtChamado;
    private JButton btnSenha, btnChamar;
    private JTextArea txtFila;
    private FilaDin f;

    public void tela() {
        senha = 1;
        frame = new JFrame("Fila Dinamica usando JFrame");
        frame.setLayout(new FlowLayout());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(800, 400));
        lbl1 = new JLabel("Senha");
        edtSenha = new JTextField(10);
        edtSenha.setText("" + senha);
        lblNome = new JLabel("Nome");
        edtNome = new JTextField(30);

        btnSenha = new JButton("Senha");
        txtFila = new JTextArea(20, 60);
        edtChamado = new JTextField(40);
        btnChamar = new JButton("Proximo");

        frame.getContentPane().add(lbl1);
        frame.getContentPane().add(edtSenha);
        frame.getContentPane().add(lblNome);
        frame.getContentPane().add(edtNome);
        frame.getContentPane().add(btnSenha);
        frame.getContentPane().add(txtFila);
        frame.getContentPane().add(new JLabel("             "));
        frame.getContentPane().add(btnChamar);
        frame.getContentPane().add(edtChamado);
        edtSenha.setEditable(false);
        edtChamado.setEditable(false);
        txtFila.setEditable(false);

        btnSenha.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnSenhaActionPerformed(e);
            }
        });

        btnChamar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnChamarActionPerformed(e);
            }
        });
        frame.pack();
        frame.setVisible(true);
        edtNome.requestFocusInWindow();

    }

    private void btnSenhaActionPerformed(java.awt.event.ActionEvent evt) {
        if (edtNome.getText().trim().isEmpty())
            JOptionPane.showMessageDialog(null, "Nome não pode estar em branco", "erro", 2);
        else {
            senha++;
            Registro r = new Registro();
            r.setChave(Integer.parseInt(edtSenha.getText()));
            r.setNome(edtNome.getText());
            f.inserirFila(r);
            txtFila.setText("");
            f.listarFila(txtFila);
            edtSenha.setText("" + senha);
            edtNome.setText("");
            edtNome.requestFocusInWindow();
        }
    }

    private void btnChamarActionPerformed(java.awt.event.ActionEvent evt) {
        if (f.tamanhoFila() > 0) {
            Registro r = new Registro();
            r = f.excluirFila();
            edtChamado.setText("" + r.getChave() + " - " + r.getNome());
            txtFila.setText("");
            f.listarFila(txtFila);
            edtNome.requestFocusInWindow();
        }
    }

    public FilaUI() {
        f = new FilaDin();
    }

    public static void main(String[] args) {
        FilaUI win = new FilaUI();
        win.tela();
    }
}

class Registro {
    private int chave;
    private String nome;
    public int getChave() {
        return chave;
    }
    public void setChave(int chave) {
        this.chave = chave;
    }

    public String getNome(){
        return nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }
}

class Elemento {
    private Registro reg;
    private Elemento prox;

    public void setReg(Registro reg) {
        this.reg = reg;
    }

    public Registro getReg() {
        return reg;
    }

    public void setProx(Elemento prox) {
        this.prox = prox;
    }

    public Elemento getProx() {
        return prox;
    }
}


class FilaDin {
    private Elemento inicio;
    private Elemento fim;

    public FilaDin() {
        inicio = null;
        fim = null;
    }

    int tamanhoFila() {
        Elemento ender = inicio;
        int tam = 0;
        while (ender != null) {
            tam++;
            ender = ender.getProx();
        }
        return tam;
    }

    void listarFila(JTextArea o) {
        Elemento ender = inicio;
        while (ender != null) {
            o.append("" + ender.getReg().getChave() + " - " + ender.getReg().getNome() + "\n");
            ender = ender.getProx();
        }
    }

    Boolean inserirFila(Registro r) {
        Elemento novo = new Elemento();
        novo.setReg(r);
        novo.setProx(null);
        if (inicio == null)
            inicio = novo;
        else
            fim.setProx(novo);
        fim = novo;
        return true;
    }

    Registro excluirFila() {
        Registro reg = null;
        if (inicio == null) {
            System.out.println("Fila vazia");
        } else {
            reg = inicio.getReg();
            inicio = inicio.getProx();
            if (inicio == null)
                fim = null;

        }
        return reg;
    }
}