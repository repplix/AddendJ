package io.jexxa.addendj.applicationcore.object;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.jexxa.addendj.applicationcore.testclasses.valueobject.BaseValueObject;
import io.jexxa.addendj.applicationcore.testclasses.valueobject.DerivedValueObject;
import io.jexxa.addendj.applicationcore.testclasses.valueobject.PrimitiveDataTypeValueObject;
import org.junit.jupiter.api.Test;


class ClassAccessorTest
{
    @Test
    void validateClassHierarchy()
    {
        //arrange
        var objectUnderTest = new DerivedValueObject(Integer.MIN_VALUE, Integer.MAX_VALUE);

        //Act
        var result = ClassAccessor.getClassHierarchy( objectUnderTest );

        //Assert
        assertEquals(2, result.size());
        assertTrue(result.contains(DerivedValueObject.class));
        assertTrue(result.contains(BaseValueObject.class));
    }

    @Test
    void validateAttributesInheritance()
    {
        //arrange
        var objectUnderTest = new DerivedValueObject(Integer.MIN_VALUE, Integer.MAX_VALUE);

        //Act
        var result = ClassAccessor.getAttributeList(objectUnderTest);

        //Assert
        assertEquals("derivedValue", result.get(0).getKey());
        assertEquals(Integer.MIN_VALUE, result.get(0).getValue());

        assertEquals("value", result.get(1).getKey());
        assertEquals(Integer.MAX_VALUE, result.get(1).getValue());
    }

    @Test
    void validateAttributesPrimitiveTypes()
    {
        //Arrange
        var objectUnderTest = PrimitiveDataTypeValueObject.newInstanceWithMaxValues();

        //Act
        var result = ClassAccessor.getAttributeList( objectUnderTest );

        //Assert
        assertEquals("intValue", result.get(0).getKey());
        assertEquals(Integer.MAX_VALUE, result.get(0).getValue());

        assertEquals("longValue", result.get(1).getKey());
        assertEquals(Long.MAX_VALUE, result.get(1).getValue());

        assertEquals("floatValue", result.get(2).getKey());
        assertEquals(Float.MAX_VALUE, result.get(2).getValue());

        assertEquals("doubleValue", result.get(3).getKey());
        assertEquals(Double.MAX_VALUE, result.get(3).getValue());

        assertEquals("booleanValue", result.get(4).getKey());
        assertEquals(true, result.get(4).getValue());

        assertEquals("charValue", result.get(5).getKey());
        assertEquals(Character.MAX_VALUE, result.get(5).getValue());

        assertEquals("byteValue", result.get(6).getKey());
        assertEquals(Byte.MAX_VALUE, result.get(6).getValue());

        assertEquals("shortValue", result.get(7).getKey());
        assertEquals(Short.MAX_VALUE, result.get(7).getValue());
    }

}



































