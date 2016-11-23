package sp;

import com.sun.javafx.geom.Vec2d;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.*;

public final class SecondPartTasks {

    /*private static class IOExceptionUnchecked implements Throwable {
        private final IOException e;

        public IOExceptionUnchecked(IOException e) {
            this.e = e;
        }

        public IOException get() {
            return e;
        }
    }*/

    private SecondPartTasks() {}

    // Найти строки из переданных файлов, в которых встречается указанная подстрока.
    public static List<String> findQuotes(List<String> paths, CharSequence sequence) throws IOException {
        List<String> res = new LinkedList<>();
        for (String pathS: paths) {
            res.addAll(Files.lines(Paths.get(pathS))
                    .filter((String s) -> s.contains(sequence))
                    .collect(Collectors.toList())
            );
        }
        return res;
    }

    // В квадрат с длиной стороны 1 вписана мишень.
    // Стрелок атакует мишень и каждый раз попадает в произвольную точку квадрата.
    // Надо промоделировать этот процесс с помощью класса java.util.Random и посчитать, какова вероятность попасть в мишень.
    public static double piDividedBy4() {
        final long cnt = (long)1e6;

        Random rng = new Random();
        Vec2d target = new Vec2d(0.5, 0.5);

        // unfortunately, there is no zip method in java
        long success = rng.doubles()
                    .mapToObj(x -> new Vec2d(x, rng.nextDouble()))
                    .limit(cnt)
                    .filter(hit -> hit.distance(target) <= 0.5)
                    .count();

        return ((double) success) / ((double) cnt);
    }

    // Дано отображение из имени автора в список с содержанием его произведений.
    // Надо вычислить, чья общая длина произведений наибольшая.
    public static String findPrinter(Map<String, List<String>> compositions) {
        return compositions
                .entrySet()
                .stream()
                .max(Comparator.comparingInt(
                    entry -> entry.getValue().stream()
                            .mapToInt(String::length)
                            .sum()
                ))
                .get()
                .getKey();
    }

    // Вы крупный поставщик продуктов. Каждая торговая сеть делает вам заказ в виде Map<Товар, Количество>.
    // Необходимо вычислить, какой товар и в каком количестве надо поставить.
    public static Map<String, Integer> calculateGlobalOrder(List<Map<String, Integer>> orders) {
        return orders.stream()
                .flatMap(map -> map.entrySet().stream())
                .collect(Collectors.groupingBy(
                    Map.Entry::getKey,
                    Collectors.mapping(
                            Map.Entry::getValue,
                            Collectors.summingInt(x -> x))
                    )
                );
    }
}
