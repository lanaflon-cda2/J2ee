package oth.presentation.controller.utilisateur;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import oth.metier.service.ServiceResponse;
import oth.metier.service.utilisateur.IServiceUtilisateur;
import oth.presentation.controller.AbstractGlobals;
import oth.presentation.controller.AccessValidator;
import oth.presentation.dto.utilisateur.EditInfoPersoByAdminDto;
import oth.presentation.validator.EditInfoPersoByAdminValidator;

/**
 * Controlleur pour l'écran de modification d'utilisateur par administrateur.
 * 
 * @author Phil9175
 * 		
 */
@Controller
@RequestMapping("/modifierUtilisateurAdmin")
public class ModifierUtilisateurAdminController {
	
	@Autowired
	public IServiceUtilisateur serviceUtilisateur;
	
	@Autowired
	public EditInfoPersoByAdminValidator editInfoPersoByAdminValidator;
	
	/**
	 * Affiche la page d'édition d'information par l'administrateur.
	 * 
	 * @param idUtilisateur
	 *            L'id utilisateur à afficher.
	 * @param model
	 *            Le modèle associé.
	 * @return Le nom de la JSP.
	 */
	@RequestMapping(method = RequestMethod.GET)
	String showEditInfoPersoByAdmin(final Integer idUtilisateur, final Model model, final HttpSession session) {
		// Check accès
		if (!AccessValidator.validateAccess(AbstractGlobals.PAGE_MODIFIER_UTILISATEUR_ADMIN,
				(String) session.getAttribute(AbstractGlobals.SESSION_ROLE_NAME))) {
			session.setAttribute(AbstractGlobals.GLOBAL_ERROR_MSG, "access.notAuthorized");
			return "redirect:/" + AbstractGlobals.PAGE_ACCUEIL;
		}
		final ServiceResponse serviceResponse = serviceUtilisateur.findByIdToEditInfoPersoByAdmin(idUtilisateur);
		final EditInfoPersoByAdminDto editInfoPersoByAdminDto = (EditInfoPersoByAdminDto) serviceResponse
				.getDataResult();
		model.addAttribute("editInfoPersoByAdminDto", editInfoPersoByAdminDto);
		return AbstractGlobals.PAGE_MODIFIER_UTILISATEUR_ADMIN;
	}
	
	/**
	 * Valide la modification d'information par l'utilisateur.
	 * 
	 * @param editInfoPersoAdminDto
	 *            DTO de l'édition d'information par l'administrateur.
	 * @param result
	 *            Le binding associé.
	 * @param session
	 *            La session.
	 * @param sessionStats
	 *            Le statut de la session.
	 * @return Le ModelAndView associé.
	 */
	@RequestMapping(method = RequestMethod.POST)
	ModelAndView validerEditInfoPersoByAdmin(
			final @ModelAttribute("editInfoPersoByAdminDto") EditInfoPersoByAdminDto editInfoPersoByAdminDto,
			final BindingResult result, final HttpSession session) {
		// Check accès
		if (!AccessValidator.validateAccess(AbstractGlobals.PAGE_MODIFIER_UTILISATEUR_ADMIN,
				(String) session.getAttribute(AbstractGlobals.SESSION_ROLE_NAME))) {
			session.setAttribute(AbstractGlobals.GLOBAL_ERROR_MSG, "access.notAuthorized");
			return new ModelAndView("redirect:/" + AbstractGlobals.PAGE_ACCUEIL);
		}
		
		// Validate utilisateur
		editInfoPersoByAdminValidator.validate(editInfoPersoByAdminDto, result);
		final ModelAndView model = new ModelAndView();
		// If no validation errors
		if (!result.hasErrors()) {
			final ServiceResponse serviceResponse = serviceUtilisateur
					.updateUtilisateurByAdmin(editInfoPersoByAdminDto);
			// If no service error
			if (!serviceResponse.isError()) {
				session.setAttribute(AbstractGlobals.GLOBAL_INFORMATION_MSG, serviceResponse.getMessage());
				model.setViewName("redirect:/" + AbstractGlobals.PAGE_LISTE_UTILISATEUR);
				return model;
			}
			session.setAttribute(AbstractGlobals.GLOBAL_ERROR_MSG, serviceResponse.getMessage());
		}
		model.addObject("editInfoPersoByAdminDto", editInfoPersoByAdminDto);
		model.setViewName(AbstractGlobals.PAGE_MODIFIER_UTILISATEUR_ADMIN);
		return model;
	}
	
	@InitBinder
	public void dataBinding(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
}
