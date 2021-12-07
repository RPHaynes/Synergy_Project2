package com.revature.shms.aspect;
import com.revature.shms.models.Reservation;
import com.revature.shms.models.User;
import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
@Component
public class LoggingAspect {

    /*
    TODO
    Log deletes to a manager accessible endpoint
     */
    Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.revature.shms.services.UserService.createNewUser(..))")
    public void newUserLogs(JoinPoint joinPoint){
        User user = (User) joinPoint.getArgs()[0];
        logger.info("New user: " + user.getUsername());
    }

    @Before("execution(* com.revature.shms.services.ReservationService.createReservation(..))")
    public void newReservationLogs(JoinPoint joinPoint){
        Reservation reservation = (Reservation) joinPoint.getArgs()[0];
        logger.info("New Reservation for:" + reservation.getUserReserve().getUsername());
    }

}
