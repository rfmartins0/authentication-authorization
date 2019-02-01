package br.com.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.demo.dtos.LoginDto;
import br.com.demo.dtos.UserResponseDto;
import br.com.demo.service.LoginService;

@RestController
public class LoginController {

	@Autowired
	private LoginService loginService;

	@PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserResponseDto> login(@RequestBody final LoginDto loginDto) {
		final UserResponseDto userResponseDto = this.loginService.login(loginDto);
		return new ResponseEntity<UserResponseDto>(userResponseDto,
				userResponseDto.getResponseHeaders(), HttpStatus.ACCEPTED);
	}

}
