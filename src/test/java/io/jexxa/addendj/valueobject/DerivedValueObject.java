package io.jexxa.addendj.valueobject;


import io.jexxa.addend.applicationcore.ValueObject;

@ValueObject
public class DerivedValueObject extends BaseValueObject
{
    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private final int derivedValue;

    public DerivedValueObject(final int derivedValue, final int baseValue)
    {
        super(baseValue);
        this.derivedValue = derivedValue;
    }
}
