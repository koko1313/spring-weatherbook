package uni.fmi.masters.vaadinUI;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

import uni.fmi.masters.repositories.RoleRepo;
import uni.fmi.masters.repositories.UserRepo;

// темата
@Theme("apptheme")
// пътя, на който ще го достъпим от браузъра
@SpringUI(path = "/admin-panel")
@UIScope
public class VaadinUI extends UI {
	
	private static final long serialVersionUID = 1L;

	// injects repositories, getters, setters ...
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Override
	protected void init(VaadinRequest request) {
		Label title = new Label("Menu");
		title.addStyleName(ValoTheme.MENU_TITLE);
		
		Button homeViewBtn = new Button("Home", e->getNavigator().navigateTo("homeView"));
		homeViewBtn.addStyleNames(ValoTheme.BUTTON_LARGE, ValoTheme.MENU_TITLE);
		
		Button profileViewBtn = new Button("Profile", e->getNavigator().navigateTo("userView"));
		profileViewBtn.addStyleNames(ValoTheme.BUTTON_LARGE, ValoTheme.MENU_TITLE);
		
		CssLayout menu = new CssLayout(title, homeViewBtn, profileViewBtn);
		menu.addStyleName(ValoTheme.MENU_ROOT);
		
		CssLayout viewContainer = new CssLayout();
		
		HorizontalLayout mainLayout = new HorizontalLayout(menu, viewContainer);
		mainLayout.setExpandRatio(viewContainer, 1);
		mainLayout.setSizeFull();
		
		setContent(mainLayout);
		
		Navigator navigator = new Navigator(this, viewContainer);
		navigator.addView("", DefaultView.class);
		navigator.addView("homeView", new HomeView(roleRepo));
		navigator.addView("userView", new UserView(userRepo));
	}
	
}
