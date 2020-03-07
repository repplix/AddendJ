package io.ddd.aspect.domain;

import io.ddd.stereotype.applicationcore.DomainEvent;

@DomainEvent
public class SimpleDomainEvent
{
    private int intValue;
    private String stringValue;

    public SimpleDomainEvent(int intValue, String stringValue) {
        this.intValue = intValue;
        this.stringValue = stringValue;
    }

    public int getIntValue()
    {
        return intValue;
    }

    public String getStringValue()
    {
        return stringValue;
    }
}
