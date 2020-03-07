package io.ddd.aspect;

//Do NOT remove import! Some IDEs (such as Intellij detect as unrequired import which is wrong.
import io.ddd.stereotype.applicationcore.DomainEvent;
import io.ddd.aspect.reflection.DeepToString;


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
                return DeepToString.toString(this);
            }

}
