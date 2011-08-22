package com.ironiacorp.datastructure.bst;

/**
 * Exception class for duplicate item errors
 * in search tree insertions.
 * 
 * @author Mark Allen Weiss
 */
public class DuplicateItem extends RuntimeException
{
    /**
	 * Class version.
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Construct this exception object.
     * 
     * @param message the error message.
     */
    public DuplicateItem( String message )
    {
        super( message );
    }
}
