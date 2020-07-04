package io.jexxa.addendj.applicationcore.testclasses.domain;

import io.jexxa.addend.applicationcore.ValueObject;

@SuppressWarnings("unused")
@ValueObject
public class SimpleValueObject
{
    private final int valueID;

    public SimpleValueObject(int valueID)
    {
        this.valueID = valueID;
    }

    public int getValueID()
    {
        return valueID;
    }
}
