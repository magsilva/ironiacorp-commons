package com.ironiacorp.imaging.converter;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.ironiacorp.computer.ComputerSystem;
import com.ironiacorp.computer.OperationalSystem;
import com.ironiacorp.imaging.Converter;

public class Pdf2Png implements Converter
{
	public static final String IMAGEMAGICK_CONVERT_FILENAME = "convert";
	
	public static final String DEFAULT_PAGE_SIZE = "a4";

	private File imConvertExec;
	
	private OperationalSystem os;
	
	private String pageSize = DEFAULT_PAGE_SIZE;
	
	private boolean mustCenterImage = false;
	
	
	public Pdf2Png() {
		os = ComputerSystem.getCurrentOperationalSystem();
		imConvertExec = os.findExecutable(IMAGEMAGICK_CONVERT_FILENAME);
		if (imConvertExec == null) {
			throw new UnsupportedOperationException("ImageMagick could not be found in the computer");
		}
	}
	
	@Override
	public File convert(Image image) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public File convert(Image[] files) {
		File outfile = os.getFilesystem().createTempFile();
		List<String> parameters = new ArrayList<String>();
		parameters.add("-page");
		parameters.add(pageSize);
		if (mustCenterImage) {
			parameters.add("-gravity");
			parameters.add("center");
		}
		parameters.add("-adjoin");
		/*
		for (File file : files) {
			parameters.add(file);
		}
		*/
		parameters.add(outfile.toString());
		os.exec(imConvertExec, parameters);
		
		return outfile;
	}

	@Override
	public boolean setAlphaChannel() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public File convert(File image) {
		// TODO Auto-generated method stub
		return null;
	}

}
