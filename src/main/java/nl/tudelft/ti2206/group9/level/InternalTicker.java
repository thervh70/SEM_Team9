package nl.tudelft.ti2206.group9.level;

import java.time.Instant;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import nl.tudelft.ti2206.group9.ShaftEscape;
import nl.tudelft.ti2206.group9.gui.GameScene;

/**
 * This thread handles the ticks of the internal system. On each tick, the track
 * is moved. This task should be active during a "run".
 *
 * @author Maarten
 *
 */
@SuppressWarnings("restriction")
public final class InternalTicker extends TimerTask {

	/** Amount of nanoseconds in a second, 10<sup>9</sup>. */
	public static final int E9 = 1000000000;
	/** A million, 10<sup>6</sup>. */
	public static final int E6 = 1000000;

	/** Amount of frames per second. */
	public static final int FPS = 60;

	/** Whether the ticks are being run. */
	private static boolean running;
	/** Time at which next tick will be scheduled. */
	private static Instant scheduleTime;
	/** Amount of nanoseconds between each frame, assuming FPS is constant. **/
	public static final int NANOS_PER_TICK = E9 / FPS;

	/** The timer that schedules the TimerTask is stored in the instance. */
	private final Timer timer;

	/**
	 * Default private constructor.
	 * @param t Timer given to the internal ticker
	 */
	private InternalTicker(final Timer t) {
		super();
		timer = t;
	}

	/**
	 * Thread method.
	 */
	public void run() {
		Platform.runLater(new Runnable() {
			public void run() {
				synchronized (ShaftEscape.TICKER_LOCK) {
					final Timer newTimer = new Timer();

					try {
						InternalTicker.this.step(); // First, perform tick.
						// Then, kill the timer that scheduled the task.
						if (timer != null) {
							timer.cancel();
						}
					} finally {
						if (running) {
							scheduleTime = scheduleTime.plusNanos(
									NANOS_PER_TICK);
							newTimer.schedule(new InternalTicker(newTimer),
									Date.from(scheduleTime));
						} else {
							newTimer.cancel();
						}
					}
				}
			}
		});
	}

	/**
	 * Step is executed for every tick in the system. It stops if the player
	 * dies.
	 */
	private void step() {
		State.getTrack().step();

		if (!State.getTrack().getPlayer().isAlive()) {
			State.checkHighscore();
			GameScene.stopTickers();
			GameScene.showDeathMenu();
		}
	}

	/**
	 * Start the InternalTicker. Will run until {@link #stop()} is called.
	 */
	public static void start() {
		scheduleTime = Instant.now();
		final InternalTicker intern = new InternalTicker(null);
		new Thread(intern).start();
		running = true;
	}

	/** Stops the InternalTicker. */
	public static void stop() {
		running = false;
	}

	/**
	 * Indicates whether the InternalTicker is running or not.
	 * @return running true if ticking, false if not.
	 */
	public static boolean isRunning() {
		return running;
	}

}
