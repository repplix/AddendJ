package io.jexxa.addendj.aspect;


import io.jexxa.addendj.aspect.reflection.DeepEqualsAndHashCode;
import io.jexxa.addendj.aspect.reflection.DeepToString;
//Do NOT remove import! Some IDEs (such as Intellij detect as unrequired import which is wrong here.
import io.jexxa.addend.applicationcore.ValueObject;

/**
 * Weaving following methods into Objects annotated with @ValueObject
 * {@link Object#toString()}
 * {@link Object#hashCode()}
 * {@link Object#equals(Object).
 */
@SuppressWarnings("DanglingJavadoc") public aspect ValueObjectAspect
{
    /**
     * Interface for weaving methods into a @ValueObject
     */
    private interface IValueObjectAspect
    {
    }

    /**
     * Extensions are made for all classes annotated with @ValueObject
     */
    // Disable formatter because space between annotation and '*' is important
    // @formatter:off
    declare parents :(@ValueObject *)implements IValueObjectAspect;
    // @formatter:on

    public String IValueObjectAspect.toString()
            {
                return DeepToString.toString(this);
            }

    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    public boolean IValueObjectAspect.equals(Object other)
            {
                return DeepEqualsAndHashCode.deepEquals(this, other);
            }

    public int IValueObjectAspect.hashCode()
            {
                return DeepEqualsAndHashCode.deepHashCode(this);
            }
}
