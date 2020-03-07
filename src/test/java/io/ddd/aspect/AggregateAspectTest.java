package io.ddd.aspect;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import io.ddd.aspect.domain.SimpleAggregate;
import io.ddd.aspect.domain.SimpleValueObject;
import org.junit.Test;

public class AggregateAspectTest
{
    int dafaultValueID = 42;

    @Test
    @SuppressWarnings("squid:S1874")
    public void testEquals()
    {
        SimpleAggregate simpleAggregate1 = SimpleAggregate.create(new SimpleValueObject(dafaultValueID));
        SimpleAggregate simpleAggregate2 = SimpleAggregate.create(new SimpleValueObject(dafaultValueID));
        
        //assertEquals(simpleAggregate1.hashCode(), simpleAggregate2.hashCode(),0);
        assertEquals(simpleAggregate1, simpleAggregate2);
    }
}
