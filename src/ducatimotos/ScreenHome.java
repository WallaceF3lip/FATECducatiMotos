/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ducatimotos;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

/**
 * Tela principal - Lista de motos cadastradas
 *
 * @author walla
 */
public class ScreenHome extends JFrame {

    // ─── Componentes da tela ────────────────────────────────────────────────
    private JTable tabelaMotos;
    private DefaultTableModel modeloTabela;
    private JButton btnNova;
    private JButton btnEditar;
    private JButton btnAtualizar;
    private JTextField campoBusca;

    // ─── Colunas da tabela ──────────────────────────────────────────────────
    private static final String[] COLUNAS = {
        "ID", "Modelo", "Cor", "Ano", "Cilindrada (cc)", "Preço (R$)"
    };

    // ─── Paleta de cores (tema Ducati) ──────────────────────────────────────
    private static final Color COR_FUNDO        = new Color(18, 18, 18);
    private static final Color COR_PAINEL       = new Color(28, 28, 28);
    private static final Color COR_HEADER       = new Color(35, 35, 35);
    private static final Color COR_VERMELHO     = new Color(204, 0, 0);
    private static final Color COR_VERMELHO_HOV = new Color(230, 30, 30);
    private static final Color COR_CINZA        = new Color(60, 60, 60);
    private static final Color COR_CINZA_HOV    = new Color(80, 80, 80);
    private static final Color COR_TEXTO        = new Color(240, 240, 240);
    private static final Color COR_TEXTO_SUAVE  = new Color(160, 160, 160);
    private static final Color COR_LINHA_PAR    = new Color(32, 32, 32);
    private static final Color COR_LINHA_IMPAR  = new Color(40, 40, 40);
    private static final Color COR_SELECAO      = new Color(120, 0, 0);

    // ────────────────────────────────────────────────────────────────────────
    public ScreenHome() {
        inicializarTela();
        construirInterface();
        carregarMotos();
        setVisible(true);
    }

