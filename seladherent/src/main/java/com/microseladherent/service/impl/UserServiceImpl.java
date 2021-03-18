package com.microseladherent.service.impl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.ws.rs.core.Response;

/*import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;*/
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.microseladherent.dao.IRoleRepository;
import com.microseladherent.dao.IUserRepository;
import com.microseladherent.dto.UpdateUserDTO;
import com.microseladherent.dto.UserDTO;
import com.microseladherent.entities.Role;
import com.microseladherent.entities.RoleEnum;
import com.microseladherent.entities.User;
import com.microseladherent.entities.UserStatutEnum;
import com.microseladherent.exceptions.EntityAlreadyExistsException;
import com.microseladherent.exceptions.DeniedAccessException;
import com.microseladherent.exceptions.EntityNotFoundException;
import com.microseladherent.mapper.IUserMapper;
import com.microseladherent.service.IUserService;

@Service
public class UserServiceImpl implements IUserService
{
	
	@Autowired
	private IUserRepository userRepository;
	@Autowired
	private IRoleRepository roleRepository;
	@Autowired
	private IUserMapper userMapper;
	
	
	
	private Role roleAdherent = new Role((long) 0, RoleEnum.ADHERENT);
	private Role roleBureau = new Role((long) 1, RoleEnum.BUREAU);
	private Role roleAdmin = new Role((long)1, RoleEnum.ADMIN);
	private List<Role> listeRolesAdherent = Arrays.asList(roleAdherent);
	private List<Role> listeRolesBureau = Arrays.asList(roleAdherent, roleBureau);
	private List<Role> listeRolesAdmin = Arrays.asList(roleAdherent, roleAdmin);
	
	//ROLE ADHERENT***********************************************************************************************
	
	/**
	 * Cette méthode permet à l'adhérent de créer son compte
	 * Il renseigne le UserDTO des informations du compte à créer
	 * La date de création du compte est enregistrée
	 * @throws EntityAlreadyExistsException 
	 */
	
	  @Override public User createAccount(UserDTO userDTO) throws
	  EntityAlreadyExistsException{
	  
	  Optional<User> usernameAlreadyExists =
	  userRepository.findByUsername(userDTO.getUsername()); if
	  (usernameAlreadyExists.isPresent()) throw new
	  EntityAlreadyExistsException("Ce nom d'adhérent est déjà utilisé");
	  
	  Optional<User> adresseMailAlreadyExists =
	  userRepository.findByEmail(userDTO.getAdresseMail());
	  if(adresseMailAlreadyExists.isPresent()) throw new
	  EntityAlreadyExistsException("Un compte d'adhérent existe déjà pour cette adresse mail"
	  );
	  
	  User userToCreate = userMapper.userDTOToUser(userDTO);
	  
	  userToCreate.setDateAdhesion(LocalDate.now());
	  userToCreate.setStatut(UserStatutEnum.ACTIVE); List<Role> roleAdherent =
	  Arrays.asList(roleRepository.findByRoleEnum(RoleEnum.ADHERENT));
	  userToCreate.setRoles(roleAdherent); return
	  userRepository.save(userToCreate); }
	 
	
	


	
	/**
	 * Cette méthode permet à un adhérent de consulter son compte
	 * Seul un adhérent peut consulter les données de son compte quel que soit son statut ACTIVE, LOCKED ou CLOSED
	 * RGPD COMPLIANT
	 * @throws EntityNotFoundException 
	 */
	@Override
	public User readAccount(Long id) throws EntityNotFoundException {
		
		Optional<User> userFound = userRepository.findById(id);
		if(!userFound.isPresent())
			throw new EntityNotFoundException("Ce compte n'existe pas");
		
		return userFound.get();
		
	}
		
