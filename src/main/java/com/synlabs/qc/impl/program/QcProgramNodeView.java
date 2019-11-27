package com.synlabs.qc.impl.program;

import com.synlabs.qc.impl.style.Style;
import com.ur.urcap.api.contribution.ContributionProvider;
import com.ur.urcap.api.contribution.program.swing.SwingProgramNodeView;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class QcProgramNodeView implements SwingProgramNodeView<QcProgramNodeContribution> {

    private JPanel panel;
    private JPanel panel1;
    private QcInterfaceView view;
    private JButton ocrButton;
    private JButton colorButton;
    private JButton barButton;
    private JButton circleButton;
    private JButton patternButton;
    private JButton connectButton;
    private Style style;


    public QcProgramNodeView(Style style, Parent parent) {
        this.style = style;
        this.view = new QcInterfaceView(parent);
    }


    @Override
    public void buildUI(JPanel panel, ContributionProvider<QcProgramNodeContribution> provider) {
        panel.setLayout(new FlowLayout(0, 0, 10));

        panel.add(createPanel(provider));

        panel.add(createButtonPanel(provider));

    }

    private Box createPanel(final ContributionProvider<QcProgramNodeContribution> provider) {
        Box box = Box.createHorizontalBox();
        box.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel = new JPanel();
        panel.setLayout(new FlowLayout(50));
        panel.setPreferredSize(new Dimension(550, 60));

        Border border = BorderFactory.createLineBorder(Color.black, 3);
        JLabel label = new JLabel("PICK");
        label.setBounds(75, 20, 200, 40);
        label.setBorder(border);
        label.setForeground(Color.black);
        label.setFont(label.getFont().deriveFont(Font.BOLD, 20));


        box.add(label);
        box.add(panel);
        return box;
    }

    private Box createButtonPanel(final ContributionProvider<QcProgramNodeContribution> provider) {
        Box box = Box.createHorizontalBox();
        box.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel1 = new JPanel();
        panel1.setLayout(new FlowLayout(50, 50, 50));
        Border border1 = BorderFactory.createLineBorder(Color.black, 1);
        panel1.setPreferredSize(new Dimension(550, 400));
        panel1.setMaximumSize(panel1.getPreferredSize());

        panel1.add(colorButton(provider));
        panel1.add(barButton(provider));
        panel1.add(ocrButton(provider));
        panel1.add(patternButton(provider));
        panel1.add(circleButton(provider));
        panel1.add(connectButton(provider));

        box.add(panel1);
        return box;
    }

    private Box colorButton(final ContributionProvider<QcProgramNodeContribution> provider) {
        Box box = Box.createHorizontalBox();
        box.setAlignmentX(Component.LEFT_ALIGNMENT);

        colorButton = new JButton("Color");
        colorButton.setPreferredSize(new Dimension(150, 50));
        colorButton.setMaximumSize(colorButton.getPreferredSize());

        colorButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                view.colorFrame();
            }
        });

        box.add(colorButton);
        return box;
    }

    private Box barButton(final ContributionProvider<QcProgramNodeContribution> provider) {
        Box box = Box.createVerticalBox();
        box.setAlignmentX(Component.LEFT_ALIGNMENT);

        barButton = new JButton("Bar Code");
        barButton.setPreferredSize(new Dimension(150, 50));
        barButton.setMaximumSize(barButton.getPreferredSize());
        barButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               view.barFrame();
            }
        });

        box.add(barButton);
        return box;
    }

    private Box patternButton(final ContributionProvider<QcProgramNodeContribution> provider) {
        Box box = Box.createHorizontalBox();
        box.setAlignmentX(Component.LEFT_ALIGNMENT);

        patternButton = new JButton("Pattern Matching");
        patternButton.setPreferredSize(new Dimension(170, 50));
        patternButton.setMaximumSize(patternButton.getPreferredSize());
        patternButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
               view.patternFrame();
            }
        });

        box.add(patternButton);
        return box;
    }

    private Box ocrButton(final ContributionProvider<QcProgramNodeContribution> provider) {
        Box box = Box.createVerticalBox();
        box.setAlignmentX(Component.LEFT_ALIGNMENT);

        ocrButton = new JButton("OCR");
        ocrButton.setPreferredSize(new Dimension(150, 50));
        ocrButton.setMaximumSize(ocrButton.getPreferredSize());
        ocrButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
              view.ocrFrame();
            }
        });

        box.add(ocrButton);
        return box;
    }

    private Box connectButton(final ContributionProvider<QcProgramNodeContribution> provider) {
        Box box = Box.createVerticalBox();
        box.setAlignmentX(Component.LEFT_ALIGNMENT);

        connectButton = new JButton("Connect");
        connectButton.setPreferredSize(new Dimension(150, 50));
        connectButton.setMaximumSize(connectButton.getPreferredSize());
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
//                try {
//                   // service.socket();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }
        });

        box.add(ocrButton);
        return box;
    }

    private Box circleButton(final ContributionProvider<QcProgramNodeContribution> provider) {
        Box box = Box.createHorizontalBox();
        box.setAlignmentX(Component.LEFT_ALIGNMENT);

        circleButton = new JButton("Circle");
        circleButton.setPreferredSize(new Dimension(150, 50));
        circleButton.setMaximumSize(circleButton.getPreferredSize());
        circleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
               view.circleFrame();
            }
        });

        box.add(circleButton);
        return box;
    }

    private Component createHorizontalSpacing() {
        return Box.createRigidArea(new Dimension(style.getHorizontalSpacing(), 0));
    }

    private Component createVerticalSpacing() {
        return Box.createRigidArea(new Dimension(style.getVerticalSpacing(), 0));
    }


}
