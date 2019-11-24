package uni.fmi.masters.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uni.fmi.masters.beans.UserBean;

@Repository
public interface UserRepo extends JpaRepository<UserBean, Integer> {

	// за име на метода, спазваме конвенцията, така ще ни търси Username и Password в базата, без да си разписваме метода
	UserBean findUserByUsernameAndPassword(String username, String password);
	
	List<UserBean> findUserByUsernameContaining(String username);
	
	UserBean findByUsername(String username);
	
	UserBean findByEmail(String email);
	
}
