package ru.aegorova.rabbitmqspringboot.services;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.stereotype.Component;
import ru.aegorova.rabbitmqspringboot.models.User;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

// write user's data in declared .xls file
@Component
public class StatisticServiceXls implements StatisticService{
    public void writeIntoFile(String path, User user){
        int NAME_COLUMN_NUMBER = 0; //Name
        int SURNAME_COLUMN_NUMBER = 1; //Surname
        int PASSPORT_COLUMN_NUMBER = 2; //Passport number
        int AGE_COLUMN_NUMBER = 3; //Age
        int PASSPORT_DATE_COLUMN_NUMBER = 4; //Passport date
        File excelFile = new File(path);
        try {
            POIFSFileSystem fileSystem = new POIFSFileSystem(excelFile); //Open .xls file
            HSSFWorkbook workBook = new HSSFWorkbook(fileSystem); // get workbook
            HSSFSheet sheet = workBook.getSheetAt(0); // check only first page
            // search free row
            HSSFRow row = (HSSFRow) sheet.createRow(sheet.getLastRowNum() + 1);
            //write user's data in the cells
            row.createCell(NAME_COLUMN_NUMBER).setCellValue(user.getName());//name
            row.createCell(SURNAME_COLUMN_NUMBER).setCellValue(user.getSurname()); //surname
            row.createCell(PASSPORT_COLUMN_NUMBER).setCellValue(user.getPassport()); //passport number
            row.createCell(AGE_COLUMN_NUMBER).setCellValue(user.getAge()); //age
            row.createCell(PASSPORT_DATE_COLUMN_NUMBER).setCellValue(user.getPassport_date()); //passport date
            FileOutputStream fileOut = new FileOutputStream(path);
            workBook.write(fileOut);
            fileOut.close();
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }
}
