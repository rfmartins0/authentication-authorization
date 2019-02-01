package br.com.demo.service;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.demo.domain.UserEntity;
import br.com.demo.dtos.LoginDto;
import br.com.demo.dtos.UserResponseDto;
import br.com.demo.exception.LoginException;
import br.com.demo.exception.SessionException;
import br.com.demo.repository.UserRepository;
import br.com.demo.security.SecurityUtil;
import br.com.demo.service.converter.UserResponseConverter;

@Service
public class LoginService {
	
	@Autowired
	private UserRepository registerRepository;
	
	@Autowired
	private SecurityUtil securityUtil;
	
	@Autowired
	private UserResponseConverter userResponseConverter;

	public UserResponseDto login(final LoginDto loginDto) throws LoginException {
		final Optional<UserEntity> optionalRegister = this.registerRepository.findByEmail(loginDto.getEmail());
		if (optionalRegister.isPresent() && isPasswordsMatch(loginDto.getPassword(), optionalRegister.get())) {
			final UserEntity userEntity = optionalRegister.get();
			final String hashPassword = securityUtil.createJWT(userEntity.getEmail());
			userEntity.setToken(hashPassword);
			this.registerRepository.save(userEntity);
			return userResponseConverter.apply(userEntity);
		}
		throw new LoginException("Usuário e/ou senha inválidos");
	}
	
	public boolean checkToken(final String token) throws LoginException, SessionException{
		Optional<UserEntity> userEntity = this.registerRepository.findByToken(token);
		if (!userEntity.isPresent()) {
			throw new LoginException("Usuário e/ou senha inválidos");
		}
		if(this.securityUtil.isExpired(token)) {
			throw new SessionException("Sessão expirada");
		}
		return true;
	}
	
	private boolean isPasswordsMatch(final String password, final UserEntity registerEntity) {
		return this.securityUtil.comparePasswords(password, registerEntity.getPassword());
	}
	

}
