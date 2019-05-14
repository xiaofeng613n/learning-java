package com.xf.basic.algorithm.skiplist;

import java.util.Random;

import static com.xf.basic.algorithm.skiplist.Node.TAIL_KEY;

/**
 * @Auther: xiaofeng
 * @Date: 2019/5/14 15:25
 * @Description:
 * https://www.jianshu.com/p/60d2561b821c
 */
public class SkipList<T> {

	private Node<T> head, tail;
	private int nodes;//节点总数
	private int listLevel;//层数
	private Random random;// 用于投掷硬币
	private static final double PROBABILITY = 0.5;//向上提升一个的概率

	public SkipList() {
		random = new Random();
		clear();
	}

	/**
	 * 清空跳跃表
	 */
	public void clear() {
		head = new Node<T>(Node.HEAD_KEY, null);
		tail = new Node<T>(TAIL_KEY, null);
		horizontalLink(head, tail);
		listLevel = 0;
		nodes = 0;
	}

	public boolean isEmpty() {
		return nodes == 0;
	}

	public int size() {
		return nodes;
	}

	/**
	 * 在最下面一层，找到要插入的位置前面的那个key
	 */
	private Node<T>  findNode(int key) {
		Node<T> p = head;
		while (true) {
			while (p.right.key != TAIL_KEY && p.right.key <= key) {
				p = p.right;
			}
			if (p.down != null) {
				p = p.down;
			}
			else {
				break;
			}

		}
		return p;
	}

	/**
	 * 查找是否存储key，存在则返回该节点，否则返回null
	 */
	public Node<T> search(int key) {
		Node<T> p = findNode(key);
		if (key == p.key) {
			return p;
		}
		else {
			return null;
		}
	}

	/**
	 * 向跳跃表中添加key-value
	 */
	public void put(int k, T v) {
		Node<T> p = findNode(k);
		//如果key值相同，替换原来的vaule即可结束
		if (k == p.key) {
			p.value = v;
			return;
		}
		Node<T> q = new Node<T>(k, v);
		backLink(p, q);
		int currentLevel = 0;//当前所在的层级是0
		//抛硬币
		while (random.nextDouble() < PROBABILITY) {
			//如果超出了高度，需要重新建一个顶层
			if (currentLevel >= listLevel) {
				listLevel++;
				Node<T> p1 = new Node<T>(Node.HEAD_KEY, null);
				Node<T> p2 = new Node<T>(TAIL_KEY, null);
				horizontalLink(p1, p2);
				vertiacallLink(p1, head);
				vertiacallLink(p2, tail);
				head = p1;
				tail = p2;
			}
			//将p移动到上一层
			while (p.up == null) {
				p = p.left;
			}
			p = p.up;

			Node<T> e = new Node<T>(k, null);//只保存key就ok
			backLink(p, e);//将e插入到p的后面
			vertiacallLink(e, q);//将e和q上下连接
			q = e;
			currentLevel++;
		}
		nodes++;//层数递增
	}

	//node1后面插入node2
	private void backLink(Node<T> node1, Node<T> node2) {
		node2.left = node1;
		node2.right = node1.right;
		node1.right.left = node2;
		node1.right = node2;
	}

	/**
	 * 水平双向连接
	 */
	private void horizontalLink(Node<T> node1, Node<T> node2) {
		node1.right = node2;
		node2.left = node1;
	}

	/**
	 * 垂直双向连接
	 */
	private void vertiacallLink(Node<T> node1, Node<T> node2) {
		node1.down = node2;
		node2.up = node1;
	}

	/**
	 * 打印出原始数据
	 */
	@Override
	public String toString() {

		if (isEmpty()) {
			return "跳跃表为空！";
		}
		StringBuilder builder = new StringBuilder();
		Node<T> p = head;
		while (p.down != null) {
			p = p.down;
		}

		while (p.left != null) {
			p = p.left;
		}
		if (p.right != null) {
			p = p.right;
		}
		while (p.right != null) {
			builder.append(p);
			builder.append("\n");
			p = p.right;
		}
		return builder.toString();
	}


	public String print() {
		StringBuilder sb = new StringBuilder();
		Node<T> currentHead = head;

		while ( true ) {
			Node<T> current = currentHead;
			while (current.right !=null && current.right.key != TAIL_KEY) {
				current = current.right;
				sb.append(current.key + ":" + current.value + " -> ");
			}
			sb.append("\n");
			if ( currentHead.down != null){
				currentHead = currentHead.down;
			} else {
				break;
			}
		}
		return sb.toString();
	}


	public static void main(String[] args) {

		SkipList<String> list = new SkipList<String>();
		System.out.println(list);
		list.put(2, "yan");
		list.put(1, "co");
		list.put(3, "feng");
		list.put(1, "cao");//测试同一个key值
		list.put(4, "a");
		list.put(7, "b");
		list.put(5, "c");
		System.out.println(list);
		System.out.println(list.size());
		System.out.println(list.print());


		Node node = list.findNode(6);
		System.out.println(node.key);
		System.out.println(node.value);
	}
}
