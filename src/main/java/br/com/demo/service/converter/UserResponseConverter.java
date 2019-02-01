package br.com.demo.service.converter;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.demo.domain.PhoneEntity;
import br.com.demo.domain.UserAuditEntity;
import br.com.demo.domain.UserEntity;
import br.com.demo.dtos.PhoneDto;
import br.com.demo.dtos.UserResponseDto;

@Component
public class UserResponseConverter implements Function<UserEntity, UserResponseDto> {
	
	@Autowired
	private PhoneResponseConverter phoneConverter;

	@Override
	public UserResponseDto apply(final UserEntity userEntity) {
		final UserResponseDto userResponseDto = new UserResponseDto();
		userResponseDto.setId(userEntity.getId());
		userResponseDto.setEmail(userEntity.getEmail());
		userResponseDto.setName(userEntity.getName());
		userResponseDto.setPassword(userEntity.getPassword());
		userResponseDto.setToken(userEntity.getToken());
		final UserAuditEntity userAuditEntity = userEntity.getUserAuditEntity();
		userResponseDto.setCreated(userAuditEntity.getCreated());
		userResponseDto.setModified(userAuditEntity.getModified());
		userResponseDto.setLastLogin(userAuditEntity.getLastLogin());
		for (PhoneEntity phoneEntity: userEntity.getPhones()) {
			PhoneDto phoneDto = phoneConverter.apply(phoneEntity);
			userResponseDto.getPhones().add(phoneDto);
		}
		return userResponseDto;
	}

}
