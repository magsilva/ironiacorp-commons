/*
Copyright (C) 2005 Camila Kozlowski Della Corte <camilakoz@gmail.com>

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

	http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package com.ironiacorp.xml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;


public final class XMLUtil
{
	private static DocumentBuilderFactory factory;
	
	private static DocumentBuilder builder;
	
	private static synchronized DocumentBuilderFactory getDocumentFactory()
	{
		if (factory == null) {
			factory = DocumentBuilderFactory.newInstance();
		}
		return factory;
	}
	
	private static synchronized DocumentBuilder getDocumentBuilder()
	{
		if (builder == null) {
			DocumentBuilderFactory factory = getDocumentFactory();
			try {
				builder = factory.newDocumentBuilder();
			} catch (ParserConfigurationException e) {
				throw new RuntimeException(e);
			}
		}
		return builder;
	}

	/**
	 * Save an XML file.
	 */
	public static void saveXML(Document document, String filename)
	{
		saveXML(document, new File(filename));
	}
	
	
	/**
	 * Save an XML file.
	 */
	public static void saveXML(Document document, File file)
	{
		XMLSerializer serializer = new XMLSerializer();
		try {
			OutputFormat of = new OutputFormat(document, "ISO-8859-1", true);
			serializer.setOutputFormat(of);
			serializer.setOutputCharStream(new FileWriter(file));
			serializer.serialize(document);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Load an XML file.
	 */
	public static Document loadXML(String filename)
	{
		File file = new File(filename);
		return loadXML(file);
	}

	/**
	 * Load an XML file.
	 */
	public static Document loadXML(File file)
	{
		Document document = null;

		if (! file.exists()) {
			throw new IllegalArgumentException("Document " + file + " not found");
		}

		try {
			DocumentBuilder builder = getDocumentBuilder();
			document = builder.parse(file);
			document.normalize();
		} catch (SAXException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return document;
	}

	/**
	 * Create an empty XML document.
	 */
	public static Document createEmptyDocument()
	{
		DocumentBuilder builder = getDocumentBuilder();
		return builder.newDocument();
	}
}
