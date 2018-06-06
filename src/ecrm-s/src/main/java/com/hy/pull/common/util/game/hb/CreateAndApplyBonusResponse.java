/**
 * CreateAndApplyBonusResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hy.pull.common.util.game.hb;

public class CreateAndApplyBonusResponse  implements java.io.Serializable {
    private boolean created;

    private boolean redeemed;

    private java.lang.String message;

    private java.lang.String bonusBalanceId;

    private java.lang.String couponCodeCreated;

    public CreateAndApplyBonusResponse() {
    }

    public CreateAndApplyBonusResponse(
           boolean created,
           boolean redeemed,
           java.lang.String message,
           java.lang.String bonusBalanceId,
           java.lang.String couponCodeCreated) {
           this.created = created;
           this.redeemed = redeemed;
           this.message = message;
           this.bonusBalanceId = bonusBalanceId;
           this.couponCodeCreated = couponCodeCreated;
    }


    /**
     * Gets the created value for this CreateAndApplyBonusResponse.
     * 
     * @return created
     */
    public boolean isCreated() {
        return created;
    }


    /**
     * Sets the created value for this CreateAndApplyBonusResponse.
     * 
     * @param created
     */
    public void setCreated(boolean created) {
        this.created = created;
    }


    /**
     * Gets the redeemed value for this CreateAndApplyBonusResponse.
     * 
     * @return redeemed
     */
    public boolean isRedeemed() {
        return redeemed;
    }


    /**
     * Sets the redeemed value for this CreateAndApplyBonusResponse.
     * 
     * @param redeemed
     */
    public void setRedeemed(boolean redeemed) {
        this.redeemed = redeemed;
    }


    /**
     * Gets the message value for this CreateAndApplyBonusResponse.
     * 
     * @return message
     */
    public java.lang.String getMessage() {
        return message;
    }


    /**
     * Sets the message value for this CreateAndApplyBonusResponse.
     * 
     * @param message
     */
    public void setMessage(java.lang.String message) {
        this.message = message;
    }


    /**
     * Gets the bonusBalanceId value for this CreateAndApplyBonusResponse.
     * 
     * @return bonusBalanceId
     */
    public java.lang.String getBonusBalanceId() {
        return bonusBalanceId;
    }


    /**
     * Sets the bonusBalanceId value for this CreateAndApplyBonusResponse.
     * 
     * @param bonusBalanceId
     */
    public void setBonusBalanceId(java.lang.String bonusBalanceId) {
        this.bonusBalanceId = bonusBalanceId;
    }


    /**
     * Gets the couponCodeCreated value for this CreateAndApplyBonusResponse.
     * 
     * @return couponCodeCreated
     */
    public java.lang.String getCouponCodeCreated() {
        return couponCodeCreated;
    }


    /**
     * Sets the couponCodeCreated value for this CreateAndApplyBonusResponse.
     * 
     * @param couponCodeCreated
     */
    public void setCouponCodeCreated(java.lang.String couponCodeCreated) {
        this.couponCodeCreated = couponCodeCreated;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CreateAndApplyBonusResponse)) return false;
        CreateAndApplyBonusResponse other = (CreateAndApplyBonusResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.created == other.isCreated() &&
            this.redeemed == other.isRedeemed() &&
            ((this.message==null && other.getMessage()==null) || 
             (this.message!=null &&
              this.message.equals(other.getMessage()))) &&
            ((this.bonusBalanceId==null && other.getBonusBalanceId()==null) || 
             (this.bonusBalanceId!=null &&
              this.bonusBalanceId.equals(other.getBonusBalanceId()))) &&
            ((this.couponCodeCreated==null && other.getCouponCodeCreated()==null) || 
             (this.couponCodeCreated!=null &&
              this.couponCodeCreated.equals(other.getCouponCodeCreated())));
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
        _hashCode += (isCreated() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isRedeemed() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getMessage() != null) {
            _hashCode += getMessage().hashCode();
        }
        if (getBonusBalanceId() != null) {
            _hashCode += getBonusBalanceId().hashCode();
        }
        if (getCouponCodeCreated() != null) {
            _hashCode += getCouponCodeCreated().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CreateAndApplyBonusResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "CreateAndApplyBonusResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("created");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "Created"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("redeemed");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "Redeemed"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("message");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "Message"));
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
        elemField.setFieldName("couponCodeCreated");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "CouponCodeCreated"));
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
