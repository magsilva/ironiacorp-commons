package com.ironiacorp.imaging.png;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.ironiacorp.imaging.BitmapImage;
import com.ironiacorp.imaging.ImageDecoder;
import com.ironiacorp.imaging.png.decoder.JclPngDecoder;
import com.ironiacorp.imaging.png.encoder.JclPngEncoder;
import com.ironiacorp.imaging.png.encoder.PngImageEncoder;

public class PngImage implements BitmapImage
{
	private PngImageEncoder encoder;
	
	private ImageDecoder decoder;
	
	private BufferedImage image;
	
	private double width;
	
	private double height;
	
	private double resolution;
	
	public PngImage(File imageFile)
	{
		try {
			Image image = ImageIO.read(imageFile);
			initializeEncoder();
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	
	public PngImage(BufferedImage image)
	{
		this.image = image;
		initializeEncoder();
	}

	private void initializeDecoder()
	{
		decoder = new JclPngDecoder();
	}

	private void initializeEncoder()
	{
		encoder = new JclPngEncoder();
		encoder.setCompressionLevel(9);
	}
	
	public double getWidth()
	{
		return image.getWidth();
	}


	public double getHeight()
	{
		return image.getHeight();
	}

	public boolean hasAlphaChannel()
	{
		if (image.getTransparency()  == BufferedImage.OPAQUE) {
			return false;
		} else {
			return true;
		}
	}

	public void removeAlphaChannel()
	{
		encoder.setAlphaChannel(false);
	}

	public void setHeight(double height)
	{
		this.height = height;
	}

	public void setResolution(double resolution)
	{
		this.resolution = resolution;

	}

	public void setWidth(double width)
	{
		this.width = width;
	}

	public File save(String filename)
	{
		boolean mustResize = false;
		if (image.getWidth() != width) {
			mustResize |= true;
		}
		if (image.getHeight() != height) {
			mustResize |= true;
		}
		if (mustResize) {
			scale();
		}

		
		return null;
	}

	private void scale()
	{
		BufferedImage ret = new BufferedImage((int) width, (int) height, image.getType());
		Graphics2D g = ret.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
		g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		// VALUE_INTERPOLATION_NEAREST_NEIGHBOR, VALUE_INTERPOLATION_BILINEAR, VALUE_INTERPOLATION_BICUBIC 
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.drawImage(image, 0, 0, (int) width, (int) height, null);
		initializeEncoder();	
	}
	
}
