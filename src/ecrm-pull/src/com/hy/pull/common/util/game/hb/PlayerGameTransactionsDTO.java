/**
 * PlayerGameTransactionsDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hy.pull.common.util.game.hb;

public class PlayerGameTransactionsDTO  implements java.io.Serializable {
    private java.lang.String username;

    private java.lang.String playerId;

    private java.lang.String brandGameId;

    private java.lang.String gameKeyName;

    private java.lang.String gameName;

    private java.lang.String gameInstanceId;

    private java.lang.String friendlyGameInstanceId;

    private java.lang.String transactionId;

    private short transactionTypeId;

    private java.lang.String transactionTypeName;

    private java.math.BigDecimal amount;

    private java.math.BigDecimal balanceAfter;

    private java.math.BigDecimal promoAmount;

    private java.math.BigDecimal promoBalanceAfter;

    private java.util.Calendar dtTx;

    public PlayerGameTransactionsDTO() {
    }

    public PlayerGameTransactionsDTO(
           java.lang.String username,
           java.lang.String playerId,
           java.lang.String brandGameId,
           java.lang.String gameKeyName,
           java.lang.String gameName,
           java.lang.String gameInstanceId,
           java.lang.String friendlyGameInstanceId,
           java.lang.String transactionId,
           short transactionTypeId,
           java.lang.String transactionTypeName,
           java.math.BigDecimal amount,
           java.math.BigDecimal balanceAfter,
           java.math.BigDecimal promoAmount,
           java.math.BigDecimal promoBalanceAfter,
           java.util.Calendar dtTx) {
           this.username = username;
           this.playerId = playerId;
           this.brandGameId = brandGameId;
           this.gameKeyName = gameKeyName;
           this.gameName = gameName;
           this.gameInstanceId = gameInstanceId;
           this.friendlyGameInstanceId = friendlyGameInstanceId;
           this.transactionId = transactionId;
           this.transactionTypeId = transactionTypeId;
           this.transactionTypeName = transactionTypeName;
           this.amount = amount;
           this.balanceAfter = balanceAfter;
           this.promoAmount = promoAmount;
           this.promoBalanceAfter = promoBalanceAfter;
           this.dtTx = dtTx;
    }


    /**
     * Gets the username value for this PlayerGameTransactionsDTO.
     * 
     * @return username
     */
    public java.lang.String getUsername() {
        return username;
    }


    /**
     * Sets the username value for this PlayerGameTransactionsDTO.
     * 
     * @param username
     */
    public void setUsername(java.lang.String username) {
        this.username = username;
    }


    /**
     * Gets the playerId value for this PlayerGameTransactionsDTO.
     * 
     * @return playerId
     */
    public java.lang.String getPlayerId() {
        return playerId;
    }


    /**
     * Sets the playerId value for this PlayerGameTransactionsDTO.
     * 
     * @param playerId
     */
    public void setPlayerId(java.lang.String playerId) {
        this.playerId = playerId;
    }


    /**
     * Gets the brandGameId value for this PlayerGameTransactionsDTO.
     * 
     * @return brandGameId
     */
    public java.lang.String getBrandGameId() {
        return brandGameId;
    }


    /**
     * Sets the brandGameId value for this PlayerGameTransactionsDTO.
     * 
     * @param brandGameId
     */
    public void setBrandGameId(java.lang.String brandGameId) {
        this.brandGameId = brandGameId;
    }


    /**
     * Gets the gameKeyName value for this PlayerGameTransactionsDTO.
     * 
     * @return gameKeyName
     */
    public java.lang.String getGameKeyName() {
        return gameKeyName;
    }


    /**
     * Sets the gameKeyName value for this PlayerGameTransactionsDTO.
     * 
     * @param gameKeyName
     */
    public void setGameKeyName(java.lang.String gameKeyName) {
        this.gameKeyName = gameKeyName;
    }


    /**
     * Gets the gameName value for this PlayerGameTransactionsDTO.
     * 
     * @return gameName
     */
    public java.lang.String getGameName() {
        return gameName;
    }


    /**
     * Sets the gameName value for this PlayerGameTransactionsDTO.
     * 
     * @param gameName
     */
    public void setGameName(java.lang.String gameName) {
        this.gameName = gameName;
    }


    /**
     * Gets the gameInstanceId value for this PlayerGameTransactionsDTO.
     * 
     * @return gameInstanceId
     */
    public java.lang.String getGameInstanceId() {
        return gameInstanceId;
    }


    /**
     * Sets the gameInstanceId value for this PlayerGameTransactionsDTO.
     * 
     * @param gameInstanceId
     */
    public void setGameInstanceId(java.lang.String gameInstanceId) {
        this.gameInstanceId = gameInstanceId;
    }


    /**
     * Gets the friendlyGameInstanceId value for this PlayerGameTransactionsDTO.
     * 
     * @return friendlyGameInstanceId
     */
    public java.lang.String getFriendlyGameInstanceId() {
        return friendlyGameInstanceId;
    }


    /**
     * Sets the friendlyGameInstanceId value for this PlayerGameTransactionsDTO.
     * 
     * @param friendlyGameInstanceId
     */
    public void setFriendlyGameInstanceId(java.lang.String friendlyGameInstanceId) {
        this.friendlyGameInstanceId = friendlyGameInstanceId;
    }


    /**
     * Gets the transactionId value for this PlayerGameTransactionsDTO.
     * 
     * @return transactionId
     */
    public java.lang.String getTransactionId() {
        return transactionId;
    }


    /**
     * Sets the transactionId value for this PlayerGameTransactionsDTO.
     * 
     * @param transactionId
     */
    public void setTransactionId(java.lang.String transactionId) {
        this.transactionId = transactionId;
    }


    /**
     * Gets the transactionTypeId value for this PlayerGameTransactionsDTO.
     * 
     * @return transactionTypeId
     */
    public short getTransactionTypeId() {
        return transactionTypeId;
    }


    /**
     * Sets the transactionTypeId value for this PlayerGameTransactionsDTO.
     * 
     * @param transactionTypeId
     */
    public void setTransactionTypeId(short transactionTypeId) {
        this.transactionTypeId = transactionTypeId;
    }


    /**
     * Gets the transactionTypeName value for this PlayerGameTransactionsDTO.
     * 
     * @return transactionTypeName
     */
    public java.lang.String getTransactionTypeName() {
        return transactionTypeName;
    }


    /**
     * Sets the transactionTypeName value for this PlayerGameTransactionsDTO.
     * 
     * @param transactionTypeName
     */
    public void setTransactionTypeName(java.lang.String transactionTypeName) {
        this.transactionTypeName = transactionTypeName;
    }


    /**
     * Gets the amount value for this PlayerGameTransactionsDTO.
     * 
     * @return amount
     */
    public java.math.BigDecimal getAmount() {
        return amount;
    }


    /**
     * Sets the amount value for this PlayerGameTransactionsDTO.
     * 
     * @param amount
     */
    public void setAmount(java.math.BigDecimal amount) {
        this.amount = amount;
    }


    /**
     * Gets the balanceAfter value for this PlayerGameTransactionsDTO.
     * 
     * @return balanceAfter
     */
    public java.math.BigDecimal getBalanceAfter() {
        return balanceAfter;
    }


    /**
     * Sets the balanceAfter value for this PlayerGameTransactionsDTO.
     * 
     * @param balanceAfter
     */
    public void setBalanceAfter(java.math.BigDecimal balanceAfter) {
        this.balanceAfter = balanceAfter;
    }


    /**
     * Gets the promoAmount value for this PlayerGameTransactionsDTO.
     * 
     * @return promoAmount
     */
    public java.math.BigDecimal getPromoAmount() {
        return promoAmount;
    }


    /**
     * Sets the promoAmount value for this PlayerGameTransactionsDTO.
     * 
     * @param promoAmount
     */
    public void setPromoAmount(java.math.BigDecimal promoAmount) {
        this.promoAmount = promoAmount;
    }


    /**
     * Gets the promoBalanceAfter value for this PlayerGameTransactionsDTO.
     * 
     * @return promoBalanceAfter
     */
    public java.math.BigDecimal getPromoBalanceAfter() {
        return promoBalanceAfter;
    }


    /**
     * Sets the promoBalanceAfter value for this PlayerGameTransactionsDTO.
     * 
     * @param promoBalanceAfter
     */
    public void setPromoBalanceAfter(java.math.BigDecimal promoBalanceAfter) {
        this.promoBalanceAfter = promoBalanceAfter;
    }


    /**
     * Gets the dtTx value for this PlayerGameTransactionsDTO.
     * 
     * @return dtTx
     */
    public java.util.Calendar getDtTx() {
        return dtTx;
    }


    /**
     * Sets the dtTx value for this PlayerGameTransactionsDTO.
     * 
     * @param dtTx
     */
    public void setDtTx(java.util.Calendar dtTx) {
        this.dtTx = dtTx;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PlayerGameTransactionsDTO)) return false;
        PlayerGameTransactionsDTO other = (PlayerGameTransactionsDTO) obj;
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
            ((this.gameKeyName==null && other.getGameKeyName()==null) || 
             (this.gameKeyName!=null &&
              this.gameKeyName.equals(other.getGameKeyName()))) &&
            ((this.gameName==null && other.getGameName()==null) || 
             (this.gameName!=null &&
              this.gameName.equals(other.getGameName()))) &&
            ((this.gameInstanceId==null && other.getGameInstanceId()==null) || 
             (this.gameInstanceId!=null &&
              this.gameInstanceId.equals(other.getGameInstanceId()))) &&
            ((this.friendlyGameInstanceId==null && other.getFriendlyGameInstanceId()==null) || 
             (this.friendlyGameInstanceId!=null &&
              this.friendlyGameInstanceId.equals(other.getFriendlyGameInstanceId()))) &&
            ((this.transactionId==null && other.getTransactionId()==null) || 
             (this.transactionId!=null &&
              this.transactionId.equals(other.getTransactionId()))) &&
            this.transactionTypeId == other.getTransactionTypeId() &&
            ((this.transactionTypeName==null && other.getTransactionTypeName()==null) || 
             (this.transactionTypeName!=null &&
              this.transactionTypeName.equals(other.getTransactionTypeName()))) &&
            ((this.amount==null && other.getAmount()==null) || 
             (this.amount!=null &&
              this.amount.equals(other.getAmount()))) &&
            ((this.balanceAfter==null && other.getBalanceAfter()==null) || 
             (this.balanceAfter!=null &&
              this.balanceAfter.equals(other.getBalanceAfter()))) &&
            ((this.promoAmount==null && other.getPromoAmount()==null) || 
             (this.promoAmount!=null &&
              this.promoAmount.equals(other.getPromoAmount()))) &&
            ((this.promoBalanceAfter==null && other.getPromoBalanceAfter()==null) || 
             (this.promoBalanceAfter!=null &&
              this.promoBalanceAfter.equals(other.getPromoBalanceAfter()))) &&
            ((this.dtTx==null && other.getDtTx()==null) || 
             (this.dtTx!=null &&
              this.dtTx.equals(other.getDtTx())));
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
        if (getGameKeyName() != null) {
            _hashCode += getGameKeyName().hashCode();
        }
        if (getGameName() != null) {
            _hashCode += getGameName().hashCode();
        }
        if (getGameInstanceId() != null) {
            _hashCode += getGameInstanceId().hashCode();
        }
        if (getFriendlyGameInstanceId() != null) {
            _hashCode += getFriendlyGameInstanceId().hashCode();
        }
        if (getTransactionId() != null) {
            _hashCode += getTransactionId().hashCode();
        }
        _hashCode += getTransactionTypeId();
        if (getTransactionTypeName() != null) {
            _hashCode += getTransactionTypeName().hashCode();
        }
        if (getAmount() != null) {
            _hashCode += getAmount().hashCode();
        }
        if (getBalanceAfter() != null) {
            _hashCode += getBalanceAfter().hashCode();
        }
        if (getPromoAmount() != null) {
            _hashCode += getPromoAmount().hashCode();
        }
        if (getPromoBalanceAfter() != null) {
            _hashCode += getPromoBalanceAfter().hashCode();
        }
        if (getDtTx() != null) {
            _hashCode += getDtTx().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PlayerGameTransactionsDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "PlayerGameTransactionsDTO"));
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
        elemField.setFieldName("gameKeyName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameKeyName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
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
        elemField.setFieldName("gameInstanceId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameInstanceId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("friendlyGameInstanceId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "FriendlyGameInstanceId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transactionId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "TransactionId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transactionTypeId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "TransactionTypeId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transactionTypeName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "TransactionTypeName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("amount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "Amount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("balanceAfter");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "BalanceAfter"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("promoAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "PromoAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("promoBalanceAfter");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "PromoBalanceAfter"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dtTx");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "DtTx"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
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
