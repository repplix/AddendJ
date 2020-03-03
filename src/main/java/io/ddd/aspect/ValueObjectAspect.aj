package io.ddd.aspect;


//Import nicht entfernen! Import wird von Intellij fälschlicherweise ausgegraut
import io.ddd.stereotype.applicationcore.ValueObject;
import io.ddd.aspect.reflection.DeepEqualsAndHashCode;
import io.ddd.aspect.reflection.ReflectiveToString;

/**
 * Fügt allen ValueObjects jeweils Implementierungen der Methoden {@link Object#toString()}, {@link Object#hashCode()} und
 * {@link Object#equals(Object)} hinzu.
 */
@SuppressWarnings("DanglingJavadoc") public aspect ValueObjectAspect
{
    /**
     * Pseudointerface, dem {@link Object#toString()}, {@link Object#hashCode()} und
     * {@link Object#equals(Object)} Methoden hinzugefügt werden.Alle ValueObjects
     * werden durch den Aspekt so erweitert, dass diese das Interface implementieren. Dabei ist zu beachten, dass dieses Interface nicht explizit in der ValueObject Klasse gesetzt werden darf.
     */
    private interface IValueObjectAspect
    {
    }

    /**
     * Deklariert die Erweiterung aller Klassen, die mit {@link de.dillinger.stw.roheisenkanal.stereotyp.ValueObject} annotiert sind um die Implementierung des Interfaces {@link IValueObjectAspect}.
     */
    // Das Leerzeichen zwischen der Annotation und "*" ist signifikant!
    // @formatter:off
    declare parents :(@ValueObject *)implements IValueObjectAspect;
    // @formatter:on

    /**
     * Fügt {@link IValueObjectAspect} eine Implmentierung der {@link Object#toString()} Methode hinzu.
     * Diese delegiert zu {@link ReflectiveToString#toString()}
     *
     * @return Eine Stringrepräsentation der Klasse.
     */
    public String IValueObjectAspect.toString()
            {
                return ReflectiveToString.toString(this);
            }

    /**
     * Fügt {@link IValueObjectAspect} eine Implementierung der {@link Object#equals(Object)} Methode hinzu.
     * Diese delegiert zu {@link DeepEqualsAndHashCode#isReflectiveEqual(Object, Object)} und führt den vergleich mit {@code this} und {@code other} aus.
     *
     * @param other Das Objekt mit dem dieses verglichen wird.
     * @return True, wenn das andere Objekt inhaltlich gleich mit diesen ist, false wenn nicht.
     */
    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    public boolean IValueObjectAspect.equals(Object other)
            {
                return DeepEqualsAndHashCode.isReflectiveEqual(this, other);
            }

    /**
     * Fügt {@link IValueObjectAspect} eine implementation der {@link Object#hashCode()} Methode hinzu.
     * Diese delegiert zu {@link DeepEqualsAndHashCode#reflectiveHashCode(Object)}
     * @return Einen hash der klasse.
     */
    public int IValueObjectAspect.hashCode()
            {
                return DeepEqualsAndHashCode.reflectiveHashCode(this);
            }
}
