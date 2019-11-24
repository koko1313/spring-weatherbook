package uni.fmi.masters.rest;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
				UserBean findUserBy = userRepo.findByUsername(username);
				
				if(findUserBy != null) {
					if(findUserBy.getId() != findUser.get().getId()) {
						return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
					}
				}
				
				findUserBy = userRepo.findByEmail(email);
				
				if(findUserBy != null) {
					if(findUserBy.getId() != findUser.get().getId()) {
						return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
					}
				}
			}
			
			findUser.get().setEmail(email);
			findUser.get().setUsername(username);
			
			userRepo.saveAndFlush(findUser.get());
		}
		
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
}
