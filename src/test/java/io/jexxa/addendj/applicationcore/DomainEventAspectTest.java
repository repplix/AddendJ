package io.jexxa.addendj.applicationcore;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.jexxa.addendj.applicationcore.testclasses.domain.SimpleDomainEvent;
import org.junit.jupiter.api.Test;


/**
 * Test for objects annotated with @DomainEvent 
 */
class DomainEventAspectTest
{
    private static final int DEFAULT_INT_VALUE = 42;
    private static final String DEFAULT_STRING_VALUE = "Hello DomainEvent";
    
    @Test
    void testToString()
    {
        SimpleDomainEvent simpleDomainEvent = new SimpleDomainEvent(DEFAULT_INT_VALUE, DEFAULT_STRING_VALUE);

        assertTrue(simpleDomainEvent.toString().contains(DEFAULT_STRING_VALUE));
        assertTrue(simpleDomainEvent.toString().contains(Integer.toString(DEFAULT_INT_VALUE)));
    }

    @Test
    @SuppressWarnings("squid:S1874")
    void testEquals(){
        SimpleDomainEvent simpleDomainEvent1 = new SimpleDomainEvent(DEFAULT_INT_VALUE, DEFAULT_STRING_VALUE);
        SimpleDomainEvent simpleDomainEvent2 = new SimpleDomainEvent(DEFAULT_INT_VALUE, DEFAULT_STRING_VALUE);

        assertEquals(simpleDomainEvent1.hashCode(), simpleDomainEvent2.hashCode());
        assertEquals(simpleDomainEvent1, simpleDomainEvent2);
    }

    @Test
    void testNotEquals(){
        SimpleDomainEvent simpleDomainEvent1 = new SimpleDomainEvent(DEFAULT_INT_VALUE, DEFAULT_STRING_VALUE);
        SimpleDomainEvent simpleDomainEvent2 = new SimpleDomainEvent(DEFAULT_INT_VALUE * DEFAULT_INT_VALUE, DEFAULT_STRING_VALUE + DEFAULT_STRING_VALUE);

        assertNotEquals(simpleDomainEvent1.hashCode(), simpleDomainEvent2.hashCode());
        assertNotEquals(simpleDomainEvent1, simpleDomainEvent2);
    }

}
