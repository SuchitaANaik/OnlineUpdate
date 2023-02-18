package com.OnlineUpdate.Service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.OnlineUpdate.Dao.LoginRepository;
import com.OnlineUpdate.Entity.Login;

@Service
public class LoginServiceImpl implements LoginService {
	@Autowired
	private LoginRepository repo;

	
	public LoginRepository getRepo() {
		return repo;
	}

	@Autowired
	public void setRepo(LoginRepository repo) {
		this.repo = repo;
	}


	@Override
	public Login login(String username, String password) {
		// TODO Auto-generated method stub
		//repo.flush();
		Login user = repo.findByUsernameAndPassword1(username,password);
		
		
		return user;
	}

}

