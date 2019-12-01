package uni.fmi.masters.vaadinUI;

import com.vaadin.navigator.View;
import com.vaadin.ui.Composite;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;

import uni.fmi.masters.beans.RoleBean;
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
		
		Grid<RoleBean> roleGrid = new Grid<>(RoleBean.class); // подавайки му RoleBean.class, автоматично му добавя всички колони
		userGrid.addSelectionListener(listener -> roleGrid.setItems(listener.getFirstSelectedItem().get().getRoles())); // взимаме ролите на потребителя
		
		VerticalLayout verticalLayout = new VerticalLayout(userGrid, roleGrid);
		verticalLayout.setSizeFull();
		
		setCompositionRoot(verticalLayout);
	}
	
}
