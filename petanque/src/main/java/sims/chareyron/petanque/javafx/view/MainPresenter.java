package sims.chareyron.petanque.javafx.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sims.chareyron.petanque.javafx.framework.mvp.AbstractPresenter;
import sims.chareyron.petanque.javafx.framework.mvp.Slot;
import sims.chareyron.petanque.javafx.framework.mvp.View;
import sims.chareyron.petanque.javafx.view.MainPresenter.MyView;
import sims.chareyron.petanque.javafx.view.header.HeaderPresenter;

@Component
public class MainPresenter extends AbstractPresenter<MyView> {

	private HeaderPresenter headerPresenter;

	private final static Slot HEADER_SLOT = new Slot("header");

	@Autowired
	public MainPresenter(MyView view, HeaderPresenter headerPresenter) {
		super();
		this.view = view;
		this.headerPresenter = headerPresenter;
	}

	public interface MyView extends View {

	}

	@Override
	public void onBind() {
		setInSlot(HEADER_SLOT, headerPresenter);
		System.out.println("Main presenter on bind");

	}

	@Override
	public void onStart() {
		System.out.println("Main presenter on start");

	}

	@Override
	public String getToken() {
		return "default";
	}
}
