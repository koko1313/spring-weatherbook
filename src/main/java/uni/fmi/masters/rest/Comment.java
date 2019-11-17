package uni.fmi.masters.rest;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uni.fmi.masters.beans.CommentBean;
import uni.fmi.masters.beans.UserBean;
import uni.fmi.masters.repositories.CommentRepo;

@RestController
public class Comment {

	private CommentRepo commentRepo;
	
	public Comment(CommentRepo commentRepo) {
		this.commentRepo = commentRepo;
	}
	
	// този метод връща json
	@GetMapping(path = "/comment/all")
	public List<CommentBean> getAllComments() {
		// по принцип трябва да си направим проверка дали е логнат потребител и тогава да връщаме коментарите
		return commentRepo.findAll();
	}
	
	@PostMapping(path = "/comment/add")
	public String addComment(
			@RequestParam(value = "selectCity") String city,
			@RequestParam(value = "comment") String comment,
			@RequestParam(value = "temp") Double temp,
			@RequestParam(value = "picture") String picture,
			HttpSession session) {
		
		UserBean user = (UserBean)session.getAttribute("user");
		
		if(user != null) {
			CommentBean commentBean = new CommentBean();
			commentBean.setCity(city);
			commentBean.setComment(comment);
			commentBean.setTemp(temp);
			commentBean.setPicture(picture);
			commentBean.setUser(user);
			
			commentBean = commentRepo.saveAndFlush(commentBean);
			
			if(commentBean != null) {
				return commentBean.getId() + "";
			}
			
			return "-1";
		} else {
			return "error";
		}
	}
	
	@DeleteMapping(path = "/comment/delete")
	public ResponseEntity<Boolean> deleteComment(
			@RequestParam(value = "id") int id,
			HttpSession session) {
		
		UserBean user = (UserBean)session.getAttribute("user");
		
		// потребителя не е логнат
		if(user == null) {
			return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
		}
		
		Optional<CommentBean> optionalComment = commentRepo.findById(id);
		
		// коментара не е намерен
		if(!optionalComment.isPresent()) {
			return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
		}
		
		CommentBean comment = optionalComment.get();
		
		// коментара не е наш
		//if(!coment.getUser().equals(user)) {
		if(comment.getUser().getId() != user.getId()) {
			return new ResponseEntity<>(false, HttpStatus.I_AM_A_TEAPOT);
		}
		
		commentRepo.delete(comment);
		
		// коментара е изтрит
		return new ResponseEntity<>(true, HttpStatus.OK);
	}
	
}
