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

package pl.kotcrab.libgdx.util;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

/** Empty implementation of ContactListener (box2d)
 * 
 * @author Pawel Pastuszak */
public class ContactAdapter implements ContactListener {
	@Override
	public void beginContact (Contact contact) {
	}

	@Override
	public void endContact (Contact contact) {
	}

	@Override
	public void preSolve (Contact contact, Manifold oldManifold) {
	}

	@Override
	public void postSolve (Contact contact, ContactImpulse impulse) {
	}
}
