package ru.aegorova.rabbitmqspringboot.services;

import com.itextpdf.html2pdf.HtmlConverter;
import org.springframework.stereotype.Component;
import ru.aegorova.rabbitmqspringboot.models.Key;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

// create pdf file from html
// replace all key-words with user's data
// then convert html in pdf
// and save it in declared folder
@Component
public class PDFCreatorServiceItext implements PDFCreatorService{
    public void formPdf(String templatePath, String fileName, List<Key> keys){
        String newStr = "";
        try {
            // read template from html file
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(templatePath));
            int i;
            StringBuilder sb = new StringBuilder();
            while ((i = bis.read()) != -1) {
                sb.append((char) i);
            }
            newStr = sb.toString();
            // replacing keys in the template with our keys
            for (Key key : keys) {
                newStr = newStr.replace(key.getKey(), key.getValue());
            }
            // write string to .pdf file
            FileOutputStream fos = new FileOutputStream(fileName);
            HtmlConverter.convertToPdf(newStr, fos);
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }
}
