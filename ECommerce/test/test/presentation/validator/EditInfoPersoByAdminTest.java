package test.presentation.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import oth.presentation.dto.utilisateur.EditInfoPersoByAdminDto;
import oth.presentation.validator.EditInfoPersoByAdminValidator;



/**
 * Test de EditInfoPersoByAdminValidator
 * 
 * @author Phil9175
 *
 */
public class EditInfoPersoByAdminTest {

	/**
	 * Test d'un formulaire valide où l'on change le mot de passe
	 */
	@Test
	public void test_01_ChangePasswordValid() {
		final EditInfoPersoByAdminValidator editValidator=new EditInfoPersoByAdminValidator();
		final EditInfoPersoByAdminDto editDto=new EditInfoPersoByAdminDto();
		editDto.setNouveauMdp("password");
		editDto.setNouveauMdpConfirmation("password");
		final Errors errors=new BeanPropertyBindingResult(editDto, "editDto");
		editValidator.validate(editDto, errors);
		assertFalse(errors.hasErrors());
	}

	/**
	 * Test d'un formulaire vide , cela est autorisé mais ne change rien
	 */
	@Test
	public void test_02_NoChanges() {
		final EditInfoPersoByAdminValidator editValidator=new EditInfoPersoByAdminValidator();
		final EditInfoPersoByAdminDto editDto=new EditInfoPersoByAdminDto();
		final Errors errors=new BeanPropertyBindingResult(editDto, "editDto");
		editValidator.validate(editDto, errors);
		assertFalse(errors.hasErrors());
	}

	/**
	 * Test d'un formulaire avec le champ mot de passe rempli mais pas de
	 * confirmation
	 */
	@Test
	public void test_03_NoConfirmationPassword() {
		final EditInfoPersoByAdminValidator editValidator=new EditInfoPersoByAdminValidator();
		final EditInfoPersoByAdminDto editDto=new EditInfoPersoByAdminDto();
		editDto.setNouveauMdp("password");
		final Errors errors=new BeanPropertyBindingResult(editDto, "editDto");
		editValidator.validate(editDto, errors);
		assertTrue(errors.hasErrors());
	}

	/**
	 * Test d'un formulaire avec les champs mot de passe et confirmation
	 * différents.
	 */
	@Test
	public void test_04_DifferentPasswords() {
		final EditInfoPersoByAdminValidator editValidator=new EditInfoPersoByAdminValidator();
		final EditInfoPersoByAdminDto editDto=new EditInfoPersoByAdminDto();
		editDto.setNouveauMdp("password");
		editDto.setNouveauMdpConfirmation("passwrd");
		final Errors errors=new BeanPropertyBindingResult(editDto, "editDto");
		editValidator.validate(editDto, errors);
		assertTrue(errors.hasErrors());
	}

}
