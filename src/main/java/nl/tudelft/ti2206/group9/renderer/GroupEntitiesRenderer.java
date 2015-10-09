package nl.tudelft.ti2206.group9.renderer;

import javafx.scene.DepthTest;
import javafx.scene.Node;
import nl.tudelft.ti2206.group9.entities.AbstractEntity;
import nl.tudelft.ti2206.group9.level.State;
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
		setDepthTest(DepthTest.ENABLE);

		for (final AbstractEntity e : State.getTrack().getEntities()) {
			getChildren().add(e.createRenderer());
		}
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
		switch (type) {
		case ADD_FIRST:    getChildren().add(0, renderer); break;
		case ADD_LAST:     getChildren().add(renderer); break;
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
