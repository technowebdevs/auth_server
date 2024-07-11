package com.technoweb.authserver.service.impl;

import com.technoweb.authserver.dto.UserDto;
import com.technoweb.authserver.entity.User;
import com.technoweb.authserver.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class UserServiceImplTest {
	@Autowired
	private UserServiceImpl userServiceImpl;

	@MockBean
	private UserRepository userRepository;

	@Test
	public void createUser() {
		UserDto userDto = new UserDto();
		Optional<User> expected = null;
		Optional<User> actual = userServiceImpl.createUser(userDto);

		assertEquals(expected, actual);
	}
}
