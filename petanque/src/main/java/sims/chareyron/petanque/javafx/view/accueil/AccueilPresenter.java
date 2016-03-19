package sims.chareyron.petanque.javafx.view.accueil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sims.chareyron.petanque.javafx.framework.mvp.AbstractPresenter;
import sims.chareyron.petanque.javafx.framework.mvp.Slot;
import sims.chareyron.petanque.javafx.framework.mvp.View;
import sims.chareyron.petanque.javafx.view.MainPresenter;

@Component
public class AccueilPresenter extends AbstractPresenter<AccueilPresenter.MyView> {

	public interface MyView extends View {

	}

	@Autowired
	public AccueilPresenter(MyView view) {
		super();
		this.view = view;
	}

	public Slot revealedInSlot() {
		return MainPresenter.BODY_SLOT;
	}

	@Override
	public String getToken() {

		return "default";
	}

	@Override
	public void onBind() {
		System.out.println("Bind accueil");

	}

	@Override
	public void onReveal() {
		// TODO Auto-generated method stub

	}

}
