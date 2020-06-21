package io.jexxa.addendj.aspect.reflection;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import io.jexxa.addendj.aspect.valueobjects.ArrayIntTestVO;
import io.jexxa.addendj.aspect.valueobjects.BaseTestVO;
import io.jexxa.addendj.aspect.valueobjects.DerivedSameAttributeNameTestVO;
import io.jexxa.addendj.aspect.valueobjects.DerivedTestVO;
import io.jexxa.addendj.aspect.valueobjects.FloatTestVO;
import io.jexxa.addendj.aspect.valueobjects.IntTestVO;
import io.jexxa.addendj.aspect.valueobjects.MultipleDifferentTypesTestVO;
import io.jexxa.addendj.aspect.valueobjects.ThreeStringsTestVO;
import io.jexxa.addendj.aspect.valueobjects.UnorderedAttributesTestVO;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;


/**
 * Testet die Methode {@link DeepEqualsAndHashCode#deepHashCode(Object)}
 */

public class DeepToStringTest
{
    static class InternalDeepToStringTest
    {
        final String expectedString;
        final Object subject;
        final Class<? extends Throwable> expectedException;

        public InternalDeepToStringTest(final String expectedString, final Object subject, final Class<? extends Throwable> expectedException)
        {
            this.expectedString = expectedString;
            this.subject = subject;
            this.expectedException = expectedException;
        }

    }


    public static Stream<InternalDeepToStringTest> data()
    {
        return Stream.of(
                new InternalDeepToStringTest(
                        ArrayIntTestVO.class.getSimpleName() + "{a=null}",
                        new ArrayIntTestVO(null),
                        null
                ),
                new InternalDeepToStringTest(
                        ArrayIntTestVO.class.getSimpleName() + "{a=[1, 2, 3, 4, 5]}",
                        new ArrayIntTestVO(new int[]{1, 2, 3, 4, 5}),
                        IllegalArgumentException.class
                ),
                new InternalDeepToStringTest(
                        BaseTestVO.class.getSimpleName() + "{a=99}",
                        new BaseTestVO(99),
                        IllegalArgumentException.class
                ),
                new InternalDeepToStringTest(
                        DerivedSameAttributeNameTestVO.class.getSimpleName() + "{a=1414, a=9999}",
                        new DerivedSameAttributeNameTestVO(1414, 9999),
                        null
                ),
                new InternalDeepToStringTest(
                        DerivedTestVO.class.getSimpleName() + "{b=142, a=734}",
                        new DerivedTestVO(142, 734),
                        null
                ),
                new InternalDeepToStringTest(
                        FloatTestVO.class.getSimpleName() + "{a=6.022137E-23}",
                        new FloatTestVO(6.022137e-23f),
                        null
                ),
                new InternalDeepToStringTest(
                        FloatTestVO.class.getSimpleName() + "{a=0.33333334}",
                        new FloatTestVO(1.0f / 3f),
                        null
                ),
                new InternalDeepToStringTest(
                        IntTestVO.class.getSimpleName() + "{a=56134}",
                        new IntTestVO(56134),
                        null
                ),
                new InternalDeepToStringTest(
                        MultipleDifferentTypesTestVO.class.getSimpleName() + "{a=125, b=1238712387, c=2531.99, d=1234.236, e=true, f='H', g='AAA', h=127, i=200}",
                        new MultipleDifferentTypesTestVO(125, 1238712387, 2531.99f, 1234.236, true, 'H', "AAA", (byte) 127, (short) 200),
                        null
                ),
                new InternalDeepToStringTest(
                        ThreeStringsTestVO.class.getSimpleName() + "{b='asd', c='qwe', d='yxc', a=1}",
                        new ThreeStringsTestVO(1, "asd", "qwe", "yxc"),
                        null
                ),
                new InternalDeepToStringTest(
                        UnorderedAttributesTestVO.class.getSimpleName() + "{a=25555, b=13.0, c=12.0, d=7}",
                        new UnorderedAttributesTestVO(12.0f, 13.0, 25555, (byte) 7),
                        null
                )
        );
    }

    /**
     * Überprüft ob das Subjekt mittels toString in den erwarteten String transformiert wird.
     */
    @ParameterizedTest
    @MethodSource("data")
    public void test(InternalDeepToStringTest testSource)
    {
        try
        {
            assertEquals(testSource.expectedString, testSource.subject.toString());
        }
        catch (Exception t)
        {
            if (testSource.expectedException == null)
            {
                throw t;
            }
            if (testSource.expectedException != t.getClass())
            {
                throw t;
            }
        }
    }

}
