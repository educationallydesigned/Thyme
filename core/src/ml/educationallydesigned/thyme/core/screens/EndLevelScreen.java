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

package ml.educationallydesigned.thyme.core.screens;

import com.badlogic.gdx.*;
import ml.educationallydesigned.thyme.Thyme;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kotcrab.vis.ui.widget.VisImage;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import ml.educationallydesigned.thyme.Thyme;
import ml.educationallydesigned.thyme.core.levels.*;
import ml.educationallydesigned.thyme.util.task.Task;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.*;

import java.util.List;

/**
 * Class for end level screen. THis screen is displayed upon the completion the
 * end of each level
 * <b>Time Spent:</b>
 * <ul>
 * <li>Theodore - 60 min</li>
 * <li>Larry - 0 min</li>
 * </ul>
 *
 * @author Theodore Preduta
 * @author Larry Yuan
 * @version 1.0
 */
public class EndLevelScreen implements Screen {
	private Thyme game;
	private Stage stage;
	private GameLevel currentLevel;
	private List<Task> tasks;
	private int points;

	/**
	 * Constructs the object.
	 *
	 * @param      game          The game that is running these classes
	 * @param      currentLevel  The current game level
	 * @param      tasks         The tasks that should be analyzed.
	 */
	public EndLevelScreen(Thyme game, GameLevel currentLevel, List<Task> tasks, int points) {
		this.game = game;
		this.currentLevel = currentLevel;
		this.tasks = tasks;
		this.points = points;
		game.setScreen(this);
	}

	/**
	 * Called when this screen becomes the current screen for a {@link Game}.
	 */
	@Override
	public void show() {
		// make the stage
		stage = new Stage();
		game.setInputProcessor(stage);
		// make table
		VisTable table = new VisTable();
		table.setFillParent(true);
		// make title
		VisLabel gameTitle = new VisLabel("Level Completed");
		table.add(gameTitle).padBottom(20);
		table.row();
		// add the stats
		for (Task t : tasks) {
			VisLabel title = new VisLabel(t.getTitle());
			table.add(title).width(100);

			VisLabel time = new VisLabel(t.getTime() / 1000 + "s");
			table.add(time).width(100);

			VisLabel accuracy = new VisLabel(t.getAttemptPercentage() + "%");
			table.add(accuracy).width(100).row();
		}
		// add buttons
		VisTextButton continueGame = new VisTextButton("Continue");
		table.add(continueGame).width(500).height(80).padBottom(20);
		table.row();

		VisTextButton exit = new VisTextButton("Main Menu");
		table.add(exit).width(500).height(80).padBottom(20);
		table.row();

		// add listeners
		continueGame.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				GameLevel current = EndLevelScreen.this.getLevel();
				if (current instanceof DeficiencyRoom)
					new PanicRoom(game);
				else if (current instanceof PanicRoom)
					new EscapeRoom(game);
				else
					new NamePromptScreen(game);
			}
		});

		exit.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new HomeScreen(game));
			}
		});

		VisLabel time = new VisLabel("Points Earned");
		table.add(time).width(100);

		VisLabel accuracy = new VisLabel(points + "");
		table.add(accuracy).width(100).row();

		stage.addActor(table);
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
	 * Gets the level.
	 *
	 * @return     The level that this was called from.
	 */
	public GameLevel getLevel() {
		return currentLevel;
	}
}