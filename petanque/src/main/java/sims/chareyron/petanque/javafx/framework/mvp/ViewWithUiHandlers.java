package sims.chareyron.petanque.javafx.framework.mvp;

public interface ViewWithUiHandlers<T extends UiHandlers> {
	T getUiHandlers();

	void setUiHandlers(T uih);
}
