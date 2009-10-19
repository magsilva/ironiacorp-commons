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
import java.util.Map;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamResult;

import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.Serializer;
import net.sf.saxon.s9api.XdmNode;
import net.sf.saxon.s9api.XsltCompiler;
import net.sf.saxon.s9api.XsltExecutable;
import net.sf.saxon.s9api.XsltTransformer;

public class SaxenXsltTransformer implements com.ironiacorp.xml.XsltTransformer
{
	private static final HashMap<Source, XsltExecutable> cachedXslts = new HashMap<Source, XsltExecutable>();

	private XsltCompiler comp;
	
	private Processor proc;
	
	public SaxenXsltTransformer()
	{
		 proc = new Processor(false);
		 
         comp = proc.newXsltCompiler();
	}

	public Result transform(Source xmlSource, Source xsltSource)
	{
		return transform(xmlSource, xsltSource, null);
	}
	
	public Result transform(Source xmlSource, Source xsltSource, Map<String, Object> params)
	{
		try {
			XsltExecutable exp = cacheXslt(xsltSource);
	        XdmNode source = proc.newDocumentBuilder().build(xmlSource);
            XsltTransformer trans = exp.load();
			
            OutputStream os = new ByteArrayOutputStream(); 
			Serializer out = new Serializer();
            out.setOutputProperty(Serializer.Property.METHOD, "xml");
            out.setOutputProperty(Serializer.Property.INDENT, "yes");
            out.setOutputProperty(Serializer.Property.ESCAPE_URI_ATTRIBUTES, "no"); 
            out.setOutputProperty(Serializer.Property.ENCODING, "UTF-8"); 
            out.setOutputStream(os);
            
            trans.setInitialContextNode(source);
            trans.setDestination(out);
            trans.transform();
            
            return new StreamResult(os);
            
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	public synchronized XsltExecutable cacheXslt(Source xsltSource)
	{
		if (! cachedXslts.containsKey(xsltSource)) {
			try {
				XsltExecutable exp = comp.compile(xsltSource);
				cachedXslts.put(xsltSource, exp);
			} catch (Exception e) {
				throw new IllegalArgumentException(e);
			}
		}
		
		return cachedXslts.get(xsltSource);
	}

	public synchronized boolean isCached(Source xsltSource)
	{
		return cachedXslts.containsKey(xsltSource);
	}
}
