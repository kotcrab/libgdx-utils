package pl.kotcrab.libgdx.util.test;

import pl.kotcrab.libgdx.util.TextureAtlasUtils;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class TextureAtlasUtilsTest
{
	public static void main (String[] args) {
		LwjglApplicationConfiguration c = new LwjglApplicationConfiguration();
		c.width = 1;
		c.height = 1;
		
		new LwjglApplication(new SplitApp(), c);
	}
}

class SplitApp extends ApplicationAdapter {
	@Override
	public void create () {
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("gfx/uiskin.atlas"));
		TextureAtlasUtils.splitIntoFiles(atlas, Gdx.files.absolute("D:\\out\\"));
		
		Gdx.app.exit();
	}
}