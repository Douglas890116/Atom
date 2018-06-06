/**
 * BaseRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hy.pull.common.util.game.hb;

public class BaseRequest  implements java.io.Serializable {
    private java.lang.String brandId;

    private java.lang.String APIKey;

    private java.lang.String playerHostAddress;

    private java.lang.String CFCId;

    private java.lang.String locale;

    public BaseRequest() {
    }

    public BaseRequest(
           java.lang.String brandId,
           java.lang.String APIKey,
           java.lang.String playerHostAddress,
           java.lang.String CFCId,
           java.lang.String locale) {
           this.brandId = brandId;
           this.APIKey = APIKey;
           this.playerHostAddress = playerHostAddress;
           this.CFCId = CFCId;
           this.locale = locale;
    }


    /**
     * Gets the brandId value for this BaseRequest.
     * 
     * @return brandId
     */
    public java.lang.String getBrandId() {
        return brandId;
    }


    /**
     * Sets the brandId value for this BaseRequest.
     * 
     * @param brandId
     */
    public void setBrandId(java.lang.String brandId) {
        this.brandId = brandId;
    }


    /**
     * Gets the APIKey value for this BaseRequest.
     * 
     * @return APIKey
     */
    public java.lang.String getAPIKey() {
        return APIKey;
    }


    /**
     * Sets the APIKey value for this BaseRequest.
     * 
     * @param APIKey
     */
    public void setAPIKey(java.lang.String APIKey) {
        this.APIKey = APIKey;
    }


    /**
     * Gets the playerHostAddress value for this BaseRequest.
     * 
     * @return playerHostAddress
     */
    public java.lang.String getPlayerHostAddress() {
        return playerHostAddress;
    }


    /**
     * Sets the playerHostAddress value for this BaseRequest.
     * 
     * @param playerHostAddress
     */
    public void setPlayerHostAddress(java.lang.String playerHostAddress) {
        this.playerHostAddress = playerHostAddress;
    }


    /**
     * Gets the CFCId value for this BaseRequest.
     * 
     * @return CFCId
     */
    public java.lang.String getCFCId() {
        return CFCId;
    }


    /**
     * Sets the CFCId value for this BaseRequest.
     * 
     * @param CFCId
     */
    public void setCFCId(java.lang.String CFCId) {
        this.CFCId = CFCId;
    }


    /**
     * Gets the locale value for this BaseRequest.
     * 
     * @return locale
     */
    public java.lang.String getLocale() {
        return locale;
    }


    /**
     * Sets the locale value for this BaseRequest.
     * 
     * @param locale
     */
    public void setLocale(java.lang.String locale) {
        this.locale = locale;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BaseRequest)) return false;
        BaseRequest other = (BaseRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.brandId==null && other.getBrandId()==null) || 
             (this.brandId!=null &&
              this.brandId.equals(other.getBrandId()))) &&
            ((this.APIKey==null && other.getAPIKey()==null) || 
             (this.APIKey!=null &&
              this.APIKey.equals(other.getAPIKey()))) &&
            ((this.playerHostAddress==null && other.getPlayerHostAddress()==null) || 
             (this.playerHostAddress!=null &&
              this.playerHostAddress.equals(other.getPlayerHostAddress()))) &&
            ((this.CFCId==null && other.getCFCId()==null) || 
             (this.CFCId!=null &&
              this.CFCId.equals(other.getCFCId()))) &&
            ((this.locale==null && other.getLocale()==null) || 
             (this.locale!=null &&
              this.locale.equals(other.getLocale())));
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
        if (getBrandId() != null) {
            _hashCode += getBrandId().hashCode();
        }
        if (getAPIKey() != null) {
            _hashCode += getAPIKey().hashCode();
        }
        if (getPlayerHostAddress() != null) {
            _hashCode += getPlayerHostAddress().hashCode();
        }
        if (getCFCId() != null) {
            _hashCode += getCFCId().hashCode();
        }
        if (getLocale() != null) {
            _hashCode += getLocale().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BaseRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "BaseRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("brandId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "BrandId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("APIKey");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "APIKey"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("playerHostAddress");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "PlayerHostAddress"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CFCId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "CFCId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("locale");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "Locale"));
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
