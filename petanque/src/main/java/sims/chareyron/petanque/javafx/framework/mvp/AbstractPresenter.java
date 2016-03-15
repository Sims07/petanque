package sims.chareyron.petanque.javafx.framework.mvp;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPresenter<V extends View> implements Presenter<V> {
	private boolean bound = false;

	protected V view;

	@SuppressWarnings("rawtypes")
	private List<Presenter> childrenPresenters = new ArrayList<>();

	public V getView() {
		return view;
	}

	protected void setInSlot(Slot slot, AbstractWidgetPresenter<?> childPresenter) {
		getView().setInSlot(slot, childPresenter.getView());
		childrenPresenters.add(childPresenter);
	}

	@SuppressWarnings("rawtypes")
	public List<Presenter> childrenPresenter() {
		return childrenPresenters;
	}

	@Override
	public void reveal() {
		if (!bound) {
			onBind();
		} else {
			onStart();
		}

	}

}
