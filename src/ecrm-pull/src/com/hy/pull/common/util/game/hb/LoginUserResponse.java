/**
 * LoginUserResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hy.pull.common.util.game.hb;

public class LoginUserResponse  implements java.io.Serializable {
    private boolean authenticated;

    private java.lang.String playerId;

    private java.lang.String brandId;

    private java.lang.String brandName;

    private java.lang.String token;

    private java.math.BigDecimal realBalance;

    private java.lang.String currencyCode;

    private boolean playerCreated;

    private boolean hasBonus;

    private java.math.BigDecimal bonusBalance;

    private int bonusSpins;

    private int bonusGameId;

    private java.math.BigDecimal bonusPercentage;

    private java.math.BigDecimal bonusWagerRemaining;

    private java.lang.String message;

    private java.lang.String currencySymbol;

    private java.lang.String bonusGameKeyName;

    public LoginUserResponse() {
    }

    public LoginUserResponse(
           boolean authenticated,
           java.lang.String playerId,
           java.lang.String brandId,
           java.lang.String brandName,
           java.lang.String token,
           java.math.BigDecimal realBalance,
           java.lang.String currencyCode,
           boolean playerCreated,
           boolean hasBonus,
           java.math.BigDecimal bonusBalance,
           int bonusSpins,
           int bonusGameId,
           java.math.BigDecimal bonusPercentage,
           java.math.BigDecimal bonusWagerRemaining,
           java.lang.String message,
           java.lang.String currencySymbol,
           java.lang.String bonusGameKeyName) {
           this.authenticated = authenticated;
           this.playerId = playerId;
           this.brandId = brandId;
           this.brandName = brandName;
           this.token = token;
           this.realBalance = realBalance;
           this.currencyCode = currencyCode;
           this.playerCreated = playerCreated;
           this.hasBonus = hasBonus;
           this.bonusBalance = bonusBalance;
           this.bonusSpins = bonusSpins;
           this.bonusGameId = bonusGameId;
           this.bonusPercentage = bonusPercentage;
           this.bonusWagerRemaining = bonusWagerRemaining;
           this.message = message;
           this.currencySymbol = currencySymbol;
           this.bonusGameKeyName = bonusGameKeyName;
    }


    /**
     * Gets the authenticated value for this LoginUserResponse.
     * 
     * @return authenticated
     */
    public boolean isAuthenticated() {
        return authenticated;
    }


    /**
     * Sets the authenticated value for this LoginUserResponse.
     * 
     * @param authenticated
     */
    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }


    /**
     * Gets the playerId value for this LoginUserResponse.
     * 
     * @return playerId
     */
    public java.lang.String getPlayerId() {
        return playerId;
    }


    /**
     * Sets the playerId value for this LoginUserResponse.
     * 
     * @param playerId
     */
    public void setPlayerId(java.lang.String playerId) {
        this.playerId = playerId;
    }


    /**
     * Gets the brandId value for this LoginUserResponse.
     * 
     * @return brandId
     */
    public java.lang.String getBrandId() {
        return brandId;
    }


    /**
     * Sets the brandId value for this LoginUserResponse.
     * 
     * @param brandId
     */
    public void setBrandId(java.lang.String brandId) {
        this.brandId = brandId;
    }


    /**
     * Gets the brandName value for this LoginUserResponse.
     * 
     * @return brandName
     */
    public java.lang.String getBrandName() {
        return brandName;
    }


    /**
     * Sets the brandName value for this LoginUserResponse.
     * 
     * @param brandName
     */
    public void setBrandName(java.lang.String brandName) {
        this.brandName = brandName;
    }


    /**
     * Gets the token value for this LoginUserResponse.
     * 
     * @return token
     */
    public java.lang.String getToken() {
        return token;
    }


    /**
     * Sets the token value for this LoginUserResponse.
     * 
     * @param token
     */
    public void setToken(java.lang.String token) {
        this.token = token;
    }


    /**
     * Gets the realBalance value for this LoginUserResponse.
     * 
     * @return realBalance
     */
    public java.math.BigDecimal getRealBalance() {
        return realBalance;
    }


    /**
     * Sets the realBalance value for this LoginUserResponse.
     * 
     * @param realBalance
     */
    public void setRealBalance(java.math.BigDecimal realBalance) {
        this.realBalance = realBalance;
    }


    /**
     * Gets the currencyCode value for this LoginUserResponse.
     * 
     * @return currencyCode
     */
    public java.lang.String getCurrencyCode() {
        return currencyCode;
    }


    /**
     * Sets the currencyCode value for this LoginUserResponse.
     * 
     * @param currencyCode
     */
    public void setCurrencyCode(java.lang.String currencyCode) {
        this.currencyCode = currencyCode;
    }


    /**
     * Gets the playerCreated value for this LoginUserResponse.
     * 
     * @return playerCreated
     */
    public boolean isPlayerCreated() {
        return playerCreated;
    }


    /**
     * Sets the playerCreated value for this LoginUserResponse.
     * 
     * @param playerCreated
     */
    public void setPlayerCreated(boolean playerCreated) {
        this.playerCreated = playerCreated;
    }


    /**
     * Gets the hasBonus value for this LoginUserResponse.
     * 
     * @return hasBonus
     */
    public boolean isHasBonus() {
        return hasBonus;
    }


    /**
     * Sets the hasBonus value for this LoginUserResponse.
     * 
     * @param hasBonus
     */
    public void setHasBonus(boolean hasBonus) {
        this.hasBonus = hasBonus;
    }


    /**
     * Gets the bonusBalance value for this LoginUserResponse.
     * 
     * @return bonusBalance
     */
    public java.math.BigDecimal getBonusBalance() {
        return bonusBalance;
    }


    /**
     * Sets the bonusBalance value for this LoginUserResponse.
     * 
     * @param bonusBalance
     */
    public void setBonusBalance(java.math.BigDecimal bonusBalance) {
        this.bonusBalance = bonusBalance;
    }


    /**
     * Gets the bonusSpins value for this LoginUserResponse.
     * 
     * @return bonusSpins
     */
    public int getBonusSpins() {
        return bonusSpins;
    }


    /**
     * Sets the bonusSpins value for this LoginUserResponse.
     * 
     * @param bonusSpins
     */
    public void setBonusSpins(int bonusSpins) {
        this.bonusSpins = bonusSpins;
    }


    /**
     * Gets the bonusGameId value for this LoginUserResponse.
     * 
     * @return bonusGameId
     */
    public int getBonusGameId() {
        return bonusGameId;
    }


    /**
     * Sets the bonusGameId value for this LoginUserResponse.
     * 
     * @param bonusGameId
     */
    public void setBonusGameId(int bonusGameId) {
        this.bonusGameId = bonusGameId;
    }


    /**
     * Gets the bonusPercentage value for this LoginUserResponse.
     * 
     * @return bonusPercentage
     */
    public java.math.BigDecimal getBonusPercentage() {
        return bonusPercentage;
    }


    /**
     * Sets the bonusPercentage value for this LoginUserResponse.
     * 
     * @param bonusPercentage
     */
    public void setBonusPercentage(java.math.BigDecimal bonusPercentage) {
        this.bonusPercentage = bonusPercentage;
    }


    /**
     * Gets the bonusWagerRemaining value for this LoginUserResponse.
     * 
     * @return bonusWagerRemaining
     */
    public java.math.BigDecimal getBonusWagerRemaining() {
        return bonusWagerRemaining;
    }


    /**
     * Sets the bonusWagerRemaining value for this LoginUserResponse.
     * 
     * @param bonusWagerRemaining
     */
    public void setBonusWagerRemaining(java.math.BigDecimal bonusWagerRemaining) {
        this.bonusWagerRemaining = bonusWagerRemaining;
    }


    /**
     * Gets the message value for this LoginUserResponse.
     * 
     * @return message
     */
    public java.lang.String getMessage() {
        return message;
    }


    /**
     * Sets the message value for this LoginUserResponse.
     * 
     * @param message
     */
    public void setMessage(java.lang.String message) {
        this.message = message;
    }


    /**
     * Gets the currencySymbol value for this LoginUserResponse.
     * 
     * @return currencySymbol
     */
    public java.lang.String getCurrencySymbol() {
        return currencySymbol;
    }


    /**
     * Sets the currencySymbol value for this LoginUserResponse.
     * 
     * @param currencySymbol
     */
    public void setCurrencySymbol(java.lang.String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }


    /**
     * Gets the bonusGameKeyName value for this LoginUserResponse.
     * 
     * @return bonusGameKeyName
     */
    public java.lang.String getBonusGameKeyName() {
        return bonusGameKeyName;
    }


    /**
     * Sets the bonusGameKeyName value for this LoginUserResponse.
     * 
     * @param bonusGameKeyName
     */
    public void setBonusGameKeyName(java.lang.String bonusGameKeyName) {
        this.bonusGameKeyName = bonusGameKeyName;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof LoginUserResponse)) return false;
        LoginUserResponse other = (LoginUserResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.authenticated == other.isAuthenticated() &&
            ((this.playerId==null && other.getPlayerId()==null) || 
             (this.playerId!=null &&
              this.playerId.equals(other.getPlayerId()))) &&
            ((this.brandId==null && other.getBrandId()==null) || 
             (this.brandId!=null &&
              this.brandId.equals(other.getBrandId()))) &&
            ((this.brandName==null && other.getBrandName()==null) || 
             (this.brandName!=null &&
              this.brandName.equals(other.getBrandName()))) &&
            ((this.token==null && other.getToken()==null) || 
             (this.token!=null &&
              this.token.equals(other.getToken()))) &&
            ((this.realBalance==null && other.getRealBalance()==null) || 
             (this.realBalance!=null &&
              this.realBalance.equals(other.getRealBalance()))) &&
            ((this.currencyCode==null && other.getCurrencyCode()==null) || 
             (this.currencyCode!=null &&
              this.currencyCode.equals(other.getCurrencyCode()))) &&
            this.playerCreated == other.isPlayerCreated() &&
            this.hasBonus == other.isHasBonus() &&
            ((this.bonusBalance==null && other.getBonusBalance()==null) || 
             (this.bonusBalance!=null &&
              this.bonusBalance.equals(other.getBonusBalance()))) &&
            this.bonusSpins == other.getBonusSpins() &&
            this.bonusGameId == other.getBonusGameId() &&
            ((this.bonusPercentage==null && other.getBonusPercentage()==null) || 
             (this.bonusPercentage!=null &&
              this.bonusPercentage.equals(other.getBonusPercentage()))) &&
            ((this.bonusWagerRemaining==null && other.getBonusWagerRemaining()==null) || 
             (this.bonusWagerRemaining!=null &&
              this.bonusWagerRemaining.equals(other.getBonusWagerRemaining()))) &&
            ((this.message==null && other.getMessage()==null) || 
             (this.message!=null &&
              this.message.equals(other.getMessage()))) &&
            ((this.currencySymbol==null && other.getCurrencySymbol()==null) || 
             (this.currencySymbol!=null &&
              this.currencySymbol.equals(other.getCurrencySymbol()))) &&
            ((this.bonusGameKeyName==null && other.getBonusGameKeyName()==null) || 
             (this.bonusGameKeyName!=null &&
              this.bonusGameKeyName.equals(other.getBonusGameKeyName())));
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
        _hashCode += (isAuthenticated() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getPlayerId() != null) {
            _hashCode += getPlayerId().hashCode();
        }
        if (getBrandId() != null) {
            _hashCode += getBrandId().hashCode();
        }
        if (getBrandName() != null) {
            _hashCode += getBrandName().hashCode();
        }
        if (getToken() != null) {
            _hashCode += getToken().hashCode();
        }
        if (getRealBalance() != null) {
            _hashCode += getRealBalance().hashCode();
        }
        if (getCurrencyCode() != null) {
            _hashCode += getCurrencyCode().hashCode();
        }
        _hashCode += (isPlayerCreated() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isHasBonus() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getBonusBalance() != null) {
            _hashCode += getBonusBalance().hashCode();
        }
        _hashCode += getBonusSpins();
        _hashCode += getBonusGameId();
        if (getBonusPercentage() != null) {
            _hashCode += getBonusPercentage().hashCode();
        }
        if (getBonusWagerRemaining() != null) {
            _hashCode += getBonusWagerRemaining().hashCode();
        }
        if (getMessage() != null) {
            _hashCode += getMessage().hashCode();
        }
        if (getCurrencySymbol() != null) {
            _hashCode += getCurrencySymbol().hashCode();
        }
        if (getBonusGameKeyName() != null) {
            _hashCode += getBonusGameKeyName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(LoginUserResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "LoginUserResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("authenticated");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "Authenticated"));
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
        elemField.setFieldName("token");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "Token"));
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
        elemField.setFieldName("playerCreated");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "PlayerCreated"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("currencySymbol");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "CurrencySymbol"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bonusGameKeyName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "BonusGameKeyName"));
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
