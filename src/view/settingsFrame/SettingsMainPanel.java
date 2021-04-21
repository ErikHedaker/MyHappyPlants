package view.settingsFrame;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.awt.*;

public class SettingsMainPanel extends JPanel {

    private int width;
    private int height;

    private SettingsCenterPanel pnlCenter;

    BorderLayout layout;
    //SettingsEastPanel pnlEast;

    public SettingsMainPanel(int width, int height){
        this.width = width;
        this.height = height;
        setupPanel();
    }

    private void setupPanel(){
        layout = new BorderLayout();
        setLayout(layout);

        Border border = this.getBorder();
        Border margin = BorderFactory.createEmptyBorder(6,6,6,6);
        setBorder(new CompoundBorder(border, margin));

        pnlCenter = new SettingsCenterPanel(4*width/10, 8*height/10,6);
        add(pnlCenter, layout.CENTER);


        //pnlEast = new SettingsEastPanel(6*width/16,8*height/16,6);
        //add(pnlEast, layout.EAST);
    }

    public SettingsCenterPanel getPnlCenter(){return pnlCenter;}

}
