package com.po.sd.algorithm;

/**
 * ####计算机是以补码的方式进行存储数据####
 * 原码表示法规定：用符号位和数值表示带符号数，正数的符号位用“0”表示，负数的符号位用“1”表示，数值部分用二进制形式表示。
 * 反码表示法规定：正数的反码与原码相同，负数的反码为对该数的原码除符号位外各位取反。
 * 补码表示法规定：正数的补码与原码相同，负数的补码为对该数的原码除符号位外各位取反，然后在最后一位加1. 正零和负零的补码相同，[+0]补=[-0]补=0000 0000B
 * 由补码求原码的过程跟由原码求补码的过程一样。
 *
 * #### 采用补码运算具有如下两个特征：
 *
 * 1）因为使用补码可以将符号位和其他位统一处理，同时，减法也可以按加法来处理，即如果是补码表示的数，不管是加减法都直接用加法运算即可实现。
 *
 * 2）两个用补码表示的数相加时，如果最高位（符号位）有进位，则进位被舍弃。
 *
 * #### 这样的运算有两个好处：
 *
 * 1）使符号位能与有效值部分一起参加运算，从而简化运算规则。从而可以简化运算器的结构，提高运算速度；（减法运算可以用加法运算表示出来。）
 *
 * 2）加法运算比减法运算更易于实现。使减法运算转换为加法运算，进一步简化计算机中运算器的线路设计。
 *
 *
 *
 * ##原码##
 *
 * 00000000 00000000 00000000 00000101 是 5的 原码
 *
 * 10000000 00000000 00000000 00000101 是 -5的 原码
 *
 *
 * ##反码##
 *
 * 正数 00000000 00000000 00000000 00000101  的反码还是 00000000 00000000 00000000 00000101
 *
 * 负数 10000000 00000000 00000000 00000101  的反码则是 11111111 11111111 11111111 11111010
 *
 *
 * ##补码##
 *
 * 正数 00000000 00000000 00000000 00000101  的补码还是 00000000 00000000 00000000 00000101
 *
 * 负数 10000000 00000000 00000000 00000101  的补码 则是 反码 11111111 11111111 11111111 11111010 + 1 =
 * 11111111 11111111 11111111 11111011
 *
 *
 *
 *
 * ####位运算####
 *
 * 只能用于整形数据，对float和double类型进行位操作会被编译器报错。
 *
 * & : 与，两位数都是 1，结果才是 1。
 *
 * | : 或，两位数都是 0，结果才是 0。
 *
 * ^ : 异或，两位数不同，结果才是 1，同为 0 或 1，结果是 0。（0^1 = 1, 1^0 = 1, 0^0 = 0, 1^1 = 0）
 *
 * ~ : 取反，0 变 1，1 变 0。
 *
 * << : 左移，各二进位全部左移若干位，高位丢弃，地位补 0。
 *
 * >> : 右移，各二进位全部右移若干位，对于无符号数高位补 0，对于有符号数各编译器处理方式不同，有的补符号位（算数右移），有的补
 * 0（逻辑右移）。算术移位是相对于逻辑移位，它们在左移操作中都一样，低位补0即可，但在右移中逻辑移位的高位补0而算术移位的高位是补符号位。
 *
 * >>> : 无符号位右移，只右移数值位，符号位不作右移。
 *
 * 位运算用途：位操作技巧 判断奇偶 交换两数 变换符号 求绝对值 位操作压缩空间 筛素数 在高低位交换 二进制逆序 二进制中1的个数 缺失的数字
 *
 * Created by ZJ on 22/08/2018.
 */
public class BitOper {

  /**
   * 1、位运算判断偶数，根据最未位是0还是1来决定，为0就是偶数，为1就是奇数。
   *
   * @param x Input Value
   * @return boolean
   */
  public static boolean isEven(int x) {
    if ((x & 1) == 1) {
      return false;
    }
    return true;
  }

  /**
   * 2、位运算交换两个数，异或满足交换率：a^b^c = a^c^b，一个数和自己异或的结果为0，并且任何数与0异或都会不变的。
   *
   * a = a ^ b
   * b = b ^ a = b ^ (a ^ b) = b ^ (b ^ a) = b ^ b ^ a = 0 ^ a = a
   * a = a ^ b = (a ^ b) ^ b = (a ^ b) ^ a = a ^ a ^ b = 0 ^ b = b
   *
   * @param a Input Value
   * @param b Input Value
   */
  public static int[] swap(int a, int b) {
    a ^= b;
    b ^= a;
    a ^= b;
    return new int[]{a, b};
  }

  /**
   * 3、位运算求负数 改变数据符号：正数求负数，负数求正数。 一个数可以通过对其取反后加1来得到正数。
   *
   * eg：
   *
   * 5 => 00000000 00000000 00000000 00000101 (计算机存储补码) => ~ + 1 => 11111111 11111111 11111111
   * 11111010 + 1 => 11111111 11111111 11111111 11111011 => -5
   *
   * -5 => 11111111 11111111 11111111 11111011 (计算机存储补码) => ~ + 1 => 00000000 00000000 00000000
   * 00000100 + 1 => 00000000 00000000 00000000 00000101 => 5
   *
   * @param a Input Value
   * @return int
   */
  public static int transSymbol(int a) {
    return ~a + 1;
  }

