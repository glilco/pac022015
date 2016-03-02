/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufg.inf.fabrica.pac.dominio.utils;

/**
 *
 * @author auf
 */
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.Part;

@SuppressWarnings("unchecked")
public class FileService {

    private String year;
    private String month;

    public FileService() {
        this.year = getYear();
        this.month = getMonth();

    }

    public Path createFolder(String baseDir) {

        Path path = Paths.get(baseDir, this.year, this.month);

        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
        } catch (IOException e) {
            Logger.getLogger(FileService.class.getName()).log(Level.SEVERE, null, e);
        }

        return path;
    }

    public String saveFile(String destination, Part part) {

        String fileName = getFileName(part);
        //String contentType = part.getContentType();        
        try {
            
            fileName = destination + File.separator + fileName.replace("\"", "");
            part.write(fileName);
            return fileName;
        } catch (IOException e) {
            Logger.getLogger(FileService.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

    private String getYear() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        return String.valueOf(year);
    }

    private String getMonth() {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;
        return String.valueOf(month);
    }

    private String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");
        Logger.getLogger(FileService.class.getName()).log(Level.INFO, "Part Header = {0} " + partHeader);

        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("'", "");
            }
        }
        return null;
    }
}