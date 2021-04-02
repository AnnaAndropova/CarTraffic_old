package Task1.service;

import Task1.model.Car;
import Task1.model.Enum.CarStatusEnum;
import Task1.model.Road;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Queue;

@Service
public class CarService {

    @Autowired
    private Road road;
    @Value("${time.slowDown.min}")
    int timeSlowMin;
    @Value("${time.slowDown.max}")
    int timeSlowMax;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public void draw() {
        for (int j = 0; j < 3; j++) {
            switch (j) {
                case 0:
                    for (int i = 0; i < road.getLength(); i++) {
                        System.out.print("__");
                    }
                    System.out.println();
                    break;
                case 1:
                    for (int i = 0; i < road.getLength(); i++) {
                        if (road.getCells()[i].getCar() != null) {
                            switch (road.getCells()[i].getCar().getStatus()) {
                                case MOVING:
                                    System.out.print("|*");
                                    break;
                                case WAITING:
                                    System.out.print("|" + ANSI_BLUE + "*" + ANSI_RESET + "");
                                    break;
                                case HARD_SLOWING_DOWN:
                                    System.out.print("|" + ANSI_RED + "*" + ANSI_RESET + "");
                                    break;
                                case CRASHED:
                                    System.out.print("|" + ANSI_PURPLE + "*" + ANSI_RESET + "");
                                    break;
                                case SPEEDING_UP:
                                    System.out.print("|" + ANSI_GREEN + "*" + ANSI_RESET + "");
                                    break;
                                case SLOWING_DOWN:
                                    System.out.print("|" + ANSI_YELLOW + "*" + ANSI_RESET + "");
                                    break;
                            }
                        } else {
                            System.out.print("| ");
                        }
                    }
                    System.out.println();
                    break;
                case 2:
                    for (int i = 0; i < road.getLength(); i++) {
                        System.out.print("‾‾");
                    }
                    System.out.println();
                    break;
            }
        }
    }

    public void move(Queue<Car> cars) {
        draw();
        for (Car car : cars) {
            changeCoordinate(car);
        }
        for (Car car : cars) {
            crash(car);
        }
        boolean isFinished = false;
        for (Car car : cars) {
            if (car.getTimeCrashed() != 0) {
                car.setTimeCrashed(car.getTimeCrashed() - 1);
            } else {
                //changeCoordinate(car);
                if (car.getStatus() != CarStatusEnum.FINISHED_MOVING) {
                    if (car.getTimeCrashed() == 0) {
                        if (car.getTimeHardSlowing() == 0) {
                            hardSlowDown(car);
                            if (car.getTimeHardSlowing() == 0) {
                                speedUp(car);
                                if (car.getStatus() == CarStatusEnum.MOVING) slowDown(car);
                            }
                        } else {
                            if (car.getCurSpeed() > 0) {
                                car.setStatus(CarStatusEnum.MOVING);
                            } else {
                                car.setStatus(CarStatusEnum.WAITING);
                            }
                            car.setTimeHardSlowing(car.getTimeHardSlowing() - 1);
                        }
                    }
                } else {
                    isFinished = true;
                }
            }
        }
        if (isFinished) {
            cars.poll();
        }

        //printInfo(cars);
    }

    private void changeCoordinate(Car car) {
        for (int i = 0; i < car.getLength(); i++) {
            car.getCells()[i].clear();
        }
        int nextPosition = car.getPosition() + car.getCurSpeed();
        if (nextPosition >= road.getLength()) {
            nextPosition = road.getLength();
            car.setStatus(CarStatusEnum.FINISHED_MOVING);
        }
        Car prev = getPrev(car);
        if (prev != null && prev.getPosition() - prev.getLength() < nextPosition) {
            nextPosition = prev.getPosition() - prev.getLength() - 1;
        }
        car.setPosition(nextPosition);
        if (car.getStatus() != CarStatusEnum.FINISHED_MOVING) {
            for (int i = 0; i < car.getLength(); i++) {
                if (nextPosition - i >= 0) {
                    road.getCells()[nextPosition - i].setCar(car);
                    car.getCells()[i] = road.getCells()[nextPosition - i];
                    car.getCells()[i].setCar(car);
                }
            }
        }
    }