  /**
   * 4、位运算求绝对值 int 32 位，先移位来取符号位，int i = a >> 31; 要注意如果a为正数，i等于0，为负数，i等于-1。对于任何数，与0异或都会保持不变，与-1即0xFFFFFFFF异或就相当于取反。因此，a与i异或后再减i（因为i为0或-1，所以减i即是要么加0要么加1）也可以得到绝对值。
   *
   * eg：
   *
   * 5 => 00000000 00000000 00000000 00000101 (计算机存储补码) => a >> 31 => 00000000 00000000 00000000
   * 00000000 => a^i => 00000000 00000000 00000000 00000101 => (a^i)-i => 00000000 00000000 00000000
   * 00000101 => -5
   *
   * -5 => 11111111 11111111 11111111 11111011 (计算机存储补码) => a >> 31 => 11111111 11111111 11111111
   * 11111111 => a^i => 00000000 00000000 00000000 00000100 => (a^i)-i => (a^i)+1 => 00000000
   * 00000000 00000000 00000101 => 5
   *
   * @param a Input value
   * @return int
   */
  public static int abs(int a) {
    int i = a >> 31;
    return (a ^ i) - i;
  }

  /**
   * Bits array operation:
   * com.po.sd.algorithm.BitOper#createBitsByteArray(int)
   * com.po.sd.algorithm.BitOper#setBitsTo1(byte[], int)
   * com.po.sd.algorithm.BitOper#checkBitsIs1(byte[], int)
   *
   * 根据长度创建一个存放 bits 的 byte[]，默认 bits 初始化为 0，可以替代 boolean[]
   *
   * @param length ：二进制数 bits 的长度
   * @return byte[] ：存放 bits 的 byte[] 数组
   */
  public static byte[] createBitsByteArray(int length) {
    // 适用于正整数 整除 (length >> 3) equals (length / 8)
    return new byte[(length >> 3) + 1];
  }

  /**
   * 将 bits 中的某个位设置为 1
   *
   * @param bits 二进制数 bits
   * @param position 需要设置为 1 的 bits 位置，从 0 开始
   */
  public static void setBitsTo1(byte[] bits, int position) {
    /**
     * 适用于 2 的幂次的正整数
     * 整除 (length >> 3) equals (length / 8)
     * 除8取余 (position & 7) equals (position % 8)
     */
    bits[position >> 3] |= 1 << (position & 7);
  }

  /**
   * 适用于 2 的幂次的正整数
   * 判断某一位置上 bit 值是否为 1
   *
   * @param bits 存放 bits 的 byte[] 数组，可以替代 boolean[]
   * @param position 位置，从 0 开始
   * @return boolean
   */
  public static boolean checkBitsIs1(byte[] bits, int position) {
    if ((bits[position >> 3] & (1 << (position & 7))) > 0) {
      return true;
    }
    return false;
  }

  /**
   * int 转 二进制字符串
   *
   * @param in input value
   * @return String result
   */
  public static String toBinaryStr(int in) {
    StringBuffer sb = new StringBuffer();
    for (int i = 31; i >= 0; i--) {
      if (((in >> i) & 1) == 1) {
        sb.append('1');
      } else {
        sb.append('0');
      }
      if (i == 16) {
        sb.append(' ');
      }
    }
    return sb.toString();
  }

  /**
   * 高低位交换，适用于无符号数
   *
   * @param in Input value
   * @return String result
   */
  public static int exchangePostion(int in) {
    return (in >>> 16) | (in << 16);
  }

  /**
   * 二进制反转顺序，将每位 0 or 1 的顺序做逆序操作。
   * 1、先获取奇数位和偶数位上的二进制数，将奇数位上的二进制数左移，偶数位上的二进制数右移，然后两数求或；
   * 2、以两位二进制数为单位，重复第一步；
   * 3、以 2^2 位二进制数为单位，重复第一步；
   * 4、以 2^3 位二进制数为单位，重复第一步；
   * 5、以 2^n (2^n 为传入数据二进制数的位数的 1/2) 位二进制数为单位，重复第一步；
   *
   * 0x55555555 = 0101010101010101 0101010101010101
   * 0x33333333 = 0011001100110011 0011001100110011
   * 0x0f0f0f0f = 0000111100001111 0000111100001111
   * 0xff00 = 0000000000000000 1111111100000000
   *
   * @param in input value
   * @return int result
   */
  public static int bitsReverse(int in) {
    in = (in & 0x55555555) << 1 | (in >>> 1) & 0x55555555;
    in = (in & 0x33333333) << 2 | (in >>> 2) & 0x33333333;
    in = (in & 0x0f0f0f0f) << 4 | (in >>> 4) & 0x0f0f0f0f;
    in = (in << 24) | ((in & 0xff00) << 8) |
        ((in >>> 8) & 0xff00) | (in >>> 24);
    return in;
  }

