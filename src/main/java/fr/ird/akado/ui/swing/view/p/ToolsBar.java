/*
 * $Id$
 *
 *Copyright (C) 2014 Observatoire thonier, IRD
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
 */package fr.ird.akado.ui.swing.view.p;

import fr.ird.akado.ui.Constant;
import fr.ird.akado.ui.swing.AkadoController;
import fr.ird.akado.ui.swing.action.GisCreatorAction;
import fr.ird.akado.ui.swing.action.OpenAction;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

/**
 * Create the toolbar of AKaDo application. This class is based on
 * {@link JMenuBar}.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 2.0
 * @date 27 mai 2014
 *
 * $LastChangedDate$
 *
 * $LastChangedRevision$
 */
public class ToolsBar extends JMenuBar implements Constant {

    private JMenu fileMenu;
    private JMenuItem openMenuItem;
    private JMenu optionMenu;
    private JMenuItem gisMenuItem;
    private JMenu helpMenu;
    private JMenuItem quitMenuItem;
    private JMenuItem aboutMenuItem;

    /**
     *
     * @param akadoController
     */
    public ToolsBar(AkadoController akadoController) {
        // Création du menu Fichier

        fileMenu = new JMenu(UIManager.getString("ui.swing.file", getLocale()));
        fileMenu.setActionCommand("file");

        openMenuItem = new JMenuItem(UIManager.getString("ui.swing.open", getLocale()), 'O');
        this.openMenuItem.setAction(new OpenAction(akadoController));
        this.openMenuItem.setActionCommand("open");
        this.openMenuItem.setText(UIManager.getString("ui.swing.open", getLocale()));
        this.openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), true));
        fileMenu.add(openMenuItem);

        fileMenu.add(new JSeparator());
        quitMenuItem = new JMenuItem(UIManager.getString("ui.swing.quit", getLocale()), 'Q');
        quitMenuItem.setAccelerator(KeyStroke.getKeyStroke('Q', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));
        quitMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int resp = JOptionPane.showConfirmDialog(null,
                        String.format(UIManager.getString("ui.swing.quit.message", getLocale()), APPLICATION_NAME), UIManager.getString("ui.swing.quit", getLocale()),
                        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                if (resp == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        fileMenu.add(quitMenuItem);

        //Creation du menu Option
        optionMenu = new JMenu(UIManager.getString("ui.swing.option", getLocale()));
        optionMenu.setActionCommand("option");

        gisMenuItem = new JMenuItem(UIManager.getString("ui.swing.gis", getLocale()), 'O');
        this.gisMenuItem.setAction(new GisCreatorAction(akadoController));
        this.gisMenuItem.setActionCommand("gis");
        this.gisMenuItem.setText(UIManager.getString("ui.swing.gis", getLocale()));
        this.gisMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), true));
        optionMenu.add(gisMenuItem);

        // Création du menu Aide
        helpMenu = new JMenu(UIManager.getString("ui.swing.help", getLocale()));
        aboutMenuItem = new JMenuItem(UIManager.getString("ui.swing.about", getLocale()));
        aboutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));
        aboutMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAboutDialog();
            }
        });
        helpMenu.add(aboutMenuItem);

        this.add(fileMenu);
//        this.add(optionMenu);
        this.add(helpMenu);
    }

    /**
     * Display the about inside a {@link  JDialog}.
     */
    public void showAboutDialog() {
        JDialog about = new JDialog();
        AboutPanel a = new AboutPanel();
        about.setContentPane(a);
        about.setSize(a.getWidth(), a.getHeight());
        about.setResizable(false);
        about.setLocationRelativeTo(getParent());
        about.setVisible(true);
    }
}
