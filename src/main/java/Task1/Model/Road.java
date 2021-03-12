package Task1.Model;

import Task1.Model.Enum.CellStatusEnum;
import lombok.Data;

@Data
public class Road {

    private int length;
    private RoadCell[] cells;

    public Road(int length) {
        this.length = length;
        this.cells = new RoadCell[length];
        for (int i = 0; i < length; i++) {
            cells[i] = new RoadCell();
            cells[i].setStatus(CellStatusEnum.FREE);
        }
    }

}
