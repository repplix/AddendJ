package io.jexxa.addendj.domain.valueobject;


import io.jexxa.addend.applicationcore.ValueObject;

/**
 * Immutable Klasse, die von einer BasisKlasse abgeleitet ist.
 * Der Attribut-Name der Klasse ist identisch mit dem der OberKlasse.
 */
@SuppressWarnings("unused")
@ValueObject
public class DerivedSameAttributeNameTestVO extends BaseTestVO
{
    @SuppressWarnings("FieldCanBeLocal")
    private final int a;

    public DerivedSameAttributeNameTestVO(final int a, final int b)
    {
        super(b);
        this.a = a;
    }
}
