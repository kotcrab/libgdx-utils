/*******************************************************************************
 * Copyright 2013 Pawel Pastuszak
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package pl.kotcrab.libgdxutils.tests;

import pl.kotcrab.libgdxutils.Analog;
import pl.kotcrab.libgdxutils.Pointer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnalogTester {
	public static void main (String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "AnalogTester";
		cfg.width = 800;
		cfg.height = 480;

		new LwjglApplication(new AnalogTestApp(), cfg);
	}
}

class AnalogTestApp implements ApplicationListener {
	private SpriteBatch batch;
	private OrthographicCamera camera;

	private Texture analogBase;
	private Texture analogKnob;
	private BitmapFont arial;

	private Analog leftAnalog;
	private Analog rightAnalog;

	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera(800, 480);
		camera.position.x = 400;
		camera.position.y = 240;
		camera.update();
		Pointer.setCamera(camera);
		batch.setProjectionMatrix(camera.combined);

		analogBase = new Texture(Gdx.files.internal("assets/gfx/analog_base.png"));
		analogKnob = new Texture(Gdx.files.internal("assets/gfx/analog_knob.png"));
		arial = new BitmapFont(Gdx.files.internal("assets/font/arial.fnt"));

		leftAnalog = new Analog(new TextureRegion(analogBase), new TextureRegion(analogKnob), 50, 50);
		rightAnalog = new Analog(new TextureRegion(analogBase), new TextureRegion(analogKnob), 550, 50);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		leftAnalog.update();
		rightAnalog.update();

		batch.begin();
		arial.draw(batch, "LeftAnalog: X:" + (int)leftAnalog.getKnobXLocal() + " Y:" + (int)leftAnalog.getKnobYLocal(), 10, 450);
		arial.draw(batch, "RightAnalog: X:" + (int)rightAnalog.getKnobXLocal() + " Y:" + (int)rightAnalog.getKnobYLocal(), 10, 430);

		leftAnalog.render(batch);
		rightAnalog.render(batch);
		batch.end();
	}

	@Override
	public void dispose () {
		analogBase.dispose();
		analogKnob.dispose();
		arial.dispose();

		leftAnalog.dispose();
		rightAnalog.dispose();
	}

	// @formatter:off
	@Override
	public void pause () {
	}

	@Override
	public void resume () {
	}

	@Override
	public void resize (int width, int height) {
	}
	// @formatter:on

}
