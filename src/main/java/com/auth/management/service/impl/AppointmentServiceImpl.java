package com.auth.management.service.impl;

import com.auth.management.dto.AppointmentDto;
import com.auth.management.entity.Appointment;
import com.auth.management.entity.User;
import com.auth.management.repository.AppointmentRepository;
import com.auth.management.service.AppointmentService;
import com.auth.management.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserService userService;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, UserService userService) {
        this.appointmentRepository = appointmentRepository;
        this.userService = userService;
    }

    @Override
    public void createAppointment(AppointmentDto appointmentDto) {
        Appointment appointment = new Appointment();
        Long patientId = appointmentDto.getPatientId();
        Long doctorId = appointmentDto.getDoctorId();
        User patientById = userService.findUserById(patientId);
        User doctorById = userService.findUserById(doctorId);
        appointment.setPatient(patientById);
        appointment.setDoctor(doctorById);
        appointment.setAppointmentTime(appointmentDto.getAppointmentTime());
        appointmentRepository.save(appointment);
    }

    @Override
    public List<LocalDateTime> findDateTimeByDoctorId(Long doctorId) {
        return appointmentRepository.findDateTimeByDoctorId(doctorId);
    }

    @Override
    public List<User> findPatientsByDoctorId(Long doctorId) {
        return appointmentRepository.findPatientsByDoctorId(doctorId);
    }

    @Override
    public List<Appointment> findAppointmentsByDoctorId(Long doctorId) {

        return appointmentRepository.findByDoctorId(doctorId);
    }

    @Override
    public List<Appointment> findAppointmentsByPatientId(Long patientId) {
        return appointmentRepository.findByDoctorId(patientId);
    }

    @Override
    public List<LocalDateTime> findDateTimeByPatientId(Long patientId) {
        return appointmentRepository.findDateTimeByPatientId(patientId);
    }

    @Override
    public List<User> findDoctorsByPatientId(Long patientId) {
        return appointmentRepository.findDoctorsByPatientId(patientId);
    }


}
