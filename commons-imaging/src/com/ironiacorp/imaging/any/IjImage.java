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

package com.ironiacorp.imaging.any;

import ij.ImagePlus;
import ij.io.Opener;
import ij.measure.Calibration;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

import com.ironiacorp.computer.ComputerSystem;
import com.ironiacorp.computer.OperationalSystem;
import com.ironiacorp.computer.Filesystem;
import com.ironiacorp.imaging.Image;
import com.ironiacorp.io.IoUtil;

public class IjImage implements Image
{
	private ImagePlus image;
	
	private Calibration imageCalibration;
	
	public IjImage(File file)
	{
		Opener imageOpener = new Opener();
		this.image = imageOpener.openImage(file.getAbsolutePath());
		init();
	}
	
	public IjImage(InputStream is)
	{
		Opener imageOpener = new Opener();
		OperationalSystem os = ComputerSystem.getCurrentOperationalSystem();
                Filesystem fs = os.getFilesystem();
		File file = fs.createTempFile();
		IoUtil.toFile(is, file);
		this.image = imageOpener.openImage(file.getAbsolutePath());
		init();
	}
	
	private void init()
	{
		this.imageCalibration = image.getCalibration();
	}
	
	

	public double getResolutionY()
	{
		return 1.0 / imageCalibration.pixelHeight;
	}
	
	public double getResolutionX()
	{
		return 1.0 / imageCalibration.pixelWidth;
	}

	@Override
	public BufferedImage getBufferedImage()
	{
		return image.getBufferedImage();
	}

	@Override
	public int getBitDepth()
	{
		return image.getBitDepth();
	}

	@Override
	public int getWidth()
	{
		return image.getWidth();
	}

	@Override
	public int getHeight()
	{
		return image.getHeight();
	}
}
