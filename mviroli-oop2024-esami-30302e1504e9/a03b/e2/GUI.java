package a03b.e2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {

    private final Map<JButton, Position> cells = new HashMap<>();
    private final LogicsImpl logics = new LogicsImpl();
    
    public GUI(int size) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(70 * size, 70 * size);
    
        JPanel panel = new JPanel(new GridLayout(size, size));
        this.getContentPane().add(panel);

        ActionListener al = e -> {
            final var jb = (JButton) e.getSource();
            final var pos = this.cells.get(jb);
            this.logics.hit(pos);
            this.refresh();
        };

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                final JButton jb = new JButton();
                final var pos = new Position(j, i);
                this.cells.put(jb, pos);
                this.logics.addCell(pos);
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }

    private void refresh() {
        for (final var jb : this.cells.keySet()) {
            final var pos = this.cells.get(jb);
            final int value = this.logics.getValue(pos);
            switch (value) {
                case 0 -> jb.setText("");
                case 1 -> jb.setText("^");
                case 2 -> jb.setText("v");
            }
        }
    }
}
