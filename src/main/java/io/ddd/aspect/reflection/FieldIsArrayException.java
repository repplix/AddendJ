package io.ddd.aspect.reflection;

/**
 * Wird geworfen, sobald versucht wird auf ein Feld zuzugreifen, dessen Typ ein Array ist.
 */
public class FieldIsArrayException extends RuntimeException
{
    /**
     * Konstruktor
     *
     * @param message Die Nachricht, die angezeigt wird.
     */
    public FieldIsArrayException(final String message)
    {
        super(message);
    }
}
