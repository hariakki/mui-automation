package helpers;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by makri on 16/05/2017.
 */


public class ConfigHelper {

    private static ConfigHelper instance;
    private Config conf;
    private static ClassLoader classLoader;

    protected ConfigHelper() {
        classLoader = getClass().getClassLoader();
        conf = ConfigFactory.load(classLoader, System
                .getProperty("project.properties", "project.properties"));
    }

    public static synchronized ConfigHelper getInstance() {
        if (instance == null) {
            instance = new ConfigHelper();
        }
        return instance;
    }

    public static String getString(String key) {
        return ConfigHelper.getInstance().conf.getString(key).replace("\"", "").replace("'", "");
    }

    public static int getInt(String key) {
        return ConfigHelper.getInstance().conf.getInt(key);
    }

    public static Integer getAttemptCount() {
        return getInt("attempts.times");
    }

    public static String getTestResourcesFolderPath() {

        File file = new File(classLoader.getResource("").getFile());
        return file.getAbsolutePath();
    }

    public static String getCurrentWorkingDir() {

        Path currentRelativePath = Paths.get("");
        return currentRelativePath.toAbsolutePath().toString();

    }

    public static String getUserDir() {
        return System.getProperty("user.dir");
    }

    public static String getTemplateDir() {
        return ConfigHelper.getTestResourcesFolderPath() + File.separator + "templates" + File.separator;

    }

    public static String getProjectBaseDir() {
        return getString("project.basedir");
    }

    public static String getDriverType() {
        return getString("driver.type");
    }

    public static String getTestEnv() {
        return getString("execution.env");
    }

    public static String getSeleniumGridURL() {
        return getString("selenium.grid.url");
    }

    public static Integer getPageLoadWaitTime() {
        return getInt("pageload.wait.time");
    }

    public static String getPageLoadWaitTimeString() {
        return getInt("pageload.wait.time") + "s";
    }

    public static Integer getElementWaitTime() {
        return getInt("element.wait.time");
    }

    public static String getElementWaitTimeString() {
        return getInt("element.wait.time") + "s";
    }

    public static Integer getImplicitWaitTime() {
        return getInt("normal.wait.interval");
    }

    public static String getImplicitWaitTimeString() {
        return getInt("normal.wait.interval") + "million seconds";
    }


    public static String getDownloadFolder() {
        if (null == getString("download.folder") || getString("download.folder").isEmpty()) {
            return getCurrentWorkingDir() + File.separator + "target";
        } else {
            return getString("download.folder");
        }
    }


    public static String getAppiumVersion() {
        return getString("appium.version");
    }

    public static String getPlatformVersion() {
        return getString("platform.version");
    }

    public static String getDeviceName() {
        return getString("device.name");
    }

    public static String getAppiumServerURL() {
        return getString("appium.server.url");
    }

    public static String getAppPath() {
        if (null == getString("app.absolute.path") || getString("app.absolute.path").isEmpty()) {
            // go to relative path
            if ((null == getString("app.relative.path") || getString("app.relative.path").isEmpty())) {
                throw new IllegalArgumentException("please input app path");
            } else {
                File appDir = new File(System.getProperty("user.dir"),
                        getString("app.relative.path"));
                File app = new File(appDir.getPath());
                return app.getAbsolutePath();
            }
        } else {
            return getString("app.absolute.path");
        }
    }

    public static String getAutomationName() {
        if(null== getString("automation.name")||getString("automation.name").isEmpty())
        {
            return "";
        }
        return getString("automation.name");
    }
}