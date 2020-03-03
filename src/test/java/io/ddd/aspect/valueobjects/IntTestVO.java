package io.ddd.aspect.valueobjects;


import io.ddd.stereotype.applicationcore.ValueObject;

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
