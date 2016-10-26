package com.xosmig.fastlazylist;

import java.util.*;

public interface FastLazyList<R> extends List<R> {
    void takeAll();
    void takeTo(int index);
}
