package com.example.treetor.service;

import com.example.treetor.entity.JobAssignment;
import com.example.treetor.entity.JobPosts;
import com.example.treetor.entity.UserModel;
import com.example.treetor.repository.JobAssignmentRepository;
import com.example.treetor.repository.TreetorRepository;
import com.example.treetor.repository.UserRepository;
import com.example.treetor.response.JobPostsUiResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
public class TreetorService {

    @Autowired
    TreetorRepository treetorRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JobAssignmentRepository jobAssignmentRepository;

    public List<JobPosts> parseExcelFile(MultipartFile file, LocalDate date) {
        List<JobPosts> jobposts = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            if (rows.hasNext()) rows.next(); // Skip the first row (header)

            // Fetch all existing links from DB to avoid duplicates
            Set<String> existingLinks = treetorRepository.findAllLinks(); // Custom method

            while (rows.hasNext()) {
                Row row = rows.next();

                // Ensure the row has at least 4 cells
                if (row.getPhysicalNumberOfCells() < 4) continue;

                String link = getCellValueAsString(row.getCell(0));
                if (existingLinks.contains(link)) continue; // Skip duplicate

                JobPosts post = new JobPosts();
                post.setLink(link);
                post.setPostContent(getCellValueAsString(row.getCell(1)));
                post.setLeadLocation(getCellValueAsString(row.getCell(2)));
                post.setLeadsDomain(getCellValueAsString(row.getCell(5)));
                post.setDatePosted(date);

                jobposts.add(post);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error processing Excel file: " + e.getMessage());
        }

        treetorRepository.saveAll(jobposts);
        return jobposts;
    }



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

    public List<JobPostsUiResponse> getAssignedPostByEmail(LocalDate date, String email) {
        List<JobAssignment> jobPostsByEmailAndDate = jobAssignmentRepository.findJobPostsByEmailAndDate(email, date);
        List<JobPostsUiResponse> jobPostsUiResponseList = new ArrayList<>();
        for(JobAssignment job :jobPostsByEmailAndDate){
            JobPostsUiResponse jobPostsUiResponse = new JobPostsUiResponse();

            jobPostsUiResponse.setPostId(job.getJobPost().getPostId());
            jobPostsUiResponse.setDatePosted(job.getJobPost().getDatePosted());
            jobPostsUiResponse.setLink(job.getJobPost().getLink());
            jobPostsUiResponse.setLeadLocation(job.getJobPost().getLeadLocation());
            jobPostsUiResponse.setPostContent(job.getJobPost().getPostContent());
            jobPostsUiResponse.setLeadsDomain(job.getJobPost().getLeadsDomain());
            jobPostsUiResponse.setInvalid(job.isMarkedInvalid());
            jobPostsUiResponse.setRequestedContactInfo(job.isContactInfoRequested());
            jobPostsUiResponse.setNotes(job.getNotes());

            jobPostsUiResponseList.add(jobPostsUiResponse);
        }

        return jobPostsUiResponseList;
    }
}
