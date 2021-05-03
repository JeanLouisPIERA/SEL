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
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import com.microseluser.criteria.UserCriteria;
import com.microseluser.dao.IUserRepository;
import com.microseluser.dao.specs.UserSpecification;
import com.microseluser.entities.User;
import com.microseluser.exceptions.EntityNotFoundException;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceImplTest {

	@Mock
	private IUserRepository userRepository;

	@InjectMocks
	private UserServiceImpl userService;

	private User user;
	private User userTest;
	private List<User> users;
	private List<User> usersTest;
	private Pageable pageable;
	private UserCriteria userCriteria;
	private Page userPage;
	private Page userPageTest;

	@Before
	public void setUp() {

		MockitoAnnotations.initMocks(this);

		user = new User();
		userTest = new User();
		users = new ArrayList<User>();
		usersTest = new ArrayList<User>();
		pageable = PageRequest.of(0, 6);
		userCriteria = new UserCriteria();
		userPage = new PageImpl<User>(users);
		userPageTest = new PageImpl<User>(users);

		when(userRepository.findById("A")).thenReturn(Optional.empty());
		when(userRepository.findById("B")).thenReturn(Optional.of(user));
		when(userRepository.findAll()).thenReturn(users);

		when(userRepository.findAll(any(UserSpecification.class), any(Pageable.class))).thenReturn(userPage);

	}

	@Test
	public void testReadAccount_whenEntityNotFoundException() {

		user.setId("A");

		try {
			userTest = userService.readAccount("A");
		} catch (Exception e) {
			assertThat(e).isInstanceOf(EntityNotFoundException.class).hasMessage("Ce compte n'existe pas");
		}

	}

	@Test
	public void testReadAccount_withoutException() throws EntityNotFoundException {

		user.setId("B");

		userTest = userService.readAccount("B");
		Assert.assertTrue(userTest.equals(user));

	}

	@Test
	public void testShowAllUsers_withoutException() {

		usersTest = userService.showAllUsers();
		Assert.assertTrue(usersTest.equals(users));

	}

	@Test
	public void testSearchAllUsersByCriteria() {

		user.setUsername("B");
		users.add(user);
		pageable = PageRequest.of(0, 6);
		userCriteria = new UserCriteria();
		userPage = new PageImpl<User>(users);

		Page<User> userPageTest = userService.searchAllUsersByCriteria(userCriteria, pageable);
		verify(userRepository, times(1)).findAll(any(UserSpecification.class), any(Pageable.class));
		System.out.println(userPage.getNumberOfElements());
		Assertions.assertTrue(userPage.getNumberOfElements() == 1);
		Assertions.assertTrue(userPage.getTotalPages() == 1);
		

	}

}
