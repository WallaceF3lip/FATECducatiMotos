/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ducatimotos;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * Tela de cadastro de nova moto
 *
 * @author walla
 */
public class ScreenCreate extends JDialog {

    // ─── Campos do formulário ────────────────────────────────────────────────
    private JTextField campoModelo;
    private JTextField campoCor;
    private JTextField campoAno;
    private JTextField campoCilindrada;
    private JTextField campoPreco;

    // ─── Botões ──────────────────────────────────────────────────────────────
    private JButton btnSalvar;
    private JButton btnCancelar;

    // ─── Referência para atualizar a tela principal após salvar ─────────────
    private final ScreenHome screenHome;

    // ─── Paleta de cores (mesma do ScreenHome) ───────────────────────────────
    private static final Color COR_FUNDO        = new Color(18, 18, 18);
    private static final Color COR_PAINEL       = new Color(28, 28, 28);
    private static final Color COR_HEADER       = new Color(35, 35, 35);
    private static final Color COR_VERMELHO     = new Color(204, 0, 0);
    private static final Color COR_VERMELHO_HOV = new Color(230, 30, 30);
    private static final Color COR_CINZA        = new Color(60, 60, 60);
    private static final Color COR_CINZA_HOV    = new Color(80, 80, 80);
    private static final Color COR_TEXTO        = new Color(240, 240, 240);
    private static final Color COR_TEXTO_SUAVE  = new Color(160, 160, 160);
    private static final Color COR_ERRO         = new Color(255, 80, 80);

    // ────────────────────────────────────────────────────────────────────────
    public ScreenCreate(ScreenHome parent) {
        // true = modal: bloqueia a janela pai enquanto este diálogo estiver aberto
        super(parent, "Ducati Motos — Nova Moto", true);
        this.screenHome = parent;
        inicializarTela();
        construirInterface();
        setVisible(true);
    }

    // ─── Configurações gerais do JDialog ────────────────────────────────
    private void inicializarTela() {
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(480, 580);
        setResizable(false);
        setLocationRelativeTo(screenHome);
        getContentPane().setBackground(COR_FUNDO);
        setLayout(new BorderLayout());
    }

    // ─── Montagem da interface ───────────────────────────────────────────────
    private void construirInterface() {
        add(criarPainelTopo(), BorderLayout.NORTH);
        add(criarPainelFormulario(), BorderLayout.CENTER);
        add(criarPainelBotoes(), BorderLayout.SOUTH);
    }

    // ── Painel superior ──────────────────────────────────────────────────────
    private JPanel criarPainelTopo() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(COR_HEADER);
        painel.setBorder(new EmptyBorder(18, 24, 18, 24));

        JLabel lblTitulo = new JLabel("Nova Moto");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblTitulo.setForeground(COR_TEXTO);

        JLabel lblSub = new JLabel("Preencha os dados para cadastrar uma nova moto");
        lblSub.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblSub.setForeground(COR_TEXTO_SUAVE);

        JPanel blocoTexto = new JPanel(new GridLayout(2, 1, 0, 4));
        blocoTexto.setOpaque(false);
        blocoTexto.add(lblTitulo);
        blocoTexto.add(lblSub);

        // Barra vermelha lateral
        JPanel barraLateral = new JPanel();
        barraLateral.setBackground(COR_VERMELHO);
        barraLateral.setPreferredSize(new Dimension(4, 0));

        painel.add(barraLateral, BorderLayout.WEST);
        painel.add(blocoTexto, BorderLayout.CENTER);

