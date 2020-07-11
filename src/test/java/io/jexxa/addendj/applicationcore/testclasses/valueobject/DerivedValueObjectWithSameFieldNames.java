package io.jexxa.addendj.applicationcore.testclasses.valueobject;


import io.jexxa.addend.applicationcore.ValueObject;

@SuppressWarnings({"unused", "FieldCanBeLocal"})
@ValueObject
public class DerivedValueObjectWithSameFieldNames extends BaseValueObject
{
    private final int value;

    public DerivedValueObjectWithSameFieldNames(final int thisValue, final int baseValue)
    {
        super(baseValue);
        this.value = thisValue;
    }
}
