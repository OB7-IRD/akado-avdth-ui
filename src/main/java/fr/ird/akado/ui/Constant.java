/*
 * $Id$
 *
 * Copyright (C) 2014 Observatoire thonier, IRD
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
package fr.ird.akado.ui;

import java.util.Locale;
import org.joda.time.DateTime;

/**
 * Set of constant.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 2.0
 * @date 27 mai 2014
 *
 * $LastChangedDate$
 *
 * $LastChangedRevision$
 */
public interface Constant {

    public final Locale DEFAULT_LOCALE = Locale.UK;

    public final static String APPLICATION_NAME = "AKaDo";
    public final static String APPLICATION_AUTHOR = "IRD - Observatoire thonier";
    public final static String APPLICATION_YEAR = "2014 - " + DateTime.now().getYear();
    public final static String APPLICATION_VERSION = "2.0";

    public static final String SPLASH = "/fr/ird/akado/ui/swing/resources/logo_akado.png";
    public static final String ICON_APPS = "/fr/ird/akado/ui/swing/resources/logo_OT_sans_texte.png";

}
