package nl.tudelft.ti2206.group9.renderer;

import nl.tudelft.ti2206.group9.entities.Player;
import nl.tudelft.ti2206.group9.level.State;

/**
 * Renders a Coin in the World.
 * @author Maarten
 */
@SuppressWarnings("restriction")
public class PlayerRenderer extends AbstractBoxRenderer<Player> {

	/**
	 * Default constructor.
	 * @param entity the entity to Trace.
	 */
	public PlayerRenderer(final Player entity) {
		super(entity);
	}

	@Override
	protected void setMaterial() {
		setMaterial(State.getSkin().getSkinMaterial());
	}

}
