package com.ironiacorp.imaging;

import java.awt.image.BufferedImage;

public interface ImageEncoder
{
	byte[] encode(BufferedImage image);
}
