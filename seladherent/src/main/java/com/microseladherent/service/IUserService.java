package com.microseladherent.service;

import java.util.List;

import com.microseladherent.dto.UpdateUserDTO;
import com.microseladherent.dto.UserDTO;
import com.microseladherent.entities.User;
import com.microseladherent.exceptions.EntityAlreadyExistsException;
import com.microseladherent.exceptions.DeniedAccessException;
import com.microseladherent.exceptions.EntityNotFoundException;

public interface IUserService {
	
	//ROLE USER**************************************************************************************************
	
	/**
	 * Cette méthode permet à l'adhérent de créer son compte
	 * Il renseigne le UserDTO des informations du compte à créer
	 * La date de création du compte est enregistrée
	 * @throws EntityAlreadyExistsException 
	 */
	User createAccount(UserDTO userDTO) throws EntityAlreadyExistsException;
	
	
	/**
	 * Cette méthode permet à un adhérent de consulter son compte
	 * Seul un adhérent peut consulter les données de son compte quel que soit son statut ACTIVE, LOCKED ou CLOSED
	 * RGPD COMPLIANT
	 * @throws EntityNotFoundException 
	 */
	User readAccount(Long id) throws EntityNotFoundException; 
	
	
	/**
	 * Cette méthode permet à l'adhérent au statut ACTIVE de mettre à jour les infos de son compte (adresse mail, etc ...) 
	 * Seul un adhérent peut mettre à jour son compte mais seulement si son statut est ACTIVE 
	 * Il renseigne tous les éléments à modifier dans le UpdateUserDTO qui ont la validation constraint @NotEmpty
	 * RGPD COMPLIANT
	 * @throws EntityNotFoundException 
	 * @throws DeniedAccessException 
	 * @throws EntityAlreadyExistsException 
	 */
	User updateAccount(Long id, UpdateUserDTO updateUserDTO) throws EntityNotFoundException, DeniedAccessException, EntityAlreadyExistsException;
	
		
	/**
	 * Cette méthode permet à l'adhérent dont le statut du compte est ACTIVE de cloturer son compte 
	 * Le statut du compte passe à EXADHERENT ce qui l'empêche d'accéder à d'autres API
	 * Seul l'adhérent peut clôturer son compte
	 * La date de clôture est enregistrée
	 * Le compte reste persisté en respectant la CONTRAINTE ACID
	 * RGPD COMPLIANT
	 * @throws EntityNotFoundException 
	 * @throws DeniedAccessException 
	 */
	User closeAccount( Long id) throws EntityNotFoundException, DeniedAccessException;
	
	/**
	 * Cette méthode permet à l'adhérent dont le statut du compte est CLOSED de réactiver son compte 
	 * Le statut du compte repasse à ACTIVE et l'adhérent peut à nouveau accéder à d'autres API
	 * Seul l'adhérent peut réactiver son compte
	 * La date de fin de clôture est enregistrée
	 * Le compte reste persisté en respectant la CONTRAINTE ACID
	 * RGPD COMPLIANT
	 * @throws EntityNotFoundException 
	 * @throws DeniedAccessException 
	 */
	User reactiveAccount( Long id) throws EntityNotFoundException, DeniedAccessException;
	
	// ROLE BUREAU***********************************************************************************************
	
	/**
	 * Cette méthode permet à un membre du bureau d'obtenir la liste de tous les utilisateurs
	 * Seul un membre du bureau peut consulter cette liste
	 */
	List<User> showAllUsers();
	
	/**
	 * Cette méthode permet à un membre du Bureau de bloquer le compte d'un adhérent 
	 * L'adhérent peut continuer à consulter son compte = RGPD Compliant
	 * Mais le statut du compte de l'adhérent passe à LOCKED ce qui l'empêche d'accéder à d'autres API 
	 * La date de blocage est enregistrée
	 * @throws EntityNotFoundException 
	 * @throws DeniedAccessException 
	 */
	User lockAccount(Long id) throws EntityNotFoundException, DeniedAccessException;
	
	/**
	 * Cette méthode permet à un membre du Bureau de débloquer le compte d'un adhérent 
	 * L'adhérent peut consulter son compte = RGPD Compliant
	 * Comme le statut de son compte passe à LOCKED à ACTIVE il peut à nouveau accéder à d'autres API 
	 * La date de fin de blocage est enregistrée
	 * @throws EntityNotFoundException 
	 * @throws DeniedAccessException 
	 */
	User unlockAccount(Long id) throws EntityNotFoundException, DeniedAccessException;

	// ROLE ADMIN********************************************************************************************************
	
	/**
	 * Cette méthode permet à un administrateur de promouvoir un adhérent à un rôle de membre du Bureau
	 * La date de promotion au Bureau est enregistrée
	 * @throws EntityNotFoundException 
	 * @throws DeniedAccessException 
	 */
	User updateToBureau(Long id) throws EntityNotFoundException, DeniedAccessException;
	
	/**
	 * Cette méthode permet à un administrateur de rétrograder un membre du Bureau à un rôle d'Adhérent	
	 * La date de fin de promotion au Bureau est enregistrée
	 * @throws EntityNotFoundException 
	 * @throws DeniedAccessException 
	 */
	User closeBureau(Long id) throws EntityNotFoundException, DeniedAccessException;
	
	/**
	 * Cette méthode permet à un administrateur de promouvoir un membre du Bureau à un rôle d'administrateur
	 * La date de promotion en tant qu'administrateur est enregistrée
	 * @throws EntityNotFoundException 
	 * @throws DeniedAccessException 
	 */
	User updateToAdmin(Long id) throws EntityNotFoundException, DeniedAccessException;
			
	/**
	 * Cette méthode permet à un administrateur de rétrograder un admistrateur à un rôle de membre du Bureau
	 * La date de fin de promotion en tant qu'administrateur est enregistrée	
	 * @throws EntityNotFoundException 
	 * @throws DeniedAccessException 
	 */
	User closeAdmin(Long id) throws EntityNotFoundException, DeniedAccessException;


	
	
	
}
