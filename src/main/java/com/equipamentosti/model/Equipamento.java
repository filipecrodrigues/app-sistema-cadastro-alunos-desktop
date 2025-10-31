package com.equipamentosti.model;
import java.time.LocalDate;

public class Equipamento {
    private int id;
    private String numeroSerie;
    private String marca;
    private String modelo;
    private LocalDate dataCompra;

    public Equipamento() {}

    public Equipamento(int id, String numeroSerie, String marca, String modelo, LocalDate dataCompra) {
        this.id = id;
        this.numeroSerie = numeroSerie;
        this.marca = marca;
        this.modelo = modelo;
        this.dataCompra = dataCompra;
    }

    public Equipamento(String numeroSerie, String marca, String modelo, LocalDate dataCompra) {
        this.numeroSerie = numeroSerie;
        this.marca = marca;
        this.modelo = modelo;
        this.dataCompra = dataCompra;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNumeroSerie() { return numeroSerie; }
    public void setNumeroSerie(String numeroSerie) { this.numeroSerie = numeroSerie; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public LocalDate getDataCompra() { return dataCompra; }
    public void setDataCompra(LocalDate dataCompra) { this.dataCompra = dataCompra; }

    @Override
    public String toString() {
        return "Equipamento{id=" + id + ", numeroSerie='" + numeroSerie +
                "', marca='" + marca + "', modelo='" + modelo +
                "', dataCompra=" + dataCompra + '}';
    }
}