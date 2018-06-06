/**
 * SetBonusBalanceActiveRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hy.pull.common.util.game.hb;

public class SetBonusBalanceActiveRequest  extends com.hy.pull.common.util.game.hb.BaseRequest  implements java.io.Serializable {
    private java.lang.String username;

    private java.lang.String bonusBalanceId;

    private boolean isActive;

    public SetBonusBalanceActiveRequest() {
    }

    public SetBonusBalanceActiveRequest(
           java.lang.String brandId,
           java.lang.String APIKey,
           java.lang.String playerHostAddress,
           java.lang.String CFCId,
           java.lang.String locale,
           java.lang.String username,
           java.lang.String bonusBalanceId,
           boolean isActive) {
        super(
            brandId,
            APIKey,
            playerHostAddress,
            CFCId,
            locale);
        this.username = username;
        this.bonusBalanceId = bonusBalanceId;
        this.isActive = isActive;
    }


    /**
     * Gets the username value for this SetBonusBalanceActiveRequest.
     * 
     * @return username
     */
    public java.lang.String getUsername() {
        return username;
    }


    /**
     * Sets the username value for this SetBonusBalanceActiveRequest.
     * 
     * @param username
     */
    public void setUsername(java.lang.String username) {
        this.username = username;
    }


    /**
     * Gets the bonusBalanceId value for this SetBonusBalanceActiveRequest.
     * 
     * @return bonusBalanceId
     */
    public java.lang.String getBonusBalanceId() {
        return bonusBalanceId;
    }


    /**
     * Sets the bonusBalanceId value for this SetBonusBalanceActiveRequest.
     * 
     * @param bonusBalanceId
     */
    public void setBonusBalanceId(java.lang.String bonusBalanceId) {
        this.bonusBalanceId = bonusBalanceId;
    }


    /**
     * Gets the isActive value for this SetBonusBalanceActiveRequest.
     * 
     * @return isActive
     */
    public boolean isIsActive() {
        return isActive;
    }


    /**
     * Sets the isActive value for this SetBonusBalanceActiveRequest.
     * 
     * @param isActive
     */
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SetBonusBalanceActiveRequest)) return false;
        SetBonusBalanceActiveRequest other = (SetBonusBalanceActiveRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.username==null && other.getUsername()==null) || 
             (this.username!=null &&
              this.username.equals(other.getUsername()))) &&
            ((this.bonusBalanceId==null && other.getBonusBalanceId()==null) || 
             (this.bonusBalanceId!=null &&
              this.bonusBalanceId.equals(other.getBonusBalanceId()))) &&
            this.isActive == other.isIsActive();
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
        if (getUsername() != null) {
            _hashCode += getUsername().hashCode();
        }
        if (getBonusBalanceId() != null) {
            _hashCode += getBonusBalanceId().hashCode();
        }
        _hashCode += (isIsActive() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SetBonusBalanceActiveRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "SetBonusBalanceActiveRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("username");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "Username"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bonusBalanceId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "BonusBalanceId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isActive");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "IsActive"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
