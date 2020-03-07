package io.ddd.aspect;

//Do NOT remove import! Some IDEs (such as Intellij detect as unrequired import which is wrong.
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import io.ddd.aspect.reflection.ClassAccessor;
import io.ddd.stereotype.applicationcore.Aggregate;
import io.ddd.aspect.reflection.DeepEqualsAndHashCode;
import io.ddd.aspect.reflection.DeepToString;
import io.ddd.stereotype.applicationcore.AggregateId;


/**
 * Weaving following methods into Objects annotated with @DomainEvent
 * {@link Object#toString()}
 *
 * // TODO: Following methods are missing
 * {@link Object#hashCode()}
 * {@link Object#equals(Object).
 */
@SuppressWarnings("DanglingJavadoc") public aspect AggregateAspect
{
    /**
     * Interface for weaving methods into a @ValueObject
     */
    private interface IAggregateAspect
    {
    }

    /**
     * Extensions are made for all classes annotated with @ValueObject
     */
    // Disable formatter because space between annotation and '*' is important
    // @formatter:off
    declare parents :(@Aggregate *)implements IAggregateAspect;
    // @formatter:on


    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    public boolean IAggregateAspect.equals(Object other)
    {
        Method thisAggregateIDMethod = ClassAccessor.getMethodAnnotatedWith(this, AggregateId.class);
        Method otherAggregateIDMethod = ClassAccessor.getMethodAnnotatedWith(other, AggregateId.class);

        System.out.println("AggregateEquals");
        try
        {
            return DeepEqualsAndHashCode.isReflectiveEquals(thisAggregateIDMethod.invoke(this), otherAggregateIDMethod.invoke(other));
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        catch (InvocationTargetException e)
        {
            e.printStackTrace();
        }

        return false;
    }

    /*public int IAggregateAspect.hashCode()
            {
                return DeepEqualsAndHashCode.reflectiveHashCode(this);
            }
            
     */
}


