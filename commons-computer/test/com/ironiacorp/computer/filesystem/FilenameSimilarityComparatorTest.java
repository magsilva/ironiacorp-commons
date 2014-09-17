/*
Licensed to the Apache Software Foundation (ASF) under one or more
contributor license agreements.  See the NOTICE file distributed with
this work for additional information regarding copyright ownership.
The ASF licenses this file to You under the Apache License, Version 2.0
(the "License"); you may not use this file except in compliance with
the License.  You may obtain a copy of the License at

	http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

Copyright (C) 2011 Marco Aurélio Graciotto Silva <magsilva@ironiacorp.com>
*/

package com.ironiacorp.computer.filesystem;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import uk.ac.shef.wit.simmetrics.similaritymetrics.Levenshtein;


public class FilenameSimilarityComparatorTest
{
	String filename;
	
	private FilenameSimilarityComparator comparator;
	
	private File file0;
	
	private File file1;

	private File file2;

	float similarity;
	
	@Before
	public void setUp() throws Exception
	{
		filename = "The quick brown fox jumps over the lazy dog.pdf";

		file0 = new File("The quick brown fox jumps over the lazy dog.pdf"); 
		file1 = new File("The quick brown fox jumps over the lazy cat.pdf");
		file2 = new File("One ring to rule them all.pdf");
	}
	
	@Test
	public void testFilenameComparatorPublication_Ok()
	{
		new FilenameSimilarityComparator(filename);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testFilenameComparatorPublication_Null()
	{
		new FilenameSimilarityComparator((String) null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testFilenameComparatorPublication_InvalidTitle_Null()
	{
		new FilenameSimilarityComparator((String) null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testFilenameComparatorPublication_InvalidTitle_Empty()
	{
		new FilenameSimilarityComparator("");
	}

	@Test
	public void testFilenameComparatorPublicationInterfaceStringMetric_Ok()
	{
		new FilenameSimilarityComparator(filename, new Levenshtein());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testFilenameComparatorPublicationInterfaceStringMetric_NullMetric()
	{
		new FilenameSimilarityComparator(filename, null);
	}
	
	@Test
	public void testGetSimilarity_Equals()
	{
		comparator = new FilenameSimilarityComparator(filename);
		assertEquals(1.0, comparator.getSimilarity(new File("The quick brown fox jumps over the lazy dog.pdf")), 0);
	}
	
	@Test
	public void testGetSimilarity_Similar_OneLetter()
	{
		comparator = new FilenameSimilarityComparator(filename);
		similarity = comparator.getSimilarity(new File("The quick brown fox jumps over the lazy do.pdf"));
		assertTrue(similarity < 1);
		assertTrue(similarity > 0.9);
	}
	
	@Test
	public void testGetSimilarity_Similar_OneWord()
	{
		comparator = new FilenameSimilarityComparator(filename);
		similarity = comparator.getSimilarity(new File("The quick brown fox jumps over the dog.pdf")); 
		assertTrue(similarity < 1);
		assertTrue(similarity > 0.8);
	}
	
	@Test
	public void testGetSimilarity_Different_EveryWord()
	{
		comparator = new FilenameSimilarityComparator(filename);
		similarity = comparator.getSimilarity(new File("You stay up late because you did not do anything productive all day.pdf")); 
		assertTrue(similarity < 1);
		assertTrue(similarity < 0.3);
	}
	
	@Test
	public void testGetSimilarity_Different_Empty()
	{
		comparator = new FilenameSimilarityComparator(filename);
		similarity = comparator.getSimilarity(new File("")); 
		assertTrue(similarity == 0);
	}
	
	@Test
	public void testGetSimilarity_Different_Null()
	{
		comparator = new FilenameSimilarityComparator(filename);
		similarity = comparator.getSimilarity(null); 
		assertTrue(similarity == 0);
	}
	
	@Test
	public void testCompare_Ok()
	{
		comparator = new FilenameSimilarityComparator(filename);
		
		assertTrue(comparator.compare(file0, file0) == 0);
		assertTrue(comparator.compare(file0, file1) == 1);
		assertTrue(comparator.compare(file0, file2) == 1);

		assertTrue(comparator.compare(file1, file0) == -1);
		assertTrue(comparator.compare(file1, file1) == 0);
		assertTrue(comparator.compare(file1, file2) == 1);

		assertTrue(comparator.compare(file2, file0) == -1);
		assertTrue(comparator.compare(file2, file1) == -1);
		assertTrue(comparator.compare(file2, file2) == 0);
	}
}
