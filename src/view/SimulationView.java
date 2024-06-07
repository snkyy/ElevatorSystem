package view;

import presenter.SimulationInterface;
import presenter.SimulationPresenter;
import javax.swing.*;
import java.awt.*;

public class SimulationView extends JPanel implements SimulationInterface {

    private static class BottomPanel extends JPanel {
        JButton step = new JButton("Step");

        public BottomPanel() {
            super();
            step.setBackground(Color.PINK);
            step.setPreferredSize(new Dimension(100, 25));
            add(step);
        }
    }

    JFrame mainFrame;
    private final BottomPanel bottomPanel = new BottomPanel();
    private final BuildingView buildingView = new BuildingView();

    @Override
    public void init(SimulationPresenter presenter, int floors, int elevators, int lowestFloor) {
        mainFrame = new JFrame();
        buildingView.init(presenter, floors, elevators, lowestFloor);
        bottomPanel.step.addActionListener(e -> presenter.step());
        mainFrame.setLayout(new BorderLayout());
        mainFrame.add(bottomPanel, BorderLayout.PAGE_END);
        mainFrame.add(new JScrollPane(buildingView), BorderLayout.CENTER);
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    @Override
    public void setFloorButtons(int floor, int direction, Color color) {
        buildingView.floorButtons[floor].setColor(direction, color);
    }

    @Override
    public void setDoor(int floor, int elevator, Color color) {
        buildingView.doors[floor][elevator].setBackground(color);
    }
}
