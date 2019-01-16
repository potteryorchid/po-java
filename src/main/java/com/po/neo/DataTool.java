package com.po.neo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Created by ZJ on 28/12/2018.
 * 152500 pro
 */
public class DataTool {

  public static void main(String[] args) {
    int f = 4;

    Random random = new Random();
    if (f == 0) {
      while (true) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 101; i++) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append(UUID.randomUUID().toString());
          stringBuilder.append(",");
          stringBuilder.append(random.nextDouble() * 100);
          list.add(stringBuilder.toString());
        }
        Config.appendData("pro.csv", list);
        System.out.println(Config.getIndex());
      }
    } else if (f == 1) {
      List<String> list = new ArrayList<>();

      for (int i = 0; i < 19000000; i++) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(i);
        stringBuilder.append(",");
        stringBuilder.append(random.nextFloat() * 100);
        stringBuilder.append(",Artist");
        list.add(stringBuilder.toString());

        if (i % 1000 == 0) {
          Config.appendData("pro.csv", list);
          list.clear();
        }
      }
    } else if (f == 2) {
      int no = 8061214;
      for (int i = 1; i < 2000001; i++) {
        List<String> list = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(i);
        stringBuilder.append(",\"");

        for (int j = 0; j < 101; j++) {
          stringBuilder.append(random.nextInt(no));
          if (j != 100) {
            stringBuilder.append(",");
          }
        }
        stringBuilder.append("\",");
        stringBuilder.append(random.nextFloat() * 100);
        list.add(stringBuilder.toString());
        Config.appendData("rel.csv", list);
        System.out.println(i);
      }

    } else if (f == 3) {
      int no = 8061214;
      List<String> list = new ArrayList<>();
      for (int i = 571029; i < no; i++) {
        int t = random.nextInt(100);
        for (int j = 0; j < t; j++) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append(i);
          stringBuilder.append(",");
          stringBuilder.append(random.nextInt(no));
          stringBuilder.append(",");
          stringBuilder.append(random.nextFloat() * 100);
          list.add(stringBuilder.toString());
          Config.appendData("rel.csv", list);
          list.clear();
        }
        System.out.println(i);
      }
    } else if (f == 4) {
      int no = 18999000;
      List<String> list = new ArrayList<>();
      for (int i = 1; i < no; i++) {
        int t = random.nextInt(100);
        for (int j = 0; j < t; j++) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append(i);
          stringBuilder.append(",");
          stringBuilder.append(random.nextFloat() * 100);
          stringBuilder.append(",");
          stringBuilder.append(random.nextInt(no));
          stringBuilder.append(",KNOWS");
          list.add(stringBuilder.toString());
        }
        if (i % 1000 > 500) {
          Config.appendData("rel.csv", list);
          list.clear();
        }
        System.out.println(i);
      }
    }

  }
}
