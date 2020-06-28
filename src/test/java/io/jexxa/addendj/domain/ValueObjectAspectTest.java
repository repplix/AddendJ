package io.jexxa.addendj.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import io.jexxa.addendj.domain.valueobject.ArrayIntTestVO;
import io.jexxa.addendj.domain.valueobject.DerivedSameAttributeNameTestVO;
import io.jexxa.addendj.domain.valueobject.DerivedTestVO;
import io.jexxa.addendj.domain.valueobject.FloatValueObject;
import io.jexxa.addendj.domain.valueobject.IntValueObject;
import io.jexxa.addendj.domain.valueobject.PrimitiveDataTypeValueObject;
import io.jexxa.addendj.domain.valueobject.ThreeStringsTestVO;
import io.jexxa.addendj.domain.valueobject.UnorderedAttributesTestVO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;


class ValueObjectAspectTest
{

    static class InternalValueObjectAspectTest
    {
        final Object equalA;
        final Object equalB;
        final Object notEqual;

        InternalValueObjectAspectTest(final Object equalA, final Object equalB, final Object notEqual)
        {
            this.equalA = equalA;
            this.equalB = equalB;
            this.notEqual = notEqual;
        }
    }


    /**
     * Datenquelle für parameterisierte tests. Arrays bestehen aus: zwei gleichen Objekten, einem ungleichen Objekt und ggf.
     * der Exception, die erwartet ist. In dieser reihenfolge
     *
     * @return Eine Liste von Datensätzen, die getestet werden.
     */
    static Stream<InternalValueObjectAspectTest> data()
    {
        return Stream.of(
                new InternalValueObjectAspectTest(
                        new DerivedSameAttributeNameTestVO(1, 2),
                        new DerivedSameAttributeNameTestVO(1, 2),
                        new DerivedSameAttributeNameTestVO(2, 2)
                ),
                new InternalValueObjectAspectTest(
                        new DerivedTestVO(9999, 123123),
                        new DerivedTestVO(9999, 123123),
                        new DerivedTestVO(1111111111, 0)
                ),
                new InternalValueObjectAspectTest(
                        new FloatValueObject(1243.283f),
                        new FloatValueObject(1243.283f),
                        new FloatValueObject(1243.284f)
                ),
                new InternalValueObjectAspectTest(
                        new IntValueObject(1),
                        new IntValueObject(1),
                        new IntValueObject(2)
                ),
                new InternalValueObjectAspectTest(
                        new PrimitiveDataTypeValueObject(1, 2L, 12.0f, 13.0, true, 'd', (byte) 12, (short) 13),
                        new PrimitiveDataTypeValueObject(1, 2L, 12.0f, 13.0, true, 'd', (byte) 12, (short) 13),
                        new PrimitiveDataTypeValueObject(1, 2L, 12.0f, 13.0, false, 'd', (byte) 12, (short) 13)
                ),

                new InternalValueObjectAspectTest(
                        new ThreeStringsTestVO(1, null, null, null),
                        new ThreeStringsTestVO(1, null, null, null),
                        new ThreeStringsTestVO(1, "a", "b", "c")
                ),
                new InternalValueObjectAspectTest(
                        new ThreeStringsTestVO(1, "a", "b", "c"),
                        new ThreeStringsTestVO(1, "a", "b", "c"),
                        new ThreeStringsTestVO(1, "a", "2", "c")
                ),
                new InternalValueObjectAspectTest(
                        new ThreeStringsTestVO(1, "a", "b", "c"),
                        new ThreeStringsTestVO(1, "a", "b", "c"),
                        new ThreeStringsTestVO(2, "a", "b", "c")
                ),
                new InternalValueObjectAspectTest(
                        new UnorderedAttributesTestVO(2423.9f, 22222222.99999, 999123472, (byte) 127),
                        new UnorderedAttributesTestVO(2423.9f, 22222222.99999, 999123472, (byte) 127),
                        new UnorderedAttributesTestVO(2423.9f, 22222222.99998, 999123472, (byte) 127)
                )
        );
    }


