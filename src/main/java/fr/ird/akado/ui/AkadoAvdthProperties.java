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

import fr.ird.akado.core.DataBaseInspector;
import fr.ird.common.configuration.AppConfig;
import fr.ird.common.configuration.IRDProperties;
import fr.ird.common.log.LogService;
import java.io.File;
import java.util.Properties;

/**
 * The AkadoAvdthProperties class represents a persistent set of properties.
 * This properties are stored in the file "akado-config.xml".
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 2.0
 * @date 24 juin 2014
 *
 * $LastChangedDate$
 *
 * $LastChangedRevision$
 */
public final class AkadoAvdthProperties extends IRDProperties {

    public final static String PROTOCOL_JDBC_ACCESS = "jdbc:Access:///";

    private static final AkadoAvdthProperties service = new AkadoAvdthProperties();
//    public static String DATE_FORMAT_XLS;

    public static AkadoAvdthProperties getService() {
        return service;
    }
    public static final String KEY_THIRD_PARTY_DATASOURCE = "third_party_datasource";
    public static Class<DataBaseInspector> THIRD_PARTY_DATASOURCE = null;

//    public static final String KEY_JDBC_URL = "jdbc_url";
    public static final String KEY_JDBC_ACCESS_DRIVER = "jdbc_access_driver";
//    public static final String KEY_JDBC_USER = "jdbc_user";
//    public static final String KEY_JDBC_PWD = "jdbc_password";
    public static String JDBC_ACCESS_DRIVER = "com.hxtt.sql.access.AccessDriver";
//    public static String JDBC_USER;
//    public static String JDBC_URL;
//    public static String JDBC_PWD;

//    public static final String SHAPEFILE_RELATIVE_PATH = "shp";
//    public static final String STANDARD_RELATIVE_CONFIG_PATH = "db";
//    public static final String KEY_LOGS_DIRECTORY = "log_directory_path";
//
//    public static String STANDARD_DIRECTORY;
//    public static String LOGS_DIRECTORY;
//
//    public static String SHP_COUNTRIES_PATH;
//    public static String SHP_OCEAN_PATH;
    public static String PROJECT_CONFIG_ABSOLUTE_PATH;
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
            DataBaseInspector dbi;
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
//        try {
//            //System.out.println("createDefaultProperties");
//
//            DataBaseInspector dbi = THIRD_PARTY_DATASOURCE.newInstance();
////            Properties p = new Properties(dbi.getDefaultPropertiesToSet());
//
////            p.setProperty(KEY_JDBC_URL, "");
//            p.setProperty(KEY_JDBC_ACCESS_DRIVER, JDBC_ACCESS_DRIVER);
////            p.setProperty(KEY_JDBC_USER, "");
////            p.setProperty(KEY_JDBC_PWD, "");
//            return p;
//        //System.out.println("Initialisation de properties ");
//        //System.out.println("Creation de la property: " + PROJECT_CONFIG_ABSOLUTE_PATH + File.separator + STANDARD_RELATIVE_CONFIG_PATH);
//            properties.setProperty(KEY_STANDARD_DIRECTORY, PROJECT_CONFIG_ABSOLUTE_PATH + File.separator + STANDARD_RELATIVE_CONFIG_PATH);
//            properties.setProperty(KEY_LOGS_DIRECTORY, PROJECT_CONFIG_ABSOLUTE_PATH + File.separator + LOGS_RELATIVE_CONFIG_PATH);
//        System.out.println("Creation de la property: " + new File(PROJECT_CONFIG_ABSOLUTE_PATH + File.separator +SHAPEFILE_RELATIVE_PATH + File.separator + "IHOSeasAndOceans.shp").getPath());
//            properties.setProperty(KEY_SHP_OCEAN_PATH, new File(PROJECT_CONFIG_ABSOLUTE_PATH + File.separator + SHAPEFILE_RELATIVE_PATH + File.separator + "IHOSeasAndOceans.shp").getPath());
//        System.out.println("Creation de la property: " + new File(PROJECT_CONFIG_ABSOLUTE_PATH + File.separator +SHAPEFILE_RELATIVE_PATH + File.separator + "countries.shp").getPath());
//            properties.setProperty(KEY_SHP_COUNTRIES_PATH, new File(PROJECT_CONFIG_ABSOLUTE_PATH + File.separator + SHAPEFILE_RELATIVE_PATH + File.separator + "countries.shp").getPath());
//            properties.setProperty(KEY_DATE_FORMAT_XLS, "dd/mm/yyyy hh:mm");
//            properties.setProperty(KEY_RESULT_MODEL_AVDTH_XLS, new File(PROJECT_CONFIG_ABSOLUTE_PATH + File.separator + "akado_avdth_result_model.xlsx").getPath());
//        for (Object key : properties.keySet()) {
//            //System.out.println("**** " + properties.get(key));
//        }
//
//        //System.out.println("Properties-> " + properties.toString());
//        } catch (InstantiationException | IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(AkadoAvdthProperties.class.getName()).log(Level.SEVERE, null, ex);
//        }
        return null;
    }

    @Override
    public void createDefaultDirectory() {
//        System.out.println("createDefaultDirectory: ");
        String appConfigPath = PROJECT_CONFIG_ABSOLUTE_PATH;
//        System.out.println("AppConfigPath: " + PROJECT_CONFIG_ABSOLUTE_PATH);
        boolean success = (new File(appConfigPath)).mkdirs();
        if (success) {
            System.out.println("Directory: " + appConfigPath + " created");
        }
//        String dbPath = PROJECT_CONFIG_ABSOLUTE_PATH + File.separator + STANDARD_RELATIVE_CONFIG_PATH;
//        //System.out.println("dbPath: " + dbPath);
//        success = (new File(dbPath)).mkdirs();
//        if (success) {
//            System.out.println("Directory: " + dbPath + " created");
//        }
//        String shpPath = PROJECT_CONFIG_ABSOLUTE_PATH + File.separator + SHAPEFILE_RELATIVE_PATH;
//        //System.out.println("shpPath: " + shpPath);
//        success = (new File(shpPath)).mkdir();
//        if (success) {
//            System.out.println("Directory: " + shpPath + " created");
//        }
    }

    @Override
    public void copyDefaultFile() {
        //System.out.println("copyDefaultFile");
//        try {
//            String shpPath = PROJECT_CONFIG_ABSOLUTE_PATH + File.separator + "shp";
//            for (String s : Utils.getResourceListing("fr/ird/akado/avdth/shp")) {
//
//            }
//        } catch (URISyntaxException ex) {
//            java.util.logging.Logger.getLogger(AkadoAvdthProperties.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            java.util.logging.Logger.getLogger(AkadoAvdthProperties.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
}
