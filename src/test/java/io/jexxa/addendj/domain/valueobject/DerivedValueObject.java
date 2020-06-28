package io.jexxa.addendj.domain.valueobject;


import io.jexxa.addend.applicationcore.ValueObject;

@ValueObject
public class DerivedValueObject extends BaseValueObject
{
    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private final int b;

    public DerivedValueObject(final int b, final int a)
    {
        super(a);
        this.b = b;
    }
}
