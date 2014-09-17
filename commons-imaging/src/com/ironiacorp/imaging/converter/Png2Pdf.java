package com.ironiacorp.imaging.converter;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.ironiacorp.computer.ComputerSystem;
import com.ironiacorp.computer.OperationalSystem;
import com.ironiacorp.imaging.Converter;

public class Png2Pdf implements Converter
{
	public static final String IMAGEMAGICK_CONVERT_FILENAME = "convert";

	public static final int DEFAULT_COMPRESSION_LEVEL = 0;

	public static final int DEFAULT_RESOLUTION = 300;
	
	public static final String DEFAULT_OUTFILE_MASK = "page%d.png";
	
	private File imConvertExec;
	
	private OperationalSystem os;
	
	/**
	 * Compression level can be 0 (Huffman, best compression) or from 1 to 9 (zlib compression).
	 */
	private int compressionLevel;
	
	private int resolution;
	
	private String outfileMask;
	
	private File outdir;
	
	public Png2Pdf() {
		os = ComputerSystem.getCurrentOperationalSystem();
		imConvertExec = os.findExecutable(IMAGEMAGICK_CONVERT_FILENAME);
		if (imConvertExec == null) {
			throw new UnsupportedOperationException("ImageMagick could not be found in the computer");
		}
		compressionLevel = DEFAULT_COMPRESSION_LEVEL;
		resolution = DEFAULT_RESOLUTION;
		outfileMask = DEFAULT_OUTFILE_MASK;
		outdir = os.getFilesystem().createTempDir();
	}
	
	@Override
	public File convert(Image image) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public File convert(File image) {
		List<String> parameters = new ArrayList<String>();
		parameters.add("-quality");
		parameters.add(compressionLevel + "0");
		parameters.add("-density");
		parameters.add(resolution + "x" + resolution);
		parameters.add(os.getFilesystem().join(outdir.toString(), outfileMask));
		os.exec(imConvertExec, parameters);
		
		return null;
	}

	@Override
	public boolean setAlphaChannel() {
		// TODO Auto-generated method stub
		return false;
	}

}
