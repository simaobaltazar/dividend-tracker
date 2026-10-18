package pt.simaobaltazar.dividendtracker.service;

import org.springframework.stereotype.Service;
import pt.simaobaltazar.dividendtracker.model.Asset;

import java.util.ArrayList;
import java.util.List;

@Service
public class AssetService {

    private final List<Asset> assets = List.of(
            new Asset("AAPL", "Apple Inc.", "Technology", "US"),
            new Asset("EDP.LS", "EDP - Energias de Portugal", "Utilities", "PT"),
            new Asset("VWCE", "Vanguard FTSE All-World ETF", null, "IE"));

    public List<Asset> findAll(){
        return assets;
    }

    public Asset findByTicker(String ticker){
        return assets.stream().filter(a -> a.ticker().equals(ticker))
                .findFirst().orElse(null);
    }
}
