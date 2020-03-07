package io.ddd.aspect;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Map;

import io.ddd.aspect.reflection.ClassAccessor;
import io.ddd.aspect.valueobjects.ArrayIntTestVO;
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
import org.junit.runners.Parameterized.Parameters;


@RunWith(Parameterized.class)
public class DeepEqualsAndHashCodeTest
{
    private final Object equalA;
    private final Object equalB;
    private final Object notEqual;
    private final Class<? extends Throwable> expected;

    /**
     * Konstruktor.
     *
     * @param equalA   Ein Objekt, das equalB gleich ist.
     * @param equalB   Ein Objekt, das equalA gleich ist.
     * @param notEqual Ein Objekt, das equalA und equalB nicht gleich ist.
     * @param expected Eine erwartete exception oder null.
     */
    public DeepEqualsAndHashCodeTest(final Object equalA, final Object equalB, final Object notEqual, final Class<? extends Throwable> expected)
    {
        this.equalA = equalA;
        this.equalB = equalB;
        this.notEqual = notEqual;
        this.expected = expected;
    }


    /**
     * Datenquelle für parameterisierte tests. Arrays bestehen aus: zwei gleichen Objekten, einem ungleichen Objekt und ggf.
     * der Exception, die erwartet ist. In dieser reihenfolge
     *
     * @return Eine Liste von Datensätzen, die getestet werden.
     */
    @Parameters
    public static Iterable<? extends Object[]> data()
    {
        return Arrays.asList(
                new Object[]{
                        new ArrayIntTestVO(new int[]{1, 2, 3}),
                        new ArrayIntTestVO(new int[]{1, 2, 3}),
                        new ArrayIntTestVO(new int[]{3, 2, 3}),
                        IllegalArgumentException.class
                },
                new Object[]{
                        new DerivedSameAttributeNameTestVO(1, 2),
                        new DerivedSameAttributeNameTestVO(1, 2),
                        new DerivedSameAttributeNameTestVO(2, 2),
                        null
                },
                new Object[]{
                        new DerivedTestVO(9999, 123123),
                        new DerivedTestVO(9999, 123123),
                        new DerivedTestVO(1111111111, 0),
                        null
                },
                new Object[]{
                        new FloatTestVO(1243.283f),
                        new FloatTestVO(1243.283f),
                        new FloatTestVO(1243.284f),
                        null
                },
                new Object[]{
                        new IntTestVO(1),
                        new IntTestVO(1),
                        new IntTestVO(2),
                        null
                },
                new Object[]{
                        new MultipleDifferentTypesTestVO(1, 2L, 12.0f, 13.0, true, 'd', "Hallo", (byte) 12, (short) 13),
                        new MultipleDifferentTypesTestVO(1, 2L, 12.0f, 13.0, true, 'd', "Hallo", (byte) 12, (short) 13),
                        new MultipleDifferentTypesTestVO(1, 2L, 12.0f, 13.0, true, 'd', "Halo", (byte) 12, (short) 13),
                        null
                },

                new Object[]{
                        new ThreeStringsTestVO(1, null, null, null),
                        new ThreeStringsTestVO(1, null, null, null),
                        new ThreeStringsTestVO(1, "a", "b", "c"),
                        null
                },
                new Object[]{
                        new ThreeStringsTestVO(1, "a", "b", "c"),
                        new ThreeStringsTestVO(1, "a", "b", "c"),
                        new ThreeStringsTestVO(1, "a", "2", "c"),
                        null
                },
                new Object[]{
                        new ThreeStringsTestVO(1, "a", "b", "c"),
                        new ThreeStringsTestVO(1, "a", "b", "c"),
                        new ThreeStringsTestVO(2, "a", "b", "c"),
                        null
                },
                new Object[]{
                        new UnorderedAttributesTestVO(2423.9f, 22222222.99999, 999123472, (byte) 127),
                        new UnorderedAttributesTestVO(2423.9f, 22222222.99999, 999123472, (byte) 127),
                        new UnorderedAttributesTestVO(2423.9f, 22222222.99998, 999123472, (byte) 127),
                        null
                }
        );
    }


    /**
     * Startet die tests
     */
    @Test
    public void test()
    {
        try
        {
            equality(equalA, equalB);
            inEquality(equalA, equalB, notEqual);
            equalsWithNull(equalA, equalB, notEqual);
            testHash(equalA, equalB, notEqual);
            testContracts(notEqual, equalA);
            testContracts(notEqual, equalB);
            testContracts(equalA, equalB);
        }
        catch (Throwable t)
        {
            if (expected == null)
            {
                throw t;
            }
            if (expected != t.getClass())
            {
                throw t;
            }
        }
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
