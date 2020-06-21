package io.jexxa.addendj.aspect;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import io.jexxa.addendj.aspect.domain.SimpleAggregate;
import io.jexxa.addendj.aspect.domain.SimpleValueObject;
import org.junit.Test;

public class AggregateAspectTest
{
    int defaultValueID = 42;

    @Test
    @SuppressWarnings("squid:S1874")
    public void testEquals()
    {
        SimpleAggregate simpleAggregate1 = SimpleAggregate.create(new SimpleValueObject(defaultValueID));
        SimpleAggregate simpleAggregate2 = SimpleAggregate.create(new SimpleValueObject(defaultValueID));
        
        assertEquals(simpleAggregate1.hashCode(), simpleAggregate2.hashCode(),0);
        assertEquals(simpleAggregate1, simpleAggregate2);
    }

    @Test
    @SuppressWarnings("squid:S1874")
    public void testNotEquals()
    {
        SimpleAggregate simpleAggregate1 = SimpleAggregate.create(new SimpleValueObject(defaultValueID));
        SimpleAggregate simpleAggregate2 = SimpleAggregate.create(new SimpleValueObject(defaultValueID * defaultValueID));

        assertNotEquals(simpleAggregate1.hashCode(), simpleAggregate2.hashCode());
        assertNotEquals(simpleAggregate1, simpleAggregate2);
    }

}
