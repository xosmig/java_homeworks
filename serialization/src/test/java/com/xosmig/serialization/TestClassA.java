package com.xosmig.serialization;


import java.util.Date;

public class TestClassA {
    private int i1;
    private final double d1;
    private String s1;
    protected TestClassB b1;


    public TestClassA() {
        this.d1 = 0;
    }

    public TestClassA(int i1, double d1, TestClassB b1, String s1) {
        this.i1 = i1;
        this.d1 = d1;
        this.b1 = b1;
        this.s1 = s1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestClassA)) return false;

        TestClassA that = (TestClassA) o;

        if (i1 != that.i1) return false;
        if (Double.compare(that.d1, d1) != 0) return false;
        if (b1 != null ? !b1.equals(that.b1) : that.b1 != null) return false;
        return s1 != null ? s1.equals(that.s1) : that.s1 == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = i1;
        temp = Double.doubleToLongBits(d1);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (b1 != null ? b1.hashCode() : 0);
        result = 31 * result + (s1 != null ? s1.hashCode() : 0);
        return result;
    }
}
