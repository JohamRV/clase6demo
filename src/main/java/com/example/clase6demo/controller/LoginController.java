package com.example.clase6demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/loginForm")
    public String loginForm(){
        return "login/form";
    }

    @GetMapping("/redirectByRole")
    public String redirectByRole(Authentication auth){
        String rol= "";
        for(GrantedAuthority role : auth.getAuthorities()){
            rol = role.getAuthority();
            break;
        }

        if(rol.equals("admin")){
            return "redirect:/shipper/";
        }else{
            return "redirect:/employee/";
        }
    }
}
