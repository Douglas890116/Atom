/**
 * GameTypeClientDbDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hy.pull.common.util.game.hb;

public class GameTypeClientDbDTO  implements java.io.Serializable {
    private short gameTypeId;

    private java.lang.String langKey;

    private java.lang.String name;

    private com.hy.pull.common.util.game.hb.GameTranslationDTO[] translatedNames;

    public GameTypeClientDbDTO() {
    }

    public GameTypeClientDbDTO(
           short gameTypeId,
           java.lang.String langKey,
           java.lang.String name,
           com.hy.pull.common.util.game.hb.GameTranslationDTO[] translatedNames) {
           this.gameTypeId = gameTypeId;
           this.langKey = langKey;
           this.name = name;
           this.translatedNames = translatedNames;
    }


    /**
     * Gets the gameTypeId value for this GameTypeClientDbDTO.
     * 
     * @return gameTypeId
     */
    public short getGameTypeId() {
        return gameTypeId;
    }


    /**
     * Sets the gameTypeId value for this GameTypeClientDbDTO.
     * 
     * @param gameTypeId
     */
    public void setGameTypeId(short gameTypeId) {
        this.gameTypeId = gameTypeId;
    }


    /**
     * Gets the langKey value for this GameTypeClientDbDTO.
     * 
     * @return langKey
     */
    public java.lang.String getLangKey() {
        return langKey;
    }


    /**
     * Sets the langKey value for this GameTypeClientDbDTO.
     * 
     * @param langKey
     */
    public void setLangKey(java.lang.String langKey) {
        this.langKey = langKey;
    }


    /**
     * Gets the name value for this GameTypeClientDbDTO.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this GameTypeClientDbDTO.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the translatedNames value for this GameTypeClientDbDTO.
     * 
     * @return translatedNames
     */
    public com.hy.pull.common.util.game.hb.GameTranslationDTO[] getTranslatedNames() {
        return translatedNames;
    }


    /**
     * Sets the translatedNames value for this GameTypeClientDbDTO.
     * 
     * @param translatedNames
     */
    public void setTranslatedNames(com.hy.pull.common.util.game.hb.GameTranslationDTO[] translatedNames) {
        this.translatedNames = translatedNames;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GameTypeClientDbDTO)) return false;
        GameTypeClientDbDTO other = (GameTypeClientDbDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.gameTypeId == other.getGameTypeId() &&
            ((this.langKey==null && other.getLangKey()==null) || 
             (this.langKey!=null &&
              this.langKey.equals(other.getLangKey()))) &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.translatedNames==null && other.getTranslatedNames()==null) || 
             (this.translatedNames!=null &&
              java.util.Arrays.equals(this.translatedNames, other.getTranslatedNames())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        _hashCode += getGameTypeId();
        if (getLangKey() != null) {
            _hashCode += getLangKey().hashCode();
        }
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getTranslatedNames() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTranslatedNames());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTranslatedNames(), i);
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
        new org.apache.axis.description.TypeDesc(GameTypeClientDbDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameTypeClientDbDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("gameTypeId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameTypeId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("langKey");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "LangKey"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "Name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("translatedNames");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "TranslatedNames"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameTranslationDTO"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameTranslationDTO"));
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
