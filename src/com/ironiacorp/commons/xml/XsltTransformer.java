/*
This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

Copyright (C) 2007 Marco Aurelio Graciotto Silva <magsilva@gmail.com>
 */

package com.ironiacorp.commons.xml;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.stream.StreamResult;

public class XsltTransformer
{
	private static HashMap<StreamSource, Templates> cachedXslts = new HashMap<StreamSource, Templates>();

	public static void transform(String xmlFilename, String xsltFilename, String outputFilename)
	{
		File outputFile = new File(outputFilename);
		Result result = new StreamResult(outputFile);
		transform(xmlFilename, xsltFilename, result);
	}

	public static void transform(String xmlFilename, String xsltFilename, Result result)
	{
		File xmlFile = new File(xmlFilename);
		File xsltFile = new File(xsltFilename);
		transform(xmlFile, xsltFile, result);
	}

	public static void transform(File xmlFile, File xsltFile, Result result)
	{
		transform(xmlFile, xsltFile, result, null);
	}

	public static void transform(File xmlFile, File xsltFile, Result result, Map<String, Object> params)
	{
		Source xmlSource = new StreamSource(xmlFile);
		Source xsltSource = new StreamSource(xsltFile);

		try {
			Transformer trans = getTransform(xsltSource);

			if (params != null) {
				Iterator<String> i = params.keySet().iterator();
				while (i.hasNext()) {
					String key = i.next();
					trans.setParameter(key, params.get(key));
				}
			}

			trans.transform(xmlSource, result);
		} catch (TransformerConfigurationException e) {
		} catch (TransformerException e) {
		}
	}

	public static Transformer getTransform(File xsltFile) throws TransformerConfigurationException
	{
		StreamSource xsltSource = new StreamSource(xsltFile);
		return getTransform(xsltSource);
	}

	public static Transformer getTransform(Source xsltSource) throws TransformerConfigurationException
	{
		TransformerFactory transFact = TransformerFactory.newInstance();

		// Cached code path
		Templates cachedXslt = null;
		if (!cachedXslts.containsKey(xsltSource)) {
			cachedXslt = transFact.newTemplates(xsltSource);
			cachedXslts.put((StreamSource) xsltSource, cachedXslt);
		}
		if (cachedXslt == null) {
			cachedXslt = cachedXslts.get(xsltSource);
		}
		Transformer trans = cachedXslt.newTransformer();

		// Uncached code path
		// Transformer trans = transFact.newTransformer(xsltSource);

		trans.clearParameters();
		trans.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		trans.setOutputProperty(OutputKeys.INDENT, "UTF-8");
		trans.setOutputProperty(OutputKeys.METHOD, "text");

		/*
		 * http://xml.apache.org/xalan-j/usagepatterns.html#debugging
		// Set up a PrintTraceListener object to print to a file.
		FileWriter fw = new FileWriter("events.log");
		PrintWriter pw = new PrintWriter(fw);
		PrintTraceListener ptl = new PrintTraceListener(pw);
		// Print information as each node is 'executed' in the stylesheet.
		ptl.m_traceElements = true;
		// Print information after each result-tree generation event.
		ptl.m_traceGeneration = true;
		// Print information after each selection event.
		ptl.m_traceSelection = true;
		// Print information whenever a template is invoked.
		ptl.m_traceTemplates = true;
		// Print information whenever an extension is called.
		ptl.m_traceExtension = true;
		// Cast the Transformer object to TransformerImpl.
		if (transformer instanceof TransformerImpl) {
		  TransformerImpl transformerImpl = (TransformerImpl)transformer;
		  
		  // Register the TraceListener with the TraceManager associated 
		  // with the TransformerImpl.
		  TraceManager trMgr = transformerImpl.getTraceManager();
		  trMgr.addTraceListener(ptl);
		*/
		
		
		return trans;
	}
}
