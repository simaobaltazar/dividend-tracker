package pt.simaobaltazar.dividendtracker.controller;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import pt.simaobaltazar.dividendtracker.model.Asset;
import pt.simaobaltazar.dividendtracker.service.AssetService;

import java.util.List;

@Controller
public class AssetController {

    private final AssetService assetService;

    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    @QueryMapping
    public List<Asset> assets() {
        return assetService.findAll();
    }

    @QueryMapping
    public Asset asset(@Argument String ticker) {
        return assetService.findByTicker(ticker);
    }

}
