package com.auth.management.controller;

import com.auth.management.dto.RegistrationDto;
import com.auth.management.entity.Role;
import com.auth.management.mapper.MapperHelper;
import com.auth.management.repository.RoleRepository;
import com.auth.management.service.UserService;
import com.auth.management.util.ROLE;
import com.auth.management.util.SecurityUtils;
import jakarta.validation.Valid;
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
public class GuestController {
    private final UserService userService;
    private final RoleRepository roleRepository;

    private final MapperHelper mapperHelper;

    private final PasswordEncoder passwordEncoder;

    public GuestController(UserService userService, RoleRepository roleRepository, MapperHelper mapperHelper, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.mapperHelper = mapperHelper;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/guest")
    public String guestPage(Model model){
        User currentUser = SecurityUtils.getCurrentUser();
        String name = currentUser.getUsername().toString();
        model.addAttribute("user",name);
        com.auth.management.entity.User guests = userService.findByEmail(name);
//        List<com.auth.management.entity.User> guests = userService.findByRole("ROLE_GUEST");
//
        model.addAttribute("patients",guests);

        List<com.auth.management.entity.User> doctors = userService.findByRole("ROLE_DOCTOR");
        List<com.auth.management.entity.User> nurses = userService.findByRole("ROLE_MANAGER");
        model.addAttribute("doctors",doctors);
        model.addAttribute("nurses",nurses);
        return "/home";
    }



    //update a patient
@GetMapping("/showFormForUpdatePatient")
public String updatePatient(@RequestParam("patientId") Long id,Model model ){
    com.auth.management.entity.User patientById = userService.findPatientById(id);
    RegistrationDto patient = mapperHelper.convertUserEntityToRegisterDto(patientById);
    model.addAttribute("patient",patient);

    return "updatePatient";
}



    @PostMapping("/updatePatient/save")
    public String saveExistedPatient(@Valid @ModelAttribute("patient") RegistrationDto registrationDto, BindingResult result, Model model ) {
        Long id = registrationDto.getId();
        com.auth.management.entity.User patientUser = userService.findUserById(id);
        patientUser.setFirstName(registrationDto.getFirstName());
        patientUser.setLastName(registrationDto.getLastName());
        patientUser.setEmail(registrationDto.getEmail());
        patientUser.setPhoneNumber(registrationDto.getPhoneNumber());
        patientUser.setPassword(passwordEncoder.encode(registrationDto.getPassword()));

        if(result.hasErrors()){
            model.addAttribute("patient", patientUser);
            return "updateDoc";
        }
        userService.saveUpdate(patientUser);

        // use a redirect to prevent duplicate submissions
        return "redirect:/admin/home#doctor";
    }









    //delete a patient
    @GetMapping("/guest/delete")
    public String delete(@RequestParam("patientId") Long id ){
        userService.deleteUserById(id);

        return "redirect:/admin/home#patient";
    }





}
