package com.ironiacorp.imaging.png.encoder;

import com.ironiacorp.imaging.ImageEncoder;

public interface PngImageEncoder extends ImageEncoder
{
	int getCompressionLevel();

	void setCompressionLevel(int compressionLevel);

	boolean useAlphaChannel();

	void setAlphaChannel(boolean alphaChannel);
}
