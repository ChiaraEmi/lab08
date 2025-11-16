package it.unibo.mvc;

import java.lang.reflect.InvocationTargetException;

import it.unibo.mvc.api.DrawNumberController;
import it.unibo.mvc.api.DrawNumberView;
import it.unibo.mvc.controller.DrawNumberControllerImpl;
import it.unibo.mvc.model.DrawNumberImpl;

/**
 * Application entry-point.
 */
public final class LaunchApp {

    private LaunchApp() { }

    /**
     * Runs the application.
     *
     * @param args ignored
     * @throws ClassNotFoundException if the fetches class does not exist
     * @throws NoSuchMethodException if the 0-ary constructor do not exist
     * @throws InvocationTargetException if the constructor throws exceptions
     * @throws InstantiationException if the constructor throws exceptions
     * @throws IllegalAccessException in case of reflection issues
     */
    public static void main(final String... args) 
        throws 
        ClassNotFoundException, 
        InstantiationException, 
        IllegalAccessException, 
        InvocationTargetException, 
        NoSuchMethodException {
        final var model = new DrawNumberImpl();
        final DrawNumberController app = new DrawNumberControllerImpl(model);
        final var classLoadSwg = Class.forName("it.unibo.mvc.view.DrawNumberSwingView");
        final var classLoadStdO = Class.forName("it.unibo.mvc.view.DrawNumberStandardOutputView");
        for (int i = 0; i < 3; i++) {
            final DrawNumberView view = (DrawNumberView) classLoadSwg.getDeclaredConstructor().newInstance();
            final DrawNumberView view2 = (DrawNumberView) classLoadStdO.getDeclaredConstructor().newInstance();
            app.addView(view);
            app.addView(view2);
        }
    }
}
