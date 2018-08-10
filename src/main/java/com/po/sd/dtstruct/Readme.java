package com.po.sd.dtstruct;

/**
 * Collection
 *
 * 1.list （ArrayList ，LinkedList（频繁插入删除的时候用），Vector（线程安全），Stack（先进后出））
 *
 * 2.set （HashSet（无序，根据哈希值查找Entry），TreeSet（需要排序的时候用），LinkedHashSet（有序&&有序迭代的时候用） ）
 *
 *
 *
 * Map
 *
 * 1.TreeMap（需要排序的时候用）
 *
 * 2.HashMap（无序，根据key的哈希值查找Entry，concurrentHashMap（线程安全）），LinkedHashMap（有序&&有序迭代的时候用），HashTable（线程安全）
 *
 *
 *
 * 然后我们对具体的每个集合做一个简单的分析
 *
 * ArrayList：数组集合，无容量限制，非线程安全
 *
 * LinkedList：基于双向列表的机制，插入创建一个Entry对象，并切换前后元素引用，非线程安全
 *
 * 相对于ArrayList，其优势：add，remove较快，因为只需要操作前后元素，而ArrayList需要操作整个列表
 *
 * get，set较慢以为ArrayList是有序的，LinkedList需要整个遍历
 *
 * Vector：同ArrayList类似，最大区别是线程安全，还有自动扩充机制为2倍（ArrayList1.5倍）
 *
 * Stack：继承自Vector，压栈，后进先出（push，pop。peek）
 *
 *
 *
 * 所有HashSet都是在加入的时候，先从对象中hashcode一个值，然后通过这个值加入到Set中
 *
 * HashSet：基于HashMap实现，非线程安全，能存一个null（哈希表通过使用散列表的形式来存贮信息，集合内元素没有特定顺序，且随时会变）
 *
 * TreeSet：（SortedSet）基于TreeMap实现 ，key需要实现comparator，实现排序
 *
 * 相对于HashSet：支持排序
 *
 *
 *
 * LinkedHashSet：根据哈希值来判断元素存贮的位置，同时使用链表来维护元素之前的顺序，所以他是有序的
 *
 * 优势：迭代速度比HashSet好，插入删除查（因为需要维护前后元素的关系）
 *
 *
 *
 * HashMap：根据数组的中hash码，查找Entry在另外一个数组中的位置，遍历用iterator
 *
 * HashTable：线程安全，不允许null，便利用enumeration
 *
 * TreeMap：能排序的map
 *
 * LinkedHashMap：相对于HashMap，插入的时候有序，所以排序的时候
 *
 *
 *
 * Created by ZJ on 10/08/2018.
 */
public class Readme {

}
