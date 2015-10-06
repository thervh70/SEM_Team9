package nl.tudelft.ti2206.group9.renderer;

import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Node;
import nl.tudelft.ti2206.group9.entities.AbstractEntity;
import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.util.ObservableLinkedList.Listener;

@SuppressWarnings("restriction")
public class GroupEntitiesRenderer extends Group implements Renderer, Listener {

	public GroupEntitiesRenderer() {
		super();
		State.getTrack().addEntitiesListener(this);

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
		if (!Platform.isSupported(ConditionalFeature.SCENE3D)) {
			return;
		}
		switch (type) {
		case ADD_FIRST:
			getChildren().add(0, ((AbstractEntity) item).createRenderer());
			break;
		case ADD_LAST:
			getChildren().add(((AbstractEntity) item).createRenderer());
			break;
		case REMOVE:
			// TODO ehm... how to remove the BoxRenderer associated with this?
			break;
		case REMOVE_INDEX:
			getChildren().remove(index);
			break;
		default: break;
		}
	}

}
