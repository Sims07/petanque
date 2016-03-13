package sims.chareyron.petanque.javafx.framework.mvp;

public interface Presenter<V extends View> {
	V getView();

	String getToken();

	void onBind();

	void onStart();

	void reveal();
}
