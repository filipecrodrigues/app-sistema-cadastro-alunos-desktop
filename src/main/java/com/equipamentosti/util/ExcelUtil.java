package com.equipamentosti.util;

import com.equipamentosti.model.Equipamento;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ExcelUtil {
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static boolean exportarEquipamentos(List<Equipamento> equipamentos, String caminhoArquivo) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Equipamentos de TI");

            // Criar estilo para o cabeçalho
            CellStyle headerStyle = criarEstiloHeader(workbook);

            // Criar cabeçalho
            Row headerRow = sheet.createRow(0);
            String[] colunas = {"ID", "Número de Série", "Marca", "Modelo", "Data da Compra"};

            for (int i = 0; i < colunas.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(colunas[i]);
                cell.setCellStyle(headerStyle);
            }

            // Preencher dados
            int rowNum = 1;
            for (Equipamento equipamento : equipamentos) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(equipamento.getId());
                row.createCell(1).setCellValue(equipamento.getNumeroSerie());
                row.createCell(2).setCellValue(equipamento.getMarca());
                row.createCell(3).setCellValue(equipamento.getModelo());
                row.createCell(4).setCellValue(equipamento.getDataCompra().format(dateFormatter));
            }

            // Ajustar largura das colunas
            for (int i = 0; i < colunas.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Escrever no arquivo
            try (FileOutputStream fileOut = new FileOutputStream(caminhoArquivo)) {
                workbook.write(fileOut);
            }

            return true;

        } catch (IOException e) {
            System.err.println("Erro ao exportar Excel: " + e.getMessage());
            return false;
        }
    }

    private static CellStyle criarEstiloHeader(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 12);
        font.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }
}