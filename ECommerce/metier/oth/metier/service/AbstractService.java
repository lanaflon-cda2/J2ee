package oth.metier.service;

/**
 * Classe abstraite pour les réponses de services.
 * 
 * @author Phil9175
 *
 */
public abstract class AbstractService {
	/**
	 * Créé une réponse d'erreur suite à un appel de service.
	 * 
	 * @param message
	 *            Le message d'information associé.
	 * @return L'instance de ServiceResponse associée.
	 */
	protected ServiceResponse buildErrorResponse(final String message) {
		return this.buildResponse(true, message, null);
	}

	/**
	 * Créé une réponse de succès suite à un appel de service.
	 * 
	 * @param message
	 *            Le message d'information.
	 * @param dataResult
	 *            Le résultat de l'appel au service (objet dto généralement).
	 * @return L'instance de ServiceResponse associée.
	 */
	protected ServiceResponse buildResponse(final String message, final Object dataResult) {
		return this.buildResponse(false, message, dataResult);
	}

	/**
	 * Créé une réponse de service.
	 * 
	 * @param error
	 *            Le flag d'erreur (true si erreur, false sinon).
	 * @param message
	 *            Le message d'information
	 * @param dataResult
	 *            La donnée résultat à l'appel au service (objet dto
	 *            généralement).
	 * @return L'instance de ServiceResponse associée.
	 */
	private ServiceResponse buildResponse(final boolean error, final String message, final Object dataResult) {
		final ServiceResponse serviceResponse = new ServiceResponse();
		serviceResponse.setError(error);
		serviceResponse.setMessage(message);
		serviceResponse.setDataResult(dataResult);
		return serviceResponse;
	}
}
