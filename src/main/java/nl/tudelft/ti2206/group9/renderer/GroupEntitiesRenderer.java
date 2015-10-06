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
			((Renderer) renderer).update();
		}
	}

	@Override
	public void update(final Type type, final Object item, final int index) {
		switch (type) {
		case ADD_FIRST:
			getChildren().add(0, ((AbstractEntity) item).createRenderer());
			break;
		case ADD_LAST:
			getChildren().add(((AbstractEntity) item).createRenderer());
			break;
		case REMOVE:
			// TODO ehm... how to remove the BoxRenderer associated with this?
			// Currently, this type of remove is not called. But you never know.
			break;
		case REMOVE_INDEX:
			getChildren().remove(index);
			break;
		default: break;
		}
	}

}
