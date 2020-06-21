package io.jexxa.addendj.aspect;

import io.jexxa.addendj.aspect.reflection.DeepEqualsAndHashCode;
import io.jexxa.addendj.aspect.reflection.DeepToString;
//Do NOT remove import! Some IDEs (such as Intellij detect as unrequired import which is wrong.
import io.jexxa.addend.applicationcore.DomainEvent;


/**
 * Weaving following methods into Objects annotated with @DomainEvent
 * {@link Object#toString()}
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

    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    public boolean IDomainEventAspect.equals(Object other)
            {
                return DeepEqualsAndHashCode.deepEquals(this, other);
            }

    public int IDomainEventAspect.hashCode()
            {
                return DeepEqualsAndHashCode.deepHashCode(this);
            }
}
