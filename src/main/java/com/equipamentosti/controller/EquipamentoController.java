package com.equipamentosti.controller;

import com.equipamentosti.model.Equipamento;
import com.equipamentosti.dao.EquipamentoDAO;
import com.equipamentosti.util.ExcelUtil;
import java.time.LocalDate;
import java.util.List;

public class EquipamentoController {
    private final EquipamentoDAO dao;

    public EquipamentoController() {
        this.dao = new EquipamentoDAO();
    }

    public String cadastrarEquipamento(String numeroSerie, String marca, String modelo, LocalDate dataCompra) {
        // Validações
        if (numeroSerie == null || numeroSerie.trim().isEmpty()) {
            return "Número de série é obrigatório!";
        }

        if (marca == null || marca.trim().isEmpty()) {
            return "Marca é obrigatória!";
        }

        if (modelo == null || modelo.trim().isEmpty()) {
            return "Modelo é obrigatório!";
        }

        if (dataCompra == null) {
            return "Data de compra é obrigatória!";
        }

        if (dataCompra.isAfter(LocalDate.now())) {
            return "Data de compra não pode ser futura!";
        }

        // Verificar se número de série já existe
        if (dao.verificarNumeroSerieExiste(numeroSerie)) {
            return "Número de série já cadastrado!";
        }

        Equipamento equipamento = new Equipamento(numeroSerie, marca, modelo, dataCompra);

        if (dao.inserir(equipamento)) {
            return "OK";
        } else {
            return "Erro ao cadastrar equipamento!";
        }
    }

    public String atualizarEquipamento(int id, String numeroSerie, String marca, String modelo, LocalDate dataCompra) {
        // Validações
        if (numeroSerie == null || numeroSerie.trim().isEmpty()) {
            return "Número de série é obrigatório!";
        }

        if (marca == null || marca.trim().isEmpty()) {
            return "Marca é obrigatória!";
        }

        if (modelo == null || modelo.trim().isEmpty()) {
            return "Modelo é obrigatório!";
        }

        if (dataCompra == null) {
            return "Data de compra é obrigatória!";
        }

        if (dataCompra.isAfter(LocalDate.now())) {
            return "Data de compra não pode ser futura!";
        }

        // Verificar se número de série já existe em outro equipamento
        if (dao.verificarNumeroSerieExisteOutroEquipamento(numeroSerie, id)) {
            return "Número de série já cadastrado para outro equipamento!";
        }

        Equipamento equipamento = new Equipamento(id, numeroSerie, marca, modelo, dataCompra);

        if (dao.atualizar(equipamento)) {
            return "OK";
        } else {
            return "Erro ao atualizar equipamento!";
        }
    }

    public boolean deletarEquipamento(int id) {
        return dao.deletar(id);
    }

    public List<Equipamento> listarTodosEquipamentos() {
        return dao.listarTodos();
    }

    public Equipamento buscarEquipamentoPorId(int id) {
        return dao.buscarPorId(id);
    }

    public boolean exportarParaExcel(String caminhoArquivo) {
        List<Equipamento> equipamentos = dao.listarTodos();
        return ExcelUtil.exportarEquipamentos(equipamentos, caminhoArquivo);
    }
}