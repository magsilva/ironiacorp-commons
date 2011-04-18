package com.ironiacorp.number;

import java.awt.CardLayout;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrdinalNumber
{
	private static final Pattern[] PATTERN_NUMBER = {
		Pattern.compile("^([0-9]+)th$", Pattern.CASE_INSENSITIVE),
		Pattern.compile("^([0-9]*1)st$", Pattern.CASE_INSENSITIVE),
		Pattern.compile("^([0-9]*2)nd$", Pattern.CASE_INSENSITIVE),
		Pattern.compile("^([0-9]*3)rd$", Pattern.CASE_INSENSITIVE),
	};
	
	private static final Object[][] PATTERN_TEXT = {
		{ Pattern.compile("^(\\w*-)*zeroth$", Pattern.CASE_INSENSITIVE), 0 },
		{ Pattern.compile("^(\\w*-)*noughth$", Pattern.CASE_INSENSITIVE), 0 },
		{ Pattern.compile("^(\\w*-)*first$", Pattern.CASE_INSENSITIVE), 1 },
		{ Pattern.compile("^(\\w*-)*second$", Pattern.CASE_INSENSITIVE), 2 },
		{ Pattern.compile("^(\\w*-)*third$", Pattern.CASE_INSENSITIVE), 3 },
		{ Pattern.compile("^(\\w*-)*fourth$", Pattern.CASE_INSENSITIVE), 4 },
		{ Pattern.compile("^(\\w*-)*fifth$", Pattern.CASE_INSENSITIVE), 5 },
		{ Pattern.compile("^(\\w*-)*sixth$", Pattern.CASE_INSENSITIVE), 6 },
		{ Pattern.compile("^(\\w*-)*seventh$", Pattern.CASE_INSENSITIVE), 7 },
		{ Pattern.compile("^(\\w*-)*eighth$", Pattern.CASE_INSENSITIVE), 8 },
		{ Pattern.compile("^(\\w*-)*ninth$", Pattern.CASE_INSENSITIVE), 9 },
		{ Pattern.compile("^(\\w*-)*tenth$", Pattern.CASE_INSENSITIVE), 10 },
		{ Pattern.compile("^(\\w*-)*eleventh$", Pattern.CASE_INSENSITIVE), 11 },
		{ Pattern.compile("^(\\w*-)*twelfth$", Pattern.CASE_INSENSITIVE), 12 },
		{ Pattern.compile("^(\\w*-)*thirteenth$", Pattern.CASE_INSENSITIVE), 13 },
		{ Pattern.compile("^(\\w*-)*fourteenth$", Pattern.CASE_INSENSITIVE), 14 },
		{ Pattern.compile("^(\\w*-)*fifteenth$", Pattern.CASE_INSENSITIVE), 15 },
		{ Pattern.compile("^(\\w*-)*sixteenth$", Pattern.CASE_INSENSITIVE), 16 },
		{ Pattern.compile("^(\\w*-)*seventeenth$", Pattern.CASE_INSENSITIVE), 17 },
		{ Pattern.compile("^(\\w*-)*eighteenth$", Pattern.CASE_INSENSITIVE), 18 },
		{ Pattern.compile("^(\\w*-)*nineteenth$", Pattern.CASE_INSENSITIVE), 19 },
		{ Pattern.compile("^(\\w*-)*twenth$", Pattern.CASE_INSENSITIVE), 20 },
		{ Pattern.compile("^(\\w*-)*thirtieth$", Pattern.CASE_INSENSITIVE), 30 },
		{ Pattern.compile("^(\\w*-)*fourtieth$", Pattern.CASE_INSENSITIVE), 40 },
		{ Pattern.compile("^(\\w*-)*fiftieth$", Pattern.CASE_INSENSITIVE), 50 },
		{ Pattern.compile("^(\\w*-)*sixtieth$", Pattern.CASE_INSENSITIVE), 60 },
		{ Pattern.compile("^(\\w*-)*seventieth$", Pattern.CASE_INSENSITIVE), 70 },
		{ Pattern.compile("^(\\w*-)*eightieth$", Pattern.CASE_INSENSITIVE), 80 },
		{ Pattern.compile("^(\\w*-)*ninetieth$", Pattern.CASE_INSENSITIVE), 90 },
		{ Pattern.compile("^(\\w*-)*hundredth$", Pattern.CASE_INSENSITIVE), 100 },
	};

	
	public int convert(String text)
	{
		Matcher m;
		
		for (Pattern pattern : PATTERN_NUMBER) {
			m = pattern.matcher(text);
			if (m.matches()) {
				return Integer.parseInt(m.group(1));
			}
		}
		
		for (int i = 0; i < PATTERN_TEXT.length; i++) {
			Pattern pattern = (Pattern) PATTERN_TEXT[i][0];
			m = pattern.matcher(text);
			if (m.matches()) {
				int number = (Integer) PATTERN_TEXT[i][1];
				if (m.groupCount() > 0 && m.group(1) != null && ! "".equals(m.group(1))) {
					String[] numbers = m.group(1).split("-");
					CardinalNumber cardinal = new CardinalNumber();
					for (String value : numbers) {
						number += cardinal.convert(value);
					}
				}
				return number;
			}
		}
		
		throw new IllegalArgumentException("Cound not find an ordinal number in the text");
	}
}
