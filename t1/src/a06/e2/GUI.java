package a06.e2;

import javax.swing.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame {
    
    private final List<List<JButton>> cells = new ArrayList<>();
    private final LogicsImpl logics;
    
    public GUI(int size) {
        this.logics = new LogicsImpl(size);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel main = new JPanel(new BorderLayout());
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(main);
        main.add(BorderLayout.CENTER, panel);
        JButton fire = new JButton("Fire");
        main.add(BorderLayout.SOUTH, fire);
        
        ActionListener al = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                final var jb = (JButton) e.getSource();
                logics.fire();
                refresh();
                if (logics.isOver()) {
                    jb.setEnabled(false);
                }
                //oppure
                //jb.setEnabled(!logics.isOver());
            }
        };
        
        fire.addActionListener(al);
        
        for (int i=0; i<size; i++){
            this.cells.add(new ArrayList<>());
            for (int j=0; j<size; j++){
                final JButton jb = new JButton();
                this.cells.get(i).add(jb);
                panel.add(jb);
            }
        }

        refresh();

        this.setVisible(true);
    }    

    private void refresh() {
        for (int i=0; i<this.cells.size(); i++){
            for (int j=0; j<this.cells.get(i).size(); j++){
                final int value = this.logics.getValue(i, j);
                final String textValue = (value == 0) ? "" : Integer.toString(value);
                this.cells.get(i).get(j).setText(textValue);
            }
        }
    }
}
