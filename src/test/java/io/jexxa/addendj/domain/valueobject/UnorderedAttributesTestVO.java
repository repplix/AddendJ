package io.jexxa.addendj.domain.valueobject;


import io.jexxa.addend.applicationcore.ValueObject;

/**
 * Immutable Klasse, die Attribute in einer unsortierten Reihenfolge deklariert hat.
 */
@SuppressWarnings({"FieldCanBeLocal", "unused"})
@ValueObject
public class UnorderedAttributesTestVO
{
    @SuppressWarnings("FieldCanBeLocal")
    private final float c;
    private final double b;
    private final long a;
    private final byte d;

    public UnorderedAttributesTestVO(final float c, final double b, final long a, final byte d)
    {
        this.c = c;
        this.b = b;
        this.a = a;
        this.d = d;
    }
}
