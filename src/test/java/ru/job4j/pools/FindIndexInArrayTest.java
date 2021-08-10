package ru.job4j.pools;

import org.junit.Test;

import java.util.concurrent.ForkJoinPool;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class FindIndexInArrayTest {
    static String[] stringArray = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l"};
    public static Integer[] getInitArray(int capacity) {
        Integer[] array = new Integer[capacity];
        for (int i = 0; i < capacity; i++) {
            array[i] = i;
        }
        return array;
    }

    @Test
    public void whenObjectInFirstHalfAndInteger() {
        Integer[] array = getInitArray(100);
        int searchObject = 15;
        FindIndexInArray<Integer> findIndexInArray = new FindIndexInArray<>(array, searchObject);
        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        int expected = 15;
        assertThat(expected, is(pool.invoke(findIndexInArray)));
    }

    @Test
    public void whenObjectInSecondHalfAndInteger() {
        Integer[] array = getInitArray(100);
        int searchObject = 95;
        FindIndexInArray<Integer> findIndexInArray = new FindIndexInArray<>(array, searchObject);
        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        int expected = 95;
        assertThat(expected, is(pool.invoke(findIndexInArray)));
    }

    @Test
    public void whenObjectInFirstHalfAndString() {
        String searchObject = "c";
        FindIndexInArray<String> findIndexInArray = new FindIndexInArray<>(stringArray, searchObject);
        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        int expected = 2;
        assertThat(expected, is(pool.invoke(findIndexInArray)));
    }

    @Test
    public void whenObjectInSecondHalfAndString() {
        String searchObject = "k";
        FindIndexInArray<String> findIndexInArray = new FindIndexInArray<>(stringArray, searchObject);
        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        int expected = 10;
        assertThat(expected, is(pool.invoke(findIndexInArray)));
    }

    @Test
    public void whenObjectNotFound() {
        String searchObject = "z";
        FindIndexInArray<String> findIndexInArray = new FindIndexInArray<>(stringArray, searchObject);
        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        int expected = -1;
        assertThat(expected, is(pool.invoke(findIndexInArray)));
    }
}
