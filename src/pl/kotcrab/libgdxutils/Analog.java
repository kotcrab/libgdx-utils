/*******************************************************************************
 * Copyright 2012-2013 Pawel Pastuszak
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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Disposable;

/** Libgdx analog implementation. Max number of analogs is 5, increase pointer tab size to support more. Analog must be disposed.
 * 
 * @author Pawel Pastuszak
 * @see AnalogTester */
public class Analog implements Disposable {
	private static final int INVALID_POINTER = 15; // id pointera ktory teoretycznie jest bledny(urzadzanie nie obsluga wiecej niz
// 10(narazie) a sam android nie wiecej niz 20(przynajmniej w 2.3.5))
	private static final boolean[] pointerTab = new boolean[5]; // tablica uzywanych paclow wspolna dla wszyskich klas(moze
// obsluzyc 5 analogw naraz(latwo ziwkeszyc))

	private Sprite base; // baza analoga
	private Sprite knob; // grzybek analoga

	private boolean updating;
	private int activePointer;

	public Analog (TextureRegion baseTex, TextureRegion knobTex, float baseX, float baseY) {
		base = new Sprite(baseTex);
		knob = new Sprite(knobTex);

		this.base.setPosition(baseX, baseY);

		float knobX = this.base.getX() + this.base.getWidth() / 2 - this.knob.getWidth() / 2; // obliczenie pozycji poczatkowej
		float knobY = this.base.getY() + this.base.getHeight() / 2 - this.knob.getHeight() / 2;
		this.knob.setPosition(knobX, knobY);

		activePointer = INVALID_POINTER; // ustawienie aktualnego palca na bledny
	}

	public void render (SpriteBatch batch) {
		base.draw(batch);
		knob.draw(batch);
	}

	public void update () {
		if (updating == false) // jezeli anlog nie jest updatedowany
		{
			for (int i = 0; i < pointerTab.length; i++) {
				if (pointerTab[i] == true) // jezeli palec jest juz uzywany pomin
				{
					continue;
				}

				if (Gdx.input.isTouched(i) == true) // palac nie w tablicy a ekran dotkniety
				{
					activePointer = i; // zapisanie palca

					// sprawdzanie czy ten palec jest na analogu
					if (base.getBoundingRectangle().contains(Pointer.calcX(Gdx.input.getX(activePointer)),
						Pointer.calcY(Gdx.input.getY(activePointer)))) {
						pointerTab[i] = true; // zapisanie dancyh
						updating = true; // aktuazliwanie analoga
						break; // przerawanie petli
					} else
						activePointer = INVALID_POINTER; // palec nie na analogu, ustaiwenie na bledny
				}
			}
		}

		float x = Pointer.calcX(Gdx.input.getX(activePointer)); // pobranie prawdidlowych danych
		float y = Pointer.calcY(Gdx.input.getY(activePointer));

		if (updating == true && Gdx.input.isTouched(activePointer) == true) // aktualizawanie analoga, ekran wciaz dotykany
		{
			// wspolrzedne
			x = x - base.getX();
			y = y - base.getY();

			updateControlKnob(x, y); // update grzybka
		}

		if (updating == true && Gdx.input.isTouched(activePointer) == false) // aktualizawanie analoga, ekran nie dotykany
		{
			pointerTab[activePointer] = false; // palac juz nie uzywany
			activePointer = INVALID_POINTER; // ustawienie na bledny
			updating = false;
		}

		if (updating == false) onUpdateControlKnob(0, 0); // grzybek na 0
	}

	// obliczenie pozycji
	private void updateControlKnob (float localTocuhX, float localTouchY) {
		float relativeX = Math.min(base.getWidth(), localTocuhX) / base.getWidth() - 0.5f;
		float relativeY = Math.min(base.getHeight(), localTouchY) / base.getHeight() - 0.5f;

		if (relativeX * relativeX + relativeY * relativeY <= 0.25f) {
			onUpdateControlKnob(relativeX, relativeY);
		} else {
			float angleRad = MathUtils.atan2(relativeY, relativeX);
			onUpdateControlKnob((float)Math.cos(angleRad) * 0.5f, (float)Math.sin(angleRad) * 0.5f);
		}
	}

	private void onUpdateControlKnob (float pRelativeX, float pRelativeY) {
		float x = base.getX() + base.getWidth() / 2 - knob.getWidth() / 2 + pRelativeX * base.getWidth() * base.getScaleX();
		float y = base.getY() + base.getHeight() / 2 - knob.getHeight() / 2 + pRelativeY * base.getHeight() * base.getScaleY();

		knob.setPosition(x, y);
	}

	/** Odblokowyuje uzywamy palec. Musi zostac wywoalne po skonczoeniu uzywania klasy, inaczej aktualny palec caly czas bedzie
	 * zablokowany */
	@Override
	public void dispose () {
		if (updating == true) pointerTab[activePointer] = false;
	}

	public boolean isUpdating () {
		return updating;
	}

	public Sprite getBase () // zwaraca baze
	{
		return base;
	}

	public Sprite getKnob () // zwaraca grzybek
	{
		return knob;
	}

	public float getKnobXLocal () // zwaraca lokalna pozycje grzybke X
	{
		return knob.getX() - base.getX() - base.getWidth() / 4;
	}

	public float getKnobYLocal () // zwaraca lokalna pozycje grzybke Y
	{
		return knob.getY() - base.getY() - base.getHeight() / 4;
	}

	public int getPointer () // zwraca aktualne id palca
	{
		return activePointer;
	}

	public void setScale (float scaleXY) // ustawia skale
	{
		base.setScale(scaleXY);
		knob.setScale(scaleXY);
	}

	public void setAlpha (float alpha) // usawia alphe
	{
		base.setColor(base.getColor().r, base.getColor().g, base.getColor().b, alpha);
		knob.setColor(knob.getColor().r, knob.getColor().g, knob.getColor().b, alpha);
	}
}
