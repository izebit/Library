package ru.izebit.structs;


import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MonadTest {

    /**
     * pure(v).bind(f) = pure(f.apply(v))
     */
    @Test
    public void leftIdentityTest() {
        String value = "hello world";

        Function<String, Monad<Integer>> f = str -> Monad.pure(str.length());
        assertEquals(Monad.pure(value).bind(f), Monad.pure(f.apply(value)));
    }

    /**
     * pure(v).bind(Monad::pure) = pure(v)
     */
    @Test
    public void rightIdentityTest() {
        String value = "hello world";

        assertEquals(Monad.pure(value).bind(Monad::pure), Monad.pure(value));
    }

    /**
     * pure(v).bind(f).bind(g) = pure(v).bind(v->f.apply(v).bind(g))
     */
    @Test
    public void associativityTest() {
        String value = "hello world";

        Function<String, Monad<Integer>> f = str -> Monad.pure(str.length());
        Function<Integer, Monad<String>> g = number -> Monad.pure(number.toString());

        assertEquals(Monad.pure(value).bind(f).bind(g), Monad.pure(value).bind(v -> f.apply(v).bind(g)));
    }

    @Test
    public void createLazyMonadTest() throws Exception {
        List<Integer> steps = new ArrayList<>();

        Monad<String> monad = Monad.pure(() -> {
            steps.add(1);
            return "monad";
        });
        monad = monad
                .bind(str -> {
                    steps.add(2);
                    return Monad.pure(str.length());
                })
                .bind(num -> {
                    steps.add(3);
                    return Monad.pure(num.toString());
                });

        steps.add(0);
        monad.get();

        for (int i = 1; i < steps.size(); i++)
            assertTrue(steps.get(i - 1) < steps.get(i));
    }
}
