package com.ironiacorp.imaging.png.encoder;

import java.awt.image.BufferedImage;

import com.ironiacorp.imaging.ImageEncoder;
import com.keypoint.PngEncoderB;

/**
 * PNG encoder (http://catcode.com/pngencoder/ and
 * http://www.jfree.org/jcommon/api/com/keypoint/PngEncoder.html)
 */
public class JFreePngEncoder implements PngImageEncoder
{
	private PngEncoderB encoder;
	
	private boolean alphaChannel;
	
	private int compressionLevel;


	public void CatcodePngEncoder()
	{
		encoder = new PngEncoderB();
	}
	
	public int getCompressionLevel()
	{
		return compressionLevel;
	}

	public void setCompressionLevel(int compressionLevel)
	{
		this.compressionLevel = compressionLevel;
	}

	public boolean useAlphaChannel()
	{
		return alphaChannel;
	}
	
	public void setAlphaChannel(boolean alphaChannel)
	{
		this.alphaChannel = alphaChannel;
	}
	
	@Override
	public byte[] encode(BufferedImage image)
	{
		encoder.setImage(image);
		return encoder.pngEncode(alphaChannel);
	}
}
