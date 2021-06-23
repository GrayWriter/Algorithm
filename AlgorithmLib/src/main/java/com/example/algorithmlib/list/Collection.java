package com.example.algorithmlib.list;

import java.util.Iterator;

/**
 * 简易抽象，ADT的基本操作无非就是遍历与访问(CRUD)
 *
 * JDK抽象太细致了
 * @see java.util.Collection
 * @see Iterable
 */
public interface Collection<E>  {
    Iterator<E> iterator();

    boolean add(E e);
    boolean add(E e, int index);

    boolean remove(E e);
    E remove(int index);

    boolean set(E e, int index);
    E get(int index);
}
