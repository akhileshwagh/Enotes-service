package com.example.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.entity.AccountStatus;
import com.example.entity.User;
import com.example.exception.ResourceNotFoundException;
import com.example.exception.SuccessException;
import com.example.repository.UserRepository;
import com.example.service.HomeService;

@Component
public class HomeServiceImpl implements HomeService {

	@Autowired
	private UserRepository userRepo;

	@Override
	public Boolean verifyAccount(Integer userId, String verificationCode) throws Exception {

		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("invalid user"));

		if (user.getStatus().getVerificationCode() == null) {
			throw new SuccessException("Account already verified");
		}

		if (user.getStatus().getVerificationCode().equals(verificationCode)) {
			AccountStatus status = user.getStatus();
			status.setIsActive(true);
			status.setVerificationCode(null);

			userRepo.save(user);

			return true;
		}

		return false;
	}

}
