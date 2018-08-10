package com.po.sd.concurrent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Created by ZJ on 12/03/2018.
 */
public class WriteOneExcelSheet {

  public static void main(String[] args) {
    multiThreadWrite();
  }

  /**
   * 使用多线程进行Excel写操作，提高写入效率。
   */
  public static void multiThreadWrite() {
    /**
     * 使用线程池进行线程管理。
     */
    ExecutorService es = Executors.newCachedThreadPool();
    /**
     * 使用计数栅栏
     */
    CountDownLatch doneSignal = new CountDownLatch(3);

//    String path = "/Users/zj/Desktop/test/Workbook1.xlsx";
    String outPath = "/Users/zj/Desktop/test/Workbook2.xlsx";

    XSSFWorkbook wb;
    try {
      wb = new XSSFWorkbook();
      XSSFSheet sheet = wb.createSheet("多人检索");
      es.submit(new PoiWriter(doneSignal, sheet, 0, 19999));
      es.submit(new PoiWriter(doneSignal, sheet, 20000, 39999));
      es.submit(new PoiWriter(doneSignal, sheet, 40000, 59999));
      /**
       * 使用CountDownLatch的await方法，等待所有线程完成sheet操作
       */
      doneSignal.await();
      es.shutdown();
      FileOutputStream os = new FileOutputStream(outPath);
      wb.write(os);
      os.flush();
      os.close();
      System.out.println("Excel completed......");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  /**
   * 进行sheet写操作的sheet。
   *
   * @author alex
   */
  protected static class PoiWriter implements Runnable {

    private final CountDownLatch doneSignal;

    private XSSFSheet sheet;

    private int start;

    private int end;

    public PoiWriter(CountDownLatch doneSignal, XSSFSheet sheet, int start,
        int end) {
      this.doneSignal = doneSignal;
      this.sheet = sheet;
      this.start = start;
      this.end = end;
    }

    public void run() {
      int i = start;
      try {
        while (i <= end) {
          XSSFRow row = getRow(sheet, i);
          XSSFCell contentCell = row.getCell(0);
          if (contentCell == null) {
            contentCell = row.createCell(0);
          }
          contentCell.setCellValue(i + 1);
          ++i;
        }
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        doneSignal.countDown();
        System.out.println("start: " + start + " end: " + end
            + " Count: " + doneSignal.getCount());
      }
    }

  }

  /**
   * sheet的row使用treeMap存储的，是非线程安全的，所以在创建row时需要进行同步操作。
   */
  private static synchronized XSSFRow getRow(XSSFSheet sheet, int rownum) {
    return sheet.createRow(rownum);
  }


  /**
   * 测试基本的POI写操作
   */
  public static void poiBasicWriteTest() {
    try {
      String path = "/Users/zj/Desktop/test/Workbook1.xlsx";

      HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(
          path));
      HSSFSheet sheet = wb.getSheetAt(0);
      HSSFRow row = sheet.createRow(0);
      HSSFCell contentCell = row.createCell(0);
      contentCell.setCellValue("abc");
      FileOutputStream os = new FileOutputStream("E:\\temp\\poiTest.xls");
      wb.write(os);
      os.flush();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
