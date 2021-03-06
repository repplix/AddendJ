package io.jexxa.addendj.applicationcore.testclasses.domain;


import io.jexxa.addend.applicationcore.Aggregate;
import io.jexxa.addend.applicationcore.AggregateFactory;
import io.jexxa.addend.applicationcore.AggregateID;

@Aggregate
public final class SimpleAggregate
{
    private final SimpleValueObject simpleValueObject;

    private SimpleAggregate(SimpleValueObject simpleValueObject)
    {
        this.simpleValueObject = simpleValueObject;
    }

    @SuppressWarnings("unused")
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
