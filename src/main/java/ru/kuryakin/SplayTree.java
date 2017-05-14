package ru.kuryakin;

public class SplayTree<T extends Comparable<T>>{

    private Node root;

    private int size = 0;

    private class Node{
        private T value;          
        private Node left, right;   

        public Node(T value){
            this.value   = value;
        }
    }

    public boolean contains(T value){
        return get(value) != null;
    }

    public T get(T value){
        root = splay(root, value);
        int cmp = value.compareTo(root.value);
        if (cmp == 0) 
            return root.value;
        else          
            return null;
    }

    public void add(T value){

        if (root == null){
            root = new Node(value);
            size++;
            return;
        }

        root = splay(root, value);

        int cmp = value.compareTo(root.value);

        if (cmp < 0){
            Node n = new Node(value);
            n.left = root.left;
            n.right = root;
            root.left = null;
            root = n;
        }

        else if (cmp > 0){
            Node n = new Node(value);
            n.right = root.right;
            n.left = root;
            root.right = null;
            root = n;
        }
        else{
            root.value = value;
        }
        size++;
    }

    public boolean remove(T value){
        if (root == null)
            return false;

        root = splay(root, value);

        int cmp = value.compareTo(root.value);

        if (cmp == 0){
            if (root.left == null){
                root = root.right;
                size--;
                return true;

            }
            else{
                Node x = root.right;
                root = root.left;
                splay(root, value);
                root.right = x;
                size--;
                return true;
            }
        }
        return false;
    }

    private Node splay(Node h, T value){
        if (h == null) return null;

        int cmp1 = value.compareTo(h.value);

        if (cmp1 < 0) {

            if (h.left == null){
                return h;
            }
            int cmp2 = value.compareTo(h.left.value);
            if (cmp2 < 0){
                h.left.left = splay(h.left.left, value);
                h = rotateRight(h);
            }
            else if (cmp2 > 0){
                h.left.right = splay(h.left.right, value);
                if (h.left.right != null)
                    h.left = rotateLeft(h.left);
            }

            if (h.left == null)
                return h;
            else
                return rotateRight(h);
        }

        else if (cmp1 > 0){

            if (h.right == null){
                return h;
            }

            int cmp2 = value.compareTo(h.right.value);
            if (cmp2 < 0){
                h.right.left  = splay(h.right.left, value);
                if (h.right.left != null)
                    h.right = rotateRight(h.right);
            }
            else if (cmp2 > 0){
                h.right.right = splay(h.right.right, value);
                h = rotateLeft(h);
            }

            if (h.right == null)
                return h;
            else
                return rotateLeft(h);
        }

        else return h;
    }

    public int size(){
        return size;
    }

    private Node rotateRight(Node h){
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        return x;
    }

    private Node rotateLeft(Node h){
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        return x;
    }
}