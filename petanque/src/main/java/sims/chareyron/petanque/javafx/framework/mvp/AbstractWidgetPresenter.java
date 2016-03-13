package sims.chareyron.petanque.javafx.framework.mvp;

public abstract class AbstractWidgetPresenter<V extends View> extends AbstractPresenter<V> implements Presenter<V> {

	@Override
	public String getToken() {
		return null;
	}

}
