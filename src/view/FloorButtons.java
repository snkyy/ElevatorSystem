package view;

import javax.swing.*;
import java.awt.*;

class FloorButtons extends JPanel {

    JButton up = new JButton("▲");
    JButton down = new JButton("▼");
    JLabel floorLabel = new JLabel();

    public FloorButtons() {
        super();
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5,0,5,0);
        add(up, c); c.gridy = 1; add(down, c);

        c = new GridBagConstraints();
        c.gridx = 1; c.gridheight = 2;
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(10,30,0,0);
        add(floorLabel, c);
    }

    public void setColor(int direction, Color color) {
        if(direction > 0) {
            up.setBackground(color);
        } else {
            down.setBackground(color);
        }
    }

    public void setFloorLabel(String text) {
        floorLabel.setText(text);
        floorLabel.setFont(new Font("Calibri", Font.BOLD, 18));
    }
}
