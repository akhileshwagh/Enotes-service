package com.example.serviceImpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.example.dto.UserDto;
import com.example.entity.Role;
import com.example.entity.User;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;
import com.example.service.UserService;
import com.example.util.Validation;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private Validation validation;

	@Autowired
	private ModelMapper mapper;

	@Override
	public Boolean register(UserDto userDto) {

		validation.userValidation(userDto);
		User user = mapper.map(userDto, User.class);

		setRole(userDto, user);

		User saveUser = userRepo.save(user);
		if (!ObjectUtils.isEmpty(saveUser)) {
			return true;
		}
		return false;
	}

	private void setRole(UserDto userDto, User user) {
		List<Integer> reqRoleId = userDto.getRoles().stream().map(r -> r.getId()).toList();
		List<Role> roles = roleRepo.findAllById(reqRoleId);
		user.setRoles(roles);
	}

}