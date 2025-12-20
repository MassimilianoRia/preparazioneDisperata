package a01a.e2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {

    private final static String STAR = "*";
    private final static String EMPTY = " ";
    private final Map<JButton, Position> cells = new HashMap<>();
    private final Logics logics = new LogicsImpl();
    private int phase;
    
    public GUI(int size) {
        this.phase = 1;
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(70 * size, 70 * size);
    
        JPanel panel = new JPanel(new GridLayout(size, size));
        this.getContentPane().add(panel);

        ActionListener al = e -> {
            var jb = (JButton) e.getSource();
            final var pos = this.cells.get(jb);
            switch (phase) {
                case 1 -> {
                    if (this.logics.isBorder(pos)) {
                        if (this.logics.firstHit(pos)) {
                            jb.setText(STAR);
                        } else {
                            int nVertex = 1;
                            for (final var vertex : this.logics.computeRectangleVertex()) {
                                this.cells.entrySet().stream()
                                .filter(el -> Objects.equals(vertex, el.getValue()))
                                .map(el -> el.getKey())
                                .findFirst()
                                .get()
                                .setText(Integer.toString(nVertex));;
                                nVertex++;
                            }
                            for (final var cell : this.logics.computeRectangleEdges()) {
                                this.cells.entrySet().stream()
                                .filter(el -> Objects.equals(cell, el.getValue()))
                                .map(el -> el.getKey())
                                .findFirst()
                                .get()
                                .setText("o");
                            }
                            this.phase++;
                        }
                    }
                }
                case 2 -> {
                    this.logics.reset();
                    for (final var tmpJb : this.cells.keySet()) {
                        tmpJb.setText(EMPTY);
                    }
                    this.phase--;
                }
            }
        };

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                final JButton jb = new JButton();
                final Position pos = new Position(j, i);
                this.cells.put(jb, pos);
                this.logics.add(pos);
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }
}
