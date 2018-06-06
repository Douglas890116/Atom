/**
 * ApplyBonusToPlayerRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hy.pull.common.util.game.hb;

public class ApplyBonusToPlayerRequest  extends com.hy.pull.common.util.game.hb.BaseRequest  implements java.io.Serializable {
    private java.lang.String username;

    private java.lang.String code;

    private java.math.BigDecimal variableCouponAmount;

    private boolean replaceActiveCoupon;

    public ApplyBonusToPlayerRequest() {
    }

    public ApplyBonusToPlayerRequest(
           java.lang.String brandId,
           java.lang.String APIKey,
           java.lang.String playerHostAddress,
           java.lang.String CFCId,
           java.lang.String locale,
           java.lang.String username,
           java.lang.String code,
           java.math.BigDecimal variableCouponAmount,
           boolean replaceActiveCoupon) {
        super(
            brandId,
            APIKey,
            playerHostAddress,
            CFCId,
            locale);
        this.username = username;
        this.code = code;
        this.variableCouponAmount = variableCouponAmount;
        this.replaceActiveCoupon = replaceActiveCoupon;
    }


    /**
     * Gets the username value for this ApplyBonusToPlayerRequest.
     * 
     * @return username
     */
    public java.lang.String getUsername() {
        return username;
    }


    /**
     * Sets the username value for this ApplyBonusToPlayerRequest.
     * 
     * @param username
     */
    public void setUsername(java.lang.String username) {
        this.username = username;
    }


    /**
     * Gets the code value for this ApplyBonusToPlayerRequest.
     * 
     * @return code
     */
    public java.lang.String getCode() {
        return code;
    }


    /**
     * Sets the code value for this ApplyBonusToPlayerRequest.
     * 
     * @param code
     */
    public void setCode(java.lang.String code) {
        this.code = code;
    }


    /**
     * Gets the variableCouponAmount value for this ApplyBonusToPlayerRequest.
     * 
     * @return variableCouponAmount
     */
    public java.math.BigDecimal getVariableCouponAmount() {
        return variableCouponAmount;
    }


    /**
     * Sets the variableCouponAmount value for this ApplyBonusToPlayerRequest.
     * 
     * @param variableCouponAmount
     */
    public void setVariableCouponAmount(java.math.BigDecimal variableCouponAmount) {
        this.variableCouponAmount = variableCouponAmount;
    }


    /**
     * Gets the replaceActiveCoupon value for this ApplyBonusToPlayerRequest.
     * 
     * @return replaceActiveCoupon
     */
    public boolean isReplaceActiveCoupon() {
        return replaceActiveCoupon;
    }


    /**
     * Sets the replaceActiveCoupon value for this ApplyBonusToPlayerRequest.
     * 
     * @param replaceActiveCoupon
     */
    public void setReplaceActiveCoupon(boolean replaceActiveCoupon) {
        this.replaceActiveCoupon = replaceActiveCoupon;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ApplyBonusToPlayerRequest)) return false;
        ApplyBonusToPlayerRequest other = (ApplyBonusToPlayerRequest) obj;
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
            ((this.code==null && other.getCode()==null) || 
             (this.code!=null &&
              this.code.equals(other.getCode()))) &&
            ((this.variableCouponAmount==null && other.getVariableCouponAmount()==null) || 
             (this.variableCouponAmount!=null &&
              this.variableCouponAmount.equals(other.getVariableCouponAmount()))) &&
            this.replaceActiveCoupon == other.isReplaceActiveCoupon();
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
        if (getCode() != null) {
            _hashCode += getCode().hashCode();
        }
        if (getVariableCouponAmount() != null) {
            _hashCode += getVariableCouponAmount().hashCode();
        }
        _hashCode += (isReplaceActiveCoupon() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ApplyBonusToPlayerRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "ApplyBonusToPlayerRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("username");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "Username"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("code");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "Code"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("variableCouponAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "VariableCouponAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("replaceActiveCoupon");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "ReplaceActiveCoupon"));
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
