package com.auth.management.controller;

import com.auth.management.dto.RegistrationDto;
import com.auth.management.entity.Appointment;
import com.auth.management.mapper.MapperHelper;
import com.auth.management.service.AppointmentService;
import com.auth.management.service.UserService;
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

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class GuestController {

    private final UserService userService;


    private final MapperHelper mapperHelper;

    private final PasswordEncoder passwordEncoder;



    private final AppointmentService appointmentService;


    public GuestController(UserService userService,  MapperHelper mapperHelper, PasswordEncoder passwordEncoder, AppointmentService appointmentService) {
        this.userService = userService;
        this.mapperHelper = mapperHelper;
        this.passwordEncoder = passwordEncoder;
        this.appointmentService = appointmentService;
    }
//render patient page with wanted data
    @GetMapping("/guest")
    public String guestPage(Model model){
        User currentUser =  SecurityUtils.getCurrentUser();
        String name = currentUser!=null? currentUser.getUsername():"";
        model.addAttribute("user",name);
        model.addAttribute("currentUser",currentUser);
        String role = SecurityUtils.getRole();
        model.addAttribute("role",role);

        com.auth.management.entity.User guests = userService.findByEmail(name);
        Long patientId = guests.getId();
      List <LocalDateTime> schedules = appointmentService.findDateTimeByPatientId(patientId);




        if(schedules.size()!=0){
            List<com.auth.management.entity.User> doctorsByPatientId = appointmentService.findDoctorsByPatientId(patientId);
            model.addAttribute("scheduledDoctors",doctorsByPatientId);
            List<Appointment> appointmentsByPatientId = appointmentService.findAppointmentsByPatientId(patientId);

            model.addAttribute("appointments",appointmentsByPatientId);

        }


        model.addAttribute("patients",guests);

        List<com.auth.management.entity.User> doctors = userService.findByRole("ROLE_DOCTOR");
        List<com.auth.management.entity.User> nurses = userService.findByRole("ROLE_MANAGER");
        model.addAttribute("doctors",doctors);
        model.addAttribute("nurses",nurses);
        model.addAttribute("schedules",schedules);



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
            return "updatePatient";
        }
        userService.saveUpdate(patientUser);

        // use a redirect to prevent duplicate submissions
        return "redirect:/admin/home#patient";
    }









    //delete a patient
    @GetMapping("/guest/delete")
    public String delete(@RequestParam("patientId") Long id ){
        userService.deleteUserById(id);

        return "redirect:/admin/home#patient";
    }





}
