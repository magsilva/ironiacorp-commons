/*
This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

Copyright (C) 2007 Marco Aurelio Graciotto Silva <magsilva@gmail.com>
*/

package com.ironiacorp.commons.imaging;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public final class Utils
{
	public static BufferedImage mkThumbImage(BufferedImage orig, int thumbW)
	{
		double origRatio = (double)orig.getWidth() / (double)orig.getHeight();
		int thumbH = (int)(thumbW * origRatio);
		BufferedImage ret = new BufferedImage(thumbW, thumbH, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = ret.createGraphics();
		g.drawImage(orig, 0, 0, thumbW, thumbH, null);
		g.dispose();
		return ret;
	}
	
	public static void scale(String inputFilename, String outputFilename, String fileFormat, int scale) throws IOException
	{
		BufferedImage img = ImageIO.read(new File(inputFilename));
		int thumbW = img.getWidth() * scale;
		int thumbH = img.getHeight() * scale;
		BufferedImage ret = new BufferedImage(thumbW, thumbH, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = ret.createGraphics();
		g.drawImage(img, 0, 0, thumbW, thumbH, null);
		g.dispose();
		ImageIO.write(ret, fileFormat, new File(outputFilename));	
	}
}
