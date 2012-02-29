package com.ironiacorp.xml;

import java.util.Map;

import javax.xml.transform.Result;
import javax.xml.transform.Source;

public interface XsltTransformer
{

	Result transform(Source xmlSource, Source xsltSource);

	Result transform(Source xmlSource, Source xsltSource, Map<String, Object> params);

	boolean isCached(Source xsltSource);

}
