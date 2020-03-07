package io.ddd.aspect;


//Do NOT remove import! Some IDEs (such as Intellij detect as unrequired import which is wrong.
import io.ddd.stereotype.applicationcore.ValueObject;
import io.ddd.aspect.reflection.DeepEqualsAndHashCode;
import io.ddd.aspect.reflection.ReflectiveToString;

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
                return ReflectiveToString.toString(this);
            }

    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    public boolean IValueObjectAspect.equals(Object other)
            {
                return DeepEqualsAndHashCode.isReflectiveEquals(this, other);
            }

    public int IValueObjectAspect.hashCode()
            {
                return DeepEqualsAndHashCode.reflectiveHashCode(this);
            }
}
