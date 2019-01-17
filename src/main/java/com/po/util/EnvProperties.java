package com.po.util;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Generate a singleton application env configurations instance, this instance provides essential
 * methods for accessing app configured value and loads local server environmental variables
 * automatically
 */
public class EnvProperties {

  private static Properties properties = new Properties();

  private static HashMap env = new HashMap<String, EnvEntry>();

  static {
    try {
      synchronized (properties) {
        // load application properties file and generate properties instance
        InputStream inputStream = ClassLoader.getSystemResourceAsStream("application.properties");
        properties.load(inputStream);

        // get app variables need to be update according to server env
        Enumeration<String> keys = (Enumeration<String>) properties.propertyNames();
        while (keys.hasMoreElements()) {
          addValue(keys.nextElement().toString());
        }

        // load environmental variables on local server
        loadEnv();

        // replace application variables with local server env variables
        Enumeration<String> keys0 = (Enumeration<String>) properties.propertyNames();
        while (keys0.hasMoreElements()) {
          repValue(keys0.nextElement().toString());
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
    }
  }

  /**
   * Get property value by key.
   *
   * @param k : key in properties
   * @return property value by key
   */
  public static String getProperty(String k) {
    return properties.getProperty(k);
  }

  /**
   * Add app env variables to env map
   */
  private static void addValue(String k) {
    Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}");
    Matcher matcher = pattern.matcher(properties.getProperty(k));

    while (matcher.find()) {
      String mr = matcher.group();
      String[] entry = mr.replace("${", "").replace("}", "").split(":");

      if (entry.length == 1) {
        env.put(entry[0], new EnvEntry(null, null));
      } else if (entry.length == 2) {
        env.put(entry[0], new EnvEntry(null, entry[1]));
      } else {
        StringBuilder v = new StringBuilder();
        for (int i = 1; i < entry.length; i++) {
          v.append(entry[i]);
          if (i != entry.length - 1) {
            v.append(":");
          }
        }
        env.put(entry[0], new EnvEntry(null, v.toString()));
      }
    }
  }

  /**
   * Replace app env variables with local server env variables
   *
   * @param k : key in properties
   */
  private static void repValue(String k) {
    Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}");
    Matcher matcher = pattern.matcher(properties.getProperty(k));

    while (matcher.find()) {
      String mr = matcher.group();
      String v = properties.get(k).toString();
      String ek = mr.replace("${", "").replace("}", "").split(":")[0];
      EnvEntry envEntry = (EnvEntry) env.get(ek);
      properties.setProperty(k, v.replaceAll(pattern.pattern(), envEntry.getV()));
    }
  }

  /**
   * Load local server env variables
   */
  private static void loadEnv() {
    Set<Entry<String, EnvEntry>> entries = env.entrySet();

    for (Entry<String, EnvEntry> entry : entries) {
      EnvEntry ee = entry.getValue();
      ee.setV(System.getenv(entry.getKey()));
      env.put(entry.getKey(), ee);
    }
  }
}
