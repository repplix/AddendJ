package io.jexxa.addendj.domain.valueobject;


import io.jexxa.addend.applicationcore.ValueObject;


@ValueObject
public class BaseValueObject
{
    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private final int value;

    public BaseValueObject(int value)
    {
        this.value = value;
    }
}
