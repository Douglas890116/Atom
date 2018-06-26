/**
 * CreateBonusAndApplyRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hy.pull.common.util.game.hb;

public class CreateBonusAndApplyRequest  extends com.hy.pull.common.util.game.hb.BaseRequest  implements java.io.Serializable {
    private java.lang.String username;

    private java.lang.String couponCodeToCreate;

    private boolean replaceActiveCoupon;

    private short couponTypeId;

    private int expireAfterDays;

    private short maxRedemptionsPerPlayer;

    private short wagerMultiplierRequirement;

    private double maxConversionToRealMultiplier;

    private java.math.BigDecimal autoCancelBonusBalanceAt;

    private short numberOfFreeSpins;

    private java.lang.String gameKeyName;

    private java.math.BigDecimal freeSpinValuePerSpin;

    private java.lang.String currencyCode;

    public CreateBonusAndApplyRequest() {
    }

    public CreateBonusAndApplyRequest(
           java.lang.String brandId,
           java.lang.String APIKey,
           java.lang.String playerHostAddress,
           java.lang.String CFCId,
           java.lang.String locale,
           java.lang.String username,
           java.lang.String couponCodeToCreate,
           boolean replaceActiveCoupon,
           short couponTypeId,
           int expireAfterDays,
           short maxRedemptionsPerPlayer,
           short wagerMultiplierRequirement,
           double maxConversionToRealMultiplier,
           java.math.BigDecimal autoCancelBonusBalanceAt,
           short numberOfFreeSpins,
           java.lang.String gameKeyName,
           java.math.BigDecimal freeSpinValuePerSpin,
           java.lang.String currencyCode) {
        super(
            brandId,
            APIKey,
            playerHostAddress,
            CFCId,
            locale);
        this.username = username;
        this.couponCodeToCreate = couponCodeToCreate;
        this.replaceActiveCoupon = replaceActiveCoupon;
        this.couponTypeId = couponTypeId;
        this.expireAfterDays = expireAfterDays;
        this.maxRedemptionsPerPlayer = maxRedemptionsPerPlayer;
        this.wagerMultiplierRequirement = wagerMultiplierRequirement;
        this.maxConversionToRealMultiplier = maxConversionToRealMultiplier;
        this.autoCancelBonusBalanceAt = autoCancelBonusBalanceAt;
        this.numberOfFreeSpins = numberOfFreeSpins;
        this.gameKeyName = gameKeyName;
        this.freeSpinValuePerSpin = freeSpinValuePerSpin;
        this.currencyCode = currencyCode;
    }


    /**
     * Gets the username value for this CreateBonusAndApplyRequest.
     * 
     * @return username
     */
    public java.lang.String getUsername() {
        return username;
    }


    /**
     * Sets the username value for this CreateBonusAndApplyRequest.
     * 
     * @param username
     */
    public void setUsername(java.lang.String username) {
        this.username = username;
    }


    /**
     * Gets the couponCodeToCreate value for this CreateBonusAndApplyRequest.
     * 
     * @return couponCodeToCreate
     */
    public java.lang.String getCouponCodeToCreate() {
        return couponCodeToCreate;
    }


    /**
     * Sets the couponCodeToCreate value for this CreateBonusAndApplyRequest.
     * 
     * @param couponCodeToCreate
     */
    public void setCouponCodeToCreate(java.lang.String couponCodeToCreate) {
        this.couponCodeToCreate = couponCodeToCreate;
    }


    /**
     * Gets the replaceActiveCoupon value for this CreateBonusAndApplyRequest.
     * 
     * @return replaceActiveCoupon
     */
    public boolean isReplaceActiveCoupon() {
        return replaceActiveCoupon;
    }


    /**
     * Sets the replaceActiveCoupon value for this CreateBonusAndApplyRequest.
     * 
     * @param replaceActiveCoupon
     */
    public void setReplaceActiveCoupon(boolean replaceActiveCoupon) {
        this.replaceActiveCoupon = replaceActiveCoupon;
    }


    /**
     * Gets the couponTypeId value for this CreateBonusAndApplyRequest.
     * 
     * @return couponTypeId
     */
    public short getCouponTypeId() {
        return couponTypeId;
    }


    /**
     * Sets the couponTypeId value for this CreateBonusAndApplyRequest.
     * 
     * @param couponTypeId
     */
    public void setCouponTypeId(short couponTypeId) {
        this.couponTypeId = couponTypeId;
    }


    /**
     * Gets the expireAfterDays value for this CreateBonusAndApplyRequest.
     * 
     * @return expireAfterDays
     */
    public int getExpireAfterDays() {
        return expireAfterDays;
    }


    /**
     * Sets the expireAfterDays value for this CreateBonusAndApplyRequest.
     * 
     * @param expireAfterDays
     */
    public void setExpireAfterDays(int expireAfterDays) {
        this.expireAfterDays = expireAfterDays;
    }


    /**
     * Gets the maxRedemptionsPerPlayer value for this CreateBonusAndApplyRequest.
     * 
     * @return maxRedemptionsPerPlayer
     */
    public short getMaxRedemptionsPerPlayer() {
        return maxRedemptionsPerPlayer;
    }


    /**
     * Sets the maxRedemptionsPerPlayer value for this CreateBonusAndApplyRequest.
     * 
     * @param maxRedemptionsPerPlayer
     */
    public void setMaxRedemptionsPerPlayer(short maxRedemptionsPerPlayer) {
        this.maxRedemptionsPerPlayer = maxRedemptionsPerPlayer;
    }


    /**
     * Gets the wagerMultiplierRequirement value for this CreateBonusAndApplyRequest.
     * 
     * @return wagerMultiplierRequirement
     */
    public short getWagerMultiplierRequirement() {
        return wagerMultiplierRequirement;
    }


    /**
     * Sets the wagerMultiplierRequirement value for this CreateBonusAndApplyRequest.
     * 
     * @param wagerMultiplierRequirement
     */
    public void setWagerMultiplierRequirement(short wagerMultiplierRequirement) {
        this.wagerMultiplierRequirement = wagerMultiplierRequirement;
    }


    /**
     * Gets the maxConversionToRealMultiplier value for this CreateBonusAndApplyRequest.
     * 
     * @return maxConversionToRealMultiplier
     */
    public double getMaxConversionToRealMultiplier() {
        return maxConversionToRealMultiplier;
    }


    /**
     * Sets the maxConversionToRealMultiplier value for this CreateBonusAndApplyRequest.
     * 
     * @param maxConversionToRealMultiplier
     */
    public void setMaxConversionToRealMultiplier(double maxConversionToRealMultiplier) {
        this.maxConversionToRealMultiplier = maxConversionToRealMultiplier;
    }


    /**
     * Gets the autoCancelBonusBalanceAt value for this CreateBonusAndApplyRequest.
     * 
     * @return autoCancelBonusBalanceAt
     */
    public java.math.BigDecimal getAutoCancelBonusBalanceAt() {
        return autoCancelBonusBalanceAt;
    }


    /**
     * Sets the autoCancelBonusBalanceAt value for this CreateBonusAndApplyRequest.
     * 
     * @param autoCancelBonusBalanceAt
     */
    public void setAutoCancelBonusBalanceAt(java.math.BigDecimal autoCancelBonusBalanceAt) {
        this.autoCancelBonusBalanceAt = autoCancelBonusBalanceAt;
    }


    /**
     * Gets the numberOfFreeSpins value for this CreateBonusAndApplyRequest.
     * 
     * @return numberOfFreeSpins
     */
    public short getNumberOfFreeSpins() {
        return numberOfFreeSpins;
    }


    /**
     * Sets the numberOfFreeSpins value for this CreateBonusAndApplyRequest.
     * 
     * @param numberOfFreeSpins
     */
    public void setNumberOfFreeSpins(short numberOfFreeSpins) {
        this.numberOfFreeSpins = numberOfFreeSpins;
    }


    /**
     * Gets the gameKeyName value for this CreateBonusAndApplyRequest.
     * 
     * @return gameKeyName
     */
    public java.lang.String getGameKeyName() {
        return gameKeyName;
    }


    /**
     * Sets the gameKeyName value for this CreateBonusAndApplyRequest.
     * 
     * @param gameKeyName
     */
    public void setGameKeyName(java.lang.String gameKeyName) {
        this.gameKeyName = gameKeyName;
    }


    /**
     * Gets the freeSpinValuePerSpin value for this CreateBonusAndApplyRequest.
     * 
     * @return freeSpinValuePerSpin
     */
    public java.math.BigDecimal getFreeSpinValuePerSpin() {
        return freeSpinValuePerSpin;
    }


    /**
     * Sets the freeSpinValuePerSpin value for this CreateBonusAndApplyRequest.
     * 
     * @param freeSpinValuePerSpin
     */
    public void setFreeSpinValuePerSpin(java.math.BigDecimal freeSpinValuePerSpin) {
        this.freeSpinValuePerSpin = freeSpinValuePerSpin;
    }


    /**
     * Gets the currencyCode value for this CreateBonusAndApplyRequest.
     * 
     * @return currencyCode
     */
    public java.lang.String getCurrencyCode() {
        return currencyCode;
    }


    /**
     * Sets the currencyCode value for this CreateBonusAndApplyRequest.
     * 
     * @param currencyCode
     */
    public void setCurrencyCode(java.lang.String currencyCode) {
        this.currencyCode = currencyCode;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CreateBonusAndApplyRequest)) return false;
        CreateBonusAndApplyRequest other = (CreateBonusAndApplyRequest) obj;
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
            ((this.couponCodeToCreate==null && other.getCouponCodeToCreate()==null) || 
             (this.couponCodeToCreate!=null &&
              this.couponCodeToCreate.equals(other.getCouponCodeToCreate()))) &&
            this.replaceActiveCoupon == other.isReplaceActiveCoupon() &&
            this.couponTypeId == other.getCouponTypeId() &&
            this.expireAfterDays == other.getExpireAfterDays() &&
            this.maxRedemptionsPerPlayer == other.getMaxRedemptionsPerPlayer() &&
            this.wagerMultiplierRequirement == other.getWagerMultiplierRequirement() &&
            this.maxConversionToRealMultiplier == other.getMaxConversionToRealMultiplier() &&
            ((this.autoCancelBonusBalanceAt==null && other.getAutoCancelBonusBalanceAt()==null) || 
             (this.autoCancelBonusBalanceAt!=null &&
              this.autoCancelBonusBalanceAt.equals(other.getAutoCancelBonusBalanceAt()))) &&
            this.numberOfFreeSpins == other.getNumberOfFreeSpins() &&
            ((this.gameKeyName==null && other.getGameKeyName()==null) || 
             (this.gameKeyName!=null &&
              this.gameKeyName.equals(other.getGameKeyName()))) &&
            ((this.freeSpinValuePerSpin==null && other.getFreeSpinValuePerSpin()==null) || 
             (this.freeSpinValuePerSpin!=null &&
              this.freeSpinValuePerSpin.equals(other.getFreeSpinValuePerSpin()))) &&
            ((this.currencyCode==null && other.getCurrencyCode()==null) || 
             (this.currencyCode!=null &&
              this.currencyCode.equals(other.getCurrencyCode())));
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
        if (getCouponCodeToCreate() != null) {
            _hashCode += getCouponCodeToCreate().hashCode();
        }
        _hashCode += (isReplaceActiveCoupon() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += getCouponTypeId();
        _hashCode += getExpireAfterDays();
        _hashCode += getMaxRedemptionsPerPlayer();
        _hashCode += getWagerMultiplierRequirement();
        _hashCode += new Double(getMaxConversionToRealMultiplier()).hashCode();
        if (getAutoCancelBonusBalanceAt() != null) {
            _hashCode += getAutoCancelBonusBalanceAt().hashCode();
        }
        _hashCode += getNumberOfFreeSpins();
        if (getGameKeyName() != null) {
            _hashCode += getGameKeyName().hashCode();
        }
        if (getFreeSpinValuePerSpin() != null) {
            _hashCode += getFreeSpinValuePerSpin().hashCode();
        }
        if (getCurrencyCode() != null) {
            _hashCode += getCurrencyCode().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CreateBonusAndApplyRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "CreateBonusAndApplyRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("username");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "Username"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("couponCodeToCreate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "CouponCodeToCreate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("replaceActiveCoupon");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "ReplaceActiveCoupon"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("couponTypeId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "CouponTypeId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("expireAfterDays");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "ExpireAfterDays"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("maxRedemptionsPerPlayer");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "MaxRedemptionsPerPlayer"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wagerMultiplierRequirement");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "WagerMultiplierRequirement"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("maxConversionToRealMultiplier");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "MaxConversionToRealMultiplier"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("autoCancelBonusBalanceAt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "AutoCancelBonusBalanceAt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numberOfFreeSpins");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "NumberOfFreeSpins"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("gameKeyName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameKeyName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("freeSpinValuePerSpin");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "FreeSpinValuePerSpin"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("currencyCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "CurrencyCode"));
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
