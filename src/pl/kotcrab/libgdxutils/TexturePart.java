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

package pl.kotcrab.libgdxutils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/** Allow to draw a part of texture
 * @author Pawel Pastuszak */
public class TexturePart {
	Texture tex;
	Vector2 position;

	// Target Dimension of image

	int targetWidth;
	int targetHeight;

	// Src Dimensions of Image

	int srcWidth;
	int srcHeight;
	int srcX;
	int srcY;

	// Ratio of dimension of target and source

	float srcTargetRatioX;
	float srcTargetRatioY;

	// ImagePart variables with values between 0-100 to draw part of image

	int startPercentX;
	int endPercentX;
	int startPercentY;
	int endPercentY;

	int clipWidth;
	int clipHeight;

	int clipSrcWidth;
	int clipSrcHeight;

	public TexturePart (TextureRegion reg, float x, float y) {

		tex = reg.getTexture();
		position = new Vector2(x, y);
		srcX = reg.getRegionX();
		srcY = reg.getRegionY();
		srcWidth = reg.getRegionWidth();
		srcHeight = reg.getRegionHeight();
		clipSrcWidth = srcWidth;
		clipSrcHeight = srcHeight;
		startPercentX = 0;
		startPercentY = 0;
		endPercentX = 100;
		endPercentY = 100;
		SetTargetDimension(srcWidth, srcHeight);
	}

	public void SetTargetDimension (int targetWidth, int targetHeight) {
		this.targetWidth = targetWidth;
		this.targetHeight = targetHeight;
		clipWidth = targetWidth;
		clipHeight = targetHeight;
		srcTargetRatioX = (float)targetWidth / (float)srcWidth;
		srcTargetRatioY = (float)targetHeight / (float)srcHeight;
	}

	public void SetStart (int x, int y) {
		startPercentX = x;
		startPercentY = y;
	}

	public void SetEnd (int x, int y) {
		endPercentX = x;
		endPercentY = y;
	}

	public void draw (SpriteBatch sp) {
		clipSrcWidth = (int)(Math.abs(startPercentX - endPercentX) / 100f * srcWidth);
		clipSrcHeight = (int)(Math.abs(startPercentX - endPercentY) / 100f * srcHeight);
		int startX = srcX + (int)((float)startPercentX / 100f * (float)srcX);
		int startY = srcY + (int)((float)startPercentY / 100f * (float)srcY);
		clipWidth = (int)(srcTargetRatioX * clipSrcWidth);
		clipHeight = (int)(srcTargetRatioY * clipSrcHeight);

		sp.draw(tex, position.x - targetWidth / 2, position.y - targetHeight / 2, targetWidth / 2, targetHeight / 2, clipWidth,
			clipHeight, 1, 1, 0, startX, startY, clipSrcWidth, clipSrcHeight, false, true);
	}

}
