package com.ironiacorp.imaging.png.decoder;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

import com.ironiacorp.imaging.ImageDecoder;

/**
 * JavaPNG PNG decoder (http://javapng.googlecode.com/)
 */
public class JavaPngDecoder implements ImageDecoder
{
	private com.sixlegs.png.PngImage decoder;

	public JavaPngDecoder()
	{
		decoder = new com.sixlegs.png.PngImage();
	}

	public BufferedImage decode(byte[] data)
	{
		try {
			return decoder.read(new ByteArrayInputStream(data), true);
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
}
