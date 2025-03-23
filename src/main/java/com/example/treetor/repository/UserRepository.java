package com.example.treetor.repository;

import com.example.treetor.entity.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
	public Optional<UserModel> findByEmail(String email);


	@Modifying
	@Query("UPDATE UserModel u SET u.password = :password WHERE u.email = :email")
	void changePassword(@Param("email") String email, @Param("password") String password);

	@Query("SELECT u FROM UserModel  u where u.userKey=:userKey")
	public Optional<UserModel> findByUserKey(String userKey);


}
