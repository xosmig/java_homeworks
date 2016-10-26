package mcollections;

import com.xosmig.functional.Function1;
import com.xosmig.functional.Predicate1;

import java.util.*;

public interface LazyList<R> extends List<R> {
    void takeAll();
    void takeTo(int index);
}


/*package*/ abstract class LazyListImpl<T, R> extends AbstractList<R> implements LazyList<R> {
    private List<R> realList = new LinkedList<>();
    protected Iterator<? extends T> it;

    public LazyListImpl(Iterator<? extends T> it) {
        this.it = it;
    }

    @Override
    public int size() {
        takeAll();
        return realList.size();
    }

    @Override
    public R get(int index) {
        takeTo(index);
        return realList.get(index);
    }

    @Override
    public R set(int index, R element) {
        takeTo(index);
        return realList.set(index, element);
    }

    @Override
    public void takeTo(int index) {
        while (realList.size() < index + 1) {
            Optional<R> e = getNext();
            if (e.isPresent()) {
                realList.add(e.get());
            } else {
                throw new IndexOutOfBoundsException();
            }
        }
    }

    @Override
    public void takeAll() {
        while (true) {
            Optional<R> x = getNext();
            if (x.isPresent()) {
                realList.add(x.get());
            } else {
                break;
            }
        }
    }



    public abstract Optional<R> getNext();
}


class MapList<T, R> extends LazyListImpl<T, R> {
    private Function1<T, ? extends R> f;

    public MapList(Function1<T, ? extends R> f, Iterator<? extends T> it) {
        super(it);
        this.f = f;
    }

    @Override
    public Optional<R> getNext() {
        if (it.hasNext()) {
            return Optional.of(f.apply(it.next()));
        } else {
            return Optional.empty();
        }
    }
}


class FilterList<T> extends LazyListImpl<T, T> {
    private Predicate1<T> p;

    public FilterList(Predicate1<T> p, Iterator<? extends T> it) {
        super(it);
        this.p = p;
    }

    @Override
    public Optional<T> getNext() {
        while (it.hasNext()) {
            T ret = it.next();
            if (p.apply(ret)) {
                return Optional.of(ret);
            }
        }
        return Optional.empty();
    }
}

class TakeWhileList<T> extends LazyListImpl<T, T> {
    private Predicate1<T> p;

    public TakeWhileList(Predicate1<T> p, Iterator<? extends T> it) {
        super(it);
        this.p = p;
    }

    @Override
    public Optional<T> getNext() {
        if (it.hasNext()) {
            T x = it.next();
            if (p.apply(x)) {
                it = Collections.emptyIterator();
                return Optional.empty();
            }
            return Optional.of(x);
        } else {
            return Optional.empty();
        }
    }
}
