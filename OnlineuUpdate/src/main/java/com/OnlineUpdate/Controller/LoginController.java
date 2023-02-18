package com.OnlineUpdate.Controller;


import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.OnlineUpdate.Entity.Login;
import com.OnlineUpdate.Service.LoginService;



@SessionAttributes({"errorMsg"})
@Controller
public class LoginController {
	
	@Autowired
    private LoginService userService;

                                   
    public LoginService getUserService() {
		return userService;
	}
    @Autowired
	public void setUserService(LoginService userService) {
		this.userService = userService;
	}

	@GetMapping("/login")
    public ModelAndView login() {
    	ModelAndView mav = new ModelAndView("login");
        mav.addObject("user", new Login());
        mav.addObject("errorMsg", "");
        return mav;
    }
	
	
    @PostMapping("/login")
    public ModelAndView login(@ModelAttribute("user") Login user) {
    	
    	ModelAndView map = new ModelAndView("login");
		map.addObject("user", new Login());
    	Login oauthUser = userService.login(user.getUsername(), user.getPassword1());
    	
    	if(Objects.nonNull(oauthUser)) 
    	{	
    		System.out.print("***********"+oauthUser.getUsername()+"**********"+oauthUser.getPassword1());
    		map.addObject("errorMsg", "");
    		return new ModelAndView("redirect:/uploadfile");
    	
    		
    	} else {
    		map.addObject("errorMsg", " Illegal Access . Please contact admin !!!");
    		
    		return map;
    	}

}
    
    @PostMapping("/logout")
    public String logoutDo(HttpServletRequest request,HttpServletResponse response)
    {
    	System.out.println("In logout");
        return "redirect:/login";
    }

}