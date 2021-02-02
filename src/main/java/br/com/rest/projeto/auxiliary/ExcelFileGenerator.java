package br.com.rest.projeto.auxiliary;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import br.com.rest.projeto.entity.NCServico;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;

public class ExcelFileGenerator {

    private static Logger logger = LoggerFactory.getLogger(ExcelFileGenerator.class);
    private static final String extension = ".xls";
    private String fileName;
    private String sheetName;
    private Cell cell;
    private Row row;
    private Integer rowNum = 0;

    public ExcelFileGenerator(String fileName, String sheetName) {
        this.fileName = fileName;
        this.sheetName = sheetName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public void generateExcelFileForNCServico(List<NCServico> ncServicoList, HttpServletResponse response) throws ParseException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(this.sheetName);

        setLayoutDefault(sheet);
        CellStyle headerStyle = setHeaderStyle(workbook);
        CellStyle textStyle = setTextStyle(workbook);
        CellStyle dataStyle = setDataStyle(workbook);
        setHeaderOfNCServico(sheet, headerStyle);

        for (NCServico ncServico : ncServicoList){
            String servicoQualidade = (ncServico.getDocQualidade()) ? "SIM" : "NAO";
            String dtConclusao = (ncServico.getDataConclusao() != null) ? FunctionsAuxiliary.formatDataUSAToBR(ncServico.getDataConclusao().toString()) : "";
            String eficacia = (ncServico.getEficacia() != null) ?  ncServico.getEficacia().name() : "";

            int cellNum = 0;
            row = sheet.createRow(rowNum++);

            cell = row.createCell(cellNum++);
            cell.setCellStyle(textStyle);
            cell.setCellValue(ncServico.getId());

            cell = row.createCell(cellNum++);
            cell.setCellStyle(textStyle);
            cell.setCellValue(FunctionsAuxiliary.defineServiceOrNC(ncServico.getTipoCadastro().name(), true));

            cell = row.createCell(cellNum++);
            cell.setCellStyle(textStyle);
            cell.setCellValue(FunctionsAuxiliary.formatStatus(FunctionsAuxiliary.setStatusCorretoPelaData(ncServico)));

            cell = row.createCell(cellNum++);
            cell.setCellStyle(textStyle);
            if (ncServico.getTorre() != null){
                cell.setCellValue(ncServico.getTorre());
            }

            cell = row.createCell(cellNum++);
            cell.setCellStyle(textStyle);
            if (ncServico.getPavimentoNumerico() != null) {
                cell.setCellValue(ncServico.getPavimentoNumerico());
            } else if (ncServico.getPavimento() != null){
                cell.setCellValue(ncServico.getPavimento().getDescricao());
            }

            cell = row.createCell(cellNum++);
            cell.setCellStyle(textStyle);
            if (ncServico.getUnidadeNumerico() != null){
                cell.setCellValue(ncServico.getUnidadeNumerico());
            } else if (ncServico.getUnidade() != null) {
                cell.setCellValue(ncServico.getUnidade().getDescricao());
            }

            cell = row.createCell(cellNum++);
            cell.setCellStyle(textStyle);
            cell.setCellValue(ncServico.getTipoServico().getDescricao());

            cell = row.createCell(cellNum++);
            cell.setCellStyle(textStyle);
            cell.setCellValue(ncServico.getResponsavel().getNome());

            cell = row.createCell(cellNum++);
            cell.setCellStyle(dataStyle);
            cell.setCellValue(FunctionsAuxiliary.formatDataUSAToBR(ncServico.getDataInicio().toString()));

            cell = row.createCell(cellNum++);
            cell.setCellStyle(textStyle);
            cell.setCellValue(ncServico.getPrazo());

            cell = row.createCell(cellNum++);
            cell.setCellStyle(textStyle);
            cell.setCellValue(ncServico.getTipoPrazo().name());

            cell = row.createCell(cellNum++);
            cell.setCellStyle(dataStyle);
            cell.setCellValue(FunctionsAuxiliary.formatDataUSAToBR(ncServico.getDataPrevisaoTermino().toString()));

            cell = row.createCell(cellNum++);
            cell.setCellStyle(textStyle);
            cell.setCellValue(ncServico.getDescricao());

            cell = row.createCell(cellNum++);
            cell.setCellStyle(textStyle);
            cell.setCellValue(ncServico.getPrescricao());

            cell = row.createCell(cellNum++);
            cell.setCellStyle(textStyle);
            cell.setCellValue(servicoQualidade);

            cell = row.createCell(cellNum++);
            cell.setCellStyle(dataStyle);
            cell.setCellValue(dtConclusao);

            cell = row.createCell(cellNum++);
            cell.setCellStyle(dataStyle);
            cell.setCellValue(ncServico.getDocumentoPbqp());

            cell = row.createCell(cellNum++);
            cell.setCellStyle(textStyle);
            if (ncServico.getRevisao() != null){
                cell.setCellValue(ncServico.getRevisao());
            }

            cell = row.createCell(cellNum++);
            cell.setCellStyle(textStyle);
            cell.setCellValue(eficacia);

            cell = row.createCell(cellNum++);
            cell.setCellStyle(textStyle);
            cell.setCellValue(ncServico.getJustificativaEficacia());

            cell = row.createCell(cellNum++);
            cell.setCellStyle(textStyle);
            cell.setCellValue(ncServico.getUsuario().getNome());

            cell = row.createCell(cellNum++);
            cell.setCellStyle(textStyle);
            cell.setCellValue(ncServico.getProjeto().getDescricao());
        }

        generateFile(workbook, response);
    }