	/**
	 * Cette méthode permet à l'adhérent au statut ACTIVE de mettre à jour les infos de son compte (adresse mail, etc ...) 
	 * Seul un adhérent peut mettre à jour son compte mais seulement si son statut est ACTIVE 
	 * Il renseigne tous les éléments à modifier dans le UpdateUserDTO qui ont la validation constraint @NotEmpty
	 * RGPD COMPLIANT
	 * @throws EntityNotFoundException 
	 * @throws DeniedAccessException 
	 * @throws EntityAlreadyExistsException 
	 */
	@Override
	public User updateAccount(Long id, UpdateUserDTO updateUserDTO) throws EntityNotFoundException, DeniedAccessException, EntityAlreadyExistsException  {
		
		Optional<User> userFound = userRepository.findById(id);
		if(!userFound.isPresent())
			throw new EntityNotFoundException("Ce compte n'existe pas");
		if(!userFound.get().getStatut().getCode().equals("ACTIVE"))
			throw new DeniedAccessException("Modification impossible : ce compte est bloqué ou clôturé");	
		

		if(!updateUserDTO.getUsername().isBlank()) {
		Optional<User> usernameAlreadyExists = userRepository.findByUsername(updateUserDTO.getUsername());
			if (usernameAlreadyExists.isPresent()) 
				throw new EntityAlreadyExistsException("Ce nom d'adhérent est déjà utilisé"); 
			//OK On change le Username
			userFound.get().setUsername(updateUserDTO.getUsername());
			}
	
		if(!updateUserDTO.getAdresseMail().isBlank()) {
			Optional<User> adresseMailAlreadyExists = userRepository.findByEmail(updateUserDTO.getAdresseMail());
			if (adresseMailAlreadyExists.isPresent()) 
				throw new EntityAlreadyExistsException("Cette adresse mail est déjà utilisée"); 
			//OK on change l'adresse email
			userFound.get().setEmail(updateUserDTO.getAdresseMail());
			}
		
		if(!updateUserDTO.getPassword().isBlank()) {
			//OK on change le password
			userFound.get().setPassword(updateUserDTO.getPassword());
			}
		
		return userRepository.save(userFound.get());
	}

	
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
	@Override
	public User closeAccount(Long id) throws EntityNotFoundException, DeniedAccessException{
		
		Optional<User> userFound = userRepository.findById(id);
		if(!userFound.isPresent())
			throw new EntityNotFoundException("Ce compte n'existe pas");
		if(!userFound.get().getStatut().getCode().equals("ACTIVE"))
			throw new DeniedAccessException("Modification impossible : ce compte est bloqué ou clôturé");	
	
		LocalDate dateClotureDebut = LocalDate.now();
		
		userFound.get().setDateClotureDebut(dateClotureDebut);
		userFound.get().setStatut(UserStatutEnum.CLOSED);
		
		return userRepository.save(userFound.get());
		
	}
	
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
	@Override
	public User reactiveAccount( Long id) throws EntityNotFoundException, DeniedAccessException {
		
		Optional<User> userFound = userRepository.findById(id);
		if(!userFound.isPresent())
			throw new EntityNotFoundException("Ce compte n'existe pas");
		if(!userFound.get().getStatut().getCode().equals("CLOSED"))
			throw new DeniedAccessException("Modification impossible : seul un compte déjà clôturé peut être réactivé");	
		
		LocalDate dateClotureFin = LocalDate.now();
		
		userFound.get().setDateClotureFin(dateClotureFin);
		userFound.get().setStatut(UserStatutEnum.ACTIVE);
		
		return userRepository.save(userFound.get());
		
	}	
	
