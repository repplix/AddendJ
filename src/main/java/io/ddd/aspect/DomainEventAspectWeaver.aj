package io.ddd.aspect;

//Import nicht entfernen! Import wird von Intellij fälschlicherweise ausgegraut
import io.ddd.stereotype.applicationcore.DomainEvent;
import io.ddd.aspect.reflection.ReflectiveToString;


/**
 * Fügt allen DomainEvents eine Implementierungen der Methode {@link Object#toString()} hinzu.
 */
@SuppressWarnings("DanglingJavadoc") public aspect DomainEventAspectWeaver
{
    /**
     * Pseudointerface, dem {@link Object#toString()} Methoden hinzugefügt werden.Alle DomainEvents
     * werden durch den Aspekt so erweitert, dass diese das Interface implementieren. Dabei ist zu beachten, dass dieses Interface nicht explizit in der DomainEvent Klasse gesetzt werden darf.
     */
    private interface IDomainEventAspect
    {
    }


    /**
     * Deklariert die Erweiterung aller Klassen, die mit {@link de.dillinger.stw.roheisenkanal.stereotyp.DomainEvent} annotiert sind um die Implementierung des Interfaces {@link IDomainEventAspect}.
     */
    // Das Leerzeichen zwischen der Annotation und "*" ist signifikant!
    // @formatter:off
    declare parents :(@DomainEvent *)implements IDomainEventAspect;
    // @formatter:on

    /**
     * Fügt {@link IDomainEventAspect} eine Implementierung  der {@link Object#toString()} Methode hinzu.
     * Diese delegiert zu {@link ReflectiveToString#toString()}
     *
     * @return Eine Stringrepräsentation der Klasse.
     */
    public String IDomainEventAspect.toString()
            {
                return ReflectiveToString.toString(this);
            }

}
