package br.com.demo.service.converter;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.demo.domain.PhoneEntity;
import br.com.demo.domain.UserEntity;
import br.com.demo.dtos.PhoneDto;
import br.com.demo.dtos.UserRequestDto;

@Component
public class UserRequestConverter implements Function<UserRequestDto, UserEntity> {
	
	@Autowired
	private PhoneRequestConverter phoneConverter;

	@Override
	public UserEntity apply(final UserRequestDto registerDto) {
		final UserEntity registerEntity = new UserEntity();
		registerEntity.setEmail(registerDto.getEmail());
		registerEntity.setName(registerDto.getName());
		registerEntity.setPassword(registerDto.getPassword());
		for (PhoneDto phoneDto: registerDto.getPhones()) {
			PhoneEntity phoneEntity = phoneConverter.apply(phoneDto);
			registerEntity.getPhones().add(phoneEntity);
		}
		return registerEntity;
	}

}
