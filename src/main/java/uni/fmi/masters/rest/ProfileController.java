package uni.fmi.masters.rest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uni.fmi.masters.beans.UserBean;
import uni.fmi.masters.repositories.UserRepo;

@RestController
public class ProfileController {

	private UserRepo userRepo;
	
	public ProfileController(UserRepo userRepo) {
		this.userRepo = userRepo;
	}
	
	@PutMapping(path="profile/updateMyProfile")
	public ResponseEntity<Boolean> updateMyProfile(
			@RequestParam(value="username") String username,
			@RequestParam(value="email") String email,
			@RequestParam(value="password") String password,
			@RequestParam(value="rePassword") String rePassword,
			HttpSession session) {
		
		UserBean user = (UserBean) session.getAttribute("user");
		
		if(user != null) {
			Optional<UserBean> findUser = userRepo.findById(user.getId());
			if(findUser.isPresent()) {
				boolean checkUser = checkUsername(username, findUser.get());
				if(!checkUser) {
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
				
				boolean checkEmail = checkEmail(email, findUser.get());
				if(!checkEmail) {
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
				
			}
			
			findUser.get().setEmail(email);
			findUser.get().setUsername(username);
			
			if(password != null && password == " " && password.equals(rePassword)) {
				findUser.get().setPassword(hashMe(password));
			}
			
			userRepo.saveAndFlush(findUser.get());
			
			return new ResponseEntity<>(true, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@PostMapping(path="/profile/logout")
	public ResponseEntity<Boolean> logout(HttpSession session) {
		UserBean user = (UserBean)session.getAttribute("user");
		
		if(user!=null) {
			session.invalidate();
			return new ResponseEntity<>(HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	private boolean checkUsername(String username, UserBean findUser) {
		
		UserBean findUserBy = userRepo.findByUsername(username);
		
		if(findUserBy != null) {
			if(findUserBy.getId() != findUser.getId()) {
				return false;
			}
		}
		
		return true;
	}
	
	private boolean checkEmail(String email, UserBean findByUser) {
		UserBean findUserBy = userRepo.findByEmail(email);
		
		if(findUserBy != null) {
			if(findUserBy.getId() != findByUser.getId()) {
				return false;
			}
		}
		
		return true;
	}
	
	private String hashMe(String text) {
		StringBuilder sb = new StringBuilder();
		
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(text.getBytes());
			byte[] bytes = md.digest();
			
			for(int i=0; i<bytes.length; i++) {
				sb.append((char)bytes[i]);
			}
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return sb.toString();
	}
	
}



