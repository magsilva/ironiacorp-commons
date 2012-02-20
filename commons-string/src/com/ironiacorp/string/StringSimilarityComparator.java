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

package com.ironiacorp.string;

import java.util.Comparator;

import uk.ac.shef.wit.simmetrics.similaritymetrics.InterfaceStringMetric;
import uk.ac.shef.wit.simmetrics.similaritymetrics.Levenshtein;

public class StringSimilarityComparator implements Comparator<String>
{
	private InterfaceStringMetric metric;
	
	private String string;
	
	public StringSimilarityComparator(String string)
	{
		this(string, new Levenshtein());
	}
	
	public StringSimilarityComparator(String string, InterfaceStringMetric metric)
	{
		if (string == null) {
			throw new IllegalArgumentException(new NullPointerException());
		}
		
		if (metric == null) {
			throw new IllegalArgumentException(new NullPointerException());
		}
		
		this.string = string;
		this.metric = metric;
	}
	
	public float getSimilarity(String text)
	{
		if (text == null) {
			return 0;
		}
		
		return metric.getSimilarity(string, text);
	}
	
	@Override
	public int compare(String text1, String text2)
	{
		float similarity1 = getSimilarity(text1);
		float similarity2 = getSimilarity(text2);
		return Float.compare(similarity1, similarity2);
	}
}
