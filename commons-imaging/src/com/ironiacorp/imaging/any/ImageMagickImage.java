package com.ironiacorp.imaging.any;

import java.awt.image.BufferedImage;
import java.io.File;

import com.ironiacorp.computer.ComputerSystem;
import com.ironiacorp.computer.OperationalSystem;
import com.ironiacorp.computer.OperationalSystemDetector;
import com.ironiacorp.imaging.Image;

public class ImageMagickImage implements Image
{
	
	private static final String IMAGEMAGICK_IDENTIFY_FILENAME = "identify";
	
	private File imConvertExec;
	
	private File imageFile;
	
	private int width;
	
	private int height;
	
	private int resolutionX;
	
	private int resolutionY;
	
	public ImageMagickImage(File file) {
		OperationalSystem os = ComputerSystem.getCurrentOperationalSystem();
		imConvertExec = os.findExecutable(IMAGEMAGICK_IDENTIFY_FILENAME);
		if (imConvertExec == null) {
			throw new UnsupportedOperationException("ImageMagick could not be found in the computer");
		}
		if (! file.exists()) {
			throw new IllegalArgumentException("Could not find requested file: " + file);
		}
		imageFile = file;
	}

	@Override
	public BufferedImage getBufferedImage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getBitDepth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getResolutionX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getResolutionY() {
		// TODO Auto-generated method stub
		return 0;
	}
}
