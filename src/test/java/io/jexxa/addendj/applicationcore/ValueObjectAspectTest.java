package io.jexxa.addendj.applicationcore;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import io.jexxa.addendj.applicationcore.testclasses.valueobject.DerivedValueObject;
import io.jexxa.addendj.applicationcore.testclasses.valueobject.DerivedValueObjectWithSameFieldNames;
import io.jexxa.addendj.applicationcore.testclasses.valueobject.GenericValueObject;
import io.jexxa.addendj.applicationcore.testclasses.valueobject.PrimitiveDataTypeValueObject;
import io.jexxa.addendj.applicationcore.testclasses.valueobject.UnsupportedValueObjectWithArray;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;


class ValueObjectAspectTest
{

    @ParameterizedTest
    @MethodSource("equalityData")
    void equalityTest(Object objectX, Object objectY, Object objectZ)
    {
        assertNotNull(objectX);
        assertNotNull(objectY);
        assertNotNull(objectZ);

        assertEquals(objectX, objectX);                                       // reflexive: an object must equal itself
        assertEquals(objectX.equals(objectY), objectY.equals(objectX));       // symmetric: x.equals(y) must return the same result as y.equals(x)
        if (objectX.equals(objectY) && objectY.equals(objectZ))
        {
            assertEquals(objectX, objectZ);                                     // transitive: if x.equals(y) and y.equals(z) then also x.equals(z)
        }
        else
        {
            throw new IllegalStateException("If statement must be evaluate to true");
        }

        assertEquals(objectX.hashCode(), objectY.hashCode());
        assertEquals(objectX.hashCode(), objectZ.hashCode());
    }


    @ParameterizedTest
    @MethodSource("inequalityData")
    void inequalityTest(Object objectX, Object objectY)
    {
        assertNotNull(objectX);
        assertNotNull(objectY);

        assertNotEquals(objectX.hashCode(), objectY.hashCode());

        assertNotEquals(objectX, objectY);

        assertEquals(objectX.equals(objectY), objectY.equals(objectX));       // symmetric: x.equals(y) must return the same result as y.equals(x)

        assertFalse(objectX.equals(null));
    }


    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test
    void invalidValueObject()
    {
        //Arrange
        var objectUnderTestA = new UnsupportedValueObjectWithArray(new int[]{1, 2, 3});
        var objectUnderTestB = new UnsupportedValueObjectWithArray(new int[]{1, 2, 3});

        //Act/Assert
        assertThrows(IllegalArgumentException.class, () -> objectUnderTestA.equals(objectUnderTestB));
        assertEquals(objectUnderTestA.hashCode(), objectUnderTestB.hashCode());
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
                        new GenericValueObject<>(PrimitiveDataTypeValueObject.newInstanceWithMaxValues()),
                        new GenericValueObject<>(PrimitiveDataTypeValueObject.newInstanceWithMaxValues()),
                        new GenericValueObject<>(PrimitiveDataTypeValueObject.newInstanceWithMaxValues())
                ),

                Arguments.of( // Test data with string
                        new GenericValueObject<>(GenericValueObject.class.getName()),
                        new GenericValueObject<>(GenericValueObject.class.getName()),
                        new GenericValueObject<>(GenericValueObject.class.getName())
                ),

                Arguments.of( // Test with NULL
                        new GenericValueObject<String>(null),
                        new GenericValueObject<String>(null),
                        new GenericValueObject<String>(null)
                )

        );
    }


    static Stream<Arguments> inequalityData()
    {
        return Stream.of(
                Arguments.of( // Test data with all primitive types (Difference: 1x max values, 1x min values)
                        PrimitiveDataTypeValueObject.newInstanceWithMaxValues(),
                        PrimitiveDataTypeValueObject.newInstanceWithMinValues()
                ),

                Arguments.of( // Test data with inheritance and same field name (Difference: Switched Min-Max values)
                        new DerivedValueObjectWithSameFieldNames(Integer.MIN_VALUE, Integer.MAX_VALUE),
                        new DerivedValueObjectWithSameFieldNames(Integer.MAX_VALUE, Integer.MIN_VALUE)
                ),

                Arguments.of( // Test data with inheritance   (Difference: Switched Min-Max values)
                        new DerivedValueObject(Integer.MIN_VALUE, Integer.MAX_VALUE),
                        new DerivedValueObject(Integer.MAX_VALUE, Integer.MIN_VALUE)
                ),

                Arguments.of( // Test data with more complex data (Difference: 1x max values, 1x min values)
                        new GenericValueObject<>(PrimitiveDataTypeValueObject.newInstanceWithMaxValues()),
                        new GenericValueObject<>(PrimitiveDataTypeValueObject.newInstanceWithMinValues())
                ),

                Arguments.of( // Test data with different strings
                        new GenericValueObject<>(GenericValueObject.class.getName()),
                        new GenericValueObject<>(GenericValueObject.class.getSimpleName())
                ),

                Arguments.of( // Test with NULL
                        new GenericValueObject<>(GenericValueObject.class.getName()),
                        new GenericValueObject<String>(null)
                )

        );
    }

}

