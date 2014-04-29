/*******************************************************************************
 * Copyright 2013-2014 Pawel Pastuszak
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

package pl.kotcrab.libgdxutils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;

/** Text that you can scale, rotate, change color itp. Supports distance field fonts
 * 
 * @author Pawel Pastuszak
 * @version 1.2 */
public class Text {
	private float gx = 0, gy = 0;
	private float originX = 0, originY = 0;
	private float scaleX = 1, scaleY = 1;
	private float rotation = 0;

	private BitmapFontCache cache;
	private TextBounds textBounds;
	private Rectangle boundingRectangle;

	private boolean autoSetOriginToCenter;

	private Matrix4 translationMatrix;

	public Text (BitmapFont bitmapFont) {
		this(bitmapFont, null);
	}

	public Text (BitmapFont bitmapFont, String text) {
		this(bitmapFont, text, 0, 0);
	}

	public Text (BitmapFont bitmapFont, String text, float x, float y) {
		this(bitmapFont, text, x, y, false);
	}

	public Text (BitmapFont bitmapFont, String text, float x, float y, boolean autoSetOriginToCenter) {
		this.autoSetOriginToCenter = autoSetOriginToCenter;
		this.gx = x;
		this.gy = y;

		translationMatrix = new Matrix4();
		cache = new BitmapFontCache(bitmapFont);
		if (text != null)
			textBounds = cache.setText(text, 0, 0);
		else
			textBounds = cache.getBounds();

		if (autoSetOriginToCenter == true) setOriginCenter();

		translate();
	}

	public void draw (SpriteBatch spriteBatch) {
		Matrix4 oldMatrix = spriteBatch.getTransformMatrix().cpy();
		spriteBatch.setTransformMatrix(translationMatrix);
		cache.draw(spriteBatch);
		spriteBatch.setTransformMatrix(oldMatrix);
	}

	private void translate () {
		translationMatrix.idt();

		translationMatrix.translate(gx + originX, gy + originY, 0);
		translationMatrix.rotate(0, 0, 1, rotation);
		translationMatrix.scale(scaleX, scaleY, 1);
		translationMatrix.translate(-originX, -originY, 0);
		translationMatrix.translate(0, textBounds.height, 0);

		calculateBoundingRectangle();
	}

	private void textChanged () {
		if (autoSetOriginToCenter == true) setOriginCenter();

		translate();
	}

	private void calculateBoundingRectangle () {
		Polygon polygon = new Polygon(new float[] {0, 0, textBounds.width, 0, textBounds.width, textBounds.height, 0,
			textBounds.height});

		polygon.setPosition(gx, gy);
		polygon.setRotation(rotation);
		polygon.setScale(scaleX, scaleY);
		polygon.setOrigin(originX, originY);

		boundingRectangle = polygon.getBoundingRectangle();
	}

	/** Clears any cached glyphs and adds glyphs for the specified text.
	 * @see #addText(CharSequence, float, float, int, int) */
	public void setText (CharSequence str) {
		textBounds = cache.setText(str, 0, 0);
		textChanged();
	}

	/** Clears any cached glyphs and adds glyphs for the specified text.
	 * @see #addText(CharSequence, float, float, int, int) */
	public void setText (CharSequence str, float x, float y) {
		textBounds = cache.setText(str, x, y);
		textChanged();
	}

	/** Clears any cached glyphs and adds glyphs for the specified text.
	 * @see #addText(CharSequence, float, float, int, int) */
	public void setText (CharSequence str, float x, float y, int start, int end) {
		textBounds = cache.addText(str, x, y, start, end);
		textChanged();
	}

	/** Adds glyphs for the specified text.
	 * @see #addText(CharSequence, float, float, int, int) */
	public void addText (CharSequence str, float originX, float originY) {
		textBounds = cache.addText(str, originX, originY);
		textChanged();
	}

	/** Adds glyphs for the the specified text.
	 * @param x The x position for the left most character.
	 * @param y The y position for the top of most capital letters in the font (the {@link BitmapFont#getCapHeight() cap height
	 *           textChanged();}).
	 * @param start The first character of the string to draw.
	 * @param end The last character of the string to draw (exclusive).
	 * @return The bounds of the cached string (the height is the distance from y to the baseline). */
	public void addText (CharSequence str, float x, float y, int start, int end) {
		textBounds = cache.addText(str, x, y, start, end);
		textChanged();
	}

	public void setMultiLineText (CharSequence str) {
		textBounds = cache.addMultiLineText(str, 0, 0, 0, HAlignment.LEFT);
		textChanged();
	}

	/** Clears any cached glyphs and adds glyphs for the specified text, which may contain newlines (\n).
	 * @see #addMultiLineText(CharSequence, float, float, float, HAlignment) */
	public void setMultiLineText (CharSequence str, float x, float y) {
		textBounds = cache.addMultiLineText(str, x, y, 0, HAlignment.LEFT);
		textChanged();
	}

	/** Clears any cached glyphs and adds glyphs for the specified text, which may contain newlines (\n).
	 * @see #addMultiLineText(CharSequence, float, float, float, HAlignment) */
	public void setMultiLineText (CharSequence str, float x, float y, float alignmentWidth, HAlignment alignment) {
		textBounds = cache.addMultiLineText(str, x, y, alignmentWidth, alignment);
		textChanged();
	}

	/** Adds glyphs for the specified text, which may contain newlines (\n).
	 * @see #addMultiLineText(CharSequence, float, float, float, HAlignment) */
	public void addMultiLineText (CharSequence str, float x, float y) {
		textBounds = cache.addMultiLineText(str, x, y, 0, HAlignment.LEFT);
		textChanged();
	}

