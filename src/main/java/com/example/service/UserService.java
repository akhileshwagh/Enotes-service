package com.example.service;

import com.example.dto.LoginRequest;
import com.example.dto.LoginResponse;
import com.example.dto.UserDto;

public interface UserService {

	public Boolean register(UserDto userDto, String url) throws Exception;

	public LoginResponse login(LoginRequest loginRequest);

}
