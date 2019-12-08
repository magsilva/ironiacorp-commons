package com.ironiacorp.number;

public final class HumanReadableBinaryNumberConverter {
	
	private boolean useSpaceBetweenNumberAndUnit;

	private String textBetweenNumberAndUnit;
	
	private String magnitudeText = "-";

	public HumanReadableBinaryNumberConverter() {
		setUseSpaceBetweenNumberAndUnit(true);
	}
	
	public boolean isUseSpaceBetweenNumberAndUnit() {
		return useSpaceBetweenNumberAndUnit;
	}

	public void setUseSpaceBetweenNumberAndUnit(boolean useSpaceBetweenNumberAndUnit) {
		if (this.useSpaceBetweenNumberAndUnit != useSpaceBetweenNumberAndUnit) {
			this.useSpaceBetweenNumberAndUnit = useSpaceBetweenNumberAndUnit;
			if (this.useSpaceBetweenNumberAndUnit) {
				textBetweenNumberAndUnit = " ";
			} else {
				textBetweenNumberAndUnit = "";
			}
		}
	}

	public String getMagnitudeSymbol() {
		return magnitudeText;
	}

	public void setMagnitudeSymbol(String magnitudeSymbol) {
		this.magnitudeText = magnitudeSymbol;
	}

	/**
	 * SI (1 k = 1,000)
	 * https://programming.guide/java/formatting-byte-size-to-human-readable-format.html
	 * 
	 * @param bytes
	 * @return
	 */
	public String toSI(long bytes) {
	    String magnitudeSymbol = bytes < 0 ? magnitudeText : "";
	    long absBytes = bytes == Long.MIN_VALUE ? Long.MAX_VALUE : Math.abs(bytes);
	    return absBytes < 1000L ? String.format("%s%.0f%sB", magnitudeSymbol, (float) absBytes, textBetweenNumberAndUnit)
	            : absBytes < 999_950L ? String.format("%s%.1f%skB", magnitudeSymbol, absBytes / 1e3, textBetweenNumberAndUnit)
	            : (absBytes /= 1000) < 999_950L ? String.format("%s%.1f%sMB", magnitudeSymbol, absBytes / 1e3, textBetweenNumberAndUnit)
	            : (absBytes /= 1000) < 999_950L ? String.format("%s%.1f%sGB", magnitudeSymbol, absBytes / 1e3, textBetweenNumberAndUnit)
	            : (absBytes /= 1000) < 999_950L ? String.format("%s%.1f%sTB", magnitudeSymbol, absBytes / 1e3, textBetweenNumberAndUnit)
	            : (absBytes /= 1000) < 999_950L ? String.format("%s%.1f%sPB", magnitudeSymbol, absBytes / 1e3, textBetweenNumberAndUnit)
	            : String.format("%s%.1f EB", magnitudeSymbol, absBytes / 1e6, textBetweenNumberAndUnit);
	}

	/**
	 * Binary (1 K = 1,024)
	 * https://programming.guide/java/formatting-byte-size-to-human-readable-format.html
	 * 
	 * @param bytes
	 * @return
	 */
	public String toBinary(long bytes) {
		String magnitudeSymbol = bytes < 0 ? magnitudeText : "";
	    long absBytes = bytes == Long.MIN_VALUE ? Long.MAX_VALUE : Math.abs(bytes);
	    return absBytes < 1024L ? String.format("%s%.0f%sB", magnitudeSymbol, (float) absBytes, textBetweenNumberAndUnit)
	            : absBytes <= 0xfffccccccccccccL >> 40 ? String.format("%s%.1f%sKiB", magnitudeSymbol, absBytes / 0x1p10, textBetweenNumberAndUnit)
	            : absBytes <= 0xfffccccccccccccL >> 30 ? String.format("%s%.1f%sMiB", magnitudeSymbol, absBytes / 0x1p20, textBetweenNumberAndUnit)
	            : absBytes <= 0xfffccccccccccccL >> 20 ? String.format("%s%.1f%sGiB", magnitudeSymbol, absBytes / 0x1p30, textBetweenNumberAndUnit)
	            : absBytes <= 0xfffccccccccccccL >> 10 ? String.format("%s%.1f%sTiB", magnitudeSymbol, absBytes / 0x1p40, textBetweenNumberAndUnit)
	            : absBytes <= 0xfffccccccccccccL ? String.format("%s%.1f%sPiB", magnitudeSymbol, (absBytes >> 10) / 0x1p40, textBetweenNumberAndUnit)
	            : String.format("%s%.1f%sEiB", magnitudeSymbol, (absBytes >> 20) / 0x1p40, textBetweenNumberAndUnit);
	}

}
