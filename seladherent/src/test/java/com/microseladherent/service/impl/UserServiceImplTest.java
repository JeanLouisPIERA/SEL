package com.microseladherent.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.microseladherent.dao.IRoleRepository;
import com.microseladherent.dao.IUserRepository;
import com.microseladherent.dto.UpdateUserDTO;
import com.microseladherent.dto.UserDTO;
import com.microseladherent.entities.Role;
import com.microseladherent.entities.RoleEnum;
import com.microseladherent.entities.User;
import com.microseladherent.entities.UserStatutEnum;
import com.microseladherent.exceptions.DeniedAccessException;
import com.microseladherent.exceptions.EntityAlreadyExistsException;
import com.microseladherent.exceptions.EntityNotFoundException;
import com.microseladherent.mapper.IUserMapper;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceImplTest {

	  @Mock
	  private IRoleRepository roleRepository;

	  @Mock
	  private IUserRepository userRepository;
	
   	  @Mock
	  private IUserMapper userMapper;

	  @InjectMocks
	  private UserServiceImpl userService;

	  private User adherent;
	  private User adherentActive;
	  private User adherentLocked;
	  private User adherentClosed;
	  private User bureau;
	  private User admin;
	  private Role roleAdherent;
	  private Role roleBureau;
	  private Role roleAdmin;
	  private List<Role> listeRolesAdherent;
	  private List<Role> listeRolesBureau;
	  private List<Role> listeRolesAdmin;
	  private User userTest;
	
	  @Before 
	  public void setUp() {
		  
		  roleAdherent = new Role((long) 0, RoleEnum.ADHERENT);
		  roleBureau = new Role((long) 1, RoleEnum.BUREAU);
		  roleAdmin = new Role((long)1, RoleEnum.ADMIN);
		  listeRolesAdherent = Arrays.asList(roleAdherent);
		  listeRolesBureau = Arrays.asList(roleAdherent, roleBureau);
		  listeRolesAdmin = Arrays.asList(roleAdherent, roleAdmin);
		  
		  adherent = new User((long)1, "adherent", "adherent@gmail.com", "adherent", "adherent");
		  adherent.setRoles(listeRolesAdherent);
		  
		  adherentActive = new User((long)1, "adherent", "adherent@gmail.com", "adherent", UserStatutEnum.ACTIVE, LocalDate.now());
		  adherentActive.setRoles(listeRolesAdherent);
		  
		  adherentLocked = new User((long)2, "adherent", "adherent@gmail.com", "adherent", UserStatutEnum.LOCKED, LocalDate.now());
		  adherentLocked.setDateBlocageDebut(LocalDate.now());
		  adherentLocked.setRoles(listeRolesAdherent);
		  
		  adherentClosed = new User((long)3, "adherent", "adherent@gmail.com", "adherent", UserStatutEnum.CLOSED, LocalDate.now());
		  adherentClosed.setDateClotureDebut(LocalDate.now());
		  adherentClosed.setRoles(listeRolesAdherent);
		  
		  bureau = new User((long)4,"bureau", "bureau@gmail.com", "bureau", UserStatutEnum.ACTIVE, LocalDate.now());
		  bureau.setRoles(listeRolesBureau);
		  
		  admin = new User((long)5, "admin", "admin@gmail.com", "admin", UserStatutEnum.ACTIVE, LocalDate.now());
		  admin.setRoles(listeRolesAdmin);
		  
		  when(userRepository.findById((long)0)).thenReturn(Optional.empty());
		  when(userRepository.findById((long)1)).thenReturn(Optional.of(adherentActive));
		  when(userRepository.findById((long)2)).thenReturn(Optional.of(adherentLocked));
		  when(userRepository.findById((long)3)).thenReturn(Optional.of(adherentClosed));
		  when(userRepository.findById((long)4)).thenReturn(Optional.of(bureau));
		  when(userRepository.findById((long)5)).thenReturn(Optional.of(admin));
		  when(userRepository.findByUsername("bureau")).thenReturn(Optional.of(bureau));
		  when(userRepository.findByEmail("bureau@gmail.com")).thenReturn(Optional.of(bureau));
		  when(userRepository.findByEmail("admin@gmail.com")).thenReturn(Optional.of(admin));
		  
		  when(roleRepository.findByRoleEnum(RoleEnum.ADHERENT)).thenReturn(roleAdherent);
		  when(roleRepository.findByRoleEnum(RoleEnum.BUREAU)).thenReturn(roleBureau);
		  when(roleRepository.findByRoleEnum(RoleEnum.ADMIN)).thenReturn(roleAdmin);
	  }
		  
			

	// ROLE ADHERENT //////////////////////////

	// Tests CREATE ADHERENT ************************************************************************************************


	@Test
	public void testCreateAccount_whenEntityAlreadyExistsException_withWrongUsername() {

		UserDTO adherentDTO = new UserDTO("bureau", "bureau1@gmail.com", "bureau1", "bureau1");

		try {
			userTest = userService.createAccount(adherentDTO);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(EntityAlreadyExistsException.class).hasMessage("Ce nom d'adhérent est déjà utilisé");
		}

	}

	
	  @Test public void testCreateAccount_whenEntityAlreadyExistsException_withWrongEmailAdress() {
	  
	  UserDTO adherentDTO = new UserDTO(); adherentDTO.setUsername("admin1");
	  adherentDTO.setAdresseMail("admin@gmail.com");
	  adherentDTO.setPassword("admin1");
	  adherentDTO.setPasswordConfirm("admin1");
	  
	  try { userTest = userService.createAccount(adherentDTO); } catch
	  (Exception e) {
	  assertThat(e).isInstanceOf(EntityAlreadyExistsException.class)
	  .hasMessage("Un compte d'adhérent existe déjà pour cette adresse mail"); } }
	  
	  
	  
	  @Test public void testCreateAccount_withoutException() throws Exception {
	  
	  Role roleAdherent = new Role(); roleAdherent.setRoleEnum(RoleEnum.ADHERENT);
	  
	  UserDTO adherentDTO = new UserDTO(); adherentDTO.setUsername("adherent");
	  adherentDTO.setAdresseMail("adherent@gmail.com");
	  adherentDTO.setPassword("adherent");
	  adherentDTO.setPasswordConfirm("adherent");
	    
	  when(userMapper.userDTOToUser(adherentDTO)).thenReturn(adherent);

	  when(userRepository.save(any(User.class))).thenReturn(adherentActive);

	  userTest = userService.createAccount(adherentDTO);
	  verify(userRepository, times(1)).save(any(User.class));
	  
	  Assert.assertTrue(userTest.equals(adherentActive)); }
	  
	  // Tests READ ADHERENT*************************************************************************
	  
	  
	  @Test public void testReadAccount_whenEntityNotFoundException() {
	  
	  try { userTest = userService.readAccount((long)0); } catch
	  (Exception e) { assertThat(e).isInstanceOf(EntityNotFoundException.class)
	  .hasMessage("Ce compte n'existe pas"); } }
	  
	  
	  @Test public void testReadAccount_withoutException() throws Exception {
	   
	  User userTest = userService.readAccount((long)1);
	  
		  Assert.assertTrue(userTest.getUsername().equals(adherentActive.
		  getUsername()));
		  Assert.assertTrue(userTest.getPassword().equals(adherentActive.
		  getPassword()));
		  Assert.assertTrue(userTest.getEmail().equals(adherentActive.getEmail()))
		  ;
	  }
	 
	// TESTS UPDATE ADHERENT*************************************************************************
  
	  
	  @Test public void testUpdateAccount_whenEntityNotFoundException() {
	  
	  UpdateUserDTO adherentDTO = new UpdateUserDTO("adherent","adherentA@gmail.com","adherent", "adherent");
	  
	  try { userTest = userService.updateAccount((long)0, adherentDTO); }
	  catch (Exception e) {
	  assertThat(e).isInstanceOf(EntityNotFoundException.class)
	  		.hasMessage("Ce compte n'existe pas"); } }
	 
	  

	  @Test public void testUpdateAccount_whenDeniedAccessException_withStatutClosed() {
	  
	  UpdateUserDTO adherentDTO = new UpdateUserDTO("adherentA","adherentA@gmail.com","adherent", "adherent");
  
	  try { userTest = userService.updateAccount((long)3, adherentDTO); }
	  catch (Exception e) {
	  assertThat(e).isInstanceOf(DeniedAccessException.class)
	  		.hasMessage("Modification impossible : ce compte est bloqué ou clôturé"
	  				
	  ); } }
	  
	  @Test public void testUpdateAccount_whenDeniedAccessException_withStatutLocked() {
		  
		  UpdateUserDTO adherentDTO = new UpdateUserDTO("adherentB","adherentB@gmail.com","adherent", "adherent");
	  
		  try { userTest = userService.updateAccount((long)2, adherentDTO); }
		  catch (Exception e) {
		  assertThat(e).isInstanceOf(DeniedAccessException.class)
		  		.hasMessage("Modification impossible : ce compte est bloqué ou clôturé"
		  ); } }
 
	  
	  	  @Test public void testUpdateAccount_whenEntityAlreadyExistsException_withSameUsername() {
		  
		  UpdateUserDTO adherentDTO = new UpdateUserDTO("bureau"," "," ", " ");
	  
		  try { userTest = userService.updateAccount((long)1, adherentDTO); }
		  catch (Exception e) {
		  assertThat(e).isInstanceOf(EntityAlreadyExistsException.class)
		  		.hasMessage("Ce nom d'adhérent est déjà utilisé"
		  ); } }
	  
	  	@Test public void testUpdateAccount_whenEntityAlreadyExistsException_withSameAdresseMail() {
			  
		  UpdateUserDTO adherentDTO = new UpdateUserDTO(" ","bureau@gmail.com"," ", " ");
	  
		  try { userTest = userService.updateAccount((long)1, adherentDTO); }
		  catch (Exception e) {
		  assertThat(e).isInstanceOf(EntityAlreadyExistsException.class)
		  		.hasMessage("Cette adresse mail est déjà utilisée"
		  ); } }
	  	
	  	@Test public void testUpdateAccount_withoutException() throws Exception {
	  		
  		  UpdateUserDTO adherentDTO = new UpdateUserDTO("adherent10","adherent10@gmail.com","adherent10", "adherent10");
		  
		  User adherent10 = new User((long)1,"adherent10","adherent10","adherent10@gmail.com",UserStatutEnum.ACTIVE, LocalDate.now());
				
		  when(userRepository.findByUsername("adherent10")).thenReturn(Optional.empty());
		  when(userRepository.findByEmail("adherent10@gmail.com")).thenReturn(Optional.empty());
		  
		  when(userRepository.save(any(User.class))).thenReturn(adherent10);
		  
		  User userTest = userService.updateAccount((long) 1, adherentDTO);
		  verify(userRepository, times(1)).save(any(User.class));
		  
		  Assert.assertTrue(userTest.equals(adherent10)); 
			  
	  	}
	  	
	  	
	  	
	 // TESTS CLOSE ADHERENT************************************************************************* 	
	  	
	  	@Test public void testCloseAccount_whenEntityNotFoundException() {
	  	    
	  	  when(userRepository.findById((long) 0)).thenReturn(Optional.empty());
	  	  
	  	  try { userTest = userService.closeAccount((long)0); }
	  	  catch (Exception e) {
	  	  assertThat(e).isInstanceOf(EntityNotFoundException.class)
	  	  		.hasMessage("Ce compte n'existe pas"); } }
	  	 
	  	  

	   @Test public void testCloseAccount_whenDeniedAccessException_withStatutClosed() {
	  		  		  
	  	  try { userTest = userService.closeAccount((long)3); }
	  	  catch (Exception e) {
	  	  assertThat(e).isInstanceOf(DeniedAccessException.class)
	  	  		.hasMessage("Modification impossible : ce compte est bloqué ou clôturé"
	  	  ); } }
	  	  
  	  @Test public void testCloseAccount_whenDeniedAccessException_withStatutLocked() {
  	 
	  	try { userTest = userService.closeAccount((long)2); }
	  	catch (Exception e) {
	  	assertThat(e).isInstanceOf(DeniedAccessException.class)
	  	  		.hasMessage("Modification impossible : ce compte est bloqué ou clôturé"
	  	);} }
  	  
  	  
  	@Test public void testCloseAccount_withoutException() throws Exception {
 
  		  when(userRepository.save(any(User.class))).thenReturn(adherentClosed);
		  
		  userTest = userService.closeAccount((long) 1);
		  verify(userRepository, times(1)).save(any(User.class));
		  
		  Assert.assertTrue(userTest.equals(adherentClosed)); 
		  
	}
  	
  	// TESTS REACTIVE ADHERENT************************************************************************* 	
  	
  	@Test public void testReactiveClosedAccount_whenEntityNotFoundException() {
  	    
  	  try { userTest = userService.reactiveAccount((long)0); }
  	  catch (Exception e) {
  	  assertThat(e).isInstanceOf(EntityNotFoundException.class)
  	  		.hasMessage("Ce compte n'existe pas"); } }
  	 
  	  

   @Test public void testReactiveClosedAccount_whenDeniedAccessException_withStatutActive() {
  		  			  	 
  	  try { userTest = userService.reactiveAccount((long)1); }
  	  catch (Exception e) {
  	  assertThat(e).isInstanceOf(DeniedAccessException.class)
  	  		.hasMessage("Modification impossible : seul un compte déjà clôturé peut être réactivé"
  	  ); } }
  	  
   @Test public void testReactiveClosedAccount_whenDeniedAccessException_withStatutLocked() {
		    
  	try { userTest = userService.reactiveAccount((long)2); }
  	catch (Exception e) {
  	assertThat(e).isInstanceOf(DeniedAccessException.class)
  	  		.hasMessage("Modification impossible : seul un compte déjà clôturé peut être réactivé"
  	);} }
	  
	  
	@Test public void testReactiveClosedAccount_withoutException() throws Exception {
			  		  
	  when(userRepository.save(any(User.class))).thenReturn(adherentActive);
	  
	  userTest = userService.reactiveAccount((long) 3);
	  verify(userRepository, times(1)).save(any(User.class));
	  
	  Assert.assertTrue(userTest.equals(adherentActive)); 
	  
}
  	
	// ROLE BUREAU //////////////////////////

	// Tests SHOW ALL ADHERENT ************************************************************************************************	
	
	
	@Test public void testShowAllUsers_withoutException() {
		
		List<User> users = new ArrayList<User>();
		users.add(adherentActive);
		
		when(userRepository.findAll()).thenReturn(users);
		
		List<User> results = userService.showAllUsers();
		
		Assert.assertTrue(results.contains(adherentActive)); 
		 
	}
	
	// Tests LOCK ADHERENT ************************************************************************************************	
	
		
	@Test public void testLockAccount_whenEntityNotFoundexception() {
	
		try { userTest = userService.lockAccount((long)0); }
	  	catch (Exception e) {
	  	assertThat(e).isInstanceOf(EntityNotFoundException.class)
	  	  		.hasMessage("Ce compte n'existe pas"
	  	);} }
	
	@Test public void testLockAccount_whenDeniedAccessException_withBureauAccount() {
	
		try { userTest = userService.lockAccount((long)4); }
	  	catch (Exception e) {
	  	assertThat(e).isInstanceOf(DeniedAccessException.class)
	  	  		.hasMessage("Blocage interdit : vous ne pouvez pas bloquer le compte d'un membre du bureau"
	  	);} }
	
	

	@Test public void testLockAccount_whenDeniedAccessException_withAdminAccount() {
	
		try { userTest = userService.lockAccount((long)5); }
	  	catch (Exception e) {
	  	assertThat(e).isInstanceOf(DeniedAccessException.class)
	  	  		.hasMessage("Blocage interdit : vous ne pouvez pas bloquer le compte d'un administrateur"
	  	);} }
	
	
	@Test public void testLockAccount_whenDeniedAccessException_withLockedAccount() {
	
		try { userTest = userService.lockAccount((long)2); }
	  	catch (Exception e) {
	  	assertThat(e).isInstanceOf(DeniedAccessException.class)
	  	  		.hasMessage("Blocage impossible : seul un compte adhérent actif peut être bloqué"
	  	);} }
	
	@Test public void testLockAccount_whenDeniedAccessException_withClosedAccount() {
	
		try { userTest = userService.lockAccount((long)3); }
	  	catch (Exception e) {
	  	assertThat(e).isInstanceOf(DeniedAccessException.class)
	  	  		.hasMessage("Blocage impossible : seul un compte adhérent actif peut être bloqué"
	  	);} }
	
	@Test public void testLockAccount_withoutException() throws Exception {

		when(userRepository.save(any(User.class))).thenReturn(adherentLocked);
		  
		userTest = userService.lockAccount((long) 1);
		verify(userRepository, times(1)).save(any(User.class));
	  
		Assert.assertTrue(userTest.equals(adherentLocked)); 
		 
	}
	
	// Tests UNLOCK ADHERENT ************************************************************************************************	
	
	@Test public void testUnlockAccount_whenEntityNotFoundexception() {
	
		try { userTest = userService.unlockAccount((long)0); }
	  	catch (Exception e) {
	  	assertThat(e).isInstanceOf(EntityNotFoundException.class)
	  	  		.hasMessage("Ce compte n'existe pas"
	  	);} }
	
	
	@Test public void testUnlockAccount_whenDeniedAccessException_withActiveAccount() {
		
		try { userTest = userService.unlockAccount((long)1); }
	  	catch (Exception e) {
	  	assertThat(e).isInstanceOf(DeniedAccessException.class)
	  	  		.hasMessage("Déblocage de compte impossible : seul un compte adhérent bloqué peut être débloqué"
	  	);} }
	
	@Test public void testunlockAccount_whenDeniedAccessException_withClosedAccount() {
		
		try { userTest = userService.unlockAccount((long)3); }
	  	catch (Exception e) {
	  	assertThat(e).isInstanceOf(DeniedAccessException.class)
	  	  		.hasMessage("Déblocage de compte impossible : seul un compte adhérent bloqué peut être débloqué"
	  	);} }
	
	@Test public void testunlockAccount_withoutException() throws Exception {
		when(userRepository.save(any(User.class))).thenReturn(adherentActive);
		  
		userTest = userService.unlockAccount((long) 2);
		verify(userRepository, times(1)).save(any(User.class));
	  
		Assert.assertTrue(userTest.equals(adherentActive)); 
		 
	}
	
	// ROLE ADMIN //////////////////////////

	// Tests UPDATE ADHERENT TO BUREAU ********************************************************************************	
		
		
	@Test public void testUpdateToBureau_whenEntityNotFoundexception() {
		
		try { userTest = userService.updateToBureau((long)0); }
	  	catch (Exception e) {
	  	assertThat(e).isInstanceOf(EntityNotFoundException.class)
	  	  		.hasMessage("Ce compte n'existe pas"
	  	);} }
	
	@Test public void testUpdateToBureau_whenDeniedAccessException_withBureauAccount() {
		
		try { userTest = userService.updateToBureau((long)4); }
	  	catch (Exception e) {
	  	assertThat(e).isInstanceOf(DeniedAccessException.class)
	  	  		.hasMessage("Mise à jour interdite : vous ne pouvez pas promouvoir un membre du bureau au bureau"
	  	);} }
	

	@Test public void testUpdateToBureau_whenDeniedAccessException_withAdminAccount() {
		
		try { userTest = userService.updateToBureau((long)5); }
	  	catch (Exception e) {
	  	assertThat(e).isInstanceOf(DeniedAccessException.class)
	  	  		.hasMessage("Mise à jour interdite : vous ne pouvez pas promouvoir un administrateur au bureau"
	  	);} }
	
	
	@Test public void testUpdateToBureau_whenDeniedAccessException_withLockedAccount() {
		
		try { userTest = userService.updateToBureau((long)2); }
	  	catch (Exception e) {
	  	assertThat(e).isInstanceOf(DeniedAccessException.class)
	  	  		.hasMessage("Mise à jour impossible : vous ne pouvez promouvoir au bureau qu'un adhérent dont le compte n'est ni clôturé ni bloqué"
	  	);} }
	
	@Test public void testUpdateToBureau_whenDeniedAccessException_withClosedAccount() {
		
		try { userTest = userService.updateToBureau((long)3); }
	  	catch (Exception e) {
	  	assertThat(e).isInstanceOf(DeniedAccessException.class)
	  	  		.hasMessage("Mise à jour impossible : vous ne pouvez promouvoir au bureau qu'un adhérent dont le compte n'est ni clôturé ni bloqué"
	  	);} }
	
	@Test public void testUpdateToBureau_withoutException() throws Exception {

		when(userRepository.save(any(User.class))).thenReturn(bureau);
		  
		userTest = userService.updateToBureau((long) 1);
		verify(userRepository, times(1)).save(any(User.class));
	  
		Assert.assertTrue(userTest.equals(bureau)); 
		 
	}
	
	// Tests CLOSE BUREAU TO ADHERENT ********************************************************************************	
	
	@Test public void testCloseBureau_whenEntityNotFoundexception() {
			
		try { userTest = userService.closeBureau((long)0); }
	  	catch (Exception e) {
	  	assertThat(e).isInstanceOf(EntityNotFoundException.class)
	  	  		.hasMessage("Ce compte n'existe pas"
	  	);} }
	
	@Test public void testCloseBureau_whenDeniedAccessException_withAdherentAccount() {
	
		try { userTest = userService.closeBureau((long)1); }
	  	catch (Exception e) {
	  	assertThat(e).isInstanceOf(DeniedAccessException.class)
	  	  		.hasMessage("Mise à jour interdite : vous ne pouvez pas retirer la fonction membre du bureau à un adhérent"
	  	);} }
	

	@Test public void testCloseBureau_whenDeniedAccessException_withAdminAccount() {
	
		try { userTest = userService.closeBureau((long)5); }
	  	catch (Exception e) {
	  	assertThat(e).isInstanceOf(DeniedAccessException.class)
	  	  		.hasMessage("Mise à jour interdite : vous ne pouvez pas retirer la fonction membre du bureau à un administrateur"
	  	);} }
	
	@Test public void testCloseBureau_withoutException() throws Exception {
				
		when(userRepository.findById((long) 1)).thenReturn(Optional.of(bureau));
		when(userRepository.save(any(User.class))).thenReturn(adherentActive);
		  
		userTest = userService.closeBureau((long) 1);
		verify(userRepository, times(1)).save(any(User.class));
	  
		Assert.assertTrue(userTest.equals(adherentActive)); 
		 
	}
	
	// Tests UPDATE BUREAU TO ADMIN ********************************************************************************	
	
	@Test public void testUpdateToAdmin_whenEntityNotFoundexception() {
	
		try { userTest = userService.updateToAdmin((long)0); }
	  	catch (Exception e) {
	  	assertThat(e).isInstanceOf(EntityNotFoundException.class)
	  	  		.hasMessage("Ce compte n'existe pas"
	  	);} }
	
	@Test public void testUpdateToAdmin_whenDeniedAccessException_withAdherentAccount() {
		
		try { userTest = userService.updateToAdmin((long)1); }
	  	catch (Exception e) {
	  	assertThat(e).isInstanceOf(DeniedAccessException.class)
	  	  		.hasMessage("Mise à jour interdite : vous ne pouvez pas promouvoir un adhérent à la fonction d'administrateur"
	  	);} }
	

	@Test public void testUpdateToAdmin_whenDeniedAccessException_withAdminAccount() {
		
		try { userTest = userService.updateToAdmin((long)5); }
	  	catch (Exception e) {
	  	assertThat(e).isInstanceOf(DeniedAccessException.class)
	  	  		.hasMessage("Mise à jour interdite : vous ne pouvez pas promouvoir un administrateur à la fonction d'administrateur"
	  	);} }
	
	@Test public void testUpdateToAdmin_withoutException() throws Exception {
		
		when(userRepository.save(any(User.class))).thenReturn(admin);
		  
		userTest = userService.updateToAdmin((long) 4);
		verify(userRepository, times(1)).save(any(User.class));
	  
		Assert.assertTrue(userTest.equals(admin)); 
		 
	}
	
	// Tests CLOSE BUREAU TO ADMIN ********************************************************************************
	
	@Test public void testCloseAdmin_whenEntityNotFoundexception() {
		
		try { userTest = userService.closeAdmin((long)0); }
	  	catch (Exception e) {
	  	assertThat(e).isInstanceOf(EntityNotFoundException.class)
	  	  		.hasMessage("Ce compte n'existe pas"
	  	);} }
	
	@Test public void testCloseAdmin_whenDeniedAccessException_withAdherentAccount() {
		
		try { userTest = userService.closeAdmin((long)1); }
	  	catch (Exception e) {
	  	assertThat(e).isInstanceOf(DeniedAccessException.class)
	  	  		.hasMessage("Mise à jour interdite : vous ne pouvez pas retirer la fonction d'administrateur à un adhérent"
	  	);} }
	

	@Test public void testCloseAdmin_whenDeniedAccessException_withBureauAccount() {
		
		try { userTest = userService.closeAdmin((long)4); }
	  	catch (Exception e) {
	  	assertThat(e).isInstanceOf(DeniedAccessException.class)
	  	  		.hasMessage("Mise à jour interdite : vous ne pouvez pas retirer la fonction d'administrateur à un membre du bureau"
	  	);} }
	
	@Test public void testCloseAdmin_withoutException() throws Exception {
			
		when(userRepository.save(any(User.class))).thenReturn(bureau);
		  
		userTest = userService.closeAdmin((long) 5);
		verify(userRepository, times(1)).save(any(User.class));
	  
		Assert.assertTrue(userTest.equals(bureau)); 
		 
	}
	
}
	
	

	  
	


