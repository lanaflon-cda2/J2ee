package oth.metier.mapper.panier;

import java.util.ArrayList;
import java.util.List;

import oth.persistance.bean.commande.CommandeProduitDo;
import oth.presentation.dto.panier.PanierDto;
import oth.presentation.dto.panier.bean.ElementPanierDto;

/**
 * Mapper des DTO liés au panier.
 * 
 * @author Phil9175
 *
 */

public class MapperPanier {

	/**
	 * Créé la liste de DO commande produit associée à un DTO panier.
	 * 
	 * @param panierDto
	 *            Le DTO d'édition des informations par l'administrateur à
	 *            mapper.
	 * @return La liste des DO commande produit mappée.
	 */
	public static List<CommandeProduitDo> createListeCommandeProduitDo(final PanierDto panierDto) {
		final List<CommandeProduitDo> liste=new ArrayList<CommandeProduitDo>();
		if (panierDto == null) {
			return new ArrayList<CommandeProduitDo>();
		}
		for (ElementPanierDto elem : panierDto.getContenu()) {
			liste.add(createCommandeProduitDo(elem));

		}

		return liste;
	}

	/**
	 * Créé le DO commande produit associé à un DTO élément panier.
	 * 
	 * @param elementPanierDto
	 *            Le DTO élément panier à mapper.
	 * @return Le DO commande produit mappé.
	 */
	public static CommandeProduitDo createCommandeProduitDo(final ElementPanierDto elementPanierDto) {
		if (elementPanierDto == null) {
			return null;
		}

		final CommandeProduitDo commandeProduit=new CommandeProduitDo();
		commandeProduit.setQuantite(elementPanierDto.getQuantite());
		commandeProduit.setDescriptionProduit(elementPanierDto.getProduit().getDescription());
		commandeProduit.setPrixUnitaire(elementPanierDto.getProduit().getPrix());
		commandeProduit.setQuantite(elementPanierDto.getQuantite());
		commandeProduit.setReferenceProduit(elementPanierDto.getProduit().getReference());
		commandeProduit.setPhotoProduit(elementPanierDto.getProduit().getPhoto());
		return commandeProduit;

	}

}
