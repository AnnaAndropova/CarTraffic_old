package Task1.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class RoadCell {

    private Car car;

    public RoadCell() {
        this.car = null;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public void clear() {
        if (car != null) {
            car = null;
        }
    }

}
