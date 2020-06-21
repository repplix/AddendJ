package io.ddd.aspect.valueobjects;


import io.jexxa.addend.applicationcore.ValueObject;

/**
 * Immutable Klasse, die von einer BasisKlasse erbt .
 */

@ValueObject
public class DerivedTestVO extends BaseTestVO
{
    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private final int b;

    public DerivedTestVO(final int b, final int a)
    {
        super(a);
        this.b = b;
    }
}
