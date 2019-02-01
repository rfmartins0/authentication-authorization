package br.com.demo.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import br.com.demo.domain.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, String> {

	public Optional<UserEntity> findByEmail(String email);

	public Optional<UserEntity> findByToken(String token);

}
