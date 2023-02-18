package com.OnlineUpdate.Dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.OnlineUpdate.Entity.Login;

@Repository
@Transactional 
public interface LoginRepository extends JpaRepository<Login, Long>{
	
	 //@Query("SELECT LD FROM login LD where username= :username and password=:password")
		Login findByUsernameAndPassword1(String username, String password1);
		//Login findByUsername(String username);
		

}