package com.ironiacorp.number;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CardinalNumber
{
	private static final Pattern PATTERN_NUMBER = Pattern.compile("^([0-9]+)$", Pattern.CASE_INSENSITIVE);

	private static final Pattern PATTERN_NUMBER_WITH_DELIMITER = Pattern.compile("^([0-9]{1,2})(?:[\\.,]([0-9]{3}))*$", Pattern.CASE_INSENSITIVE);

	private static final String NUMBER_SEPARATOR = "[\\s,-]";

	private static final String NUMBER_MUL_SEPARATOR = "[\\s]";
	
	private static final Object[][] PATTERN_TEXT = {
		{ Pattern.compile("^((?:\\w*" + NUMBER_SEPARATOR + ")*)(zero)$", Pattern.CASE_INSENSITIVE), 0L },
		{ Pattern.compile("^((?:\\w*" + NUMBER_SEPARATOR + ")*)(nought)$", Pattern.CASE_INSENSITIVE), 0L },
		{ Pattern.compile("^((?:\\w*" + NUMBER_SEPARATOR + ")*)(naught)$", Pattern.CASE_INSENSITIVE), 0L },
		{ Pattern.compile("^((?:\\w*" + NUMBER_SEPARATOR + ")*)(nil)$", Pattern.CASE_INSENSITIVE), 0L },
		{ Pattern.compile("^((?:\\w*" + NUMBER_SEPARATOR + ")*)(o)$", Pattern.CASE_INSENSITIVE), 0L },
		{ Pattern.compile("^((?:\\w*" + NUMBER_SEPARATOR + ")*)(one)$", Pattern.CASE_INSENSITIVE), 1L },
		{ Pattern.compile("^((?:\\w*" + NUMBER_SEPARATOR + ")*)(two)$", Pattern.CASE_INSENSITIVE), 2L },
		{ Pattern.compile("^((?:\\w*" + NUMBER_SEPARATOR + ")*)(three)$", Pattern.CASE_INSENSITIVE), 3L },
		{ Pattern.compile("^((?:\\w*" + NUMBER_SEPARATOR + ")*)(four)$", Pattern.CASE_INSENSITIVE), 4L },
		{ Pattern.compile("^((?:\\w*" + NUMBER_SEPARATOR + ")*)(five)$", Pattern.CASE_INSENSITIVE), 5L },
		{ Pattern.compile("^((?:\\w*" + NUMBER_SEPARATOR + ")*)(six)$", Pattern.CASE_INSENSITIVE), 6L },
		{ Pattern.compile("^((?:\\w*" + NUMBER_SEPARATOR + ")*)(seven)$", Pattern.CASE_INSENSITIVE), 7L },
		{ Pattern.compile("^((?:\\w*" + NUMBER_SEPARATOR + ")*)(eight)$", Pattern.CASE_INSENSITIVE), 8L },
		{ Pattern.compile("^((?:\\w*" + NUMBER_SEPARATOR + ")*)(nine)$", Pattern.CASE_INSENSITIVE), 9L },
		{ Pattern.compile("^((?:\\w*" + NUMBER_SEPARATOR + ")*)(ten)$", Pattern.CASE_INSENSITIVE), 10L },
		{ Pattern.compile("^((?:\\w*" + NUMBER_SEPARATOR + ")*)(eleven)$", Pattern.CASE_INSENSITIVE), 11L },
		{ Pattern.compile("^((?:\\w*" + NUMBER_SEPARATOR + ")*)(twelve)$", Pattern.CASE_INSENSITIVE), 12L },
		{ Pattern.compile("^((?:\\w*" + NUMBER_SEPARATOR + ")*)(thirteen)$", Pattern.CASE_INSENSITIVE), 13L },
		{ Pattern.compile("^((?:\\w*" + NUMBER_SEPARATOR + ")*)(fourteen)$", Pattern.CASE_INSENSITIVE), 14L },
		{ Pattern.compile("^((?:\\w*" + NUMBER_SEPARATOR + ")*)(fifteen)$", Pattern.CASE_INSENSITIVE), 15L },
		{ Pattern.compile("^((?:\\w*" + NUMBER_SEPARATOR + ")*)(sixteen)$", Pattern.CASE_INSENSITIVE), 16L },
		{ Pattern.compile("^((?:\\w*" + NUMBER_SEPARATOR + ")*)(seventeen)$", Pattern.CASE_INSENSITIVE), 17L },
		{ Pattern.compile("^((?:\\w*" + NUMBER_SEPARATOR + ")*)(eighteen)$", Pattern.CASE_INSENSITIVE), 18L },
		{ Pattern.compile("^((?:\\w*" + NUMBER_SEPARATOR + ")*)(nineteen)$", Pattern.CASE_INSENSITIVE), 19L },
		{ Pattern.compile("^((?:\\w*" + NUMBER_SEPARATOR + ")*)(twenty)$", Pattern.CASE_INSENSITIVE), 20L },
		{ Pattern.compile("^((?:\\w*" + NUMBER_SEPARATOR + ")*)(thirty)$", Pattern.CASE_INSENSITIVE), 30L },
		{ Pattern.compile("^((?:\\w*" + NUMBER_SEPARATOR + ")*)(fourty)$", Pattern.CASE_INSENSITIVE), 40L },
		{ Pattern.compile("^((?:\\w*" + NUMBER_SEPARATOR + ")*)(fifty)$", Pattern.CASE_INSENSITIVE), 50L },
		{ Pattern.compile("^((?:\\w*" + NUMBER_SEPARATOR + ")*)(sixty)$", Pattern.CASE_INSENSITIVE), 60L },
		{ Pattern.compile("^((?:\\w*" + NUMBER_SEPARATOR + ")*)(seventy)$", Pattern.CASE_INSENSITIVE), 70L },
		{ Pattern.compile("^((?:\\w*" + NUMBER_SEPARATOR + ")*)(eighty)$", Pattern.CASE_INSENSITIVE), 80L },
		{ Pattern.compile("^((?:\\w*" + NUMBER_SEPARATOR + ")*)(ninety)$", Pattern.CASE_INSENSITIVE), 90L },
		{ Pattern.compile("^((?:\\w*" + NUMBER_SEPARATOR + ")*)(hundred)$", Pattern.CASE_INSENSITIVE), 100L },
		{ Pattern.compile("^((?:\\w*" + NUMBER_SEPARATOR + ")*)(thousand)$", Pattern.CASE_INSENSITIVE), 1000L },
		{ Pattern.compile("^((?:\\w*" + NUMBER_SEPARATOR + ")*)(million)$", Pattern.CASE_INSENSITIVE), 1000000L },
		{ Pattern.compile("^((?:\\w*" + NUMBER_SEPARATOR + ")*)(billion)$", Pattern.CASE_INSENSITIVE), 1000000000L },
	};
	
	private static final Object[][] PATTERN_MULTIPLIER_TEXT = {
		{ Pattern.compile("^((?:\\w*" + NUMBER_MUL_SEPARATOR + ")*)?(hundred)$", Pattern.CASE_INSENSITIVE), 100L },
		{ Pattern.compile("^((?:\\w*" + NUMBER_MUL_SEPARATOR + ")*)?(thousand)$", Pattern.CASE_INSENSITIVE), 1000L },
		{ Pattern.compile("^((?:\\w*" + NUMBER_MUL_SEPARATOR + ")*)?(million)$", Pattern.CASE_INSENSITIVE), 1000000L },
		{ Pattern.compile("^((?:\\w*" + NUMBER_MUL_SEPARATOR + ")*)?(billion)$", Pattern.CASE_INSENSITIVE), 1000000000L },
	};
	
	public long convert(String text)
	{
		Matcher m;
		text = text.replaceAll("(?:\\s+)and\\s*", ",");
		text = text.replaceAll("\\s*,\\s*,", ",");
		
		m = PATTERN_NUMBER.matcher(text);
		if (m.matches()) {
			return Long.parseLong(m.group(1));
		}
		
		m = PATTERN_NUMBER_WITH_DELIMITER.matcher(text);
		if (m.matches()) {
			String[] numbers = text.split("[\\.,]");
			long number = Long.parseLong(numbers[numbers.length - 1]); 
			for (int i = 1, j = (numbers.length - 2); j >= 0; j--, i++) {
				number += i * 1000 * Integer.parseInt(numbers[j]); 
			}
			return number;
		}
		
		for (int i = 0; i < PATTERN_MULTIPLIER_TEXT.length; i++) {
			Pattern pattern = (Pattern) PATTERN_MULTIPLIER_TEXT[i][0];
			m = pattern.matcher(text);
			if (m.matches()) {
				long number = (Long) PATTERN_MULTIPLIER_TEXT[i][1];
				if (m.groupCount() > 1 && ! "".equals(m.group(1))) {
					// 15 billion, 6 million, nineteen
					String[] numbers = m.group(1).split(NUMBER_MUL_SEPARATOR);
					for (int j = (numbers.length - 1); j >= 0; j--) {
						number *= convert(numbers[j].trim());
					}
				}
				return number;
			}
		}
		
		for (int i = 0; i < PATTERN_TEXT.length; i++) {
			Pattern pattern = (Pattern) PATTERN_TEXT[i][0];
			m = pattern.matcher(text);
			if (m.matches()) {
				long number = (Long) PATTERN_TEXT[i][1];
				if (m.groupCount() > 1 && ! "".equals(m.group(1))) {
					String[] numbers = m.group(1).split("[,-]");
					for (int j = (numbers.length - 1); j >= 0; j--) {
						number += convert(numbers[j].trim());
					}
				}
				return number;
			}
		}
		
		throw new IllegalArgumentException("Could not find a cardinal number in the text");
	}
}