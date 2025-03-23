package com.example.treetor.service;


import com.example.treetor.entity.RefreshToken;
import com.example.treetor.entity.UserModel;
import com.example.treetor.request.CreateUserUiRequest;
import com.example.treetor.request.JwtRequest;
import com.example.treetor.request.RefreshTokenRequest;
import com.example.treetor.response.JwtResponse;
import com.example.treetor.response.UserDetailsUiResponse;
import com.example.treetor.utility.CommonHelper;
import com.example.treetor.utility.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthBl {

	@Autowired
	private AuthenticationManager manager;
	
	@Autowired
	private AuthService service;
	
	@Autowired
	TokenGenerator tokenGen;
	
	@Autowired
	RefreshTokenService refreshService;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	UserService userService;
	
	@Autowired
	CustomUserDetailsService userDetailsService;

	public void doAuthenticate(String email, String password) {

		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email.toLowerCase(), password);
		try {
			manager.authenticate(authentication);

		} catch (Exception e) {
			//throw new ProviderCustomException(ErrorCodes.LOGIN_Exception, ErrorMessages.getErrorMessages().get(ErrorCodes.LOGIN_Exception));
		}

	}

	public UserDetails loadUserByUsername(String email) {

		return service.loadUserByUsername(email);
	
	}

	public String generateToken(UserDetails userDetails) {
		
		return tokenGen.generateToken(userDetails);
	}

	public RefreshToken createRefreshToken(String email) {

		return refreshService.createRefreshToken(email);
	}

	public String forgotPassword(JwtRequest request) {

		UserModel model = new UserModel();
		model.setEmail(request.getEmail());
		model.setPassword(encoder.encode(request.getNewPassword()));

		return service.forgotPassword(model);	
	}

	public Boolean createUser(CreateUserUiRequest req) {

		UserModel model = new UserModel();
		model.setAbout(req.getAbout());
		model.setEmail(req.getEmail().toLowerCase());
		model.setName(req.getName());
		model.setPassword(req.getPassword());
		model.setRoles(req.getRole());
		model.setUserKey(CommonHelper.generateUserKey());
		 UserModel createUser = userService.createUser(model);
		 if(createUser!=null)
			 return true;
		 else
			 return false;
	}

	public UserDetailsUiResponse getUserByUserKey(String userKey) {

		 UserModel loadUserByUserKey = (UserModel)userDetailsService.loadUserByUserKey(userKey);
		 UserDetailsUiResponse response = CommonHelper.convertToUserDetailsUiResponse(loadUserByUserKey);
		 return response;
	}

	/*public JwtResponse refreshJwtToken(RefreshTokenRequest request) {
		// TODO Auto-generated method stub
		RefreshToken refreshToken = refreshService.verifyRefreshToken(request.getRefreshToken());
		UserModel user = refreshToken.getUser();
		String s =generateToken(user);
		
		return JwtResponse.builder().refreshToken(refreshToken.getRefreshToken()).token(s).userName(user.getUsername()).build();
	}*/

}
