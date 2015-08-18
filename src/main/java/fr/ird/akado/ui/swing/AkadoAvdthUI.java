/*
 * $Id: AkadoAvdthUI.java 553 2015-03-20 11:04:12Z lebranch $
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
package fr.ird.akado.ui.swing;

import fr.ird.akado.ui.AkadoAvdthProperties;
import fr.ird.akado.ui.Constant;
import fr.ird.avdth.common.AAProperties;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.nio.channels.*;
import java.util.Locale;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 * The main class of Akado Avdth UI.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 2.0
 * @date 27 mai 2014
 *
 * $LastChangedDate: 2015-03-20 12:04:12 +0100 (ven., 20 mars 2015) $
 *
 * $LastChangedRevision: 553 $
 */
public class AkadoAvdthUI {

    private static File f;
    private static FileChannel channel;
    private static FileLock lock;

    public static void main(String[] args) {
        AkadoAvdthProperties.getService().init();

        UIManager.getDefaults().addResourceBundle("AKaDo-UI");
        UIManager.getDefaults().setDefaultLocale(new Locale(AAProperties.L10N));

        
        try {
            f = new File("RingOnRequest.lock");
            // Check if the lock exist
            if (f.exists()) {
                // if exist try to delete it
                f.delete();
            }
            // Try to get the lock
            channel = new RandomAccessFile(f, "rw").getChannel();
            lock = channel.tryLock();
            if (lock == null) {
                // File is lock by other application
                channel.close();
                JOptionPane.showMessageDialog(new JFrame(),
                        UIManager.getString("ui.swing.only.one.instance"),
                        Constant.APPLICATION_NAME,
                        JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
            // Add shutdown hook to release lock when application shutdown
            ShutdownHook shutdownHook = new ShutdownHook();
            Runtime.getRuntime().addShutdownHook(shutdownHook);

            AkadoController akadoController = new AkadoController();

        } catch (IOException e) {
            throw new RuntimeException("Could not start process.", e);
        }

    }

    public static void unlockFile() {
        // release and delete file lock
        try {
            if (lock != null) {
                lock.release();
                channel.close();
                f.delete();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ShutdownHook extends Thread {

        public void run() {
            unlockFile();
        }
    }
}
