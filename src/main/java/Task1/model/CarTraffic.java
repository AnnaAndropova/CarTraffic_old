package Task1.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Queue;

@Data
@Component
public class CarTraffic {

    @Autowired
    private Road road;
    private Queue<Car> cars = new LinkedList<>();

}
