package io.jexxa.addendj.applicationcore;

import io.jexxa.addendj.applicationcore.object.DeepEqualsAndHashCode;
import io.jexxa.addendj.applicationcore.object.DeepToString;
//Do NOT remove import! Some IDEs (such as Intellij detect as unrequired import which is wrong.
import io.jexxa.addend.applicationcore.DomainEvent;


/**
 * Weaving following methods into Objects annotated with {@link DomainEvent}
 * {@link Object#toString()}
 * {@link Object#hashCode()}
 * {@link Object#equals(Object).
 */
public aspect DomainEventAspect
{
    /**
     * Interface for weaving methods into a @ValueObject
     */
    private interface IDomainEventAspect
    {

    }
    
    // 1. Extensions are made for all classes annotated with @DomainEvent
    // 2. Disable formatter because space between annotation and '*' is important
    // @formatter:off
    declare parents :(@DomainEvent *)implements IDomainEventAspect;
    // @formatter:on


    public String IDomainEventAspect.toString()
            {
                return DeepToString.objectToString(this);
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
