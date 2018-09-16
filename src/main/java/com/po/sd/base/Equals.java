package com.po.sd.base;

/**
 * Created by po on 2017/7/10.
 * 自定义类中equals方法默认继承自父类Object，代码如下：
 * public boolean equals(Object obj) {return (this == obj);}
 * 所以如果自己不实现equals方法，当调用equals方法时等价与"=="判断，即判断两个对象当引用是否相等。
 */
public class Equals {

    private String name;

    private String flag;

    private Equals() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public static void main(String[] args) {
        Equals e1 = new Equals();
        e1.setName("google");
        Equals e2 = new Equals();
        e2.setName("google");
        Equals e3 =e1;
        System.out.println("e1 and e2 equals : " + e1.equals(e2));
        System.out.println("e1 and e2 == : "+ (e1 == e2));
        System.out.println("e1 and e3 equals : " + e1.equals(e3));
        System.out.println("e1 and e3 == : "+ (e1 == e3));
        e1.setName("e1");
        System.out.println("e1 and e3 equals : " + e1.equals(e3));
        System.out.println("e1 and e3 == : "+ (e1 == e3));
        System.out.println(e3.getName());
        e3.setName("e3");
        System.out.println("e1 and e3 equals : " + e1.equals(e3));
        System.out.println("e1 and e3 == : "+ (e1 == e3));
        System.out.println(e1.getName());
        System.out.println(e3.getName());

        /**
         * String类实现了equals方法的重写，方法判断的是两个对象的值是否相等，代码如下：
         *
         public boolean equals(Object anObject) {
             if (this == anObject) {
                return true;
             }
             if (anObject instanceof String) {
                 String anotherString = (String)anObject;
                 int n = value.length;
                 if (n == anotherString.value.length) {
                     char v1[] = value;
                     char v2[] = anotherString.value;
                     int i = 0;
                     while (n-- != 0) {
                         if (v1[i] != v2[i])
                            return false;
                         i++;
                     }
                     return true;
                 }
             }
             return false;
         }
         */
        String a=new String("foo");
        String b=new String("foo");
        System.out.println(a.equals(b));
        System.out.println(a == b);
    }
}