    private void crash(Car car) {
        Car next = getNext(car);
        if (next != null && next.getCurSpeed() != 0 &&
                car.getPosition() - car.getLength() - next.getPosition() <= next.getLength()) {
            doCrash(car, car.getTimeCrashed() + 1);
            doCrash(next, car.getTimeCrashed() + 1);
        }
    }

    private void doCrash(Car car, int time) {
        car.setCurSpeed(0);
        car.setTimeHardSlowing(0);
        car.setTimeCrashed(time);
        car.setStatus(CarStatusEnum.CRASHED);
    }

    private void hardSlowDown(Car car) {
        double chance = Math.random();
        if (1 - chance >= 0.95) {
            int speed = Math.max(car.getCurSpeed() - 2 * car.getBoost(), 0);
            car.setCurSpeed(speed);
            car.setTimeHardSlowing(getRandom());
            car.setStatus(CarStatusEnum.HARD_SLOWING_DOWN);
        }
    }

    private void slowDown(Car car) {
        car.setStatus(CarStatusEnum.MOVING);
        Car prev = getPrev(car);
        if (prev != null && car.getCurSpeed() > prev.getCurSpeed()) {
            if (prev.getPosition() - prev.getLength() - car.getPosition() <= car.getLength() * 3) {
                int speed = Math.max(prev.getCurSpeed(), car.getCurSpeed() - car.getBoost());
                car.setCurSpeed(speed);
                car.setStatus(CarStatusEnum.SLOWING_DOWN);
            }
        }
        if (car.getCurSpeed() == 0) car.setStatus(CarStatusEnum.WAITING);
    }

    private void speedUp(Car car) {
        car.setStatus(CarStatusEnum.MOVING);
        if (car.getCurSpeed() < car.getNormalSpeed()) {
            Car prev = getPrev(car);
            int speed = Math.min(car.getCurSpeed() + car.getBoost(), car.getNormalSpeed());
            if (prev != null &&
                    prev.getPosition() - prev.getLength() - car.getPosition() <= car.getLength() * 3 &&
                    prev.getPosition() - prev.getLength() - car.getPosition() > car.getLength()) {
                speed = Math.min(speed, prev.getCurSpeed());
            } else if (prev != null &&
                    prev.getPosition() - prev.getLength() - car.getPosition() <= car.getLength()) {
                return;
            }
            if (car.getCurSpeed() < speed) {
                car.setCurSpeed(speed);
                if (car.getCurSpeed() != 0)
                    car.setStatus(CarStatusEnum.SPEEDING_UP);
            }
        }
    }

    private void printInfo(Queue<Car> cars) {
        boolean isFinished = false;
        for (Car car : cars) {
            System.out.println(car.getNumber() +
                    " " + car.getType() +
                    " " + car.getStatus() +
                    " " + car.getPosition());
            if (car.getStatus() == CarStatusEnum.FINISHED_MOVING) {
                isFinished = true;
            }
        }
        if (isFinished) cars.poll();
    }

    private int getRandom() {
        int min = timeSlowMin;
        int max = timeSlowMax;
        max -= min;
        return (int) ((Math.random() * ++max) + min);
    }

    private Car getNext(Car car) {
        if (car.getPosition() > 0) {
            for (int i = car.getPosition() - car.getLength(); i >= 0; i--) {
                if (road.getCells()[i].getCar() != null) {
                    return road.getCells()[i].getCar();
                }
            }
        }
        return null;
    }

    private Car getPrev(Car car) {
        if (car.getPosition() >= 0) {
            for (int i = car.getPosition() + 1; i < road.getCells().length; i++) {
                if (road.getCells()[i].getCar() != null) {
                    return road.getCells()[i].getCar();
                }
            }
        }
        return null;
    }
}
