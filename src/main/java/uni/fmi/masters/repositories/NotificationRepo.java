package uni.fmi.masters.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uni.fmi.masters.beans.NotificationBean;

@Repository
public interface NotificationRepo extends JpaRepository<NotificationBean, Integer> {

	NotificationBean findByFromUserIdAndToUserId(int fromUserId, int toUserId);
	
}
