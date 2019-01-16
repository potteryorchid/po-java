package com.po.neo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.List;
import java.util.Properties;

/**
 * Created by ZJ on 20/12/2018.
 */
public class Config {

  private static Properties properties = new Properties();

  static {
    try {
      InputStream in = ClassLoader.getSystemResourceAsStream("config.properties");
      properties.load(in);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static String getProperty(String k) {
    return properties.getProperty(k);
  }

  public static Integer getIndex() {

    URL url = ClassLoader.getSystemResource("index.properties");

    try {
      BufferedReader r = new BufferedReader(
          new InputStreamReader(new FileInputStream(url.getFile()), "8859_1"));
      Integer index = Integer.parseInt(r.readLine());
      r.close();

      BufferedWriter w = new BufferedWriter(
          new OutputStreamWriter(new FileOutputStream(url.getFile(), false), "8859_1"));
      w.append(String.valueOf(index + 1));
      w.flush();
      w.close();

      return index;
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static Integer getValue() {
    URL url = ClassLoader.getSystemResource("index.properties");
    try {
      BufferedReader r = new BufferedReader(
          new InputStreamReader(new FileInputStream(url.getFile()), "8859_1"));
      Integer index = Integer.parseInt(r.readLine());
      r.close();
      return index;
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static void appendData(String fname, List<String> strs) {
    URL url = ClassLoader.getSystemResource(fname);
    try {
      BufferedWriter w = new BufferedWriter(
          new OutputStreamWriter(new FileOutputStream(url.getFile(), true), "UTF-8"));
      for (String str : strs) {
        w.append(str);
        w.newLine();
      }
      w.flush();
      w.close();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
