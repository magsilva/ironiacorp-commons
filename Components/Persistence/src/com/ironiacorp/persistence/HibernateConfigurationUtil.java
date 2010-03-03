package com.ironiacorp.persistence;
/*
This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

Copyright (C) 2005 Marco Aurelio Graciotto Silva <magsilva@gmail.com>
*/



import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import java.io.File;

import javax.naming.Binding;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.Context;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.util.DTDEntityResolver;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

/**
 * The DataSource is used to change the data source configuration used by
 * Wiki/RE.
 * 
 * @author Marco Aurélio Graciotto Silva
 * 
 */
public final class HibernateConfigurationUtil
{
	/**
	 * Commons Logging instance.
	 */
	private static Log log = LogFactory.getLog(HibernateConfigurationUtil.class);

	private String contextPath;

	private static String configFileSufix = File.separator + "WEB-INF" + File.separator + "classes"
			+ File.separator + "hibernate.cfg.xml";

	private Map<String, String> preferences;

	private Document config;

	public static String JDBC_TYPE = "JDBC";
	public static String JNDI_TYPE = "JNDI";

	/**
	 * 
	 */
	public HibernateConfigurationUtil(String contextPath)
	{
		this.contextPath = contextPath;
		log.debug("Context path set to " + this.contextPath);

		this.preferences = new HashMap<String, String>();
		preferences.put("connection.driver_class", null);
		preferences.put("connection.url", null);
		preferences.put("connection.username", null);
		preferences.put("connection.password", null);
		preferences.put("dialect", null);
		preferences.put("hibernate.connection.datasource", null);
		preferences.put("hibernate.connection.username", null);
		preferences.put("hibernate.connection.password", null);
		preferences.put("currentDatasource", null);

		load();
	}

	/**
	 * Load the current hibernate.cfg.xml.
	 */
	public void load()
	{
		File configFile = new File(contextPath + configFileSufix);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder parser = null;

		log.debug("Loading configuration from file " + configFile.getAbsolutePath());
		try {
			factory.setValidating(true);
			factory.setNamespaceAware(false);
			// BUG 1: It try to validate.
			// BUG 2: 'null', following the specification, is a valid value.
			// Unfortunately,
			// the current implementation fails on it.
			// factory.setSchema( null );
			parser = factory.newDocumentBuilder();
			parser.setEntityResolver(new DTDEntityResolver());
			config = parser.parse(configFile);
		} catch (Exception e) {
			log.debug("Error loading the configuration: ", e);
		}
		NodeList propertiesNodes = config.getElementsByTagName("property");
		for (int i = 0; i < propertiesNodes.getLength(); i++) {
			Node propertyNode = propertiesNodes.item(i);

			// Get the property name
			NamedNodeMap attributesNodes = propertyNode.getAttributes();
			Node attributeNode = attributesNodes.getNamedItem("name");
			String property = attributeNode.getNodeValue();

			// Get the property value
			NodeList childrenNodes = propertyNode.getChildNodes();
			String value = null;
			for (int j = 0; j < childrenNodes.getLength(); j++) {
				Node childNode = childrenNodes.item(j);
				if (childNode.getNodeType() == Node.TEXT_NODE) {
					value = childNode.getNodeValue();
					break;
				}
			}

			if (property.equals("connection.driver_class") || property.equals("connection.url")
					|| property.equals("connection.username") || property.equals("connection.password")
					|| property.equals("dialect") || property.equals("hibernate.connection.datasource")
					|| property.equals("hibernate.connection.username")
					|| property.equals("hibernate.connection.password")) {
				preferences.put(property, value);
			}
		}
	}

	public void save()
	{
		NodeList propertiesNodes = config.getElementsByTagName("property");
		Node parentNode = config.getElementsByTagName("session-factory").item(0);

		for (int i = 0; i < propertiesNodes.getLength(); i++) {
			Node propertyNode = propertiesNodes.item(i);

			// Get the property name
			NamedNodeMap attributesNodes = propertyNode.getAttributes();
			Node attributeNode = attributesNodes.getNamedItem("name");
			String property = attributeNode.getNodeValue();

			if (property.equals("connection.driver_class") || property.equals("connection.url")
					|| property.equals("connection.username") || property.equals("connection.password")
					|| property.equals("dialect") || property.equals("hibernate.connection.datasource")
					|| property.equals("hibernate.connection.username")
					|| property.equals("hibernate.connection.password")) {
				parentNode.removeChild(propertyNode);
			}
		}

		for (Map.Entry<String, String> property : preferences.entrySet()) {
			if (property.getValue() != null) {
				Element propertyNode = config.createElement("property");
				propertyNode.setAttribute("name", (String) property.getKey());
				// TODO: propertyNode.setTextContent((String) property.getValue());
				parentNode.appendChild(propertyNode);
			}
		}

		try {
			File configFile = new File(contextPath + configFileSufix);
			Transformer t = TransformerFactory.newInstance().newTransformer();
			t.transform(new DOMSource(config), new StreamResult(configFile));
		} catch (TransformerConfigurationException tce) {
		} catch (TransformerException te) {
		}

	}

