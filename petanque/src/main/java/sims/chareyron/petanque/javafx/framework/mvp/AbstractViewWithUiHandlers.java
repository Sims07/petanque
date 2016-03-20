package sims.chareyron.petanque.javafx.framework.mvp;

import sims.chareyron.petanque.javafx.framework.AbstractFxmlView;

public abstract class AbstractViewWithUiHandlers<T extends UiHandlers> extends AbstractFxmlView
		implements ViewWithUiHandlers<T> {
	private T uiHandlers;

	@Override
	public T getUiHandlers() {
		return uiHandlers;
	}

	@Override
	public void setUiHandlers(T uih) {
		this.uiHandlers = uih;

	}

}
