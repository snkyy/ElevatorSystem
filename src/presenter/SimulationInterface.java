package presenter;

import java.awt.*;

public interface SimulationInterface {
    void init(SimulationPresenter presenter, int floors, int elevators, int lowestFloor);
    void setFloorButtons(int floor, int direction, Color color);
    void setDoor(int floor, int elevator, Color color);
}
