/**
 * QueryPlayerResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hy.pull.common.util.game.hb;

public class QueryPlayerResponse  implements java.io.Serializable {
    private boolean found;

    private java.lang.String playerId;

    private java.lang.String brandId;

    private java.lang.String brandName;

    private java.math.BigDecimal realBalance;

    private java.lang.String currencyCode;

    private java.lang.String currencySymbol;

    private java.lang.String token;

    private boolean hasBonus;

    private java.math.BigDecimal bonusBalance;

    private int bonusSpins;

    private int bonusGameId;

    private java.lang.String bonusGameKeyName;

    private java.math.BigDecimal bonusPercentage;

    private java.math.BigDecimal bonusWagerRemaining;

    private java.lang.String message;

    public QueryPlayerResponse() {
    }

    public QueryPlayerResponse(
           boolean found,
           java.lang.String playerId,
           java.lang.String brandId,
           java.lang.String brandName,
           java.math.BigDecimal realBalance,
           java.lang.String currencyCode,
           java.lang.String currencySymbol,
           java.lang.String token,
           boolean hasBonus,
           java.math.BigDecimal bonusBalance,
           int bonusSpins,
           int bonusGameId,
           java.lang.String bonusGameKeyName,
           java.math.BigDecimal bonusPercentage,
           java.math.BigDecimal bonusWagerRemaining,
           java.lang.String message) {
           this.found = found;
           this.playerId = playerId;
           this.brandId = brandId;
           this.brandName = brandName;
           this.realBalance = realBalance;
           this.currencyCode = currencyCode;
           this.currencySymbol = currencySymbol;
           this.token = token;
           this.hasBonus = hasBonus;
           this.bonusBalance = bonusBalance;
           this.bonusSpins = bonusSpins;
           this.bonusGameId = bonusGameId;
           this.bonusGameKeyName = bonusGameKeyName;
           this.bonusPercentage = bonusPercentage;
           this.bonusWagerRemaining = bonusWagerRemaining;
           this.message = message;
    }


    /**
     * Gets the found value for this QueryPlayerResponse.
     * 
     * @return found
     */
    public boolean isFound() {
        return found;
    }


    /**
     * Sets the found value for this QueryPlayerResponse.
     * 
     * @param found
     */
    public void setFound(boolean found) {
        this.found = found;
    }


    /**
     * Gets the playerId value for this QueryPlayerResponse.
     * 
     * @return playerId
     */
    public java.lang.String getPlayerId() {
        return playerId;
    }


    /**
     * Sets the playerId value for this QueryPlayerResponse.
     * 
     * @param playerId
     */
    public void setPlayerId(java.lang.String playerId) {
        this.playerId = playerId;
    }


    /**
     * Gets the brandId value for this QueryPlayerResponse.
     * 
     * @return brandId
     */
    public java.lang.String getBrandId() {
        return brandId;
    }


    /**
     * Sets the brandId value for this QueryPlayerResponse.
     * 
     * @param brandId
     */
    public void setBrandId(java.lang.String brandId) {
        this.brandId = brandId;
    }


    /**
     * Gets the brandName value for this QueryPlayerResponse.
     * 
     * @return brandName
     */
    public java.lang.String getBrandName() {
        return brandName;
    }


    /**
     * Sets the brandName value for this QueryPlayerResponse.
     * 
     * @param brandName
     */
    public void setBrandName(java.lang.String brandName) {
        this.brandName = brandName;
    }


    /**
     * Gets the realBalance value for this QueryPlayerResponse.
     * 
     * @return realBalance
     */
    public java.math.BigDecimal getRealBalance() {
        return realBalance;
    }


    /**
     * Sets the realBalance value for this QueryPlayerResponse.
     * 
     * @param realBalance
     */
    public void setRealBalance(java.math.BigDecimal realBalance) {
        this.realBalance = realBalance;
    }


    /**
     * Gets the currencyCode value for this QueryPlayerResponse.
     * 
     * @return currencyCode
     */
    public java.lang.String getCurrencyCode() {
        return currencyCode;
    }


    /**
     * Sets the currencyCode value for this QueryPlayerResponse.
     * 
     * @param currencyCode
     */
    public void setCurrencyCode(java.lang.String currencyCode) {
        this.currencyCode = currencyCode;
    }


    /**
     * Gets the currencySymbol value for this QueryPlayerResponse.
     * 
     * @return currencySymbol
     */
    public java.lang.String getCurrencySymbol() {
        return currencySymbol;
    }


    /**
     * Sets the currencySymbol value for this QueryPlayerResponse.
     * 
     * @param currencySymbol
     */
    public void setCurrencySymbol(java.lang.String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }


    /**
     * Gets the token value for this QueryPlayerResponse.
     * 
     * @return token
     */
    public java.lang.String getToken() {
        return token;
    }


    /**
     * Sets the token value for this QueryPlayerResponse.
     * 
     * @param token
     */
    public void setToken(java.lang.String token) {
        this.token = token;
    }


    /**
     * Gets the hasBonus value for this QueryPlayerResponse.
     * 
     * @return hasBonus
     */
    public boolean isHasBonus() {
        return hasBonus;
    }


    /**
     * Sets the hasBonus value for this QueryPlayerResponse.
     * 
     * @param hasBonus
     */
    public void setHasBonus(boolean hasBonus) {
        this.hasBonus = hasBonus;
    }


    /**
     * Gets the bonusBalance value for this QueryPlayerResponse.
     * 
     * @return bonusBalance
     */
    public java.math.BigDecimal getBonusBalance() {
        return bonusBalance;
    }


    /**
     * Sets the bonusBalance value for this QueryPlayerResponse.
     * 
     * @param bonusBalance
     */
    public void setBonusBalance(java.math.BigDecimal bonusBalance) {
        this.bonusBalance = bonusBalance;
    }


    /**
     * Gets the bonusSpins value for this QueryPlayerResponse.
     * 
     * @return bonusSpins
     */
    public int getBonusSpins() {
        return bonusSpins;
    }


    /**
     * Sets the bonusSpins value for this QueryPlayerResponse.
     * 
     * @param bonusSpins
     */
    public void setBonusSpins(int bonusSpins) {
        this.bonusSpins = bonusSpins;
    }


    /**
     * Gets the bonusGameId value for this QueryPlayerResponse.
     * 
     * @return bonusGameId
     */
    public int getBonusGameId() {
        return bonusGameId;
    }


    /**
     * Sets the bonusGameId value for this QueryPlayerResponse.
     * 
     * @param bonusGameId
     */
    public void setBonusGameId(int bonusGameId) {
        this.bonusGameId = bonusGameId;
    }


    /**
     * Gets the bonusGameKeyName value for this QueryPlayerResponse.
     * 
     * @return bonusGameKeyName
     */
    public java.lang.String getBonusGameKeyName() {
        return bonusGameKeyName;
    }


    /**
     * Sets the bonusGameKeyName value for this QueryPlayerResponse.
     * 
     * @param bonusGameKeyName
     */
    public void setBonusGameKeyName(java.lang.String bonusGameKeyName) {
        this.bonusGameKeyName = bonusGameKeyName;
    }


    /**
     * Gets the bonusPercentage value for this QueryPlayerResponse.
     * 
     * @return bonusPercentage
     */
    public java.math.BigDecimal getBonusPercentage() {
        return bonusPercentage;
    }


    /**
     * Sets the bonusPercentage value for this QueryPlayerResponse.
     * 
     * @param bonusPercentage
     */
    public void setBonusPercentage(java.math.BigDecimal bonusPercentage) {
        this.bonusPercentage = bonusPercentage;
    }


    /**
     * Gets the bonusWagerRemaining value for this QueryPlayerResponse.
     * 
     * @return bonusWagerRemaining
     */
    public java.math.BigDecimal getBonusWagerRemaining() {
        return bonusWagerRemaining;
    }


    /**
     * Sets the bonusWagerRemaining value for this QueryPlayerResponse.
     * 
     * @param bonusWagerRemaining
     */
    public void setBonusWagerRemaining(java.math.BigDecimal bonusWagerRemaining) {
        this.bonusWagerRemaining = bonusWagerRemaining;
    }


    /**
     * Gets the message value for this QueryPlayerResponse.
     * 
     * @return message
     */
    public java.lang.String getMessage() {
        return message;
    }


    /**
     * Sets the message value for this QueryPlayerResponse.
     * 
     * @param message
     */
    public void setMessage(java.lang.String message) {
        this.message = message;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof QueryPlayerResponse)) return false;
        QueryPlayerResponse other = (QueryPlayerResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.found == other.isFound() &&
            ((this.playerId==null && other.getPlayerId()==null) || 
             (this.playerId!=null &&
              this.playerId.equals(other.getPlayerId()))) &&
            ((this.brandId==null && other.getBrandId()==null) || 
             (this.brandId!=null &&
              this.brandId.equals(other.getBrandId()))) &&
            ((this.brandName==null && other.getBrandName()==null) || 
             (this.brandName!=null &&
              this.brandName.equals(other.getBrandName()))) &&
            ((this.realBalance==null && other.getRealBalance()==null) || 
             (this.realBalance!=null &&
              this.realBalance.equals(other.getRealBalance()))) &&
            ((this.currencyCode==null && other.getCurrencyCode()==null) || 
             (this.currencyCode!=null &&
              this.currencyCode.equals(other.getCurrencyCode()))) &&
            ((this.currencySymbol==null && other.getCurrencySymbol()==null) || 
             (this.currencySymbol!=null &&
              this.currencySymbol.equals(other.getCurrencySymbol()))) &&
            ((this.token==null && other.getToken()==null) || 
             (this.token!=null &&
              this.token.equals(other.getToken()))) &&
            this.hasBonus == other.isHasBonus() &&
            ((this.bonusBalance==null && other.getBonusBalance()==null) || 
             (this.bonusBalance!=null &&
              this.bonusBalance.equals(other.getBonusBalance()))) &&
            this.bonusSpins == other.getBonusSpins() &&
            this.bonusGameId == other.getBonusGameId() &&
            ((this.bonusGameKeyName==null && other.getBonusGameKeyName()==null) || 
             (this.bonusGameKeyName!=null &&
              this.bonusGameKeyName.equals(other.getBonusGameKeyName()))) &&
            ((this.bonusPercentage==null && other.getBonusPercentage()==null) || 
             (this.bonusPercentage!=null &&
              this.bonusPercentage.equals(other.getBonusPercentage()))) &&
            ((this.bonusWagerRemaining==null && other.getBonusWagerRemaining()==null) || 
             (this.bonusWagerRemaining!=null &&
              this.bonusWagerRemaining.equals(other.getBonusWagerRemaining()))) &&
            ((this.message==null && other.getMessage()==null) || 
             (this.message!=null &&
              this.message.equals(other.getMessage())));
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
        _hashCode += (isFound() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getPlayerId() != null) {
            _hashCode += getPlayerId().hashCode();
        }
        if (getBrandId() != null) {
            _hashCode += getBrandId().hashCode();
        }
        if (getBrandName() != null) {
            _hashCode += getBrandName().hashCode();
        }
        if (getRealBalance() != null) {
            _hashCode += getRealBalance().hashCode();
        }
        if (getCurrencyCode() != null) {
            _hashCode += getCurrencyCode().hashCode();
        }
        if (getCurrencySymbol() != null) {
            _hashCode += getCurrencySymbol().hashCode();
        }
        if (getToken() != null) {
            _hashCode += getToken().hashCode();
        }
        _hashCode += (isHasBonus() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getBonusBalance() != null) {
            _hashCode += getBonusBalance().hashCode();
        }
        _hashCode += getBonusSpins();
        _hashCode += getBonusGameId();
        if (getBonusGameKeyName() != null) {
            _hashCode += getBonusGameKeyName().hashCode();
        }
        if (getBonusPercentage() != null) {
            _hashCode += getBonusPercentage().hashCode();
        }
        if (getBonusWagerRemaining() != null) {
            _hashCode += getBonusWagerRemaining().hashCode();
        }
        if (getMessage() != null) {
            _hashCode += getMessage().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(QueryPlayerResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "QueryPlayerResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("found");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "Found"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
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
        elemField.setFieldName("brandName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "BrandName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("realBalance");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "RealBalance"));
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
        elemField.setFieldName("currencySymbol");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "CurrencySymbol"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("token");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "Token"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hasBonus");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "HasBonus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bonusBalance");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "BonusBalance"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bonusSpins");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "BonusSpins"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bonusGameId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "BonusGameId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bonusGameKeyName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "BonusGameKeyName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bonusPercentage");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "BonusPercentage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bonusWagerRemaining");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "BonusWagerRemaining"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("message");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "Message"));
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
