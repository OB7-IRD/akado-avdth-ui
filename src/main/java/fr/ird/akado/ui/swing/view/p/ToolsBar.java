/*
 * $Id: ToolsBar.java 553 2015-03-20 11:04:12Z lebranch $
 *
 *Copyright (C) 2014 Observatoire thonier, IRD
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
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

import fr.ird.akado.ui.Constant;
import fr.ird.akado.ui.swing.AkadoController;
import fr.ird.akado.ui.swing.action.GISHandlerAction;
import fr.ird.akado.ui.swing.action.OpenAction;
import fr.ird.avdth.common.AAProperties;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
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
 * $LastChangedDate: 2015-03-20 12:04:12 +0100 (ven., 20 mars 2015) $
 *
 * $LastChangedRevision: 553 $
 */
public class ToolsBar extends JMenuBar implements Constant {

    private final JMenu fileMenu;
    private final JMenuItem openMenuItem;
    private final JMenu optionMenu;
    private final JMenuItem gisMenuItem;
    private final JMenu helpMenu;
    private final JMenuItem quitMenuItem;
    private final JMenuItem aboutMenuItem;
    private JCheckBoxMenuItem configMenuItem;

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

        gisMenuItem = new JMenuItem(UIManager.getString("ui.swing.gis", getLocale()), 'G');
        this.gisMenuItem.setAction(new GISHandlerAction(akadoController));
        this.gisMenuItem.setActionCommand("gis");
        this.gisMenuItem.setText(UIManager.getString("ui.swing.gis", getLocale()));
        this.gisMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), true));
        optionMenu.add(gisMenuItem);
        optionMenu.add(new JSeparator()); // SEPARATOR  
        addL10NMenuItem(optionMenu);
        optionMenu.add(new JSeparator()); // SEPARATOR
        addInspectorSelector(optionMenu);

        // Création du menu Aide
        helpMenu = new JMenu(UIManager.getString("ui.swing.help", getLocale()));
        aboutMenuItem = new JMenuItem(UIManager.getString("ui.swing.about", getLocale()));
        aboutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));
        aboutMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAboutDialog();
            }
        });
        helpMenu.add(aboutMenuItem);

        this.add(fileMenu);
        this.add(optionMenu);
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

    private void addL10NMenuItem(JMenu menu) {
        ButtonGroup myGroup = new ButtonGroup();
        JRadioButtonMenuItem myItem = new JRadioButtonMenuItem(UIManager.getString("ui.swing.l10n.fr", getLocale()));
        myItem.setSelected(true);
        myGroup.add(myItem);
        menu.add(myItem);
        myItem = new JRadioButtonMenuItem(UIManager.getString("ui.swing.l10n.en", getLocale()));
        myGroup.add(myItem);
        menu.add(myItem);
        myItem = new JRadioButtonMenuItem(UIManager.getString("ui.swing.l10n.es", getLocale()));
        myGroup.add(myItem);
        menu.add(myItem);
    }

    private void addInspectorSelector(JMenu menu) {
        JMenuItem configMenuItem = new JCheckBoxMenuItem(UIManager.getString("ui.swing.config.activity.inspector.enable", getLocale()));
        configMenuItem.setMnemonic(KeyEvent.VK_A);
        configMenuItem.setSelected(AAProperties.ACTIVITY_INSPECTOR.equals(AAProperties.ACTIVE_VALUE));
        configMenuItem.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                System.out.println(e.getStateChange() == ItemEvent.SELECTED
                        ? "SELECTED" : "DESELECTED");
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    AAProperties.ACTIVITY_INSPECTOR = AAProperties.ACTIVE_VALUE;
                } else {
                    AAProperties.ACTIVITY_INSPECTOR = AAProperties.DISABLE_VALUE;
                }
            }
        });
        menu.add(configMenuItem);

        configMenuItem.setSelected(AAProperties.TRIP_INSPECTOR.equals(AAProperties.ACTIVE_VALUE));
        configMenuItem = new JCheckBoxMenuItem(UIManager.getString("ui.swing.config.trip.inspector.enable", getLocale()));
//        configMenuItem.setMnemonic(KeyEvent.VK_T);
        configMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), true));
        configMenuItem.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                System.out.println(e.getStateChange() == ItemEvent.SELECTED
                        ? "SELECTED" : "DESELECTED");
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    AAProperties.TRIP_INSPECTOR = AAProperties.ACTIVE_VALUE;
                } else {
                    AAProperties.TRIP_INSPECTOR = AAProperties.DISABLE_VALUE;
                }
            }
        });

        menu.add(configMenuItem);

        configMenuItem = new JCheckBoxMenuItem(UIManager.getString("ui.swing.config.sample.inspector.enable", getLocale()));
        configMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), true));
        configMenuItem.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                System.out.println(e.getStateChange() == ItemEvent.SELECTED
                        ? "SELECTED" : "DESELECTED");
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    AAProperties.SAMPLE_INSPECTOR = AAProperties.ACTIVE_VALUE;
                } else {
                    AAProperties.SAMPLE_INSPECTOR = AAProperties.DISABLE_VALUE;
                }
            }
        });

        menu.add(configMenuItem);

        configMenuItem = new JCheckBoxMenuItem(UIManager.getString("ui.swing.config.well.inspector.enable", getLocale()));
        configMenuItem.setSelected(AAProperties.WELL_INSPECTOR.equals(AAProperties.ACTIVE_VALUE));
        configMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), true));
        configMenuItem.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                System.out.println(e.getStateChange() == ItemEvent.SELECTED
                        ? "SELECTED" : "DESELECTED");
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    AAProperties.WELL_INSPECTOR = AAProperties.ACTIVE_VALUE;
                } else {
                    AAProperties.WELL_INSPECTOR = AAProperties.DISABLE_VALUE;
                }
            }
        });

        menu.add(configMenuItem);

        configMenuItem = new JCheckBoxMenuItem(UIManager.getString("ui.swing.config.warning.inspector.enable", getLocale()));
        configMenuItem.setSelected(AAProperties.WARNING_INSPECTOR.equals(AAProperties.ACTIVE_VALUE));
        configMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), true));
        configMenuItem.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                System.out.println(e.getStateChange() == ItemEvent.SELECTED
                        ? "SELECTED" : "DESELECTED");
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    AAProperties.WARNING_INSPECTOR = AAProperties.ACTIVE_VALUE;
                } else {
                    AAProperties.WARNING_INSPECTOR = AAProperties.DISABLE_VALUE;
                }
            }
        });

        menu.add(configMenuItem);
    }
}