	// ROLE BUREAU**************************************************************************************************

	
	 /**
      * Cette méthode permet à un membre du bureau d'obtenir la liste de tous les utilisateurs
	  * Seul un membre du bureau peut consulter cette liste
	 */	
	  @Override public List<User> showAllUsers() {
	  
	  List<User> listUsers = userRepository.findAll();
	   
	  for (User user : listUsers) { user.setPassword(null);
	  user.setPasswordConfirm(null); }
	  
	  return listUsers; }
	  
	  
	  /**
	   	* Cette méthode permet à un membre du Bureau de bloquer le compte d'un adhérent 
	   	* L'adhérent peut continuer à consulter son compte = RGPD Compliant
	   	* Mais le statut du compte de l'adhérent passe à LOCKED ce qui l'empêche d'accéder à d'autres API 
	   	* La date de blocage est enregistrée
	   	* @throws EntityNotFoundException 
	   	* @throws DeniedAccessException 
	   	*/
	  @Override public User lockAccount(Long id) throws EntityNotFoundException, DeniedAccessException {
	  
	  Optional<User> userFound = userRepository.findById(id);
	  
		if(!userFound.isPresent())
			throw new EntityNotFoundException("Ce compte n'existe pas");
		
		Role admin = roleRepository.findByRoleEnum(RoleEnum.ADMIN);
		if(userFound.get().getRoles().contains(admin))
			throw new DeniedAccessException("Blocage interdit : vous ne pouvez pas bloquer le compte d'un administrateur");
		
		Role bureau = roleRepository.findByRoleEnum(RoleEnum.BUREAU);
		if(userFound.get().getRoles().contains(bureau))
			throw new DeniedAccessException("Blocage interdit : vous ne pouvez pas bloquer le compte d'un membre du bureau");
		
		if(!userFound.get().getStatut().getCode().equals("ACTIVE"))
			throw new DeniedAccessException("Blocage impossible : seul un compte adhérent actif peut être bloqué");	
		
		userFound.get().setDateBlocageDebut(LocalDate.now());
		userFound.get().setStatut(UserStatutEnum.LOCKED);
		
		return userRepository.save(userFound.get());
		  
	  }
		   
	  
	  /**
		 * Cette méthode permet à un membre du Bureau de débloquer le compte d'un adhérent 
		 * L'adhérent peut consulter son compte = RGPD Compliant
		 * Comme le statut de son compte passe à LOCKED à ACTIVE il peut à nouveau accéder à d'autres API 
		 * La date de fin de blocage est enregistrée
		 * @throws EntityNotFoundException 
		 * @throws DeniedAccessException 
		 */
	  @Override public User unlockAccount(Long id) throws EntityNotFoundException, DeniedAccessException {
		  
	  Optional<User> userFound = userRepository.findById(id);
  
	  if(!userFound.isPresent())
			throw new EntityNotFoundException("Ce compte n'existe pas");
		
	  if(!userFound.get().getStatut().getCode().equals("LOCKED"))
		  	throw new DeniedAccessException("Déblocage de compte impossible : seul un compte adhérent bloqué peut être débloqué");	
		
		userFound.get().setDateBlocageFin(LocalDate.now());
		userFound.get().setStatut(UserStatutEnum.ACTIVE);
		
		return userRepository.save(userFound.get());
		  
	  }
	 
	 
	
	// ROLE ADMIN****************************************************************************************************
		
	
	  /**
		 * Cette méthode permet à un administrateur de promouvoir un adhérent à un rôle de membre du Bureau
		 * La date de promotion au Bureau est enregistrée
		 * @throws EntityNotFoundException 
		 * @throws DeniedAccessException 
		 */
	  @Override public User updateToBureau(Long id) throws EntityNotFoundException, DeniedAccessException{
		  
	  Optional<User> userFound = userRepository.findById(id);
	  
		if(!userFound.isPresent())
			throw new EntityNotFoundException("Ce compte n'existe pas");
		
		if(userFound.get().getRoles().contains(roleRepository.findByRoleEnum(RoleEnum.ADMIN)))
			throw new DeniedAccessException("Mise à jour interdite : vous ne pouvez pas promouvoir un administrateur au bureau");
		
		if(userFound.get().getRoles().contains(roleRepository.findByRoleEnum(RoleEnum.BUREAU)))
			throw new DeniedAccessException("Mise à jour interdite : vous ne pouvez pas promouvoir un membre du bureau au bureau");
		
		if(!userFound.get().getStatut().getCode().equals("ACTIVE"))
			throw new DeniedAccessException("Mise à jour impossible : vous ne pouvez promouvoir au bureau qu'un adhérent dont le compte n'est ni clôturé ni bloqué");	
		
		userFound.get().setDateBureauDebut(LocalDate.now());
		userFound.get().setRoles(listeRolesBureau);
		
		return userRepository.save(userFound.get());
		  
	  }  

