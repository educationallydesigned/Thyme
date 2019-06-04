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

import com.badlogic.gdx.scenes.scene2d.Actor;
import ml.educationallydesigned.thyme.Thyme;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import ml.educationallydesigned.thyme.core.windows.*;
import ml.educationallydesigned.thyme.util.*;
import ml.educationallydesigned.thyme.core.screens.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Game that contains all the methods that all levels have.
 *
 * @author Theodore Preduta
 * @author Larry Yuan
 * @version 1.0
 */
public class GameLevel implements Screen {
	protected Thyme game;
	protected Stage stage;
	protected List<Task> tasks;
	protected int currentTask;
	protected Queue<Actor> windows;
	protected boolean scoreable;

	/**
	 * Initiates the class with the game.
	 *
	 * @param game the main game object
	 */
	public GameLevel(Thyme game) {
		this.game = game;
		game.setScreen(this);
		scoreable = false;
	}

	/**
	 * Called when this screen becomes the current screen for a {@link Game}.
	 */
	@Override
	public void show() {
		stage = new Stage();
		game.setInputProcessor(stage);
		tasks = new ArrayList<Task>();
		windows = new LinkedList<Actor>();
		currentTask = 0;
	}

	/**
	 * Called when the screen should render itself.
	 *
	 * @param delta The time in seconds since the last render.
	 */
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(delta);
		stage.draw();
	}

	/**
	 * @param width
	 * @param height
	 * @see ApplicationListener#resize(int, int)
	 */
	@Override
	public void resize(int width, int height) {
	}

	/**
	 * @see ApplicationListener#pause()
	 */
	@Override
	public void pause() {
	}

	/**
	 * @see ApplicationListener#resume()
	 */
	@Override
	public void resume() {
	}

	/**
	 * Called when this screen is no longer the current screen for a {@link Game}.
	 */
	@Override
	public void hide() {
	}

	/**
	 * Called when this screen should release all resources.
	 */
	@Override
	public void dispose() {
	}

	/**
	 * Submits a set of answers to the current task, increments the current task
	 * if necessary, and updates windows.
	 *
	 * @param      givenAnswers  The given answers.
	 */
	public void submit(String[] givenAnswers) {
		if (tasks.get(currentTask).submit(givenAnswers)) {
			currentTask++;
			if (currentTask < tasks.size()) {
				resetWindows();
				tasks.get(currentTask).start();
			} else {
				if (scoreable)
					enterScore(calcScore());
				game.setScreen(new HomeScreen(game));
			}
		} else
			resetWindows();
	}

	/**
	 * Calculates the score once this level is done.
	 *
	 * @return     0
	 */
	private int calcScore() {
		return 0;
	}

	/**
	 * Add the score to the scoreboard
	 *
	 * @param      score  The score of this game.
	 */
	private void enterScore(int score) {
		// TODO
	}

	/**
	 * Opens new windows one a teask is completed, used to reset TextEditor, and TrackerWindows.
	 */
	private void resetWindows() {
		// close windows
		while (!windows.isEmpty())
			windows.remove().remove();

		TrackerWindow tracker = new TrackerWindow(tasks.get(currentTask));
		TextEditorWindow editor = new TextEditorWindow(tasks.get(currentTask), this);
		stage.addActor(tracker);
		windows.add(tracker);
		stage.addActor(editor);
		windows.add(editor);
	}
}