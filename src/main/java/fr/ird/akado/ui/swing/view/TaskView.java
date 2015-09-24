/*
 * $Id: TaskView.java 553 2015-03-20 11:04:12Z lebranch $
 *
 * Copyright (C) 2014 Observatoire thonier, IRD
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
package fr.ird.akado.ui.swing.view;

import fr.ird.akado.core.AkadoCore;
import fr.ird.akado.core.DataBaseInspector;
import fr.ird.akado.core.Inspection;
import fr.ird.akado.core.common.AkadoMessage;
import fr.ird.akado.core.common.AkadoMessages;
import fr.ird.akado.core.common.MessageAdapter;
import fr.ird.akado.ui.AkadoAvdthProperties;
import fr.ird.common.log.LogService;
import fr.ird.driver.avdth.service.AvdthService;
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.text.DefaultCaret;
import org.apache.commons.io.FilenameUtils;
import org.joda.time.DateTime;
import org.joda.time.Seconds;

/**
 * The TaskView class display the running button and the panel of the results.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 2.0
 * @date 3 juin 2014
 * @see Inspection
 * @see Task
 *
 * $LastChangedDate: 2015-03-20 12:04:12 +0100 (ven., 20 mars 2015) $
 *
 * $LastChangedRevision: 553 $
 */
public class TaskView extends JPanel implements ActionListener,
        PropertyChangeListener {

    private Task task;

    private DateTime startProcess;

    /**
     * The Task class represents a thread where the main validation is executed.
     *
     * @see DataBaseInspector
     * @see AkadoMessages
     * @see SwingWorker
     */
    class Task
            extends SwingWorker<Void, Void> {

        DataBaseInspector inspector;

        private AkadoCore akado;
        int progress = 0;
        private String dataBasePath;

        Task(String path) throws Exception {
            Constructor ctor = AkadoAvdthProperties.THIRD_PARTY_DATASOURCE.getDeclaredConstructor(String.class, String.class, String.class, String.class);
            ctor.setAccessible(true);

            this.dataBasePath = path;
            akado = new AkadoCore();
            try {
                inspector = (DataBaseInspector) ctor.newInstance(AkadoAvdthProperties.PROTOCOL_JDBC_ACCESS + path, AkadoAvdthProperties.JDBC_ACCESS_DRIVER, "", "");
            } catch (InstantiationException e) {
                LogService.getService().logApplicationError(e.getCause().toString());
            }

            if (!akado.addDataBaseValidator(inspector)) {
                throw new Exception("Error during the AVDTHValidator creation");
            }

            inspector.getAkadoMessages().addMessageListener(new MessageAdapter() {
                @Override
                public void messageAdded(AkadoMessage m) {
                    taskOutput.append(m.getContent() + "\n");
                }
            });
            inspector.info();
        }

        /*
         * Main task. Executed in background thread.
         */
        @Override
        public Void doInBackground() {
            ref = System.currentTimeMillis();
            timer.start();
            startProcess = new DateTime();
            try {
                akado.execute();
            } catch (Exception ex) {
                LogService.getService().logApplicationError(ex.getMessage());
                LogService.getService().logApplicationError(ex.getCause().toString());
                JOptionPane.showMessageDialog(null,
                        ex.getMessage(),
                        "Akado error",
                        JOptionPane.ERROR_MESSAGE);
            }
            export();
            return null;
        }
        private String exportNameWithExt;
        private String exportOut = "";

        private void export() {
            DateTime endProcess = new DateTime();
            int duration = Seconds.secondsBetween(startProcess, endProcess).getSeconds();
            exportOut = "Done in " + duration / 60 + " minute(s) and " + duration % 60 + " seconds ! There is " + inspector.getAkadoMessages().size() + " messages.\n";

            exportOut += "The export of results is processing...";
            String pathExport = new File(dataBasePath).getParent();
            String dbName = FilenameUtils.removeExtension(new File(dataBasePath).getName());
            String exportName = pathExport + File.separator + dbName + "_akado_result_" + endProcess.getYear() + endProcess.getMonthOfYear() + endProcess.getDayOfMonth() + "_" + endProcess.getHourOfDay() + endProcess.getMinuteOfHour();
            exportNameWithExt = exportName + ".xlsx";
            DateTime startExport = new DateTime();

            inspector.getResults().exportToXLS(exportNameWithExt);

            DateTime endExport = new DateTime();
            exportOut += "The results are in the file: \"" + exportNameWithExt + "\".\n";
            duration = Seconds.secondsBetween(startExport, endExport).getSeconds();
            exportOut += "Export done in " + duration / 60 + " minute(s) and " + duration % 60 + " seconds !\n";
        }

        /*
         * Executed in event dispatch thread
         */
        @Override
        public void done() {
            AvdthService.getService().close();
            taskOutput.append(exportOut);
            timer.stop();
            Toolkit.getDefaultToolkit().beep();
            startButton.setEnabled(true);
            if (exportNameWithExt != null) {
                runExternalProgram(exportNameWithExt);
            }
        }

        /**
         * Launch a registered application to open, edit or print a result file.
         *
         * @param exportNameWithExt the result file
         */
        private void runExternalProgram(String exportNameWithExt) {
            try {
                Desktop.getDesktop().open(new File(exportNameWithExt));
            } catch (IOException ex) {
                LogService.getService().logApplicationError(ex.getMessage());
                JOptionPane.showMessageDialog(null,
                        ex.getMessage(),
                        "Akado error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
//    private final JProgressBar progressBar;
    private final JButton startButton;
    private final JTextArea taskOutput;
    private TaskController vtc;
    public static Boolean DEBUG = false;

    final SimpleDateFormat timef = new SimpleDateFormat("mm:ss");
    final JLabel elapsedLabel = new JLabel("Elapsed Time = " + timef.format(new Date(0)),
            SwingUtilities.CENTER);
    Timer timer = new javax.swing.Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Date currentDate = new Date(System.currentTimeMillis() - ref);
            String formattedDate = timef.format(currentDate);
            elapsedLabel.setText("Elapsed Time = " + formattedDate);
        }
    });
    static long ref;

    public TaskView(TaskController controller) {
        super(new BorderLayout());

        this.vtc = controller;

        //Create the demo's UI.
        startButton = new JButton(UIManager.getString("ui.swing.start", this.getLocale()));
        startButton.setActionCommand(UIManager.getString("ui.swing.start", this.getLocale()));
        startButton.addActionListener(this);

        taskOutput = new JTextArea(5, 20);
        taskOutput.setFont(new Font("Arial", Font.PLAIN, 20));
        taskOutput.setMargin(new Insets(5, 5, 5, 5));
        taskOutput.setEditable(false);

        DefaultCaret caret = (DefaultCaret) taskOutput.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        JPanel panel = new JPanel();
        panel.add(startButton);
        panel.add(elapsedLabel);

        add(panel, BorderLayout.PAGE_START);
        add(new JScrollPane(taskOutput), BorderLayout.CENTER);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    }

    public void displayView() {
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            taskOutput.setText("");
            task = new Task(vtc.getPathFile());
            startButton.setEnabled(false);
            task.addPropertyChangeListener(this);
            task.execute();
        } catch (Exception ex) {
            LogService.getService().logApplicationError(ex.getMessage());
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Akado error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
    }

}
