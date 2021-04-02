package Task1.model;

import Task1.model.Enum.CarStatusEnum;
import Task1.model.Enum.CarTypeEnum;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Random;

@Data
@Component
@Scope("prototype")
public class Car {

    private Object[] number = new Object[6];
    private CarTypeEnum type;
    private int length;
    private int normalSpeed;
    private int curSpeed;
    private int position;
    private RoadCell[] cells;
    private int boost;
    private CarStatusEnum status;
    private int timeCrashed;
    private int timeHardSlowing;

    @Value("${passenger.minSpeed}")
    private int pMinS;
    @Value("${passenger.maxSpeed}")
    private int pMaxS;
    @Value("${passenger.boost}")
    private int pBoost;
    @Value("${passenger.length}")
    private int pLength;
    @Value("${truck.minSpeed}")
    private int tMinS;
    @Value("${truck.maxSpeed}")
    private int tMaxS;
    @Value("${truck.boost}")
    private int tBoost;
    @Value("${truck.length}")
    private int tLength;

    public Car() {

        setNumber();

        this.position = 0;
        this.timeCrashed = 0;
        this.timeHardSlowing = 0;

    }

    @PostConstruct
    public void init() {

        double tmp = Math.random();
        if (1 - tmp < 0.5) {
            this.type = CarTypeEnum.TRUCK;
            this.curSpeed = (int) ((Math.random() * ++tMaxS) + tMinS);
            this.length = tLength;
            this.boost = tBoost;
        } else {
            this.type = CarTypeEnum.PASSENGER;
            this.curSpeed = (int) ((Math.random() * ++pMaxS) + pMinS);
            this.length = pLength;
            this.boost = pBoost;
        }

        this.cells = new RoadCell[this.length];
        for (int i = 0; i < cells.length; i++) {
            cells[i] = new RoadCell();
        }

        this.normalSpeed = curSpeed;

    }

    private void setNumber() {

        Random r = new Random();
        char c1 = (char) (r.nextInt(26) + 'A');
        char c2 = (char) (r.nextInt(26) + 'A');
        char c3 = (char) (r.nextInt(26) + 'A');
        number[0] = c1;
        number[4] = c2;
        number[5] = c3;
        for (int i = 1; i < number.length - 2; i++) {
            int max = 0;
            int min = 9;
            max -= min;
            number[i] = (int) ((Math.random() * ++max) + min);
        }

    }

    public String getNumber() {
        StringBuilder s = new StringBuilder();
        for (Object o : this.number) {
            s.append(o);
        }
        return s.toString();
    }

}
