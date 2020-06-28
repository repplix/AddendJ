package io.jexxa.addendj.domain.object;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import io.jexxa.addendj.domain.valueobject.ArrayIntTestVO;
import io.jexxa.addendj.domain.valueobject.BaseValueObject;
import io.jexxa.addendj.domain.valueobject.DerivedValueObjectWithSameFieldNames;
import io.jexxa.addendj.domain.valueobject.DerivedValueObject;
import io.jexxa.addendj.domain.valueobject.PrimitiveDataTypeValueObject;
import io.jexxa.addendj.domain.valueobject.ThreeStringsValueObject;
import io.jexxa.addendj.domain.valueobject.UnorderedAttributesTestVO;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;


/**
 * Testet die Methode {@link DeepEqualsAndHashCode#deepHashCode(Object)}
 */

class DeepToStringTest
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


    static Stream<InternalDeepToStringTest> data()
    {
        return Stream.of(
                new InternalDeepToStringTest(
                        ArrayIntTestVO.class.getSimpleName() + "{value=null}",
                        new ArrayIntTestVO(null),
                        null
                ),
                new InternalDeepToStringTest(
                        ArrayIntTestVO.class.getSimpleName() + "{value=[1, 2, 3, 4, 5]}",
                        new ArrayIntTestVO(new int[]{1, 2, 3, 4, 5}),
                        IllegalArgumentException.class
                ),
                new InternalDeepToStringTest(
                        BaseValueObject.class.getSimpleName() + "{value=99}",
                        new BaseValueObject(99),
                        IllegalArgumentException.class
                ),
                new InternalDeepToStringTest(
                        DerivedValueObjectWithSameFieldNames.class.getSimpleName() + "{value=1414, value=9999}",
                        new DerivedValueObjectWithSameFieldNames(1414, 9999),
                        null
                ),
                new InternalDeepToStringTest(
                        DerivedValueObject.class.getSimpleName() + "{b=142, value=734}",
                        new DerivedValueObject(142, 734),
                        null
                ),
                new InternalDeepToStringTest(
                        PrimitiveDataTypeValueObject.class.getSimpleName() + "{" +
                                "intValue=125, " +
                                "longValue=1238712387, " +
                                "floatValue=2531.99, " +
                                "doubleValue=1234.236, " +
                                "booleanValue=true, " +
                                "charValue='H', " +
                                "byteValue=127, " +
                                "shortValue=200}",
                        new PrimitiveDataTypeValueObject(125, 1238712387, 2531.99f, 1234.236, true, 'H', (byte) 127, (short) 200),
                        null
                ),
                new InternalDeepToStringTest(
                        ThreeStringsValueObject.class.getSimpleName() + "{b='asd', c='qwe', d='yxc', value=1}",
                        new ThreeStringsValueObject(1, "asd", "qwe", "yxc"),
                        null
                ),
                new InternalDeepToStringTest(
                        UnorderedAttributesTestVO.class.getSimpleName() + "{c=12.0, b=13.0, a=25555, d=7}",
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
    void test(InternalDeepToStringTest testSource)
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
