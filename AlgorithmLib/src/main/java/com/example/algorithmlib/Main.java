package com.example.algorithmlib;


import com.example.algorithmlib.tree.TreeCodec;
import com.example.algorithmlib.tree.TreeCodecFactory;
import com.example.algorithmlib.tree.TreeNode;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class Main {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(-5);
        root.right = new TreeNode(-3);

        TreeCodec codec = TreeCodecFactory.getCodec(TreeCodecFactory.LEVEL);
        String a = codec.serialize(root);
        TreeNode node = codec.deserialize("2,null,3,null,4,null,5,null,6");
    }


    static class Solution {
        public int maxSumBST(TreeNode root) {
            return maxSumBSTHelper(root).sum;
        }

        private Result maxSumBSTHelper(TreeNode root) {
            //base case
            if (root == null) {
                Result result = new Result();
                result.isBST = true;
                return result;
            }

            Result left = maxSumBSTHelper(root.left);
            Result right = maxSumBSTHelper(root.right);

            //case1. 左右子树都是BST
            if (left.isBST && right.isBST && isBST(root)) {
                int rootSum = left.sum + right.sum + root.val;
                Result result = new Result();
                result.isBST = true;
                result.treeNode = root;
                result.sum = Math.max(left.sum, Math.max(rootSum, right.sum));

                return result;
            }

            Result result = new Result();
            result.isBST = false;
            result.treeNode = root;
            result.sum = Math.max(left.sum, right.sum);

            return result;


        }

        private boolean isBST(TreeNode root) {
            if (root == null) {
                return false;
            }

            if (root.left == null && root.right == null) {
                return true;
            } else if (root.left == null) {
                return root.val < root.right.val;
            } else if (root.right == null) {
                return root.val > root.left.val;
            } else {
                return root.val > root.left.val && root.val < root.right.val;
            }
        }

        private static class Result {
            int sum;
            boolean isBST;
            TreeNode treeNode;
        }


    }


}
