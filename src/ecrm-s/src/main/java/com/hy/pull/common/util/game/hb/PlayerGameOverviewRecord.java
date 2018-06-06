/**
 * PlayerGameOverviewRecord.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hy.pull.common.util.game.hb;

public class PlayerGameOverviewRecord  implements java.io.Serializable {
    private java.lang.String brandGameId;

    private java.lang.String gameKeyName;

    private java.lang.String gameName;

    private java.lang.String gameTypeName;

    private int totalGames;

    private java.math.BigDecimal totalStake;

    private java.math.BigDecimal totalPayout;

    public PlayerGameOverviewRecord() {
    }

    public PlayerGameOverviewRecord(
           java.lang.String brandGameId,
           java.lang.String gameKeyName,
           java.lang.String gameName,
           java.lang.String gameTypeName,
           int totalGames,
           java.math.BigDecimal totalStake,
           java.math.BigDecimal totalPayout) {
           this.brandGameId = brandGameId;
           this.gameKeyName = gameKeyName;
           this.gameName = gameName;
           this.gameTypeName = gameTypeName;
           this.totalGames = totalGames;
           this.totalStake = totalStake;
           this.totalPayout = totalPayout;
    }


    /**
     * Gets the brandGameId value for this PlayerGameOverviewRecord.
     * 
     * @return brandGameId
     */
    public java.lang.String getBrandGameId() {
        return brandGameId;
    }


    /**
     * Sets the brandGameId value for this PlayerGameOverviewRecord.
     * 
     * @param brandGameId
     */
    public void setBrandGameId(java.lang.String brandGameId) {
        this.brandGameId = brandGameId;
    }


    /**
     * Gets the gameKeyName value for this PlayerGameOverviewRecord.
     * 
     * @return gameKeyName
     */
    public java.lang.String getGameKeyName() {
        return gameKeyName;
    }


    /**
     * Sets the gameKeyName value for this PlayerGameOverviewRecord.
     * 
     * @param gameKeyName
     */
    public void setGameKeyName(java.lang.String gameKeyName) {
        this.gameKeyName = gameKeyName;
    }


    /**
     * Gets the gameName value for this PlayerGameOverviewRecord.
     * 
     * @return gameName
     */
    public java.lang.String getGameName() {
        return gameName;
    }


    /**
     * Sets the gameName value for this PlayerGameOverviewRecord.
     * 
     * @param gameName
     */
    public void setGameName(java.lang.String gameName) {
        this.gameName = gameName;
    }


    /**
     * Gets the gameTypeName value for this PlayerGameOverviewRecord.
     * 
     * @return gameTypeName
     */
    public java.lang.String getGameTypeName() {
        return gameTypeName;
    }


    /**
     * Sets the gameTypeName value for this PlayerGameOverviewRecord.
     * 
     * @param gameTypeName
     */
    public void setGameTypeName(java.lang.String gameTypeName) {
        this.gameTypeName = gameTypeName;
    }


    /**
     * Gets the totalGames value for this PlayerGameOverviewRecord.
     * 
     * @return totalGames
     */
    public int getTotalGames() {
        return totalGames;
    }


    /**
     * Sets the totalGames value for this PlayerGameOverviewRecord.
     * 
     * @param totalGames
     */
    public void setTotalGames(int totalGames) {
        this.totalGames = totalGames;
    }


    /**
     * Gets the totalStake value for this PlayerGameOverviewRecord.
     * 
     * @return totalStake
     */
    public java.math.BigDecimal getTotalStake() {
        return totalStake;
    }


    /**
     * Sets the totalStake value for this PlayerGameOverviewRecord.
     * 
     * @param totalStake
     */
    public void setTotalStake(java.math.BigDecimal totalStake) {
        this.totalStake = totalStake;
    }


    /**
     * Gets the totalPayout value for this PlayerGameOverviewRecord.
     * 
     * @return totalPayout
     */
    public java.math.BigDecimal getTotalPayout() {
        return totalPayout;
    }


    /**
     * Sets the totalPayout value for this PlayerGameOverviewRecord.
     * 
     * @param totalPayout
     */
    public void setTotalPayout(java.math.BigDecimal totalPayout) {
        this.totalPayout = totalPayout;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PlayerGameOverviewRecord)) return false;
        PlayerGameOverviewRecord other = (PlayerGameOverviewRecord) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.brandGameId==null && other.getBrandGameId()==null) || 
             (this.brandGameId!=null &&
              this.brandGameId.equals(other.getBrandGameId()))) &&
            ((this.gameKeyName==null && other.getGameKeyName()==null) || 
             (this.gameKeyName!=null &&
              this.gameKeyName.equals(other.getGameKeyName()))) &&
            ((this.gameName==null && other.getGameName()==null) || 
             (this.gameName!=null &&
              this.gameName.equals(other.getGameName()))) &&
            ((this.gameTypeName==null && other.getGameTypeName()==null) || 
             (this.gameTypeName!=null &&
              this.gameTypeName.equals(other.getGameTypeName()))) &&
            this.totalGames == other.getTotalGames() &&
            ((this.totalStake==null && other.getTotalStake()==null) || 
             (this.totalStake!=null &&
              this.totalStake.equals(other.getTotalStake()))) &&
            ((this.totalPayout==null && other.getTotalPayout()==null) || 
             (this.totalPayout!=null &&
              this.totalPayout.equals(other.getTotalPayout())));
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
        if (getBrandGameId() != null) {
            _hashCode += getBrandGameId().hashCode();
        }
        if (getGameKeyName() != null) {
            _hashCode += getGameKeyName().hashCode();
        }
        if (getGameName() != null) {
            _hashCode += getGameName().hashCode();
        }
        if (getGameTypeName() != null) {
            _hashCode += getGameTypeName().hashCode();
        }
        _hashCode += getTotalGames();
        if (getTotalStake() != null) {
            _hashCode += getTotalStake().hashCode();
        }
        if (getTotalPayout() != null) {
            _hashCode += getTotalPayout().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PlayerGameOverviewRecord.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "PlayerGameOverviewRecord"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("brandGameId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "BrandGameId"));
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
        elemField.setFieldName("gameName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("gameTypeName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameTypeName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalGames");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "TotalGames"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalStake");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "TotalStake"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalPayout");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "TotalPayout"));
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
