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
public class TariffWeightFactor {
    private Long id;
    private int minWeightRange;
    private int maxWeightRange;
    private int overPayOnKilometer;

    private List<Way> waysWhereUsed;

    public TariffWeightFactor(int minWeightRange, int maxWeightRange, int overPayOnKilometer) {
        this.minWeightRange = minWeightRange;
        this.maxWeightRange = maxWeightRange;
        this.overPayOnKilometer = overPayOnKilometer;
    }
}
