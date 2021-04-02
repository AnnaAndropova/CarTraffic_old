package Task1.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Data
@Component
public class Road {

    @Value("${road.length}")
    private int length;
    private RoadCell[] cells;

    @PostConstruct
    public void init() {
        cells = new RoadCell[length];
        for (int i = 0; i < length; i++) {
            cells[i] = new RoadCell();
        }
    }

}
