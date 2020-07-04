package io.jexxa.addendj.applicationcore.testclasses.valueobject;


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
