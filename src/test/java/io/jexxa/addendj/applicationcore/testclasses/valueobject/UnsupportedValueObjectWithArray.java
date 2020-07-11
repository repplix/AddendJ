package io.jexxa.addendj.applicationcore.testclasses.valueobject;


import io.jexxa.addend.applicationcore.ValueObject;


@SuppressWarnings({"unused", "FieldCanBeLocal"})
@ValueObject
public class UnsupportedValueObjectWithArray
{
    private final int[] value;

    public UnsupportedValueObjectWithArray(final int[] value)
    {
        this.value = value;
    }
}
