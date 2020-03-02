package io.ddd.aspect.valueobjects;


import io.ddd.stereotyp.applicationcore.ValueObject;

/**
 * Immutable Klasse, die drei Strings als Attribut hat.
 */

@SuppressWarnings({"FieldCanBeLocal", "unused"})
@ValueObject
public class ThreeStringsTestVO extends BaseTestVO
{
    @SuppressWarnings("FieldCanBeLocal")
    private final String b;
    private final String c;
    private final String d;

    public ThreeStringsTestVO(final int a, final String b, final String c, final String d)
    {
        super(a);
        this.b = b;
        this.c = c;
        this.d = d;
    }
}
