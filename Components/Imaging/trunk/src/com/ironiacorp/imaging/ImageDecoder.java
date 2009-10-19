package com.ironiacorp.imaging;

import java.awt.image.BufferedImage;

public interface ImageDecoder
{
	BufferedImage decode(byte[] data);
}
