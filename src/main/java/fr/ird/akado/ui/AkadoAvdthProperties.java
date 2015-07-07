/*
 * $Id: AkadoAvdthProperties.java 553 2015-03-20 11:04:12Z lebranch $
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
package fr.ird.akado.ui;

import fr.ird.akado.core.DataBaseInspector;
import fr.ird.avdth.common.AAProperties;
import static fr.ird.avdth.common.AAProperties.ACTIVE_VALUE;
import static fr.ird.avdth.common.AAProperties.DATE_FORMAT_XLS;
import static fr.ird.avdth.common.AAProperties.KEY_ACTIVITY_INSPECTOR;
import static fr.ird.avdth.common.AAProperties.KEY_DATE_FORMAT_XLS;
import static fr.ird.avdth.common.AAProperties.KEY_LOGS_DIRECTORY;
import static fr.ird.avdth.common.AAProperties.KEY_POSITION_INSPECTOR;
import static fr.ird.avdth.common.AAProperties.KEY_RESULT_MODEL_AVDTH_XLS;
import static fr.ird.avdth.common.AAProperties.KEY_SAMPLE_INSPECTOR;
import static fr.ird.avdth.common.AAProperties.KEY_SHP_COUNTRIES_PATH;
import static fr.ird.avdth.common.AAProperties.KEY_SHP_OCEAN_PATH;
import static fr.ird.avdth.common.AAProperties.KEY_STANDARD_DIRECTORY;
import static fr.ird.avdth.common.AAProperties.KEY_TRIP_INSPECTOR;
import static fr.ird.avdth.common.AAProperties.KEY_WARNING_INSPECTOR;
import static fr.ird.avdth.common.AAProperties.KEY_WELL_INSPECTOR;
import static fr.ird.avdth.common.AAProperties.LOGS_DIRECTORY;
import static fr.ird.avdth.common.AAProperties.RESULT_MODEL_AVDTH_XLS;
import static fr.ird.avdth.common.AAProperties.SHP_COUNTRIES_PATH;
import static fr.ird.avdth.common.AAProperties.SHP_OCEAN_PATH;
import static fr.ird.avdth.common.AAProperties.STANDARD_DIRECTORY;
import fr.ird.common.configuration.AppConfig;
import fr.ird.common.configuration.IRDProperties;
import fr.ird.common.log.LogService;
import java.io.File;
import java.net.URISyntaxException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The AkadoAvdthProperties class represents a persistent set of properties.
 * This properties are stored in the file "akado-config.xml".
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 2.0
 * @date 24 juin 2014
 *
 * $LastChangedDate: 2015-03-20 12:04:12 +0100 (ven., 20 mars 2015) $
 *
 * $LastChangedRevision: 553 $
 */
public final class AkadoAvdthProperties extends IRDProperties {

    public final static String PROTOCOL_JDBC_ACCESS = "jdbc:Access:///";

    private static final AkadoAvdthProperties service = new AkadoAvdthProperties();
//    public static String DATE_FORMAT_XLS;

    public static AkadoAvdthProperties getService() {
        return service;
    }
    public static final String KEY_THIRD_PARTY_DATASOURCE = "third_party_datasource";
    public static String THIRD_PARTY_DATASOURCE_NAME = "fr.ird.akado.avdth.AvdthInspector";
    public static Class<DataBaseInspector> THIRD_PARTY_DATASOURCE = null;

    public static final String KEY_JDBC_ACCESS_DRIVER = "jdbc_access_driver";
    public static String JDBC_ACCESS_DRIVER = "com.hxtt.sql.access.AccessDriver";
    public static final String STANDARD_RELATIVE_CONFIG_PATH = "db";

//
//    public static String RESULT_MODEL_AVDTH_XLS;
    private AkadoAvdthProperties() {
        PROJECT_NAME = "akado-avdth";
        PROJECT_CONFIG_FILENAME = "akado-config.xml";
        PROJECT_CONFIG_COMMENT = "AKaDo Avdth configuration's properties";

        PROJECT_CONFIG_ABSOLUTE_PATH = AppConfig.getConfigDirectory(AppConfig.getRelativeConfigPath(PROJECT_NAME));

//        init();
    }

