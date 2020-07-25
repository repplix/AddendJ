package io.jexxa.addendj.applicationcore.testclasses.domain;

import io.jexxa.addend.applicationcore.Aggregate;
import io.jexxa.addend.applicationcore.AggregateFactory;

@SuppressWarnings("unused")
@Aggregate
public final class InvalidAggregate
{
    private final SimpleValueObject simpleValueObject;

    private InvalidAggregate(SimpleValueObject simpleValueObject)
    {
        this.simpleValueObject = simpleValueObject;
    }

    public SimpleValueObject getSimpleValueObject()
    {
        return simpleValueObject;
    }

    @AggregateFactory(SimpleAggregate.class)
    public static InvalidAggregate create(SimpleValueObject simpleValueObject) {
        return new InvalidAggregate(simpleValueObject);
    }

}
