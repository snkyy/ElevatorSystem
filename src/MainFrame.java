import model.ElevatorSystemImpl;
import presenter.SimulationPresenter;
import view.SimulationView;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    JLabel fLabel = new JLabel("Floors:");
    JLabel eLabel = new JLabel("Elevators:");
    JLabel lfLabel = new JLabel("Lowest floor:");

    JTextField floors = new JTextField("7", 8);
    JTextField elevators = new JTextField("4", 8);
    JTextField lowestFloor = new JTextField("-2", 8);

    JPanel panel = new JPanel();
    JButton start = new JButton("Start");

    public MainFrame() {
        super();
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        add(panel);

        c = new GridBagConstraints();
        panel.setLayout(new GridBagLayout());
        c.gridy = 0; panel.add(fLabel, c);
        c.gridy = 1; panel.add(floors, c);
        c.gridy = 2; panel.add(eLabel, c);
        c.gridy = 3; panel.add(elevators, c);
        c.gridy = 4; panel.add(lfLabel, c);
        c.gridy = 5; panel.add(lowestFloor, c);
        c.gridy = 7; panel.add(start, c);

        start.setBackground(Color.PINK);
        start.setPreferredSize(new Dimension(100, 25));
        start.addActionListener(e -> {
            int el, fl, low;
            try {
                fl = Integer.parseInt(floors.getText());
                el = Integer.parseInt(elevators.getText());
                low = Integer.parseInt(lowestFloor.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid Input");
                return;
            }

            if(fl < 1 || el < 1 || el > 16 || low < (1-fl) || low > 0) {
                JOptionPane.showMessageDialog(this, "Invalid Input");
                return;
            }

            SimulationView view = new SimulationView();
            new SimulationPresenter(view, new ElevatorSystemImpl(fl, el, low));
        });

        setTitle("ElevatorSimulator");
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
    }
}