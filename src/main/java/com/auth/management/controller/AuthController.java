package com.auth.management.controller;

import com.auth.management.dto.RegistrationDto;
import com.auth.management.service.UserService;
import com.auth.management.util.ROLE;
import com.auth.management.util.SecurityUtils;
import jakarta.validation.Valid;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;

@Controller
public class AuthController {

    private final  UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String guestRegister(Model model) {
        RegistrationDto user = new RegistrationDto();
        model.addAttribute("user", user);
        return "register";
    }


    @PostMapping("/register/save")
    public String register(@Valid @ModelAttribute("user") RegistrationDto user,
                           BindingResult result,
                           Model model){

        com.auth.management.entity.User existingUser = userService.findByEmail(user.getEmail());

        if(existingUser != null && existingUser.getEmail() !=null && !existingUser.getEmail().isEmpty()){
            result.rejectValue("email", null, "There is already a user with same email id");
        }

        if(result.hasErrors()){
            model.addAttribute("user", user);
            return "register";
        }
        userService.saveUser(user);
        return "redirect:/register?success";
    }



    @GetMapping("/")
    public String fontPage(){
        return "/frontend";
    }

    @GetMapping("/login")
    public String loginPage(){

        return "login";
    }

    @GetMapping("/admin/home")
    public String home(Model model ){
        User currentUser = SecurityUtils.getCurrentUser();
        String user = currentUser.getUsername();
        model.addAttribute("user",user);
        String role = SecurityUtils.getRole();

        List<com.auth.management.entity.User> patients = userService.findByRole("ROLE_GUEST");
        List<com.auth.management.entity.User> doctors = userService.findByRole("ROLE_DOCTOR");
        List<com.auth.management.entity.User> nurses = userService.findByRole("ROLE_MANAGER");
        model.addAttribute("patients",patients);
        model.addAttribute("doctors",doctors);
        model.addAttribute("nurses",nurses);

        if(ROLE.ROLE_GUEST.name().equals(role)){
            return "redirect:/guest";

        }
        if (ROLE.ROLE_DOCTOR.name().equals(role)){
            return "redirect:/doctor";

        }
        if(ROLE.ROLE_MANAGER.name().equals(role)){
            return "redirect:/nurse";
        }

            return "home";



    }




//    @GetMapping("/nurse")
//    public String nursePage(){
//        return "home";
//    }

    @GetMapping("/admin/home/search")
    public String showAllUserPage(@RequestParam("keyword") String keyword, Model model){
        List<com.auth.management.entity.User> byFirstName = userService.findByFirstName(keyword);
        List<com.auth.management.entity.User> byLastName = userService.findByLastName(keyword);
        com.auth.management.entity.User byPhoneNumber = userService.findByPhoneNumber(keyword);
        com.auth.management.entity.User byEmail = userService.findByEmail(keyword);
        if(keyword.trim().isEmpty()||keyword.trim().isBlank()){
            List<com.auth.management.entity.User> allUsers = userService.findAll();


            model.addAttribute("users",allUsers);
            return "search";
        }
        if(byFirstName.size()!=0){

            model.addAttribute("users",byFirstName);
            return "search";
        }
        if(byLastName.size()!=0){
            model.addAttribute("users",byLastName);
            return "search";
        }
        if(byPhoneNumber!=null){
            model.addAttribute("users",byPhoneNumber);
            return "search";
        }
        if(!byEmail.getEmail().isEmpty()){
            model.addAttribute("users",byEmail);
            return "search";
        }

        return "404";
    }



}
