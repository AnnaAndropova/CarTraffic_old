package Task1.Model.Enum;

public enum CarTypeEnum {

    PASSENGER(5, 8, 3),
    TRUCK(3, 6, 2);

    private int minSpeed;
    private int maxSpeed;
    private int boost;

    CarTypeEnum (int minSpeed, int maxSpeed, int boost) {
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
        this.boost = boost;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public int getMinSpeed() {
        return minSpeed;
    }

    public int getBoost() {
        return boost;
    }
}
