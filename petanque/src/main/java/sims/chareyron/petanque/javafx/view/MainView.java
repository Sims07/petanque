package sims.chareyron.petanque.javafx.view;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import sims.chareyron.petanque.javafx.framework.AbstractFxmlView;
import sims.chareyron.petanque.javafx.framework.mvp.Slot;
import sims.chareyron.petanque.javafx.framework.mvp.View;

@Component
public class MainView extends AbstractFxmlView implements MainPresenter.MyView {

	@FXML
	private Pane header;
	@FXML
	private Pane body;

	@PostConstruct
	public void init() {
		System.out.println("init main view");
	}

	@Override
	public void setInSlot(Slot slot, View view) {
		if (MainPresenter.HEADER_SLOT.equals(slot)) {
			header.getChildren().add(view.getParent());
		} else {
			body.getChildren().add(view.getParent());
		}

	}

}
