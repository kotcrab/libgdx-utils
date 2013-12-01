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

package pl.kotcrab.core.accessor;

import aurelienribon.tweenengine.TweenAccessor;

import com.badlogic.gdx.graphics.g2d.Sprite;

//For Universal Tween Engine

public class AccessorSprite implements TweenAccessor<Sprite>
{
	
	public static final int POSITION_X = 1;
	public static final int POSITION_Y = 2;
	public static final int POSITION_XY = 3;
	public static final int ALPHA = 4;
	public static final int SCALE = 5;
	
	// TweenAccessor implementation
	
	@Override
	public int getValues(Sprite target, int tweenType, float[] returnValues)
	{
		switch (tweenType)
		{
		case POSITION_X:
			returnValues[0] = target.getX();
			return 1;
		case POSITION_Y:
			returnValues[0] = target.getY();
			return 1;
		case POSITION_XY:
			returnValues[0] = target.getX();
			returnValues[1] = target.getY();
			return 2;
		case ALPHA:
			returnValues[0] = target.getColor().a;
			return 1;
		case SCALE:
			returnValues[0] = target.getScaleX();
			return 1;
		default:
			assert false;
			return -1;
		}
	}
	
	@Override
	public void setValues(Sprite target, int tweenType, float[] newValues)
	{
		switch (tweenType)
		{
		case POSITION_X:
			target.setX(newValues[0]);
			break;
		case POSITION_Y:
			target.setY(newValues[0]);
			break;
		case POSITION_XY:
			target.setX(newValues[0]);
			target.setY(newValues[1]);
			break;
		case ALPHA:
			target.setColor(1, 1, 1, newValues[0]);
			break;
		case SCALE:
			target.setScale(newValues[0]);
			break;
		default:
			assert false;
			break;
		}
	}
	
}