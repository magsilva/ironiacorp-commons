package com.ironiacorp.imaging.png.decoder;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.ironiacorp.imaging.ImageDecoder;

/**
 * Java 2D PNG decoder.
 */
public class JclPngDecoder implements ImageDecoder
{
	public BufferedImage decode(byte[] data)
	{
		return decode(new ByteArrayInputStream(data));
	}

	public BufferedImage decode(File file)
	{
		try {
			BufferedImage image =  decode(new FileInputStream(file));
			return image;
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	public BufferedImage decode(InputStream is)
	{
		try {
			return ImageIO.read(is);
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
}
