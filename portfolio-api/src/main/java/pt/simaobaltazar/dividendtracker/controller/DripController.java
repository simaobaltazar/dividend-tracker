package pt.simaobaltazar.dividendtracker.controller;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import pt.simaobaltazar.dividendtracker.model.DripYear;
import pt.simaobaltazar.dividendtracker.service.DripSimulatorService;

import java.util.List;

@Controller
public class DripController {

    private final DripSimulatorService dripSimulatorService;

    public DripController(DripSimulatorService dripSimulatorService) {
        this.dripSimulatorService = dripSimulatorService;
    }

    @QueryMapping
    public List<DripYear> dripSimulation(@Argument String ticker, @Argument int years) {
        return dripSimulatorService.simulate(ticker, years);
    }
}
