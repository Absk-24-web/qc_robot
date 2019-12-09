package com.synlabs.qc.impl.installation;

import com.synlabs.qc.impl.common.Service;
import com.synlabs.qc.impl.program.Parent;
import com.synlabs.qc.impl.program.QcInterfaceView;
import com.synlabs.qc.impl.style.Style;
import com.ur.urcap.api.contribution.installation.swing.SwingInstallationNodeView;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class QcInstallationNodeView implements SwingInstallationNodeView<QcInstallationNodeContribution> {
    private final Style style;
    private JButton windowConnectButton;
    private JButton windowCalibrationButton;
    private JPanel connectionStatusPanel = new JPanel();
    private JLabel status;
    public JTextField pointsText;
    public JPanel panel;
    private QcInterfaceView view;
    public Parent parent;



    public QcInstallationNodeView(Style style, Parent parent){
        this.style = style;
        this.parent = parent;

    }

    @Override
    public void buildUI(JPanel panel, QcInstallationNodeContribution contribution) {
        panel.setLayout(new FlowLayout());

        panel.add(createWindowButtons(contribution));
        panel.add(createVerticalSpacing());
        Box connectionStatusPanel = createWindowPanelStatus(contribution);
        panel.add(connectionStatusPanel);

    }

    private Box createWindowButtons(final QcInstallationNodeContribution contribution) {
        Box box = Box.createHorizontalBox();
        box.setAlignmentX(Component.LEFT_ALIGNMENT);

        windowConnectButton = new JButton("Connect");
        windowConnectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //System.out.println(parent);
                    parent.service.socket();
                    if (parent.service.getClient() != null){
                        status.setText("Connected");
                        windowCalibrationButton.setEnabled(true);
                    }else {
                        JOptionPane.showMessageDialog(createWindowPanelStatus(contribution), "Connection is not available");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(createWindowPanelStatus(contribution), ex);

                }
            }
        });
        box.add(windowConnectButton);

        box.add(createHorizontalSpacing());

        windowCalibrationButton = new JButton("Calibration");
        windowCalibrationButton.setEnabled(false);
        windowCalibrationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                windowConnectButton.setEnabled(false);
                connectionStatusPanel.add(createCalibrationPanel(contribution));
                createCalibrationPanel(contribution).setVisible(true);

            }
        });
        box.add(windowCalibrationButton);

        return box;
    }



    private Box createWindowPanelStatus(final QcInstallationNodeContribution contribution) {
        Box box = Box.createHorizontalBox();
        box.setAlignmentX(Component.LEFT_ALIGNMENT);

        final Border border = BorderFactory.createLineBorder(Color.black, 2);


        connectionStatusPanel.setPreferredSize(new Dimension(550, 370));
        connectionStatusPanel.setMaximumSize(connectionStatusPanel.getPreferredSize());
        connectionStatusPanel.setLayout(new FlowLayout());
        connectionStatusPanel.setBorder(border);

        JLabel connection = new JLabel("Connection Status:");
        connection.setBounds(30, 20, 150, 50);

        status = new JLabel("Not Connected");
        status.setBounds(170, 20, 150, 50);


        connectionStatusPanel.add(connection);
        connectionStatusPanel.add(status);
        box.add(connectionStatusPanel);
        return box;
    }


    private Box createCalibrationPanel(final  QcInstallationNodeContribution contribution) {
        Box box = Box.createHorizontalBox();
        box.setAlignmentX(Component.LEFT_ALIGNMENT);

        final Border border = BorderFactory.createLineBorder(Color.black, 2);
        panel = new JPanel();
        panel.setPreferredSize(new Dimension(550, 650));
        panel.setMaximumSize(panel.getPreferredSize());
        panel.setLayout(null);
        panel.setBorder(border);

        final JLabel label = new JLabel("Set Plane point A");
        label.setBounds(30, 40, 220, 30);

        final JLabel label1 = new JLabel("Set Plane point B");
        label1.setBounds(30, 80, 220, 30);

        final JLabel label2 = new JLabel("Set Plane point C");
        label2.setBounds(30, 120, 220, 30);

        final JLabel label3 = new JLabel("Set Plane point D");
        label3.setBounds(30, 160, 220, 30);

        final JButton setA = new JButton("Set A");
        setA.setBounds(270, 45, 80, 20);
        setA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                contribution.getPose();

            }
        });


        final JButton setB = new JButton("Set B");
        setB.setBounds(270, 85, 80, 20);
        setB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                    contribution.getPose();
            }
        });

        final JButton setC = new JButton("Set C");
        setC.setBounds(270, 125, 80, 20);
        setC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                contribution.getPose();
            }
        });

        final JButton setD = new JButton("Set D");
        setD.setBounds(270, 165, 80, 20);
        setD.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                contribution.getPose();
            }
        });

        final JLabel points = new JLabel("Calibration Points:");
        points.setBounds(40, 205, 150, 30);

        pointsText = new JTextField();
        pointsText.setBounds(200, 205, 250, 30);
        pointsText.setEditable(false);
        pointsText.setText(contribution.s);

        final JButton send = new JButton("Send Calibration");
        send.setBounds(80, 270, 170, 30);
        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(parent.service.getClient() != null){
                    try {
                        PrintWriter  output = new PrintWriter(new OutputStreamWriter(parent.service.getClient().getOutputStream()), true);
                        output.println("CALIB|"+contribution.s);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    JOptionPane.showMessageDialog(panel, "Connection is lost");
                }

            }
        });

        final JButton reset = new JButton("Reset");
        reset.setBounds(270, 270, 90, 30);
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });

        final JButton back = new JButton("Back");
        back.setBounds(380, 270, 70, 30);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                    panel.setVisible(false);
                    connectionStatusPanel.setVisible(true);
                    windowConnectButton.setEnabled(true);
            }
        });


        panel.add(label);
        panel.add(label1);
        panel.add(label2);
        panel.add(label3);
        panel.add(setA);
        panel.add(setB);
        panel.add(setC);
        panel.add(setD);
        panel.add(points);
        panel.add(pointsText);
        panel.add(send);
        panel.add(reset);
        panel.add(back);
        panel.revalidate();
        panel.repaint();

        box.add(panel);
        return box;
    }


    private Component createHorizontalSpacing() {
        return Box.createRigidArea(new Dimension(style.getHorizontalSpacing(), 0));
    }

    private Component createVerticalSpacing() {
        return Box.createRigidArea(new Dimension(style.getVerticalSpacing(), 0));
    }


}
