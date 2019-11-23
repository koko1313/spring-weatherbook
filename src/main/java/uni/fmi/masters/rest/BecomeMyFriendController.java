package uni.fmi.masters.rest;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uni.fmi.masters.beans.NotificationBean;
import uni.fmi.masters.beans.UserBean;
import uni.fmi.masters.repositories.NotificationRepo;
import uni.fmi.masters.repositories.UserRepo;

@RestController
public class BecomeMyFriendController {
	
	private UserRepo userRepo;
	
	private NotificationRepo notificationRepo;
	
	public BecomeMyFriendController(UserRepo userRepo, NotificationRepo notificationRepo) {
		this.userRepo = userRepo;
		this.notificationRepo = notificationRepo;
	}
	
	@PostMapping(path = "/user/sendFriendRequest")
	public ResponseEntity<Boolean> sendFriendRequest(
			@RequestParam(value = "forUserId") int forUserId,
			HttpSession session) {
		
		UserBean user = (UserBean)session.getAttribute("user");
		
		if(user == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		Optional<UserBean> forUser = userRepo.findById(forUserId);
		
		// ако потребителя, на който изпращаме поканата не е намерен
		if(!forUser.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		NotificationBean notificationBean = notificationRepo.findByFromUserIdAndToUserId(user.getId(), forUser.get().getId());
		
		// ако вече няма такава покана (за да няма спам)
		if(notificationBean == null) {
			notificationBean = new NotificationBean();
			notificationBean.setStatus("Please");
			notificationBean.setFromUsert(user);
			notificationBean.setToUser(forUser.get());
			notificationBean.setDate(new Date());
			
			notificationRepo.saveAndFlush(notificationBean);
			
			return new ResponseEntity<>(true, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
		
	}
	
	@GetMapping(path = "/user/search")
	public ResponseEntity<List<UserBean>> findUsers(
			@RequestParam(value="username") String username,
			HttpSession session) {
		
		UserBean user = (UserBean)session.getAttribute("user");
		
		if(user == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		List<UserBean> users = userRepo.findUserByUsernameContaining(username);
		
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
	
}
