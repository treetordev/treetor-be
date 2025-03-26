package com.example.treetor.service;

import com.example.treetor.entity.JobPosts;
import com.example.treetor.entity.UserModel;
import com.example.treetor.repository.TreetorRepository;
import com.example.treetor.repository.UserRepository;
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

    @Autowired
    UserRepository userRepository;

    private String getCellValueAsString(Cell cell) {
        if (cell == null) return ""; // Return empty string if cell is null
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getLocalDateTimeCellValue().toLocalDate().toString(); // Convert date to string
                }
                return String.valueOf((long) cell.getNumericCellValue()); // Handle numeric values correctly
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
            case ERROR:
            default:
                return ""; // Return empty string for blank or error cells
        }
    }

    public List<JobPosts> getAllTodaysPost(LocalDate date) {
       return  treetorRepository.findByDate(date);
    }

    public List<String> getAllJobDomains() {
        return treetorRepository.getAllJobDomains();
    }

    public List<String> getAllUsers() {
        List<UserModel> allUsersFromDb = userRepository.findAll();
        List<String> responses = new ArrayList<>();
        String response="";
        for(UserModel user :allUsersFromDb){
            if(user.getRoles().equalsIgnoreCase("USER")){
                response= user.getName()+ "--(" +user.getEmail()+")";
                responses.add(response);
                response="";
            }
        }
        return responses;
    }
}
