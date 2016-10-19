package com.xomig.maybe;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.function.Function;

/**
 * Represents an optional value: every Maybe is either just and contains a value, or nothing, and does not.
 * Immutable (but may contains reference to mutable data).
 */
public class Maybe<T> implements Iterable<T> {
    // Just for efficiency.
    // It is not really matter that there is no type parameter.
    public static final @NotNull Maybe NOTHING = new Maybe(null);
    private T obj;

    private Maybe(T obj) {
        this.obj = obj;
    }

    /**
     * Returns Maybe with a given value.
     */
    @NotNull
    public static <T> Maybe<T> just(T obj) {
        if (obj == null) {
            throw new IllegalArgumentException();
        }
        return new Maybe<>(obj);
    }

    /**
     * Returns an empty option. Unwrap on it will cause an exception.
     */
    @NotNull
    public static <T> Maybe<T> nothing() {
        return NOTHING;
    }

    /**
     * Check if option is not empty and returns containing object.
     */
    @NotNull
    public T get() throws UnwrapException {
        if (isNothing()) {
            throw new UnwrapException();
        }
        return obj;
    }

    /**
     * Returns true if it contains a value.
     */
    public boolean isPresent() {
        return obj != null;
    }

    /**
     * Return true if it doesn't contain a value.
     */
    public boolean isNothing() {
        return obj == null;
    }

    /**
     * Applies a given function to containing value and returns a new option, which contains a result.
     */
    @NotNull
    public <R> Maybe<R> map(@NotNull Function<? super T, ? extends R> func) {
        return isNothing() ? nothing() : just(func.apply(getUnchecked()));
    }

    @Nullable
    private T getUnchecked() {
        return obj;
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new Iter();
    }

    /**
     * Supposed to be thrown when get is called on nothing.
     */
    public static class UnwrapException extends RuntimeException {}

    private class Iter implements Iterator<T> {
        @Override
        public boolean hasNext() {
            return obj != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new IllegalStateException();
            }
            return obj;
        }
    }
}
