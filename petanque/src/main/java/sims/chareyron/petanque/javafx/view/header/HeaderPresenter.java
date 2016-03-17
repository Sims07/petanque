package sims.chareyron.petanque.javafx.view.header;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sims.chareyron.petanque.javafx.framework.mvp.AbstractWidgetPresenter;
import sims.chareyron.petanque.javafx.framework.mvp.View;

@Component
public class HeaderPresenter extends AbstractWidgetPresenter<HeaderPresenter.MyView> {
	public interface MyView extends View {

	}

	@Autowired
	public HeaderPresenter(MyView view) {
		super();
		this.view = view;
	}

	@Override
	public void onBind() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onReveal() {
		// TODO Auto-generated method stub

	}

}
