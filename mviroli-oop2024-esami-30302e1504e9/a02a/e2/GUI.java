package a02a.e2;

import javax.swing.*;

import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {

    private final Map<JButton, Position> cells = new HashMap<>();
    private final Logics logics = new LogicsImpl();
    
    public GUI(int size) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(70 * size, 70 * size);
    
        JPanel panel = new JPanel(new GridLayout(size, size));
        this.getContentPane().add(panel);

        ActionListener al = e -> {
           if (this.logics.nextStep()) {
                dispose();
           } else {
                refresh();
           }
        };

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                final Position pos = new Position(j, i);
                final JButton jb = new JButton();
                if (pos.x() == 0 || pos.x() == size - 1 || pos.y() == 0 || pos.y() == size - 1) {
                    this.logics.addCell(pos);
                    this.cells.put(jb, pos);
                } else {
                    jb.setEnabled(false);
                }
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        
        this.logics.initGrid();
        this.refresh();
        this.setVisible(true);
    }

    private void refresh() {
        for (final var entry : this.cells.entrySet()) {
            final var value = this.logics.getValue(entry.getValue());
            final var jb = entry.getKey();
            switch (value) {
                case 0 -> jb.setText("");
                case 1 -> jb.setText("*");
                case 2 -> jb.setText("o");
            }
        }
    }
}
