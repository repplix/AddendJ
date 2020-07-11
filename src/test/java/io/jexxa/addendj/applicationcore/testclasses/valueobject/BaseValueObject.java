package io.jexxa.addendj.applicationcore.testclasses.valueobject;


import io.jexxa.addend.applicationcore.ValueObject;


@SuppressWarnings({"unused", "FieldCanBeLocal"})
@ValueObject
public class BaseValueObject
{
    private final int value;

    public BaseValueObject(int value)
    {
        this.value = value;
    }
}
