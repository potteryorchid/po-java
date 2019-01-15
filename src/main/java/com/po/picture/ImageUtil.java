package com.po.picture;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.color.ColorSpace;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.Raster;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

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

  public static void main(String[] args) throws IOException {

//    boolean f = ImageUtil.isColorImageByRaster("/Users/zj/Documents/bak/test/9.jpg", 2, true);
//    System.out.println(f);

    ImageUtil.getCircleImage(ImageIO.read(new File("/Users/zj/Desktop/data.jpeg")));
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
        // 裁剪器
        ImageFilter cropFilter = new CropImageFilter(x, y, width, height);
        // 生成结果图片数据
        Image resImage = Toolkit.getDefaultToolkit().createImage(
            new FilteredImageSource(image.getSource(),
                cropFilter));
        // 创建结果文件流缓冲
        BufferedImage resBufferedImage = new BufferedImage(width, height,
            BufferedImage.TYPE_INT_RGB);
        // 画图器,将结果数据写入缓冲器
        Graphics g = resBufferedImage.getGraphics();
        g.drawImage(resImage, 0, 0, width, height, null);
        g.dispose();

        ImageIO.write(resBufferedImage, getFormatName(srcPath), new File(resPath));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static ByteArrayOutputStream getCircleImage(BufferedImage image) throws IOException {
    ByteArrayOutputStream bos = new ByteArrayOutputStream();

    try {
      BufferedImage res = new BufferedImage(image.getWidth(), image.getHeight(),
          BufferedImage.TYPE_INT_ARGB);

      Graphics2D g2 = res.createGraphics();

      int diameter = Math.max(image.getWidth(), image.getHeight());
      g2.setClip(new Ellipse2D.Double((image.getWidth() - diameter) / 2, 0, diameter,
          diameter));

      // 使用 setRenderingHint 设置抗锯齿
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      g2.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
      g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
          RenderingHints.VALUE_FRACTIONALMETRICS_ON);
      g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
          RenderingHints.VALUE_INTERPOLATION_BICUBIC);

      g2.drawImage(image, 0, 0, null);
      g2.dispose();

      ImageIO.write(res, "png", bos);
      return bos;
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      bos.close();
    }
    return null;
  }

  public static String imageToBase64(ByteArrayOutputStream bos) {
    return new BASE64Encoder().encode(bos.toByteArray());
  }

  public static BufferedImage base64ToImage(String base64) {
    BufferedImage image = null;
    byte[] imageByte;
    try {
      BASE64Decoder decoder = new BASE64Decoder();
      imageByte = decoder.decodeBuffer(base64);
      ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
      image = ImageIO.read(bis);
      bis.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return image;
  }

  /**
   * 文件转存
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

  public static boolean isColorImage(String srcPath, int lv, boolean clip) {
    try {
      BufferedImage srcImage = ImageIO.read(new File(srcPath));
      Raster raster = srcImage.getRaster();

      int no = raster.getNumBands();
      if (no < 3) {
        return false;
      }
      //Set level value.
      lv = 10 * lv;
      int x = 0;
      int y = 0;
      int width = raster.getWidth();
      int height = raster.getHeight();

      if (clip) {
        int clipSize = 300;
        if (width > clipSize && height > clipSize) {
          x = (width - clipSize) / 2;
          y = (height - clipSize) / 2;
          width = clipSize;
          height = clipSize;
        }
      }

      int wh = width * height;
      int threshold = wh / 8;

      int[] pixels = srcImage.getRGB(x, y, width, height, null, 0, width);

      int res_rg = 0;
      int res_gb = 0;
      int res_br = 0;

      for (int i = 0; i < pixels.length; i++) {
        int r = (pixels[i] & 0xff0000) >> 16;
        int g = (pixels[i] & 0xff00) >> 8;
        int b = (pixels[i] & 0xff);
        if (Math.abs(r - g) > lv) {
          res_rg++;
        }
        if (Math.abs(g - b) > lv) {
          res_gb++;
        }
        if (Math.abs(b - r) > lv) {
          res_br++;
        }
      }

      int res = res_rg + res_gb + res_br;
      if (res < threshold) {
        return false;
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return true;
  }

  /**
   *
   * @param srcPath
   * @param lv
   * @param clip
   * @return
   */
  public static boolean isColorImageByRaster(String srcPath, int lv, boolean clip) {
    try {
      BufferedImage srcImage = ImageIO.read(new File(srcPath));
      Raster raster = srcImage.getRaster();

      int no = raster.getNumBands();
      if (no < 3) {
        return false;
      }
      //Set level value.
      lv = 10 * lv;
      int x = 0;
      int y = 0;
      int width = raster.getWidth();
      int height = raster.getHeight();

      if (clip) {
        int clipSize = 300;
        if (width > clipSize && height > clipSize) {
          x = (width - clipSize) / 2;
          y = (height - clipSize) / 2;
          width = clipSize;
          height = clipSize;
        }
      }

      int wh = width * height;
      int threshold = wh / 8;

      int[] arr = new int[wh * no];
      int[] pixels = raster.getPixels(x, y, width, height, arr);

      int res_rg = 0;
      int res_gb = 0;
      int res_br = 0;

      for (int i = 0; i < pixels.length; i += no) {
        int r = pixels[i];
        int g = pixels[i + 1];
        int b = pixels[i + 2];
        if (Math.abs(r - g) > lv) {
          res_rg++;
        }
        if (Math.abs(g - b) > lv) {
          res_gb++;
        }
        if (Math.abs(b - r) > lv) {
          res_br++;
        }
      }

      int res = res_rg + res_gb + res_br;
      if (res < threshold) {
        return false;
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return true;
  }

  public static void checkImage(String srcPath, int lv) {
    try {
      BufferedImage srcImage = ImageIO.read(new File(srcPath));
      Raster raster = srcImage.getRaster();

      int no = raster.getNumBands();

      int x = 0;
      int y = 0;
      int width = raster.getWidth();
      int height = raster.getHeight();

      int wh = width * height;

      int[] pixels = srcImage.getRGB(x, y, width, height, null, 0, width);

      int[] arr = new int[wh * no];
      int[] pixelArr = raster.getPixels(x, y, width, height, arr);

      for (int i = 0; i < pixels.length; i++) {
        int r = (pixels[i] & 0xff0000) >> 16;
        int g = (pixels[i] & 0xff00) >> 8;
        int b = (pixels[i] & 0xff);
        System.out.print("First: " + r + "=" + g + "=" + b + " === Second: ");

        int j = i * no;

        for (int m = 0; m < no; m++) {
          System.out.print(pixelArr[j + m] + "=");
        }
        System.out.println();
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
