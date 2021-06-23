package com.example.algorithmlib.tree;

/**
 * 二叉树的序列化与反序列化的编解码器
 */
public interface TreeCodec {
    String serialize(TreeNode root);
    TreeNode deserialize(String data);
}
