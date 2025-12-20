package a01c.e2;

import javax.swing.*;

import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {

    private final Map<JButton, Position> cells = new HashMap<>();
    private final LogicsImpl logics;

    public GUI(int size) {
        this.logics = new LogicsImpl();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(70 * size, 70 * size);
    
        JPanel panel = new JPanel(new GridLayout(size, size));
        this.getContentPane().add(panel);

        ActionListener al = e -> {
            final var jb = (JButton) e.getSource();
            final var pos = this.cells.get(jb);
            this.logics.hit(pos);
            refresh();
        };

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                final Position pos = new Position(j, i);
                final JButton jb = new JButton();
                this.cells.put(jb, pos);
                this.logics.addToLogicsGrid(pos);
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }

    private void refresh() {
        for (final var entry : this.cells.entrySet()) {
            final int value = this.logics.getValueFromLogics(entry.getValue());
            final JButton jb = entry.getKey();
            switch (value) {
                case 0 -> jb.setText("");
                case 1 -> jb.setText("1");
                case 2 -> jb.setText("2");
                case 3 -> jb.setText("3");
                case 4 -> jb.setText("4");
                case 5 -> jb.setText("*");
            }
        }
    }
}