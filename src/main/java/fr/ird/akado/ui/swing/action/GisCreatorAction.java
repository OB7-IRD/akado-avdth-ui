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
 */
package fr.ird.akado.ui.swing.action;

import fr.ird.akado.ui.swing.AkadoController;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 * This class provides an action which creates the GIS database.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 2.0
 * @date 27 mai 2014
 *
 * $LastChangedDate$
 *
 * $LastChangedRevision$
 */
public class GisCreatorAction extends AbstractAction {

    private final Boolean DEBUG = false;
    private AkadoController akadoController;

    public GisCreatorAction(AkadoController vpc) {
        this.akadoController = vpc;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        generateGisDB();
    }

    /**
     * Generate the H2Gis database.
     */
    private void generateGisDB() {

//        int n = JOptionPane.showConfirmDialog(
//                null,
//                "Generate the GIS database?",
//                "Important Question",
//                JOptionPane.YES_NO_OPTION);
//
//        if (n == JOptionPane.YES_OPTION) {
//            System.out.println("Generate the gis DB - yes");
//            GISCreator gisDB = new GISCreator();
//            if (gisDB.exists()) {
//                gisDB.delete();
//            }
//            gisDB.create();
//            GISCreator.createGISDB();
//        }
    }
}
