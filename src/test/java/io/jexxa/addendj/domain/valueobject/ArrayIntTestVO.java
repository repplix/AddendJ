package io.jexxa.addendj.domain.valueobject;


import io.jexxa.addend.applicationcore.ValueObject;


@SuppressWarnings("unused")
@ValueObject
public class ArrayIntTestVO
{
    @SuppressWarnings("FieldCanBeLocal")
    private final int[] value;

    public ArrayIntTestVO(final int[] value)
    {
        this.value = value;
    }
}
