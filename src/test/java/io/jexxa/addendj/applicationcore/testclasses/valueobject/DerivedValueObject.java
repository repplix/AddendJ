package io.jexxa.addendj.applicationcore.testclasses.valueobject;


import io.jexxa.addend.applicationcore.ValueObject;

@SuppressWarnings({"unused", "FieldCanBeLocal"})
@ValueObject
public class DerivedValueObject extends BaseValueObject
{
    private final int derivedValue;

    public DerivedValueObject(final int derivedValue, final int baseValue)
    {
        super(baseValue);
        this.derivedValue = derivedValue;
    }
}
