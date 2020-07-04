package io.jexxa.addendj.applicationcore;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import io.jexxa.addend.applicationcore.AggregateID;
import io.jexxa.addendj.applicationcore.object.ClassAccessor;
import io.jexxa.addendj.applicationcore.object.DeepEqualsAndHashCode;

//Do NOT remove import! Some IDEs (such as Intellij detect as unrequired import which is wrong.
import io.jexxa.addend.applicationcore.Aggregate;

/**
 * Weaving following methods into Objects annotated with {@link Aggregate}
 * {@link Object#hashCode()}
 * {@link Object#equals(Object)}.
 *
 * NOTE: These methods depends on {@link Aggregate}  and {@link AggregateID}
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


    public boolean IAggregateAspect.equals(Object other)
    {
        if (this == other)
        {
            return true;
        }

        if ( other == null )
        {
            return false;
        }
        
        if (getClass() != other.getClass())
        {
            return false;
        }

        Method thisAggregateIDMethod = ClassAccessor.getMethodAnnotatedWith(this, AggregateID.class);
        Method otherAggregateIDMethod = ClassAccessor.getMethodAnnotatedWith(other, AggregateID.class);

        //Compare result of AggrgateID mehtod to decide of both objects are equal
        try
        {
            return DeepEqualsAndHashCode.deepEquals(thisAggregateIDMethod.invoke(this),
                                                            otherAggregateIDMethod.invoke(other));
        }
        catch (IllegalAccessException | InvocationTargetException e)
        {
            throw new IllegalArgumentException("Could not invoke method annotated with " +
                    AggregateID.class.getSimpleName() + " on object " + this.getClass().getSimpleName());
        }
    }

    public int IAggregateAspect.hashCode()
    {
        Method thisAggregateIDMethod = ClassAccessor.getMethodAnnotatedWith(this, AggregateID.class);

        try
        {
            return DeepEqualsAndHashCode.deepHashCode(thisAggregateIDMethod.invoke(this));
        }
        catch (IllegalAccessException | InvocationTargetException e)
        {
            throw new IllegalArgumentException("Could not invoke method annotated with " +
                    AggregateID.class.getSimpleName() + " on object " + this.getClass().getSimpleName());
        }
    }
    
}


