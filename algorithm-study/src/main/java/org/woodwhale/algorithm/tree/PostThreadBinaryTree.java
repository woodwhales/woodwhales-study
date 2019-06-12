package org.woodwhale.algorithm.tree;

/**
 * @Title: 后序线索化二叉树相关操作
 * @Description：
 * @Author： Uncle Ming
 * @Date：2017年1月8日 下午3:42:14
 * @Version V1.0
 */
public class PostThreadBinaryTree {

    private Node preNode;   //线索化时记录前一个节点

    //节点存储结构
    static class Node {
        String data;        //数据域
        Node left;          //左指针域
        Node right;         //右指针域
        Node parent;        //父节点的指针（为了后序线索化使用）
        boolean isLeftThread = false;   //左指针域类型  false：指向子节点、true：前驱或后继线索
        boolean isRightThread = false;  //右指针域类型  false：指向子节点、true：前驱或后继线索

        Node(String data) {
            this.data = data;
        }
    }

    /**
     * 通过数组构造一个二叉树（完全二叉树）
     * @param array
     * @param index
     * @return
     */
    static Node createBinaryTree(String[] array, int index) {
        Node node = null;

        if(index < array.length) {
            node = new Node(array[index]);
            node.left = createBinaryTree(array, index * 2 + 1);
            node.right = createBinaryTree(array, index * 2 + 2);

            //记录节点的父节点（后序线索化遍历时使用）
            if(node.left != null) {
                node.left.parent = node;
            }

            if(node.right != null) {
                node.right.parent = node;
            }
        }

        return node;
    }

    /**
     * 后序线索化二叉树
     * @param node  节点
     */
    void postThreadOrder(Node node) {
        if(node == null) {
            return;
        }

        //处理左子树
        postThreadOrder(node.left);
        //处理右子树
        postThreadOrder(node.right);

        //左指针为空,将左指针指向前驱节点
        if(node.left == null) {
            node.left = preNode;
            node.isLeftThread = true;
        }

        //前一个节点的后继节点指向当前节点
        if(preNode != null && preNode.right == null) {
            preNode.right = node;
            preNode.isRightThread = true;
        }
        preNode = node;
    }

    /**
     * 后续遍历线索二叉树，按照后继方式遍历（思路：后序遍历开始节点是最左节点）
     * @param node
     */
    void postThreadList(Node root) {
        //1、找后序遍历方式开始的节点
        Node node = root;
        while(node != null && !node.isLeftThread) {
            node = node.left;
        }

        Node preNode = null;
        while(node != null) {
            //右节点是线索
            if(node.isRightThread) {
                System.out.print(node.data + ", ");
                preNode = node;
                node = node.right;

            } else {
                //如果上个处理的节点是当前节点的右节点
                if(node.right == preNode) {
                    System.out.print(node.data + ", ");
                    if(node == root) {
                        return;
                    }

                    preNode = node;
                    node = node.parent;

                } else {    //如果从左节点的进入则找到有子树的最左节点
                    node = node.right;
                    while(node != null && !node.isLeftThread) {
                        node = node.left;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        String[] array = {"A", "B", "C", "D", "E", "F", "G", "H", "I"};
        Node root = createBinaryTree(array, 0);

        PostThreadBinaryTree tree = new PostThreadBinaryTree();
        tree.postThreadOrder(root);
        System.out.println("后序按后继节点遍历线索二叉树结果：");
        tree.postThreadList(root);
    }
}
