package io.ddd.aspect.domain;

import io.ddd.stereotype.applicationcore.Aggregate;
import io.ddd.stereotype.applicationcore.AggregateFactory;
import io.ddd.stereotype.applicationcore.AggregateId;

@Aggregate
public class SimpleAggregate
{
    private SimpleValueObject simpleValueObject;

    private SimpleAggregate(SimpleValueObject simpleValueObject)
    {
        this.simpleValueObject = simpleValueObject;
    }

    @AggregateId
    public SimpleValueObject getSimpleValueObject()
    {
        return simpleValueObject;
    }

    @AggregateFactory(SimpleAggregate.class)
    public static SimpleAggregate create(SimpleValueObject simpleValueObject) {
        return new SimpleAggregate(simpleValueObject);
    }
}
