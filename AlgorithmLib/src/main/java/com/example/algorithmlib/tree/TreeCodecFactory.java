package com.example.algorithmlib.tree;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * NO.297
 */
public class TreeCodecFactory {
    /** 前序遍历编解码 */
    public static final int PRE = 0;

    /** 后序遍历编解码 */
    public static final int POST = 1;

    /** 层次遍历编解码，力扣使用的是层次遍历 */
    public static final int LEVEL = 2;

    private static final Map<Integer, TreeCodec> CACHE = new HashMap<>(3);
    private static final String SEP = ",";
    private static final String NULL = "null";

    /**
     * @param order {@link TreeCodecFactory#PRE}
     * @return
     */
    public static TreeCodec getCodec(int order) {
        TreeCodec codec = CACHE.get(order);
        if (codec == null) {
            codec = makeCodec(order);
            CACHE.put(order, codec);
        }
        return codec;
    }


    private static TreeCodec makeCodec(int order) {
        TreeCodec codec;
        switch (order) {
            case PRE:
                codec = new PreCodec();
                break;
            case POST:
                codec = new PostCodec();
                break;
            case LEVEL:
                codec = new LevelCodec();
                break;
            default:
                throw new IllegalArgumentException("unSupport order");
        }

        return codec;
    }


    private static class PreCodec implements TreeCodec {
        @Override
        public String serialize(TreeNode root) {
            StringBuilder sb = new StringBuilder();
            serializeInternal(root, sb);
            return sb.toString();
        }

        private void serializeInternal(TreeNode root, StringBuilder sb) {
            if (root == null) {
                sb.append(NULL).append(SEP);
                return;
            }

            sb.append(root.val).append(SEP);
            serializeInternal(root.left, sb);
            serializeInternal(root.right, sb);
        }

        @Override
        public TreeNode deserialize(String data) {
            Deque<String> nodes = new LinkedList<>();
            for (String s : data.split(SEP)) {
                nodes.addLast(s);
            }
            return deserializeInternal(nodes);
        }

        private TreeNode deserializeInternal(Deque<String> queue) {
            if (queue.isEmpty()) {
                return null;
            }

            String node = queue.poll();
            if (node.equals(NULL)) {
                return null;
            }
            TreeNode root = new TreeNode(Integer.parseInt(node));

            root.left = deserializeInternal(queue);
            root.right = deserializeInternal(queue);

            return root;
        }
    }

    private static class PostCodec implements TreeCodec {
        @Override
        public String serialize(TreeNode root) {
            StringBuilder sb = new StringBuilder();
            serializeInternal(root, sb);
            return sb.toString();
        }

        private void serializeInternal(TreeNode root, StringBuilder sb) {
            if (root == null) {
                sb.append(NULL).append(SEP);
                return;
            }
            serializeInternal(root.left, sb);
            serializeInternal(root.right, sb);
            sb.append(root.val).append(SEP);
        }

        @Override
        public TreeNode deserialize(String data) {
            Deque<String> nodes = new LinkedList<>();
            for (String s : data.split(SEP)) {
                nodes.addLast(s);
            }
            return deserializeInternal(nodes);
        }

        private TreeNode deserializeInternal(Deque<String> queue) {
            if (queue.isEmpty()) {
                return null;
            }

            String node = queue.removeLast();
            if (node.equals(NULL)) {
                return null;
            }
            TreeNode root = new TreeNode(Integer.parseInt(node));

            root.right = deserializeInternal(queue);
            root.left = deserializeInternal(queue);

            return root;
        }
    }

    private static class LevelCodec implements TreeCodec {
        @Override
        public String serialize(TreeNode root) {
            if (root == null) {
                return "";
            }

            Queue<TreeNode> queue = new LinkedList<>();
            StringBuilder sb = new StringBuilder();
            queue.offer(root);

            while (!queue.isEmpty()) {
                TreeNode node = queue.poll();
                if (node == null) {
                    sb.append(NULL).append(SEP);
                } else {
                    sb.append(node.val).append(SEP);
                    //扩散
                    queue.offer(node.left);
                    queue.offer(node.right);
                }
            }
            return sb.toString();
        }

        @Override
        public TreeNode deserialize(String data) {
            if (data.isEmpty()) {
                return null;
            }
            String[] nodes = data.split(SEP);
            // 第一个元素就是 root 的值
            TreeNode root = new TreeNode(Integer.parseInt(nodes[0]));

            // 队列 q 记录父节点，将 root 加入队列
            Queue<TreeNode> q = new LinkedList<>();
            q.offer(root);

            for (int i = 1; i < nodes.length; ) {
                // 队列中存的都是父节点
                TreeNode parent = q.poll();

                // 父节点对应的左侧子节点的值
                String left = nodes[i++];
                if (!left.equals(NULL)) {
                    assert parent != null;
                    parent.left = new TreeNode(Integer.parseInt(left));
                    q.offer(parent.left);
                } else {
                    assert parent != null;
                    parent.left = null;
                }
                // 父节点对应的右侧子节点的值，力扣样板尾部null会省略
                // eg. 1,null,2,3,null,null,null,力扣展示为1,null,2,3
                if (i == nodes.length) {
                    break;
                }
                String right = nodes[i++];
                if (!right.equals(NULL)) {
                    parent.right = new TreeNode(Integer.parseInt(right));
                    q.offer(parent.right);
                } else {
                    parent.right = null;
                }
            }
            return root;
        }
    }
 }
