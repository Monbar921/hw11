package org.example;


import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="https://ru.wikipedia.org/wiki/%D0%90%D0%92%D0%9B-%D0%B4%D0%B5%D1%80%D0%B5%D0%B2%D0%BE">https://ru.wikipedia.org/wiki/%D0%90%D0%92%D0%9B-%D0%B4%D0%B5%D1%80%D0%B5%D0%B2%D0%BE</a>
 * <a href="https://habr.com/ru/articles/150732/">https://habr.com/ru/articles/150732/</a>
 * <a href="https://github.com/surajsubramanian/AVL-Trees/tree/master">https://github.com/surajsubramanian/AVL-Trees/tree/master</a>
 * @author Адельсон-Вельский Георгий Максимович и Ландис Евгений Михайлович
 */
public class AVLTree {

    private int size = 0;
    private Node root;
    int uniqueLength = 0;

    public boolean add(int key, int value) {
        if (findNode(root, key) == null) {
            root = bstInsert(root, key, value);
            return true;
        }
        return false;
    }

    public boolean contains(int key) {
        return findNode(root, key) != null;
    }

    public boolean inverseValue(int key) {
        Node node = findNode(root, key);
        if(node != null){
            node.value *= -1;
            int[] elementsParams = new int[]{0, 0, 1};
            recalculateSum(root,elementsParams);
            uniqueLength = elementsParams[2];
        }
        return node != null;
    }

    public int getMaxUniqueLength(){
        return uniqueLength;
    }

    private Node recalculateSum(Node node, int[] elementsParams){
        Node subNode = null;
        if(node != null) {
            if(node.left != null){
               recalculateSum(node.left, elementsParams);
            }

            if(node.value != elementsParams[1]){
                elementsParams[1] = node.value;
                elementsParams[0] += 1;
            }else {
                elementsParams[1] = node.value;
                elementsParams[0] = 1;
            }

            elementsParams[2] = Math.max(elementsParams[2], elementsParams[0]);

            if(node.right != null){
                recalculateSum(node.right, elementsParams);
            }
        }

        return node;
    }

    public boolean remove(int key) {
        if (findNode(root, key) != null) {
            root = remove(root, key);
            return true;
        }
        return false;
    }

    public List<Integer> inOrder(){
        List<Integer> arrayList = new ArrayList<>();
        inOrder(root, arrayList);
        return arrayList;
    }

    public void clear(){
        root = null;
    }

    private int Height(Node key) {
        if (key == null)
            return 0;

        else
            return key.height;
    }


    private int Balance(Node key) {
        if (key == null)
            return 0;

        else
            return (Height(key.right) - Height(key.left));
    }


    private void updateHeight(Node key) {
        int l = Height(key.left);
        int r = Height(key.right);

        key.height = Math.max(l, r) + 1;
    }

    private Node rotateLeft(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        updateHeight(x);
        updateHeight(y);

        return y;
    }

    private Node rotateRight(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        updateHeight(y);
        updateHeight(x);

        return x;
    }

    private Node balanceTree(Node root) {
        updateHeight(root);

        int balance = Balance(root);

        if (balance > 1) //R
        {
            if (Balance(root.right) < 0)//RL
            {
                root.right = rotateRight(root.right);
                return rotateLeft(root);
            } else //RR
                return rotateLeft(root);
        }

        if (balance < -1)//L
        {
            if (Balance(root.left) > 0)//LR
            {
                root.left = rotateLeft(root.left);
                return rotateRight(root);
            } else//LL
                return rotateRight(root);
        }

        return root;
    }


    private Node bstInsert(Node root, int key, int value) {
        // Performs normal BST insertion
        if (root == null) {
            ++size;
            return new Node(key, value);
        }

        else if (key < root.key)
            root.left = bstInsert(root.left, key, value);

        else
            root.right = bstInsert(root.right, key, value);

        // Balances the tree after BST Insertion
        return balanceTree(root);
    }

    private Node  successor(Node root) {
        if (root.left != null)
            return successor(root.left);

        else
            return root;
    }


    private Node remove(Node root, int key) {
        // Performs standard BST Deletion
        if (root == null)
            return root;

        else if (key < root.key)
            root.left = remove(root.left, key);

        else if (key > root.key)
            root.right = remove(root.right, key);

        else {
            if (root.right == null)
                root = root.left;

            else if (root.left == null)
                root = root.right;

            else {
                Node temp = successor(root.right);
                root.value = temp.key;
                root.right = remove(root.right, root.key);
            }
        }

        if (root == null)
            return root;

        else
            // Balances the tree after deletion
            return balanceTree(root);
    }

    private Node findNode(Node node, int key) {
        if (node == null || key == node.key)
            return node;

        if (key < node.key) {
            return findNode(node.left, key);
        }
        else {
            return findNode(node.right, key);
        }
    }

    private Node findKeyRootNode(Node node, int key) {
        if (node == null || key == node.key)
            return node;

        if (node.left != null && key == node.left.key)
            return node;

        if (node.right != null && key == node.right.key)
            return node;

        if (key < node.key) {
            return findNode(node.left, key);
        }
        else {
            return findNode(node.right, key);
        }
    }

    private void inOrder(Node cur, List<Integer> res) {
        if (cur == null) {
            return;
        }

        if (cur.left != null)
            inOrder(cur.left, res);
        res.add(cur.key);
        if (cur.right != null)
            inOrder(cur.right, res);

    }

    @Getter
    static class Node {
        int key;
        int value;
        int height;
        int uniqueLength;
        Node left;
        Node right;

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.height = 1;
            this.uniqueLength = 1;
        }

        public String getLabel() {
            return String.format("%d (%d)", this.value, this.height);
        }


    }

}