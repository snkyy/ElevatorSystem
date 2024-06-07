package model;

import java.util.TreeSet;
import static java.lang.Math.*;

class Elevator {

    int id;
    int lowestFloor;
    int currentDirection;
    int currentFloor;
    int idleTime;
    boolean open;

    TreeSet<Integer> destinationsAbove;
    TreeSet<Integer> destinationsBelow;
    TreeSet<Integer> destinations = new TreeSet<>();
    TreeSet<Integer> localDestinationsAbove = new TreeSet<>();
    TreeSet<Integer> localDestinationsBelow = new TreeSet<>();

    public Elevator(int id, int lowestFloor, TreeSet<Integer> destinationsAbove, TreeSet<Integer> destinationsBelow) {
        this.id = id;
        this.lowestFloor = lowestFloor;
        this.currentFloor = -lowestFloor;
        this.currentDirection = 0;
        this.open = false;
        this.idleTime = 20;
        this.destinationsAbove = destinationsAbove;
        this.destinationsBelow = destinationsBelow;
    }

    // ok
    void destinationsUpdate() {
        localDestinationsBelow.retainAll(destinationsBelow);
        localDestinationsAbove.retainAll(destinationsAbove);
        if (destinationsAbove.contains(currentFloor)) {
            localDestinationsAbove.add(currentFloor);
        }
        if (destinationsBelow.contains(currentFloor)) {
            localDestinationsBelow.add(currentFloor);
        }
    }

    boolean destinationBelow() {
        return destinations.floor(currentFloor - 1) != null ||
                localDestinationsBelow.floor(currentFloor) != null ||
                localDestinationsAbove.floor(currentFloor - 1) != null;
    }

    boolean destinationAbove() {
        return destinations.ceiling(currentFloor + 1) != null ||
                localDestinationsBelow.ceiling(currentFloor + 1) != null ||
                localDestinationsAbove.ceiling(currentFloor) != null;
    }

    void directionUpdate() {
        if (localDestinationsBelow.isEmpty() && localDestinationsAbove.isEmpty() && destinations.isEmpty()) {
            currentDirection = 0;
            return;
        }
        if (currentDirection == 0) {
            if (destinationAbove()) {
                currentDirection = 1;
            } else if (destinationBelow()) {
                currentDirection = -1;
            }
        }
        else if (currentDirection == -1) {
            if (!(destinationBelow() || destinations.contains(currentFloor))) {
                currentDirection = 1;
            }
        }
        else if (currentDirection == 1) {
            if (!(destinationAbove() || destinations.contains(currentFloor))) {
                currentDirection = -1;
            }
        }
    }

    boolean doorsOpen() {
        if (destinations.contains(currentFloor)) {
            return true;
        }
        return (currentDirection == 1 && localDestinationsAbove.contains(currentFloor)) ||
                (currentDirection == -1 && localDestinationsBelow.contains(currentFloor));
    }

    boolean changePosition() {
        if (currentDirection == 1) {
            if (destinations.contains(currentFloor) || localDestinationsAbove.contains(currentFloor)) {
                return false;
            } else {
                currentFloor += 1;
                return true;
            }
        }
        else if (currentDirection == -1) {
            if (destinations.contains(currentFloor) || localDestinationsBelow.contains(currentFloor)) {
                return false;
            } else {
                currentFloor -= 1;
                return true;
            }
        }
        else if (currentDirection == 0) {
            if (idleTime < 0) {
                if (currentFloor < -lowestFloor) {
                    currentFloor += 1;
                } else if (currentFloor > -lowestFloor) {
                    currentFloor -= 1;
                }
            }
        }
        return false;
    }

    public void step() {
        open = false;
        destinationsUpdate();
        if (localDestinationsBelow.isEmpty() && localDestinationsAbove.isEmpty() && destinations.isEmpty()) {
            idleTime -= 1;
        } else {
            idleTime = 20;
        }
        directionUpdate();
        if (changePosition()) {
            destinationsUpdate();
            directionUpdate();
        }
        open = doorsOpen();
        if (open) {
            destinationsBelow.remove(currentFloor);
            destinationsAbove.remove(currentFloor);
            destinations.remove(currentFloor);
        }
    }

    public void addFloorRequest(int floor, int direction) {
        if (direction == 1) {
            localDestinationsAbove.add(floor);
        } else {
            localDestinationsBelow.add(floor);
        }
    }

    public void addDestination(int floor) {
        destinations.add(floor);
    }

    ElevatorSystem.ElevatorState getElevatorState() {
        return new ElevatorSystem.ElevatorState(this.id, this.currentFloor, this.destinations.stream().toList(), this.open);
    }

    int requestsIncludedDistance(int requestedFloor, int direction) {
        destinationsUpdate();
        directionUpdate();
        int lowestRequest = currentFloor;
        int highestRequest = currentFloor;

        if(!destinations.isEmpty()) {
            lowestRequest = min(lowestRequest, destinations.first());
            highestRequest = max(highestRequest, destinations.last());
        }
        if(!localDestinationsBelow.isEmpty()) {
            lowestRequest = min(lowestRequest, localDestinationsBelow.first());
            highestRequest = max(highestRequest, localDestinationsBelow.last());
        }
        if(!localDestinationsAbove.isEmpty()) {
            lowestRequest = min(lowestRequest, localDestinationsAbove.first());
            highestRequest = max(highestRequest, localDestinationsAbove.last());
        }

        if(currentDirection == 0) {
            return abs(currentFloor - requestedFloor);
        }
        else if (currentDirection == 1 && direction == 1) {
            if(requestedFloor >= currentFloor) {
                return requestedFloor - currentFloor;
            }
            else if (requestedFloor >= lowestRequest) {
                return (highestRequest - currentFloor) + (highestRequest - lowestRequest) + (requestedFloor - lowestRequest);
            }
            else {
                return (highestRequest - currentFloor) + (highestRequest - requestedFloor);
            }
        }
        else if (currentDirection == -1 && direction == -1) {
            if(requestedFloor <= currentFloor) {
                return currentFloor - requestedFloor;
            }
            else if(requestedFloor <= highestRequest) {
                return (currentFloor - lowestRequest) + (highestRequest - lowestRequest) + (highestRequest - requestedFloor);
            }
            else {
                return (currentFloor - lowestRequest) + (requestedFloor - lowestRequest);
            }
        }
        else if (currentDirection == 1 && direction == -1) {
            if(requestedFloor >= highestRequest) {
                return (requestedFloor - currentFloor);
            }
            else {
                return (highestRequest - currentFloor) + (highestRequest - requestedFloor);
            }
        }
        else if (currentDirection == -1 && direction == 1) {
            if (requestedFloor <= lowestRequest) {
                return (currentDirection - requestedFloor);
            } else {
                return (currentDirection - lowestRequest) + (requestedFloor - lowestRequest);
            }
        }
        return 0;
    }
}