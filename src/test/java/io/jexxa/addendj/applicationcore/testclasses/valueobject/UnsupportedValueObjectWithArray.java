package io.jexxa.addendj.applicationcore.testclasses.valueobject;


import io.jexxa.addend.applicationcore.ValueObject;


@SuppressWarnings("unused")
@ValueObject
public class UnsupportedValueObjectWithArray
{
    @SuppressWarnings("FieldCanBeLocal")
    private final int[] value;

    public UnsupportedValueObjectWithArray(final int[] value)
    {
        this.value = value;
    }
}
