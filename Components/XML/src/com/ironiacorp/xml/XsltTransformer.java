package com.ironiacorp.xml;

import java.util.Map;

import javax.xml.transform.Result;
import javax.xml.transform.Source;

import net.sf.saxon.s9api.XsltExecutable;

public interface XsltTransformer
{

	Result transform(Source xmlSource, Source xsltSource);

	Result transform(Source xmlSource, Source xsltSource, Map<String, Object> params);

	XsltExecutable cacheXslt(Source xsltSource);

	boolean isCached(Source xsltSource);

}
