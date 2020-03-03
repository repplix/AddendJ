package io.ddd.aspect;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import io.ddd.aspect.reflection.FieldIsArrayException;
import io.ddd.aspect.reflection.DeepEqualsAndHashCode;
import io.ddd.aspect.valueobjects.ArrayIntTestVO;
import io.ddd.aspect.valueobjects.BaseTestVO;
import io.ddd.aspect.valueobjects.DerivedSameAttributeNameTestVO;
import io.ddd.aspect.valueobjects.DerivedTestVO;
import io.ddd.aspect.valueobjects.FloatTestVO;
import io.ddd.aspect.valueobjects.IntTestVO;
import io.ddd.aspect.valueobjects.MultipleDifferentTypesTestVO;
import io.ddd.aspect.valueobjects.ThreeStringsTestVO;
import io.ddd.aspect.valueobjects.UnorderedAttributesTestVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;



/**
 * Testet die Methode {@link DeepEqualsAndHashCode#reflectiveHashCode(Object)}
 */

@RunWith(Parameterized.class)
public class ReflectiveToStringTest
{
    private final String expectedString;
    private final Object subject;
    private final Class<? extends Throwable> expectedException;

    /**
     * Konstruktor.
     *
     * @param expectedString    Der String, der das Subjekt repräsentieren soll.
     * @param subject           Ein Objekt, welches getestet wird.
     * @param expectedException Eine EXception, die werwartet wird oder null.
     */
    public ReflectiveToStringTest(final String expectedString, final Object subject, final Class<? extends Throwable> expectedException)
    {
        this.expectedString = expectedString;
        this.subject = subject;
        this.expectedException = expectedException;
    }

    /**
     * Datenquelle für parameterisierte tests. Arrays bestehen aus: dem erwarteten String, einem zu testenden Objekt und ggf.
     * der Exception, die erwartet ist. In dieser reihenfolge.
     *
     * @return Eine Liste von Datensätzen, die getestet werden.
     */
    @Parameterized.Parameters
    public static Iterable<? extends Object[]> data()
    {
        return Arrays.asList(

                new Object[]{
                        ArrayIntTestVO.class.getSimpleName() + "{a=null}",
                        new ArrayIntTestVO(null),
                        null
                },
                new Object[]{
                        ArrayIntTestVO.class.getSimpleName() + "{a=[1, 2, 3, 4, 5]}",
                        new ArrayIntTestVO(new int[]{1, 2, 3, 4, 5}),
                        FieldIsArrayException.class
                },
                new Object[]{
                        BaseTestVO.class.getSimpleName() + "{a=99}",
                        new BaseTestVO(99),
                        FieldIsArrayException.class
                },
                new Object[]{
                        DerivedSameAttributeNameTestVO.class.getSimpleName() + "{a=1414, a=9999}",
                        new DerivedSameAttributeNameTestVO(1414, 9999),
                        null
                },
                new Object[]{
                        DerivedTestVO.class.getSimpleName() + "{b=142, a=734}",
                        new DerivedTestVO(142, 734),
                        null
                },
                new Object[]{
                        FloatTestVO.class.getSimpleName() + "{a=6.022137E-23}",
                        new FloatTestVO(6.022137e-23f),
                        null
                },
                new Object[]{
                        FloatTestVO.class.getSimpleName() + "{a=0.33333334}",
                        new FloatTestVO(1.0f / 3f),
                        null
                },
                new Object[]{
                        IntTestVO.class.getSimpleName() + "{a=56134}",
                        new IntTestVO(56134),
                        null
                },
                new Object[]{
                        MultipleDifferentTypesTestVO.class.getSimpleName() + "{a=125, b=1238712387, c=2531.99, d=1234.236, e=true, f='H', g='AAA', h=127, i=200}",
                        new MultipleDifferentTypesTestVO(125, 1238712387, 2531.99f, 1234.236, true, 'H', "AAA", (byte) 127, (short) 200),
                        null
                },
                new Object[]{
                        ThreeStringsTestVO.class.getSimpleName() + "{b='asd', c='qwe', d='yxc', a=1}",
                        new ThreeStringsTestVO(1, "asd", "qwe", "yxc"),
                        null
                },
                new Object[]{
                        UnorderedAttributesTestVO.class.getSimpleName() + "{a=25555, b=13.0, c=12.0, d=7}",
                        new UnorderedAttributesTestVO(12.0f, 13.0, 25555, (byte) 7),
                        null
                }
        );
    }

    /**
     * Überprüft ob das Subjekt mittels toString in den erwarteten String transformiert wird.
     */
    @Test
    public void test()
    {
        try
        {
            assertEquals(expectedString, subject.toString());
        }
        catch (Throwable t)
        {
            if (expectedException == null)
            {
                throw t;
            }
            if (expectedException != t.getClass())
            {
                throw t;
            }
        }
    }
}
