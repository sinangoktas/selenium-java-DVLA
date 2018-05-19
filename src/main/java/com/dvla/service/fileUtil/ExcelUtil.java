package com.dvla.service.fileUtil;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;

public class ExcelUtil {

    private  XSSFSheet ExcelWSheet;
    private  XSSFWorkbook ExcelWBook;
    private  XSSFCell cell;


    public  void setExcelFile(String path, String sheetName) throws Exception {
            FileInputStream ExcelFile = new FileInputStream(path);
            ExcelWBook = new XSSFWorkbook(ExcelFile);
            ExcelWSheet = ExcelWBook.getSheet(sheetName);
    }

    public  int getRowCount(String sheetName) {
        ExcelWSheet = ExcelWBook.getSheet(sheetName);
        return ExcelWSheet.getPhysicalNumberOfRows();
    }

    public  String getCellData(int rowNum, int colNum) throws Exception {
            cell = ExcelWSheet.getRow(rowNum).getCell(colNum);
            String cellValue = cell.getStringCellValue();
            return cellValue;

    }

}



