package com.xf.basic.algorithm.trie;

import java.util.LinkedList;

/**
 * @Auther: xiaofeng
 * @Date: 2019-05-12 21:42
 * @Description:
 */
public class Node {
    char content;
    boolean isEnd;
    int count;
    LinkedList<Node> childList;

    public Node(char c) {
        childList = new LinkedList<>();
        isEnd = false;
        content = c;
        count = 0;
    }

    public Node subNode(char c) {
        if (childList != null) {
            for (Node child : childList) {
                if( child.content == c) {
                    return child;
                }
            }
        }
        return null;
    }
}
