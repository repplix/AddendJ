package io.ddd.aspect;

//Import nicht entfernen! Import wird von Intellij fälschlicherweise ausgegraut
import io.ddd.stereotype.applicationcore.DomainEvent;
import io.ddd.aspect.reflection.ReflectiveToString;


/**
 * Weaving following methods into Objects annotated with @DomainEvent
 * {@link Object#toString()}
 *
 * // TODO: Following methods are missing
 * {@link Object#hashCode()}
 * {@link Object#equals(Object).
 */
@SuppressWarnings("DanglingJavadoc") public aspect DomainEventAspect
{
    /**
     * Interface for weaving methods into a @ValueObject
     */
    private interface IDomainEventAspect
    {
    }


    /**
     * Extensions are made for all classes annotated with @ValueObject
     */
    // Disable formatter because space between annotation and '*' is important
    // @formatter:off
    declare parents :(@DomainEvent *)implements IDomainEventAspect;
    // @formatter:on


    public String IDomainEventAspect.toString()
            {
                return ReflectiveToString.toString(this);
            }

}
