package com.ironiacorp.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Run Java application.
 *
 */
public class SunJava extends Java
{
	boolean overrideBootClasspath;
	
	private Classpath bootClasspathReplacement;
	
	private Classpath bootClasspathSuffix;
	
	private Classpath bootClasspathPrefix;
	
	boolean checkJNI;
	
	boolean future;
	
	boolean enableAggressiveOptimizations;
	
	/*
	 * http://www.fasterj.com/articles/oraclecollectors1.shtml
	 */
	public enum YoungGarbageCollector
	{
		NONE,

		/**
		 * the serial copy collector uses one thread to copy surviving objects from Eden to
		 * Survivor spaces and between Survivor spaces until it decides they've been there long
		 * enough, at which point it copies them into the old generation.
		 */
		SERIAL_COPY,

		/**
		 * The parallel scavenge collector is like the Serial Copy collector, but uses multiple
		 * threads in parallel and has some knowledge of how the old generation is collected
		 * (essentially written to work with the serial and PS old gen collectors).
		 */
		PARALLEL_SCAVENGE_COPY,

		/**
		 * The parallel copy collector is like the Serial Copy collector, but uses multiple
		 * threads in parallel and has an internal 'callback' that allows an old generation
		 * collector to operate on the objects it collects (really written to work with the
		 * concurrent collector).
		 */
		PARALLEL_NEW,
		
	
		/**
		 * The garbage first collector uses the 'Garbage First' algorithm which splits up the
		 * heap into lots of smaller spaces, but these are still separated into Eden and
		 * Survivor spaces in the young generation for G1.
		 */
		G1,
	}

	public enum OldGarbageCollector
	{
		NONE,

		/**
		 * The serial mark-sweep collector uses a serial (one thread) full mark-sweep garbage
		 * collection algorithm, with optional compaction.
		 */
		MARK_SWEEP_COMPACT,
		
		/**
		 * Parallel scavenge mark-sweep collector is a parallelised version (i.e. uses multiple
		 * threads) of the MarkSweepCompact.
		 */
		PARALLEL_MARK_SWEEP_COMPACT,
		
		/**
		 * The concurrent collector is a garbage collection algorithm that attempts to do most
		 * of the garbage collection work in the background without stopping application threads
		 * while it works (there are still phases where it has to stop application threads, but
		 * these phases are attempted to be kept to a minimum). Note if the concurrent collector
		 * fails to keep up with the garbage, it fails over to the serial MarkSweepCompact
		 * collector (Serial Collection) for (just) the next GC.
		 */
		CMS,
		
		/**
		 * The garbage first collector uses the 'Garbage First' algorithm which splits up the
		 * heap into lots of smaller spaces.
		 */
		G1,
	}

	private YoungGarbageCollector youngGC;

	private OldGarbageCollector oldGC;

	int gcMaxPause;
	
	boolean gcEnableMultithreading;
	
	boolean gcEnableIncremetalMode;
	
	boolean gcEnableAutoCompact;
	
	int initialMemorySize;
	
	int maxMemorySize;

	int nurseryMemorySize;

	boolean useLargePages;

	boolean useCompressedReferences;
	
	boolean enableAttachmentMechanisms;
	
	int predictedClassesCount;
	
	
	public SunJava()
	{
		super();
		bootClasspathPrefix = new Classpath();
		bootClasspathReplacement = new Classpath();
		bootClasspathSuffix = new Classpath();
		java = os.findExecutable("java");
		verboseClasses = false;
		verboseGarbageCollection = false;
		verboseJNICalls = false;
		properties = new HashMap<String, String>();
	}
	
	/*
	public void run(File jar)
	{
		parameters.add("-jar");
		parameters.add(jar.getAbsolutePath());
	}
	*/
	
