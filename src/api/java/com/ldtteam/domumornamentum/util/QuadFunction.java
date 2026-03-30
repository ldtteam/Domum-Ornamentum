package com.ldtteam.domumornamentum.util;

/**
 * A {@link FunctionalInterface callback} which can get invoked from a lambda that takes four parameters and returns a result.
 *
 * @param <T1> The type of the first parameter.
 * @param <T2> The type of the second parameter.
 * @param <T3> The type of the third parameter.
 * @param <T4> The type of the fourth parameter.
 * @param <TResult> The type of the result.
 */
@FunctionalInterface
public interface QuadFunction<T1, T2, T3, T4, TResult>
{
    /**
     * Invokes the given function that this callback represents.
     *
     * @param one The first argument
     * @param two The second argument
     * @param three The third argument
     * @param four The fourth argument
     * @return The return value of the function.
     */
    TResult apply(T1 one, T2 two, T3 three, T4 four);
}
