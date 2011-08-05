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

package com.ironiacorp.imaging.png.encoder;

import java.awt.image.BufferedImage;

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
	
	public byte[] encode(BufferedImage image)
	{
		encoder.setImage(image);
		return encoder.pngEncode(alphaChannel);
	}
}
