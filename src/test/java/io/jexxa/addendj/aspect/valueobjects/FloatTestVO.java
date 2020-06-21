package io.jexxa.addendj.aspect.valueobjects;


import io.jexxa.addend.applicationcore.ValueObject;

/**
 * Immutable Klasse, die einen Float als Attribut besitzt.
 */
@ValueObject
public class FloatTestVO
{
    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private final float a;

    public FloatTestVO(final float a)
    {
        this.a = a;
    }
}
