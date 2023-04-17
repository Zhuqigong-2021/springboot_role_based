package com.auth.management.repository;

import com.auth.management.entity.Appointment;
import com.auth.management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {



    @Query("SELECT a.appointmentTime FROM Appointment a WHERE a.patient.id = :patientId")
    List<LocalDateTime> findDateTimeByPatientId(@Param("patientId") Long patientId);

    @Query("SELECT a.doctor FROM Appointment a WHERE a.patient.id = :patientId")
    List<User> findDoctorsByPatientId(@Param("patientId") Long patientId);

    @Query("SELECT a.doctor FROM Appointment a WHERE a.patient.id = :patientId")
    User findDoctorByPatientId(@Param("patientId") Long patientId);


    @Query("SELECT a.doctor FROM Appointment a WHERE a.appointmentTime = :dateTime")
    User findDoctorsByAppointmentDateTime(@Param("dateTime") LocalDateTime dateTime);

    @Query("SELECT  a.doctor FROM Appointment a WHERE a.appointmentTime IN :dateTimeList")
    List<User> findDoctorsByAppointmentDateTimes(@Param("dateTimeList") List<LocalDateTime> dateTimeList);



    List<Appointment> findByDoctorId(Long doctorId);























    @Query("SELECT a.appointmentTime FROM Appointment a WHERE a.doctor.id = :doctorId")
    List<LocalDateTime> findDateTimeByDoctorId(@Param("doctorId") Long doctorId);
    @Query("SELECT DISTINCT a.patient FROM Appointment a WHERE a.doctor.id = :doctorId")
    List<User> findPatientsByDoctorId(@Param("doctorId") Long doctorId);

}