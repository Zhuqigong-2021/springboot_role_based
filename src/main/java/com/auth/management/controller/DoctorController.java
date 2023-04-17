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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class DoctorController {


    private final UserService userService;
    private final MapperHelper mapperHelper;

    private final PasswordEncoder passwordEncoder;

    private final AppointmentService appointmentService;

    public DoctorController(UserService userService, MapperHelper mapperHelper, PasswordEncoder passwordEncoder, AppointmentService appointmentService) {
        this.userService = userService;
        this.mapperHelper = mapperHelper;
        this.passwordEncoder = passwordEncoder;
        this.appointmentService = appointmentService;
    }

    @GetMapping("/doctor")
    public String doctorPage(Model model){
        User currentUser = SecurityUtils.getCurrentUser();
        String user = currentUser!=null? currentUser.getUsername():"";
        String role = SecurityUtils.getRole();
        String name = currentUser!=null? currentUser.getUsername():"";
        com.auth.management.entity.User currentDoctor = userService.findByEmail(name);
        Long doctorId = currentDoctor.getId();
        List<com.auth.management.entity.User> patientsByDoctorId = appointmentService.findPatientsByDoctorId(doctorId);
        List<LocalDateTime> dateTimeByDoctorId = appointmentService.findDateTimeByDoctorId(doctorId);
        List<Appointment> appointmentsByDoctorId = appointmentService.findAppointmentsByDoctorId(doctorId);

        List<com.auth.management.entity.User> nurses = userService.findByRole("ROLE_MANAGER");

        model.addAttribute("nurses",nurses);
        model.addAttribute("role",role);
        model.addAttribute("user",user);
        model.addAttribute("doctor",name);
        model.addAttribute("patients",patientsByDoctorId);
        model.addAttribute("schedules",dateTimeByDoctorId);
        model.addAttribute("appointments",appointmentsByDoctorId);
        return "home";
    }
  //register a doctor
    @GetMapping("/admin/register/doctor")
    public String docRegister(Model model){
        RegistrationDto doctor = new RegistrationDto();
        model.addAttribute("doctor",doctor);
        return "registerDoc";
    }
    @PostMapping("/registerDoctor/save")
    public String registerDoc(@Valid @ModelAttribute("doctor") RegistrationDto doctor,
                              BindingResult result,
                              Model model){

        com.auth.management.entity.User existingUser = userService.findByEmail(doctor.getEmail());

        if(existingUser != null && existingUser.getEmail() !=null && !existingUser.getEmail().isEmpty()){
            result.rejectValue("email", null, "There is already a user with same email id");
        }
        if(existingUser != null && existingUser.getPhoneNumber() !=null &&!existingUser.getPhoneNumber().isEmpty()){
            result.rejectValue("phoneNumber",null,"the phone number is taken");
        }

        if(result.hasErrors()){
            model.addAttribute("doctor", doctor);
            return "registerDoc";
        }
        userService.saveDoctor(doctor);
        return "redirect:/admin/home#doctor";
    }

    //update a doctor
    @GetMapping("/showFormForUpdate")
    public String updateDoctor(@RequestParam("doctorId") Long id,Model model ){
        com.auth.management.entity.User doctorById = userService.findUserById(id);
        RegistrationDto doctor = mapperHelper.convertUserEntityToRegisterDto(doctorById);
        model.addAttribute("doctor",doctor);

        return "updateDoc";
    }

    @PostMapping("/updateDoctor/save")
    public String saveExistedDoctor( @Valid @ModelAttribute("doctor") RegistrationDto registrationDto,BindingResult result,Model model ) {
        Long id = registrationDto.getId();
        com.auth.management.entity.User doctorUser = userService.findUserById(id);
        doctorUser.setFirstName(registrationDto.getFirstName());
        doctorUser.setLastName(registrationDto.getLastName());
        doctorUser.setEmail(registrationDto.getEmail());
        doctorUser.setPhoneNumber(registrationDto.getPhoneNumber());
        doctorUser.setPassword(passwordEncoder.encode(registrationDto.getPassword()));

        if(result.hasErrors()){
            model.addAttribute("doctor", doctorUser);
            return "updateDoc";
        }
        userService.saveUpdate(doctorUser);

        // use a redirect to prevent duplicate submissions
        return "redirect:/admin/home#doctor";
    }

    //delete a doctor
    @GetMapping("/doctor/delete")
    public String delete(@RequestParam("doctorId") Long id ){
        userService.deleteUserById(id);

        return "redirect:/admin/home#doctor";
    }



}
