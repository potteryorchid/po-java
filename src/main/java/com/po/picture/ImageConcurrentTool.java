package com.po.picture;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.ReentrantLock;
import javax.imageio.ImageIO;
import sun.misc.BASE64Encoder;

/**
 * Image tool for getting web images by urls, and then shape images to circle image, last
 * transforming images to base64 str list and return list res.
 *
 * Created by ZJ on 11/01/2019.
 */
public class ImageConcurrentTool implements Runnable {

  private ReentrantLock lock;
  private LongAdder adder;
  private ArrayList<String> urls;
  private ConcurrentHashMap base64map;

  ImageConcurrentTool(ArrayList<String> urls, ConcurrentHashMap base64map, LongAdder adder,
      ReentrantLock reentrantLock) {
    this.urls = urls;
    if (null == this.urls || this.urls.size() == 0) {
      throw new NullPointerException(
          "com.po.picture.ImageConcurrentTool.urls can not be null or empty list.");
    }
    this.base64map = base64map;
    this.adder = adder;
    this.lock = reentrantLock;
  }

  @Override
  public void run() {
    long t1 = System.currentTimeMillis();
    BASE64Encoder encoder = new BASE64Encoder();
    while (adder.intValue() < urls.size()) {
      BufferedImage img = null;
      String u = null;
      int index = 0;

      try {
        synchronized (urls) {
          index = adder.intValue();
          if (index > urls.size() - 1) {
            break;
          }
          u = urls.get(index);
          URL url = new URL(u);
          img = ImageIO.read(url);

          if (null != img) {
            this.adder.increment();
          } else {
            System.out.println("Can not get image");
          }

          urls.notify();
        }
        //this.lock.lock();

      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        //this.lock.unlock();
      }

      long t2 = System.currentTimeMillis();

      BufferedImage res = new BufferedImage(img.getWidth(), img.getHeight(),
          BufferedImage.TYPE_INT_ARGB);
      Graphics2D g2 = res.createGraphics();

      int diameter = Math.max(img.getWidth(), img.getHeight());
      g2.setClip(new Ellipse2D.Double((img.getWidth() - diameter) / 2, 0, diameter,
          diameter));
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      g2.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
      g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
          RenderingHints.VALUE_FRACTIONALMETRICS_ON);
      g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
          RenderingHints.VALUE_INTERPOLATION_BICUBIC);
      g2.drawImage(img, 0, 0, null);
      g2.dispose();

      try {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(res, "png", bos);

        if (u != null) {
          this.base64map.put(index, encoder.encode(bos.toByteArray()));
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
      //Runtime.getRuntime().gc();
      System.out.println("time == " + (System.currentTimeMillis() - t2));
    }

    System.out.println(
        Thread.currentThread().getName() + " time --> " + (System.currentTimeMillis() - t1)
            + "  size --> " + base64map.size());
  }
}
