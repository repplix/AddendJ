package io.jexxa.addendj.aspect.valueobjects;


import io.jexxa.addend.applicationcore.ValueObject;

/**
 * Immutable Klasse, die einen Integer als Attribut besitzt.
 */
@SuppressWarnings("unused")
@ValueObject
public class IntTestVO
{
    @SuppressWarnings("FieldCanBeLocal")
    private final int a;

    public IntTestVO(final int a)
    {
        this.a = a;
    }
}
