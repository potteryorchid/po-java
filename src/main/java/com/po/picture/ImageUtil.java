package com.po.picture;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.IOException;

import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

/**
 * Created by ZJ on 13/06/2018.
 */
public class ImageUtil {

  /**
   * Not image file.
   */
  public static final String NOT_IMAGAE = "NOT_IMAGE";

  /**
   * Zoom in (放大)
   */
  public static final String ZOOM_IN = "ZOOM_IN";

  /**
   * Zoom out (缩小)
   */
  public static final String ZOOM_OUT = "ZOOM_OUT";

  /**
   * JPEG
   */
  public static final String IMAGE_JPEG = "JPEG";

  /**
   * PNG
   */
  public static final String IMAGE_PNG = "png";

  /**
   * BMP
   */
  public static final String IMAGE_BMP = "bmp";

  public static void main(String[] args) {

    ImageUtil.zoomInOrOutByRatio("/Users/zj/Documents/bak/test/7.bmp",
        "/Users/zj/Documents/bak/test/01.jpg", ZOOM_IN, 2, Image.SCALE_DEFAULT);

//    System.out.println(ImageUtil.getFormatName("/Users/zj/Documents/bak/test/7.bmp"));
  }

  /**
   * Return image format name if file is an ImageFile, otherwise return NOT_IMAGE.
   */
  public static String getFormatName(String srcPath) {
    ImageInputStream imageInputStream = null;
    try {
      File file = new File(srcPath);
      imageInputStream = ImageIO.createImageInputStream(file);

      Iterator<ImageReader> iterator = ImageIO
          .getImageReaders(imageInputStream);

      if (iterator.hasNext()) {
        return iterator.next().getFormatName();
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (imageInputStream != null) {
        try {
          imageInputStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return NOT_IMAGAE;
  }

  /**
   * Zoom in or out by ratio.
   */
  public static void zoomInOrOutByRatio(String srcPath, String resPath,
      String zoom, int radio, int scaleMode) {
    try {
      if (scaleMode <= 0) {
        scaleMode = Image.SCALE_DEFAULT;
      }

      File file = new File(srcPath);

      BufferedImage srcImage = ImageIO.read(file);

      int width = srcImage.getWidth();
      int height = srcImage.getHeight();

      if (ZOOM_IN.equals(zoom)) {  // 放大
        width = width * radio;
        height = height * radio;
      } else {  // 缩小
        width = width / radio;
        height = height / radio;
      }

      Image image = srcImage.getScaledInstance(width, height, scaleMode);

      BufferedImage resImage = new BufferedImage(width, height,
          BufferedImage.TYPE_INT_RGB);

      Graphics graphics = resImage.getGraphics();

      graphics.drawImage(image, 0, 0, null); // 绘制缩小后的图
      graphics.dispose();

      ImageIO.write(resImage, getFormatName(srcPath), new File(resPath));// 输出到文件流
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   *
   * @param srcPath
   * @param resPath
   * @param x
   * @param y
   * @param width
   * @param height
   */
  public static void cutImage(String srcPath, String resPath, int x, int y, int width, int height,
      int scaleMode) {
    try {
      if (scaleMode <= 0) {
        scaleMode = Image.SCALE_DEFAULT;
      }

      BufferedImage bufferedImage = ImageIO.read(new File(srcPath));

      int srcWidth = bufferedImage.getHeight();
      int srcHeight = bufferedImage.getWidth();

      if (srcWidth > 0 && srcHeight > 0) {
        Image image = bufferedImage.getScaledInstance(srcWidth, srcHeight,
            scaleMode);

        ImageFilter cropFilter = new CropImageFilter(x, y, width, height);

        Image resImage = Toolkit.getDefaultToolkit().createImage(
            new FilteredImageSource(image.getSource(),
                cropFilter));

        BufferedImage resBufferedImage = new BufferedImage(width, height,
            BufferedImage.TYPE_INT_RGB);

        Graphics g = resBufferedImage.getGraphics();
        g.drawImage(resImage, 0, 0, width, height, null);
        g.dispose();

        ImageIO.write(resBufferedImage, getFormatName(srcPath), new File(resPath));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   *
   * @param srcPath
   * @param resPath
   */
  public static void convert(String srcPath, String resPath) {
    try {
      BufferedImage srcImage = ImageIO.read(new File(srcPath));
      ImageIO.write(srcImage, getFormatName(srcPath), new File(resPath));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   *
   * @param srcPath
   * @param resPath
   */
  public static void getGayscaleImage(String srcPath, String resPath) {
    try {
      BufferedImage srcImage = ImageIO.read(new File(srcPath));

      ColorSpace colorSpace = ColorSpace.getInstance(ColorSpace.CS_GRAY);

      ColorConvertOp colorConvertOp = new ColorConvertOp(colorSpace, null);

      srcImage = colorConvertOp.filter(srcImage, null);

      ImageIO.write(srcImage, getFormatName(srcPath), new File(resPath));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * @param srcPath resource file path
   * @param resPath result file path
   * @param text text content
   * @param fontName text font name
   * @param fontStyle text font style
   * @param fontSize text font size
   * @param color text color
   * @param x x
   * @param y y
   * @param alpha 透明度
   */
  public static void waterMark(String srcPath, String resPath, String text,
      String fontName, int fontStyle, int fontSize, Color color, int x, int y, float alpha) {
    try {
      Image srcImage = ImageIO.read(new File(srcPath));
      int width = srcImage.getWidth(null);
      int height = srcImage.getHeight(null);

      BufferedImage image = new BufferedImage(width, height,
          BufferedImage.TYPE_INT_RGB);

      Graphics2D g = image.createGraphics();

      g.drawImage(srcImage, 0, 0, width, height, null);
      g.setColor(color);
      g.setFont(new Font(fontName, fontStyle, fontSize));
      g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
      g.drawString(text, x, y);
      g.dispose();

      ImageIO.write(image, getFormatName(srcPath), new File(resPath));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
