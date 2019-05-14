package com.xf.basic.algorithm.skiplist;

/**
 * @Auther: xiaofeng
 * @Date: 2019/5/14 15:24
 * @Description:
 */
public class Node <T> {

	int key;
	T value;
	Node<T> up, down, left, right;

	public static final int HEAD_KEY = Integer.MIN_VALUE; // 负无穷
	public static final int  TAIL_KEY = Integer.MAX_VALUE; // 正无穷

	public Node(int key, T value) {
		this.key = key;
		this.value = value;
	}

	public boolean equals(Object o) {
		if (this==o) {
			return true;
		}
		if (o==null) {
			return false;
		}
		if (!(o instanceof Node<?>)) {
			return false;
		}
		Node<T> ent;
		try {
			ent = (Node<T>)  o; // 检测类型
		} catch (ClassCastException ex) {
			return false;
		}
		return (ent.key == key) && (ent.value == value);
	}
	@Override
	public String toString() {
		return "key-value:"+key+"-"+value;
	}

}