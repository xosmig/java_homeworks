package com.xosmig.fastlazylist;

import java.util.*;

/**
 * It's not a true-way lazy lis
 */
public interface FastLazyList<R> extends List<R> {
    void takeAll();
    void takeTo(int index);
}