    /**
     * Startet die tests
     */
    @ParameterizedTest
    @MethodSource("data")
    void test(InternalValueObjectAspectTest testSetup)
    {
        equality(testSetup.equalA, testSetup.equalB);
        inEquality(testSetup.equalA, testSetup.equalB, testSetup.notEqual);
        equalsWithNull(testSetup.equalA, testSetup.equalB, testSetup.notEqual);
        testHash(testSetup.equalA, testSetup.equalB, testSetup.notEqual);
        testContracts(testSetup.notEqual, testSetup.equalA);
        testContracts(testSetup.notEqual, testSetup.equalB);
        testContracts(testSetup.equalA, testSetup.equalB);
    }

    @Test
    void invalidValueObject()
    {
        //Arrange
        var objectUnderTestA = new ArrayIntTestVO(new int[]{1, 2, 3});
        var objectUnderTestB = new ArrayIntTestVO(new int[]{1, 2, 3});

        //Act/Assert
        assertThrows(IllegalArgumentException.class, () -> objectUnderTestA.equals(objectUnderTestB) );
        assertEquals(objectUnderTestA.hashCode(), objectUnderTestB.hashCode());
    }

    
    /**
     * Testet ob beide übergebenen Objekte gleich sind. Testet auch ob die relation reflexiv ist.
     *
     * @param equalA Ein Objekt
     * @param equalB Ein anderes Objekt
     */
    private void equality(final Object equalA, final Object equalB)
    {
        assertEquals(equalA, equalB);
        assertEquals(equalB, equalA);
    }

    /**
     * Testet ob equalA und equalB jeweils nicht gleich zu notEqual sind. Testet auch ob die Relation reflexiv ist.
     *
     * @param equalA   Ein Objekt, das equalB gleich ist.
     * @param equalB   Ein Objekt, das equalA gleich ist.
     * @param notEqual Ein Objekt, das equalA und equalB nicht gleich ist.
     */
    private void inEquality(final Object equalA, final Object equalB, final Object notEqual)
    {
        assertNotEquals(equalA, notEqual);
        assertNotEquals(notEqual, equalA);

        assertNotEquals(equalB, notEqual);
        assertNotEquals(notEqual, equalB);
    }

    /**
     * Testet ob alle Objekte jeweils ungleich null sind.
     *
     * @param equalA   Ein Objekt, das equalB gleich ist.
     * @param equalB   Ein Objekt, das equalA gleich ist.
     * @param notEqual Ein Objekt, das equalA und equalB nicht gleich ist.
     */
    private void equalsWithNull(final Object equalA, final Object equalB, final Object notEqual)
    {
        assertNotEquals(equalA, null);
        assertNotEquals(equalB, null);
        assertNotEquals(notEqual, null);
    }

    /**
     * Testet ob der hashCode von zwei gleichen Objekten gleich ist. Testet auch, dass der hashCode von unterschiedlichen Objekten auch unterschiedlich ist.
     *
     * @param equalA   Ein Objekt, das equalB gleich ist.
     * @param equalB   Ein Objekt, das equalA gleich ist.
     * @param notEqual Ein Objekt, das equalA und equalB nicht gleich ist.
     */
    private void testHash(final Object equalA, final Object equalB, final Object notEqual)
    {
        assertEquals(equalA.hashCode(), equalB.hashCode());
        assertNotEquals(equalA.hashCode(), notEqual.hashCode());
        assertNotEquals(equalB.hashCode(), notEqual.hashCode());
    }

    /**
     * Testet die Contracts zwischen hashcode und equals.
     * Wenn beide Objekte gleich sind, muss der selbe hashCode erzeugt werden.
     * Sind beide Objekte unterschiedlich, so sollte ein unterschiedlicher hashCode generiert werden.
     *
     * @param a Irgendein Objekt
     * @param b Ein anderes Objekt, des selben Typen.
     */
    private void testContracts(final Object a, final Object b)
    {
        if (a.equals(b))
        {
            assertEquals(a.hashCode(), b.hashCode());
        }
        if (a.hashCode() != b.hashCode())
        {
            assertNotEquals(a, b);
        }
    }
}
