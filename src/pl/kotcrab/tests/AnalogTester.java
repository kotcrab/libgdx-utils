package pl.kotcrab.tests;

import pl.kotcrab.core.Analog;
import pl.kotcrab.core.Touch;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnalogTester
{
	public static void main(String[] args)
	{
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "AnalogTester";
		cfg.useGL20 = true;
		cfg.width = 800;
		cfg.height = 480;
		
		new LwjglApplication(new AnalogTesterApp(), cfg);
	}
}

class AnalogTesterApp implements ApplicationListener
{
	private SpriteBatch batch;
	private OrthographicCamera camera;
	
	private Texture analogBase;
	private Texture analogKnob;
	private BitmapFont arial;
	
	private Analog leftAnalog;
	private Analog rightAnalog;
	
	@Override
	public void create()
	{
		batch = new SpriteBatch();
		camera = new OrthographicCamera(800, 480);
		camera.position.x = 400;
		camera.position.y = 240;
		camera.update();
		Touch.setCamera(camera);
		batch.setProjectionMatrix(camera.combined);
		
		analogBase = new Texture(Gdx.files.internal("assets/gfx/analog_base.png"));
		analogKnob = new Texture(Gdx.files.internal("assets/gfx/analog_knob.png"));
		arial = new BitmapFont(Gdx.files.internal("assets/font/arial.fnt"));
		
		leftAnalog = new Analog(new TextureRegion(analogBase), new TextureRegion(analogKnob), 50, 50);
		rightAnalog = new Analog(new TextureRegion(analogBase), new TextureRegion(analogKnob), 550, 50);
	}
	
	@Override
	public void render()
	{
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
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
	public void dispose()
	{
		analogBase.dispose();
		analogKnob.dispose();
		arial.dispose();
		
		leftAnalog.dispose();
		rightAnalog.dispose();
	}
	
	//@formatter:off
	@Override public void pause(){}
	@Override public void resume(){}
	@Override public void resize(int width, int height){}
	//@formatter:on
	
}
