package io.jexxa.addendj.valueobject;


import io.jexxa.addend.applicationcore.ValueObject;

/**
 * Immutable Klasse, die drei Strings als Attribut hat.
 */

@SuppressWarnings({"FieldCanBeLocal", "unused"})
@ValueObject
public class ThreeStringsValueObject extends BaseValueObject
{
    @SuppressWarnings("FieldCanBeLocal")
    private final String b;
    private final String c;
    private final String d;

    public ThreeStringsValueObject(final int a, final String b, final String c, final String d)
    {
        super(a);
        this.b = b;
        this.c = c;
        this.d = d;
    }
}
