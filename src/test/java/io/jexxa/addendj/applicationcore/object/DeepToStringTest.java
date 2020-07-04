package io.jexxa.addendj.applicationcore.object;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import io.jexxa.addendj.applicationcore.testclasses.valueobject.GenericValueObject;
import io.jexxa.addendj.applicationcore.testclasses.valueobject.UnsupportedValueObjectWithArray;
import io.jexxa.addendj.applicationcore.testclasses.valueobject.BaseValueObject;
import io.jexxa.addendj.applicationcore.testclasses.valueobject.DerivedValueObjectWithSameFieldNames;
import io.jexxa.addendj.applicationcore.testclasses.valueobject.DerivedValueObject;
import io.jexxa.addendj.applicationcore.testclasses.valueobject.PrimitiveDataTypeValueObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;



@SuppressWarnings("StringBufferReplaceableByString")
class DeepToStringTest
{

    @ParameterizedTest
    @MethodSource("toStringData")
    void validateToString(Object referenceString, Object objectUnderTest)
    {
         assertEquals(referenceString, objectUnderTest.toString());
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test
    void invalidToString()
    {
        //Arrange
        var objectUnderTestA = new UnsupportedValueObjectWithArray(new int[]{1, 2, 3});
        
        //Act/Assert
        assertThrows(IllegalArgumentException.class, objectUnderTestA::toString);
    }

    static Stream<Arguments> toStringData()
    {
        return Stream.of(
                Arguments.of(
                        GenericValueObject.class.getSimpleName() +  "{value=null}",
                        new GenericValueObject<String>(null)
                ),
                Arguments.of(
                        BaseValueObject.class.getSimpleName() +
                                new StringBuilder()
                                        .append("{value=")
                                        .append(Integer.MIN_VALUE)
                                        .append("}")
                                        .toString(),
                        new BaseValueObject(Integer.MIN_VALUE)
                ),
                Arguments.of(
                        DerivedValueObjectWithSameFieldNames.class.getSimpleName() +
                                new StringBuilder()
                                        .append("{value=").append(Integer.MIN_VALUE).append(", ")
                                        .append("value=").append(Integer.MAX_VALUE).append("}")
                                        .toString(),
                        new DerivedValueObjectWithSameFieldNames(Integer.MIN_VALUE, Integer.MAX_VALUE)
                ),
                Arguments.of(
                        DerivedValueObject.class.getSimpleName() +
                                new StringBuilder()
                                        .append("{derivedValue=").append(Integer.MIN_VALUE).append(", ")
                                        .append("value=").append(Integer.MAX_VALUE).append("}")
                                        .toString(),
                        new DerivedValueObject(Integer.MIN_VALUE, Integer.MAX_VALUE)
                ),
                Arguments.of(
                        PrimitiveDataTypeValueObject.class.getSimpleName() + new StringBuilder()
                                .append("{")
                                .append("intValue=").append(Integer.MIN_VALUE).append(", ")
                                .append("longValue=").append(Long.MIN_VALUE).append(", ")
                                .append("floatValue=").append(Float.MIN_VALUE).append(", ")
                                .append("doubleValue=").append(Double.MIN_VALUE).append(", ")
                                .append("booleanValue=").append(false).append(", ")
                                .append("charValue='").append(Character.MIN_VALUE).append("', ")
                                .append("byteValue=").append(Byte.MIN_VALUE).append(", ")
                                .append("shortValue=").append(Short.MIN_VALUE).append("}")
                                .toString(),
                        PrimitiveDataTypeValueObject.newInstanceWithMinValues()
                )
        );
    }


}
