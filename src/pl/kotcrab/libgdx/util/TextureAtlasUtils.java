
package pl.kotcrab.libgdx.util;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

public class TextureAtlasUtils {

	/** Split TextureAtlas into single individual and saves them in output directory.
	 * @param atlas to be split
	 * @param outputDirectory directory were files will be saved, must be empty */
	public static void splitIntoFiles (TextureAtlas atlas, FileHandle outputDirectory) {
		if (outputDirectory.exists() == false) throw new IllegalStateException("Output directory does not exist");

		if (outputDirectory.isDirectory() == false || outputDirectory.list().length > 0)
			throw new IllegalStateException("Output is not a directory or output is not empty!");

		Array<AtlasRegion> regions = atlas.getRegions();

		SpriteBatch sb = new SpriteBatch();

		for (AtlasRegion region : regions) {
			Sprite s = atlas.createSprite(region.name);

			int width = (int)s.getWidth();
			int height = (int)s.getHeight();

			Matrix4 projectionMatrix = new Matrix4();
			projectionMatrix.setToOrtho2D(0, -height, width, height).scale(1, -1, 1);
			sb.setProjectionMatrix(projectionMatrix);

			FrameBuffer fb = new FrameBuffer(Pixmap.Format.RGBA8888, width, height, false);
			fb.begin();

			sb.begin();
			sb.draw(s, 0, 0, width, height);
			sb.end();

			Pixmap pm = ScreenUtils.getFrameBufferPixmap(0, 0, width, height);

			fb.end();

			FileHandle outputPNG = outputDirectory.child(region.name + ".png");
			PixmapIO.writePNG(outputPNG, pm);

			fb.dispose();
		}

		sb.dispose();
	}
}
