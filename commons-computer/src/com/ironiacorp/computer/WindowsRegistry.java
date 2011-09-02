/* 
 * Java native interface to the Windows Registry API.
 * 
 * Authored by Timothy Gerard Endres
 * <mailto:time@gjt.org>  <http://www.trustice.com>
 * 
 * Changed by Maso Gato
 * <mailto:masogato@users.sourceforge.net>
 * Changelog:
 * 20050315 : If 'ICE_JNIRegistry.DLL' DLL is not installed, then 
 * static code try to load it from the 'registry.jar' directory. 
 * 
 * This work has been placed into the public domain.
 * You may use this work in any way and for any purpose you wish.
 *
 * THIS SOFTWARE IS PROVIDED AS-IS WITHOUT WARRANTY OF ANY KIND,
 * NOT EVEN THE IMPLIED WARRANTY OF MERCHANTABILITY. THE AUTHOR
 OF THIS SOFTWARE, ASSUMES _NO_ RESPONSIBILITY FOR ANY
 * CONSEQUENCE RESULTING FROM THE USE, MODIFICATION, OR
 * REDISTRIBUTION OF THIS SOFTWARE. 
 */

package com.ironiacorp.computer;

import java.io.*;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.*;

import com.ice.jni.registry.NoSuchKeyException;
import com.ice.jni.registry.RegistryException;
import com.ice.jni.registry.RegistryKey;

/**
 * The Registry class provides is used to load the native library DLL, as well
 * as a placeholder for the top level keys, error codes, and utility methods. <br>
 * Changelog:<br>
 */
public class WindowsRegistry {
	/**
	 * The following statics are the top level keys. Without these, there is no
	 * way to get "into" the registry, since the RegOpenSubkey() call requires
	 * an existing key which contains the subkey.
	 */
	private RegistryKey HKEY_CLASSES_ROOT;
	private RegistryKey HKEY_CURRENT_USER;
	private RegistryKey HKEY_LOCAL_MACHINE;
	private RegistryKey HKEY_USERS;
	private RegistryKey HKEY_PERFORMANCE_DATA;
	private RegistryKey HKEY_CURRENT_CONFIG;
	private RegistryKey HKEY_DYN_DATA;

	/**
	 * These are used by dumpHex().
	 */
	static final int ROW_BYTES = 16;
	static final int ROW_QTR1 = 3;
	static final int ROW_HALF = 7;
	static final int ROW_QTR2 = 11;

	/**
	 * This is a Hashtable which maps names to the top level keys.
	 */
	private static Hashtable topLevelKeys = null;

	public WindowsRegistry() {
		loadLibrary();
		initializeTopKeys();
	}

	/**
	 * Loads the DLL needed for the native methods, creates the toplevel keys,
	 * fills the hashtable that maps various names to the toplevel keys.
	 */
	private void loadLibrary() {
		try {
			System.loadLibrary("ICE_JNIRegistry");
		} catch (UnsatisfiedLinkError e) {
			loadLibraryFromJarDirectory();

		} catch (SecurityException e) {
			System.err
					.println("ERROR You do not have permission to load the DLL named '"
							+ "ICE_JNIRegistry.DLL'.\n\t" + e.getMessage());
		}
	}

	/**
	 * Try to load 'ICE_JNIRegistry.DLL' DLL from the 'registry.jar' directory.
	 */
	private void loadLibraryFromJarDirectory() {
		ClassLoader loader = RegistryKey.class.getClassLoader();
		URL urlJar = loader
				.getResource("com/ice/jni/registry/RegistryKey.class");

		try {
			URLConnection urlCon = urlJar.openConnection();
			if (!(urlCon instanceof JarURLConnection)) {
				// Try to load from the directory!
				System.err
						.println("ERROR You may have the REGISTRY api into JAR file.\n\t");
				return;
			}

			JarURLConnection jarCon = (JarURLConnection) urlCon;
			File jarFile = new File(jarCon.getJarFileURL().getFile());
			File directory = jarFile.getParentFile();
			String directoryPath = URLDecoder.decode(directory
					.getAbsolutePath());
			Runtime.getRuntime().load(
					directoryPath + File.separator + "ICE_JNIRegistry.DLL");
		} catch (IOException e) {
			System.err
					.println("ERROR You have not the DLL named '"
							+ "ICE_JNIRegistry.DLL' into same directory than JAR file.\n\t"
							+ e.getMessage());
		} catch (UnsatisfiedLinkError ule) {
			System.err.println("ERROR You have not installed the DLL named '"
					+ "ICE_JNIRegistry.DLL'.\n\t" + ule.getMessage());
		}
	}

