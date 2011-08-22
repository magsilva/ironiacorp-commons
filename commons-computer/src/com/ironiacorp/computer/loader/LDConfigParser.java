package com.ironiacorp.computer.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.ironiacorp.finder.FileFinder;

public class LDConfigParser
{
	public LDConfigParser()
	{
		parse();
	}
	
	public void parse()
	{
		File config = new File("/etc/ld.so.conf");
		if (! config.isFile()) {
			throw new IllegalArgumentException("No configuration file found for Linux dynamic linker.");
		}

		FileFinder finder = new FileFinder();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(config));
			String line = null;
			while ((line = reader.readLine()) != null) {
				if (line.startsWith("include ")) {
					String[] words = line.split("\\s");
					finder.find("/etc", words[1]);
				}
			}
		} catch (IOException e) {
			throw new IllegalArgumentException("Error found while parsing the configuration file.");
		}
		
	}
}
