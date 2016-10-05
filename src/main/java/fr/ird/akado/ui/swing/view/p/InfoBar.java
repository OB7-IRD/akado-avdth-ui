/*
 * Copyright (C) 2016 Observatoire thonier, IRD
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.ird.akado.ui.swing.view.p;

import fr.ird.akado.avdth.common.AAProperties;
import fr.ird.akado.ui.swing.listener.InfoListener;
import fr.ird.akado.ui.swing.listener.InfoListeners;
import fr.ird.akado.ui.swing.view.TaskController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 */
public class InfoBar extends JPanel implements InfoListener {

//    JPanel avdthInfo = new JPanel();
//    JPanel classInfo = new JPanel();
//    JPanel anapoInfo = new JPanel();
    TaskController vtc;
    
    public InfoBar(TaskController vtc, InfoListeners listeners) {
        this.vtc = vtc;
        System.out.println("InfoBar " +  vtc.getListeners());
        System.out.println("InfoBar " + listeners);
        listeners.addInfoListener(this);
        this.setLayout(new BorderLayout());

        this.add(createAVDTHInfo(), BorderLayout.LINE_START);
        this.add(createClassInfo(), BorderLayout.CENTER);
        this.add(createAnapoInfo(), BorderLayout.LINE_END);
    }

    private JPanel createAVDTHInfo() {

        JPanel avdthInfo = new JPanel();
        JLabel label = new JLabel("AVDTH DB :");
        avdthInfo.add(label);
        String avdthFilePath = "";
        label = new JLabel();
        if (vtc == null) {
            label.setText("No database");
            label.setForeground(Color.RED);
        } else {
            label.setText(vtc.getFilename());
            label.setForeground(Color.BLUE);
        }
        avdthInfo.add(label);

        return avdthInfo;
    }

    private JPanel createClassInfo() {

        JPanel classInfo = new JPanel();
        JLabel label = new JLabel("CL1 :");
        classInfo.add(label);
        label = new JLabel("" + AAProperties.THRESHOLD_CLASS_ONE);
        label.setForeground(Color.BLUE);
        classInfo.add(label);

        label = new JLabel("CL2 :");
        classInfo.add(label);
        label = new JLabel("" + AAProperties.THRESHOLD_CLASS_TWO);
        label.setForeground(Color.BLUE);
        classInfo.add(label);
        return classInfo;

    }

    private JPanel createAnapoInfo() {

        JPanel anapoInfo = new JPanel();
        JLabel label = new JLabel("ANAPO BD :");
        anapoInfo.add(label);
        String anapoFilePath = AAProperties.ANAPO_DB_URL;
        label = new JLabel();
        if (anapoFilePath == null || "".equals(anapoFilePath)) {
            label.setText("No database");
            label.setForeground(Color.RED);
        } else {
            label.setText(anapoFilePath.substring(anapoFilePath.lastIndexOf(File.separator)));
            label.setForeground(Color.BLUE);
        }
        anapoInfo.add(label);
        return anapoInfo;

    }

    @Override
    public void infoUpdated() {
        System.out.println("infoUpdated");
        this.removeAll();
        this.add(createAVDTHInfo(), BorderLayout.LINE_START);
        this.add(createClassInfo(), BorderLayout.CENTER);
        this.add(createAnapoInfo(), BorderLayout.LINE_END);
        this.validate();
    }

}
