package io.jexxa.addendj.domain.object;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;

import io.jexxa.addendj.domain.valueobject.BaseValueObject;
import io.jexxa.addendj.domain.valueobject.DerivedValueObject;
import io.jexxa.addendj.domain.valueobject.ThreeStringsValueObject;
import io.jexxa.addendj.domain.valueobject.UnorderedAttributesTestVO;
import org.junit.jupiter.api.Test;


class ClassAccessorTest
{


    /**
     * Checks class hierarchy with Inheritance .
     */
    @Test
    void testClassHierarchyWithInheritance()
    {
        assertEquals(2, ClassAccessor.getClassHierarchy(new DerivedValueObject(1,2) ).size());
    }


    /**
     * Testet ob alle Attribute korrekt wiedergegeben werden, auch geerbte attribute.
     */
    @Test
    void testGetAllAttributes()
    {
        assertEquals(2, ClassAccessor.getAllClassAttributes(new DerivedValueObject(1, 2)).get(BaseValueObject.class.getName() + ".value"));
        assertEquals(1, ClassAccessor.getAllClassAttributes(new DerivedValueObject(1, 2)).get(DerivedValueObject.class.getName() + ".b"));
    }

    /**
     * Attribute sollten nach der ebene der Vererbung sortiert werden und dann alphabetisch.
     */
    @Test
    void testGetAllAttributesSortedInheritance()
    {
        List<Map.Entry<String, Object>> attributes = ClassAccessor.getAttributeList(new DerivedValueObject(1, 2));
        assertEquals("b", attributes.get(0).getKey());
        assertEquals(1, attributes.get(0).getValue());

        assertEquals("value", attributes.get(1).getKey());
        assertEquals(2, attributes.get(1).getValue());
    }

    /**
     * Deklarierte attribute sollten alphabetisch sortiert werden.
     */
    @Test
    void testGetAllAttributesSorted()
    {
        List<Map.Entry<String, Object>> attributes = ClassAccessor.getAttributeList(new UnorderedAttributesTestVO(1F, 2D, 3L, (byte) 4));
        assertEquals("c", attributes.get(0).getKey());
        assertEquals(1F, attributes.get(0).getValue());

        assertEquals("b", attributes.get(1).getKey());
        assertEquals(2D, attributes.get(1).getValue());

        assertEquals("a", attributes.get(2).getKey());
        assertEquals(3L, attributes.get(2).getValue());

        assertEquals("d", attributes.get(3).getKey());
        assertEquals((byte) 4, attributes.get(3).getValue());
    }

    /**
     * Attribute mit vererbungshierarchie, die unsortiert deklariert wurden sollten richtig sortiert zurückgegeben werden.
     */
    @Test
    void testGetAllAttributesSortedUnorderedHierarchy()
    {
        List<Map.Entry<String, Object>> attributes = ClassAccessor.getAttributeList(new ThreeStringsValueObject(1, "two", "three", "four"));

        assertEquals("b", attributes.get(0).getKey());
        assertEquals("two", attributes.get(0).getValue());

        assertEquals("c", attributes.get(1).getKey());
        assertEquals("three", attributes.get(1).getValue());

        assertEquals("d", attributes.get(2).getKey());
        assertEquals("four", attributes.get(2).getValue());

        assertEquals("value", attributes.get(3).getKey());
        assertEquals(1, attributes.get(3).getValue());
    }


}



































