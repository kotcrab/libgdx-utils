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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;

/**
 * Class that allows drawing rectangles on game screen
 * 
 * @author Pawel Pastuszak
 * 
 */
public class RectangleDrawer extends InputAdapter
{
	private ShapeRenderer shapeRenderer;
	private Matrix4 matrix;
	
	private Rectangle currentRect = null;
	private Rectangle rectToDraw = null;
	private Rectangle previousRectDrawn = new Rectangle();
	
	private int drawingPointer = -1;
	
	public RectangleDrawer()
	{
		shapeRenderer = new ShapeRenderer();
		matrix = new Matrix4();
		matrix.setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		shapeRenderer.setProjectionMatrix(matrix);
		
		shapeRenderer.setColor(Color.GREEN);
	}
	
	public void setColor(Color color)
	{
		shapeRenderer.setColor(color);
	}
	
	public void render()
	{
		if(rectToDraw != null)
		{
			
			shapeRenderer.begin(ShapeType.Line);
			
			shapeRenderer.rect(rectToDraw.getX(), rectToDraw.getY(), rectToDraw.getWidth(), rectToDraw.getHeight());
			shapeRenderer.end();
		}
	}
	
	public void attachInputProcessor()
	{
		if(Gdx.input.getInputProcessor() == null)
		{
			Gdx.input.setInputProcessor(this);
			return;
		}
		
		if(Gdx.input.getInputProcessor() instanceof InputMultiplexer)
		{
			InputMultiplexer mul = (InputMultiplexer) Gdx.input.getInputProcessor();
			mul.addProcessor(this);
			Gdx.input.setInputProcessor(mul);
		}
		else
		{
			InputMultiplexer mul = new InputMultiplexer();
			mul.addProcessor(Gdx.input.getInputProcessor());
			mul.addProcessor(this);
			Gdx.input.setInputProcessor(mul);
		}
	}
	
	public void dispose()
	{
		shapeRenderer.dispose();
	}
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		if(button == Buttons.MIDDLE)
		{
			drawingPointer = pointer;
			int x = (int) Touch.calcX(screenX);
			int y = (int) Touch.calcY(screenY);
			currentRect = new Rectangle(x, y, 0, 0);
			updateDrawableRect();
		}
		
		return false;
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button)
	{
		if(drawingPointer == pointer)
		{
			drawingPointer = -1;
			Gdx.app.log("RectangleDrawer", String.format("X:%s Y:%s Width:%s Height:%s", rectToDraw.x, rectToDraw.y, rectToDraw.width, rectToDraw.height));
		}
		
		return false;
	}
	
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer)
	{
		if(drawingPointer == pointer)
		{
			int x = (int) Touch.calcX(screenX);
			int y = (int) Touch.calcY(screenY);
			currentRect.setSize(x - currentRect.x, y - currentRect.y);
			updateDrawableRect();
		}
		
		return false;
	}
	
	private void updateDrawableRect()
	{
		float x = currentRect.x;
		float y = currentRect.y;
		float width = currentRect.width;
		float height = currentRect.height;
		
		// Make the width and height positive, if necessary.
		if(width < 0)
		{
			width = 0 - width;
			x = x - width + 1;
			if(x < 0)
			{
				width += x;
				x = 0;
			}
		}
		if(height < 0)
		{
			height = 0 - height;
			y = y - height + 1;
			if(y < 0)
			{
				height += y;
				y = 0;
			}
		}
		
		// Update rectToDraw after saving old value.
		if(rectToDraw != null)
		{
			previousRectDrawn.set(rectToDraw.x, rectToDraw.y, rectToDraw.width, rectToDraw.height);
			rectToDraw.set(x, y, width, height);
		}
		else
		{
			rectToDraw = new Rectangle(x, y, width, height);
		}
	}
}
