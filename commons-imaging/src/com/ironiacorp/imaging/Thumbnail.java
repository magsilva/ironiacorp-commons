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

package com.ironiacorp.imaging;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public final class Thumbnail implements Image
{
	private Image image;
	
	private BufferedImage thumbnail;
	
	private double ratio = 0.5;
	
	public Thumbnail(Image image)
	{
		this.image = image;
	}

	@Override
	public BufferedImage getBufferedImage()
	{
		if (thumbnail == null) {
			createThumbImage();
		}
		return thumbnail;
	}

	@Override
	public int getBitDepth()
	{
		return image.getBitDepth();
	}

	@Override
	public int getWidth()
	{
		return (int) (image.getWidth() * ratio);
	}

	@Override
	public int getHeight()
	{
		return (int) (image.getHeight() * ratio);
	}

	@Override
	public double getResolutionX()
	{
		return image.getResolutionX() / ratio;
	}

	@Override
	public double getResolutionY()
	{
		return image.getResolutionY() / ratio;
	}
	
	private void createThumbImage()
	{
		Graphics2D g;
		int thumbH = (int) (ratio * image.getWidth());
		int thumbW = (int) (ratio * image.getHeight());
		
		thumbnail = new BufferedImage(thumbW, thumbH, BufferedImage.TYPE_INT_RGB);
		g = thumbnail.createGraphics();
		g.drawImage(image.getBufferedImage(), 0, 0, thumbW, thumbH, null);
		g.dispose();
	}
}