    /**
     * Initialize the loading of properties.
     */
    public void init() {
        try {
            if (!configFileExist()) {
                System.out.println("!configFileExist(): so create it!");
                createConfigFile();
            }
//            DataBaseInspector dbi;
            Properties p = loadProperties();

            System.out.println("Properties :");
            for (String k : p.stringPropertyNames()) {
                System.out.println("Property : (" + k + "," + p.getProperty(k) + ")");
            }
            try {
                THIRD_PARTY_DATASOURCE = (Class<DataBaseInspector>) Class.forName(p.getProperty(KEY_THIRD_PARTY_DATASOURCE));
                // Retrieving the matching records from the data source
//                dbi = THIRD_PARTY_DATASOURCE.newInstance();
                DataBaseInspector.CONFIGURATION_PROPERTIES = p;

                AAProperties.STANDARD_DIRECTORY = p.getProperty(AAProperties.KEY_STANDARD_DIRECTORY);
                AAProperties.SHP_COUNTRIES_PATH = p.getProperty(AAProperties.KEY_SHP_COUNTRIES_PATH);
                AAProperties.SHP_OCEAN_PATH = p.getProperty(AAProperties.KEY_SHP_OCEAN_PATH);
                AAProperties.DATE_FORMAT_XLS = p.getProperty(AAProperties.KEY_DATE_FORMAT_XLS);
                AAProperties.RESULT_MODEL_AVDTH_XLS = p.getProperty(AAProperties.KEY_RESULT_MODEL_AVDTH_XLS);
                AAProperties.LOGS_DIRECTORY = p.getProperty(AAProperties.KEY_LOGS_DIRECTORY);

                AAProperties.SAMPLE_INSPECTOR = p.getProperty(AAProperties.KEY_SAMPLE_INSPECTOR);
                AAProperties.WELL_INSPECTOR = p.getProperty(AAProperties.KEY_WELL_INSPECTOR);
                AAProperties.TRIP_INSPECTOR = p.getProperty(AAProperties.KEY_TRIP_INSPECTOR);
                AAProperties.POSITION_INSPECTOR = p.getProperty(AAProperties.KEY_POSITION_INSPECTOR);
                AAProperties.ACTIVITY_INSPECTOR = p.getProperty(AAProperties.KEY_ACTIVITY_INSPECTOR);

                AAProperties.WARNING_INSPECTOR = p.getProperty(AAProperties.KEY_WARNING_INSPECTOR);
                AAProperties.ANAPO_DB_URL = p.getProperty(AAProperties.KEY_ANAPO_DB_PATH);
                AAProperties.L10N = p.getProperty(AAProperties.KEY_L10N);

            } catch (ClassNotFoundException e) {
                LogService.getService().logApplicationError(e.toString());
                LogService.getService().logApplicationError(e.getMessage() + "\nThe value of " + KEY_THIRD_PARTY_DATASOURCE + " key is " + THIRD_PARTY_DATASOURCE);

            }

//            AkadoAvdthProperties.JDBC_URL = p.getProperty(KEY_JDBC_URL);
            AkadoAvdthProperties.JDBC_ACCESS_DRIVER = p.getProperty(KEY_JDBC_ACCESS_DRIVER);
//            AkadoAvdthProperties.JDBC_USER = p.getProperty(KEY_JDBC_USER);
//            AkadoAvdthProperties.JDBC_PWD = p.getProperty(KEY_JDBC_PWD);

        } catch (Exception e) {

            LogService.getService().logApplicationError(e.getMessage());
            LogService.getService().logApplicationError(e.toString());
        }
    }

    @Override
    public Properties createDefaultProperties() {
        System.out.println("createDefaultProperties");
        System.out.println("Initialisation de properties ");

        Properties p = new Properties();
        p.setProperty(KEY_SAMPLE_INSPECTOR, ACTIVE_VALUE);
        p.setProperty(KEY_TRIP_INSPECTOR, ACTIVE_VALUE);
        p.setProperty(KEY_ACTIVITY_INSPECTOR, ACTIVE_VALUE);
        p.setProperty(KEY_POSITION_INSPECTOR, ACTIVE_VALUE);
        p.setProperty(KEY_WELL_INSPECTOR, ACTIVE_VALUE);
        p.setProperty(KEY_WARNING_INSPECTOR, ACTIVE_VALUE);

        p.setProperty(KEY_DATE_FORMAT_XLS, DATE_FORMAT_XLS);

        p.setProperty(KEY_THIRD_PARTY_DATASOURCE, THIRD_PARTY_DATASOURCE_NAME);
        p.setProperty(KEY_JDBC_ACCESS_DRIVER, JDBC_ACCESS_DRIVER);

        p.setProperty(AAProperties.KEY_ANAPO_DB_PATH, "");
        p.setProperty(AAProperties.KEY_L10N, "fr");

        System.out.println("Creation de la property: " + PROJECT_CONFIG_ABSOLUTE_PATH + File.separator + STANDARD_RELATIVE_CONFIG_PATH);
        p.setProperty(KEY_STANDARD_DIRECTORY, PROJECT_CONFIG_ABSOLUTE_PATH + File.separator + STANDARD_RELATIVE_CONFIG_PATH);
        p.setProperty(KEY_LOGS_DIRECTORY, PROJECT_CONFIG_ABSOLUTE_PATH + File.separator + LOGS_DIRECTORY);
        p.setProperty(KEY_SHP_OCEAN_PATH, new File(getInstallPath() + File.separator + "resource" + File.separator + "shp" + File.separator + "IHOSeasAndOceans.shp").getPath());
        p.setProperty(KEY_SHP_COUNTRIES_PATH, new File(getInstallPath() + File.separator + "resource" + File.separator + "shp" + File.separator + "countries.shp").getPath());
        p.setProperty(KEY_RESULT_MODEL_AVDTH_XLS, new File(getInstallPath() + File.separator + "resource" + File.separator + "akado_avdth_result_model.xlsx").getPath());
        return p;
    }

