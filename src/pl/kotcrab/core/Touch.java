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

package pl.kotcrab.core;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

/** Class for caluclating propper touch cordinates
 * 
 * @author Pawel Pastuszak */
public class Touch {
	private static OrthographicCamera camera;
	private static OrthographicCamera rawCamera;
	private static Vector3 calcVector;

	/** Prepares class for use */
	public static void setCamera (OrthographicCamera camera) {
		Touch.camera = camera;
		Touch.rawCamera = new OrthographicCamera(camera.viewportWidth, camera.viewportHeight);
		calcVector = new Vector3(0, 0, 0);
	}

	/** Return camera */
	public static OrthographicCamera getCamera () {
		return camera;
	}

	/** Return proper touch posistion using provided camera Call the {@link #setCamera(OrthographicCamera camera) setCamera} method
	 * before using.
	 * @param x form Gdx.input.getX() or event method */
	public static float calcX (float x) {
		calcVector.x = x;
		camera.unproject(calcVector);
		return calcVector.x;
	}

	/** Return proper touch posistion using provided camera Call the {@link #setCamera(OrthographicCamera camera) setCamera} method
	 * before using.
	 * @param y form Gdx.input.getY() or event method */
	public static float calcY (float y) {
		calcVector.y = y;
		camera.unproject(calcVector);
		return calcVector.y;
	}

	/** Return proper touch posistion using provided camera. This ignores camera rotation or zoom, this can be used for HUD and
	 * menus Call the {@link #setCamera(OrthographicCamera camera) setCamera} method before using.
	 * @param x form Gdx.input.getX() or event method */
	public static float calcRawX (float x) {
		calcVector.x = x;
		rawCamera.unproject(calcVector);
		return calcVector.x;
	}

	/** Return proper touch posistion using provided camera. This ignores camera rotation or zoom, this can be used for HUD and
	 * menus Call the {@link #setCamera(OrthographicCamera camera) setCamera} method before using.
	 * @param y form Gdx.input.getY() or event method */
	public static float calcRawY (float y) {
		calcVector.y = y;
		rawCamera.unproject(calcVector);
		return calcVector.y;
	}

}
