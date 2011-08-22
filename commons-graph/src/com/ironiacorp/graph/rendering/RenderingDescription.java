/*
 * Copyright (C) 2011 Marco Aurélio Graciotto Silva <magsilva@ironiacorp.com>
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
 */

package com.ironiacorp.graph.rendering;

import java.awt.Color;
import java.awt.Shape;

import com.ironiacorp.graph.rendering.color.ColorScale;

public class RenderingDescription
{
	private BoundingBox boundingBox;
	
	private Position position;
	
	private Shape shape;
	
	private Color foregroundColor;

	private Color backgroundColor;

	private Color borderColor;
	
	private ColorScale colorScale;
	
	private boolean selected;

	public Position getPosition()
	{
		return position;
	}

	public void setPosition(Position position)
	{
		this.position = position;
	}

	public Shape getShape()
	{
		return shape;
	}

	public void setShape(Shape shape)
	{
		this.shape = shape;
	}

	public Color getForegroundColor()
	{
		return foregroundColor;
	}

	public void setForegroundColor(Color foregroundColor)
	{
		this.foregroundColor = foregroundColor;
	}

	public Color getBackgroundColor()
	{
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor)
	{
		this.backgroundColor = backgroundColor;
	}

	public Color getBorderColor()
	{
		return borderColor;
	}

	public void setBorderColor(Color borderColor)
	{
		this.borderColor = borderColor;
	}

	public ColorScale getColorScale()
	{
		return colorScale;
	}

	public void setColorScale(ColorScale colorScale)
	{
		this.colorScale = colorScale;
	}

	public boolean isSelected()
	{
		return selected;
	}

	public void setSelected(boolean selected)
	{
		this.selected = selected;
	}
	
	public BoundingBox getBoundingBox()
	{
		return boundingBox;
	}

	public void setBoundingBox(BoundingBox boundingBox)
	{
		this.boundingBox = boundingBox;
	}

}
