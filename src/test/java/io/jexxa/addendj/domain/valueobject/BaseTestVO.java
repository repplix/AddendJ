package io.jexxa.addendj.domain.valueobject;


import io.jexxa.addend.applicationcore.ValueObject;


@ValueObject
public class BaseTestVO
{
    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private final int value;

    public BaseTestVO(int value)
    {
        this.value = value;
    }
}
