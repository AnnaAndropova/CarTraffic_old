package Task1.Service;

import Task1.Model.Car;
import Task1.Model.CarTraffic;
import org.springframework.stereotype.Service;

import static java.lang.Thread.sleep;

@Service
public class TrafficService {

    private int timeStep = 0;

    public void runModeling(CarService cs, CarTraffic traffic) throws InterruptedException {
        while (true) {
            if (timeStep == 0) {
                traffic.getCars().add(new Car());
                timeStep = getTimeStep();
            }
            cs.move(traffic.getCars(), traffic.getRoad());
            timeStep--;
            System.out.println("----------------------------------------");
            sleep(1000);
        }
    }

    private int getTimeStep(){
        int max = 10;
        int min = 5;
        max -= min;
        return (int) ((Math.random() * ++max) + min);
    }

}
