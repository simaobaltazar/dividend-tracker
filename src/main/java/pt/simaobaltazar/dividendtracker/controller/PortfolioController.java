package pt.simaobaltazar.dividendtracker.controller;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import pt.simaobaltazar.dividendtracker.model.Asset;
import pt.simaobaltazar.dividendtracker.model.Holding;
import pt.simaobaltazar.dividendtracker.service.AssetService;
import pt.simaobaltazar.dividendtracker.service.PortfolioService;

import java.util.List;

@Controller
public class PortfolioController {

    private final PortfolioService portfolioService;
    private final AssetService assetService;

    public PortfolioController(PortfolioService portfolioService, AssetService assetService) {
        this.portfolioService = portfolioService;
        this.assetService = assetService;
    }

    @QueryMapping
    public List<Holding> portfolio(){
        return portfolioService.portfolio();
    }

    @SchemaMapping
    public Asset asset(Holding holding){
        return assetService.findByTicker(holding.assetTicker());
    }
}
