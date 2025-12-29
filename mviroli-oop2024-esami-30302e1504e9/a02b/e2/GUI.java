package a02b.e2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {

    private final Map<JButton, Position> colCounters = new HashMap<>();
    private final Map<JButton, Position> rowCounters = new HashMap<>();
    private final Map<JButton, Position> cells = new HashMap<>();
    private final Logics logics;
    private int stars = 0;
    
    public GUI(int size) {
        this.logics = new LogicsImpl();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(70 * size, 70 * size);
    
        JPanel panel = new JPanel(new GridLayout(size, size));
        this.getContentPane().add(panel);

        ActionListener al = e -> {
            var jb = (JButton) e.getSource();
            this.logics.hit(this.cells.get(jb));
            this.refresh();
        };

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                final JButton jb = new JButton();
                final Position pos = new Position(j, i);
                jb.setEnabled(false);

                if (i == j) {
                    this.logics.addColCounter(pos);
                    this.colCounters.put(jb, pos);
                } else if (j == 9) {
                    this.logics.addRowCounter(pos);
                    this.rowCounters.put(jb, pos);
                } else if (j > i) {
                    this.logics.addCell(pos);
                    this.cells.put(jb, pos);
                    jb.setEnabled(true);
                }

                jb.addActionListener(al);
                panel.add(jb);
            }
        }



        this.setVisible(true);
    }

    private void refresh() {
        this.stars = 0;
        for (final var jbutton : this.cells.keySet()) {
            final int value = this.logics.getValue(this.cells.get(jbutton));
            if (value == 1) {
                jbutton.setText("*");
                this.stars++;
            }
        }
        if (this.stars == 5) {
            this.refreshCounters();
        } else if (this.stars >= 6) {
            this.reset();
        }
    }

    private void refreshCounters() {
        for (final var jbutton : this.colCounters.keySet()) {
            final int value = this.logics.getValue(this.colCounters.get(jbutton));
            jbutton.setText(Integer.toString(value));
        }
        for (final var jbutton : this.rowCounters.keySet()) {
            final int value = this.logics.getValue(this.rowCounters.get(jbutton));
            jbutton.setText(Integer.toString(value));
        }
    }

    private void reset() {
        for (final var jbutton : this.cells.keySet()) {
            jbutton.setText("");
        }
        for (final var jbutton : this.colCounters.keySet()) {
            jbutton.setText("");
        }
        for (final var jbutton : this.rowCounters.keySet()) {
            jbutton.setText("");
        }
        this.logics.reset();
    }
}
