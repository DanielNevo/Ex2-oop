package gameClient;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Label extends JFrame implements ActionListener {
    JTextField tf;
    JLabel l;
    JButton b;

public Label(){
    tf=new JFormattedTextField( );
    tf.setBounds(50,50,150,20);
    l=new JLabel();
    l.setBounds(50,50,250,20);
    l.setText("enter your id");
    b=new JButton("enter the level");
    b.setBounds(50,150,95,30);
   b.addActionListener(this);
this.add(b);
this.add(tf);
this.add(l);
setSize(400,400);
setLayout(null);
setVisible(true);

}

    @Override
    public void actionPerformed(ActionEvent e) {
String host=tf.getText();

String ip=java.text.NumberFormat.getNumberInstance().format(host);


    }
}
