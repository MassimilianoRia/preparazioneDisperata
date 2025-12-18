package a01b.e2;

import javax.swing.*;

import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {

    private final static String STAR = "*";
    private final static String CIRCLE = "o";
    private final static String EMPTY = "";

    private final Map<Position, JButton> cells = new HashMap<>();
    private final LogicsImpl logics;
    
    public GUI(int size) {
        this.logics = new LogicsImpl();

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(70 * size, 70 * size);
    
        JPanel panel = new JPanel(new GridLayout(size, size));
        this.getContentPane().add(panel);

        ActionListener al = e -> {
            var jb = (JButton) e.getSource();
            for (final var tmp : this.cells.entrySet()) {
                if (Objects.equals(tmp.getValue(), jb)) {
                    this.logics.hit(tmp.getKey());
                }
            }
            refresh();
        };

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                final Position pos = new Position(j, i);
                final JButton jb = new JButton();
                this.cells.put(pos, jb);
                this.logics.add(pos);
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }

    private void refresh() {
        for (final var entry : this.cells.entrySet()) {
            final int state = this.logics.getGridValue(entry.getKey());
            final JButton jb = entry.getValue();
            switch (state) {
                case 0 -> jb.setText(EMPTY);
                case 1 -> jb.setText(STAR);
                case 2 -> jb.setText(CIRCLE);
            }
        }
    }

}