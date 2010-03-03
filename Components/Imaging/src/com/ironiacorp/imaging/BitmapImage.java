package com.ironiacorp.imaging;

import java.io.File;

public interface BitmapImage
{
	boolean hasAlphaChannel();
	
	void removeAlphaChannel();
	
	void setWidth(double width);
	
	void setHeight(double height);
	
	void setResolution(double resolution);
	
	File save(String filename);
}
