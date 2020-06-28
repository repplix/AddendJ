package io.jexxa.addendj.domain.valueobject;

import io.jexxa.addend.applicationcore.ValueObject;

@ValueObject
@SuppressWarnings("unused")
public class GenericValueObject<T>
{
    @SuppressWarnings("FieldCanBeLocal")
    private final T value;

    public GenericValueObject(T value)
    {
        this.value = value;
    }

}
