package uni.fmi.masters.vaadinUI;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.Composite;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

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
		
		Button openDialog = new Button("Open Dialog", e->initDialog());
		
		VerticalLayout verticalLayout = new VerticalLayout(openDialog, roleGrid);		
		verticalLayout.setSizeFull();
		
		setCompositionRoot(verticalLayout);
	}
	
	private void initDialog() {
		// Create a sub-window and set the content
        Window subWindow = new Window("Sub-window");
        
        FormLayout nameLayout = new FormLayout();
        TextField code = new TextField("Code");
        code.setPlaceholder("Code");
        
        TextField description = new TextField("Description");
        description.setPlaceholder("Description");
        
        Button confirm = new Button("Save");
        confirm.addClickListener(listener -> insertRole(code.getValue(), description.getValue()));

        nameLayout.addComponent(code);
        nameLayout.addComponent(description);
        nameLayout.addComponent(confirm);
        
        subWindow.setContent(nameLayout);
        
        // Center it in the browser window
        subWindow.center();

        // Open it in the UI
        UI.getCurrent().addWindow(subWindow);
	}
	
	private void insertRole(String code, String description) {
		RoleBean role = new RoleBean();
		role.setCode(code);
		role.setDescription(description);
		
		roleRepo.saveAndFlush(role);
	}
	
}
