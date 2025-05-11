package com.example.treetor.service;

import com.example.treetor.entity.JobAssignment;
import com.example.treetor.entity.JobPosts;
import com.example.treetor.entity.UserModel;
import com.example.treetor.repository.JobAssignmentRepository;
import com.example.treetor.repository.JobPostsRepository;
import com.example.treetor.repository.TreetorRepository;
import com.example.treetor.repository.UserRepository;
import com.example.treetor.request.AssignJobPostsRequest;
import com.example.treetor.request.InvalidAndContactInfoRequest;
import com.example.treetor.request.LeadNotesRequest;
import com.example.treetor.response.UserDetailsUiResponse;
import com.example.treetor.utility.CommonHelper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserService {


	@Autowired
	UserRepository repo;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	TreetorRepository jobPostsRepository;

	@Autowired
	JobAssignmentRepository jobAssignmentRepository;

	@Autowired
	private EmailService emailService;

	public UserService() {

	}

	public List<UserModel> getUsers()
	{
		return repo.findAll();
	}

	public UserModel createUser(UserModel user)
	{
		user.setPassword(encoder.encode(user.getPassword()));
		return repo.save(user);
	}
	
	public static String getLoggedInUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userDetails.getUsername();
        }
        return null;
    }

	public UserDetailsUiResponse getUserDetailsByEmail(String email) {
		UserModel user = repo.findByEmail(email).orElseThrow((()-> new RuntimeException("User Not Found!!")));
		return CommonHelper.convertToUserDetailsUiResponse(user);
	}

	@Transactional
	public void assignJobPostsToUser(AssignJobPostsRequest request) {
		List<JobPosts> jobPosts = jobPostsRepository.findAllById(request.getPostIds());

		List<JobAssignment> assignments = jobPosts.stream()
				.map(jobPost -> new JobAssignment(request.getUserEmail(), jobPost))
				.collect(Collectors.toList());

		jobAssignmentRepository.saveAll(assignments);
	}

	public void markInvalid(InvalidAndContactInfoRequest request) {
		int i = jobAssignmentRepository.markInvalid(request.getEmail(), request.getPostId());
		JobPosts byId = jobPostsRepository.findById(request.getPostId()).get();
		if (i > 0) {
			String subject = "Job Post Marked as Invalid";
			String message = "User with email " + request.getEmail() + " has marked the post with ID " + request.getPostId() + " as invalid."+"/n"
					+ "Post link :"+byId.getLink()+"/n"
					+"Post Content :"+byId.getPostContent();

			emailService.sendSimpleEmail("care.treetor@gmail.com",subject,message);
		} else {
			throw new RuntimeException("No matching job assignment found for user: " +  request.getEmail() + " and postId: " +  request.getPostId());
		}
	}

	public void saveLeadNotes(LeadNotesRequest request) {
		jobAssignmentRepository.saveNotes(request.getEmail(),request.getPostId(),request.getNotes());
	}

	public String getLeadNotes(String email, Long postId) {
		return jobAssignmentRepository.getLeadNotes(email,postId);
	}

	public void requestContactInfo(InvalidAndContactInfoRequest request) {

		int i = jobAssignmentRepository.requestedContactInfo(request.getEmail(), request.getPostId());
		JobPosts byId = jobPostsRepository.findById(request.getPostId()).get();

		if (i > 0) {
			String subject = "Contact info requested";
			String message = "User with email " + request.getEmail() + " has asked for the contact info of the post with ID " +
					request.getPostId() +"/n"
					+ "Post link :"+byId.getLink()+"/n"
					+"Post Content :"+byId.getPostContent();


			emailService.sendSimpleEmail("care.treetor@gmail.com",subject,message);
		} else {
			throw new RuntimeException("No matching job assignment found for user: " +  request.getEmail() + " and postId: " +  request.getPostId());
		}
	}
}
