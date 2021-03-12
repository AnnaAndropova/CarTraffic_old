package Task1.Service;

import Task1.Model.Car;
import Task1.Model.Enum.CarStatusEnum;
import Task1.Model.Enum.CellStatusEnum;
import Task1.Model.Road;
import org.springframework.stereotype.Service;

import java.util.Queue;

@Service
public class CarService {

    private static final int CAR_LENGTH = 3;

    public void move(Queue<Car> cars, Road road) {
        for (Car car : cars) {
            if (car.getTimeCrashed() != 0) car.setTimeCrashed(car.getTimeCrashed() - 1);
            changeCoordinate(car, road);
            if (car.getStatus() != CarStatusEnum.FINISHED_MOVING) {
                if (car.getTimeCrashed() == 0) {
                    if (car.getTimeHardSlowing() == 0) {
                        hardSlowDown(car);
                        if (car.getTimeHardSlowing() == 0) {
                            speedUp(car, road);
                            if (car.getStatus() == CarStatusEnum.MOVING) slowDown(car, road);
                        }
                    } else {
                        car.setStatus(CarStatusEnum.MOVING);
                        car.setTimeHardSlowing(car.getTimeHardSlowing() - 1);
                    }
                }
            }
        }
        for (Car car : cars) {
            crash(car, road);
        }
        printInfo(cars);
    }

    private void changeCoordinate(Car car, Road road) {
        int idx = car.getPosition();
        if (idx == road.getLength()) idx--;
        road.getCells()[idx].setStatus(CellStatusEnum.FREE);
        int nextPosition = car.getPosition() + car.getCurSpeed();
        if (nextPosition > road.getLength()) {
            nextPosition = road.getLength();
            car.setStatus(CarStatusEnum.FINISHED_MOVING);
        }
        Car prev = getPrev(car, road);
        if (prev != null && prev.getPosition() < nextPosition) {
            nextPosition = prev.getPosition();
        }
        car.setPosition(nextPosition);
        if (nextPosition != road.getLength()) {
            road.getCells()[nextPosition].setCar(car);
        }
    }

    private void crash(Car car, Road road) {
        Car next = getNext(car, road);
        if (next != null &&
                next.getStatus() != CarStatusEnum.CRASHED &&
                next.getStatus() != CarStatusEnum.WAITING &&
                car.getPosition() - next.getPosition() <= CAR_LENGTH * 2) {
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
        if (1 - chance >= 0.9) {
            int speed = Math.max(car.getCurSpeed() - 2 * car.getType().getBoost(), 0);
            car.setCurSpeed(speed);
            car.setTimeHardSlowing(getRandom(1, 3));
            car.setStatus(CarStatusEnum.HARD_SLOWING_DOWN);
        }
    }

    private void slowDown(Car car, Road road) {
        car.setStatus(CarStatusEnum.MOVING);
        Car prev = getPrev(car, road);
        if (prev != null && car.getCurSpeed() > prev.getCurSpeed()) {
            if (prev.getPosition() - car.getPosition() <= CAR_LENGTH * 4) {
                int speed = Math.max(prev.getCurSpeed(), car.getCurSpeed() - car.getType().getBoost());
                car.setCurSpeed(speed);
                car.setStatus(CarStatusEnum.SLOWING_DOWN);
            }
        }
        if (car.getCurSpeed() == 0) {
            car.setStatus(CarStatusEnum.WAITING);
        }
    }

    private void speedUp(Car car, Road road) {
        car.setStatus(CarStatusEnum.MOVING);
        if (car.getCurSpeed() < car.getNormalSpeed()) {
            Car prev = getPrev(car, road);
            int speed = Math.min(car.getCurSpeed() + car.getType().getBoost(), car.getNormalSpeed());
            if (prev != null &&
                    prev.getPosition() - car.getPosition() <= CAR_LENGTH * 4 &&
                    prev.getPosition() - car.getPosition() > CAR_LENGTH * 2) {
                speed = Math.min(speed, prev.getCurSpeed());
            }
            if (car.getCurSpeed() < speed) {
                car.setCurSpeed(speed);
                car.setStatus(CarStatusEnum.SPEEDING_UP);
            }
        }
    }

    private void printInfo(Queue<Car> cars) {
        boolean isFinished = false;
        for (Car car : cars) {
            System.out.println(getCarNumber(car) +
                    " " + car.getType() +
                    " " + car.getStatus() +
                    " " + car.getPosition());
            if (car.getStatus() == CarStatusEnum.FINISHED_MOVING) {
                isFinished = true;
            }
        }
        if (isFinished) cars.poll();
    }

    private String getCarNumber(Car car) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < car.getNumber().length; i++) {
            s.append(car.getNumber()[i]);
        }
        return s.toString();
    }

    private int getRandom(int min, int max) {
        max -= min;
        return (int) ((Math.random() * ++max) + min);
    }

    private Car getNext(Car car, Road road) {
        if (car.getPosition() > 0) {
            for (int i = car.getPosition() - 1; i >= 0; i--) {
                if (road.getCells()[i].getStatus() == CellStatusEnum.BUSY) {
                    return road.getCells()[i].getCar();
                }
            }
        }
        return null;
    }

    private Car getPrev(Car car, Road road) {
        for (int i = car.getPosition() + 1; i < road.getCells().length; i++) {
            if (road.getCells()[i].getStatus() == CellStatusEnum.BUSY) {
                return road.getCells()[i].getCar();
            }
        }
        return null;
    }
}
