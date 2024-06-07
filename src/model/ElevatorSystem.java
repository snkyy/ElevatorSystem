package model;

import java.util.List;

public interface ElevatorSystem {

    class FloorState {
        public int floor;
        public boolean up;
        public boolean down;

        public FloorState(int floor, boolean up, boolean down) {
            this.floor = floor;
            this.up = up;
            this.down = down;
        }
    }

    class ElevatorState {
        public int id;
        public int currentFloor;
        public List<Integer> destinations;
        public boolean opened;

        public ElevatorState(int id, int currentFloor, List<Integer> destinations, boolean opened) {
            this.id = id;
            this.currentFloor = currentFloor;
            this.destinations = destinations;
            this.opened = opened;
        }
    }

    int getFloors();
    int getElevators();
    int getLowestFloor();

    void pickup(int floor, int direction);
    void step();
    void addDestination(int id, int destination);

    List<ElevatorState> getElevatorStatus();
    List<FloorState> getFloorStatus();
}
