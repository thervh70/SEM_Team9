package nl.tudelft.ti2206.group9.util;

import java.util.Observable;

import nl.tudelft.ti2206.group9.util.GameObserver.Category;
import nl.tudelft.ti2206.group9.util.GameObserver.GameUpdate;
import nl.tudelft.ti2206.group9.util.GameObserver.Specific;

/**
 * This utility class handles the observability of the game. If there are any
 * {@link GameObserver}s attached, calling the
 * {@link #notify(Category, Specific, Object...)} method will update these
 * observers.
 * @author Maarten
 */
public final class GameObservable extends Observable {

    /**
     * @throws UnsupportedOperationException
     *         because notifyObservers() must be called with argument.
     */
    @Deprecated
    public void notifyObservers() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("notifyObservers() must be "
                + "called with a GameUpdate as argument.");
    }

    /**
     * Deprecated, because notifyObservers() must be called with GameUpdate.
     * @param arg any GameUpdate
     */
    @Deprecated
    public void notifyObservers(final Object arg) {
        notify((GameUpdate) arg);
    }

    /**
     * Call this method when you want to update the observers.
     * @param cat the Category of this update.
     * @param spec the Specific action of this update.
     * @param optionalArgs Optional arguments that come with the update
     *             (e.g. lane numbers, mouse buttons, keyboard keys, ...)
     */
    public void notify(final Category cat, final Specific spec,
            final Object... optionalArgs) {
        notify(new GameUpdate(cat, spec, optionalArgs));
    }

    /**
     * If this object has changed, as indicated by the hasChanged method,
     * then notify all of its observers and then call the clearChanged method to
     * indicate that this object has no longer changed.
     * Each observer has its update method called with two arguments:
     * this observable object and the arg argument.
     * @param arg any GameUpdate
     */
    public void notify(final GameUpdate arg) {
        setChanged();
        super.notifyObservers(arg);
    }

}
