package com.ironiacorp.ecc;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;

public class ECC
{
	// http://www.javafaq.nu/java-example-code-230.html
	private static void doChecksum(String fileName) {

        try {

            CheckedInputStream cis = null;
            long fileSize = 0;
            try {
                // Computer CRC32 checksum
                cis = new CheckedInputStream(
                        new FileInputStream(fileName), new CRC32());

                fileSize = new File(fileName).length();
               
            } catch (FileNotFoundException e) {
                System.err.println("File not found.");
                System.exit(1);
            }

            byte[] buf = new byte[128];
            while(cis.read(buf) >= 0) {
            }

            long checksum = cis.getChecksum().getValue();
            System.out.println(checksum + " " + fileSize + " " + fileName);

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }


    }
}
