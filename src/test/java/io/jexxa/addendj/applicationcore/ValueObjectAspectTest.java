package io.jexxa.addendj.applicationcore;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import io.jexxa.addendj.applicationcore.testclasses.valueobject.DerivedValueObject;
import io.jexxa.addendj.applicationcore.testclasses.valueobject.DerivedValueObjectWithSameFieldNames;
import io.jexxa.addendj.applicationcore.testclasses.valueobject.GenericValueObject;
import io.jexxa.addendj.applicationcore.testclasses.valueobject.PrimitiveDataTypeValueObject;
import io.jexxa.addendj.applicationcore.testclasses.valueobject.ThreeStringsValueObject;
import io.jexxa.addendj.applicationcore.testclasses.valueobject.UnsupportedValueObjectWithArray;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
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
                        new DerivedValueObjectWithSameFieldNames(1, 2),
                        new DerivedValueObjectWithSameFieldNames(1, 2),
                        new DerivedValueObjectWithSameFieldNames(2, 2)
                ),
                new InternalValueObjectAspectTest(
                        new DerivedValueObject(9999, 123123),
                        new DerivedValueObject(9999, 123123),
                        new DerivedValueObject(1111111111, 0)
                ),

                new InternalValueObjectAspectTest(
                        new ThreeStringsValueObject(1, null, null, null),
                        new ThreeStringsValueObject(1, null, null, null),
                        new ThreeStringsValueObject(1, "a", "b", "c")
                ),
                new InternalValueObjectAspectTest(
                        new ThreeStringsValueObject(1, "a", "b", "c"),
                        new ThreeStringsValueObject(1, "a", "b", "c"),
                        new ThreeStringsValueObject(1, "a", "2", "c")
                ),
                new InternalValueObjectAspectTest(
                        new ThreeStringsValueObject(1, "a", "b", "c"),
                        new ThreeStringsValueObject(1, "a", "b", "c"),
                        new ThreeStringsValueObject(2, "a", "b", "c")
                )
        );
    }

    static Stream<Arguments> equalityData()
    {
        return Stream.of(
                Arguments.of( // Test data with all primitive types (max value)
                        PrimitiveDataTypeValueObject.newInstanceWithMaxValues(),
                        PrimitiveDataTypeValueObject.newInstanceWithMaxValues(),
                        PrimitiveDataTypeValueObject.newInstanceWithMaxValues()
                ),
                Arguments.of( // Test data with all primitive types (min value)
                        PrimitiveDataTypeValueObject.newInstanceWithMinValues(),
                        PrimitiveDataTypeValueObject.newInstanceWithMinValues(),
                        PrimitiveDataTypeValueObject.newInstanceWithMinValues()
                ),
                
                Arguments.of( // Test data with inheritance and same field naem 
                        new DerivedValueObjectWithSameFieldNames(Integer.MIN_VALUE, Integer.MAX_VALUE),
                        new DerivedValueObjectWithSameFieldNames(Integer.MIN_VALUE, Integer.MAX_VALUE),
                        new DerivedValueObjectWithSameFieldNames(Integer.MIN_VALUE, Integer.MAX_VALUE)
                ),
                
                Arguments.of( // Test data with inheritance
                        new DerivedValueObject(Integer.MIN_VALUE, Integer.MAX_VALUE),
                        new DerivedValueObject(Integer.MIN_VALUE, Integer.MAX_VALUE),
                        new DerivedValueObject(Integer.MIN_VALUE, Integer.MAX_VALUE)
                ),
                
                Arguments.of( // Test data with more complex data 
                        new GenericValueObject<>( PrimitiveDataTypeValueObject.newInstanceWithMaxValues() ),
                        new GenericValueObject<>( PrimitiveDataTypeValueObject.newInstanceWithMaxValues() ),
                        new GenericValueObject<>( PrimitiveDataTypeValueObject.newInstanceWithMaxValues() )
                ),

                Arguments.of( // Test data with string
                        new GenericValueObject<>( GenericValueObject.class.getName() ),
                        new GenericValueObject<>( GenericValueObject.class.getName() ),
                        new GenericValueObject<>( GenericValueObject.class.getName() )
                )


        );
    }


    /**
     * Checks conditions for equality
     */
    @ParameterizedTest
    @MethodSource("equalityData")
    void equalityTest(Object objectX, Object objectY, Object objectZ)
    {
        assertNotNull(objectX);
        assertNotNull(objectY);
        assertNotNull(objectZ);

        assertEquals( objectX, objectX );                                       // reflexive: an object must equal itself
        assertEquals( objectX.equals(objectY), objectY.equals(objectX) );       // symmetric: x.equals(y) must return the same result as y.equals(x)
        if( objectX.equals(objectY) && objectY.equals(objectZ)){
            assertEquals(objectX, objectZ);                                     // transitive: if x.equals(y) and y.equals(z) then also x.equals(z)
        }
        else
        {
            throw new IllegalStateException("If statement must be evaluate to true");
        }
        
        assertEquals(objectX, objectY);
        assertEquals(objectX.hashCode(), objectY.hashCode());
        assertEquals(objectX.hashCode(), objectZ.hashCode());
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
        var objectUnderTestA = new UnsupportedValueObjectWithArray(new int[]{1, 2, 3});
        var objectUnderTestB = new UnsupportedValueObjectWithArray(new int[]{1, 2, 3});

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
