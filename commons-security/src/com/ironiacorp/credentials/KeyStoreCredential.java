/*
 * Copyright (C) 2009 Marco Aurélio Graciotto Silva <magsilva@ironiacorp.com>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.ironiacorp.credentials;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.Map;

/**
 * Credential for accessing the password and key storage.
 */
public class KeyStoreCredential implements Credential<Map<String, Object>>
{
	public static final String ALIAS = "alias";
	private String alias;

	public static final String PASSWORD = "password";
	private String password;

	public static final String KEYSTORE = "keystore";
	private KeyStore keyStore;

	private void setKeyStoreFile(File keyStoreFile)
	{
		if (!keyStoreFile.exists()) {
			throw new IllegalArgumentException("Requested file does not exist or cannot be accessed by the current user");
		}

		if (!keyStoreFile.canRead()) {
			throw new IllegalArgumentException("Requested file cannot be read by the current user");
		}

		try {
			keyStore = KeyStore.getInstance("PKCS12");
			keyStore.load(new FileInputStream(keyStoreFile), password.toCharArray());
		} catch (Exception e) {
			keyStore = null;
			throw new IllegalArgumentException("Cannot open the required key store file", e);
		}
	}

	public KeyStoreCredential(File keyStoreFile, String alias, String password)
	{
		this.alias = alias;
		this.password = password;
		setKeyStoreFile(keyStoreFile);
	}

	public Map<String, Object> get()
	{
		Map<String, Object> credential = new HashMap<String, Object>();
		credential.put(KEYSTORE, keyStore);
		credential.put(ALIAS, alias);
		credential.put(PASSWORD, password);

		return credential;
	}
}
