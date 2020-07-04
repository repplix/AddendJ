package io.jexxa.addendj.applicationcore.testclasses.valueobject;


import io.jexxa.addend.applicationcore.ValueObject;

/**
 * ValueObject for testing purpose including all kind of primitive Java data types  
 */
@SuppressWarnings({"FieldCanBeLocal", "unused"})
@ValueObject
public class PrimitiveDataTypeValueObject
{
    private final int intValue;
    private final long longValue;
    private final float floatValue;
    private final double doubleValue;
    private final boolean booleanValue;
    private final char charValue;
    private final byte byteValue;
    private final short shortValue;

    public PrimitiveDataTypeValueObject(int intValue,
                                        long longValue,
                                        float floatValue,
                                        double doubleValue,
                                        boolean booleanValue,
                                        char charValue,
                                        byte byteValue,
                                        short shortValue)
    {
        this.intValue = intValue;
        this.longValue = longValue;
        this.floatValue = floatValue;
        this.doubleValue = doubleValue;
        this.booleanValue = booleanValue;
        this.charValue = charValue;
        this.byteValue = byteValue;
        this.shortValue = shortValue;
    }

    public static PrimitiveDataTypeValueObject newInstanceWithMinValues()
    {
        return  new PrimitiveDataTypeValueObject(
                Integer.MIN_VALUE,
                Long.MIN_VALUE,
                Float.MIN_VALUE,
                Double.MIN_VALUE,
                Boolean.TRUE,
                Character.MIN_VALUE,
                Byte.MIN_VALUE,
                Short.MIN_VALUE);
    }


    public static PrimitiveDataTypeValueObject newInstanceWithMaxValues()
    {
        return  new PrimitiveDataTypeValueObject(
                Integer.MAX_VALUE,
                Long.MAX_VALUE,
                Float.MAX_VALUE, Double.MAX_VALUE,
                Boolean.FALSE,
                Character.MAX_VALUE,
                Byte.MAX_VALUE,
                Short.MAX_VALUE);
    }

}
