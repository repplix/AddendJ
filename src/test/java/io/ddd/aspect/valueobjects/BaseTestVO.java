package io.ddd.aspect.valueobjects;


import io.ddd.stereotype.applicationcore.ValueObject;

/**
 * Immutable Klasse, die als Basis-Klasse dient zum erben beim testen von VererbungsHierarchien.
 */
@ValueObject
public class BaseTestVO
{
    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private final int a;

    public BaseTestVO(final int a)
    {
        this.a = a;
    }
}
