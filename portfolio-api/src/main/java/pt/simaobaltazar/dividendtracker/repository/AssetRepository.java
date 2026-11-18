package pt.simaobaltazar.dividendtracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.simaobaltazar.dividendtracker.model.Asset;

public interface AssetRepository extends JpaRepository<Asset, String> {
}
