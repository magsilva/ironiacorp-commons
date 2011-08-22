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

package com.ironiacorp.xml;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

public class TraxXsltTransformer
{
	private static final HashMap<Source, Templates> cachedXslts = new HashMap<Source, Templates>();

	public TraxXsltTransformer()
	{
	}

	public Result transform(Source xmlSource, Source xsltSource)
	{
		return transform(xmlSource, xsltSource);
	}

	
	public Result transform(Source xmlSource, Source xsltSource, Map<String, Object> params)
	{
		try {
			Transformer trans = cacheXslt(xsltSource).newTransformer();

			trans.clearParameters();
			trans.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			trans.setOutputProperty(OutputKeys.INDENT, "yes");
			trans.setOutputProperty(OutputKeys.METHOD, "xml");
			
			if (params != null) {
				Iterator<String> i = params.keySet().iterator();
				while (i.hasNext()) {
					String key = i.next();
					trans.setParameter(key, params.get(key));
				}
			}

			OutputStream os = new ByteArrayOutputStream();
			Result result = new StreamResult(os);
			trans.transform(xmlSource, result);
			
			return result;
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	public synchronized Templates cacheXslt(Source xsltSource)
	{
		if (! cachedXslts.containsKey(xsltSource)) {
			TransformerFactory transFact = TransformerFactory.newInstance();
			Templates cachedXslt;
			try {
				cachedXslt = transFact.newTemplates(xsltSource);
			} catch (TransformerConfigurationException e) {
				throw new IllegalArgumentException(e);
			}
			cachedXslts.put(xsltSource, cachedXslt);
		}
		
		return cachedXslts.get(xsltSource);
	}

	public synchronized boolean isCached(Source xsltSource)
	{
		return cachedXslts.containsKey(xsltSource);
	}
}
