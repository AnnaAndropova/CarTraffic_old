package Task1.Model;

import Task1.Model.Enum.CellStatusEnum;
import lombok.Data;

@Data
public class RoadCell {

    private Car car;
    private CellStatusEnum status;

    public RoadCell(){
        this.car = null;
        this.status = null;
    }

    public void setStatus(CellStatusEnum status) {
        this.status = status;
        if (status == CellStatusEnum.FREE) {
            this.car = null;
        }
    }

    public void setCar(Car car) {
        this.car = car;
        this.status = CellStatusEnum.BUSY;
    }

}
