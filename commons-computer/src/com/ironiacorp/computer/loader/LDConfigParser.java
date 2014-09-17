package com.ironiacorp.computer.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class LDConfigParser
{
	public static final String DEFAULT_LD_SO_CONF = "/etc/ld.so.conf";
	
	public class GlobFileFinder extends SimpleFileVisitor<Path>
	{
		private final PathMatcher matcher;

		private Set<Path> paths = new LinkedHashSet<Path>();
		
		GlobFileFinder(String pattern) {
			matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
		}

		// Compares the glob pattern against the file or directory name.
		void find(Path file) {
			Path name = file.getFileName();
			if (name != null && matcher.matches(name)) {
				try (
					FileInputStream fis = new FileInputStream(name.toFile());
					InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
					BufferedReader br = new BufferedReader(isr);
				) {
					String line = null;
					while ((line = br.readLine()) != null) {
						if (line.startsWith("include")) {
							String pattern = line.replaceFirst("include ", "");
							GlobFileFinder finder = new GlobFileFinder(pattern);
							Files.walkFileTree(Paths.get("/etc"), finder);
							for (Path path : finder.paths) {
								paths.add(path);
							}
						} else {
							String[] pathNames = line.split(": \t,");
							for (String path : pathNames) {
								paths.add(Paths.get(path));
							}
						}
					
					}
				} catch (IOException e) {
				}
			}
		}

		// Invoke the pattern matching method on each file.
		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
			find(file);
			return FileVisitResult.CONTINUE;
		}

		// Invoke the pattern matching method on each directory.
		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
			find(dir);
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc) {
			return FileVisitResult.CONTINUE;
		}
	}

	
	
	public List<String> parse() {
		return parse(new File(DEFAULT_LD_SO_CONF));
	}
	
	public List<String> parse(File configFile)
	{
		List<String> paths = new ArrayList<String>();
		if (! configFile.isFile()) {
			// throw new IllegalArgumentException("No configuration file found for Linux dynamic linker.");
			return paths;
		}

		/*
		FileFinder finder = new FileFinder();
		try (
			FileInputStream fis = new FileInputStream(configFile);
			InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
			BufferedReader reader = new BufferedReader(isr);
		) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				if (line.startsWith("include ")) {
					String[] words = line.split("\\s");
					List<File> includedFiles = finder.find("/etc", words[1]);
					for (File file : includedFiles) {
						paths.addAll(parse(file));
					}
				} else {
					paths.add(line);
				}
			}
		} catch (IOException e) {
			throw new IllegalArgumentException("Error found while parsing the configuration file.");
		}
		
		return paths;
		*/
		
		LinkedHashSet<String> directories = new LinkedHashSet<String>();
		try {
			GlobFileFinder finder = new GlobFileFinder("ld.so.conf");
			Files.walkFileTree(Paths.get("/etc"), finder);
			for (Path path : finder.paths) {
				directories.add(path.toString());
			}
		} catch (IOException e) {
		}
		
		paths.addAll(directories);
		return paths;
	}
}
