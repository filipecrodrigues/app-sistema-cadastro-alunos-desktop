package com.equipamentosti.view;

import com.equipamentosti.controller.EquipamentoController;
import com.equipamentosti.model.Equipamento;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class EquipamentoFrame extends JFrame {
    private JTextField txtNumeroSerie, txtMarca, txtModelo, txtDataCompra;
    private JTable tabelaEquipamentos;
    private DefaultTableModel modeloTabela;
    private EquipamentoController controller;
    private int idSelecionado = -1;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public EquipamentoFrame() {
        controller = new EquipamentoController();
        inicializarInterface();
        carregarDados();
    }

    private void inicializarInterface() {
        setTitle("Sistema de Cadastro de Equipamentos de TI");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Painel superior com formulário e botões
        JPanel painelSuperior = criarPainelSuperior();

        // Tabela
        JScrollPane scrollPane = criarTabela();

        // Adicionar componentes
        add(painelSuperior, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel criarPainelSuperior() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Formulário
        JPanel painelForm = new JPanel(new GridBagLayout());
        painelForm.setBorder(BorderFactory.createTitledBorder("Dados do Equipamento"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Número de Série
        gbc.gridx = 0; gbc.gridy = 0;
        painelForm.add(new JLabel("Número de Série:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        txtNumeroSerie = new JTextField(20);
        painelForm.add(txtNumeroSerie, gbc);

        // Marca
        gbc.gridx = 2; gbc.weightx = 0;
        painelForm.add(new JLabel("Marca:"), gbc);
        gbc.gridx = 3; gbc.weightx = 1.0;
        txtMarca = new JTextField(20);
        painelForm.add(txtMarca, gbc);

        // Modelo
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        painelForm.add(new JLabel("Modelo:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        txtModelo = new JTextField(20);
        painelForm.add(txtModelo, gbc);

        // Data de Compra
        gbc.gridx = 2; gbc.weightx = 0;
        painelForm.add(new JLabel("Data da Compra:"), gbc);
        gbc.gridx = 3; gbc.weightx = 1.0;
        JPanel painelData = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        txtDataCompra = new JTextField(10);
        painelData.add(txtDataCompra);
        painelData.add(new JLabel("(dd/mm/aaaa)"));
        painelForm.add(painelData, gbc);

        // Painel de botões
        JPanel painelBotoes = criarPainelBotoes();

        painel.add(painelForm, BorderLayout.CENTER);
        painel.add(painelBotoes, BorderLayout.SOUTH);

        return painel;
    }

    private JPanel criarPainelBotoes() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> salvarEquipamento());
        painel.add(btnSalvar);

        JButton btnAtualizar = new JButton("Atualizar");
        btnAtualizar.addActionListener(e -> atualizarEquipamento());
        painel.add(btnAtualizar);

        JButton btnDeletar = new JButton("Deletar");
        btnDeletar.addActionListener(e -> deletarEquipamento());
        painel.add(btnDeletar);

        JButton btnLimpar = new JButton("Limpar");
        btnLimpar.addActionListener(e -> limparCampos());
        painel.add(btnLimpar);

        JButton btnExportar = new JButton("Exportar Excel");
        btnExportar.addActionListener(e -> exportarExcel());
        painel.add(btnExportar);

        return painel;
    }

    private JScrollPane criarTabela() {
        String[] colunas = {"ID", "Número de Série", "Marca", "Modelo", "Data da Compra"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaEquipamentos = new JTable(modeloTabela);
        tabelaEquipamentos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaEquipamentos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selecionarEquipamento();
            }
        });

        // Ajustar largura das colunas
        tabelaEquipamentos.getColumnModel().getColumn(0).setPreferredWidth(50);
        tabelaEquipamentos.getColumnModel().getColumn(1).setPreferredWidth(200);
        tabelaEquipamentos.getColumnModel().getColumn(2).setPreferredWidth(150);
        tabelaEquipamentos.getColumnModel().getColumn(3).setPreferredWidth(200);
        tabelaEquipamentos.getColumnModel().getColumn(4).setPreferredWidth(120);

        return new JScrollPane(tabelaEquipamentos);
    }

    private void carregarDados() {
        modeloTabela.setRowCount(0);
        for (Equipamento equipamento : controller.listarTodosEquipamentos()) {
            modeloTabela.addRow(new Object[]{
                    equipamento.getId(),
                    equipamento.getNumeroSerie(),
                    equipamento.getMarca(),
                    equipamento.getModelo(),
                    equipamento.getDataCompra().format(dateFormatter)
            });
        }
    }

    private void salvarEquipamento() {
        try {
            LocalDate dataCompra = LocalDate.parse(txtDataCompra.getText(), dateFormatter);

            String resultado = controller.cadastrarEquipamento(
                    txtNumeroSerie.getText().trim(),
                    txtMarca.getText().trim(),
                    txtModelo.getText().trim(),
                    dataCompra
            );

            if (resultado.equals("OK")) {
                JOptionPane.showMessageDialog(this, "Equipamento cadastrado com sucesso!");
                limparCampos();
                carregarDados();
            } else {
                JOptionPane.showMessageDialog(this, resultado, "Aviso", JOptionPane.WARNING_MESSAGE);
            }

        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this,
                    "Data inválida! Use o formato dd/mm/aaaa",
                    "Erro de Validação",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarEquipamento() {
        if (idSelecionado == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um equipamento para atualizar!");
            return;
        }

        try {
            LocalDate dataCompra = LocalDate.parse(txtDataCompra.getText(), dateFormatter);

            String resultado = controller.atualizarEquipamento(
                    idSelecionado,
                    txtNumeroSerie.getText().trim(),
                    txtMarca.getText().trim(),
                    txtModelo.getText().trim(),
                    dataCompra
            );

            if (resultado.equals("OK")) {
                JOptionPane.showMessageDialog(this, "Equipamento atualizado com sucesso!");
                limparCampos();
                carregarDados();
            } else {
                JOptionPane.showMessageDialog(this, resultado, "Aviso", JOptionPane.WARNING_MESSAGE);
            }

        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this,
                    "Data inválida! Use o formato dd/mm/aaaa",
                    "Erro de Validação",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deletarEquipamento() {
        if (idSelecionado == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um equipamento para deletar!");
            return;
        }

        int confirmacao = JOptionPane.showConfirmDialog(this,
                "Deseja realmente deletar este equipamento?",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION);

        if (confirmacao == JOptionPane.YES_OPTION) {
            if (controller.deletarEquipamento(idSelecionado)) {
                JOptionPane.showMessageDialog(this, "Equipamento deletado com sucesso!");
                limparCampos();
                carregarDados();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Erro ao deletar equipamento!",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void selecionarEquipamento() {
        int linha = tabelaEquipamentos.getSelectedRow();
        if (linha != -1) {
            idSelecionado = (int) modeloTabela.getValueAt(linha, 0);
            txtNumeroSerie.setText((String) modeloTabela.getValueAt(linha, 1));
            txtMarca.setText((String) modeloTabela.getValueAt(linha, 2));
            txtModelo.setText((String) modeloTabela.getValueAt(linha, 3));
            txtDataCompra.setText((String) modeloTabela.getValueAt(linha, 4));
        }
    }

    private void limparCampos() {
        txtNumeroSerie.setText("");
        txtMarca.setText("");
        txtModelo.setText("");
        txtDataCompra.setText("");
        idSelecionado = -1;
        tabelaEquipamentos.clearSelection();
    }

    private void exportarExcel() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Salvar arquivo Excel");
        fileChooser.setSelectedFile(new java.io.File("equipamentos_ti.xlsx"));

        int opcao = fileChooser.showSaveDialog(this);
        if (opcao == JFileChooser.APPROVE_OPTION) {
            String caminho = fileChooser.getSelectedFile().getAbsolutePath();
            if (!caminho.endsWith(".xlsx")) {
                caminho += ".xlsx";
            }

            if (controller.exportarParaExcel(caminho)) {
                JOptionPane.showMessageDialog(this,
                        "Excel exportado com sucesso em:\n" + caminho,
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Erro ao exportar Excel!",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}