    // ─── Configurações gerais do JFrame ─────────────────────────────────────
    private void inicializarTela() {
        setTitle("Ducati Motos — Catálogo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setMinimumSize(new Dimension(750, 480));
        setLocationRelativeTo(null);
        setBackground(COR_FUNDO);
        getContentPane().setBackground(COR_FUNDO);
        setLayout(new BorderLayout());
    }

    // ─── Montagem da interface ───────────────────────────────────────────────
    private void construirInterface() {
        add(criarPainelTopo(), BorderLayout.NORTH);
        add(criarPainelTabela(), BorderLayout.CENTER);
        add(criarPainelRodape(), BorderLayout.SOUTH);
    }

    // ── Painel superior: logo + título + campo de busca ─────────────────────
    private JPanel criarPainelTopo() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(COR_HEADER);
        painel.setBorder(new EmptyBorder(16, 24, 16, 24));

        // Bloco esquerdo: título
        JPanel blocoTitulo = new JPanel(new GridBagLayout());
        blocoTitulo.setOpaque(false);

        JLabel lblMarca = new JLabel("DUCATI");
        lblMarca.setFont(new Font("SansSerif", Font.BOLD, 26));
        lblMarca.setForeground(COR_VERMELHO);

        JLabel lblSub = new JLabel("  Catálogo de Motos");
        lblSub.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblSub.setForeground(COR_TEXTO_SUAVE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        blocoTitulo.add(lblMarca, gbc);
        gbc.gridx = 1;
        blocoTitulo.add(lblSub, gbc);

        // Bloco direito: busca
        JPanel blocoBusca = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        blocoBusca.setOpaque(false);

        JLabel lblBusca = new JLabel("🔍");
        lblBusca.setForeground(COR_TEXTO_SUAVE);

        campoBusca = new JTextField(18);
        campoBusca.setBackground(COR_CINZA);
        campoBusca.setForeground(COR_TEXTO);
        campoBusca.setCaretColor(COR_TEXTO);
        campoBusca.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COR_VERMELHO, 1, true),
            new EmptyBorder(4, 8, 4, 8)
        ));
        campoBusca.setFont(new Font("SansSerif", Font.PLAIN, 13));
        campoBusca.putClientProperty("JTextField.placeholderText", "Buscar modelo...");
        campoBusca.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filtrarTabela(campoBusca.getText());
            }
        });

        blocoBusca.add(lblBusca);
        blocoBusca.add(campoBusca);

        painel.add(blocoTitulo, BorderLayout.WEST);
        painel.add(blocoBusca, BorderLayout.EAST);

        // Linha separadora vermelha
        JSeparator sep = new JSeparator();
        sep.setForeground(COR_VERMELHO);
        sep.setBackground(COR_VERMELHO);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(COR_HEADER);
        wrapper.add(painel, BorderLayout.CENTER);
        wrapper.add(sep, BorderLayout.SOUTH);

        return wrapper;
    }

    // ── Painel central: tabela de motos ─────────────────────────────────────
    private JPanel criarPainelTabela() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(COR_FUNDO);
        painel.setBorder(new EmptyBorder(16, 24, 8, 24));

        // Título da seção
        JLabel lblSecao = new JLabel("Motos Cadastradas");
        lblSecao.setFont(new Font("SansSerif", Font.BOLD, 15));
        lblSecao.setForeground(COR_TEXTO);
        lblSecao.setBorder(new EmptyBorder(0, 0, 10, 0));

        // Modelo da tabela (não editável)
        modeloTabela = new DefaultTableModel(COLUNAS, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaMotos = new JTable(modeloTabela);
        estilizarTabela();

        JScrollPane scroll = new JScrollPane(tabelaMotos);
        scroll.setBackground(COR_PAINEL);
        scroll.getViewport().setBackground(COR_PAINEL);
        scroll.setBorder(BorderFactory.createLineBorder(COR_CINZA, 1));
        scroll.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override protected void configureScrollBarColors() {
                thumbColor = COR_VERMELHO;
                trackColor = COR_PAINEL;
            }
        });

        painel.add(lblSecao, BorderLayout.NORTH);
        painel.add(scroll, BorderLayout.CENTER);

        return painel;
    }

    // ── Estilização da JTable ────────────────────────────────────────────────
    private void estilizarTabela() {
        // Cabeçalho
        JTableHeader header = tabelaMotos.getTableHeader();
        header.setBackground(COR_VERMELHO);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("SansSerif", Font.BOLD, 13));
        header.setPreferredSize(new Dimension(header.getWidth(), 36));
        header.setReorderingAllowed(false);

        // Corpo
        tabelaMotos.setBackground(COR_PAINEL);
        tabelaMotos.setForeground(COR_TEXTO);
        tabelaMotos.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tabelaMotos.setRowHeight(32);
        tabelaMotos.setSelectionBackground(COR_SELECAO);
        tabelaMotos.setSelectionForeground(Color.WHITE);
        tabelaMotos.setGridColor(new Color(50, 50, 50));
        tabelaMotos.setShowHorizontalLines(true);
        tabelaMotos.setShowVerticalLines(false);
        tabelaMotos.setIntercellSpacing(new Dimension(0, 1));
        tabelaMotos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaMotos.setFillsViewportHeight(true);

        // Renderer para linhas zebradas
        tabelaMotos.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {

                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(column == 0 ? CENTER : LEFT);
                setBorder(new EmptyBorder(0, 10, 0, 10));

                if (isSelected) {
                    setBackground(COR_SELECAO);
                    setForeground(Color.WHITE);
                } else {
                    setBackground(row % 2 == 0 ? COR_LINHA_PAR : COR_LINHA_IMPAR);
                    setForeground(COR_TEXTO);
                }
                return this;
            }
        });

        // Larguras das colunas
        int[] larguras = {50, 200, 120, 70, 120, 130};
        for (int i = 0; i < larguras.length; i++) {
            tabelaMotos.getColumnModel().getColumn(i).setPreferredWidth(larguras[i]);
        }

        // Duplo clique → editar
        tabelaMotos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    abrirEdicao();
                }
            }
        });
    }

    // ── Painel inferior: botões de ação ─────────────────────────────────────
    private JPanel criarPainelRodape() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(COR_HEADER);
        painel.setBorder(new EmptyBorder(12, 24, 12, 24));

        // Info de seleção
        JLabel lblInfo = new JLabel("Selecione uma moto e clique em 'Editar', ou dê duplo clique na linha.");
        lblInfo.setFont(new Font("SansSerif", Font.ITALIC, 12));
        lblInfo.setForeground(COR_TEXTO_SUAVE);

        // Botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        painelBotoes.setOpaque(false);

        btnAtualizar = criarBotao("↺  Atualizar", COR_CINZA, COR_CINZA_HOV);
        btnEditar    = criarBotao("✏  Editar",    COR_CINZA, COR_CINZA_HOV);
        btnNova      = criarBotao("＋  Nova Moto", COR_VERMELHO, COR_VERMELHO_HOV);

        btnAtualizar.addActionListener(e -> carregarMotos());
        btnEditar.addActionListener(e -> abrirEdicao());
        btnNova.addActionListener(e -> abrirCadastro());

        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnNova);

        painel.add(lblInfo, BorderLayout.WEST);
        painel.add(painelBotoes, BorderLayout.EAST);

        // Linha separadora no topo do rodapé
        JSeparator sep = new JSeparator();
        sep.setForeground(COR_CINZA);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(COR_HEADER);
        wrapper.add(sep, BorderLayout.NORTH);
        wrapper.add(painel, BorderLayout.CENTER);

        return wrapper;
    }

    // ─── Fábrica de botões estilizados ──────────────────────────────────────
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
        btn.setPreferredSize(new Dimension(140, 38));
        return btn;
    }

    // ─── Carrega (ou recarrega) as motos na tabela ──────────────────────────
    public void carregarMotos() {
        modeloTabela.setRowCount(0);

        DucatiMotos Dmoto = new DucatiMotos();
        ArrayList<DucatiMotos> listaMotos = Dmoto.readMotos();

        for (DucatiMotos moto : listaMotos) {
            Object[] linha = {
                moto.getID(),
                moto.getModelo(),
                moto.getCor(),
                moto.getAno(),
                moto.getCilindrada(),
                moto.getPreço()
            };
            modeloTabela.addRow(linha);
        }

        atualizarContador();
    }

    // ─── Filtro de busca ─────────────────────────────────────────────────────
    private void filtrarTabela(String texto) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modeloTabela);
        tabelaMotos.setRowSorter(sorter);

        if (texto.trim().isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto));
        }
        atualizarContador();
    }

    // ─── Atualiza o título da janela com o total de registros ───────────────
    private void atualizarContador() {
        int total = tabelaMotos.getRowCount();
        setTitle("Ducati Motos — Catálogo  (" + total + " moto" + (total != 1 ? "s" : "") + ")");
    }

    // ─── Ação: Abrir tela Home ───────────────────────────────────────────────
    public void abrirHome() {
        ScreenHome screen = new ScreenHome();
        screen.setVisible(true);
        
        this.dispose();
    }

    // ─── Ação: abrir tela de cadastro ───────────────────────────────────────
    private void abrirCadastro() {
        new ScreenCreate(this);
    }

    // ─── Ação: abrir tela de edição ─────────────────────────────────────────
    private void abrirEdicao() {
        int linhaSelecionada = tabelaMotos.getSelectedRow();

        if (linhaSelecionada < 0) {
            JOptionPane.showMessageDialog(this,
                "Selecione uma moto na tabela antes de editar.",
                "Nenhuma moto selecionada", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Converte índice caso haja filtro ativo
        int linhaModelo = tabelaMotos.convertRowIndexToModel(linhaSelecionada);
        int id = (int) modeloTabela.getValueAt(linhaModelo, 0);

        new ScreenEdit(this, id);
    }
}
