package io.ddd.aspect.domain;


import io.jexxa.addend.applicationcore.Aggregate;
import io.jexxa.addend.applicationcore.AggregateFactory;
import io.jexxa.addend.applicationcore.AggregateID;

@Aggregate
public class SimpleAggregate
{
    private SimpleValueObject simpleValueObject;

    private SimpleAggregate(SimpleValueObject simpleValueObject)
    {
        this.simpleValueObject = simpleValueObject;
    }

    @AggregateID
    public SimpleValueObject getSimpleValueObject()
    {
        return simpleValueObject;
    }

    @AggregateFactory(SimpleAggregate.class)
    public static SimpleAggregate create(SimpleValueObject simpleValueObject) {
        return new SimpleAggregate(simpleValueObject);
    }
}