	private void initializeTopKeys() {
		HKEY_CLASSES_ROOT = new RegistryKey(0x80000000, "HKEY_CLASSES_ROOT");
		HKEY_CURRENT_USER = new RegistryKey(0x80000001, "HKEY_CURRENT_USER");
		HKEY_LOCAL_MACHINE = new RegistryKey(0x80000002, "HKEY_LOCAL_MACHINE");
		HKEY_USERS = new RegistryKey(0x80000003, "HKEY_USERS");
		HKEY_PERFORMANCE_DATA = new RegistryKey(0x80000004,
				"HKEY_PERFORMANCE_DATA");
		HKEY_CURRENT_CONFIG = new RegistryKey(0x80000005, "HKEY_CURRENT_CONFIG");
		HKEY_DYN_DATA = new RegistryKey(0x80000006, "HKEY_DYN_DATA");

		WindowsRegistry.topLevelKeys = new Hashtable(16);
		topLevelKeys.put("HKCR", HKEY_CLASSES_ROOT);
		topLevelKeys.put("HKEY_CLASSES_ROOT", HKEY_CLASSES_ROOT);
		topLevelKeys.put("HKCU", HKEY_CURRENT_USER);
		topLevelKeys.put("HKEY_CURRENT_USER", HKEY_CURRENT_USER);
		topLevelKeys.put("HKLM", HKEY_LOCAL_MACHINE);
		topLevelKeys.put("HKEY_LOCAL_MACHINE", HKEY_LOCAL_MACHINE);
		topLevelKeys.put("HKU", HKEY_USERS);
		topLevelKeys.put("HKUS", HKEY_USERS);
		topLevelKeys.put("HKEY_USERS", HKEY_USERS);
		topLevelKeys.put("HKPD", HKEY_PERFORMANCE_DATA);
		topLevelKeys.put("HKEY_PERFORMANCE_DATA", HKEY_PERFORMANCE_DATA);
		topLevelKeys.put("HKCC", HKEY_PERFORMANCE_DATA);
		topLevelKeys.put("HKEY_CURRENT_CONFIG", HKEY_PERFORMANCE_DATA);
		topLevelKeys.put("HKDD", HKEY_PERFORMANCE_DATA);
		topLevelKeys.put("HKEY_DYN_DATA", HKEY_PERFORMANCE_DATA);
	}

	private void checkKey(RegistryKey topKey, String keyName, int access) {
		RegistryKey subKey = null;
		try {
			subKey = topKey.openSubKey(keyName, access);
		} catch (NoSuchKeyException e) {
			throw new IllegalArgumentException("Key '" + keyName + "' does not exist.", e);
		} catch (RegistryException e) {
			throw new IllegalArgumentException("Registry error", e);
		}
	}

	/**
	 * Get the description of a Registry error code.
	 * 
	 * @param errCode
	 *            The error code from a RegistryException
	 * @return The description of the error code.
	 */

	private void checkKey(String keyName) {
		if (keyName.startsWith("\\\\")) {
			throw new IllegalArgumentException(
					"Remote registry access is not allowed");
		}

		int index = keyName.indexOf('\\');
		if (index >= 0 && index < 4) {
			throw new IllegalArgumentException("Invalid key '" + keyName
					+ "', top level key name too short.");
		}

		// "topLevelKeyname\subKey\subKey\..."
		String topKeyName = keyName.substring(0, index);
		if ((index + 1) >= keyName.length()) {
			keyName = null;
		} else {
			keyName = keyName.substring(index + 1);
		}

		RegistryKey topKey = getTopLevelKey(topKeyName);
		if (topKey == null) {
			throw new IllegalArgumentException("Toplevel key '" + topKeyName
					+ "' could not be resolved");
		}
	}

	
	
	/**
	 * Get a top level key by name using the top level key Hashtable.
	 * 
	 * @param keyName
	 *            The name of the top level key.
	 * @return The top level RegistryKey, or null if unknown keyName.
	 * 
	 * @see topLevelKeys
	 */
	private RegistryKey getTopLevelKey(String keyName) {
		return (RegistryKey) topLevelKeys.get(keyName);
	}

	private String getString(RegistryKey topKey, String keyName, String valueName) {
		try {
			RegistryKey subKey = topKey.openSubKey(keyName, RegistryKey.ACCESS_READ); 
			if (subKey == null) {
				throw new IllegalArgumentException();
			}

			String value = subKey.getStringValue(valueName);
			
			return value;
		} catch (RegistryException e) {
			throw new IllegalArgumentException("Error getting value '" + valueName + "'", e);
		}
	}
	
	public String getString(String keyName)
	{
		int index = keyName.indexOf('\\');

		String topKeyName = keyName.substring(0, index);
		if ((index + 1) >= keyName.length()) {
			keyName = null;
		} else {
			keyName = keyName.substring(index + 1);
		}

		RegistryKey topKey = getTopLevelKey(topKeyName);
		if (topKey == null) {
			throw new IllegalArgumentException("Toplevel key '" + topKeyName
					+ "' could not be resolved");
		}

		int lastIndex = keyName.lastIndexOf("\\");
		String valueName = keyName.substring(lastIndex + 1);
		keyName = keyName.substring(0, lastIndex);
		
		return getString(topKey, keyName, valueName);
	}
}
