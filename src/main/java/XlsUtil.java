import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.*;

public class XlsUtil {

    public static void createExcel(String name,String townName,String cityName, double distance) {
        try {
            FileOutputStream fos = new FileOutputStream(name);
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet(name);
            HSSFRow row0 = sheet.createRow(0);
            row0.createCell(0).setCellValue(townName);
            row0.createCell(1).setCellValue(cityName);
            row0.createCell(2).setCellValue(distance);
            workbook.write(fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void appendToExcel(String name,String townName,String cityName, double distance) {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(name);
            HSSFWorkbook workbook = new HSSFWorkbook(fis);
            HSSFSheet sheet = workbook.getSheet(name);
            if (sheet != null) {
                int rowNum = sheet.getLastRowNum();
                HSSFRow newRow = sheet.createRow(rowNum+1);
                newRow.createCell(0).setCellValue(townName);
                newRow.createCell(1).setCellValue(cityName);
                newRow.createCell(2).setCellValue(distance);
            }
            fos = new FileOutputStream(name);
            workbook.write(fos);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                //ignore
            }

        }
    }
}

