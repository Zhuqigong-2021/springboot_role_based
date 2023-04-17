package com.auth.management.controller;

import com.auth.management.dto.AppointmentDto;

import com.auth.management.service.AppointmentService;
import com.auth.management.service.UserService;
import com.auth.management.util.SecurityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ScheduleController {
    private final UserService userService;
    private final AppointmentService appointmentService;

    public ScheduleController(UserService userService, AppointmentService appointmentService) {
        this.userService = userService;
        this.appointmentService = appointmentService;
    }

    @GetMapping("/showFormForSchedule")
    public String startAppointment(@RequestParam("doctorId") Long doctorId,Model model){
        User currentUser = SecurityUtils.getCurrentUser();
        String username = currentUser!=null?currentUser.getUsername():"";
        com.auth.management.entity.User patient = userService.findByEmail(username);
        com.auth.management.entity.User doctor = userService.findUserById(doctorId);

        AppointmentDto appointment = new AppointmentDto();
        appointment.setPatientId(patient.getId());
        appointment.setDoctorId(doctorId);


        model.addAttribute("appointment",appointment);
        model.addAttribute("patient",patient);
        model.addAttribute("doctor",doctor);

        return "appointment";
    }

    @PostMapping("/updateSchedule/save")
    public String applyAppointment(@ModelAttribute("appointment") AppointmentDto appointment){

       appointmentService.createAppointment(appointment);
        return "redirect:/admin/home#patient";
    }
}