	/**
	 * Run a Java application.
	 * 
	 * Options are used as documented at:
	 * http://docs.oracle.com/javase/7/docs/technotes/tools/solaris/java.html
	 */
	public List<String> getJvmParameters()
	{
		List<String> parameters = new ArrayList<String>();
		boolean enableExperimental = false;
		

		
		// Override boot classpath
		if (overrideBootClasspath) {
			if (bootClasspathReplacement.size() > 0) {
				parameters.add("-Xbootclasspath:" + bootClasspathReplacement.getClasspath(os));
			}
			if (bootClasspathPrefix.size() > 0) {
				parameters.add("-Xbootclasspath/p:" + bootClasspathPrefix.getClasspath(os));
			}
			if (bootClasspathSuffix.size() > 0) {
				parameters.add("-Xbootclasspath/a:" + bootClasspathSuffix.getClasspath(os));
			}
		}
		
		// Enable further JNI checks
		if (checkJNI) {
			parameters.add("-Xcheck:jni");
		}
		
		// Enable advanced Java checks
		if (future) {
			parameters.add("-Xfuture");
		}
		
		// Configure memory
		if (initialMemorySize > 0) {
			parameters.add("-Xms" + initialMemorySize);
		}
		if (maxMemorySize > 0) {
			parameters.add("-Xmx" + maxMemorySize);
		}
		if (nurseryMemorySize > 0) {
			parameters.add("-Xmn" + nurseryMemorySize);
		}
		if (useLargePages) {
			parameters.add("-XX:+UseLargePages");
		} else {
			parameters.add("-XX:-UseLargePages");
		}
		if (useCompressedReferences) {
			parameters.add("-XX:+UseCOmpressedOops");
		} else {
			parameters.add("-XX:-UseCOmpressedOops");
		}
		
		// Control use of attachments to the JVM (JConsole, etc)
		if (enableAttachmentMechanisms) {
			parameters.add("-XX:-DisableAttachMechanism");
		} else {
			parameters.add("-XX:+DisableAttachMechanism");
		}
		
		if (enableAggressiveOptimizations) {
			parameters.add("-XX:+AggressiveOpts");
		}
		
		if (predictedClassesCount > 0) {
			parameters.add("-XX:PredictedClassLoadCount=" + predictedClassesCount);
			enableExperimental = true;
		}
		
		// Garbage collector configuration
		if (youngGC == YoungGarbageCollector.SERIAL_COPY && oldGC == OldGarbageCollector.MARK_SWEEP_COMPACT) {
			parameters.add("-XX:+UseSerialGC");
		} else if (youngGC == YoungGarbageCollector.G1 && oldGC == OldGarbageCollector.G1) {
			parameters.add("-XX:+UseG1GC");
		} else if (youngGC == YoungGarbageCollector.PARALLEL_SCAVENGE_COPY && oldGC == OldGarbageCollector.PARALLEL_MARK_SWEEP_COMPACT) {
			parameters.add("-XX:+UseParallelGC");
			parameters.add("-XX:+UseParallelOldGC");
			parameters.add("-XX:+UseAdaptiveSizePolicy");
		} else if (youngGC == YoungGarbageCollector.PARALLEL_NEW && oldGC == OldGarbageCollector.MARK_SWEEP_COMPACT) {
			parameters.add("-XX:+UseParNewGC");
		} else if (youngGC == YoungGarbageCollector.PARALLEL_NEW && oldGC == OldGarbageCollector.CMS) {
			parameters.add("-XX:+UseConcMarkSweepGC");
			parameters.add("-XX:+UseParNewGC");
		} else if (youngGC == YoungGarbageCollector.SERIAL_COPY && oldGC == OldGarbageCollector.CMS) {
			parameters.add("-XX:+UseConcMarkSweepGC");
			parameters.add("-XX:-UseParNewGC");
		}
		if (oldGC == OldGarbageCollector.CMS) {
			if (gcEnableAutoCompact) {
				parameters.add("-XX:+UseCMSCompactAtFullCollection");
			}
			if (gcEnableIncremetalMode) {
				parameters.add("-XX:+CMSIncrementalMode");
			}
			if (gcEnableMultithreading) {
				parameters.add("-XX:+CMSConcurrentMTEnabled");
			}
		}
		
			
		if (gcMaxPause > 0) {
			parameters.add("-XX:MaxGCPauseMillis=" + gcMaxPause);
		}
		
		if (enableExperimental) {
			parameters.add("-XX:+UnlockExperimentalVMOptions");
		}
		
		return parameters;
	}

}
