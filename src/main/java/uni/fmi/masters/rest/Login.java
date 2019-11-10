package uni.fmi.masters.rest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uni.fmi.masters.beans.UserBean;
import uni.fmi.masters.repositories.UserRepo;

@RestController
public class Login {

	private UserRepo userRepo;
	
	public Login(UserRepo userRepo) {
		this.userRepo = userRepo;
	}
	
	@PostMapping(path = "/register")
	public UserBean register(
			@RequestParam(value = "email") String email, 
			@RequestParam(value = "username") String username, 
			@RequestParam(value = "password") String password, 
			@RequestParam(value = "repeatPassword") String repeatPassword) {
		
		if(!password.equals(repeatPassword)) return null;
		
		password = hashMe(password);
		
		UserBean user = new UserBean(username, email, password);
		return userRepo.saveAndFlush(user);
	}
	
	@PostMapping(path = "/login")
	public ResponseEntity<UserBean> login(
			@RequestParam(value = "username") String username, 
			@RequestParam(value = "password") String password) {
		
		password = hashMe(password);
		
		UserBean user = userRepo.findUserByUsernameAndPassword(username, password);
		
		if(user != null) {
			return ResponseEntity.ok().body(user);
		}
			
		return ResponseEntity.notFound().build();
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
