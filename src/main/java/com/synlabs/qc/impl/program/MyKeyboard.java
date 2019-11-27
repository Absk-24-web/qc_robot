package com.synlabs.qc.impl.program;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MyKeyboard extends JFrame {

    //private JFrame frame = new JFrame("Typing Tutor");
    private JPanel parent = new JPanel(new GridLayout(0, 1));
    private JTextField textField = new JTextField();
    private JPanel[] panel;
    private JButton[][] button;
    public String s,set;
    private boolean capsLock = false;
    private QcInterfaceView interfaceView ;
    public StringBuilder str = new StringBuilder();
    private static final String[][] key = {

            {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "/", "*", "-", "+"},
            {"!", "%", "&", "\"", "'", "(", ")", "[", "]", ":", ";", "=", "BkSpc"},
            {"CapsLock", "q", "w", "e", "r", "t", "y", "u", "i", "o", "p", ",",},
            {"a", "s", "d", "f", "g", "h", "j", "k", "l", ".",},
            {"z", "x", "c", "v", "Space", "b", "n", "m", "<html>&#x027F5</html>", "<html>&#x027F6</html>", "Enter"}};

    public MyKeyboard(QcInterfaceView interfaceView ) {
        super("Typing Tutor");
        this.interfaceView = interfaceView;
        panel = new JPanel[6];
        for (int row = 0; row < key.length; row++) {
            panel[row] = new JPanel();
            button = new JButton[20][20];
            for (int column = 0; column < key[row].length; column++) {
                button[row][column] = new JButton(key[row][column]);
                button[row][column].putClientProperty("column", column);
                button[row][column].putClientProperty("row", row);
                button[row][column].putClientProperty("key", key[row][column]);
                button[row][column].addActionListener(new MyActionListener());
                panel[row].add(button[row][column]);
            }
            parent.add(panel[row]);
        }
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dispose();
        parent.add(textField);
        textField.setText(String.valueOf(str.append("|")));
        add(parent);
        setLocation(d.width / 2 - this.getSize().width / 2,
                        d.height / 2 - this.getSize().height / 2);
        pack();
        setResizable(false);
        setVisible(true);
        setEnabled(true);
    }
    int count =0;
    public class MyActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton btn = (JButton) e.getSource();
            s = (String) btn.getClientProperty("key");

            if (s != null) {
                if (s != "BkSpc" && s != "Enter" && s != "Space") {
                    if(s != "CapsLock" && s != "<html>&#x027F5</html>" && s!= "<html>&#x027F6</html>"){
                        if(capsLock) {
                            String st = s.toUpperCase();
                            s = st;
                        }
                        int a = str.indexOf("|");
                        str.replace(a,a,s);
                        textField.setText(str.toString());
                        //System.out.println(str.toString());
                    }

                }
            }
           // System.out.println(s);
            if ("BkSpc".equals(s)) {
                if(str.length()!=0) {
                    int n = str.indexOf("|");
                    str.deleteCharAt(n - 1);
                    textField.setText(String.valueOf(str));
                }
            } else if ("Enter".equals(s)) {
                str.deleteCharAt(str.length()-1);
                s = str.toString();
                interfaceView.confidencetxt.setText(s);
                dispose();
            } else if ("Space".equals(s)) {
                int a = str.indexOf("|");
                str.replace(a, a, " ");
                textField.setText(str.toString());
            } else if ("CapsLock".equals(s)) {
                capsLock = !capsLock;
            } else if ("<html>&#x027F5</html>".equals(s)) {
                if (str.indexOf("|") != 0) {
                    int b = str.indexOf("|");
                    str = swap(str.toString(), b - 1, b);
                    textField.setText(String.valueOf(str));
                } else {
                    str.replace(0, 1, "");
                    str.append("|");
                    textField.setText(str.toString());
                }
            } else if ("<html>&#x027F6</html>".equals(s)) {
                int c = str.indexOf("|");
                if (str.indexOf("|") != str.length() - 1) {
                    str = swap(str.toString(), c, c + 1);
                    textField.setText(str.toString());
                } else {
                    str.deleteCharAt(str.length() - 1);
                    str.insert(0, "|");
                    textField.setText(str.toString());
                }
            }

        }
        StringBuilder swap(String str, int i, int j)
        {
            StringBuilder sb = new StringBuilder(str);
            sb.setCharAt(i, str.charAt(j));
            sb.setCharAt(j, str.charAt(i));
            return sb;
        }
    }


//    public static void main(String[] args) {
//        EventQueue.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                MyKeyboard guI = new MyKeyboard();
//            }
//        });
//    }
}

