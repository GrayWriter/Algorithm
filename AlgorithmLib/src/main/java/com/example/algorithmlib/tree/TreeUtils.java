package com.example.algorithmlib.tree;


import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class TreeUtils {
    public static List<Integer> preTraversal(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        if (root == null) {
            return ans;
        }

        Stack<TreeNode> stack = new Stack<>();
        pushLeftTree(root, stack, ans);

        while (!stack.empty()) {
            TreeNode node  = stack.pop();
            //入右子树
            pushLeftTree(node.right, stack, ans);
        }

        return ans;
    }

    public static List<Integer> postTraversal(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        if (root == null) {
            return ans;
        }

        Stack<TreeNode> stack = new Stack<>();
        // 指向上一次遍历完的子树根节点
        TreeNode visited = new TreeNode(-1);
        pushLeftTree(root, stack, null);

        while (!stack.empty()) {
            TreeNode node = stack.peek();
            // node的左子树被遍历完了，且右子树没有被遍历过,
            // 后序遍历当前节点的右子树有可能被遍历过

            if ((node.left == null || node.left == visited) && node.right != visited) {
                // 去遍历 p 的右子树
                pushLeftTree(node.right, stack, null);
            }

            //当前节点何时该弹出呢？
            //没有右子树或者的右子树被遍历过了
            if (node.right == null || node.right == visited) {
                //后序
                ans.add(node.val);
                // 以node为根的子树被遍历完了，出栈
                // visited 指针指向 p
                visited = stack.pop();
            }
        }

        return ans;
    }

    public static List<Integer> inTraversal(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        if (root == null) {
            return ans;
        }

        Stack<TreeNode> stack = new Stack<>();
        TreeNode visited = new TreeNode(-1);
        pushLeftTree(root, stack, null);

        while (!stack.empty()) {
            TreeNode node = stack.peek();
            // node的左子树被遍历完了，且右子树没有被遍历过
            if ((node.left == null || node.left == visited) && node.right != visited) {
                //中序
                ans.add(node.val);
                // 去遍历 p 的右子树
                pushLeftTree(node.right, stack, null);
            }

            // node 的右子树被遍历完了
            if (node.right == null || node.right == visited) {

                // 以node为根的子树被遍历完了，出栈
                // visited 指针指向 p
                visited = stack.pop();
            }
        }

        return ans;
    }



    private static void pushLeftTree(TreeNode root, Stack<TreeNode> stack, List<Integer> ans) {
        while (root != null) {
            /*前序遍历代码段*/
            if (ans != null) {
                ans.add(root.val);
            }
            stack.push(root);
            root = root.left;
        }
    }

    /**
     * traverse by recursion
     * @param root
     * @return
     */
    public static List<Integer> preTraversalR(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        preTraversalInternal(root, ans);
        return ans;
    }

    private static void preTraversalInternal(TreeNode root, List<Integer> ans) {
        if (root == null) {
            return;
        }

        ans.add(root.val);
        preTraversalInternal(root.left, ans);
        preTraversalInternal(root.right, ans);
    }


    public static List<Integer> postTraversalR(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        postTraversalInternal(root, ans);
        return ans;
    }

    private static void postTraversalInternal(TreeNode root, List<Integer> ans) {
        if (root == null) {
            return;
        }

        postTraversalInternal(root.left, ans);
        postTraversalInternal(root.right, ans);
        ans.add(root.val);
    }

    public static List<Integer> inTraversalR(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        inTraversalInternal(root, ans);
        return ans;
    }

    private static void inTraversalInternal(TreeNode root, List<Integer> ans) {
        if (root == null) {
            return;
        }

        inTraversalInternal(root.left, ans);
        ans.add(root.val);
        inTraversalInternal(root.right, ans);
    }


    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<Integer>();
        if (root == null) {
            return res;
        }

        Stack<TreeNode> stack = new Stack<>();
        while (!stack.isEmpty() || root != null) {
            //左子树一撸到底
            while (root != null) {
                //前序遍历位置
                res.add(root.val);
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            root = root.right;
        }
        return res;
    }

    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<Integer>();
        Stack<TreeNode> stk = new Stack<>();
        while (root != null || !stk.isEmpty()) {
            while (root != null) {
                stk.push(root);
                root = root.left;
            }
            root = stk.pop();
            //中序遍历
            res.add(root.val);
            root = root.right;
        }
        return res;
    }


    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }

        Stack<TreeNode> stack = new Stack<>();
        TreeNode prev = null;
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            //根据当前栈弹出的节点，决定是继续压右子树，还是直接下一轮弹栈
            root = stack.pop();
            //当前节点没有右子树或者右子树以及被遍历过了，直接加入结果
            if (root.right == null || root.right == prev) {
                res.add(root.val);
                prev = root;
                //关键点,为何要置空呢？如果不置空，最后根节点出栈，又会继续死循环了
                root = null;
            } else {
                //右子树还没遍历，把压自己再入栈，再接着迭代压右子树
                stack.push(root);
                root = root.right;
            }
        }
        return res;
    }

    public List<Integer> preorder(Node root) {
        List<Integer> ans = new LinkedList<>();
        if(root == null) {
            return ans;
        }
        Stack<Node> stack = new Stack<>();
        stack.add(root);
        while (!stack.isEmpty()) {
            root = stack.pop();
            ans.add(root.val);
            //逆序压栈
            for(int i = root.children.size() - 1; i >= 0; i--) {
                stack.push(root.children.get(i));
            }
        }
        return ans;
    }

    public List<Integer> preorderR(Node root) {
        List<Integer> ans = new LinkedList<>();
        preorder(root, ans);
        return ans;
    }

    private void preorder(Node root, List<Integer> ans) {
        if(root == null) {
            return;
        }

        ans.add(root.val);
        for(Node child : root.children) {
            preorder(child, ans);
        }
    }

    public List<Integer> postorder(Node root) {
        LinkedList<Integer> ans = new LinkedList<>();
        if (root == null) {
            return ans;
        }

        Stack<Node> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            Node node = stack.pop();
            //头部插入，根节点一定在最后
            ans.addFirst(node.val);
            //顺序压栈
            for (int i = 0; i < node.children.size(); i++) {
                stack.push(node.children.get(i));
            }
        }
        return ans;
    }

}
