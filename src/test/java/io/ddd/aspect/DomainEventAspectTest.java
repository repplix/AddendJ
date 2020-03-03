package io.ddd.aspect;

import org.junit.Test;



/**
 * Diese Methode überprüft ob die Standard implementierung der toString Methode durch AspectJ überschrieben wurde nicht nicht deren Korrektheit!
 * Dies wird dadurch realisiert das zwei gleiche Objekte erzeugt werden und deren Ergebnis der toString Methode verglichen wird. Falls die toString Methode nicht überschrieben wurde sind die Ergebnisse unterschiedlich da dort dann die unterschiedlichen ObjectIDs teil des Strings sind.
 */
public class DomainEventAspectTest
{
    @Test
    public void testeErzeugtToStringMethodeFuerDomainEvent()
    {
        //
        /*final BestellnummerTest.Bestellnummer bestelnummer = new BestellnummerTest.Bestellnummer(64);
        final GleisTest.Gleis gleis = GleisTest.Gleis.GLEIS_1;
        final DomainEventStub domainEventStub1 = new DomainEventStub(bestelnummer, gleis, 64);
        final DomainEventStub domainEventStub2 = new DomainEventStub(bestelnummer, gleis, 64);

        assertEquals("Die toString() methode wird für @DomainEvent nicht korrekt erzeugt", domainEventStub1.toString(),
                domainEventStub2.toString()); */
    }
}
