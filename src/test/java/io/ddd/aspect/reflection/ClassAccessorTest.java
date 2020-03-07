package io.ddd.aspect.reflection;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import io.ddd.aspect.valueobjects.BaseTestVO;
import io.ddd.aspect.valueobjects.DerivedTestVO;
import io.ddd.aspect.valueobjects.ThreeStringsTestVO;
import io.ddd.aspect.valueobjects.UnorderedAttributesTestVO;
import org.junit.Test;


/**
 * Diese Klasse testet rein nur {@link ClassAccessor}
 */
public class ClassAccessorTest
{


    /**
     * Checks class hierarchy with Inheritance .
     */
    @Test
    public void testClassHierarchyWithInheritance()
    {
        assertEquals(2, ClassAccessor.getClassHierarchy(new DerivedTestVO(1,2) ).size());
    }


    /**
     * Testet ob alle Attribute korrekt wiedergegeben werden, auch geerbte attribute.
     */
    @Test
    public void testGetAllAttributes()
    {
        assertEquals(2, ClassAccessor.getAllClassAttributes(new DerivedTestVO(1, 2)).get(BaseTestVO.class.getName() + ".a"));
        assertEquals(1, ClassAccessor.getAllClassAttributes(new DerivedTestVO(1, 2)).get(DerivedTestVO.class.getName() + ".b"));
    }

    /**
     * Attribute sollten nach der ebene der Vererbung sortiert werden und dann alphabetisch.
     */
    @Test
    public void testGetAllAttributesSortedInheritance()
    {
        List<Map.Entry<String, Object>> attributes = ClassAccessor.getAttributeList(new DerivedTestVO(1, 2));
        assertEquals("b", attributes.get(0).getKey());
        assertEquals(1, attributes.get(0).getValue());

        assertEquals("a", attributes.get(1).getKey());
        assertEquals(2, attributes.get(1).getValue());
    }

    /**
     * Deklarierte attribute sollten alphabetisch sortiert werden.
     */
    @Test
    public void testGetAllAttributesSorted()
    {
        List<Map.Entry<String, Object>> attributes = ClassAccessor.getAttributeList(new UnorderedAttributesTestVO(1F, 2D, 3L, (byte) 4));
        assertEquals("a", attributes.get(0).getKey());
        assertEquals(3L, attributes.get(0).getValue());

        assertEquals("b", attributes.get(1).getKey());
        assertEquals(2D, attributes.get(1).getValue());

        assertEquals("c", attributes.get(2).getKey());
        assertEquals(1F, attributes.get(2).getValue());

        assertEquals("d", attributes.get(3).getKey());
        assertEquals((byte) 4, attributes.get(3).getValue());
    }

    /**
     * Attribute mit vererbungshierarchie, die unsortiert deklariert wurden sollten richtig sortiert zurückgegeben werden.
     */
    @Test
    public void testGetAllAttributesSortedUnorderedHierarchy()
    {
        List<Map.Entry<String, Object>> attributes = ClassAccessor.getAttributeList(new ThreeStringsTestVO(1, "two", "three", "four"));

        assertEquals("b", attributes.get(0).getKey());
        assertEquals("two", attributes.get(0).getValue());

        assertEquals("c", attributes.get(1).getKey());
        assertEquals("three", attributes.get(1).getValue());

        assertEquals("d", attributes.get(2).getKey());
        assertEquals("four", attributes.get(2).getValue());

        assertEquals("a", attributes.get(3).getKey());
        assertEquals(1, attributes.get(3).getValue());
    }


}



































