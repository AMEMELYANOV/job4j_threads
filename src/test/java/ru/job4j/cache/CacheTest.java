package ru.job4j.cache;

import org.junit.Test;

import static org.junit.Assert.*;

public class CacheTest {

    @Test(expected = OptimisticException.class)
    public void when2SetNameAndFail() {
        Cache cache = new Cache();
        Base base = new Base(1, 0);
        cache.add(base);
        Base base1 = new Base(1, 0);
        base1.setName("User1");
        Base base2 = new Base(1, 0);
        base2.setName("User2");

        cache.update(base1);
        cache.update(base2);
    }

    @Test
    public void when2SetNameAndTrue() {
        Cache cache = new Cache();
        Base base = new Base(1, 0);
        cache.add(base);
        Base base1 = new Base(1, 0);
        base1.setName("User1");
        Base base2 = new Base(1, 1);
        base2.setName("User2");
        cache.update(base1);
        assertTrue(cache.update(base2));
    }

    @Test
    public void whenDeleteAndAdd() {
        Cache cache = new Cache();
        Base base = new Base(1, 0);
        cache.add(base);
        assertFalse(cache.add(base));
        cache.delete(base);
        assertTrue(cache.add(base));
    }
}
