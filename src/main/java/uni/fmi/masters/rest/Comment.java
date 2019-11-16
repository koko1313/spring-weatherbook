package uni.fmi.masters.rest;

import javax.servlet.http.HttpSession;

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
	
}
