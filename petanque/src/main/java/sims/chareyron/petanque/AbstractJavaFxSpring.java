package sims.chareyron.petanque;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javafx.application.Application;

/**
 * @author Thomas Darimont
 */
public abstract class AbstractJavaFxSpring extends Application {

	private static String[] savedArgs;

	private ConfigurableApplicationContext applicationContext;

	@Override
	public void init() throws Exception {
		applicationContext = SpringApplication.run(getClass(), savedArgs);
		applicationContext.getAutowireCapableBeanFactory().autowireBean(this);
	}

	@Override
	public void stop() throws Exception {

		super.stop();
		applicationContext.close();
	}

	protected static void launchApp(Class<? extends AbstractJavaFxSpring> appClass, String[] args) {

		AbstractJavaFxSpring.savedArgs = args;
		Application.launch(appClass, args);
	}
}
