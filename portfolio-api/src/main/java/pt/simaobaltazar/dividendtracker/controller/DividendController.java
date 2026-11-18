package pt.simaobaltazar.dividendtracker.controller;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import pt.simaobaltazar.dividendtracker.model.Asset;
import pt.simaobaltazar.dividendtracker.model.DividendForecast;
import pt.simaobaltazar.dividendtracker.model.DividendSummary;
import pt.simaobaltazar.dividendtracker.service.AssetService;
import pt.simaobaltazar.dividendtracker.service.DividendForecastService;

@Controller
public class DividendController {
    private final DividendForecastService dividendForecastService;
    private final AssetService assetService;

    public DividendController(DividendForecastService dividendForecastService, AssetService assetService) {
        this.dividendForecastService = dividendForecastService;
        this.assetService = assetService;
    }

    @QueryMapping
    public DividendSummary dividendForecast() {
        return dividendForecastService.summary();
    }

    @SchemaMapping
    public Asset asset(DividendForecast forecast) {
        return assetService.findByTicker(forecast.getAssetTicker());
    }
}
