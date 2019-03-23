package com.po.sd.designpattern;

/**
 * 《 7种设计原则 》
 * 1、单一职责原则：一个类只负责一个功能领域中的相应职责。或者可以定义为：就一个类而言，应该只有一个引起它变化的原因。
 * 2、开闭原则：一个软件实体应当对扩展开放，对修改关闭。即软件实体应尽量在不修改原有代码的情况下进行扩展。
 * 3、里氏代换原则：所有引用基类（父类）的地方必须能透明地使用其子类的对象。
 * 4、依赖倒转原则：抽象不应该依赖于细节，细节应当依赖于抽象。换言之，要针对接口编程，而不是针对实现编程。
 * 5、接口隔离原则：使用多个专门的接口，而不使用单一的总接口，即客户端不应该依赖那些它不需要的接口。
 * 6、合成复用原则：尽量使用对象组合，而不是继承来达到复用的目的。
 * 7、迪米特法则：一个软件实体应当尽可能少地与其他实体发生相互作用。一个对象只能与直接朋友发生交互，不要与"陌生人"发生直接交互。
 *
 * 单例模式：Singleton Pattern，确保某一个类只有一个实例，而且自行实例化并向整个系统提供这个实例，这个类称为单例类，它提供全局访问的方法.
 *
 * 双重检查单例：懒汉生成方式。确保实例化执行一次，线程安全，延时加载，效率高。
 *
 * Created by po on 23/03/2019.
 */
public class DoubleCheckSingleton {

    //添加 volatile 关键字，可实现实例状态及时共享。
    private static volatile DoubleCheckSingleton instance;

    private DoubleCheckSingleton() {
    }

    public static final DoubleCheckSingleton getInstance() {
        // 第一次判断可以确保实例化后的高效访问，避免重复加锁。
        if (null == instance) {
            // 添加锁后，重复判断，确保实例化一次。
            synchronized (DoubleCheckSingleton.class) {
                if (null == instance) {
                    instance = new DoubleCheckSingleton();
                }
            }
        }
        return instance;
    }
}
