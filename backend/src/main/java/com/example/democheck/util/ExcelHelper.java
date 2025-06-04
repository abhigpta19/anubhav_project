package com.example.democheck.util;

import com.example.democheck.model.User;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.util.*;

public class ExcelHelper {
    private static final String FILE_PATH = "src/main/resources/users.xlsx";

    public static void initExcelIfNeeded() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("Users");
                Row header = sheet.createRow(0);
                String[] headers = {"First Name", "Last Name", "Email", "Address1", "Address2", "City", "State", "Country"};
                for (int i = 0; i < headers.length; i++) {
                    header.createCell(i).setCellValue(headers[i]);
                }
                try (FileOutputStream fos = new FileOutputStream(FILE_PATH)) {
                    workbook.write(fos);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void writeUser(User user) {
        try (FileInputStream fis = new FileInputStream(FILE_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            int lastRow = sheet.getLastRowNum() + 1;
            Row row = sheet.createRow(lastRow);
            fillRowWithUser(row, user);

            try (FileOutputStream fos = new FileOutputStream(FILE_PATH)) {
                workbook.write(fos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<User> readUsers() {
        List<User> users = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(FILE_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;
                users.add(readUserFromRow(row));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public static void updateUser(String email, User updatedUser) {
        try (FileInputStream fis = new FileInputStream(FILE_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;
                String existingEmail = row.getCell(2).getStringCellValue();
                if (existingEmail.equals(email)) {
                    fillRowWithUser(row, updatedUser);
                    break;
                }
            }
            try (FileOutputStream fos = new FileOutputStream(FILE_PATH)) {
                workbook.write(fos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteUser(String email) {
        try (FileInputStream fis = new FileInputStream(FILE_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            int rowIndex = -1;
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;
                if (row.getCell(2).getStringCellValue().equals(email)) {
                    rowIndex = row.getRowNum();
                    break;
                }
            }
            if (rowIndex != -1) {
                int lastRowNum = sheet.getLastRowNum();
                if (rowIndex >= 0 && rowIndex < lastRowNum) {
                    sheet.shiftRows(rowIndex + 1, lastRowNum, -1);
                } else {
                    Row removingRow = sheet.getRow(rowIndex);
                    if (removingRow != null) sheet.removeRow(removingRow);
                }
                try (FileOutputStream fos = new FileOutputStream(FILE_PATH)) {
                    workbook.write(fos);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static User readUserFromRow(Row row) {
        return new User(
                row.getCell(0).getStringCellValue(),
                row.getCell(1).getStringCellValue(),
                row.getCell(2).getStringCellValue(),
                row.getCell(3).getStringCellValue(),
                row.getCell(4).getStringCellValue(),
                row.getCell(5).getStringCellValue(),
                row.getCell(6).getStringCellValue(),
                row.getCell(7).getStringCellValue()
        );
    }

    private static void fillRowWithUser(Row row, User user) {
        row.createCell(0).setCellValue(user.getFirstName());
        row.createCell(1).setCellValue(user.getLastName());
        row.createCell(2).setCellValue(user.getEmail());
        row.createCell(3).setCellValue(user.getAddressLine1());
        row.createCell(4).setCellValue(user.getAddressLine2());
        row.createCell(5).setCellValue(user.getCity());
        row.createCell(6).setCellValue(user.getState());
        row.createCell(7).setCellValue(user.getCountry());
    }
}