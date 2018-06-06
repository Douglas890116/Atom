/**
 * PlayerCompletedGamesDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hy.pull.common.util.game.hb;

public class PlayerCompletedGamesDTO  implements java.io.Serializable {
    private java.lang.String playerId;

    private java.lang.String brandId;

    private java.lang.String username;

    private java.lang.String brandGameId;

    private java.lang.String gameKeyName;

    private short gameTypeId;

    private java.util.Calendar dtStarted;

    private java.util.Calendar dtCompleted;

    private long friendlyGameInstanceId;

    private java.lang.String gameInstanceId;

    private java.math.BigDecimal stake;

    private java.math.BigDecimal payout;

    private java.math.BigDecimal jackpotWin;

    private java.math.BigDecimal jackpotContribution;

    private java.lang.String currencyCode;

    private short channelTypeId;

    public PlayerCompletedGamesDTO() {
    }

    public PlayerCompletedGamesDTO(
           java.lang.String playerId,
           java.lang.String brandId,
           java.lang.String username,
           java.lang.String brandGameId,
           java.lang.String gameKeyName,
           short gameTypeId,
           java.util.Calendar dtStarted,
           java.util.Calendar dtCompleted,
           long friendlyGameInstanceId,
           java.lang.String gameInstanceId,
           java.math.BigDecimal stake,
           java.math.BigDecimal payout,
           java.math.BigDecimal jackpotWin,
           java.math.BigDecimal jackpotContribution,
           java.lang.String currencyCode,
           short channelTypeId) {
           this.playerId = playerId;
           this.brandId = brandId;
           this.username = username;
           this.brandGameId = brandGameId;
           this.gameKeyName = gameKeyName;
           this.gameTypeId = gameTypeId;
           this.dtStarted = dtStarted;
           this.dtCompleted = dtCompleted;
           this.friendlyGameInstanceId = friendlyGameInstanceId;
           this.gameInstanceId = gameInstanceId;
           this.stake = stake;
           this.payout = payout;
           this.jackpotWin = jackpotWin;
           this.jackpotContribution = jackpotContribution;
           this.currencyCode = currencyCode;
           this.channelTypeId = channelTypeId;
    }


    /**
     * Gets the playerId value for this PlayerCompletedGamesDTO.
     * 
     * @return playerId
     */
    public java.lang.String getPlayerId() {
        return playerId;
    }


    /**
     * Sets the playerId value for this PlayerCompletedGamesDTO.
     * 
     * @param playerId
     */
    public void setPlayerId(java.lang.String playerId) {
        this.playerId = playerId;
    }


    /**
     * Gets the brandId value for this PlayerCompletedGamesDTO.
     * 
     * @return brandId
     */
    public java.lang.String getBrandId() {
        return brandId;
    }


    /**
     * Sets the brandId value for this PlayerCompletedGamesDTO.
     * 
     * @param brandId
     */
    public void setBrandId(java.lang.String brandId) {
        this.brandId = brandId;
    }


    /**
     * Gets the username value for this PlayerCompletedGamesDTO.
     * 
     * @return username
     */
    public java.lang.String getUsername() {
        return username;
    }


    /**
     * Sets the username value for this PlayerCompletedGamesDTO.
     * 
     * @param username
     */
    public void setUsername(java.lang.String username) {
        this.username = username;
    }


    /**
     * Gets the brandGameId value for this PlayerCompletedGamesDTO.
     * 
     * @return brandGameId
     */
    public java.lang.String getBrandGameId() {
        return brandGameId;
    }


    /**
     * Sets the brandGameId value for this PlayerCompletedGamesDTO.
     * 
     * @param brandGameId
     */
    public void setBrandGameId(java.lang.String brandGameId) {
        this.brandGameId = brandGameId;
    }


    /**
     * Gets the gameKeyName value for this PlayerCompletedGamesDTO.
     * 
     * @return gameKeyName
     */
    public java.lang.String getGameKeyName() {
        return gameKeyName;
    }


    /**
     * Sets the gameKeyName value for this PlayerCompletedGamesDTO.
     * 
     * @param gameKeyName
     */
    public void setGameKeyName(java.lang.String gameKeyName) {
        this.gameKeyName = gameKeyName;
    }


    /**
     * Gets the gameTypeId value for this PlayerCompletedGamesDTO.
     * 
     * @return gameTypeId
     */
    public short getGameTypeId() {
        return gameTypeId;
    }


    /**
     * Sets the gameTypeId value for this PlayerCompletedGamesDTO.
     * 
     * @param gameTypeId
     */
    public void setGameTypeId(short gameTypeId) {
        this.gameTypeId = gameTypeId;
    }


    /**
     * Gets the dtStarted value for this PlayerCompletedGamesDTO.
     * 
     * @return dtStarted
     */
    public java.util.Calendar getDtStarted() {
        return dtStarted;
    }


    /**
     * Sets the dtStarted value for this PlayerCompletedGamesDTO.
     * 
     * @param dtStarted
     */
    public void setDtStarted(java.util.Calendar dtStarted) {
        this.dtStarted = dtStarted;
    }


    /**
     * Gets the dtCompleted value for this PlayerCompletedGamesDTO.
     * 
     * @return dtCompleted
     */
    public java.util.Calendar getDtCompleted() {
        return dtCompleted;
    }


    /**
     * Sets the dtCompleted value for this PlayerCompletedGamesDTO.
     * 
     * @param dtCompleted
     */
    public void setDtCompleted(java.util.Calendar dtCompleted) {
        this.dtCompleted = dtCompleted;
    }


    /**
     * Gets the friendlyGameInstanceId value for this PlayerCompletedGamesDTO.
     * 
     * @return friendlyGameInstanceId
     */
    public long getFriendlyGameInstanceId() {
        return friendlyGameInstanceId;
    }


    /**
     * Sets the friendlyGameInstanceId value for this PlayerCompletedGamesDTO.
     * 
     * @param friendlyGameInstanceId
     */
    public void setFriendlyGameInstanceId(long friendlyGameInstanceId) {
        this.friendlyGameInstanceId = friendlyGameInstanceId;
    }


    /**
     * Gets the gameInstanceId value for this PlayerCompletedGamesDTO.
     * 
     * @return gameInstanceId
     */
    public java.lang.String getGameInstanceId() {
        return gameInstanceId;
    }


    /**
     * Sets the gameInstanceId value for this PlayerCompletedGamesDTO.
     * 
     * @param gameInstanceId
     */
    public void setGameInstanceId(java.lang.String gameInstanceId) {
        this.gameInstanceId = gameInstanceId;
    }


    /**
     * Gets the stake value for this PlayerCompletedGamesDTO.
     * 
     * @return stake
     */
    public java.math.BigDecimal getStake() {
        return stake;
    }


    /**
     * Sets the stake value for this PlayerCompletedGamesDTO.
     * 
     * @param stake
     */
    public void setStake(java.math.BigDecimal stake) {
        this.stake = stake;
    }


    /**
     * Gets the payout value for this PlayerCompletedGamesDTO.
     * 
     * @return payout
     */
    public java.math.BigDecimal getPayout() {
        return payout;
    }


    /**
     * Sets the payout value for this PlayerCompletedGamesDTO.
     * 
     * @param payout
     */
    public void setPayout(java.math.BigDecimal payout) {
        this.payout = payout;
    }


    /**
     * Gets the jackpotWin value for this PlayerCompletedGamesDTO.
     * 
     * @return jackpotWin
     */
    public java.math.BigDecimal getJackpotWin() {
        return jackpotWin;
    }


    /**
     * Sets the jackpotWin value for this PlayerCompletedGamesDTO.
     * 
     * @param jackpotWin
     */
    public void setJackpotWin(java.math.BigDecimal jackpotWin) {
        this.jackpotWin = jackpotWin;
    }


    /**
     * Gets the jackpotContribution value for this PlayerCompletedGamesDTO.
     * 
     * @return jackpotContribution
     */
    public java.math.BigDecimal getJackpotContribution() {
        return jackpotContribution;
    }


    /**
     * Sets the jackpotContribution value for this PlayerCompletedGamesDTO.
     * 
     * @param jackpotContribution
     */
    public void setJackpotContribution(java.math.BigDecimal jackpotContribution) {
        this.jackpotContribution = jackpotContribution;
    }


    /**
     * Gets the currencyCode value for this PlayerCompletedGamesDTO.
     * 
     * @return currencyCode
     */
    public java.lang.String getCurrencyCode() {
        return currencyCode;
    }


    /**
     * Sets the currencyCode value for this PlayerCompletedGamesDTO.
     * 
     * @param currencyCode
     */
    public void setCurrencyCode(java.lang.String currencyCode) {
        this.currencyCode = currencyCode;
    }


    /**
     * Gets the channelTypeId value for this PlayerCompletedGamesDTO.
     * 
     * @return channelTypeId
     */
    public short getChannelTypeId() {
        return channelTypeId;
    }


    /**
     * Sets the channelTypeId value for this PlayerCompletedGamesDTO.
     * 
     * @param channelTypeId
     */
    public void setChannelTypeId(short channelTypeId) {
        this.channelTypeId = channelTypeId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PlayerCompletedGamesDTO)) return false;
        PlayerCompletedGamesDTO other = (PlayerCompletedGamesDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.playerId==null && other.getPlayerId()==null) || 
             (this.playerId!=null &&
              this.playerId.equals(other.getPlayerId()))) &&
            ((this.brandId==null && other.getBrandId()==null) || 
             (this.brandId!=null &&
              this.brandId.equals(other.getBrandId()))) &&
            ((this.username==null && other.getUsername()==null) || 
             (this.username!=null &&
              this.username.equals(other.getUsername()))) &&
            ((this.brandGameId==null && other.getBrandGameId()==null) || 
             (this.brandGameId!=null &&
              this.brandGameId.equals(other.getBrandGameId()))) &&
            ((this.gameKeyName==null && other.getGameKeyName()==null) || 
             (this.gameKeyName!=null &&
              this.gameKeyName.equals(other.getGameKeyName()))) &&
            this.gameTypeId == other.getGameTypeId() &&
            ((this.dtStarted==null && other.getDtStarted()==null) || 
             (this.dtStarted!=null &&
              this.dtStarted.equals(other.getDtStarted()))) &&
            ((this.dtCompleted==null && other.getDtCompleted()==null) || 
             (this.dtCompleted!=null &&
              this.dtCompleted.equals(other.getDtCompleted()))) &&
            this.friendlyGameInstanceId == other.getFriendlyGameInstanceId() &&
            ((this.gameInstanceId==null && other.getGameInstanceId()==null) || 
             (this.gameInstanceId!=null &&
              this.gameInstanceId.equals(other.getGameInstanceId()))) &&
            ((this.stake==null && other.getStake()==null) || 
             (this.stake!=null &&
              this.stake.equals(other.getStake()))) &&
            ((this.payout==null && other.getPayout()==null) || 
             (this.payout!=null &&
              this.payout.equals(other.getPayout()))) &&
            ((this.jackpotWin==null && other.getJackpotWin()==null) || 
             (this.jackpotWin!=null &&
              this.jackpotWin.equals(other.getJackpotWin()))) &&
            ((this.jackpotContribution==null && other.getJackpotContribution()==null) || 
             (this.jackpotContribution!=null &&
              this.jackpotContribution.equals(other.getJackpotContribution()))) &&
            ((this.currencyCode==null && other.getCurrencyCode()==null) || 
             (this.currencyCode!=null &&
              this.currencyCode.equals(other.getCurrencyCode()))) &&
            this.channelTypeId == other.getChannelTypeId();
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
        if (getPlayerId() != null) {
            _hashCode += getPlayerId().hashCode();
        }
        if (getBrandId() != null) {
            _hashCode += getBrandId().hashCode();
        }
        if (getUsername() != null) {
            _hashCode += getUsername().hashCode();
        }
        if (getBrandGameId() != null) {
            _hashCode += getBrandGameId().hashCode();
        }
        if (getGameKeyName() != null) {
            _hashCode += getGameKeyName().hashCode();
        }
        _hashCode += getGameTypeId();
        if (getDtStarted() != null) {
            _hashCode += getDtStarted().hashCode();
        }
        if (getDtCompleted() != null) {
            _hashCode += getDtCompleted().hashCode();
        }
        _hashCode += new Long(getFriendlyGameInstanceId()).hashCode();
        if (getGameInstanceId() != null) {
            _hashCode += getGameInstanceId().hashCode();
        }
        if (getStake() != null) {
            _hashCode += getStake().hashCode();
        }
        if (getPayout() != null) {
            _hashCode += getPayout().hashCode();
        }
        if (getJackpotWin() != null) {
            _hashCode += getJackpotWin().hashCode();
        }
        if (getJackpotContribution() != null) {
            _hashCode += getJackpotContribution().hashCode();
        }
        if (getCurrencyCode() != null) {
            _hashCode += getCurrencyCode().hashCode();
        }
        _hashCode += getChannelTypeId();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PlayerCompletedGamesDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "PlayerCompletedGamesDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("playerId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "PlayerId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("brandId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "BrandId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("username");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "Username"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("brandGameId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "BrandGameId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
        elemField.setFieldName("gameTypeId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameTypeId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dtStarted");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "DtStarted"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dtCompleted");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "DtCompleted"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("friendlyGameInstanceId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "FriendlyGameInstanceId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("gameInstanceId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameInstanceId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stake");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "Stake"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("payout");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "Payout"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("jackpotWin");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "JackpotWin"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("jackpotContribution");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "JackpotContribution"));
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
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("channelTypeId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "ChannelTypeId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
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
