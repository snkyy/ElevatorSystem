package presenter;

import model.ElevatorSystem;
import java.awt.*;

public class SimulationPresenter {

    private final int floors;
    private final int elevators;
    private final int lowestFloor;

    SimulationInterface simulationView;
    ElevatorSystem elevatorSystem;

    public SimulationPresenter(SimulationInterface simulationView, ElevatorSystem elevatorSystem) {
        this.simulationView = simulationView;
        this.elevatorSystem = elevatorSystem;
        this.floors = elevatorSystem.getFloors();
        this.elevators = elevatorSystem.getElevators();
        this.lowestFloor = elevatorSystem.getLowestFloor();

        simulationView.init(this, floors, elevators, lowestFloor);
    }

    private void clear() {
        for(int i = 0; i < floors; i++) {
            simulationView.setFloorButtons(i, 1, Color.PINK);
            simulationView.setFloorButtons(i, -1, Color.PINK);
            for(int j = 0; j < elevators; j++) {
                simulationView.setDoor(i, j, Color.GRAY);
            }
        }
    }

    private void update() {
        clear();
        for(ElevatorSystem.FloorState state : elevatorSystem.getFloorStatus()) {
            if(state.up) {
                simulationView.setFloorButtons(state.floor, 1, Color.MAGENTA);
            }
            if(state.down) {
                simulationView.setFloorButtons(state.floor, -1, Color.MAGENTA);
            }
        }
        for(ElevatorSystem.ElevatorState state : elevatorSystem.getElevatorStatus()) {
            for(int destination : state.destinations) {
                simulationView.setDoor(destination, state.id, Color.CYAN);
            }
            if(state.opened) {
                simulationView.setDoor(state.currentFloor, state.id, Color.YELLOW);
            } else {
                simulationView.setDoor(state.currentFloor, state.id, Color.ORANGE);
            }
        }
    }

    public void clickFloorButton(int floor, int direction) {
        elevatorSystem.pickup(floor, direction);
        update();
    }

    public void clickDoor(int id, int floor) {
        elevatorSystem.addDestination(id, floor);
        update();
    }

    public void step() {
        elevatorSystem.step();
        update();
    }
}
