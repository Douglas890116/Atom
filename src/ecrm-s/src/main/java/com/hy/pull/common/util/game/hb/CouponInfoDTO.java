/**
 * CouponInfoDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hy.pull.common.util.game.hb;

public class CouponInfoDTO  implements java.io.Serializable {
    private java.lang.String couponId;

    private java.lang.Integer percentage;

    private java.math.BigDecimal maxRedeemAmount;

    private java.math.BigDecimal couponAmount;

    private java.lang.String name;

    private short wagerRequirement;

    private short couponTypeId;

    private java.math.BigDecimal autoCancelAmount;

    private java.lang.String couponTypeName;

    private java.math.BigDecimal trickleBack;

    private java.lang.Double freeConversionMultiplier;

    private java.math.BigDecimal minDepositAmount;

    private java.lang.String code;

    private java.lang.String playerClassId;

    private java.lang.String playerClassName;

    private java.util.Calendar dtStart;

    private java.util.Calendar dtExpire;

    private java.math.BigDecimal maxBalanceAmount;

    private boolean isSignup;

    private int totalRedeemedByPlayer;

    private java.lang.String currencySymbol;

    private java.lang.Short freeSpinCount;

    private java.math.BigDecimal freeSpinValue;

    private java.lang.String gameName;

    private java.lang.Short maxRedemptionsPerPlayer;

    private com.hy.pull.common.util.game.hb.ComplexDepositCouponInfo[] complexConfig;

    private boolean lockTillDeposit;

    private boolean blockRedeem;

    private java.lang.String blockMessage;

    private java.math.BigDecimal wagerLock;

    private boolean isPrivate;

    private boolean isAdminUseOnly;

    private java.lang.String gameKeyName;

    public CouponInfoDTO() {
    }

    public CouponInfoDTO(
           java.lang.String couponId,
           java.lang.Integer percentage,
           java.math.BigDecimal maxRedeemAmount,
           java.math.BigDecimal couponAmount,
           java.lang.String name,
           short wagerRequirement,
           short couponTypeId,
           java.math.BigDecimal autoCancelAmount,
           java.lang.String couponTypeName,
           java.math.BigDecimal trickleBack,
           java.lang.Double freeConversionMultiplier,
           java.math.BigDecimal minDepositAmount,
           java.lang.String code,
           java.lang.String playerClassId,
           java.lang.String playerClassName,
           java.util.Calendar dtStart,
           java.util.Calendar dtExpire,
           java.math.BigDecimal maxBalanceAmount,
           boolean isSignup,
           int totalRedeemedByPlayer,
           java.lang.String currencySymbol,
           java.lang.Short freeSpinCount,
           java.math.BigDecimal freeSpinValue,
           java.lang.String gameName,
           java.lang.Short maxRedemptionsPerPlayer,
           com.hy.pull.common.util.game.hb.ComplexDepositCouponInfo[] complexConfig,
           boolean lockTillDeposit,
           boolean blockRedeem,
           java.lang.String blockMessage,
           java.math.BigDecimal wagerLock,
           boolean isPrivate,
           boolean isAdminUseOnly,
           java.lang.String gameKeyName) {
           this.couponId = couponId;
           this.percentage = percentage;
           this.maxRedeemAmount = maxRedeemAmount;
           this.couponAmount = couponAmount;
           this.name = name;
           this.wagerRequirement = wagerRequirement;
           this.couponTypeId = couponTypeId;
           this.autoCancelAmount = autoCancelAmount;
           this.couponTypeName = couponTypeName;
           this.trickleBack = trickleBack;
           this.freeConversionMultiplier = freeConversionMultiplier;
           this.minDepositAmount = minDepositAmount;
           this.code = code;
           this.playerClassId = playerClassId;
           this.playerClassName = playerClassName;
           this.dtStart = dtStart;
           this.dtExpire = dtExpire;
           this.maxBalanceAmount = maxBalanceAmount;
           this.isSignup = isSignup;
           this.totalRedeemedByPlayer = totalRedeemedByPlayer;
           this.currencySymbol = currencySymbol;
           this.freeSpinCount = freeSpinCount;
           this.freeSpinValue = freeSpinValue;
           this.gameName = gameName;
           this.maxRedemptionsPerPlayer = maxRedemptionsPerPlayer;
           this.complexConfig = complexConfig;
           this.lockTillDeposit = lockTillDeposit;
           this.blockRedeem = blockRedeem;
           this.blockMessage = blockMessage;
           this.wagerLock = wagerLock;
           this.isPrivate = isPrivate;
           this.isAdminUseOnly = isAdminUseOnly;
           this.gameKeyName = gameKeyName;
    }


    /**
     * Gets the couponId value for this CouponInfoDTO.
     * 
     * @return couponId
     */
    public java.lang.String getCouponId() {
        return couponId;
    }


    /**
     * Sets the couponId value for this CouponInfoDTO.
     * 
     * @param couponId
     */
    public void setCouponId(java.lang.String couponId) {
        this.couponId = couponId;
    }


    /**
     * Gets the percentage value for this CouponInfoDTO.
     * 
     * @return percentage
     */
    public java.lang.Integer getPercentage() {
        return percentage;
    }


    /**
     * Sets the percentage value for this CouponInfoDTO.
     * 
     * @param percentage
     */
    public void setPercentage(java.lang.Integer percentage) {
        this.percentage = percentage;
    }


    /**
     * Gets the maxRedeemAmount value for this CouponInfoDTO.
     * 
     * @return maxRedeemAmount
     */
    public java.math.BigDecimal getMaxRedeemAmount() {
        return maxRedeemAmount;
    }


    /**
     * Sets the maxRedeemAmount value for this CouponInfoDTO.
     * 
     * @param maxRedeemAmount
     */
    public void setMaxRedeemAmount(java.math.BigDecimal maxRedeemAmount) {
        this.maxRedeemAmount = maxRedeemAmount;
    }


    /**
     * Gets the couponAmount value for this CouponInfoDTO.
     * 
     * @return couponAmount
     */
    public java.math.BigDecimal getCouponAmount() {
        return couponAmount;
    }


    /**
     * Sets the couponAmount value for this CouponInfoDTO.
     * 
     * @param couponAmount
     */
    public void setCouponAmount(java.math.BigDecimal couponAmount) {
        this.couponAmount = couponAmount;
    }


    /**
     * Gets the name value for this CouponInfoDTO.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this CouponInfoDTO.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the wagerRequirement value for this CouponInfoDTO.
     * 
     * @return wagerRequirement
     */
    public short getWagerRequirement() {
        return wagerRequirement;
    }


    /**
     * Sets the wagerRequirement value for this CouponInfoDTO.
     * 
     * @param wagerRequirement
     */
    public void setWagerRequirement(short wagerRequirement) {
        this.wagerRequirement = wagerRequirement;
    }


    /**
     * Gets the couponTypeId value for this CouponInfoDTO.
     * 
     * @return couponTypeId
     */
    public short getCouponTypeId() {
        return couponTypeId;
    }


    /**
     * Sets the couponTypeId value for this CouponInfoDTO.
     * 
     * @param couponTypeId
     */
    public void setCouponTypeId(short couponTypeId) {
        this.couponTypeId = couponTypeId;
    }


    /**
     * Gets the autoCancelAmount value for this CouponInfoDTO.
     * 
     * @return autoCancelAmount
     */
    public java.math.BigDecimal getAutoCancelAmount() {
        return autoCancelAmount;
    }


    /**
     * Sets the autoCancelAmount value for this CouponInfoDTO.
     * 
     * @param autoCancelAmount
     */
    public void setAutoCancelAmount(java.math.BigDecimal autoCancelAmount) {
        this.autoCancelAmount = autoCancelAmount;
    }


    /**
     * Gets the couponTypeName value for this CouponInfoDTO.
     * 
     * @return couponTypeName
     */
    public java.lang.String getCouponTypeName() {
        return couponTypeName;
    }


    /**
     * Sets the couponTypeName value for this CouponInfoDTO.
     * 
     * @param couponTypeName
     */
    public void setCouponTypeName(java.lang.String couponTypeName) {
        this.couponTypeName = couponTypeName;
    }


    /**
     * Gets the trickleBack value for this CouponInfoDTO.
     * 
     * @return trickleBack
     */
    public java.math.BigDecimal getTrickleBack() {
        return trickleBack;
    }


    /**
     * Sets the trickleBack value for this CouponInfoDTO.
     * 
     * @param trickleBack
     */
    public void setTrickleBack(java.math.BigDecimal trickleBack) {
        this.trickleBack = trickleBack;
    }


    /**
     * Gets the freeConversionMultiplier value for this CouponInfoDTO.
     * 
     * @return freeConversionMultiplier
     */
    public java.lang.Double getFreeConversionMultiplier() {
        return freeConversionMultiplier;
    }


    /**
     * Sets the freeConversionMultiplier value for this CouponInfoDTO.
     * 
     * @param freeConversionMultiplier
     */
    public void setFreeConversionMultiplier(java.lang.Double freeConversionMultiplier) {
        this.freeConversionMultiplier = freeConversionMultiplier;
    }


    /**
     * Gets the minDepositAmount value for this CouponInfoDTO.
     * 
     * @return minDepositAmount
     */
    public java.math.BigDecimal getMinDepositAmount() {
        return minDepositAmount;
    }


    /**
     * Sets the minDepositAmount value for this CouponInfoDTO.
     * 
     * @param minDepositAmount
     */
    public void setMinDepositAmount(java.math.BigDecimal minDepositAmount) {
        this.minDepositAmount = minDepositAmount;
    }


    /**
     * Gets the code value for this CouponInfoDTO.
     * 
     * @return code
     */
    public java.lang.String getCode() {
        return code;
    }


    /**
     * Sets the code value for this CouponInfoDTO.
     * 
     * @param code
     */
    public void setCode(java.lang.String code) {
        this.code = code;
    }


    /**
     * Gets the playerClassId value for this CouponInfoDTO.
     * 
     * @return playerClassId
     */
    public java.lang.String getPlayerClassId() {
        return playerClassId;
    }


    /**
     * Sets the playerClassId value for this CouponInfoDTO.
     * 
     * @param playerClassId
     */
    public void setPlayerClassId(java.lang.String playerClassId) {
        this.playerClassId = playerClassId;
    }


    /**
     * Gets the playerClassName value for this CouponInfoDTO.
     * 
     * @return playerClassName
     */
    public java.lang.String getPlayerClassName() {
        return playerClassName;
    }


    /**
     * Sets the playerClassName value for this CouponInfoDTO.
     * 
     * @param playerClassName
     */
    public void setPlayerClassName(java.lang.String playerClassName) {
        this.playerClassName = playerClassName;
    }


    /**
     * Gets the dtStart value for this CouponInfoDTO.
     * 
     * @return dtStart
     */
    public java.util.Calendar getDtStart() {
        return dtStart;
    }


    /**
     * Sets the dtStart value for this CouponInfoDTO.
     * 
     * @param dtStart
     */
    public void setDtStart(java.util.Calendar dtStart) {
        this.dtStart = dtStart;
    }


    /**
     * Gets the dtExpire value for this CouponInfoDTO.
     * 
     * @return dtExpire
     */
    public java.util.Calendar getDtExpire() {
        return dtExpire;
    }


    /**
     * Sets the dtExpire value for this CouponInfoDTO.
     * 
     * @param dtExpire
     */
    public void setDtExpire(java.util.Calendar dtExpire) {
        this.dtExpire = dtExpire;
    }


    /**
     * Gets the maxBalanceAmount value for this CouponInfoDTO.
     * 
     * @return maxBalanceAmount
     */
    public java.math.BigDecimal getMaxBalanceAmount() {
        return maxBalanceAmount;
    }


    /**
     * Sets the maxBalanceAmount value for this CouponInfoDTO.
     * 
     * @param maxBalanceAmount
     */
    public void setMaxBalanceAmount(java.math.BigDecimal maxBalanceAmount) {
        this.maxBalanceAmount = maxBalanceAmount;
    }


    /**
     * Gets the isSignup value for this CouponInfoDTO.
     * 
     * @return isSignup
     */
    public boolean isIsSignup() {
        return isSignup;
    }


    /**
     * Sets the isSignup value for this CouponInfoDTO.
     * 
     * @param isSignup
     */
    public void setIsSignup(boolean isSignup) {
        this.isSignup = isSignup;
    }


    /**
     * Gets the totalRedeemedByPlayer value for this CouponInfoDTO.
     * 
     * @return totalRedeemedByPlayer
     */
    public int getTotalRedeemedByPlayer() {
        return totalRedeemedByPlayer;
    }


    /**
     * Sets the totalRedeemedByPlayer value for this CouponInfoDTO.
     * 
     * @param totalRedeemedByPlayer
     */
    public void setTotalRedeemedByPlayer(int totalRedeemedByPlayer) {
        this.totalRedeemedByPlayer = totalRedeemedByPlayer;
    }


    /**
     * Gets the currencySymbol value for this CouponInfoDTO.
     * 
     * @return currencySymbol
     */
    public java.lang.String getCurrencySymbol() {
        return currencySymbol;
    }


    /**
     * Sets the currencySymbol value for this CouponInfoDTO.
     * 
     * @param currencySymbol
     */
    public void setCurrencySymbol(java.lang.String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }


    /**
     * Gets the freeSpinCount value for this CouponInfoDTO.
     * 
     * @return freeSpinCount
     */
    public java.lang.Short getFreeSpinCount() {
        return freeSpinCount;
    }


    /**
     * Sets the freeSpinCount value for this CouponInfoDTO.
     * 
     * @param freeSpinCount
     */
    public void setFreeSpinCount(java.lang.Short freeSpinCount) {
        this.freeSpinCount = freeSpinCount;
    }


    /**
     * Gets the freeSpinValue value for this CouponInfoDTO.
     * 
     * @return freeSpinValue
     */
    public java.math.BigDecimal getFreeSpinValue() {
        return freeSpinValue;
    }


    /**
     * Sets the freeSpinValue value for this CouponInfoDTO.
     * 
     * @param freeSpinValue
     */
    public void setFreeSpinValue(java.math.BigDecimal freeSpinValue) {
        this.freeSpinValue = freeSpinValue;
    }


    /**
     * Gets the gameName value for this CouponInfoDTO.
     * 
     * @return gameName
     */
    public java.lang.String getGameName() {
        return gameName;
    }


    /**
     * Sets the gameName value for this CouponInfoDTO.
     * 
     * @param gameName
     */
    public void setGameName(java.lang.String gameName) {
        this.gameName = gameName;
    }


    /**
     * Gets the maxRedemptionsPerPlayer value for this CouponInfoDTO.
     * 
     * @return maxRedemptionsPerPlayer
     */
    public java.lang.Short getMaxRedemptionsPerPlayer() {
        return maxRedemptionsPerPlayer;
    }


    /**
     * Sets the maxRedemptionsPerPlayer value for this CouponInfoDTO.
     * 
     * @param maxRedemptionsPerPlayer
     */
    public void setMaxRedemptionsPerPlayer(java.lang.Short maxRedemptionsPerPlayer) {
        this.maxRedemptionsPerPlayer = maxRedemptionsPerPlayer;
    }


    /**
     * Gets the complexConfig value for this CouponInfoDTO.
     * 
     * @return complexConfig
     */
    public com.hy.pull.common.util.game.hb.ComplexDepositCouponInfo[] getComplexConfig() {
        return complexConfig;
    }


    /**
     * Sets the complexConfig value for this CouponInfoDTO.
     * 
     * @param complexConfig
     */
    public void setComplexConfig(com.hy.pull.common.util.game.hb.ComplexDepositCouponInfo[] complexConfig) {
        this.complexConfig = complexConfig;
    }


    /**
     * Gets the lockTillDeposit value for this CouponInfoDTO.
     * 
     * @return lockTillDeposit
     */
    public boolean isLockTillDeposit() {
        return lockTillDeposit;
    }


    /**
     * Sets the lockTillDeposit value for this CouponInfoDTO.
     * 
     * @param lockTillDeposit
     */
    public void setLockTillDeposit(boolean lockTillDeposit) {
        this.lockTillDeposit = lockTillDeposit;
    }


    /**
     * Gets the blockRedeem value for this CouponInfoDTO.
     * 
     * @return blockRedeem
     */
    public boolean isBlockRedeem() {
        return blockRedeem;
    }


    /**
     * Sets the blockRedeem value for this CouponInfoDTO.
     * 
     * @param blockRedeem
     */
    public void setBlockRedeem(boolean blockRedeem) {
        this.blockRedeem = blockRedeem;
    }


    /**
     * Gets the blockMessage value for this CouponInfoDTO.
     * 
     * @return blockMessage
     */
    public java.lang.String getBlockMessage() {
        return blockMessage;
    }


    /**
     * Sets the blockMessage value for this CouponInfoDTO.
     * 
     * @param blockMessage
     */
    public void setBlockMessage(java.lang.String blockMessage) {
        this.blockMessage = blockMessage;
    }


    /**
     * Gets the wagerLock value for this CouponInfoDTO.
     * 
     * @return wagerLock
     */
    public java.math.BigDecimal getWagerLock() {
        return wagerLock;
    }


    /**
     * Sets the wagerLock value for this CouponInfoDTO.
     * 
     * @param wagerLock
     */
    public void setWagerLock(java.math.BigDecimal wagerLock) {
        this.wagerLock = wagerLock;
    }


    /**
     * Gets the isPrivate value for this CouponInfoDTO.
     * 
     * @return isPrivate
     */
    public boolean isIsPrivate() {
        return isPrivate;
    }


    /**
     * Sets the isPrivate value for this CouponInfoDTO.
     * 
     * @param isPrivate
     */
    public void setIsPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }


    /**
     * Gets the isAdminUseOnly value for this CouponInfoDTO.
     * 
     * @return isAdminUseOnly
     */
    public boolean isIsAdminUseOnly() {
        return isAdminUseOnly;
    }


    /**
     * Sets the isAdminUseOnly value for this CouponInfoDTO.
     * 
     * @param isAdminUseOnly
     */
    public void setIsAdminUseOnly(boolean isAdminUseOnly) {
        this.isAdminUseOnly = isAdminUseOnly;
    }


    /**
     * Gets the gameKeyName value for this CouponInfoDTO.
     * 
     * @return gameKeyName
     */
    public java.lang.String getGameKeyName() {
        return gameKeyName;
    }


    /**
     * Sets the gameKeyName value for this CouponInfoDTO.
     * 
     * @param gameKeyName
     */
    public void setGameKeyName(java.lang.String gameKeyName) {
        this.gameKeyName = gameKeyName;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CouponInfoDTO)) return false;
        CouponInfoDTO other = (CouponInfoDTO) obj;
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
            ((this.percentage==null && other.getPercentage()==null) || 
             (this.percentage!=null &&
              this.percentage.equals(other.getPercentage()))) &&
            ((this.maxRedeemAmount==null && other.getMaxRedeemAmount()==null) || 
             (this.maxRedeemAmount!=null &&
              this.maxRedeemAmount.equals(other.getMaxRedeemAmount()))) &&
            ((this.couponAmount==null && other.getCouponAmount()==null) || 
             (this.couponAmount!=null &&
              this.couponAmount.equals(other.getCouponAmount()))) &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            this.wagerRequirement == other.getWagerRequirement() &&
            this.couponTypeId == other.getCouponTypeId() &&
            ((this.autoCancelAmount==null && other.getAutoCancelAmount()==null) || 
             (this.autoCancelAmount!=null &&
              this.autoCancelAmount.equals(other.getAutoCancelAmount()))) &&
            ((this.couponTypeName==null && other.getCouponTypeName()==null) || 
             (this.couponTypeName!=null &&
              this.couponTypeName.equals(other.getCouponTypeName()))) &&
            ((this.trickleBack==null && other.getTrickleBack()==null) || 
             (this.trickleBack!=null &&
              this.trickleBack.equals(other.getTrickleBack()))) &&
            ((this.freeConversionMultiplier==null && other.getFreeConversionMultiplier()==null) || 
             (this.freeConversionMultiplier!=null &&
              this.freeConversionMultiplier.equals(other.getFreeConversionMultiplier()))) &&
            ((this.minDepositAmount==null && other.getMinDepositAmount()==null) || 
             (this.minDepositAmount!=null &&
              this.minDepositAmount.equals(other.getMinDepositAmount()))) &&
            ((this.code==null && other.getCode()==null) || 
             (this.code!=null &&
              this.code.equals(other.getCode()))) &&
            ((this.playerClassId==null && other.getPlayerClassId()==null) || 
             (this.playerClassId!=null &&
              this.playerClassId.equals(other.getPlayerClassId()))) &&
            ((this.playerClassName==null && other.getPlayerClassName()==null) || 
             (this.playerClassName!=null &&
              this.playerClassName.equals(other.getPlayerClassName()))) &&
            ((this.dtStart==null && other.getDtStart()==null) || 
             (this.dtStart!=null &&
              this.dtStart.equals(other.getDtStart()))) &&
            ((this.dtExpire==null && other.getDtExpire()==null) || 
             (this.dtExpire!=null &&
              this.dtExpire.equals(other.getDtExpire()))) &&
            ((this.maxBalanceAmount==null && other.getMaxBalanceAmount()==null) || 
             (this.maxBalanceAmount!=null &&
              this.maxBalanceAmount.equals(other.getMaxBalanceAmount()))) &&
            this.isSignup == other.isIsSignup() &&
            this.totalRedeemedByPlayer == other.getTotalRedeemedByPlayer() &&
            ((this.currencySymbol==null && other.getCurrencySymbol()==null) || 
             (this.currencySymbol!=null &&
              this.currencySymbol.equals(other.getCurrencySymbol()))) &&
            ((this.freeSpinCount==null && other.getFreeSpinCount()==null) || 
             (this.freeSpinCount!=null &&
              this.freeSpinCount.equals(other.getFreeSpinCount()))) &&
            ((this.freeSpinValue==null && other.getFreeSpinValue()==null) || 
             (this.freeSpinValue!=null &&
              this.freeSpinValue.equals(other.getFreeSpinValue()))) &&
            ((this.gameName==null && other.getGameName()==null) || 
             (this.gameName!=null &&
              this.gameName.equals(other.getGameName()))) &&
            ((this.maxRedemptionsPerPlayer==null && other.getMaxRedemptionsPerPlayer()==null) || 
             (this.maxRedemptionsPerPlayer!=null &&
              this.maxRedemptionsPerPlayer.equals(other.getMaxRedemptionsPerPlayer()))) &&
            ((this.complexConfig==null && other.getComplexConfig()==null) || 
             (this.complexConfig!=null &&
              java.util.Arrays.equals(this.complexConfig, other.getComplexConfig()))) &&
            this.lockTillDeposit == other.isLockTillDeposit() &&
            this.blockRedeem == other.isBlockRedeem() &&
            ((this.blockMessage==null && other.getBlockMessage()==null) || 
             (this.blockMessage!=null &&
              this.blockMessage.equals(other.getBlockMessage()))) &&
            ((this.wagerLock==null && other.getWagerLock()==null) || 
             (this.wagerLock!=null &&
              this.wagerLock.equals(other.getWagerLock()))) &&
            this.isPrivate == other.isIsPrivate() &&
            this.isAdminUseOnly == other.isIsAdminUseOnly() &&
            ((this.gameKeyName==null && other.getGameKeyName()==null) || 
             (this.gameKeyName!=null &&
              this.gameKeyName.equals(other.getGameKeyName())));
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
        if (getPercentage() != null) {
            _hashCode += getPercentage().hashCode();
        }
        if (getMaxRedeemAmount() != null) {
            _hashCode += getMaxRedeemAmount().hashCode();
        }
        if (getCouponAmount() != null) {
            _hashCode += getCouponAmount().hashCode();
        }
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        _hashCode += getWagerRequirement();
        _hashCode += getCouponTypeId();
        if (getAutoCancelAmount() != null) {
            _hashCode += getAutoCancelAmount().hashCode();
        }
        if (getCouponTypeName() != null) {
            _hashCode += getCouponTypeName().hashCode();
        }
        if (getTrickleBack() != null) {
            _hashCode += getTrickleBack().hashCode();
        }
        if (getFreeConversionMultiplier() != null) {
            _hashCode += getFreeConversionMultiplier().hashCode();
        }
        if (getMinDepositAmount() != null) {
            _hashCode += getMinDepositAmount().hashCode();
        }
        if (getCode() != null) {
            _hashCode += getCode().hashCode();
        }
        if (getPlayerClassId() != null) {
            _hashCode += getPlayerClassId().hashCode();
        }
        if (getPlayerClassName() != null) {
            _hashCode += getPlayerClassName().hashCode();
        }
        if (getDtStart() != null) {
            _hashCode += getDtStart().hashCode();
        }
        if (getDtExpire() != null) {
            _hashCode += getDtExpire().hashCode();
        }
        if (getMaxBalanceAmount() != null) {
            _hashCode += getMaxBalanceAmount().hashCode();
        }
        _hashCode += (isIsSignup() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += getTotalRedeemedByPlayer();
        if (getCurrencySymbol() != null) {
            _hashCode += getCurrencySymbol().hashCode();
        }
        if (getFreeSpinCount() != null) {
            _hashCode += getFreeSpinCount().hashCode();
        }
        if (getFreeSpinValue() != null) {
            _hashCode += getFreeSpinValue().hashCode();
        }
        if (getGameName() != null) {
            _hashCode += getGameName().hashCode();
        }
        if (getMaxRedemptionsPerPlayer() != null) {
            _hashCode += getMaxRedemptionsPerPlayer().hashCode();
        }
        if (getComplexConfig() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getComplexConfig());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getComplexConfig(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        _hashCode += (isLockTillDeposit() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isBlockRedeem() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getBlockMessage() != null) {
            _hashCode += getBlockMessage().hashCode();
        }
        if (getWagerLock() != null) {
            _hashCode += getWagerLock().hashCode();
        }
        _hashCode += (isIsPrivate() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isIsAdminUseOnly() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getGameKeyName() != null) {
            _hashCode += getGameKeyName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CouponInfoDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "CouponInfoDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("couponId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "CouponId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("percentage");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "Percentage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("maxRedeemAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "MaxRedeemAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("couponAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "CouponAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "Name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wagerRequirement");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "WagerRequirement"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("couponTypeId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "CouponTypeId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("autoCancelAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "AutoCancelAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("couponTypeName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "CouponTypeName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("trickleBack");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "TrickleBack"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("freeConversionMultiplier");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "FreeConversionMultiplier"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("minDepositAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "MinDepositAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("code");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "Code"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("playerClassId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "PlayerClassId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("playerClassName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "PlayerClassName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dtStart");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "DtStart"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dtExpire");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "DtExpire"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("maxBalanceAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "MaxBalanceAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isSignup");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "IsSignup"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalRedeemedByPlayer");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "TotalRedeemedByPlayer"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("currencySymbol");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "CurrencySymbol"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("freeSpinCount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "FreeSpinCount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("freeSpinValue");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "FreeSpinValue"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("gameName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("maxRedemptionsPerPlayer");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "MaxRedemptionsPerPlayer"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("complexConfig");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "ComplexConfig"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "ComplexDepositCouponInfo"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "ComplexDepositCouponInfo"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lockTillDeposit");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "LockTillDeposit"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("blockRedeem");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "BlockRedeem"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("blockMessage");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "BlockMessage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wagerLock");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "WagerLock"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isPrivate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "IsPrivate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isAdminUseOnly");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "IsAdminUseOnly"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("gameKeyName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameKeyName"));
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
