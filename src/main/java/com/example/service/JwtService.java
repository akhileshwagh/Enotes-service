package com.example.service;

import com.example.entity.User;

public interface JwtService {

	public String generateToken(User user);
}
