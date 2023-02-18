package com.OnlineUpdate.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.OnlineUpdate.Dao.LoginRepository;
import com.OnlineUpdate.Entity.Login;



@Service
public interface LoginService {
	
	
	public Login login(String username, String password);
	
	
	

}