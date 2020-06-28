package io.jexxa.addendj.domain.valueobject;


import io.jexxa.addend.applicationcore.ValueObject;

/**
 * Immutable Klasse, die einen Float als Attribut besitzt.
 */
@ValueObject
public class FloatValueObject
{
    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private final float value;

    public FloatValueObject(float a)
    {
        this.value = a;
    }
}
