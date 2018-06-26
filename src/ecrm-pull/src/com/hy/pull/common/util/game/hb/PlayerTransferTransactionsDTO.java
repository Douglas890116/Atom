/**
 * PlayerTransferTransactionsDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hy.pull.common.util.game.hb;

public class PlayerTransferTransactionsDTO  implements java.io.Serializable {
    private java.lang.String playerId;

    private java.lang.String brandId;

    private java.lang.String username;

    private java.lang.String transactionId;

    private java.lang.String requestId;

    private java.util.Calendar dtTx;

    private short transactionTypeId;

    private java.lang.String transactionTypeName;

    private java.math.BigDecimal amount;

    private java.math.BigDecimal balanceAfter;

    private java.lang.String currencyCode;

    public PlayerTransferTransactionsDTO() {
    }

    public PlayerTransferTransactionsDTO(
           java.lang.String playerId,
           java.lang.String brandId,
           java.lang.String username,
           java.lang.String transactionId,
           java.lang.String requestId,
           java.util.Calendar dtTx,
           short transactionTypeId,
           java.lang.String transactionTypeName,
           java.math.BigDecimal amount,
           java.math.BigDecimal balanceAfter,
           java.lang.String currencyCode) {
           this.playerId = playerId;
           this.brandId = brandId;
           this.username = username;
           this.transactionId = transactionId;
           this.requestId = requestId;
           this.dtTx = dtTx;
           this.transactionTypeId = transactionTypeId;
           this.transactionTypeName = transactionTypeName;
           this.amount = amount;
           this.balanceAfter = balanceAfter;
           this.currencyCode = currencyCode;
    }


    /**
     * Gets the playerId value for this PlayerTransferTransactionsDTO.
     * 
     * @return playerId
     */
    public java.lang.String getPlayerId() {
        return playerId;
    }


    /**
     * Sets the playerId value for this PlayerTransferTransactionsDTO.
     * 
     * @param playerId
     */
    public void setPlayerId(java.lang.String playerId) {
        this.playerId = playerId;
    }


    /**
     * Gets the brandId value for this PlayerTransferTransactionsDTO.
     * 
     * @return brandId
     */
    public java.lang.String getBrandId() {
        return brandId;
    }


    /**
     * Sets the brandId value for this PlayerTransferTransactionsDTO.
     * 
     * @param brandId
     */
    public void setBrandId(java.lang.String brandId) {
        this.brandId = brandId;
    }


    /**
     * Gets the username value for this PlayerTransferTransactionsDTO.
     * 
     * @return username
     */
    public java.lang.String getUsername() {
        return username;
    }


    /**
     * Sets the username value for this PlayerTransferTransactionsDTO.
     * 
     * @param username
     */
    public void setUsername(java.lang.String username) {
        this.username = username;
    }


    /**
     * Gets the transactionId value for this PlayerTransferTransactionsDTO.
     * 
     * @return transactionId
     */
    public java.lang.String getTransactionId() {
        return transactionId;
    }


    /**
     * Sets the transactionId value for this PlayerTransferTransactionsDTO.
     * 
     * @param transactionId
     */
    public void setTransactionId(java.lang.String transactionId) {
        this.transactionId = transactionId;
    }


    /**
     * Gets the requestId value for this PlayerTransferTransactionsDTO.
     * 
     * @return requestId
     */
    public java.lang.String getRequestId() {
        return requestId;
    }


    /**
     * Sets the requestId value for this PlayerTransferTransactionsDTO.
     * 
     * @param requestId
     */
    public void setRequestId(java.lang.String requestId) {
        this.requestId = requestId;
    }


    /**
     * Gets the dtTx value for this PlayerTransferTransactionsDTO.
     * 
     * @return dtTx
     */
    public java.util.Calendar getDtTx() {
        return dtTx;
    }


    /**
     * Sets the dtTx value for this PlayerTransferTransactionsDTO.
     * 
     * @param dtTx
     */
    public void setDtTx(java.util.Calendar dtTx) {
        this.dtTx = dtTx;
    }


    /**
     * Gets the transactionTypeId value for this PlayerTransferTransactionsDTO.
     * 
     * @return transactionTypeId
     */
    public short getTransactionTypeId() {
        return transactionTypeId;
    }


    /**
     * Sets the transactionTypeId value for this PlayerTransferTransactionsDTO.
     * 
     * @param transactionTypeId
     */
    public void setTransactionTypeId(short transactionTypeId) {
        this.transactionTypeId = transactionTypeId;
    }


    /**
     * Gets the transactionTypeName value for this PlayerTransferTransactionsDTO.
     * 
     * @return transactionTypeName
     */
    public java.lang.String getTransactionTypeName() {
        return transactionTypeName;
    }


    /**
     * Sets the transactionTypeName value for this PlayerTransferTransactionsDTO.
     * 
     * @param transactionTypeName
     */
    public void setTransactionTypeName(java.lang.String transactionTypeName) {
        this.transactionTypeName = transactionTypeName;
    }


    /**
     * Gets the amount value for this PlayerTransferTransactionsDTO.
     * 
     * @return amount
     */
    public java.math.BigDecimal getAmount() {
        return amount;
    }


    /**
     * Sets the amount value for this PlayerTransferTransactionsDTO.
     * 
     * @param amount
     */
    public void setAmount(java.math.BigDecimal amount) {
        this.amount = amount;
    }


    /**
     * Gets the balanceAfter value for this PlayerTransferTransactionsDTO.
     * 
     * @return balanceAfter
     */
    public java.math.BigDecimal getBalanceAfter() {
        return balanceAfter;
    }


    /**
     * Sets the balanceAfter value for this PlayerTransferTransactionsDTO.
     * 
     * @param balanceAfter
     */
    public void setBalanceAfter(java.math.BigDecimal balanceAfter) {
        this.balanceAfter = balanceAfter;
    }


    /**
     * Gets the currencyCode value for this PlayerTransferTransactionsDTO.
     * 
     * @return currencyCode
     */
    public java.lang.String getCurrencyCode() {
        return currencyCode;
    }


    /**
     * Sets the currencyCode value for this PlayerTransferTransactionsDTO.
     * 
     * @param currencyCode
     */
    public void setCurrencyCode(java.lang.String currencyCode) {
        this.currencyCode = currencyCode;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PlayerTransferTransactionsDTO)) return false;
        PlayerTransferTransactionsDTO other = (PlayerTransferTransactionsDTO) obj;
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
            ((this.transactionId==null && other.getTransactionId()==null) || 
             (this.transactionId!=null &&
              this.transactionId.equals(other.getTransactionId()))) &&
            ((this.requestId==null && other.getRequestId()==null) || 
             (this.requestId!=null &&
              this.requestId.equals(other.getRequestId()))) &&
            ((this.dtTx==null && other.getDtTx()==null) || 
             (this.dtTx!=null &&
              this.dtTx.equals(other.getDtTx()))) &&
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
        if (getTransactionId() != null) {
            _hashCode += getTransactionId().hashCode();
        }
        if (getRequestId() != null) {
            _hashCode += getRequestId().hashCode();
        }
        if (getDtTx() != null) {
            _hashCode += getDtTx().hashCode();
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
        if (getCurrencyCode() != null) {
            _hashCode += getCurrencyCode().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PlayerTransferTransactionsDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "PlayerTransferTransactionsDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("playerId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "PlayerId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
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
        elemField.setFieldName("transactionId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "TransactionId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("requestId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "RequestId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dtTx");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "DtTx"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
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
