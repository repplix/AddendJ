package io.jexxa.addendj.aspect;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import io.jexxa.addendj.aspect.domain.SimpleDomainEvent;
import org.junit.Test;



/**
 * Test for objects annotated with @DomainEvent 
 */
public class DomainEventAspectTest
{
    private int defaultIntValue = 42;
    private String defaultStringValue = "Hello DomainEvent";
    
    @Test
    public void testToString()
    {
        SimpleDomainEvent simpleDomainEvent = new SimpleDomainEvent(defaultIntValue, defaultStringValue);

        assertTrue(simpleDomainEvent.toString().contains(defaultStringValue));
        assertTrue(simpleDomainEvent.toString().contains(Integer.toString(defaultIntValue)));
    }

    @Test
    @SuppressWarnings("squid:S1874")
    public void testEquals(){
        SimpleDomainEvent simpleDomainEvent1 = new SimpleDomainEvent(defaultIntValue, defaultStringValue);
        SimpleDomainEvent simpleDomainEvent2 = new SimpleDomainEvent(defaultIntValue, defaultStringValue);

        assertEquals(simpleDomainEvent1.hashCode(), simpleDomainEvent2.hashCode(), 0);
        assertEquals(simpleDomainEvent1, simpleDomainEvent2);
    }

    @Test
    public void testNotEquals(){
        SimpleDomainEvent simpleDomainEvent1 = new SimpleDomainEvent(defaultIntValue, defaultStringValue);
        SimpleDomainEvent simpleDomainEvent2 = new SimpleDomainEvent(defaultIntValue * defaultIntValue, defaultStringValue + defaultStringValue);

        assertNotEquals(simpleDomainEvent1.hashCode(), simpleDomainEvent2.hashCode());
        assertNotEquals(simpleDomainEvent1, simpleDomainEvent2);
    }

}
