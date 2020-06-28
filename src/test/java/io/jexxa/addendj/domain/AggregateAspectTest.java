package io.jexxa.addendj.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import io.jexxa.addendj.domain.domain.SimpleAggregate;
import io.jexxa.addendj.domain.domain.SimpleValueObject;
import org.junit.jupiter.api.Test;

public class AggregateAspectTest
{
    private static final int DEFAULT_VALUE_ID = 42;

    @Test
    @SuppressWarnings("squid:S1874")
    public void testEquals()
    {
        SimpleAggregate simpleAggregate1 = SimpleAggregate.create(new SimpleValueObject(DEFAULT_VALUE_ID));
        SimpleAggregate simpleAggregate2 = SimpleAggregate.create(new SimpleValueObject(DEFAULT_VALUE_ID));
        
        assertEquals(simpleAggregate1.hashCode(), simpleAggregate2.hashCode(),0);
        assertEquals(simpleAggregate1, simpleAggregate2);
    }

    @Test
    @SuppressWarnings("squid:S1874")
    public void testNotEquals()
    {
        SimpleAggregate simpleAggregate1 = SimpleAggregate.create(new SimpleValueObject(DEFAULT_VALUE_ID));
        SimpleAggregate simpleAggregate2 = SimpleAggregate.create(new SimpleValueObject(DEFAULT_VALUE_ID * DEFAULT_VALUE_ID));

        assertNotEquals(simpleAggregate1.hashCode(), simpleAggregate2.hashCode());
        assertNotEquals(simpleAggregate1, simpleAggregate2);
    }

}
