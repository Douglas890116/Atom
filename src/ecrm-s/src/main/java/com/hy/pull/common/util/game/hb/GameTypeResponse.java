/**
 * GameTypeResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hy.pull.common.util.game.hb;

public class GameTypeResponse  extends com.hy.pull.common.util.game.hb.BaseResponse  implements java.io.Serializable {
    private com.hy.pull.common.util.game.hb.GameTypeClientDbDTO[] gameTypes;

    public GameTypeResponse() {
    }

    public GameTypeResponse(
           com.hy.pull.common.util.game.hb.GameTypeClientDbDTO[] gameTypes) {
        this.gameTypes = gameTypes;
    }


    /**
     * Gets the gameTypes value for this GameTypeResponse.
     * 
     * @return gameTypes
     */
    public com.hy.pull.common.util.game.hb.GameTypeClientDbDTO[] getGameTypes() {
        return gameTypes;
    }


    /**
     * Sets the gameTypes value for this GameTypeResponse.
     * 
     * @param gameTypes
     */
    public void setGameTypes(com.hy.pull.common.util.game.hb.GameTypeClientDbDTO[] gameTypes) {
        this.gameTypes = gameTypes;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GameTypeResponse)) return false;
        GameTypeResponse other = (GameTypeResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.gameTypes==null && other.getGameTypes()==null) || 
             (this.gameTypes!=null &&
              java.util.Arrays.equals(this.gameTypes, other.getGameTypes())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getGameTypes() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getGameTypes());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getGameTypes(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GameTypeResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameTypeResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("gameTypes");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameTypes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameTypeClientDbDTO"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameTypeClientDbDTO"));
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
