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
 
Copyright (C) 2007 Marco Aurelio Graciotto Silva <magsilva@gmail.com>
*/

package com.ironiacorp.persistence.hibernate;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.cfg.Configuration;
// import org.hibernate.connection.ConnectionProvider;
// import org.hibernate.connection.ConnectionProviderFactory;
import org.hibernate.dialect.Dialect;
// import org.hibernate.tool.hbm2ddl.DatabaseMetadata;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;

import com.ironiacorp.datastructure.array.ArrayUtil;
import com.ironiacorp.persistence.dao.ideais.DataSource;

public abstract class GenericHibernateDataSource implements DataSource
{
	/**
	* Commons Logging instance.
	*/
	static Log log = LogFactory.getLog(GenericHibernateDataSource.class);

	/**
	 * Configuration used by Hibernate, responsable for mapping the objects to an
	 * relational database.
	 */
	protected Configuration hibernateConfig;


	public GenericHibernateDataSource()
	{
		super();
	}

	/**
	 * Get the current Hibernate configuration.
	 * 
	 * @return Hibernate configuration.
	 */
	public Configuration getConfig()
	{
		return hibernateConfig;
	}
	
	/**
	 * Check the exceptions thrown while running the update or create
	 * scripts for the current Hibernate configuration.
	 *
	 * @param ddl The SchemaExport or SchemaUpdate used.
	 * @throws IllegalArgumentException If the argument is not a SchemaExport or
	 * SchemaUpdate instance.
	 */
	private List handleExceptions(Object ddl)
	{
		if (! (ddl instanceof SchemaExport ^ ddl instanceof SchemaUpdate)) {
			throw new IllegalArgumentException();
		}
		
		List exceptions = null;
		List<Exception> errors = new LinkedList<Exception>();
		
		if (ddl instanceof SchemaExport) {
			SchemaExport schema = (SchemaExport) ddl;
			exceptions = schema.getExceptions();
		} else {
			SchemaUpdate schema = (SchemaUpdate) ddl;
			exceptions = schema.getExceptions();
		}
		
		if (! exceptions.isEmpty()) {
			for (Object o : exceptions) {
				Exception e = (Exception) o;
			
				if (e instanceof SQLException) {
					// Filter exceptions.
					String dialect = hibernateConfig.getProperty("dialect");
					if (dialect.equals("org.hibernate.dialect.MySQLDialect") ||
							dialect.equals("org.hibernate.dialect.MySQLInnoDBDialect") ||
							dialect.equals("org.hibernate.dialect.MySQLMyISAMDialect")) {
						e = handleMySQLError((SQLException) e);
					}
				}
				
				if (e != null) {
					errors.add(e);
				}			
			}
		}
		return errors;
	}
	
	/**
	 * Create the database.
	 * 
	 * @throws RuntimeException If an error is found when running the DDL script.
	 */
	public void createDB()
	{
		log.debug("Creating the database");
		log.debug(getCreateDDLScript());
		SchemaExport ddl = new SchemaExport(hibernateConfig);
		List exceptions = null;
		
		ddl.create(false, true);
		exceptions = handleExceptions(ddl);
		if (! exceptions.isEmpty()) {
			throw new RuntimeException("exception.bootstrap.createdb", (Exception) ddl.getExceptions().get(0));
		}
	}

	/**
	 * Update the database.
	 * 
	 * @throws RuntimeException If an error is found when running the DDL script.
	 */
	public void updateDB()
	{
		log.debug("Updating the database");
		log.debug(getUpdateDDLScript());
		SchemaUpdate ddl = new SchemaUpdate(hibernateConfig);
		List exceptions = null;

		ddl.execute(false, true);
		exceptions = handleExceptions(ddl);
		if (! exceptions.isEmpty()) {
			throw new RuntimeException("exception.bootstrap.updatedb", (Exception) ddl.getExceptions().get(0));
		}
	}

	
	/**
	 * Drop the entire database.
	 * 
	 * @throws RuntimeException If an error is found when running the DDL script.
	 */
	public void dropDB()
	{
		log.debug("Dropping the database");
		log.debug(getDropDDLScript());
		SchemaExport ddl = new SchemaExport(hibernateConfig);
		List exceptions = null;

		ddl.drop(false, true);
		exceptions = handleExceptions(ddl);
		if (! exceptions.isEmpty()) {
			throw new RuntimeException("exception.bootstrap.dropdb", (Exception) ddl.getExceptions().get(0));
		}
	}
	
	
	/**
	 * Get the DDL script to drop the database.
	 */
	public String getDropDDLScript()
	{
		Dialect dialect = Dialect.getDialect(hibernateConfig.getProperties());
		String[] script = hibernateConfig.generateDropSchemaScript(dialect);
		return ArrayUtil.toString(script);
	}
	 	
	/**
	 * Get the DDL script to create the database.
	 */
	public String getCreateDDLScript()
	{
		Dialect dialect = Dialect.getDialect(hibernateConfig.getProperties());
		String[] script = hibernateConfig.generateSchemaCreationScript(dialect);
		return ArrayUtil.toString( script );
	}

	/**
	 * Get the DDL script to update the database.
	 * TODO: http://stackoverflow.com/questions/10607196/how-to-get-database-metadata-from-entity-manager
	 */
	public String getUpdateDDLScript()
	{
		Dialect dialect = Dialect.getDialect(hibernateConfig.getProperties());
        Properties props = new Properties();
//        ConnectionProvider connectionProvider = null;
        DatabaseMetadata dm = null;

        props.putAll(dialect.getDefaultProperties());
        props.putAll(hibernateConfig.getProperties());
 /*       connectionProvider = ConnectionProviderFactory.newConnectionProvider(props);
        
        try {
        	dm = new DatabaseMetadata(connectionProvider.getConnection(), dialect);
        } catch ( SQLException e ) {
        	log.debug("Could not get database DDL script", e);
        }
	*/			
		String[] script = hibernateConfig.generateSchemaUpdateScript(dialect, dm);
		return ArrayUtil.toString( script );
	}

	
	/**
	 * Handles an specific MySQL error.
	 * 
	 * @param sqle The SQLException to be analysed.
	 * 
	 * @return The exception (if the error is unrecoverable) or null if the
	 * problem was solved (or simply ignored).
	 */
	public Exception handleMySQLError(SQLException sqle)
	{
		switch (sqle.getErrorCode()) {
			case 1146:
				log.debug("Ignoring error", sqle);
				sqle = null;
				break;
			default:
				log.debug("Cannot recover from error", sqle);
		}
		return sqle;
	}

	public Object instance()
	{
		return this;
	}

	public void setConfiguration(Configuration conf)
	{
		// Just ignore it.	
	}

}
