package io.jexxa.addendj.domain.valueobject;


import io.jexxa.addend.applicationcore.ValueObject;

/**
 * Immutable Klasse, die einen Integer als Attribut besitzt.
 */
@SuppressWarnings("unused")
@ValueObject
public class IntValueObject
{
    @SuppressWarnings("FieldCanBeLocal")
    private final int value;

    public IntValueObject(int value)
    {
        this.value = value;
    }
}
