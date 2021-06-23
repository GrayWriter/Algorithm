package com.example.algorithmlib.list;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import javax.swing.text.html.HTMLDocument;

/**
 * 支持动态扩容的数组
 */
public class ArrayList<E> implements Collection<E> {
    private static final int DEFAULT_CAPACITY = 10;
    /**
     * The maximum size of array to allocate.
     * Some VMs reserve some header words in an array.
     * Attempts to allocate larger arrays may result in
     * OutOfMemoryError: Requested array size exceeds VM limit
     * 留出Java对象头存储数据
     */
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
    private static final Object[] EMPTY = {};
    private Object[] mElements;
    private int mSize;
    private int mModCount;

    public ArrayList() {
        mElements = EMPTY;
    }

    public ArrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            mElements = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            mElements = EMPTY;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        }
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public boolean add(E e) {
        ensureCapacity(mSize + 1);
        mElements[mSize++] = e;
        return true;
    }

    @Override
    public boolean add(E e, int index) {
        //扩容
        ensureCapacity(mSize + 1);
        //数据搬移
        System.arraycopy(mElements, index, mElements, index + 1, mSize - index);
        mElements[index] = e;
        mSize++;
        return true;
    }

    private void ensureCapacity(int minCapacity){
        mModCount++;

        if (mElements == EMPTY) {
            minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
        }

        if (mElements.length - minCapacity < 0) {
            grow(minCapacity);
        }
    }

    /**
     * 扩容，加入了溢出考虑
     * @param minCapacity
     */
    private void  grow(int minCapacity) {
      int oldCapacity = mElements.length;
      //overflow-conscious code
      int newCapacity = oldCapacity + (oldCapacity >>1);
      //扩容值小于需求值
      if (newCapacity - minCapacity < 0 ) {
          newCapacity = minCapacity;
      }

      //扩容值大于限定值的情况处理
      if (newCapacity - MAX_ARRAY_SIZE > 0) {
          newCapacity = hugeCapacity(minCapacity);
      }

      mElements = Arrays.copyOf(mElements, newCapacity);
    }

    /**
     *需求数字溢出抛异常，大于限定值扩容到最大，否则直接扩容到限定值
     * @param minCapacity
     * @return
     */
    private int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) {
            throw new OutOfMemoryError();
        }

        return minCapacity > MAX_ARRAY_SIZE ? Integer.MAX_VALUE : MAX_ARRAY_SIZE;
    }

    /**
     * 删除是比值
     * @param e
     * @return
     */
    @Override
    public boolean remove(E e) {
        if (e == null) {
            for (int index = 0; index < mSize; index++)
                if (mElements[index] == null) {
                    remove(index);
                    return true;
                }
        } else {
            for (int index = 0; index < mSize; index++)
                if (e.equals(mElements[index])) {
                   remove(index);
                    return true;
                }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E remove(int index) {
        mModCount++;
        E oldValue = (E) mElements[index];
        if (index == mSize - 1) {
            mElements[index] = null;
        } else {
            System.arraycopy(mElements, index + 1, mElements, index, mSize - index - 1);
            mElements[mSize - 1] = null;
        }
        mSize--;
        return oldValue;
    }

    @Override
    public boolean set(E e, int index) {
        mModCount++;
        mElements[index] = e;
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E get(int index) {
        return (E) mElements[index];
    }

    /**
     * Returns the number of elements in this list.
     *
     * @return the number of elements in this list
     */
    public int size() {
        return mSize;
    }

    private class Itr implements Iterator<E>  {
        private int cursor;
        private int expectModCount = mModCount;
        int lastRet = -1;
        private int limit = ArrayList.this.mSize;

        @Override
        public boolean hasNext() {
            return cursor < limit;
        }

        @SuppressWarnings("unchecked")
        @Override
        public E next() {
            //fast-fail
            int i = cursor;
            if (i >= limit)
                throw new NoSuchElementException();
            Object[] elementData = mElements;
            if (i >= elementData.length)
                throw new ConcurrentModificationException();
            cursor = i + 1;
            return (E) elementData[lastRet = i];

        }

        @Override
        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();
            if (mModCount != expectModCount)
                throw new ConcurrentModificationException();

            try {
                ArrayList.this.remove(lastRet);
                cursor = lastRet;
                lastRet = -1;
                expectModCount = mModCount;
                limit--;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }
    }
}
