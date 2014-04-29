/*******************************************************************************
 * Copyright 2014 Pawel Pastuszak
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

package pl.kotcrab.libgdxutils.util;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;

public class CameraUtils {
	public static Rectangle calcCameraBoundingRectangle (OrthographicCamera camera) {
		float cameraWidth = camera.viewportWidth * camera.zoom;
		float cameraHeight = camera.viewportHeight * camera.zoom;

		float cameraX = camera.position.x - cameraWidth / 2;
		float cameraY = camera.position.y - cameraHeight / 2;

		return new Rectangle(cameraX, cameraY, cameraWidth, cameraHeight);
	}
}