        JSeparator sep = new JSeparator();
        sep.setForeground(COR_VERMELHO);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(COR_HEADER);
        wrapper.add(painel, BorderLayout.CENTER);
        wrapper.add(sep, BorderLayout.SOUTH);
        return wrapper;
    }

    // ── Painel com os campos do formulário ───────────────────────────────────
    private JPanel criarPainelFormulario() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(COR_PAINEL);
        painel.setBorder(new EmptyBorder(24, 32, 16, 32));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 0, 6, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        // ── Modelo ──────────────────────────────────────────────────────────
        painel.add(criarLabel("Modelo"), gbc);
        campoModelo = criarCampo("Ex: Panigale V4");
        painel.add(campoModelo, gbc);

        // ── Cor ─────────────────────────────────────────────────────────────
        painel.add(criarLabel("Cor"), gbc);
        campoCor = criarCampo("Ex: Vermelha");
        painel.add(campoCor, gbc);

        // ── Linha dupla: Ano | Cilindrada ────────────────────────────────────
        painel.add(criarLabel("Ano"), gbc);
        campoAno = criarCampo("Ex: 2024");
        campoAno.addKeyListener(apenasNumeros());
        painel.add(campoAno, gbc);

        painel.add(criarLabel("Cilindrada (cc)"), gbc);
        campoCilindrada = criarCampo("Ex: 1103");
        campoCilindrada.addKeyListener(apenasNumeros());
        painel.add(campoCilindrada, gbc);

        // ── Preço ────────────────────────────────────────────────────────────
        painel.add(criarLabel("Preço (R$)"), gbc);
        campoPreco = criarCampo("Ex: 189990.00");
        campoPreco.addKeyListener(apenasDecimal());
        painel.add(campoPreco, gbc);

        // Espaçador final
        gbc.weighty = 1.0;
        painel.add(Box.createVerticalGlue(), gbc);

        return painel;
    }

    // ── Painel de botões ─────────────────────────────────────────────────────
    private JPanel criarPainelBotoes() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        painel.setBackground(COR_HEADER);
        painel.setBorder(new EmptyBorder(14, 24, 14, 24));

        btnCancelar = criarBotao("Cancelar", COR_CINZA, COR_CINZA_HOV);
        btnSalvar   = criarBotao("✔  Salvar", COR_VERMELHO, COR_VERMELHO_HOV);

        btnCancelar.addActionListener(e -> dispose());
        btnSalvar.addActionListener(e -> salvar());

        // Enter também salva
        getRootPane().setDefaultButton(btnSalvar);

        painel.add(btnCancelar);
        painel.add(btnSalvar);

        JSeparator sep = new JSeparator();
        sep.setForeground(COR_CINZA);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(COR_HEADER);
        wrapper.add(sep, BorderLayout.NORTH);
        wrapper.add(painel, BorderLayout.CENTER);
        return wrapper;
    }

    // ─── Lógica de salvar ────────────────────────────────────────────────────
    private void salvar() {
        // Limpa bordas de erro anteriores
        resetarBordas();

        // Validação dos campos
        String modelo     = campoModelo.getText().trim();
        String cor        = campoCor.getText().trim();
        String anoTxt     = campoAno.getText().trim();
        String cilTxt     = campoCilindrada.getText().trim();
        String precoTxt   = campoPreco.getText().trim();

        boolean valido = true;

        if (modelo.isEmpty())   { marcarErro(campoModelo);      valido = false; }
        if (cor.isEmpty())      { marcarErro(campoCor);         valido = false; }
        if (anoTxt.isEmpty())   { marcarErro(campoAno);         valido = false; }
        if (cilTxt.isEmpty())   { marcarErro(campoCilindrada);  valido = false; }
        if (precoTxt.isEmpty()) { marcarErro(campoPreco);       valido = false; }

        if (!valido) {
            JOptionPane.showMessageDialog(this,
                "Preencha todos os campos destacados em vermelho.",
                "Campos obrigatórios", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int    ano;
        int    cilindrada;
        double preco;

        try { ano = Integer.parseInt(anoTxt); }
        catch (NumberFormatException ex) {
            marcarErro(campoAno);
            JOptionPane.showMessageDialog(this, "Ano inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try { cilindrada = Integer.parseInt(cilTxt); }
        catch (NumberFormatException ex) {
            marcarErro(campoCilindrada);
            JOptionPane.showMessageDialog(this, "Cilindrada inválida.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try { preco = Double.parseDouble(precoTxt.replace(",", ".")); }
        catch (NumberFormatException ex) {
            marcarErro(campoPreco);
            JOptionPane.showMessageDialog(this, "Preço inválido. Use ponto como separador decimal.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Chama o método creatMoto da classe DucatiMotos
        DucatiMotos moto = new DucatiMotos();
        boolean sucesso = moto.creatMoto(modelo, cor, ano, cilindrada, preco);

        if (sucesso) {
            JOptionPane.showMessageDialog(this,
                "Moto \"" + modelo + "\" cadastrada com sucesso!",
                "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            // Recarrega a lista na tela principal e fecha esta janela
            if (screenHome != null) {
                screenHome.carregarMotos();
            }
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                "Erro ao cadastrar a moto. Verifique a conexão com o banco de dados.",
                "Erro ao salvar", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ─── Helpers visuais ─────────────────────────────────────────────────────
    private JLabel criarLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("SansSerif", Font.BOLD, 12));
        label.setForeground(COR_TEXTO_SUAVE);
        return label;
    }

    private JTextField criarCampo(String placeholder) {
        JTextField campo = new JTextField();
        campo.setBackground(new Color(45, 45, 45));
        campo.setForeground(COR_TEXTO);
        campo.setCaretColor(COR_TEXTO);
        campo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        campo.setPreferredSize(new Dimension(0, 36));
        campo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COR_CINZA, 1, true),
            new EmptyBorder(4, 10, 4, 10)
        ));
        campo.putClientProperty("JTextField.placeholderText", placeholder);
        // Foco: borda acende em vermelho
        campo.addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) {
                campo.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(COR_VERMELHO, 1, true),
                    new EmptyBorder(4, 10, 4, 10)
                ));
            }
            @Override public void focusLost(FocusEvent e) {
                campo.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(COR_CINZA, 1, true),
                    new EmptyBorder(4, 10, 4, 10)
                ));
            }
        });
        return campo;
    }

    private JButton criarBotao(String texto, Color corFundo, Color corHover) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? corHover : corFundo);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font("SansSerif", Font.BOLD, 13));
        btn.setForeground(Color.WHITE);
        btn.setBackground(corFundo);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(130, 38));
        return btn;
    }

    private void marcarErro(JTextField campo) {
        campo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COR_ERRO, 2, true),
            new EmptyBorder(4, 10, 4, 10)
        ));
    }

    private void resetarBordas() {
        for (JTextField campo : new JTextField[]{campoModelo, campoCor, campoAno, campoCilindrada, campoPreco}) {
            campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COR_CINZA, 1, true),
                new EmptyBorder(4, 10, 4, 10)
            ));
        }
    }

    // ─── KeyListeners para validar tipos numéricos ───────────────────────────
    private KeyAdapter apenasNumeros() {
        return new KeyAdapter() {
            @Override public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar()) && e.getKeyChar() != KeyEvent.VK_BACK_SPACE) {
                    e.consume();
                }
            }
        };
    }

    private KeyAdapter apenasDecimal() {
        return new KeyAdapter() {
            @Override public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                JTextField src = (JTextField) e.getSource();
                if (!Character.isDigit(c) && c != '.' && c != ',' && c != KeyEvent.VK_BACK_SPACE) {
                    e.consume();
                }
                // Permite apenas um separador decimal
                if ((c == '.' || c == ',') && (src.getText().contains(".") || src.getText().contains(","))) {
                    e.consume();
                }
            }
        };
    }
}
