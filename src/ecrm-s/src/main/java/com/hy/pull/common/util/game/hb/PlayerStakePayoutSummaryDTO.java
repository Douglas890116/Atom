/**
 * PlayerStakePayoutSummaryDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hy.pull.common.util.game.hb;

public class PlayerStakePayoutSummaryDTO  implements java.io.Serializable {
    private java.math.BigDecimal stake;

    private java.math.BigDecimal payout;

    private java.math.BigDecimal jackpotWin;

    private java.math.BigDecimal jackpotContribution;

    public PlayerStakePayoutSummaryDTO() {
    }

    public PlayerStakePayoutSummaryDTO(
           java.math.BigDecimal stake,
           java.math.BigDecimal payout,
           java.math.BigDecimal jackpotWin,
           java.math.BigDecimal jackpotContribution) {
           this.stake = stake;
           this.payout = payout;
           this.jackpotWin = jackpotWin;
           this.jackpotContribution = jackpotContribution;
    }


    /**
     * Gets the stake value for this PlayerStakePayoutSummaryDTO.
     * 
     * @return stake
     */
    public java.math.BigDecimal getStake() {
        return stake;
    }


    /**
     * Sets the stake value for this PlayerStakePayoutSummaryDTO.
     * 
     * @param stake
     */
    public void setStake(java.math.BigDecimal stake) {
        this.stake = stake;
    }


    /**
     * Gets the payout value for this PlayerStakePayoutSummaryDTO.
     * 
     * @return payout
     */
    public java.math.BigDecimal getPayout() {
        return payout;
    }


    /**
     * Sets the payout value for this PlayerStakePayoutSummaryDTO.
     * 
     * @param payout
     */
    public void setPayout(java.math.BigDecimal payout) {
        this.payout = payout;
    }


    /**
     * Gets the jackpotWin value for this PlayerStakePayoutSummaryDTO.
     * 
     * @return jackpotWin
     */
    public java.math.BigDecimal getJackpotWin() {
        return jackpotWin;
    }


    /**
     * Sets the jackpotWin value for this PlayerStakePayoutSummaryDTO.
     * 
     * @param jackpotWin
     */
    public void setJackpotWin(java.math.BigDecimal jackpotWin) {
        this.jackpotWin = jackpotWin;
    }


    /**
     * Gets the jackpotContribution value for this PlayerStakePayoutSummaryDTO.
     * 
     * @return jackpotContribution
     */
    public java.math.BigDecimal getJackpotContribution() {
        return jackpotContribution;
    }


    /**
     * Sets the jackpotContribution value for this PlayerStakePayoutSummaryDTO.
     * 
     * @param jackpotContribution
     */
    public void setJackpotContribution(java.math.BigDecimal jackpotContribution) {
        this.jackpotContribution = jackpotContribution;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PlayerStakePayoutSummaryDTO)) return false;
        PlayerStakePayoutSummaryDTO other = (PlayerStakePayoutSummaryDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
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
              this.jackpotContribution.equals(other.getJackpotContribution())));
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
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PlayerStakePayoutSummaryDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "PlayerStakePayoutSummaryDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
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
