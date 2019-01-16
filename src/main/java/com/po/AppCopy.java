package com.po;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

/**
 * Hello world!
 */
public class AppCopy {

  public static void main(String[] args) {

    System.out.println(System.getProperty("java.library.path"));
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    System.out.println("\nRunning FaceDetector");

    CascadeClassifier faceDetector = new CascadeClassifier(
        "/Users/zj/Desktop/tm/haarcascade_frontalface_alt.xml");
    Mat image =
        Imgcodecs.imread("/Users/zj/Desktop/shekhar.JPG");

    MatOfRect faceDetections = new MatOfRect();
    faceDetector.detectMultiScale(image, faceDetections);

    System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));

    for (Rect rect : faceDetections.toArray()) {
      Imgproc.rectangle(image, new Point(rect.x, rect.y),
          new Point(rect.x + rect.width, rect.y + rect.height),
          new Scalar(0, 255, 0));
    }

    String filename = "/Users/zj/Desktop/shekhar.png";
    System.out.println(String.format("Writing %s", filename));
    Imgcodecs.imwrite(filename, image);

  }
}
