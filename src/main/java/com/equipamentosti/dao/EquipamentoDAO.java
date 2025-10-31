package com.equipamentosti.dao;

import com.equipamentosti.model.Equipamento;
import com.equipamentosti.util.DatabaseUtil;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EquipamentoDAO {

    public EquipamentoDAO() {
        DatabaseUtil.inicializarBanco();
    }

    public boolean inserir(Equipamento equipamento) {
        String sql = "INSERT INTO equipamentos(numero_serie, marca, modelo, data_compra) VALUES(?, ?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, equipamento.getNumeroSerie());
            pstmt.setString(2, equipamento.getMarca());
            pstmt.setString(3, equipamento.getModelo());
            pstmt.setString(4, equipamento.getDataCompra().toString());
            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Erro ao inserir equipamento: " + e.getMessage());
            return false;
        }
    }

    public List<Equipamento> listarTodos() {
        String sql = "SELECT * FROM equipamentos ORDER BY marca, modelo";
        List<Equipamento> equipamentos = new ArrayList<>();

        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                equipamentos.add(new Equipamento(
                        rs.getInt("id"),
                        rs.getString("numero_serie"),
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        LocalDate.parse(rs.getString("data_compra"))
                ));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar equipamentos: " + e.getMessage());
        }

        return equipamentos;
    }

    public Equipamento buscarPorId(int id) {
        String sql = "SELECT * FROM equipamentos WHERE id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Equipamento(
                        rs.getInt("id"),
                        rs.getString("numero_serie"),
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        LocalDate.parse(rs.getString("data_compra"))
                );
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar equipamento: " + e.getMessage());
        }

        return null;
    }

    public boolean atualizar(Equipamento equipamento) {
        String sql = "UPDATE equipamentos SET numero_serie = ?, marca = ?, modelo = ?, data_compra = ? WHERE id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, equipamento.getNumeroSerie());
            pstmt.setString(2, equipamento.getMarca());
            pstmt.setString(3, equipamento.getModelo());
            pstmt.setString(4, equipamento.getDataCompra().toString());
            pstmt.setInt(5, equipamento.getId());
            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar equipamento: " + e.getMessage());
            return false;
        }
    }

    public boolean deletar(int id) {
        String sql = "DELETE FROM equipamentos WHERE id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Erro ao deletar equipamento: " + e.getMessage());
            return false;
        }
    }

    public boolean verificarNumeroSerieExiste(String numeroSerie) {
        String sql = "SELECT COUNT(*) FROM equipamentos WHERE numero_serie = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, numeroSerie);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            System.err.println("Erro ao verificar número de série: " + e.getMessage());
        }

        return false;
    }

    public boolean verificarNumeroSerieExisteOutroEquipamento(String numeroSerie, int idAtual) {
        String sql = "SELECT COUNT(*) FROM equipamentos WHERE numero_serie = ? AND id != ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, numeroSerie);
            pstmt.setInt(2, idAtual);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            System.err.println("Erro ao verificar número de série: " + e.getMessage());
        }

        return false;
    }
}
