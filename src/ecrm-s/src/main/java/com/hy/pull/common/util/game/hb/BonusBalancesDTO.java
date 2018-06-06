/**
 * BonusBalancesDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hy.pull.common.util.game.hb;

public class BonusBalancesDTO  implements java.io.Serializable {
    private java.lang.String bonusBalanceId;

    private int couponTypeId;

    private java.lang.String couponTypeName;

    private java.lang.String couponTypeLangKey;

    private java.math.BigDecimal balance;

    private int freespins;

    private java.math.BigDecimal freespinValue;

    private java.math.BigDecimal wagerRemaining;

    private java.math.BigDecimal totalWagerRequired;

    private java.math.BigDecimal percentageComplete;

    private boolean isActive;

    private java.util.Calendar dtExpires;

    private java.lang.String code;

    private java.lang.String couponName;

    private boolean isFrozen;

    private java.lang.String gameName;

    private java.lang.String gameKeyName;

    private java.lang.Integer gameId;

    private java.math.BigDecimal wagerLockUntil;

    private java.math.BigDecimal wagerLock;

    public BonusBalancesDTO() {
    }

    public BonusBalancesDTO(
           java.lang.String bonusBalanceId,
           int couponTypeId,
           java.lang.String couponTypeName,
           java.lang.String couponTypeLangKey,
           java.math.BigDecimal balance,
           int freespins,
           java.math.BigDecimal freespinValue,
           java.math.BigDecimal wagerRemaining,
           java.math.BigDecimal totalWagerRequired,
           java.math.BigDecimal percentageComplete,
           boolean isActive,
           java.util.Calendar dtExpires,
           java.lang.String code,
           java.lang.String couponName,
           boolean isFrozen,
           java.lang.String gameName,
           java.lang.String gameKeyName,
           java.lang.Integer gameId,
           java.math.BigDecimal wagerLockUntil,
           java.math.BigDecimal wagerLock) {
           this.bonusBalanceId = bonusBalanceId;
           this.couponTypeId = couponTypeId;
           this.couponTypeName = couponTypeName;
           this.couponTypeLangKey = couponTypeLangKey;
           this.balance = balance;
           this.freespins = freespins;
           this.freespinValue = freespinValue;
           this.wagerRemaining = wagerRemaining;
           this.totalWagerRequired = totalWagerRequired;
           this.percentageComplete = percentageComplete;
           this.isActive = isActive;
           this.dtExpires = dtExpires;
           this.code = code;
           this.couponName = couponName;
           this.isFrozen = isFrozen;
           this.gameName = gameName;
           this.gameKeyName = gameKeyName;
           this.gameId = gameId;
           this.wagerLockUntil = wagerLockUntil;
           this.wagerLock = wagerLock;
    }


    /**
     * Gets the bonusBalanceId value for this BonusBalancesDTO.
     * 
     * @return bonusBalanceId
     */
    public java.lang.String getBonusBalanceId() {
        return bonusBalanceId;
    }


    /**
     * Sets the bonusBalanceId value for this BonusBalancesDTO.
     * 
     * @param bonusBalanceId
     */
    public void setBonusBalanceId(java.lang.String bonusBalanceId) {
        this.bonusBalanceId = bonusBalanceId;
    }


    /**
     * Gets the couponTypeId value for this BonusBalancesDTO.
     * 
     * @return couponTypeId
     */
    public int getCouponTypeId() {
        return couponTypeId;
    }


    /**
     * Sets the couponTypeId value for this BonusBalancesDTO.
     * 
     * @param couponTypeId
     */
    public void setCouponTypeId(int couponTypeId) {
        this.couponTypeId = couponTypeId;
    }


    /**
     * Gets the couponTypeName value for this BonusBalancesDTO.
     * 
     * @return couponTypeName
     */
    public java.lang.String getCouponTypeName() {
        return couponTypeName;
    }


    /**
     * Sets the couponTypeName value for this BonusBalancesDTO.
     * 
     * @param couponTypeName
     */
    public void setCouponTypeName(java.lang.String couponTypeName) {
        this.couponTypeName = couponTypeName;
    }


    /**
     * Gets the couponTypeLangKey value for this BonusBalancesDTO.
     * 
     * @return couponTypeLangKey
     */
    public java.lang.String getCouponTypeLangKey() {
        return couponTypeLangKey;
    }


    /**
     * Sets the couponTypeLangKey value for this BonusBalancesDTO.
     * 
     * @param couponTypeLangKey
     */
    public void setCouponTypeLangKey(java.lang.String couponTypeLangKey) {
        this.couponTypeLangKey = couponTypeLangKey;
    }


    /**
     * Gets the balance value for this BonusBalancesDTO.
     * 
     * @return balance
     */
    public java.math.BigDecimal getBalance() {
        return balance;
    }


    /**
     * Sets the balance value for this BonusBalancesDTO.
     * 
     * @param balance
     */
    public void setBalance(java.math.BigDecimal balance) {
        this.balance = balance;
    }


    /**
     * Gets the freespins value for this BonusBalancesDTO.
     * 
     * @return freespins
     */
    public int getFreespins() {
        return freespins;
    }


    /**
     * Sets the freespins value for this BonusBalancesDTO.
     * 
     * @param freespins
     */
    public void setFreespins(int freespins) {
        this.freespins = freespins;
    }


    /**
     * Gets the freespinValue value for this BonusBalancesDTO.
     * 
     * @return freespinValue
     */
    public java.math.BigDecimal getFreespinValue() {
        return freespinValue;
    }


    /**
     * Sets the freespinValue value for this BonusBalancesDTO.
     * 
     * @param freespinValue
     */
    public void setFreespinValue(java.math.BigDecimal freespinValue) {
        this.freespinValue = freespinValue;
    }


    /**
     * Gets the wagerRemaining value for this BonusBalancesDTO.
     * 
     * @return wagerRemaining
     */
    public java.math.BigDecimal getWagerRemaining() {
        return wagerRemaining;
    }


    /**
     * Sets the wagerRemaining value for this BonusBalancesDTO.
     * 
     * @param wagerRemaining
     */
    public void setWagerRemaining(java.math.BigDecimal wagerRemaining) {
        this.wagerRemaining = wagerRemaining;
    }


    /**
     * Gets the totalWagerRequired value for this BonusBalancesDTO.
     * 
     * @return totalWagerRequired
     */
    public java.math.BigDecimal getTotalWagerRequired() {
        return totalWagerRequired;
    }


    /**
     * Sets the totalWagerRequired value for this BonusBalancesDTO.
     * 
     * @param totalWagerRequired
     */
    public void setTotalWagerRequired(java.math.BigDecimal totalWagerRequired) {
        this.totalWagerRequired = totalWagerRequired;
    }


    /**
     * Gets the percentageComplete value for this BonusBalancesDTO.
     * 
     * @return percentageComplete
     */
    public java.math.BigDecimal getPercentageComplete() {
        return percentageComplete;
    }


    /**
     * Sets the percentageComplete value for this BonusBalancesDTO.
     * 
     * @param percentageComplete
     */
    public void setPercentageComplete(java.math.BigDecimal percentageComplete) {
        this.percentageComplete = percentageComplete;
    }


    /**
     * Gets the isActive value for this BonusBalancesDTO.
     * 
     * @return isActive
     */
    public boolean isIsActive() {
        return isActive;
    }


    /**
     * Sets the isActive value for this BonusBalancesDTO.
     * 
     * @param isActive
     */
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }


    /**
     * Gets the dtExpires value for this BonusBalancesDTO.
     * 
     * @return dtExpires
     */
    public java.util.Calendar getDtExpires() {
        return dtExpires;
    }


    /**
     * Sets the dtExpires value for this BonusBalancesDTO.
     * 
     * @param dtExpires
     */
    public void setDtExpires(java.util.Calendar dtExpires) {
        this.dtExpires = dtExpires;
    }


    /**
     * Gets the code value for this BonusBalancesDTO.
     * 
     * @return code
     */
    public java.lang.String getCode() {
        return code;
    }


    /**
     * Sets the code value for this BonusBalancesDTO.
     * 
     * @param code
     */
    public void setCode(java.lang.String code) {
        this.code = code;
    }


    /**
     * Gets the couponName value for this BonusBalancesDTO.
     * 
     * @return couponName
     */
    public java.lang.String getCouponName() {
        return couponName;
    }


    /**
     * Sets the couponName value for this BonusBalancesDTO.
     * 
     * @param couponName
     */
    public void setCouponName(java.lang.String couponName) {
        this.couponName = couponName;
    }


    /**
     * Gets the isFrozen value for this BonusBalancesDTO.
     * 
     * @return isFrozen
     */
    public boolean isIsFrozen() {
        return isFrozen;
    }


    /**
     * Sets the isFrozen value for this BonusBalancesDTO.
     * 
     * @param isFrozen
     */
    public void setIsFrozen(boolean isFrozen) {
        this.isFrozen = isFrozen;
    }


    /**
     * Gets the gameName value for this BonusBalancesDTO.
     * 
     * @return gameName
     */
    public java.lang.String getGameName() {
        return gameName;
    }


    /**
     * Sets the gameName value for this BonusBalancesDTO.
     * 
     * @param gameName
     */
    public void setGameName(java.lang.String gameName) {
        this.gameName = gameName;
    }


    /**
     * Gets the gameKeyName value for this BonusBalancesDTO.
     * 
     * @return gameKeyName
     */
    public java.lang.String getGameKeyName() {
        return gameKeyName;
    }


    /**
     * Sets the gameKeyName value for this BonusBalancesDTO.
     * 
     * @param gameKeyName
     */
    public void setGameKeyName(java.lang.String gameKeyName) {
        this.gameKeyName = gameKeyName;
    }


    /**
     * Gets the gameId value for this BonusBalancesDTO.
     * 
     * @return gameId
     */
    public java.lang.Integer getGameId() {
        return gameId;
    }


    /**
     * Sets the gameId value for this BonusBalancesDTO.
     * 
     * @param gameId
     */
    public void setGameId(java.lang.Integer gameId) {
        this.gameId = gameId;
    }


    /**
     * Gets the wagerLockUntil value for this BonusBalancesDTO.
     * 
     * @return wagerLockUntil
     */
    public java.math.BigDecimal getWagerLockUntil() {
        return wagerLockUntil;
    }


    /**
     * Sets the wagerLockUntil value for this BonusBalancesDTO.
     * 
     * @param wagerLockUntil
     */
    public void setWagerLockUntil(java.math.BigDecimal wagerLockUntil) {
        this.wagerLockUntil = wagerLockUntil;
    }


    /**
     * Gets the wagerLock value for this BonusBalancesDTO.
     * 
     * @return wagerLock
     */
    public java.math.BigDecimal getWagerLock() {
        return wagerLock;
    }


    /**
     * Sets the wagerLock value for this BonusBalancesDTO.
     * 
     * @param wagerLock
     */
    public void setWagerLock(java.math.BigDecimal wagerLock) {
        this.wagerLock = wagerLock;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BonusBalancesDTO)) return false;
        BonusBalancesDTO other = (BonusBalancesDTO) obj;
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
            this.couponTypeId == other.getCouponTypeId() &&
            ((this.couponTypeName==null && other.getCouponTypeName()==null) || 
             (this.couponTypeName!=null &&
              this.couponTypeName.equals(other.getCouponTypeName()))) &&
            ((this.couponTypeLangKey==null && other.getCouponTypeLangKey()==null) || 
             (this.couponTypeLangKey!=null &&
              this.couponTypeLangKey.equals(other.getCouponTypeLangKey()))) &&
            ((this.balance==null && other.getBalance()==null) || 
             (this.balance!=null &&
              this.balance.equals(other.getBalance()))) &&
            this.freespins == other.getFreespins() &&
            ((this.freespinValue==null && other.getFreespinValue()==null) || 
             (this.freespinValue!=null &&
              this.freespinValue.equals(other.getFreespinValue()))) &&
            ((this.wagerRemaining==null && other.getWagerRemaining()==null) || 
             (this.wagerRemaining!=null &&
              this.wagerRemaining.equals(other.getWagerRemaining()))) &&
            ((this.totalWagerRequired==null && other.getTotalWagerRequired()==null) || 
             (this.totalWagerRequired!=null &&
              this.totalWagerRequired.equals(other.getTotalWagerRequired()))) &&
            ((this.percentageComplete==null && other.getPercentageComplete()==null) || 
             (this.percentageComplete!=null &&
              this.percentageComplete.equals(other.getPercentageComplete()))) &&
            this.isActive == other.isIsActive() &&
            ((this.dtExpires==null && other.getDtExpires()==null) || 
             (this.dtExpires!=null &&
              this.dtExpires.equals(other.getDtExpires()))) &&
            ((this.code==null && other.getCode()==null) || 
             (this.code!=null &&
              this.code.equals(other.getCode()))) &&
            ((this.couponName==null && other.getCouponName()==null) || 
             (this.couponName!=null &&
              this.couponName.equals(other.getCouponName()))) &&
            this.isFrozen == other.isIsFrozen() &&
            ((this.gameName==null && other.getGameName()==null) || 
             (this.gameName!=null &&
              this.gameName.equals(other.getGameName()))) &&
            ((this.gameKeyName==null && other.getGameKeyName()==null) || 
             (this.gameKeyName!=null &&
              this.gameKeyName.equals(other.getGameKeyName()))) &&
            ((this.gameId==null && other.getGameId()==null) || 
             (this.gameId!=null &&
              this.gameId.equals(other.getGameId()))) &&
            ((this.wagerLockUntil==null && other.getWagerLockUntil()==null) || 
             (this.wagerLockUntil!=null &&
              this.wagerLockUntil.equals(other.getWagerLockUntil()))) &&
            ((this.wagerLock==null && other.getWagerLock()==null) || 
             (this.wagerLock!=null &&
              this.wagerLock.equals(other.getWagerLock())));
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
        _hashCode += getCouponTypeId();
        if (getCouponTypeName() != null) {
            _hashCode += getCouponTypeName().hashCode();
        }
        if (getCouponTypeLangKey() != null) {
            _hashCode += getCouponTypeLangKey().hashCode();
        }
        if (getBalance() != null) {
            _hashCode += getBalance().hashCode();
        }
        _hashCode += getFreespins();
        if (getFreespinValue() != null) {
            _hashCode += getFreespinValue().hashCode();
        }
        if (getWagerRemaining() != null) {
            _hashCode += getWagerRemaining().hashCode();
        }
        if (getTotalWagerRequired() != null) {
            _hashCode += getTotalWagerRequired().hashCode();
        }
        if (getPercentageComplete() != null) {
            _hashCode += getPercentageComplete().hashCode();
        }
        _hashCode += (isIsActive() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getDtExpires() != null) {
            _hashCode += getDtExpires().hashCode();
        }
        if (getCode() != null) {
            _hashCode += getCode().hashCode();
        }
        if (getCouponName() != null) {
            _hashCode += getCouponName().hashCode();
        }
        _hashCode += (isIsFrozen() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getGameName() != null) {
            _hashCode += getGameName().hashCode();
        }
        if (getGameKeyName() != null) {
            _hashCode += getGameKeyName().hashCode();
        }
        if (getGameId() != null) {
            _hashCode += getGameId().hashCode();
        }
        if (getWagerLockUntil() != null) {
            _hashCode += getWagerLockUntil().hashCode();
        }
        if (getWagerLock() != null) {
            _hashCode += getWagerLock().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BonusBalancesDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "BonusBalancesDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bonusBalanceId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "BonusBalanceId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("couponTypeId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "CouponTypeId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("couponTypeName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "CouponTypeName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("couponTypeLangKey");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "CouponTypeLangKey"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("balance");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "Balance"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("freespins");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "Freespins"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("freespinValue");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "FreespinValue"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wagerRemaining");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "WagerRemaining"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalWagerRequired");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "TotalWagerRequired"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("percentageComplete");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "PercentageComplete"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isActive");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "IsActive"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dtExpires");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "DtExpires"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
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
        elemField.setFieldName("couponName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "CouponName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isFrozen");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "IsFrozen"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("gameName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
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
        elemField.setFieldName("gameId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wagerLockUntil");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "WagerLockUntil"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wagerLock");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "WagerLock"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
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
