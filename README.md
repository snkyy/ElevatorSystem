# Elevator system simulator

### Setup
Clone the repository and run Main.java class.

### How to use
- After running the programm you can either click start button right away or input number of floors/elevators and lowest floor manually. 
- You can click ▲/▼ buttons on the left to summon the elevator to a certain floor
- You can click on the doors symbol of the elevator x on floor y to simulate clicking the y floor button inside the elevator x.
- You can click step to see the next step of the simulation

Orange doors symbolize the current position of the elevators.
After clicking on the gray door, they will switch color to blue.
Doors become yellow when they are opened.

### Additional Information

Each elevator moves upwards and downwards and during that it tries to keep the direction of its movement until it reaches lowest/highest requested floor. It will stop along the way if someone requests it and if the direction of the request matches the current direction of the elevator.
After arriving at requested floor elevator opens its doors for the duration of one step of the simulation. After 20 steps of idleness the elevator will begin to return to floor 0.
