package view;

import presenter.SimulationPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BuildingView extends JPanel {

    int floors;
    int elevators;
    FloorButtons[] floorButtons;
    JLabel[][] doors;
    JButton[] floorSelectionButtons;

    void init(SimulationPresenter presenter, int floors, int elevators, int lowestFloor) {
        this.floors = floors;
        this.elevators = elevators;

        floorButtons = new FloorButtons[this.floors];
        for(int i = 0; i < this.floors; i++) {
            floorButtons[i] = new FloorButtons();
            floorButtons[i].up.setBackground(Color.PINK);
            floorButtons[i].down.setBackground(Color.PINK);
            floorButtons[i].setFloorLabel(Integer.toString(i+lowestFloor));

            int floor = i;
            floorButtons[i].up.addActionListener(e -> {
                presenter.clickFloorButton(floor, 1);
            });
            floorButtons[i].down.addActionListener(e -> {
                presenter.clickFloorButton(floor, -1);
            });
        }

        doors = new JLabel[this.floors][this.elevators];
        for(int i = 0; i < this.floors; i++) {
            for(int j = 0; j < this.elevators; j++) {
                doors[i][j] = new JLabel();
                doors[i][j].setOpaque(true);
                doors[i][j].setPreferredSize(new Dimension(40,64));
                doors[i][j].setBackground(Color.LIGHT_GRAY);

                int floor = i, elevator = j;
                doors[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        presenter.clickDoor(elevator, floor);
                    }
                });
            }
        }

        floorSelectionButtons = new JButton[this.elevators];
        for(int i = 0; i < this.elevators; i++) {
            floorSelectionButtons[i] = new JButton("floor selection");
            floorSelectionButtons[i].setBackground(Color.PINK);
        }

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        for(int i = 0; i < this.floors; i++) {
            c.gridx = 0; c.gridy = 2*i;
            c.insets = new Insets(20,0,5,20);
            c.anchor = GridBagConstraints.CENTER;
            add(floorButtons[this.floors-1-i], c);
            c.anchor = GridBagConstraints.PAGE_END;
            for(int j = 0; j < this.elevators; j++) {
                c.gridx = j+1;
                add(doors[this.floors-1-i][j], c);
            }
        }

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = this.elevators + 1;
        for(int i = 0; i < this.floors; i++) {
            c.gridy = 2*i + 1;
            JSeparator separator = new JSeparator(JSeparator.HORIZONTAL);
            separator.setForeground(Color.BLACK);
            add(separator, c);
        }
    }
}
