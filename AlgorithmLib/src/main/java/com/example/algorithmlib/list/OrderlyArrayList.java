package com.example.algorithmlib.list;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

/**
 * 固定大小的有序数据
 * @param <E>
 */
public class OrderlyArrayList<E extends Comparable<E>> implements Collection<E> {
    private Object[] mElements;
    private int mSize;
    private Comparator mComparator = new MyComparator();

    public OrderlyArrayList(int capacity) {
        mElements = new Object[capacity];
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public boolean add(E e) {
        mElements[mSize++] = e;
        sort();
        return true;
    }

    @Override
    public boolean add(E e, int index) {
        System.arraycopy(mElements, index, mElements, index +1, mSize - index);
        mElements[index] = e;
        mSize++;
        sort();
        return true;
    }

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
        Object oldValue = mElements[index];
        int numMoved = mSize - index - 1;
        if (numMoved > 0) {
            System.arraycopy(mElements, index + 1, mElements, index, numMoved);
        }
        mElements[--mSize] = null;
        sort();
        return (E)oldValue;
    }

    @Override
    public boolean set(E e, int index) {
        mElements[index] = e;
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E get(int index) {
        return (E)mElements[index];
    }

    @SuppressWarnings("unchecked")
    private void sort() {
        Arrays.sort(mElements, mComparator);
    }

    private  class MyComparator implements Comparator {
        @Override
        public int compare(Object o1, Object o2) {
            if (o1 == null && o2 == null) {
                return 0;
            }

            if (o1 == null) {
                return 1;
            }

            if (o2 == null) {
                return 1;
            }
            return ((E)o1).compareTo((E)o2);
        }
    }

    public void printf() {
        System.out.println("start");
        for (Object e : mElements) {
            System.out.print(e+",");
        }
        System.out.println();
    }




}
