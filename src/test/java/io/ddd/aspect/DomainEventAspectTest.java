package io.ddd.aspect;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import io.ddd.aspect.domain.SimpleDomainEvent;
import org.junit.Test;



/**
 * Diese Methode überprüft ob die Standard implementierung der toString Methode durch AspectJ überschrieben wurde nicht nicht deren Korrektheit!
 * Dies wird dadurch realisiert das zwei gleiche Objekte erzeugt werden und deren Ergebnis der toString Methode verglichen wird. Falls die toString Methode nicht überschrieben wurde sind die Ergebnisse unterschiedlich da dort dann die unterschiedlichen ObjectIDs teil des Strings sind.
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
    public void testEquals(){
        SimpleDomainEvent simpleDomainEvent1 = new SimpleDomainEvent(defaultIntValue, defaultStringValue);
        SimpleDomainEvent simpleDomainEvent2 = new SimpleDomainEvent(defaultIntValue, defaultStringValue);

        assertEquals(simpleDomainEvent1.hashCode(), simpleDomainEvent2.hashCode());
        assertEquals(simpleDomainEvent1, simpleDomainEvent2);
    }
}
