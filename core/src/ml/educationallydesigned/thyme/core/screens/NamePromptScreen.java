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

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.VisTextField;
import ml.educationallydesigned.thyme.Thyme;
import ml.educationallydesigned.thyme.util.CenteredTable;
import ml.educationallydesigned.thyme.util.Dialog;

import java.io.IOException;

/**
 * Class for end level screen. THis screen is displayed upon the completion the
 * end of each level
 * <b>Time Spent:</b>
 * <ul>
 * <li>Theodore - 20 min</li>
 * <li>Larry - 10 min</li>
 * </ul>
 *
 * @author Theodore Preduta
 * @author Larry Yuan
 * @version 1.0
 */
public class NamePromptScreen implements Screen {
	/** the game that runs these */
	private Thyme game;
	/** the stage that holds information about the interface */
	private Stage stage;
	/** The name field */
	private VisTextField nameField;
	/** the background image */
	private Texture background;

	/**
	 * Constructs the object.
	 *
	 * @param game The game
	 */
	public NamePromptScreen(Thyme game) {
		this.game = game;
		game.setScreen(this);
	}

	/**
	 * Called when this screen becomes the current screen for a {@link Game}.
	 */
	@Override
	public void show() {
		background = new Texture(Gdx.files.internal("backgrounds/home.png"));
		// make the stage
		stage = new Stage();
		game.setInputProcessor(stage);
		// make table
		CenteredTable table = new CenteredTable();
		// make title
		VisLabel prompt = new VisLabel("Enter your name for the scoreboard");
		table.add(prompt).padBottom(20);
		table.row();
		// add name field
		nameField = new VisTextField();
		nameField.setAlignment(Align.center);
		table.add(nameField).padBottom(20).width(200).row();
		// add buttons
		VisTextButton submitButton = new VisTextButton("Submit");
		table.add(submitButton).width(500).height(80);
		table.row();
		// add listeners
		submitButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				VisTextField nameField = NamePromptScreen.this.getNameField();

				if (!nameField.getText().trim().equals(""))
					try {
						game.catScores(nameField.getText().trim().substring(0, Math.min(20, nameField.getText().trim().length())));
						Dialog.showDefaultDialog(stage, "Saved Score", "Successfully saved score");
						game.setScreen(new HomeScreen(game));
					} catch (IOException e) {
						Dialog.showErrorDialog(stage, "Failed to save score", e);
					}
			}
		});
		table.setWidth(550);
		table.setHeight(300);

		table.centerTable();
		stage.addActor(table);
	}

	/**
	 * Called when the screen should render itself.
	 *
	 * @param delta The time in seconds since the last render.
	 */
	@Override
	public void render(float delta) {
		stage.act(delta);
		stage.getBatch().begin();
		stage.getBatch().draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		stage.getBatch().end();
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
	 * Gets the name field.
	 *
	 * @return The name field.
	 */
	public VisTextField getNameField() {
		return nameField;
	}
}