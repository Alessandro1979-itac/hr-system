package br.com.alemcar.payrollapi.services;

import br.com.alemcar.payrollapi.domain.Payroll;
import br.com.alemcar.payrollapi.feignClients.UserFeign;
import br.com.alemcar.payrollapi.services.exceptions.ObjectNotFoundException;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;

import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
@Service
public class PayrollService {

    private final Environment env;
    private final UserFeign feign;

    public Payroll getPayment(Long workerId, Payroll payroll) {
        log.info("PAYROLL_SERVICE ::: Get request on " + env.getProperty("local.server.port") + " port");

        try {
            var user = feign.findById(workerId).getBody();
            if (Objects.nonNull(user)) {
                return new Payroll(
                        user.getName(),
                        payroll.getDescription(),
                        user.getHourlyPrice(),
                        payroll.getWorkedHours(),
                        payroll.getWorkedHours() * user.getHourlyPrice()
                );
            }
        } catch (FeignException.NotFound ex) {
            throw new ObjectNotFoundException("Object not found");
        } catch (Exception ex) {
            throw new IllegalArgumentException("Illegal argument exception");
        }

        return null;
    }
}
