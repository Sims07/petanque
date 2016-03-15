package sims.chareyron.petanque.javafx.framework.mvp;

import java.util.List;

public interface Presenter<V extends View> {
	V getView();

	String getToken();

	void onBind();

	void onStart();

	void reveal();

	List<Presenter> childrenPresenter();
}