    private void generateFile(HSSFWorkbook workbook, HttpServletResponse response) {
        try {
            File file = new File(fileName + extension);
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());

            OutputStream out = response.getOutputStream();
            workbook.write(out);
            out.close();
        } catch (FileNotFoundException e) {
            logger.error("Erro ao encontrar o arquivo! >> " + e);
        } catch (IOException e) {
            logger.error("Erro na edição do arquivo! >> " + e);
        }
    }

    private void setLayoutDefault(HSSFSheet sheet){
        sheet.setDefaultColumnWidth(15);
        sheet.setDefaultRowHeight((short)400);
    }

    private CellStyle setHeaderStyle(HSSFWorkbook workbook){
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        headerStyle.setAlignment(CellStyle.ALIGN_CENTER);
        headerStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        return headerStyle;
    }

    private CellStyle setTextStyle(HSSFWorkbook workbook){
        CellStyle textStyle = workbook.createCellStyle();
        textStyle.setAlignment(CellStyle.ALIGN_CENTER);
        textStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        return textStyle;
    }

    private CellStyle setDataStyle(HSSFWorkbook workbook){
        HSSFDataFormat numberFormat = workbook.createDataFormat();
        CellStyle dataStyle = workbook.createCellStyle();
        dataStyle.setDataFormat(numberFormat.getFormat("dd/mm/yyyy"));
        dataStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        return dataStyle;
    }

    private void setHeaderOfNCServico(HSSFSheet sheet, CellStyle headerStyle) {
        List<String> cabecalhos = new ArrayList<>();
        cabecalhos.add("ID");
        cabecalhos.add("Tipo Cadastro");
        cabecalhos.add("Status");
        cabecalhos.add("Torre");
        cabecalhos.add("Pavimento");
        cabecalhos.add("Unidade");
        cabecalhos.add("Servico");
        cabecalhos.add("Responsável");
        cabecalhos.add("Data Inicio");
        cabecalhos.add("Prazo");
        cabecalhos.add("Tipo Prazo");
        cabecalhos.add("Data Previsão Término");
        cabecalhos.add("Descrição");
        cabecalhos.add("Prescrição");
        cabecalhos.add("Serviço Controlado");
        cabecalhos.add("Data Conclusão");
        cabecalhos.add("Documento PBQP");
        cabecalhos.add("Revisão");
        cabecalhos.add("Eficacia");
        cabecalhos.add("Justificativa");
        cabecalhos.add("Usuário Cadastro");
        cabecalhos.add("Obra");

        int cellNum = 0;
        row = sheet.createRow(rowNum++);

        for (String cabec : cabecalhos){
            cell = row.createCell(cellNum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue(cabec);
        }
    }
}
