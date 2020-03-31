package db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Way {
    private Long id;
    private Locality localitySand;
    private Locality localityGet;
    private List<Delivery> deliveries;
    private int distanceInKilometres;
    private int timeOnWayInDays;
    private int priceForKilometerInCents;
    private List<TariffWeightFactor> wayTariffs;

    @Override
    public String toString() {
        return "Way{"; //+
//                "id=" + id +
//                ", localitySand=" + localitySand.getNameRu() +
//                ", localityGet=" + localityGet.getNameRu() +
//                ", distanceInKilometres=" + distanceInKilometres +
//                ", timeOnWayInHours=" + timeOnWayInHours +
//                ", priceForKilometerInCents=" + priceForKilometerInCents +
//                ", wayTariffs=" + wayTariffs +
//                '}';
    }
}
