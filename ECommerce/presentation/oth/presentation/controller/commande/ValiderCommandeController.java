package oth.presentation.controller.commande;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import oth.metier.service.ServiceResponse;
import oth.metier.service.commande.IServiceCommande;
import oth.presentation.controller.AbstractGlobals;
import oth.presentation.controller.AccessValidator;
import oth.presentation.dto.commande.ValiderCommandeDto;
import oth.presentation.dto.panier.PanierDto;
import oth.presentation.dto.utilisateur.bean.UtilisateurDto;
import oth.presentation.validator.CommandeValidator;

/**
 * Controlleur pour l'écran de la validation de commande.
 * 
 * @author Phil9175
 * 
 */
@Controller
@RequestMapping("/validerCommande")
public class ValiderCommandeController {

	@Autowired
	private IServiceCommande serviceCommande;

	@Autowired
	private CommandeValidator commandeValidator;

	/**
	 * Affiche la page de validation de commande.
	 * 
	 * @param model
	 *            Le modèle associé.
	 * @return Le nom de la JSP.
	 */
	@RequestMapping(method = RequestMethod.GET)
	String showValiderCommande(final Model model, final HttpSession session) {
		// Check accès
		if (!AccessValidator.validateAccess(AbstractGlobals.PAGE_VALIDATION_COMMANDE,
				(String) session.getAttribute(AbstractGlobals.SESSION_ROLE_NAME))) {
			session.setAttribute(AbstractGlobals.GLOBAL_ERROR_MSG, "access.notAuthorized");
			return "redirect:/" + AbstractGlobals.PAGE_ACCUEIL;
		}

		final ValiderCommandeDto validerCommandeDto = new ValiderCommandeDto();
		validerCommandeDto.setPanier((PanierDto) session.getAttribute(AbstractGlobals.PAGE_PANIER));

		model.addAttribute("validerCommandeDto", validerCommandeDto);
		return AbstractGlobals.PAGE_VALIDATION_COMMANDE;
	}

	/**
	 * Valide une commande.
	 * 
	 * @param validerCommandeDto
	 *            Le DTO de validation de commande.
	 * @param model
	 *            Le modèle associé.
	 * @return Le nom de la JSP.
	 */
	@RequestMapping(method = RequestMethod.POST)
	String validerCommande(final @ModelAttribute("validerCommandeDto") ValiderCommandeDto validerCommandeDto,
			final Model model, final HttpSession session, final BindingResult result) {
		// Check accès
		if (!AccessValidator.validateAccess(AbstractGlobals.PAGE_VALIDATION_COMMANDE,
				(String) session.getAttribute(AbstractGlobals.SESSION_ROLE_NAME))) {
			session.setAttribute(AbstractGlobals.GLOBAL_ERROR_MSG, "access.notAuthorized");
			return "redirect:/" + AbstractGlobals.PAGE_ACCUEIL;
		}
		validerCommandeDto.setPanier((PanierDto) session.getAttribute(AbstractGlobals.SESSION_PANIER_NAME));
		validerCommandeDto.setUtilisateurDto((UtilisateurDto) session.getAttribute(AbstractGlobals.SESSION_USER_NAME));
		commandeValidator.validate(validerCommandeDto, result);
		if (result.hasErrors()) {
			return AbstractGlobals.PAGE_VALIDATION_COMMANDE;
		}

		final ServiceResponse serviceResponse = serviceCommande.validerCommande(validerCommandeDto);
		if (serviceResponse.isError()) {
			session.setAttribute(AbstractGlobals.GLOBAL_ERROR_MSG, serviceResponse.getMessage());
			return "redirect:/" + AbstractGlobals.PAGE_VALIDATION_COMMANDE;
		}
		
		
		session.setAttribute(AbstractGlobals.GLOBAL_INFORMATION_MSG, serviceResponse.getMessage());
		session.removeAttribute(AbstractGlobals.SESSION_PANIER_NAME);
		return "redirect:/" + AbstractGlobals.PAGE_ACCUEIL;
	}

}
