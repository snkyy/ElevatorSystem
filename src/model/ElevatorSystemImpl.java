package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ElevatorSystemImpl implements ElevatorSystem {

    private final int elevators, floors, lowestFloor;
    private final List<Elevator> elevatorsList = new ArrayList<>();
    private TreeSet<Integer> destinationsAbove = new TreeSet<>();
    private TreeSet<Integer> destinationsBelow = new TreeSet<>();

    public ElevatorSystemImpl(int floors, int elevators, int lowestFloor) {
        this.elevators = elevators;
        this.floors = floors;
        this.lowestFloor = lowestFloor;

        for(int i = 0; i < elevators; i++) {
            elevatorsList.add(new Elevator(i, lowestFloor, destinationsAbove, destinationsBelow));
        }
    }

    @Override
    public int getFloors() {
        return floors;
    }

    @Override
    public int getElevators() {
        return elevators;
    }

    @Override
    public int getLowestFloor() {
        return lowestFloor;
    }

    @Override
    public void pickup(int floor, int direction) {
        if(direction > 0) {
            destinationsAbove.add(floor);
        } else {
            destinationsBelow.add(floor);
        }

        elevatorsList.stream()
                .min(Comparator.comparingInt(e -> e.requestsIncludedDistance(floor, direction)))
                .get().addFloorRequest(floor, direction);
    }

    @Override
    public void step() {
        for(Elevator elevator : elevatorsList) {
            elevator.step();
        }
    }

    @Override
    public List<FloorState> getFloorStatus() {
        return IntStream.range(0, floors)
                .mapToObj(f -> new FloorState(f, destinationsAbove.contains(f), destinationsBelow.contains(f)))
                .collect(Collectors.toList());
    }

    @Override
    public void addDestination(int id, int destination) {
        elevatorsList.get(id).addDestination(destination);
    }

    @Override
    public List<ElevatorState> getElevatorStatus() {
        return elevatorsList.stream().map(Elevator::getElevatorState).collect(Collectors.toList());
    }
}
