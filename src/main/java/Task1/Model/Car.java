package Task1.Model;

import Task1.Model.Enum.CarStatusEnum;
import Task1.Model.Enum.CarTypeEnum;
import lombok.Data;

import java.util.Random;

@Data
public class Car {

    private Object[] number = new Object[6];
    private CarTypeEnum type;
    private int normalSpeed;
    private int curSpeed;
    private int position;
    private CarStatusEnum status;
    private int timeCrashed;
    private int timeHardSlowing;

    public Car() {
        double tmp = Math.random();
        if (1 - tmp < 0.5) {
            this.type = CarTypeEnum.TRUCK;
        } else {
            this.type = CarTypeEnum.PASSENGER;
        }
        int maxS = type.getMaxSpeed();
        int minS = type.getMinSpeed();
        maxS -= minS;
        this.curSpeed = (int) ((Math.random() * ++maxS) + minS);
        this.normalSpeed = curSpeed;
        this.position = 0;
        Random r = new Random();
        char c1 = (char)(r.nextInt(26) + 'A');
        char c2 = (char)(r.nextInt(26) + 'A');
        char c3 = (char)(r.nextInt(26) + 'A');
        number[0] = c1;
        number[4] = c2;
        number[5] = c3;
        for (int i = 1; i < number.length - 2; i++) {
            int max = 0;
            int min = 9;
            max -= min;
            number[i] = (int) ((Math.random() * ++max) + min);
        }
        this.timeCrashed = 0;
        this.timeHardSlowing = 0;
    }

}
