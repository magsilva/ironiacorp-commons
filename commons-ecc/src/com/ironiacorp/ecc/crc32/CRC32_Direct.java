package com.ironiacorp.ecc.crc32;

import com.ironiacorp.number.NumberConverter;


public class CRC32_Direct
{
		public byte[] calculate(byte[] data)
		{
			int crc  = 0xFFFFFFFF;       // initial contents of LFBSR
			int poly = 0xEDB88320;   // reverse polynomial

			for (byte b : data) {
			    int temp = (crc ^ b) & 0xff;

			    // read 8 bits one at a time
			    for (int i = 0; i < 8; i++) {
			        if ((temp & 1) == 1) temp = (temp >>> 1) ^ poly;
			        else                 temp = (temp >>> 1);
			    }
			    crc = (crc >>> 8) ^ temp;
			}

			// flip bits
			crc = crc ^ 0xffffffff;
	        
			return NumberConverter.toBigEndianByteArray(crc);
		}
}



