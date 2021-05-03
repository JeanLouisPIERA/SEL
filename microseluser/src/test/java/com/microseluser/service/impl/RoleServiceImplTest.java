package com.microseluser.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;



import com.microseluser.dao.IUserRepository;
import com.microseluser.entities.Role;
import com.microseluser.entities.User;
import com.microseluser.exceptions.EntityNotFoundException;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RoleServiceImplTest {
	
	@Mock
	private IUserRepository userRepository;
	
	@InjectMocks
	private RoleServiceImpl roleService;
	
	private Role role;
	private User user;
	private List<Role> roles;
	private List<User> users;
	private List<Role> rolesToTest;
	
	@Before
	public void setUp() {
		
	MockitoAnnotations.initMocks(this);
		
	role = new Role();
	user = new User();
	roles = new ArrayList<Role>();
	users = new ArrayList<User>();
	rolesToTest = new ArrayList<Role>();
	
	when(userRepository.findById("A")).thenReturn(Optional.empty());
	when(userRepository.findById("B")).thenReturn(Optional.of(user));
			
			
		}
		
	

	@Test
	public void testshowAllRolesByUserId_whenEntityNotFoundException() {
		
		user.setId("A");
		
		try {
			roles = roleService.showAllRolesByUserId("A");
		} catch (Exception e) {
			assertThat(e).isInstanceOf(EntityNotFoundException.class)
					.hasMessage("Il n'existe aucun compte correspond à votre recherche");
		}
		
	}
	
	@Test
	public void testshowAllRolesByUserId_withoutException() throws EntityNotFoundException {
		
		user.setId("B");
		users.add(user);
		role.setUsers(users);
		roles.add(role);
		user.setRoles(roles);
		
		rolesToTest = roleService.showAllRolesByUserId("B");
		Assert.assertTrue(rolesToTest.size()==1);

		
	}
		

}
