package sims.chareyron.petanque.javafx.view;

import java.util.Arrays;
import java.util.List;

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

	public final static Slot HEADER_SLOT = new Slot("header");
	public final static Slot BODY_SLOT = new Slot("body");

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

	}

	@Override
	public List<Slot> getSlotList() {
		return Arrays.asList(HEADER_SLOT, BODY_SLOT);
	}

	@Override
	public void onReveal() {
		System.out.println("Main presenter on start");

	}

	@Override
	public String getToken() {
		return null;
	}
}
