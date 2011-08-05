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

package com.ironiacorp.imaging;

import static org.junit.Assert.*;

import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;

import com.ironiacorp.imaging.any.IjImage;

public class ImageImageJTest
{
	private Image image;
	
	@Before
	public void setUp() throws Exception
	{
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("png/mccabe-example.png");
		image = new IjImage(is);
	}

	@Test
	public void testGetResolutionY()
	{
		assertEquals(72, image.getResolutionX(), 0);
	}

	@Test
	public void testGetResolutionX()
	{
		assertEquals(72, image.getResolutionX(), 0);
	}

	@Test
	public void testGetWidth()
	{
		assertEquals(971, image.getWidth());
	}

	@Test
	public void testGetHeight()
	{
		assertEquals(495, image.getHeight());
	}

}
