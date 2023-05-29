package com.auth.management.controller;

import com.auth.management.dto.RegistrationDto;
import com.auth.management.service.UserService;
import com.auth.management.util.ROLE;
import com.auth.management.util.SecurityUtils;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


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
        String user = currentUser!=null? currentUser.getUsername():"";
        model.addAttribute("user",user);
        String role = SecurityUtils.getRole();

        List<com.auth.management.entity.User> patients = userService.findByRole("ROLE_GUEST");
        List<com.auth.management.entity.User> doctors = userService.findByRole("ROLE_DOCTOR");
        List<com.auth.management.entity.User> nurses = userService.findByRole("ROLE_MANAGER");
        model.addAttribute("patients",patients);
        model.addAttribute("doctors",doctors);
        model.addAttribute("nurses",nurses);
        model.addAttribute("role",role);

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

    @GetMapping("admin/home/search/page")
    public String showAll(@RequestParam("keyword") String keyword,Model model){

        return findPaginated(1 ,keyword,model);
    }
    @GetMapping("admin/home/search/page/{number}")
    public String findPaginated(@PathVariable(value="number")int number, @RequestParam(value="keyword",required = false,defaultValue = "") String keyword , Model model){
        int size =4;

        Pageable pageable = PageRequest.of(number-1, 8);
        Page<com.auth.management.entity.User> page = userService.search(keyword,pageable);
        List<com.auth.management.entity.User> users= page.getContent();
        User currentUser = SecurityUtils.getCurrentUser();
        String user = currentUser!=null? currentUser.getUsername():"";

        model.addAttribute("currentPage",number);
        model.addAttribute("totalPages",page.getTotalPages());
        model.addAttribute("totalItems",page.getTotalElements());
        model.addAttribute("users",users);
        model.addAttribute("user",user);
        model.addAttribute("keyword",keyword);
        return "search";


    }




}