	/**
	 * Get current dialect in use.
	 */
	public String getDialect()
	{
		return preferences.get("dialect");
	}

	/**
	 * Set current dialect in use.
	 */
	public void setDialect(String dialect)
	{
		log.debug("Set the dialect to " + dialect);
		preferences.put("dialect", dialect);
	}

	/**
	 * Get the datasource type in use.
	 * 
	 * @return The datasource type (JDBC or JNDI).
	 */
	public String getDataSourceType()
	{
		if (preferences.containsKey("connection.driver_class")) {
			return HibernateConfigurationUtil.JNDI_TYPE;
		}
		if (preferences.containsKey("hibernate.connection.datasource")) {
			return HibernateConfigurationUtil.JDBC_TYPE;
		}
		return null;
	}

	/**
	 * Get the JDBC driver (if any).
	 */
	public String getJDBCDriver()
	{
		return preferences.get("connection.driver_class");
	}

	/**
	 * Set the JDBC driver.
	 * 
	 * @param driver
	 *            The class name for the JDBC driver to be used.
	 */
	public void setJDBCDriver(String driver)
	{
		log.debug("Set the JDBC driver to " + driver);
		preferences.remove("hibernate.connection.datasource");
		preferences.put("connection.driver_class", driver);
	}

	/**
	 * Get the data source name (JNDI).
	 */
	public String getDataSourceName()
	{
		return preferences.get("hibernate.connection.datasource");
	}

	/**
	 * Set the data source name (JNDI).
	 * 
	 * @param name
	 *            The data source name.
	 */
	public void setDataSourceName(String name)
	{
		log.debug("Set the JNDI data source name to " + name);
		preferences.remove("connection.driver_class");
		preferences.put("hibernate.connection.datasource", name);
	}

	/**
	 * Get the URL used to access the chosen data source.
	 */
	public String getURL()
	{
		return preferences.get("connection.url");
	}

	/**
	 * Set the URL used to access the chosen data source.
	 * 
	 * @param url
	 *            The URL used to connect to the data source (usually a JDBC
	 *            style URL).
	 */
	public void setURL(String url)
	{
		log.debug("Set the URL to " + url);
		preferences.put("connection.url", url);
	}

	/**
	 * Get the username used to connect to the datasource.
	 */
	public String getUsername()
	{
		if (preferences.containsKey("connection.username")) {
			return preferences.get("connection.username");
		}
		if (preferences.containsKey("hibernate.connection.username")) {
			return preferences.get("hibernate.connection.username");
		}
		return null;
	}

	/**
	 * Change the username used to connect to the data source.
	 * 
	 * @param username
	 *            The username to be used.
	 */
	public void setUsername(String username)
	{
		log.debug("Set the username to " + username);

		preferences.remove("connection.username");
		preferences.remove("hibernate.connection.username");

		if (getDataSourceType().equals(HibernateConfigurationUtil.JNDI_TYPE)) {
			preferences.put("hibernate.connection.username", username);
		}
		if (getDataSourceType().equals(HibernateConfigurationUtil.JDBC_TYPE)) {
			preferences.put("connection.username", username);
		}
	}

	/**
	 * Check if the password was set.
	 */
	public String getPassword()
	{
		if (preferences.containsKey("connection.password")) {
			return preferences.get("connection.password");
		}
		if (preferences.containsKey("hibernate.connection.password")) {
			return preferences.get("hibernata.connection.password");
		}
		return null;
	}

	/**
	 * Change the password used to connect to the data source.
	 * 
	 * @param password
	 *            The password to be used.
	 */
	public void setPassword(String password)
	{
		log.debug("Set the password");

		preferences.remove("connection.password");
		preferences.remove("hibernate.connection.password");

		if (getDataSourceType().equals(HibernateConfigurationUtil.JNDI_TYPE)) {
			preferences.put("hibernate.connection.password", password);
		}
		if (getDataSourceType().equals(HibernateConfigurationUtil.JDBC_TYPE)) {
			preferences.put("connection.password", password);
		}
	}

	/**
	 * Retrieve the data sources registered at JNDI.
	 * 
	 * @return A collection of the data sources found.
	 */
	public static Collection<String> getAvailableDataSources()
	{
		Context initCtx = null;
		Context envCtx = null;

		try {
			initCtx = new InitialContext();
			envCtx = (Context) initCtx.lookup("java:comp/env");
		} catch (NamingException e) {
			return new ArrayList<String>(0);
		}
		return getAvailableDataSources(envCtx);
	}

