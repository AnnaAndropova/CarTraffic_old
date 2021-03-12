package Task1.Model;

import lombok.Data;

import java.util.LinkedList;
import java.util.Queue;

@Data
public class CarTraffic {

    private Road road;
    private Queue<Car> cars = new LinkedList<>();

    public CarTraffic(int length) {
        this.road = new Road(length);
    }
}
