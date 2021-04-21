package view.settingsFrame;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.awt.*;

public class SettingsEastPanel extends JPanel {
    private int width;
    private int height;

    private JPanel pnlLeft, pnlRight;

    private JLabel sound, theme;

    public SettingsEastPanel(int width, int height, int margin){
        this.width = width;
        this.height = height;

        setBorder(BorderFactory.createTitledBorder("EAST"));

        Border border = this.getBorder();
        Border emptyBorder = BorderFactory.createEmptyBorder(margin, margin, margin, margin);
        setBorder(new CompoundBorder(border, emptyBorder));

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(width, height));

        createComponents();
    }

    private void createComponents(){
        GridLayout layoutleft = new GridLayout(10,1,2,3);
        pnlLeft = new JPanel(layoutleft);
        Dimension dim = new Dimension(4*width/10, height);
        pnlLeft.setPreferredSize(dim);

        GridLayout layoutRight = new GridLayout(10,1,2,2);
        pnlRight = new JPanel(layoutRight);
        Dimension dimr = new Dimension(6*width/10, height);

        pnlRight.setPreferredSize(dimr);

        sound = new JLabel("SOUND OFF/ON");

        pnlLeft.add(sound);

        add(pnlLeft, BorderLayout.WEST);
        add(pnlRight, BorderLayout.CENTER);
    }

}
