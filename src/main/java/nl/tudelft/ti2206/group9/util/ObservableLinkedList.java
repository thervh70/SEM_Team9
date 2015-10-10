package nl.tudelft.ti2206.group9.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import nl.tudelft.ti2206.group9.util.ObservableLinkedList.Listener.Type;

/**
 * Observable LinkedList, which is used in Track to make the ExternalTicker able
 * to know when new entities are added to the list or when old ones are removed.
 * @author Maarten
 * @param <T> The type of the items in the LinkedList.
 *
 */
@SuppressWarnings("serial")
public class ObservableLinkedList<T> extends LinkedList<T> {

    /** List of listeners for this ObservableList. */
    private final List<Listener> listeners = new ArrayList<Listener>();

    /**
     * Add a listener to the ObservableLinkedList.
     * @param listener listener to be added.
     */
    public void addListener(final Listener listener) {
        listeners.add(listener);
    }

    /**
     * Remove a listener from the ObservableLinkedList.
     * @param listener listener to be removed.
     */
    public void removeListener(final Listener listener) {
        listeners.remove(listener);
    }

    /**
     * Fires an event to all listeners.
     * @param type the type of the update.
     * @param item the item involved in the update.
     * @param index the index involved in the update. If not used, equals -1.
     */
    private void fireEvent(final Type type, final Object item,
            final int index) {
        for (final Listener l : listeners) {
            l.update(type, item, index);
        }
    }

    /**
     * Fires an event to all listeners.
     * @param type the type of the update.
     * @param item the item involved in the update.
     */
    private void fireEvent(final Type type, final Object item) {
        fireEvent(type, item, -1);
    }

    /**
     * Interface for Listening to this ObservableLinkedList.
     */
    public interface Listener {
        /** Enumeration for the update types. */
        enum Type {
            /** When an element is added to the end of the list. */
            ADD_LAST,
            /** When an element is added to the begin of the list. */
            ADD_FIRST,
            /** When an element is removed from the list. */
            REMOVE,
            /** When an element is removed from the list by index. */
            REMOVE_INDEX
        }

        /**
         * Called when an update is made to the LinkedList.
         * @param type the type of the update.
         * @param item the item involved in the update.
         * @param index the index involved in the update.
         *             If not used, equals -1.
         */
        void update(Type type, Object item, int index);
    }


    @Override
    public boolean add(final T item) {
        fireEvent(Type.ADD_LAST, item);
        return super.add(item);
    }

    @Override
    public void addFirst(final T item) {
        fireEvent(Type.ADD_FIRST, item);
        super.addFirst(item);
    }

    @Override
    public void addLast(final T item) {
        fireEvent(Type.ADD_LAST, item);
        super.addLast(item);
    }

    @Override
    public boolean remove(final Object o) {
        fireEvent(Type.REMOVE, o);
        return super.remove(o);
    }

    @Override
    public T remove(final int index) {
        fireEvent(Type.REMOVE_INDEX, get(index), index);
        return super.remove(index);
    }

}
