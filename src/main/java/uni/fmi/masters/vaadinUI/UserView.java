package uni.fmi.masters.vaadinUI;

import com.vaadin.navigator.View;
import com.vaadin.ui.Composite;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;

import uni.fmi.masters.beans.UserBean;
import uni.fmi.masters.repositories.UserRepo;

public class UserView extends Composite implements View {

	private static final long serialVersionUID = 1L;
	
	private UserRepo userRepo;
	
	public UserView(UserRepo userRepo) {
		this.userRepo = userRepo;
		init();
	}
	
	private void init() {	
		Grid<UserBean> userGrid = new Grid<>();
		userGrid.addColumn(UserBean::getUsername).setCaption("Потребителско име"); // обръща се към всеки един UserBean обект и се обръща към гетера му
		userGrid.addColumn(UserBean::getEmail).setCaption("Email");
		
		userGrid.setItems(userRepo.findAll()); // подаваме си висчки user-и
		
		VerticalLayout verticalLayout = new VerticalLayout(userGrid);
		verticalLayout.setSizeFull();
		
		setCompositionRoot(verticalLayout);
	}
	
}
