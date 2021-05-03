package com.example.clase6demo.controller;

import com.example.clase6demo.entity.Usuario;
import com.example.clase6demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class LoginController {
    
    @Autowired
    UsuarioRepository usuarioRepository;

    @GetMapping("/loginForm")
    public String loginForm(){
        return "login/form";
    }

    @GetMapping("/redirectByRole")
    public String redirectByRole(Authentication auth, HttpSession session, HttpServletRequest req){
        String rol= "";
        for(GrantedAuthority role : auth.getAuthorities()){
            rol = role.getAuthority();
            break;
        }

        String email = auth.getName();
        Usuario usuario = usuarioRepository.findByEmail(email);
        session.setAttribute("usuario",usuario);

        if(rol.equals("admin")){
            return "redirect:/shipper/";
        }else{
            return "redirect:/employee/";
        }
    }
}