	/** Adds glyphs for the specified text, which may contain newlines (\n). Each line is aligned horizontally within a rectangle of
	 * the specified width.
	 * @param x The x position for the left most character.
	 * @param y The y position for the top of most capital letters in the font (the {@link BitmapFont#getCapHeight() cap height
	 *           textChanged();}).
	 * @param alignment The horizontal alignment of wrapped line.
	 * @return The bounds of the cached string (the height is the distance from y to the baseline of the last line). */
	public void addMultiLineText (CharSequence str, float x, float y, float alignmentWidth, HAlignment alignment) {
		textBounds = cache.addMultiLineText(str, x, y, alignmentWidth, alignment);
		textChanged();
	}

	/** Clears any cached glyphs and adds glyphs for the specified text, which may contain newlines (\n) and is automatically
	 * wrapped within the specified width.
	 * @see #addWrappedText(CharSequence, float, float, float, HAlignment) */
	public void setWrappedText (CharSequence str, float wrapWidth) {
		textBounds = cache.addWrappedText(str, 0, 0, wrapWidth, HAlignment.LEFT);
		textChanged();
	}

	/** Clears any cached glyphs and adds glyphs for the specified text, which may contain newlines (\n) and is automatically
	 * wrapped within the specified width.
	 * @see #addWrappedText(CharSequence, float, float, float, HAlignment) */
	public void setWrappedText (CharSequence str, float x, float y, float wrapWidth) {
		textBounds = cache.addWrappedText(str, x, y, wrapWidth, HAlignment.LEFT);
		textChanged();
	}

	/** Clears any cached glyphs and adds glyphs for the specified text, which may contain newlines (\n) and is automatically
	 * wrapped within the specified width.
	 * @see #addWrappedText(CharSequence, float, float, float, HAlignment) */
	public void setWrappedText (CharSequence str, float x, float y, float wrapWidth, HAlignment alignment) {
		textBounds = cache.addWrappedText(str, x, y, wrapWidth, alignment);
		textChanged();
	}

	/** Adds glyphs for the specified text, which may contain newlines (\n) and is automatically wrapped within the specified width.
	 * @see #addWrappedText(CharSequence, float, float, float, HAlignment) */
	public void addWrappedText (CharSequence str, float x, float y, float wrapWidth) {
		textBounds = cache.addWrappedText(str, x, y, wrapWidth, HAlignment.LEFT);
		textChanged();
	}

	/** Adds glyphs for the specified text, which may contain newlines (\n) and is automatically wrapped within the specified width.
	 * @param x The x position for the left most character.
	 * @param y The y position for the top of most capital letters in the font (the {@link BitmapFont#getCapHeight() cap height
	 *           textChanged();}).
	 * @param alignment The horizontal alignment of wrapped line.
	 * @return The bounds of the cached string (the height is the distance from y to the baseline of the last line). */
	public void addWrappedText (CharSequence str, float x, float y, float wrapWidth, HAlignment alignment) {
		textBounds = cache.addWrappedText(str, x, y, wrapWidth, alignment);
		textChanged();
	}

	public boolean isAutoSetOriginToCenter () {
		return autoSetOriginToCenter;
	}

	public void enableAutoSetOriginToCenter () {
		autoSetOriginToCenter = true;
	}

	public void disableAutoSetOriginToCenter () {
		autoSetOriginToCenter = false;
	}

	/** Removes all glyphs in the cache. */
	public void clearCache () {
		cache.clear();
	}

	public void setOriginCenter () {
		setOrigin(textBounds.width / 2, -textBounds.height / 2);
	}

	public void center (int screenWidth) {
		gx = (screenWidth - textBounds.width * scaleX) / 2;
		translate();
	}

	// Getter and setter
	public void setPosition (float x, float y) {
		this.gx = x;
		this.gy = y;

		translate();
	}

	public void setX (float x) {
		this.gx = x;

		translate();
	}

	public void setY (float y) {
		this.gy = y;

		translate();
	}

	public float getX () {
		return gx;
	}

	public float getY () {
		return gy;
	}

	public void setOrigin (float originX, float originY) {
		this.originX = originX;
		this.originY = originY;

		translate();
	}

	public float getOriginX () {
		return originX;
	}

	public float getOriginY () {
		return originY;
	}

	public void setScale (float scaleX, float scaleY) {
		this.scaleX = scaleX;
		this.scaleY = scaleY;

		translate();
	}

	public void setScale (float scaleXY) {
		scaleX = scaleXY;
		scaleY = scaleXY;

		translate();
	}

	public float getScaleX () {
		return scaleX;
	}

	public float getScaleY () {
		return scaleY;
	}

	public float getRotation () {
		return rotation;
	}

	public void setRotation (float rotation) {
		this.rotation = rotation;

		translate();
	}

	public Color getColor () {
		return cache.getColor();
	}

	public void setColor (float r, float g, float b, float a) {
		cache.setColor(new Color(r, g, b, a));
	}

	public void setColor (Color color) {
		cache.setColor(color);
	}

	public void setSize (float width, float height) {
		setScale(width / textBounds.width, height / textBounds.height);
	}

	public float getWidth () {
		return boundingRectangle.width;
	}

	public float getHeight () {
		return boundingRectangle.height;
	}

	public Rectangle getBoundingRectangle () {
		return boundingRectangle;
	}

}
