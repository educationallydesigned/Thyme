/*
	Thyme is an educational game to assist teenagers in time management, and tracking.
	Copyright (C) 2019 Theodore Preduta, Larry Yuan

	This program is free software: you can redistribute it and/or modify
	it under the terms of the GNU Affero General Public License as published
	by the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.

	This program is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU Affero General Public License for more details.

	You should have received a copy of the GNU Affero General Public License
	along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/

package ml.educationallydesigned.thyme.core.levels;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import ml.educationallydesigned.thyme.Thyme;
import ml.educationallydesigned.thyme.core.windows.SocialMediaWindow;
import ml.educationallydesigned.thyme.core.windows.TrackerWindow;
import ml.educationallydesigned.thyme.util.task.Survey;
import ml.educationallydesigned.thyme.util.task.Task;
import ml.educationallydesigned.thyme.util.task.TaskGenerator;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

/**
 * Class to implement the third game mode for the Thyme video game.
 * <b>Time Spent:</b>
 * <ul>
 * <li>Theodore - 30 min</li>
 * <li>Larry - 5 min </li>
 * </ul>
 *
 * @author Theodore Preduta
 * @author Larry Yuan
 * @version 1.1
 */
public class EscapeRoom extends GameLevel {
	/** the background image used */
	private Texture background;
	/** The estimates of the amount of time something will take */
	private int[] estimates;

	/**
	 * Constructs the object.
	 *
	 * @param game The main game object
	 */
	public EscapeRoom(Thyme game) {
		super(game);
		background = new Texture(Gdx.files.internal("backgrounds/two.png"));
	}

	/**
	 * Called when this screen becomes the current screen for a {@link Game}.
	 */
	@Override
	public void show() {
		// call GameLevel's show to initialize protected variables
		super.show();

		tasks.add(TaskGenerator.generateTask());
		tasks.add(TaskGenerator.generateTask());

		// prepare survey
		String[] questions = new String[tasks.size()];
		for (int i = 0; i < questions.length; i++) {
			Task current = tasks.get(i);
			questions[i] = current.getTitle() + "(" + current.getMinPassPercentage() + " - " + current.getQuestions().length + ")";
		}
		tasks.add(0, new Survey("Planning Time!",
				"Enter the amount of time needed for each task. In the quiz window, you are provided the name of the quiz. In brackets, you are provided the minimum pass percentage, and the number of questions. Your estimates should be in seconds.",
				questions, this));

		// open the windows
		tracker = new TrackerWindow(tasks.get(currentTask), this);
		stage.addActor(tracker);

		// start current task
		tasks.get(currentTask).start();

	}

	/**
	 * Renders the desktop with a custom background image
	 */
	@Override
	void renderDesktop() {
		stage.getBatch().begin();
		stage.getBatch().draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		stage.getBatch().end();
	}

	/**
	 * Adds estimate times.
	 *
	 * @param estimates The estimate times.
	 */
	public void addEstimates(int[] estimates) {
		this.estimates = estimates;
	}

	/**
	 * Calculates the score once this level is done.
	 *
	 * @return the score for the escape room.
	 */
	@Override
	protected int calcScore() {
		int totalDiff = 0;
		for (int i = 0; i < estimates.length; i++) {
			totalDiff += Math.abs(estimates[i] * 100 - tasks.get(i + 1).getTime() / 10);
		}

		return Math.max(0, 10000 - totalDiff);
	}

}