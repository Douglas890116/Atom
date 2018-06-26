/**
 * ToggleBonusBalanceResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hy.pull.common.util.game.hb;

public class ToggleBonusBalanceResponse  implements java.io.Serializable {
    private java.lang.String bonusBalanceId;

    private boolean success;

    private boolean isActive;

    private java.lang.String message;

    private java.lang.String blockingGameNames;

    private int blockingCount;

    private java.lang.String couponCode;

    private java.lang.String playerId;

    private boolean overrideWagerLock;

    public ToggleBonusBalanceResponse() {
    }

    public ToggleBonusBalanceResponse(
           java.lang.String bonusBalanceId,
           boolean success,
           boolean isActive,
           java.lang.String message,
           java.lang.String blockingGameNames,
           int blockingCount,
           java.lang.String couponCode,
           java.lang.String playerId,
           boolean overrideWagerLock) {
           this.bonusBalanceId = bonusBalanceId;
           this.success = success;
           this.isActive = isActive;
           this.message = message;
           this.blockingGameNames = blockingGameNames;
           this.blockingCount = blockingCount;
           this.couponCode = couponCode;
           this.playerId = playerId;
           this.overrideWagerLock = overrideWagerLock;
    }


    /**
     * Gets the bonusBalanceId value for this ToggleBonusBalanceResponse.
     * 
     * @return bonusBalanceId
     */
    public java.lang.String getBonusBalanceId() {
        return bonusBalanceId;
    }


    /**
     * Sets the bonusBalanceId value for this ToggleBonusBalanceResponse.
     * 
     * @param bonusBalanceId
     */
    public void setBonusBalanceId(java.lang.String bonusBalanceId) {
        this.bonusBalanceId = bonusBalanceId;
    }


    /**
     * Gets the success value for this ToggleBonusBalanceResponse.
     * 
     * @return success
     */
    public boolean isSuccess() {
        return success;
    }


    /**
     * Sets the success value for this ToggleBonusBalanceResponse.
     * 
     * @param success
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }


    /**
     * Gets the isActive value for this ToggleBonusBalanceResponse.
     * 
     * @return isActive
     */
    public boolean isIsActive() {
        return isActive;
    }


    /**
     * Sets the isActive value for this ToggleBonusBalanceResponse.
     * 
     * @param isActive
     */
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }


    /**
     * Gets the message value for this ToggleBonusBalanceResponse.
     * 
     * @return message
     */
    public java.lang.String getMessage() {
        return message;
    }


    /**
     * Sets the message value for this ToggleBonusBalanceResponse.
     * 
     * @param message
     */
    public void setMessage(java.lang.String message) {
        this.message = message;
    }


    /**
     * Gets the blockingGameNames value for this ToggleBonusBalanceResponse.
     * 
     * @return blockingGameNames
     */
    public java.lang.String getBlockingGameNames() {
        return blockingGameNames;
    }


    /**
     * Sets the blockingGameNames value for this ToggleBonusBalanceResponse.
     * 
     * @param blockingGameNames
     */
    public void setBlockingGameNames(java.lang.String blockingGameNames) {
        this.blockingGameNames = blockingGameNames;
    }


    /**
     * Gets the blockingCount value for this ToggleBonusBalanceResponse.
     * 
     * @return blockingCount
     */
    public int getBlockingCount() {
        return blockingCount;
    }


    /**
     * Sets the blockingCount value for this ToggleBonusBalanceResponse.
     * 
     * @param blockingCount
     */
    public void setBlockingCount(int blockingCount) {
        this.blockingCount = blockingCount;
    }


    /**
     * Gets the couponCode value for this ToggleBonusBalanceResponse.
     * 
     * @return couponCode
     */
    public java.lang.String getCouponCode() {
        return couponCode;
    }


    /**
     * Sets the couponCode value for this ToggleBonusBalanceResponse.
     * 
     * @param couponCode
     */
    public void setCouponCode(java.lang.String couponCode) {
        this.couponCode = couponCode;
    }


    /**
     * Gets the playerId value for this ToggleBonusBalanceResponse.
     * 
     * @return playerId
     */
    public java.lang.String getPlayerId() {
        return playerId;
    }


    /**
     * Sets the playerId value for this ToggleBonusBalanceResponse.
     * 
     * @param playerId
     */
    public void setPlayerId(java.lang.String playerId) {
        this.playerId = playerId;
    }


    /**
     * Gets the overrideWagerLock value for this ToggleBonusBalanceResponse.
     * 
     * @return overrideWagerLock
     */
    public boolean isOverrideWagerLock() {
        return overrideWagerLock;
    }


    /**
     * Sets the overrideWagerLock value for this ToggleBonusBalanceResponse.
     * 
     * @param overrideWagerLock
     */
    public void setOverrideWagerLock(boolean overrideWagerLock) {
        this.overrideWagerLock = overrideWagerLock;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ToggleBonusBalanceResponse)) return false;
        ToggleBonusBalanceResponse other = (ToggleBonusBalanceResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.bonusBalanceId==null && other.getBonusBalanceId()==null) || 
             (this.bonusBalanceId!=null &&
              this.bonusBalanceId.equals(other.getBonusBalanceId()))) &&
            this.success == other.isSuccess() &&
            this.isActive == other.isIsActive() &&
            ((this.message==null && other.getMessage()==null) || 
             (this.message!=null &&
              this.message.equals(other.getMessage()))) &&
            ((this.blockingGameNames==null && other.getBlockingGameNames()==null) || 
             (this.blockingGameNames!=null &&
              this.blockingGameNames.equals(other.getBlockingGameNames()))) &&
            this.blockingCount == other.getBlockingCount() &&
            ((this.couponCode==null && other.getCouponCode()==null) || 
             (this.couponCode!=null &&
              this.couponCode.equals(other.getCouponCode()))) &&
            ((this.playerId==null && other.getPlayerId()==null) || 
             (this.playerId!=null &&
              this.playerId.equals(other.getPlayerId()))) &&
            this.overrideWagerLock == other.isOverrideWagerLock();
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
        if (getBonusBalanceId() != null) {
            _hashCode += getBonusBalanceId().hashCode();
        }
        _hashCode += (isSuccess() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isIsActive() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getMessage() != null) {
            _hashCode += getMessage().hashCode();
        }
        if (getBlockingGameNames() != null) {
            _hashCode += getBlockingGameNames().hashCode();
        }
        _hashCode += getBlockingCount();
        if (getCouponCode() != null) {
            _hashCode += getCouponCode().hashCode();
        }
        if (getPlayerId() != null) {
            _hashCode += getPlayerId().hashCode();
        }
        _hashCode += (isOverrideWagerLock() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ToggleBonusBalanceResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "ToggleBonusBalanceResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bonusBalanceId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "BonusBalanceId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("success");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "Success"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isActive");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "IsActive"));
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
        elemField.setFieldName("blockingGameNames");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "BlockingGameNames"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("blockingCount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "BlockingCount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("couponCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "CouponCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("playerId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "PlayerId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("overrideWagerLock");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "OverrideWagerLock"));
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
