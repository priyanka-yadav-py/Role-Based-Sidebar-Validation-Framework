package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtils {

    public static Object[][] getTestData(String filePath, String sheetName) {

        List<Object[]> data = new ArrayList<>();

        try {
            FileInputStream fis = new FileInputStream(filePath);
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheet(sheetName);

            int rowCount = sheet.getPhysicalNumberOfRows();

            // Skip header (row 0)
            for (int i = 1; i < rowCount; i++) {

                Row row = sheet.getRow(i);

                String username = getCellValue(row.getCell(0));
                String password = getCellValue(row.getCell(1));
                String menus = getCellValue(row.getCell(2));

                data.add(new Object[]{username, password, menus});
            }

            workbook.close();
            fis.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return data.toArray(new Object[0][0]);
    }

    // 🔹 Helper method to safely read any cell type
    private static String getCellValue(Cell cell) {

        if (cell == null) return "";

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();

            case NUMERIC:
                return String.valueOf((long) cell.getNumericCellValue());

            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());

            case FORMULA:
                return cell.getCellFormula();

            default:
                return "";
        }
    }
}