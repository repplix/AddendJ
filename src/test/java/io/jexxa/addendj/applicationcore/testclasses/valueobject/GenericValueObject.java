package io.jexxa.addendj.applicationcore.testclasses.valueobject;

import io.jexxa.addend.applicationcore.ValueObject;

@SuppressWarnings({"unused", "FieldCanBeLocal"})
@ValueObject
public class GenericValueObject<T>
{
    private final T value;

    public GenericValueObject(T value)
    {
        this.value = value;
    }

}
