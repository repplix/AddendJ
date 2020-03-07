package io.ddd.aspect.domain;

import io.ddd.stereotype.applicationcore.ValueObject;

@ValueObject
public class SimpleValueObject
{
    private int valueID;

    public SimpleValueObject(int valueID)
    {
        this.valueID = valueID;
    }

    public int getValueID()
    {
        return valueID;
    }
}
