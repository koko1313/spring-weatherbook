package uni.fmi.masters.vaadinUI;

import com.vaadin.navigator.View;
import com.vaadin.ui.Composite;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;

import uni.fmi.masters.beans.RoleBean;
import uni.fmi.masters.repositories.RoleRepo;

public class HomeView extends Composite implements View {

	private static final long serialVersionUID = 1L;
	
	RoleRepo roleRepo;
	
	public HomeView(RoleRepo roleRepo) {
		this.roleRepo = roleRepo;
		init();
	}
	
	private void init() {
		Grid<RoleBean> roleGrid = new Grid<>();
		roleGrid.addColumn(RoleBean::getId).setCaption("Id");
		roleGrid.addColumn(RoleBean::getCode).setCaption("Code");
		roleGrid.addColumn(RoleBean::getDescription).setCaption("Description");
		
		roleGrid.setItems(roleRepo.findAll());
		
		VerticalLayout verticalLayout = new VerticalLayout(roleGrid);
		verticalLayout.setSizeFull();
		
		setCompositionRoot(verticalLayout);
	}
	
}
