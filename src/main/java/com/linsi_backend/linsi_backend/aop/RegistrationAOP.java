package com.linsi_backend.linsi_backend.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import com.linsi_backend.linsi_backend.service.dto.response.RegistrationDTO;
import com.linsi_backend.linsi_backend.service.EmailService;

@Aspect
@Component
public class RegistrationAOP {
    private final EmailService emailService;

    public RegistrationAOP(EmailService emailService) {
        this.emailService = emailService;
    }

    @AfterReturning(pointcut = "execution(* com.linsi_backend.linsi_backend.service.impl.RegistrationServiceImpl.create(..))", returning = "dtoValue")
    public void notificationRegistration(JoinPoint joinPoint, Object dtoValue) {
        RegistrationDTO registrationDTO = (RegistrationDTO) dtoValue;
        // Ya no es necesario pasar el destinatario
        emailService.registration("Hay una nueva solicitud de inscripción", registrationDTO.getFirstName(), registrationDTO.getLastName(), registrationDTO.getId().toString()
        );
    }
}

