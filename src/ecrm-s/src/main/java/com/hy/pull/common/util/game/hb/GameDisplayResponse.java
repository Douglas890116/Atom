/**
 * GameDisplayResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hy.pull.common.util.game.hb;

public class GameDisplayResponse  extends com.hy.pull.common.util.game.hb.BaseResponse  implements java.io.Serializable {
    private com.hy.pull.common.util.game.hb.Game[][] gameDisplay;

    public GameDisplayResponse() {
    }

    public GameDisplayResponse(
           com.hy.pull.common.util.game.hb.Game[][] gameDisplay) {
        this.gameDisplay = gameDisplay;
    }


    /**
     * Gets the gameDisplay value for this GameDisplayResponse.
     * 
     * @return gameDisplay
     */
    public com.hy.pull.common.util.game.hb.Game[][] getGameDisplay() {
        return gameDisplay;
    }


    /**
     * Sets the gameDisplay value for this GameDisplayResponse.
     * 
     * @param gameDisplay
     */
    public void setGameDisplay(com.hy.pull.common.util.game.hb.Game[][] gameDisplay) {
        this.gameDisplay = gameDisplay;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GameDisplayResponse)) return false;
        GameDisplayResponse other = (GameDisplayResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.gameDisplay==null && other.getGameDisplay()==null) || 
             (this.gameDisplay!=null &&
              java.util.Arrays.equals(this.gameDisplay, other.getGameDisplay())));
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
        if (getGameDisplay() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getGameDisplay());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getGameDisplay(), i);
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
        new org.apache.axis.description.TypeDesc(GameDisplayResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameDisplayResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("gameDisplay");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameDisplay"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "DisplayNode"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "DisplayNode"));
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
