package com.example.algorithmlib.list;

import com.example.algorithmlib.tree.TreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Sorter {
    private static final int[] TEST_DATA = new int[] {6,5,4,4,3,2,1,0};
    public static int[] getTestData() {
        return TEST_DATA;
    }

    public static void bubbleSort(int[] array) {
        if (array == null || array.length <= 1) {
            return;
        }

        for (int i = 0; i < array.length; i++) {
            boolean swapFlag = false;
            for (int j = 0; j < array.length - 1- i; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    swapFlag = true;
                }
            }
            if (!swapFlag) {
                break;
            }
        }
    }

    /**
     *
     * @param a
     * @param start the start index include
     * @param end the end index exclude
     */
    public static void bubbleRecursion(int[] a, int start , int end) {
        if (end <=1) {
            return;
        }

        for (int i = 0; i < end - 1; i++) {
            if (a[i] > a[i + 1]) {
                int temp = a[i];
                a[i] = a[i + 1];
                a[i + 1] = temp;
            }
        }

        bubbleRecursion(a, 0, end - 1);
    }

    public static void insertionSort(int[] array) {
        if (array == null || array.length <= 1) {
            return;
        }
        for (int i = 1; i < array.length; i++) {
            int j = i - 1;
            int temp = array[i];
            //逆序交換确定插入位置并交换
            for (; j >= 0 ; j--) {
                if (array[j] > temp) {
                    //数据搬移
                    array[j+1] = array[j];
                } else {
                    break;
                }
            }
            //插入数据
            array[j+1] = temp;
        }
    }

    public static void selectionSort(int[] array) {
        if (array == null || array.length <=1) {
            return;
        }
        //找到未排序区间的最小数
        for (int i = 0; i < array.length; i++) {
            int minIndex = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < array[minIndex]) {
                    minIndex = j;
                }
            }

            //swap
            int temp = array[i];
            array[i] = array[minIndex];
            array[minIndex] = temp;
        }
    }

    /**归并排序**/
    public static void mergeSort(int[] array) {
        if (array == null || array.length <= 1) {
            return;
        }
        mergeSortInternal(array, 0, array.length - 1);
    }

    private static void mergeSortInternal(int[] array, int start, int end) {
        if (start >= end) {
            return;
        }

        int pivot = (start + end) / 2;
        mergeSortInternal(array, start, pivot);
        mergeSortInternal(array, pivot + 1, end);
        merge(array, start, pivot, end);
    }

    /***
     * 双指针合并二个有序数组，可参考LeetCode88题解解析
     * @param array
     * @param left
     * @param pivot
     * @param right
     */
    private static void merge(int[] array, int left, int pivot, int right) {
        int[] temp = new int[right - left + 1];
        int lp = left;
        int rp = pivot + 1;
        int p = 0;

        while (lp <= pivot ||  rp <= right) {
            if (lp > pivot) {
                temp[p++] = array[rp++];
            } else if ( rp > right) {
                temp[p++] = array[lp++];
            } else if (array[lp] <= array[rp]) {
                temp[p++] = array[lp++];
            } else {
                temp[p++] = array[rp++];
            }
        }

        //copy temp to array[left, right]
        System.arraycopy(temp, 0, array, left, right - left + 1 );
    }


    public static void quickSort(int[] array) {
        if (array == null || array.length <= 1) {
            return;
        }

        quickSortInternal(array, 0, array.length - 1);
    }

    private static void quickSortInternal(int[] array, int start, int end) {
        if (start >= end) {
            return;
        }

        int pivot = partition(array, start, end);
        quickSortInternal(array, start, pivot - 1);
        quickSortInternal(array, pivot + 1, end);
    }

    private static int partition(int[] array , int start, int end) {
        int pivotValue = array[end];
        int i = start;

        for (int j = start; j < end; j++) {
            if (array[j] < pivotValue) {
                //已经有序，不需要交换
                if (i == j) {
                    i++;
                } else {
                    swap(array, i, j);
                    i++;
                }
            }
        }

        //插入中间节点
        swap(array, i, end);
        return i;
    }

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    /***
     * 二分查找迭代实现
     * @param array
     * @param target
     * @return
     */
    public static int binarySearch(int[] array, int target) {
     return binarySearch(array, 0, array.length -1, target);
    }

    /**
     *
     * @param array
     * @param fromIndex include
     * @param toIndex  include
     * @param target
     * @return
     */
    public static int binarySearch(int[] array, int fromIndex, int toIndex, int target) {
        int low = fromIndex;
        int high = toIndex;

        while(low <= high) {
            int mid  = low + ((high - low) >> 1);
            if (array[mid] > target) {
                high = mid - 1;
            } else if (array[mid] < target) {
                low = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    /**
     * @see Arrays#binarySearch(int[], int)
     * @param a
     * @param fromIndex
     * @param toIndex
     * @param key
     * @return 如果返回值为负数，再将返回值按位取反，得到的便是插入位置，>= 0 表示找到了
     */
    public static int binarySearch0(int[] a, int fromIndex, int toIndex, int key) {
        int low = fromIndex;
        int high = toIndex - 1;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            int midVal = a[mid];

            if (midVal < key)
                low = mid + 1;
            else if (midVal > key)
                high = mid - 1;
            else
                return mid; // key found
        }
        return ~low;  // key not found. means -(low + 1)
    }



    /**
     * 二分查找递归实现
     * @param array
     * @param target
     * @return
     */
    public static int binarySearchR(int[] array, int target) {
        return binarySearchInternal(array, 0 , array.length - 1, target);
    }

    private static int binarySearchInternal(int[] array, int low , int high, int target) {
        if (low > high) {
            return  -1;
        }

        int mid = low  + ((high - low) >> 1);

        if (array[mid] > target) {
            return binarySearchInternal(array, low, high - 1, target);
        } else if (array[mid] < target) {
            return binarySearchInternal(array, low + 1, high, target);
        } else {
            return mid;
        }
    }

    /**
     * 寻找左边界，找到的是第一个target
     * @param nums
     * @param target
     * @return 表示数组中小于target的数有几个，正数表示找到了，负数和0表示没找到
     */
    public static int leftBound(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while(left <= right) {
            int mid = (left + right) >>> 1;
            //isBlue二分条件,根据当前的搜索区间，中间值，目标值，确定二分条件
            if(nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
          //不做处理，返回值表示当前小于target的数有几个
//        if (left >= nums.length || nums[left] != target) {
//            return -1 ; //not found
//        }

        // 检查越界情况，无非三种情况，最终搜索区间为数组第一个元素或者为数组最后一个元素，搜索区间位于
        // 数组中间的情况不存在越界
//        if (left >= nums.length) {
//            return ~left; //not found, means -(left + 1)
//        }

        return left;
    }

    /**
     * 寻找右边界，找到的是最后一个target
     * 与STD库函数不一样
     * @param nums
     * @param target
     * @return
     */
    public static int rightBound(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while(left <= right) {
            int mid = (left + right) >>> 1;
            //isBlue二分条件,根据当前的搜索区间，中间值，目标值，确定二分条件
            if(nums[mid] <= target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        if(right < 0 || nums[right] != target) {
            return -1;
        }
        return right;
    }

    /**
     * 二分查找变种问题：
     * 找到有序数组中的第一个目标值得位置
     * eg. [1,2,2,3] 返回索引1
     * @param array
     * @param target
     * @return
     */
    public static int findFirst(int[] array, int target) {
        int low  = 0;
        int high = array.length - 1;

        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            if (array[mid] > target) {
                high = mid - 1;
            } else if (array[mid] < target) {
                low =  mid + 1;
            } else {
                //关键点:命中，且前一个值不等于target，则mid为第一个目标值
                if (mid == 0 || array[mid - 1] != target) {
                    return mid;
                }

                high = mid - 1;
            }
        }

        return -1;
    }

    /**
     * 二分查找变种问题：
     * 找到有序数组中的最后一个目标值得位置
     * eg. [1,2,2,3] 返回索引2
     * @param array
     * @param target
     * @return
     */
    public static int findLast(int[] array, int target) {
        int low  = 0;
        int high = array.length - 1;

        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            if (array[mid] > target) {
                high = mid - 1;
            } else if (array[mid] < target) {
                low =  mid + 1;
            } else {
                //关键点:命中，且后一个值不等于target，则mid为最后一个目标值
                if (mid == array.length - 1|| array[mid + 1] != target) {
                    return mid;
                }
                low = mid + 1;
            }
        }

        return -1;
    }

    /**
     * 二分查找变种问题：
     * 查找第一个大于等于给定值的元素
     * eg. [3, 6, 9, 12] target 4 返回 1
     * @param array
     * @param target
     * @return
     */

    public static int findFirstGreaterThan(int[] array, int target) {
        int low  = 0;
        int high = array.length - 1;

        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            if (array[mid] >= target) {
                //等于的情況和大于相同，整合
                if (mid == 0 || array[mid - 1] < target) {
                    return mid;
                }
                high = mid - 1;
            } else{
                low =  mid + 1;
            }
        }
        return -1;
    }

    /**
     * 二分查找变种问题：
     * 查找最后一个小于等于给定值的元素
     * eg. [3, 6, 9, 12] target 4 返回 1
     * @param array
     * @param target
     * @return
     */

    public static int findLastGreaterThan(int[] array, int target) {
        int low = 0;
        int high = array.length - 1;

        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            if (array[mid] <= target) {
                if (mid == array.length - 1 || array[mid + 1] > target) {
                    return mid;
                }
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }





    /**
     * 大数减法运算，a >= b;
     * @param a
     * @param b
     * @return
     */
    public static String sub(String a, String b) {
        StringBuilder sb = new StringBuilder(a.length());
        char[] aChars= a.toCharArray();
        char[] bChars = b.toCharArray();
        int indexA = a.length();
        int indexB = b.length();
        for (int i = 0; i < b.length(); i++) {
            int numA = aChars[--indexA] - '0';
            int numB = bChars[--indexB] - '0';

            int result = numA - numB;
            if (result < 0) {
                aChars[indexA - 1] -= 1;
                result += 10;
            }
            sb.append(result);
        }

        //處理剩餘部分
        indexA--;
        for ( ; indexA>= 0; indexA--) {
            sb.append(aChars[indexA]);
        }

        //處理前導0
        for (int j = sb.length() - 1; j >= 1; j--) {
            if (sb.charAt(j) == '0') {
                sb.deleteCharAt(j);
            } else {
                break;
            }
        }

        return sb.reverse().toString();
    }


    public static int bfs(TreeNode root) {
        if (root == null) {
            return 0;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int depth = 1;

        while (!queue.isEmpty()) {
            int size = queue.size();
            //将当前队列里面的所有数据向外扩散
            //当前队列里面的所有数据，就是本层的所有数据，所以又称层次遍历
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();

                //find the target
                if (node.left == null && node.right == null) {
                    return depth;
                }

                //扩散
                if (node.left != null) {
                    queue.add(node.left);
                }

                if (node.right != null) {
                    queue.add(node.right);
                }
            }


            //步数+1,下一层扩散就绪
            depth++;
        }

        return depth;
    }

    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> ans = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        if (root != null) {
            queue.offer(root);
        }


        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> level = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                level.add(node.val);
                //扩散
                if (node.left != null) {
                    queue.offer(node.left);
                }



                if (node.right != null) {
                    queue.offer(node.right);
                }

            }

            ans.add(level);
        }

        return ans;

    }

    public TreeNode buildTree(int[] preorder, int[] inorder) {
      return   buildTreeInternal(preorder, inorder,0, preorder.length - 1, 0);
    }

    public TreeNode buildTreeInternal(int[] preorder, int[] inorder, int start, int end, int preIndex) {
            if (start > end) {
                return null;
            }
            int val = preorder[preIndex];
            int rootIndex = findRoot(inorder,start, end, val );
            preIndex ++;
            TreeNode left = buildTreeInternal(preorder, inorder, start, rootIndex - 1,preIndex);
            TreeNode right = buildTreeInternal(preorder, inorder, rootIndex + 1, end, preIndex);

            TreeNode root = new TreeNode(val);
            root.left = left;
            root.right = right;

            return root;

    }

    private int findRoot(int[] inorder,int start, int end, int val) {
        for (int i = start; i <= end; i++) {
            if (val == inorder[i]) {
                return i;
            }
        }

        return  -1;
    }





}
