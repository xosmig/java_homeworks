package com.xosmig.serialization;


/*package*/ final class TestClassB {
    TestClassB b1;
    int i1;


    public TestClassB() {}

    public TestClassB(TestClassB b1, int i1) {
        this.b1 = b1;
        this.i1 = i1;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestClassB)) return false;

        TestClassB that = (TestClassB) o;

        if (i1 != that.i1) return false;
        return b1 != null ? b1.equals(that.b1) : that.b1 == null;

    }

    @Override
    public int hashCode() {
        int result = b1 != null ? b1.hashCode() : 0;
        result = 31 * result + i1;
        return result;
    }
}
