package com.po.sd;

import java.lang.instrument.Instrumentation;

/**
 * Created by ZJ on 14/01/2019.
 */
public class MemoryMonitor {

  public static Instrumentation inst;

  public static void premain(String agentArgs, Instrumentation instp) {
    inst = instp;
  }

  public static long sizeOf(Object obj) {
    return inst.getObjectSize(obj);
  }

  void test() {
    long free1 = Runtime.getRuntime().freeMemory();
  }

}
