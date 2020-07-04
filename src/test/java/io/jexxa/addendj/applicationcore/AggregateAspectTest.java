package io.jexxa.addendj.applicationcore;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.jexxa.addend.applicationcore.Aggregate;
import io.jexxa.addend.applicationcore.AggregateFactory;
import io.jexxa.addend.applicationcore.AggregateID;
import io.jexxa.addendj.applicationcore.testclasses.domain.SimpleAggregate;
import io.jexxa.addendj.applicationcore.testclasses.domain.SimpleValueObject;
import org.junit.jupiter.api.Test;

class AggregateAspectTest
{
    private static final int DEFAULT_VALUE_ID = 42;

    @Test
    @SuppressWarnings("squid:S1874")
    void testEquals()
    {
        SimpleAggregate simpleAggregate1 = SimpleAggregate.create(new SimpleValueObject(DEFAULT_VALUE_ID));
        SimpleAggregate simpleAggregate2 = SimpleAggregate.create(new SimpleValueObject(DEFAULT_VALUE_ID));
        
        assertEquals(simpleAggregate1.hashCode(), simpleAggregate2.hashCode());
        assertEquals(simpleAggregate1, simpleAggregate2);
    }

    @Test
    @SuppressWarnings("squid:S1874")
    void testNotEquals()
    {
        SimpleAggregate simpleAggregate1 = SimpleAggregate.create(new SimpleValueObject(DEFAULT_VALUE_ID));
        SimpleAggregate simpleAggregate2 = SimpleAggregate.create(new SimpleValueObject(DEFAULT_VALUE_ID * DEFAULT_VALUE_ID));

        assertNotEquals(simpleAggregate1.hashCode(), simpleAggregate2.hashCode());
        assertNotEquals(simpleAggregate1, simpleAggregate2);
    }

    @Test
    @SuppressWarnings("squid:S1874")
    void testNotEqualsDifferentType()
    {
        var aggregateA = SimpleAggregate.create(new SimpleValueObject(DEFAULT_VALUE_ID));
        var aggregateB = AggregateDifferentType.create(new SimpleValueObject(DEFAULT_VALUE_ID));

        assertEquals(aggregateA.hashCode(), aggregateB.hashCode());
        assertNotEquals(aggregateA, aggregateB);
    }


    @Test
    @SuppressWarnings("squid:S1874")
    void testMissingAnnotation()
    {
        var aggregateA = AggregateMissingAnnotation.create(new SimpleValueObject(DEFAULT_VALUE_ID));
        var aggregateB = AggregateMissingAnnotation.create(new SimpleValueObject(DEFAULT_VALUE_ID));

        //noinspection ResultOfMethodCallIgnored
        assertThrows(IllegalArgumentException.class, aggregateA::hashCode);
        //noinspection ResultOfMethodCallIgnored
        assertThrows(IllegalArgumentException.class, () -> aggregateA.equals(aggregateB));
    }


    @SuppressWarnings("unused")
    @Aggregate
    static final class AggregateMissingAnnotation
    {
        private final SimpleValueObject simpleValueObject;

        private AggregateMissingAnnotation(SimpleValueObject simpleValueObject)
        {
            this.simpleValueObject = simpleValueObject;
        }

        SimpleValueObject getSimpleValueObject()
        {
            return simpleValueObject;
        }

        @AggregateFactory(AggregateMissingAnnotation.class)
        static AggregateMissingAnnotation create(SimpleValueObject simpleValueObject) {
            return new AggregateMissingAnnotation(simpleValueObject);
        }
    }


    @SuppressWarnings("unused")
    @Aggregate
    static final class AggregateDifferentType
    {
        private final SimpleValueObject simpleValueObject;

        private AggregateDifferentType(SimpleValueObject simpleValueObject)
        {
            this.simpleValueObject = simpleValueObject;
        }

        @AggregateID
        SimpleValueObject getSimpleValueObject()
        {
            return simpleValueObject;
        }

        @AggregateFactory(AggregateDifferentType.class)
        static AggregateDifferentType create(SimpleValueObject simpleValueObject) {
            return new AggregateDifferentType(simpleValueObject);
        }
    }

}
