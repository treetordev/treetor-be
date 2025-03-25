package com.example.treetor.service;

import com.example.treetor.entity.JobPosts;
import com.example.treetor.repository.TreetorRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class TreetorService {

    @Autowired
    TreetorRepository treetorRepository;

    public List<JobPosts> parseExcelFile(MultipartFile file, LocalDate date) {
        List<JobPosts> jobposts = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            if (rows.hasNext()) rows.next(); // Skip the first row (header)

            while (rows.hasNext()) {
                Row row = rows.next();

                // Ensure the row has at least 3 cells
                if (row.getPhysicalNumberOfCells() < 3) continue;

                JobPosts post = new JobPosts();
                post.setLink(getCellValueAsString(row.getCell(0)));
                post.setPostContent(getCellValueAsString(row.getCell(1)));
                post.setLeadLocation(getCellValueAsString(row.getCell(2)));
                post.setDatePosted(date); // Use provided date instead of reading from Excel

                jobposts.add(post);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error processing Excel file: " + e.getMessage());
        }

        treetorRepository.saveAll(jobposts);
        return jobposts;
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) return "";
        switch (cell.getCellType()) {
            case STRING: return cell.getStringCellValue().trim();
            case NUMERIC: return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN: return String.valueOf(cell.getBooleanCellValue());
            default: return "";
        }
    }

    public List<JobPosts> getAllTodaysPost() {
       return  treetorRepository.findByDate(LocalDate.now());
    }
}
