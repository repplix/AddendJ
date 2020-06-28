package io.jexxa.addendj.domain.valueobject;


import io.jexxa.addend.applicationcore.ValueObject;

/**
 * Immutable Klasse, die von einer BasisKlasse abgeleitet ist.
 * Der Attribut-Name der Klasse ist identisch mit dem der OberKlasse.
 */
@SuppressWarnings("unused")
@ValueObject
public class DerivedValueObjectWithSameFieldNames extends BaseValueObject
{
    @SuppressWarnings("FieldCanBeLocal")
    private final int value;

    public DerivedValueObjectWithSameFieldNames(final int thisValue, final int baseValue)
    {
        super(baseValue);
        this.value = thisValue;
    }
}
