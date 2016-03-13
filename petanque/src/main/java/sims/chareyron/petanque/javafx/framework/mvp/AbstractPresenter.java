package sims.chareyron.petanque.javafx.framework.mvp;

public abstract class AbstractPresenter<V extends View> implements Presenter<V> {
	private boolean bound = false;

	protected V view;

	public V getView() {
		return view;
	}

	protected void setInSlot(Slot slot, AbstractWidgetPresenter<?> headerPresenter) {
		getView().setInSlot(slot, headerPresenter.getView());
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
