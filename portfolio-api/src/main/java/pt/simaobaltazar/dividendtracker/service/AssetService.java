package pt.simaobaltazar.dividendtracker.service;

import org.springframework.stereotype.Service;
import pt.simaobaltazar.dividendtracker.model.Asset;
import pt.simaobaltazar.dividendtracker.repository.AssetRepository;
import pt.simaobaltazar.dividendtracker.service.exceptions.ResourceNotFoundException;

import java.util.List;

@Service
public class AssetService {

    private final AssetRepository assetRepository;

    public AssetService(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    public List<Asset> findAll() {
        return assetRepository.findAll();
    }

    public Asset findByTicker(String ticker) {
        return assetRepository.findById(ticker)
                .orElseThrow(() -> new ResourceNotFoundException(ticker));
    }
}
