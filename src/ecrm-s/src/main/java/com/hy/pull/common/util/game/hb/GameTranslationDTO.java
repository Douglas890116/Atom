/**
 * GameTranslationDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hy.pull.common.util.game.hb;

public class GameTranslationDTO  implements java.io.Serializable {
    private short languageId;

    private java.lang.String locale;

    private java.lang.String translation;

    public GameTranslationDTO() {
    }

    public GameTranslationDTO(
           short languageId,
           java.lang.String locale,
           java.lang.String translation) {
           this.languageId = languageId;
           this.locale = locale;
           this.translation = translation;
    }


    /**
     * Gets the languageId value for this GameTranslationDTO.
     * 
     * @return languageId
     */
    public short getLanguageId() {
        return languageId;
    }


    /**
     * Sets the languageId value for this GameTranslationDTO.
     * 
     * @param languageId
     */
    public void setLanguageId(short languageId) {
        this.languageId = languageId;
    }


    /**
     * Gets the locale value for this GameTranslationDTO.
     * 
     * @return locale
     */
    public java.lang.String getLocale() {
        return locale;
    }


    /**
     * Sets the locale value for this GameTranslationDTO.
     * 
     * @param locale
     */
    public void setLocale(java.lang.String locale) {
        this.locale = locale;
    }


    /**
     * Gets the translation value for this GameTranslationDTO.
     * 
     * @return translation
     */
    public java.lang.String getTranslation() {
        return translation;
    }


    /**
     * Sets the translation value for this GameTranslationDTO.
     * 
     * @param translation
     */
    public void setTranslation(java.lang.String translation) {
        this.translation = translation;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GameTranslationDTO)) return false;
        GameTranslationDTO other = (GameTranslationDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.languageId == other.getLanguageId() &&
            ((this.locale==null && other.getLocale()==null) || 
             (this.locale!=null &&
              this.locale.equals(other.getLocale()))) &&
            ((this.translation==null && other.getTranslation()==null) || 
             (this.translation!=null &&
              this.translation.equals(other.getTranslation())));
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
        _hashCode += getLanguageId();
        if (getLocale() != null) {
            _hashCode += getLocale().hashCode();
        }
        if (getTranslation() != null) {
            _hashCode += getTranslation().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GameTranslationDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameTranslationDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("languageId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "LanguageId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("locale");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "Locale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("translation");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "Translation"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
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
