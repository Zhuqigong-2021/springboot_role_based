package com.auth.management.service;

import com.auth.management.dto.AppointmentDto;
import com.auth.management.entity.Appointment;
import com.auth.management.entity.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


public interface AppointmentService {
    public void createAppointment(AppointmentDto appointmentDto);

    public List<LocalDateTime> findDateTimeByDoctorId(Long doctorId);

    public List<User> findPatientsByDoctorId(Long doctorId);


    public List<Appointment> findAppointmentsByDoctorId(Long doctorId);


    public List<Appointment> findAppointmentsByPatientId(Long patientId);

    public List<LocalDateTime> findDateTimeByPatientId(Long patientId);


    public List<User> findDoctorsByPatientId(Long patientId);
}
