package com.po.sd.nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Buffer types:
 *
 * 1. ByteBuffer 2. MappedByteBuffer 3. CharBuffer 4. DoubleBuffer 5. FloatBuffer 6. IntBuffer 7.
 * LongBuffer 8. ShortBuffer
 *
 * Buffer methods:
 *
 * 1. flip(): sets the position back to 0, and sets the limit to where position just was.
 *
 * 2. rewind(): sets the position back to 0, so you can reread all the data in the buffer. the limit
 * remains untouched.
 *
 * 3. compact(): copies all unread data to the beginning of the buffer, then sets position to right
 * after the last unread element.
 *
 * 4. clear(): the position is set back to 0 and the limit to capacity. in other words, the buffer
 * is cleared.
 *
 * 5. mark(): mark a given position in buffer. used in pairs with method reset().
 *
 * 6. reset(): reset the position back to the marked position. used in pairs with method mark().
 *
 * 7. equals(): same types, same amount of remaining elements, and all remaining elements(position
 * to limit) are equal. But not compare element that already read (start to position). this method
 * effects on the remaining elements.
 *
 * 8. compareTo(): compared remaining elements of the two buffers, a buffer is considered "smaller"
 * than another buffer if meets one of the next two condition: 1. compare element in buffer with the
 * corresponding element in the other buffer one by one, until find one element is smaller than the
 * one in the other buffer. 2. all elements are equal, but the first buffer runs out of elements
 * before the second buffer, in other word, the first buffer has fewer elements.
 *
 *
 * Created by po on 30/04/2019.
 */
public class NioBuffer {

  public void basicMethod() {
    try {
      RandomAccessFile file = new RandomAccessFile("/Users/po/Documents/repo/tmp/tmp.txt", "rw");
      FileChannel channel = file.getChannel();

      /*
      allocateDirect(int): 分配堆外内存，直接在JVM外分配内存，这种分配方式低效，但省去内存copy，当文件大小比较大时效率高。
      allocate(int): 普通内存分配，在JVM内分配内存。
       */
      ByteBuffer buffer = ByteBuffer.allocate(40);
      // read data into buffer (write data into buffer)
      int bytes = channel.read(buffer);
      // read data into the buffer and then print the data out from buffer.
      while (bytes != -1) {
        System.out.println("Out = " + bytes);
        // flip(): The limit is set to the current position and then the position is set to zero.
        buffer.flip();
        // print date out
        while (buffer.hasRemaining()) {
          System.out.println((char) buffer.get());
        }

        buffer.clear();
        // read data from file again.
        bytes = channel.read(buffer);
      }

      file.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void scatterAndGather() {
    try {
      RandomAccessFile file = new RandomAccessFile("/Users/po/Documents/repo/tmp/tmp.txt", "rw");
      FileChannel channel = file.getChannel();

      /*
      allocateDirect(int): 分配堆外内存，直接在JVM外分配内存，这种分配方式低效，但省去内存copy，当文件大小比较大时效率高。
      allocate(int): 普通内存分配，在JVM内分配内存。
       */
      ByteBuffer tile = ByteBuffer.allocate(20);
      ByteBuffer text = ByteBuffer.allocate(40);

      ByteBuffer[] arr = {tile, text};

      // write data into buffers
      channel.read(arr);

      // read data and print the data out from buffer.
      tile.flip();
      text.flip();

      while (tile.hasRemaining()) {
        System.out.print((char) tile.get());
      }
      while (text.hasRemaining()) {
        System.out.print((char) text.get());
      }

      // reset position to zero.
      tile.rewind();
      text.rewind();

      RandomAccessFile res = new RandomAccessFile("/Users/po/Documents/repo/tmp/res.txt", "rw");
      FileChannel resChannel = res.getChannel();

      resChannel.position(47);

      resChannel.write(arr);

      resChannel.force(true);

      resChannel.close();
      channel.close();

      res.close();
      file.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) throws InterruptedException {
    NioBuffer nioBuffer = new NioBuffer();
    while (true){
      nioBuffer.basicMethod();
      Thread.sleep(10000);
    }

  }
}