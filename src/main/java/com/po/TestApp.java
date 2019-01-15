package com.po;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import sun.misc.BASE64Encoder;

/**
 * Hello world!
 */
public class TestApp {

  public static void main(String[] args) throws IOException, InterruptedException {
    ArrayList stringList = new ArrayList<String>(1001);

    long t = System.currentTimeMillis();

    ByteArrayOutputStream bos = new ByteArrayOutputStream();

    BufferedImage image = ImageIO.read(new File("/Users/zj/Desktop/data.jpeg"));

    ImageIO.write(image, "png", bos);

    BASE64Encoder encoder = new BASE64Encoder();


    for (int i = 0; i < 1001; i++) {
      String str = encoder.encode(bos.toByteArray());

      Runtime.getRuntime().gc();
      Thread.sleep(20);

      stringList.add(str);
      System.out.println(i);
    }
    long u = System.currentTimeMillis() - t;
    System.out.println(u);
  }
}
