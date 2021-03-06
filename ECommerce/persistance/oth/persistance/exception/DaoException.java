package oth.persistance.exception;

/**
 * Classe d'exception pour les problèmes liés aux DAO.
 * 
 * @author Phil9175
 *
 */
public class DaoException extends Exception {
	private static final long serialVersionUID = -2928281914399491138L;

	/**
	 * Constructeur par défaut.
	 */
	public DaoException() {
		// Empty Constructor
	}

	/**
	 * Constructeur de l'exception liée aux DAO.
	 * 
	 * @param message
	 *            Le message associé à l'exception.
	 */
	public DaoException(final String message) {
		super(message);
	}

}
