/**
 * CouponResponseMessage.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hy.pull.common.util.game.hb;

public class CouponResponseMessage  implements java.io.Serializable {
    private java.lang.String couponId;

    private boolean isQueued;

    private java.lang.String bonusBalanceId;

    private java.lang.String code;

    private boolean isException;

    private boolean success;

    private java.math.BigDecimal amount;

    private int freeSpins;

    private com.hy.pull.common.util.game.hb.InfoMessage error;

    private boolean setToActive;

    public CouponResponseMessage() {
    }

    public CouponResponseMessage(
           java.lang.String couponId,
           boolean isQueued,
           java.lang.String bonusBalanceId,
           java.lang.String code,
           boolean isException,
           boolean success,
           java.math.BigDecimal amount,
           int freeSpins,
           com.hy.pull.common.util.game.hb.InfoMessage error,
           boolean setToActive) {
           this.couponId = couponId;
           this.isQueued = isQueued;
           this.bonusBalanceId = bonusBalanceId;
           this.code = code;
           this.isException = isException;
           this.success = success;
           this.amount = amount;
           this.freeSpins = freeSpins;
           this.error = error;
           this.setToActive = setToActive;
    }


    /**
     * Gets the couponId value for this CouponResponseMessage.
     * 
     * @return couponId
     */
    public java.lang.String getCouponId() {
        return couponId;
    }


    /**
     * Sets the couponId value for this CouponResponseMessage.
     * 
     * @param couponId
     */
    public void setCouponId(java.lang.String couponId) {
        this.couponId = couponId;
    }


    /**
     * Gets the isQueued value for this CouponResponseMessage.
     * 
     * @return isQueued
     */
    public boolean isIsQueued() {
        return isQueued;
    }


    /**
     * Sets the isQueued value for this CouponResponseMessage.
     * 
     * @param isQueued
     */
    public void setIsQueued(boolean isQueued) {
        this.isQueued = isQueued;
    }


    /**
     * Gets the bonusBalanceId value for this CouponResponseMessage.
     * 
     * @return bonusBalanceId
     */
    public java.lang.String getBonusBalanceId() {
        return bonusBalanceId;
    }


    /**
     * Sets the bonusBalanceId value for this CouponResponseMessage.
     * 
     * @param bonusBalanceId
     */
    public void setBonusBalanceId(java.lang.String bonusBalanceId) {
        this.bonusBalanceId = bonusBalanceId;
    }


    /**
     * Gets the code value for this CouponResponseMessage.
     * 
     * @return code
     */
    public java.lang.String getCode() {
        return code;
    }


    /**
     * Sets the code value for this CouponResponseMessage.
     * 
     * @param code
     */
    public void setCode(java.lang.String code) {
        this.code = code;
    }


    /**
     * Gets the isException value for this CouponResponseMessage.
     * 
     * @return isException
     */
    public boolean isIsException() {
        return isException;
    }


    /**
     * Sets the isException value for this CouponResponseMessage.
     * 
     * @param isException
     */
    public void setIsException(boolean isException) {
        this.isException = isException;
    }


    /**
     * Gets the success value for this CouponResponseMessage.
     * 
     * @return success
     */
    public boolean isSuccess() {
        return success;
    }


    /**
     * Sets the success value for this CouponResponseMessage.
     * 
     * @param success
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }


    /**
     * Gets the amount value for this CouponResponseMessage.
     * 
     * @return amount
     */
    public java.math.BigDecimal getAmount() {
        return amount;
    }


    /**
     * Sets the amount value for this CouponResponseMessage.
     * 
     * @param amount
     */
    public void setAmount(java.math.BigDecimal amount) {
        this.amount = amount;
    }


    /**
     * Gets the freeSpins value for this CouponResponseMessage.
     * 
     * @return freeSpins
     */
    public int getFreeSpins() {
        return freeSpins;
    }


    /**
     * Sets the freeSpins value for this CouponResponseMessage.
     * 
     * @param freeSpins
     */
    public void setFreeSpins(int freeSpins) {
        this.freeSpins = freeSpins;
    }


    /**
     * Gets the error value for this CouponResponseMessage.
     * 
     * @return error
     */
    public com.hy.pull.common.util.game.hb.InfoMessage getError() {
        return error;
    }


    /**
     * Sets the error value for this CouponResponseMessage.
     * 
     * @param error
     */
    public void setError(com.hy.pull.common.util.game.hb.InfoMessage error) {
        this.error = error;
    }


    /**
     * Gets the setToActive value for this CouponResponseMessage.
     * 
     * @return setToActive
     */
    public boolean isSetToActive() {
        return setToActive;
    }


    /**
     * Sets the setToActive value for this CouponResponseMessage.
     * 
     * @param setToActive
     */
    public void setSetToActive(boolean setToActive) {
        this.setToActive = setToActive;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CouponResponseMessage)) return false;
        CouponResponseMessage other = (CouponResponseMessage) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.couponId==null && other.getCouponId()==null) || 
             (this.couponId!=null &&
              this.couponId.equals(other.getCouponId()))) &&
            this.isQueued == other.isIsQueued() &&
            ((this.bonusBalanceId==null && other.getBonusBalanceId()==null) || 
             (this.bonusBalanceId!=null &&
              this.bonusBalanceId.equals(other.getBonusBalanceId()))) &&
            ((this.code==null && other.getCode()==null) || 
             (this.code!=null &&
              this.code.equals(other.getCode()))) &&
            this.isException == other.isIsException() &&
            this.success == other.isSuccess() &&
            ((this.amount==null && other.getAmount()==null) || 
             (this.amount!=null &&
              this.amount.equals(other.getAmount()))) &&
            this.freeSpins == other.getFreeSpins() &&
            ((this.error==null && other.getError()==null) || 
             (this.error!=null &&
              this.error.equals(other.getError()))) &&
            this.setToActive == other.isSetToActive();
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
        if (getCouponId() != null) {
            _hashCode += getCouponId().hashCode();
        }
        _hashCode += (isIsQueued() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getBonusBalanceId() != null) {
            _hashCode += getBonusBalanceId().hashCode();
        }
        if (getCode() != null) {
            _hashCode += getCode().hashCode();
        }
        _hashCode += (isIsException() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isSuccess() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getAmount() != null) {
            _hashCode += getAmount().hashCode();
        }
        _hashCode += getFreeSpins();
        if (getError() != null) {
            _hashCode += getError().hashCode();
        }
        _hashCode += (isSetToActive() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CouponResponseMessage.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "CouponResponseMessage"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("couponId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "CouponId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isQueued");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "IsQueued"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bonusBalanceId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "BonusBalanceId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
        elemField.setFieldName("isException");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "IsException"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("success");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "Success"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("amount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "Amount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("freeSpins");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "FreeSpins"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("error");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "Error"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "InfoMessage"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("setToActive");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "SetToActive"));
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