	  /**
		 * Cette méthode permet à un administrateur de rétrograder un membre du Bureau à un rôle d'Adhérent	
		 * La date de fin de promotion au Bureau est enregistrée
		 * @throws EntityNotFoundException 
		 * @throws DeniedAccessException 
		 */
	  @Override public User closeBureau(Long id) throws EntityNotFoundException, DeniedAccessException {
	  
	  Optional<User> userFound = userRepository.findById(id);
	  
		if(!userFound.isPresent())
			throw new EntityNotFoundException("Ce compte n'existe pas");
		
		if(userFound.get().getRoles().contains(roleRepository.findByRoleEnum(RoleEnum.ADHERENT))&&userFound.get().getRoles().size()==1)
			throw new DeniedAccessException("Mise à jour interdite : vous ne pouvez pas retirer la fonction membre du bureau à un adhérent");
		
		if(userFound.get().getRoles().contains(roleRepository.findByRoleEnum(RoleEnum.ADMIN)))
			throw new DeniedAccessException("Mise à jour interdite : vous ne pouvez pas retirer la fonction membre du bureau à un administrateur");
		
		
		userFound.get().setDateBureauFin(LocalDate.now());
		userFound.get().setRoles(listeRolesAdherent);
		return userRepository.save(userFound.get());
	  
	  }
	  
	  
	  /**
		 * Cette méthode permet à un administrateur de promouvoir un membre du Bureau à un rôle d'administrateur
		 * La date de promotion en tant qu'administrateur est enregistrée
		 * @throws EntityNotFoundException 
		 * @throws DeniedAccessException 
		 */
	  @Override public User updateToAdmin(Long id) throws EntityNotFoundException, DeniedAccessException {
	  
	  Optional<User> userFound = userRepository.findById(id);
	  
		if(!userFound.isPresent())
			throw new EntityNotFoundException("Ce compte n'existe pas");
		
		if(userFound.get().getRoles().contains(roleRepository.findByRoleEnum(RoleEnum.ADHERENT))&&userFound.get().getRoles().size()==1)
			throw new DeniedAccessException("Mise à jour interdite : vous ne pouvez pas promouvoir un adhérent à la fonction d'administrateur");
		
		if(userFound.get().getRoles().contains(roleRepository.findByRoleEnum(RoleEnum.ADMIN)))
			throw new DeniedAccessException("Mise à jour interdite : vous ne pouvez pas promouvoir un administrateur à la fonction d'administrateur");
		
		
		userFound.get().setDateAdminDebut(LocalDate.now());
		userFound.get().setRoles(listeRolesAdmin);
		
		return userRepository.save(userFound.get());
	  
	  }
	  
	  /**
		 * Cette méthode permet à un administrateur de rétrograder un admistrateur à un rôle de membre du Bureau
		 * La date de fin de promotion en tant qu'administrateur est enregistrée	
		 * @throws EntityNotFoundException 
		 * @throws DeniedAccessException 
		 */
	  @Override public User closeAdmin(Long id) throws EntityNotFoundException, DeniedAccessException {
	  
		  Optional<User> userFound = userRepository.findById(id);
		  
			if(!userFound.isPresent())
				throw new EntityNotFoundException("Ce compte n'existe pas");
			
			if(userFound.get().getRoles().contains(roleRepository.findByRoleEnum(RoleEnum.ADHERENT))&&userFound.get().getRoles().size()==1)
				throw new DeniedAccessException("Mise à jour interdite : vous ne pouvez pas retirer la fonction d'administrateur à un adhérent");
			
			if(userFound.get().getRoles().contains(roleRepository.findByRoleEnum(RoleEnum.BUREAU)))
				throw new DeniedAccessException("Mise à jour interdite : vous ne pouvez pas retirer la fonction d'administrateur à un membre du bureau");
			
		
			userFound.get().setDateBureauFin(LocalDate.now());
			userFound.get().setRoles(listeRolesBureau);
			
			return userRepository.save(userFound.get());
		  
		  }

	



	
		 
	
}
	
	

	



