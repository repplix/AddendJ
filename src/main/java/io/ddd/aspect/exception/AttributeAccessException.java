package io.ddd.aspect.exception;

/**
 * Diese Exception wird geworfen, falls es nicht möglich ist über Reflection auf ein
 * Attribut oder Feld einer Klasse zuzugreifen.
 */
public class AttributeAccessException extends RuntimeException
{
    /**
     * Konstruktor
     *
     * @param message Die angezeigte Nachricht.
     * @param cause   Der grund warum die Exception ausgelöst wurde
     */
    public AttributeAccessException(final String message, final Throwable cause)
    {
        super(message, cause);
    }
}
