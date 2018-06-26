/**
 * PlayerGameResultsDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hy.pull.common.util.game.hb;

public class PlayerGameResultsDTO  implements java.io.Serializable {
    private java.lang.String username;

    private java.lang.String playerId;

    private java.lang.String brandGameId;

    private java.lang.String gameName;

    private java.lang.String gameKeyName;

    private java.lang.String gameInstanceId;

    private long friendlyGameInstanceId;

    private java.math.BigDecimal stake;

    private java.math.BigDecimal payout;

    private java.math.BigDecimal jackpotWin;

    private java.math.BigDecimal jackpotContribution;

    private java.util.Calendar dtStart;

    private java.util.Calendar dtCompleted;

    private java.lang.String gameStateName;

    private int gameStateId;

    private short gameTypeId;

    public PlayerGameResultsDTO() {
    }

    public PlayerGameResultsDTO(
           java.lang.String username,
           java.lang.String playerId,
           java.lang.String brandGameId,
           java.lang.String gameName,
           java.lang.String gameKeyName,
           java.lang.String gameInstanceId,
           long friendlyGameInstanceId,
           java.math.BigDecimal stake,
           java.math.BigDecimal payout,
           java.math.BigDecimal jackpotWin,
           java.math.BigDecimal jackpotContribution,
           java.util.Calendar dtStart,
           java.util.Calendar dtCompleted,
           java.lang.String gameStateName,
           int gameStateId,
           short gameTypeId) {
           this.username = username;
           this.playerId = playerId;
           this.brandGameId = brandGameId;
           this.gameName = gameName;
           this.gameKeyName = gameKeyName;
           this.gameInstanceId = gameInstanceId;
           this.friendlyGameInstanceId = friendlyGameInstanceId;
           this.stake = stake;
           this.payout = payout;
           this.jackpotWin = jackpotWin;
           this.jackpotContribution = jackpotContribution;
           this.dtStart = dtStart;
           this.dtCompleted = dtCompleted;
           this.gameStateName = gameStateName;
           this.gameStateId = gameStateId;
           this.gameTypeId = gameTypeId;
    }


    /**
     * Gets the username value for this PlayerGameResultsDTO.
     * 
     * @return username
     */
    public java.lang.String getUsername() {
        return username;
    }


    /**
     * Sets the username value for this PlayerGameResultsDTO.
     * 
     * @param username
     */
    public void setUsername(java.lang.String username) {
        this.username = username;
    }


    /**
     * Gets the playerId value for this PlayerGameResultsDTO.
     * 
     * @return playerId
     */
    public java.lang.String getPlayerId() {
        return playerId;
    }


    /**
     * Sets the playerId value for this PlayerGameResultsDTO.
     * 
     * @param playerId
     */
    public void setPlayerId(java.lang.String playerId) {
        this.playerId = playerId;
    }


    /**
     * Gets the brandGameId value for this PlayerGameResultsDTO.
     * 
     * @return brandGameId
     */
    public java.lang.String getBrandGameId() {
        return brandGameId;
    }


    /**
     * Sets the brandGameId value for this PlayerGameResultsDTO.
     * 
     * @param brandGameId
     */
    public void setBrandGameId(java.lang.String brandGameId) {
        this.brandGameId = brandGameId;
    }


    /**
     * Gets the gameName value for this PlayerGameResultsDTO.
     * 
     * @return gameName
     */
    public java.lang.String getGameName() {
        return gameName;
    }


    /**
     * Sets the gameName value for this PlayerGameResultsDTO.
     * 
     * @param gameName
     */
    public void setGameName(java.lang.String gameName) {
        this.gameName = gameName;
    }


    /**
     * Gets the gameKeyName value for this PlayerGameResultsDTO.
     * 
     * @return gameKeyName
     */
    public java.lang.String getGameKeyName() {
        return gameKeyName;
    }


    /**
     * Sets the gameKeyName value for this PlayerGameResultsDTO.
     * 
     * @param gameKeyName
     */
    public void setGameKeyName(java.lang.String gameKeyName) {
        this.gameKeyName = gameKeyName;
    }


    /**
     * Gets the gameInstanceId value for this PlayerGameResultsDTO.
     * 
     * @return gameInstanceId
     */
    public java.lang.String getGameInstanceId() {
        return gameInstanceId;
    }


    /**
     * Sets the gameInstanceId value for this PlayerGameResultsDTO.
     * 
     * @param gameInstanceId
     */
    public void setGameInstanceId(java.lang.String gameInstanceId) {
        this.gameInstanceId = gameInstanceId;
    }


    /**
     * Gets the friendlyGameInstanceId value for this PlayerGameResultsDTO.
     * 
     * @return friendlyGameInstanceId
     */
    public long getFriendlyGameInstanceId() {
        return friendlyGameInstanceId;
    }


    /**
     * Sets the friendlyGameInstanceId value for this PlayerGameResultsDTO.
     * 
     * @param friendlyGameInstanceId
     */
    public void setFriendlyGameInstanceId(long friendlyGameInstanceId) {
        this.friendlyGameInstanceId = friendlyGameInstanceId;
    }


    /**
     * Gets the stake value for this PlayerGameResultsDTO.
     * 
     * @return stake
     */
    public java.math.BigDecimal getStake() {
        return stake;
    }


    /**
     * Sets the stake value for this PlayerGameResultsDTO.
     * 
     * @param stake
     */
    public void setStake(java.math.BigDecimal stake) {
        this.stake = stake;
    }


    /**
     * Gets the payout value for this PlayerGameResultsDTO.
     * 
     * @return payout
     */
    public java.math.BigDecimal getPayout() {
        return payout;
    }


    /**
     * Sets the payout value for this PlayerGameResultsDTO.
     * 
     * @param payout
     */
    public void setPayout(java.math.BigDecimal payout) {
        this.payout = payout;
    }


    /**
     * Gets the jackpotWin value for this PlayerGameResultsDTO.
     * 
     * @return jackpotWin
     */
    public java.math.BigDecimal getJackpotWin() {
        return jackpotWin;
    }


    /**
     * Sets the jackpotWin value for this PlayerGameResultsDTO.
     * 
     * @param jackpotWin
     */
    public void setJackpotWin(java.math.BigDecimal jackpotWin) {
        this.jackpotWin = jackpotWin;
    }


    /**
     * Gets the jackpotContribution value for this PlayerGameResultsDTO.
     * 
     * @return jackpotContribution
     */
    public java.math.BigDecimal getJackpotContribution() {
        return jackpotContribution;
    }


    /**
     * Sets the jackpotContribution value for this PlayerGameResultsDTO.
     * 
     * @param jackpotContribution
     */
    public void setJackpotContribution(java.math.BigDecimal jackpotContribution) {
        this.jackpotContribution = jackpotContribution;
    }


    /**
     * Gets the dtStart value for this PlayerGameResultsDTO.
     * 
     * @return dtStart
     */
    public java.util.Calendar getDtStart() {
        return dtStart;
    }


    /**
     * Sets the dtStart value for this PlayerGameResultsDTO.
     * 
     * @param dtStart
     */
    public void setDtStart(java.util.Calendar dtStart) {
        this.dtStart = dtStart;
    }


    /**
     * Gets the dtCompleted value for this PlayerGameResultsDTO.
     * 
     * @return dtCompleted
     */
    public java.util.Calendar getDtCompleted() {
        return dtCompleted;
    }


    /**
     * Sets the dtCompleted value for this PlayerGameResultsDTO.
     * 
     * @param dtCompleted
     */
    public void setDtCompleted(java.util.Calendar dtCompleted) {
        this.dtCompleted = dtCompleted;
    }


    /**
     * Gets the gameStateName value for this PlayerGameResultsDTO.
     * 
     * @return gameStateName
     */
    public java.lang.String getGameStateName() {
        return gameStateName;
    }


    /**
     * Sets the gameStateName value for this PlayerGameResultsDTO.
     * 
     * @param gameStateName
     */
    public void setGameStateName(java.lang.String gameStateName) {
        this.gameStateName = gameStateName;
    }


    /**
     * Gets the gameStateId value for this PlayerGameResultsDTO.
     * 
     * @return gameStateId
     */
    public int getGameStateId() {
        return gameStateId;
    }


    /**
     * Sets the gameStateId value for this PlayerGameResultsDTO.
     * 
     * @param gameStateId
     */
    public void setGameStateId(int gameStateId) {
        this.gameStateId = gameStateId;
    }


    /**
     * Gets the gameTypeId value for this PlayerGameResultsDTO.
     * 
     * @return gameTypeId
     */
    public short getGameTypeId() {
        return gameTypeId;
    }


    /**
     * Sets the gameTypeId value for this PlayerGameResultsDTO.
     * 
     * @param gameTypeId
     */
    public void setGameTypeId(short gameTypeId) {
        this.gameTypeId = gameTypeId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PlayerGameResultsDTO)) return false;
        PlayerGameResultsDTO other = (PlayerGameResultsDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.username==null && other.getUsername()==null) || 
             (this.username!=null &&
              this.username.equals(other.getUsername()))) &&
            ((this.playerId==null && other.getPlayerId()==null) || 
             (this.playerId!=null &&
              this.playerId.equals(other.getPlayerId()))) &&
            ((this.brandGameId==null && other.getBrandGameId()==null) || 
             (this.brandGameId!=null &&
              this.brandGameId.equals(other.getBrandGameId()))) &&
            ((this.gameName==null && other.getGameName()==null) || 
             (this.gameName!=null &&
              this.gameName.equals(other.getGameName()))) &&
            ((this.gameKeyName==null && other.getGameKeyName()==null) || 
             (this.gameKeyName!=null &&
              this.gameKeyName.equals(other.getGameKeyName()))) &&
            ((this.gameInstanceId==null && other.getGameInstanceId()==null) || 
             (this.gameInstanceId!=null &&
              this.gameInstanceId.equals(other.getGameInstanceId()))) &&
            this.friendlyGameInstanceId == other.getFriendlyGameInstanceId() &&
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
            ((this.dtStart==null && other.getDtStart()==null) || 
             (this.dtStart!=null &&
              this.dtStart.equals(other.getDtStart()))) &&
            ((this.dtCompleted==null && other.getDtCompleted()==null) || 
             (this.dtCompleted!=null &&
              this.dtCompleted.equals(other.getDtCompleted()))) &&
            ((this.gameStateName==null && other.getGameStateName()==null) || 
             (this.gameStateName!=null &&
              this.gameStateName.equals(other.getGameStateName()))) &&
            this.gameStateId == other.getGameStateId() &&
            this.gameTypeId == other.getGameTypeId();
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
        if (getUsername() != null) {
            _hashCode += getUsername().hashCode();
        }
        if (getPlayerId() != null) {
            _hashCode += getPlayerId().hashCode();
        }
        if (getBrandGameId() != null) {
            _hashCode += getBrandGameId().hashCode();
        }
        if (getGameName() != null) {
            _hashCode += getGameName().hashCode();
        }
        if (getGameKeyName() != null) {
            _hashCode += getGameKeyName().hashCode();
        }
        if (getGameInstanceId() != null) {
            _hashCode += getGameInstanceId().hashCode();
        }
        _hashCode += new Long(getFriendlyGameInstanceId()).hashCode();
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
        if (getDtStart() != null) {
            _hashCode += getDtStart().hashCode();
        }
        if (getDtCompleted() != null) {
            _hashCode += getDtCompleted().hashCode();
        }
        if (getGameStateName() != null) {
            _hashCode += getGameStateName().hashCode();
        }
        _hashCode += getGameStateId();
        _hashCode += getGameTypeId();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PlayerGameResultsDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "PlayerGameResultsDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("username");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "Username"));
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
        elemField.setFieldName("brandGameId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "BrandGameId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
        elemField.setFieldName("gameInstanceId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameInstanceId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("friendlyGameInstanceId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "FriendlyGameInstanceId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
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
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("jackpotWin");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "JackpotWin"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("jackpotContribution");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "JackpotContribution"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dtStart");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "DtStart"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dtCompleted");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "DtCompleted"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("gameStateName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameStateName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("gameStateId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameStateId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("gameTypeId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameTypeId"));
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