	/**
	 * Retrieve the data sources registered at JNDI in the given context.
	 * 
	 * @param namingContext
	 *            Start point context.
	 * 
	 * @return A collection of the data sources found within the context.
	 */
	private static Collection<String> getAvailableDataSources(Context namingContext)
	{
		Collection<String> datasources = new ArrayList<String>();
		Class<?> type = null;

		// Acquire global JNDI resources if available
		try {
			type = Class.forName("xyz");
		} catch (ClassNotFoundException e) {
		}

		try {
			NamingEnumeration<Binding> items = namingContext.listBindings("");
			while (items.hasMore()) {
				Binding item = (Binding) items.next();
				if (item.getObject() instanceof Context) {
					datasources.addAll(getAvailableDataSources((Context) item.getObject()));
				} else {
					if (type.isInstance(item.getObject())) {
						datasources.add(item.getName());
					}
				}
			}
		} catch (Throwable t) {
		}

		return datasources;
	}

	/**
	 * Get a listing of JDBC drivers supported by Wiki/RE. This listing is
	 * provided as a mapping (String, String), more precisely (Java class name,
	 * Description).
	 * 
	 * @return A mapping of JDBC drivers.
	 */
	public static Map<String, String> getSupportedJDBC()
	{
		Map<String, String> drivers = new HashMap<String, String>();
		drivers.put("com.mysql.jdbc.Driver", "resource.jdbc.MySQL");
		drivers.put("org.hsqldb.jdbcDriver", "resource.jdbc.Hsqldb");
		return drivers;
	}

	/**
	 * Get a listing of Hibernate dialects. This listing is provided as a
	 * mapping (String, String), more precisely (Java class name, Description).
	 * 
	 * @return A mapping of Hibernate dialects.
	 */
	public static Map<String, String> getHibernateDialects()
	{
		Map<String, String> dialects = new HashMap<String, String>();
		dialects.put("org.hibernate.dialect.DB2Dialect", "resource.hibernateDialect.DB2");
		dialects.put("org.hibernate.dialect.DB2390Dialect", "resource.hibernateDialect.DB2390");
		dialects.put("org.hibernate.dialect.DB2400Dialect", "resource.hibernateDialect.DB2400");
		dialects.put("org.hibernate.dialect.FirebirdDialect", "resource.hibernateDialect.Firebird");
		dialects.put("org.hibernate.dialect.FrontbaseDialect", "resource.hibernateDialect.FrontBase");
		dialects.put("org.hibernate.dialect.HSQLDialect", "resource.hibernateDialect.HSQLDB");
		dialects.put("org.hibernate.dialect.InformixDialect", "resource.hibernateDialect.Informix");
		dialects.put("org.hibernate.dialect.IngresDialect", "resource.hibernateDialect.Ingres");
		dialects.put("org.hibernate.dialect.InterbaseDialect", "resource.hibernateDialect.Interbase");
		dialects.put("org.hibernate.dialect.MckoiDialect", "resource.hibernateDialect.Mckoi");
		dialects.put("org.hibernate.dialect.MySQLDialect", "resource.hibernateDialect.MySQL");
		dialects.put("org.hibernate.dialect.MySQLInnoDBDialect", "resource.hibernateDialect.MySQLInnoDB");
		dialects.put("org.hibernate.dialect.MySQLMyISAMDialect", "resource.hibernateDialect.MySQLMyISAM");
		dialects.put("org.hibernate.dialect.OracleDialect", "resource.hibernateDialect.Oracle");
		dialects.put("org.hibernate.dialect.Oracle9Dialect", "resource.hibernateDialect.Oracle9");
		dialects.put("org.hibernate.dialect.PointbaseDialect", "resource.hibernateDialect.Pointbase");
		dialects.put("org.hibernate.dialect.PostgreSQLDialect", "resource.hibernateDialect.PostgreSQL");
		dialects.put("org.hibernate.dialect.ProgressDialect", "resource.hibernateDialect.Progres");
		dialects.put("org.hibernate.dialect.SAPDBDialect", "resource.hibernateDialect.SAPDB");
		dialects.put("org.hibernate.dialect.SQLServerDialect", "resource.hibernateDialect.SQLServer");
		dialects.put("org.hibernate.dialect.SybaseDialect", "resource.hibernateDialect.Sybase");
		dialects.put("org.hibernate.dialect.SybaseAnywhereDialect",
				"resource.hibernateDialect.SybaseAnywhere");
		return dialects;
	}
}