  /**
   * variable-precision SWAR 算法统计 32位二进制数中 1 的个数，可以衍生出求64位128位等二进制数中 1 的个数。
   * 过程：先求两两位的1的个数，求四位四位1的个数，求8位8位1的个数，依次类推。
   *
   * @param in int type number has 32 bits in java
   * @return int result
   */
  public static int countBits1(int in) {
    in = (in & 0x55555555) + ((in >>> 1) & 0x55555555);
    in = (in & 0x33333333) + ((in >>> 2) & 0x33333333);
    in = (in & 0x0f0f0f0f) + ((in >>> 4) & 0x0f0f0f0f);
    in = (in & 0x00ff00ff) + ((in >>> 8) & 0x00ff00ff);
    in = (in & 0x0000ffff) + ((in >>> 16) & 0x0000ffff);
    return in;
  }

  /**
   * 数据集合中所有数都是成对出现的，即每一个数的出现次数为偶数，在这样数据集合中找出丢失的一个数。
   * 利用异或运算的两个特性: 1.自己与自己异或结果为0; 2.异或满足交换律。因此我们将这些数字全异或一遍，结果就一定是那个仅出现一个的那个数。
   * eg: int[] ins = new int[]{23, 43, 5, 7, 53, 43, 13, 5, 53, 23, 7} 中找出 13
   *
   * @param ins input value
   * @return int result
   */
  public static int findLostNum(int[] ins) {
    int lost = 0;
    for (int in : ins) {
      lost ^= in;
    }
    return lost;
  }

  /**
   * 数据集合中所有数都是成对出现的，即每一个数的出现次数为偶数，在这样的数据集合中找出丢失的两个数。
   * 利用异或运算的两个特性: 1.自己与自己异或结果为0; 2.异或满足交换律。因此我们将这些数字全异或一遍，结果就一定是那个仅出现一个的那个数。
   * 这个题目的关键点就是将A，B分开到二个数组中。由于A，B肯定是不相等的，因此在二进制上必定有一位是不同的。根据这一位是0还是1可以将A，B分开到A组和B组。
   * 而这个数组中其它数字要么就属于A组，要么就属于B组。再对A组和B组分别执行“异或”解法就可以得到A，B了。
   * 而要判断A，B在哪一位上不相同，只要根据A异或B的结果就可以知道了，这个结果在二进制上为1的位都说明A，B在这一位上是不相同的。
   *
   * eg: int[] ins = new int[]{23, 43, 71, 5, 7, 53, 43, 13, 5, 53, 23, 7} 中找出 13,71
   *
   * @param ins input value
   * @return int[] result
   */
  public static int[] findTwoLostNum(int[] ins) {
    int flag = 0, pos = 0, res1 = 0, res2 = 0;

    // 遍历求异或和
    for (int in : ins) {
      flag ^= in;
    }
    // 根据和数判断两个缺失数字某一位上不同
    for (; pos < Integer.SIZE; pos++) {
      if (((flag >> pos) & 1) == 1) {
        break;
      }
    }
    // 将所有数字根据上面求出的不同位值的位置，将数字分成两组，按求解缺失一个数字的原理分别求出缺失的数字
    for (int in : ins) {
      if (((in >> pos) & 1) == 1) {
        res1 ^= in;
      } else {
        res2 ^= in;
      }
    }

    return new int[]{res1, res2};
  }

  public static void main(String[] args) {

    /**
     * Java int is 32 bits，所以计算机中存储的二进制为（计算机以补码形式存储）：
     * a = 15, bit data is 00000000 00000000 00000000 00001111.（补码）
     * b = -15, bit data is 11111111 11111111 11111111 11110001.（补码，原码为：10000000 00000000 00000000 00001111）
     *
     * a >> 2  =>  00000000 00000000 00000000 00000011(补码)  =>  00000000 00000000 00000000 00000011(原码)  =>  3
     * b >> 2  =>  11111111 11111111 11111111 11111100(补码)  =>  10000000 00000000 00000000 00000100(原码)  =>  -4
     */
    int a = 15, b = -15;
    System.out.printf("\rRes: 15 >> 2 => %d  ||  -15 >> 2 => %d \n", a >> 2, b >> 2);

    /**
     * 奇偶判断 \r 回车 \n 换行
     */
    System.out.printf("\r是否偶数：%s \r", BitOper.isEven(78));

    /**
     * 交换两个数
     */
    int[] res = BitOper.swap(a, b);
    System.out.printf("\ra = %s  ||  b = %s \r", res[0], res[1]);

    /**
     * 改变正负号
     */
    System.out.printf("Transform symbol: %d\r", BitOper.transSymbol(-78));

    /**
     * 求绝对值
     */
    System.out.printf("Abs value: %d\r", BitOper.abs(3));

    /**
     * Bits array[]
     */
    byte[] bytes = BitOper.createBitsByteArray(99);
    BitOper.setBitsTo1(bytes, 3);
    BitOper.setBitsTo1(bytes, 23);
    BitOper.setBitsTo1(bytes, 93);
    System.out.println(BitOper.checkBitsIs1(bytes, 24));
    System.out.println(BitOper.checkBitsIs1(bytes, 23));


  }
}
