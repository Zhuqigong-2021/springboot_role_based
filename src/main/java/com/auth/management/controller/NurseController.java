package com.auth.management.controller;

import com.auth.management.dto.RegistrationDto;
import com.auth.management.mapper.MapperHelper;
import com.auth.management.service.UserService;
import com.auth.management.util.SecurityUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class NurseController {
    private final UserService userService;
    private final MapperHelper mapperHelper;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public NurseController(UserService userService, MapperHelper mapperHelper, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.mapperHelper = mapperHelper;
        this.passwordEncoder = passwordEncoder;
    }
   //show nurse name
    @GetMapping("/nurse")
    public String nursePage(Model model){
        User currentUser = SecurityUtils.getCurrentUser();
        String name = currentUser.getUsername().toString();
        model.addAttribute("user",name);

        List<com.auth.management.entity.User> patients = userService.findByRole("ROLE_GUEST");
        List<com.auth.management.entity.User> doctors = userService.findByRole("ROLE_DOCTOR");
        model.addAttribute("patients",patients);
        model.addAttribute("doctors",doctors);

        return "home";
    }
    //register a nurse
    @GetMapping("/admin/register/nurse")
    public String nurseRegister(Model model){
        RegistrationDto nurse = new RegistrationDto();
        model.addAttribute("nurse",nurse);
        return "registerNurse";
    }


    @PostMapping("/registerNurse/save")
    public String registerDoc(@Valid @ModelAttribute("nurse") RegistrationDto nurse,
                              BindingResult result,
                              Model model){

        com.auth.management.entity.User existingUser = userService.findByEmail(nurse.getEmail());

        if(existingUser != null && existingUser.getEmail() !=null && !existingUser.getEmail().isEmpty()){
            result.rejectValue("email", null, "There is already a user with same email id");
        }
        if(existingUser != null && existingUser.getPhoneNumber() !=null &&!existingUser.getPhoneNumber().isEmpty()){
            result.rejectValue("phoneNumber",null,"the phone number is taken");
        }

        if(result.hasErrors()){
            model.addAttribute("nurse", nurse);
            return "registerNurse";
        }
        userService.saveNurse(nurse);
        return "redirect:/admin/home#nurse";
    }

//update a nurse
@GetMapping("/showFormForUpdateNurse")
public String updateNurse(@RequestParam("nurseId") Long id,Model model ){
    com.auth.management.entity.User nurseById = userService.findUserById(id);
    RegistrationDto nurse = mapperHelper.convertUserEntityToRegisterDto(nurseById);
    model.addAttribute("nurse",nurse);

    return "updateNurse";
}



    @PostMapping("/updateNurse/save")
    public String saveExistedNurse( @Valid @ModelAttribute("nurse") RegistrationDto registrationDto,BindingResult result,Model model ) {
        Long id = registrationDto.getId();
        com.auth.management.entity.User nurseUser = userService.findUserById(id);
        nurseUser.setFirstName(registrationDto.getFirstName());
        nurseUser.setLastName(registrationDto.getLastName());
        nurseUser.setEmail(registrationDto.getEmail());
        nurseUser.setPhoneNumber(registrationDto.getPhoneNumber());
        nurseUser.setPassword(passwordEncoder.encode(registrationDto.getPassword()));

        if(result.hasErrors()){
            model.addAttribute("nurse", nurseUser);
            return "updateNurse";
        }
        userService.saveUpdate(nurseUser);

        // use a redirect to prevent duplicate submissions
        return "redirect:/admin/home#nurse";
    }


    //delete a nurse
    @GetMapping("/nurse/delete")
    public String delete(@RequestParam("nurseId") Long id ){
        userService.deleteUserById(id);

        return "redirect:/admin/home#nurse";
    }






}
