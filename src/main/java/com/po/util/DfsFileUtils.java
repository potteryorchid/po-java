package com.po.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FsStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

public class DfsFileUtils {

  private FileSystem fileSystem;

  public DfsFileUtils() {
    Configuration configuration = new Configuration();
    configuration.set("fs.defaultFS", EnvProperties.getProperty("fs.defaultFS"));
    try {
      fileSystem = FileSystem.get(configuration);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public DfsFileUtils(Configuration configuration) {
    try {
      fileSystem = FileSystem.get(configuration);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public FileStatus[] list(String path) {
    FileStatus[] statusList = new FileStatus[0];
    try {
      statusList = this.fileSystem.listStatus(new Path(path));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return statusList;
  }

  public boolean rename(String oldPath, String newPath) {
    boolean flag = false;
    try {
      flag = this.fileSystem.rename(new Path(oldPath), new Path(newPath));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return flag;
  }

  public boolean exist(String path) {
    boolean flag = false;
    try {
      flag = this.fileSystem.exists(new Path(path));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return flag;
  }

  public FsStatus getFsStatus(String path) {
    FsStatus fsStatus = null;
    try {
      fsStatus = this.fileSystem.getStatus(new Path(path));
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      return fsStatus;
    }
  }

  public void cat(String path) {
    try {
      FSDataInputStream inputStream = this.fileSystem.open(new Path(path));
      IOUtils.copyBytes(inputStream, System.out, fileSystem.getConf(), true);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public ArrayList<String> topNLines(String path, int n) {
    FSDataInputStream inputStream = null;
    BufferedReader bufferedReader = null;
    String line;
    ArrayList<String> res = null;

    try {
      inputStream = this.fileSystem.open(new Path(path));
      bufferedReader = new BufferedReader(
          new InputStreamReader(inputStream));
      res = new ArrayList<>();
      int no = 0;

      while ((line = bufferedReader.readLine()) != null && no < n) {
        res.add(line);
        no++;
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (bufferedReader != null) {
        try {
          bufferedReader.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if (inputStream != null) {
        try {
          inputStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

    return res;
  }

  public void upload(String srcPath, String destPath, int bufferSize) {
    FileInputStream in = null;
    FSDataOutputStream os = null;
    try {
      in = new FileInputStream(srcPath);
      os = this.fileSystem.create(new Path(destPath), true);
      IOUtils.copyBytes(in, os, bufferSize, true);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public boolean deleteFile(String path) {
    boolean flag = false;
    try {
      flag = this.fileSystem.delete(new Path(path), true);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return flag;
  }

  public boolean makeDir(String path) {
    boolean flag = false;
    try {
      flag = this.fileSystem.mkdirs(new Path(path));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return flag;
  }

  public void downloadWithProcessor(String path, Map<String, Integer> descript) {
    FSDataInputStream in = null;
    try {
      in = fileSystem.open(new Path(path));
      descript.put("byteSize", in.available());
      descript.put("current", 0);
      byte[] bs = new byte[1024];
      while (-1 != (in.read(bs))) {
        descript.put("current", descript.get("current") + 1024);
      }
      in.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void uploadWithProcessor(String src, String dest, Map<String, Long> descript) {
    File file = new File(src);
    FileInputStream in = null;
    FSDataOutputStream os = null;
    try {
      in = new FileInputStream(file);
      os = this.fileSystem.create(new Path(dest), true);
      descript.put("byteSize", file.length());
      descript.put("current", 0l);
      // 0.5mb
      byte[] bs = new byte[1024 * 1024 / 2];
      while (-1 != (in.read(bs))) {
        os.write(bs);
        descript.put("current", descript.get("current") + 1024);
      }
      os.close();
      in.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    String s = "/test/test.csv";

    String[] strings = s.split("/");

    System.out.println(strings[strings.length - 1]);
  }
}
