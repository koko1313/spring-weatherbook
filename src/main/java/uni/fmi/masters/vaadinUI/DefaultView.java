package uni.fmi.masters.vaadinUI;

import com.vaadin.navigator.View;
import com.vaadin.ui.Composite;
import com.vaadin.ui.Label;

public class DefaultView extends Composite implements View {

	private static final long serialVersionUID = 1L;

	public DefaultView() {
		init();
	}
	
	protected void init() {
		setCompositionRoot(new Label("Начален екран"));
	}
	
}
