package com.ironiacorp.commons.compiler;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class JavaCompiler
{
	public boolean compile() throws IOException
	{
		javax.tools.JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();

		StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);
		// int results = compiler.run(null, null, null, "Hello.java");
		// System.out.println("Result code: + " + results);

		Iterable<? extends JavaFileObject> compilationUnits = fileManager
				.getJavaFileObjectsFromStrings(Arrays.asList("Hello.java"));
		javax.tools.JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, null, null,
				compilationUnits);
		Boolean result = task.call();
		
		List<Diagnostic<? extends JavaFileObject>> diags = diagnostics.getDiagnostics();
		Iterator<Diagnostic<? extends JavaFileObject>> i = diags.iterator();
		while (i.hasNext()) {
			Diagnostic<? extends JavaFileObject> diagnostic = i.next();
			System.console().printf(
					"Code: %s%n" + "Kind: %s%n" + "Position: %s%n" + "Start Position: %s%n"
							+ "End Position: %s%n" + "Source: %s%n" + "Message:  %s%n", diagnostic.getCode(),
					diagnostic.getKind(), diagnostic.getPosition(), diagnostic.getStartPosition(),
					diagnostic.getEndPosition(), diagnostic.getSource(), diagnostic.getMessage(null));
		}
		fileManager.close();
		
		return result;
	}
}
