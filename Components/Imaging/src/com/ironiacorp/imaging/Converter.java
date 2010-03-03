package com.ironiacorp.imaging;

import java.awt.Image;
import java.io.File;

public interface Converter
{
	File convert(Image image);
	
	boolean setAlphaChannel();
}
