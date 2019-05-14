package com.xf.basic.algorithm.trie;

/**
 * @Auther: xiaofeng
 * @Date: 2019-05-12 21:48
 * @Description:
 */
public class Trie {

    private Node root;

    public Trie() {
        root = new Node(' ');
    }

    public void insert(String word) {
        if( search(word)) {
            return;
        }

        Node current = root;
        for (int i = 0; i < word.length(); i++) {
            Node child = current.subNode(word.charAt(i));
            if (child != null) {
                current = child;
            } else {
                Node newNode = new Node(word.charAt(i));
                current.childList.add(newNode);
                current = newNode;
            }
            current.count ++;
        }

        current.isEnd = true;
    }

    public boolean search(String word) {
        Node current = root;
        for (int i = 0; i < word.length(); i++) {
            if (current.subNode(word.charAt(i)) == null) {
                return false;
            } else {
                current = current.subNode(word.charAt(i));
            }
        }
        if (current.isEnd == true) {
            return true;
        } else {
            return false;
        }
    }

    public void deleteWord(String word) {
        if( search(word) == false) {
            return;
        }
        Node current = root;
        for (char c : word.toCharArray()) {
            Node child = current.subNode(c);
            if (child.count == 1) {
                current.childList.remove(child);
            } else {
                child.count --;
                current = child;
            }
        }
       // current.isEnd = false;
    }


    public static void main(String[] args) {
        Trie trie = new Trie();
        trie.insert("ball");
        trie.insert("balls");
        trie.insert("sense");

        // testing deletion
        System.out.println(trie.search("balls"));
        System.out.println(trie.search("ba"));
        System.out.println(trie.search("ball"));
        trie.deleteWord("balls");
        System.out.println(trie.search("balls"));
        System.out.println(trie.search("ball"));
    }

}
