package nl.tudelft.ti2206.group9.gui.renderer;

import javafx.scene.Node;
import javafx.scene.shape.Box;
import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.level.entity.AbstractEntity;
import nl.tudelft.ti2206.group9.util.ObservableLinkedList.Listener;

/**
 * Renders the entire group of entities, and keeps it up-to-date.
 * @author Maarten
 */
@SuppressWarnings("restriction")
public class GroupEntitiesRenderer extends AbstractGroupRenderer
		implements Listener {

	/** Default constructor. */
	public GroupEntitiesRenderer() {
		super();
		State.getTrack().addEntitiesListener(this);

		final Box playerOverlay = new Box();

		for (final AbstractEntity e : State.getTrack().getEntities()) {
			final AbstractBoxRenderer<?> r = e.createRenderer();
			getChildren().add(r);
			if (r instanceof PlayerRenderer) {
				((PlayerRenderer) r).setOverlay(playerOverlay);
			}
		}

		getChildren().add(playerOverlay);
	}

	@Override
	public void update() {
		for (final Node renderer : getChildren()) {
			try {
				((Renderer) renderer).update();
			} catch (ClassCastException e) { //NOPMD
				// If the node is not a renderer, no update is needed.
			}
		}
	}

	@Override
	public void update(final Type type, final Object item, final int index) {
		final AbstractEntity entity = (AbstractEntity) item;
		final AbstractBoxRenderer<?> renderer = entity.createRenderer();
		final int lastIndex = getChildren().size() - 1;
		switch (type) {
		case ADD_FIRST:    getChildren().add(0, renderer); break;
		case ADD_LAST:     getChildren().add(lastIndex, renderer); break;
							// index + 1, because overlays is at index 0.
		case REMOVE_INDEX: getChildren().remove(index); break;
		case REMOVE:
			// It is not possible to call remove(entity) on the children of this
			// BoxRenderer. Track.children contains Entities,
			// this.children contains BoxRenderers.
			final int indexOf = State.getTrack().getEntities().indexOf(entity);
			getChildren().remove(indexOf);
			break;
		default: break;
		}
	}

}
