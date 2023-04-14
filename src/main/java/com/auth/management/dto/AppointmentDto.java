package com.auth.management.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDto {
    @NotEmpty(message="patient should not be empty")
    private Long patientId;

    @NotEmpty(message="Doctor should not be empty")
    private Long doctorId;

    @NotEmpty(message="appoint time should not be empty")
    private LocalDateTime appointmentTime;


}