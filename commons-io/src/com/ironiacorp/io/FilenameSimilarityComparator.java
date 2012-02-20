/*
 * Copyright (C) 2012 Marco Aurélio Graciotto Silva <magsilva@ironiacorp.com>
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

package com.ironiacorp.io;

import java.io.File;
import java.util.Comparator;

import uk.ac.shef.wit.simmetrics.similaritymetrics.InterfaceStringMetric;
import uk.ac.shef.wit.simmetrics.similaritymetrics.Levenshtein;

import com.ironiacorp.string.StringSimilarityComparator;
import com.ironiacorp.string.StringUtil;

public class FilenameSimilarityComparator implements Comparator<File>
{
	private StringSimilarityComparator similarityComparator;
	
	public FilenameSimilarityComparator(File file)
	{
		this(file.getName(), new Levenshtein());
	}
	
	public FilenameSimilarityComparator(String filename)
	{
		this(filename, new Levenshtein());
	}
	
	public FilenameSimilarityComparator(String filename, InterfaceStringMetric metric)
	{
		if (StringUtil.isEmpty(filename)) {
			throw new IllegalArgumentException(new NullPointerException());
		}
		
		if (metric == null) {
			throw new IllegalArgumentException(new NullPointerException());
		}

		this.similarityComparator = new StringSimilarityComparator(filename, metric);
	}
	
	public float getSimilarity(File file)
	{
		if (file == null) {
			return 0;
		}
		return similarityComparator.getSimilarity(file.getName());
	}
	
	@Override
	public int compare(File file1, File file2)
	{
		float similarity1 = similarityComparator.getSimilarity(file1.getName());
		float similarity2 = similarityComparator.getSimilarity(file2.getName());
		return Float.compare(similarity1, similarity2);
	}
}
