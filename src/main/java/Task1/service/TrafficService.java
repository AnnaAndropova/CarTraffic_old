package Task1.service;

import Task1.model.Car;
import Task1.model.CarTraffic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import static java.lang.Thread.sleep;

@Service
public class TrafficService {

    @Autowired
    private CarService carService;
    @Autowired
    private CarTraffic traffic;
    @Autowired
    private ApplicationContext context;

    @Value("${timeStep.min}")
    int tsMin;
    @Value("${timeStep.max}")
    int tsMax;

    private int timeStep = 0;

    public void runModeling() throws Exception {
        while (true) {
            if (timeStep == 0) {
                Car car = context.getBean(Car.class);
                traffic.getCars().add(car);
                timeStep = getTimeStep();
            }
            carService.move(traffic.getCars());
            timeStep--;
            //System.out.println("----------------------------------------");
            sleep(1500);
        }
    }

    private int getTimeStep() {
        int max = tsMax;
        int min = tsMin;
        max -= min;
        return (int) ((Math.random() * ++max) + min);
    }

}
