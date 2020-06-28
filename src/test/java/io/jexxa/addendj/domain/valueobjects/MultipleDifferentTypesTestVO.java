package io.jexxa.addendj.domain.valueobjects;


import io.jexxa.addend.applicationcore.ValueObject;

/**
 * Immutable Klasse, die viele verschiedene Typen als Attribut hat.
 */
@SuppressWarnings({"FieldCanBeLocal", "unused"})
@ValueObject
public class MultipleDifferentTypesTestVO
{
    private final int a;
    private final long b;
    private final float c;
    private final double d;
    private final boolean e;
    private final char f;
    private final String g;
    private final byte h;
    private final short i;

    public MultipleDifferentTypesTestVO(final int a, final long b, final float c, final double d, final boolean e, final char f, final String g, final byte h, final short i)
    {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.f = f;
        this.g = g;
        this.h = h;
        this.i = i;
    }
}
