package view.settingsFrame;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.awt.*;

public class SettingsCenterPanel extends JPanel {
    private int width;
    private int height;

    private JPanel pnlLeft;
    private JPanel pnlRight;
    private JComboBox soundOffOn;

    public SettingsCenterPanel(int width, int height, int margin){
        this.width = width;
        this.height = height;

        setBorder(BorderFactory.createTitledBorder("Inställningar"));

        Border border = this.getBorder();
        Border emptyBorder = BorderFactory.createEmptyBorder(margin, margin, margin, margin);
        setBorder(new CompoundBorder(border, emptyBorder));

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(width,height));
        CreateComponents();
    }

    private void CreateComponents() {
        createComponentsOnLeftPanel();
        createComponentsOnRightPanel();
    }

    private void createComponentsOnLeftPanel(){
        JLabel soundOffOn = new JLabel("Sound");

        GridLayout layoutLeft = new GridLayout(10,1,2,2);
        Dimension dim = new Dimension(4*width/10,height);

        pnlLeft = new JPanel(layoutLeft);
        pnlLeft.setPreferredSize(dim);

        pnlLeft.add(soundOffOn);
        add(pnlLeft, BorderLayout.WEST);
    }

    private void createComponentsOnRightPanel(){
        GridLayout layoutright = new GridLayout(10,1,2,2);
        pnlRight = new JPanel();
        Dimension dim = new Dimension(6*width / 10, height);

        soundOffOn =  new JComboBox();

        pnlRight.add(soundOffOn);
        add(pnlRight);
    }

}
