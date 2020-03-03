package io.ddd.aspect.valueobjects;


import io.ddd.stereotype.applicationcore.ValueObject;

/**
 * Immutable Klasse, die nur ein Integer array beinhaltete
 */
@SuppressWarnings("unused")
@ValueObject
public class ArrayIntTestVO
{
    @SuppressWarnings("FieldCanBeLocal")
    private final int[] a;

    public ArrayIntTestVO(final int[] a)
    {
        this.a = a;
    }
}
