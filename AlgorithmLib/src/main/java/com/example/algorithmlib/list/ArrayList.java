package com.example.algorithmlib.list;

import java.util.Iterator;

/**
 * 支持动态扩容的数组
 */
public class ArrayList<E> implements Collection<E> {
    private static final int DEFAULT_CAPACITY = 10;
    private E[] mElements;
    public ArrayList() {
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public boolean add(E e) {
        return true;
    }

    @Override
    public boolean add(E e, int index) {
        return false;
    }

    @Override
    public boolean remove(E e) {
        return false;
    }

    @Override
    public boolean remove(E e, int index) {
        return false;
    }

    @Override
    public boolean set(E e, int index) {
        return false;
    }

    @Override
    public E get(int index) {
        return null;
    }
}
