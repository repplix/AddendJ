package io.jexxa.addendj.aspect.reflection;

/**
 * Runtime exception which is used if we cannot access attributes of an object using reflection
 */
public class AttributeAccessException extends RuntimeException
{
    public AttributeAccessException(final String message, final Throwable cause)
    {
        super(message, cause);
    }
}
