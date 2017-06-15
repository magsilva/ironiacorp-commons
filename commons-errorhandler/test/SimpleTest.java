import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;


public class SimpleTest
{
	@Test
	public void test()
	{
		Logger log = LoggerFactory.getLogger(SimpleTest.class);
		log.info("Hello World");
	}
	
	@Test
	@Ignore
	public void testLogback()
	{
		LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
		StatusPrinter.print(lc);
	}
}
