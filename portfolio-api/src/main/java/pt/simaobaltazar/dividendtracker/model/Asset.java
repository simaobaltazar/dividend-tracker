package pt.simaobaltazar.dividendtracker.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "assets")
public class Asset {

    @Id
    private String ticker;

    @Column(nullable = false)
    private String companyName;

    private String sector;

    @Column(nullable = false)
    private String country;

    protected Asset() {
    }

    public Asset(String ticker, String companyName, String sector, String country) {
        this.ticker = ticker;
        this.companyName = companyName;
        this.sector = sector;
        this.country = country;
    }

    public String getTicker() { return ticker; }

    public String getCompanyName() { return companyName; }

    public String getSector() { return sector; }

    public String getCountry() { return country; }
}
