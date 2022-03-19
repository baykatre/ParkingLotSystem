package co.anilozturk.ParkingLotSystem.generic;

import co.anilozturk.ParkingLotSystem.config.ParkingMessageSource;
import co.anilozturk.ParkingLotSystem.exception.ParkingGenericException;
import co.anilozturk.ParkingLotSystem.system.history.History;
import co.anilozturk.ParkingLotSystem.system.history.HistoryService;
import co.anilozturk.ParkingLotSystem.system.model.ParkingOutput;
import co.anilozturk.ParkingLotSystem.system.ticket.Ticket;
import co.anilozturk.ParkingLotSystem.system.vehicle.Vehicle;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Aspect
@Component
@RequiredArgsConstructor
public class CrazyLoggerAspect {

  public static final String VEHICLE_NOT_FOUND = "error.status.vehicle_is_not_found";

  private final HistoryService historyService;

  private final ParkingMessageSource messageSource;

  private long start, executionTime;

  @Pointcut("execution(* co.anilozturk.ParkingLotSystem.system.SystemController.*(..))")
  private void selectControllerMethods() {}

  @Around("selectControllerMethods()")
  public Object logProcess(ProceedingJoinPoint joinPoint) throws Throwable {
    start = System.currentTimeMillis();

    return joinPoint.proceed();
  }

  @AfterReturning(pointcut = "selectControllerMethods()", returning = "retVal")
  public Object logProcess(JoinPoint joinPoint, Object retVal) {

    executionTime = System.currentTimeMillis() - start;

    final ResponseEntity<Response<Object>> returnValue = (ResponseEntity<Response<Object>>) retVal;
    if (Objects.isNull(returnValue.getBody())
        || !(returnValue.getBody().getData() instanceof ParkingOutput)) {
      return null;
    }

    final List<Ticket> ticketList = ((ParkingOutput) returnValue.getBody().getData()).getTickets();
    final String tickets =
        ticketList.stream()
            .map(Ticket::getId)
            .map(String::valueOf)
            .collect(Collectors.joining("-"));

    final Vehicle vehicle =
        ticketList.stream()
            .map(Ticket::getVehicle)
            .findFirst()
            .orElseThrow(() -> new ParkingGenericException(messageSource, VEHICLE_NOT_FOUND));

    final MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    final String operationType = signature.getMethod().getName();

    final History history =
        History.builder()
            .tickets(tickets)
            .plate(vehicle.getPlate())
            .operationType(operationType)
            .executionTime(executionTime)
            .build();
    historyService.save(history);

    return null;
  }
}
