package io.jexxa.addendj.aspect.domain;

import io.jexxa.addend.applicationcore.DomainEvent;

@DomainEvent
public class SimpleDomainEvent
{
    private final int intValue;
    private final String stringValue;

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
