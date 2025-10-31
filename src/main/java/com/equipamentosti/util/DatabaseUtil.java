package com.equipamentosti.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtil {
    private static final String URL = "jdbc:sqlite:equipamentos_ti.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void inicializarBanco() {
        String sql = "CREATE TABLE IF NOT EXISTS equipamentos (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "numero_serie TEXT NOT NULL UNIQUE," +
                "marca TEXT NOT NULL," +
                "modelo TEXT NOT NULL," +
                "data_compra TEXT NOT NULL)";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Banco de dados inicializado com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao criar tabela: " + e.getMessage());
        }
    }
}