    @Override
    public void createDefaultDirectory() {
        super.createDefaultDirectory();

        String dbPath = PROJECT_CONFIG_ABSOLUTE_PATH + File.separator + STANDARD_RELATIVE_CONFIG_PATH;
        //System.out.println("dbPath: " + dbPath);
        boolean success = (new File(dbPath)).mkdirs();
        if (success) {
            System.out.println("Directory: " + dbPath + " created");
        }
    }

    @Override
    public void copyDefaultFile() {
    }

    public static Properties getDefaultProperties() {
        Properties p = new Properties();
        p.setProperty(KEY_SAMPLE_INSPECTOR, ACTIVE_VALUE);
        p.setProperty(KEY_TRIP_INSPECTOR, ACTIVE_VALUE);
        p.setProperty(KEY_ACTIVITY_INSPECTOR, ACTIVE_VALUE);
        p.setProperty(KEY_POSITION_INSPECTOR, ACTIVE_VALUE);
        p.setProperty(KEY_WELL_INSPECTOR, ACTIVE_VALUE);
        p.setProperty(KEY_WARNING_INSPECTOR, ACTIVE_VALUE);

        p.setProperty(KEY_STANDARD_DIRECTORY, STANDARD_DIRECTORY);
        p.setProperty(KEY_LOGS_DIRECTORY, LOGS_DIRECTORY);
        p.setProperty(KEY_RESULT_MODEL_AVDTH_XLS, RESULT_MODEL_AVDTH_XLS);
        p.setProperty(KEY_SHP_COUNTRIES_PATH, SHP_COUNTRIES_PATH);
        p.setProperty(KEY_SHP_OCEAN_PATH, SHP_OCEAN_PATH);
        p.setProperty(KEY_DATE_FORMAT_XLS, DATE_FORMAT_XLS);

        System.out.println("**************************");
        System.out.println(p);
        System.out.println("**************************");
        return p;
    }

    public void saveProperties() {
        Properties p = new Properties();
        p.setProperty(KEY_SAMPLE_INSPECTOR, AAProperties.SAMPLE_INSPECTOR);
        p.setProperty(KEY_TRIP_INSPECTOR, AAProperties.TRIP_INSPECTOR);
        p.setProperty(KEY_ACTIVITY_INSPECTOR, AAProperties.ACTIVITY_INSPECTOR);
        p.setProperty(KEY_POSITION_INSPECTOR, AAProperties.POSITION_INSPECTOR);
        p.setProperty(KEY_WELL_INSPECTOR, AAProperties.WELL_INSPECTOR);
        p.setProperty(KEY_WARNING_INSPECTOR, AAProperties.WARNING_INSPECTOR);

        p.setProperty(KEY_STANDARD_DIRECTORY, STANDARD_DIRECTORY);
//        p.setProperty(KEY_LOGS_DIRECTORY, LOGS_DIRECTORY);
        p.setProperty(KEY_RESULT_MODEL_AVDTH_XLS, RESULT_MODEL_AVDTH_XLS);
        p.setProperty(KEY_SHP_COUNTRIES_PATH, SHP_COUNTRIES_PATH);
        p.setProperty(KEY_SHP_OCEAN_PATH, SHP_OCEAN_PATH);
        p.setProperty(KEY_DATE_FORMAT_XLS, DATE_FORMAT_XLS);

        p.setProperty(AAProperties.KEY_ANAPO_DB_PATH, AAProperties.ANAPO_DB_URL);
        p.setProperty(AAProperties.KEY_L10N, AAProperties.L10N);

        p.setProperty(KEY_JDBC_ACCESS_DRIVER, JDBC_ACCESS_DRIVER);
        p.setProperty(KEY_THIRD_PARTY_DATASOURCE, THIRD_PARTY_DATASOURCE_NAME);

        System.out.println("**************************");
        System.out.println(p);
        System.out.println("**************************");
        saveProperties(p);
    }

    public String getInstallPath() {
        try {
            File file = new File(AkadoAvdthProperties.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
            return file.getParentFile().getParentFile().getAbsolutePath();
        } catch (URISyntaxException ex) {
            Logger.getLogger(AkadoAvdthProperties.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

}
