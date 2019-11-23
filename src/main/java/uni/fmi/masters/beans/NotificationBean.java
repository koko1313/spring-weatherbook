package uni.fmi.masters.beans;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

// не трябва да са Beans, по-добре да ги кръстим Entity-та
@Entity
@Table(name = "notification")
public class NotificationBean {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(referencedColumnName = "id")
	private UserBean fromUser;
	
	@ManyToOne
	@JoinColumn(referencedColumnName = "id")
	private UserBean toUser;
	
	@Column(name = "status", length = 20)
	private String status;
	
	@Column(name = "date")
	private Date date;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UserBean getFromUser() {
		return fromUser;
	}

	public void setFromUsert(UserBean fromUser) {
		this.fromUser = fromUser;
	}

	public UserBean getToUser() {
		return toUser;
	}

	public void setToUser(UserBean toUser) {
		this.toUser = toUser;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
