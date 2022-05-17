package com.example.algorithmlib.tree;

/**
 * LetCode树的定义，方便调试
 */
public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;

    public TreeNode() {
    }

    public TreeNode(int val) {
        this.val = val;
    }

    public TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    /**
     *
     * @param data eg. "4,3,null,1,2"
     * @return
     */
    public static TreeNode fromLevel(String data) {
        return TreeCodecFactory.getCodec(TreeCodecFactory.LEVEL).deserialize(data);
    }

    public static TreeNode fromPre(String data) {
        return TreeCodecFactory.getCodec(TreeCodecFactory.PRE).deserialize(data);
    }

    public static TreeNode fromPost(String data) {
        return TreeCodecFactory.getCodec(TreeCodecFactory.POST).deserialize(data);
    }
}
