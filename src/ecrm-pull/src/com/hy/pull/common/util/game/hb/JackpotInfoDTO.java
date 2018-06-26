/**
 * JackpotInfoDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hy.pull.common.util.game.hb;

public class JackpotInfoDTO  implements java.io.Serializable {
    private java.lang.String brandId;

    private java.lang.String jackpotId;

    private java.lang.String jackpotGroupId;

    private java.lang.String jackpotTypeName;

    private int jackpotTypeId;

    private int currencyId;

    private java.lang.String currencyCode;

    private java.lang.String currencySymbol;

    private int currencyExponent;

    private java.math.BigDecimal currentValue;

    private java.math.BigDecimal increment;

    private java.math.BigDecimal betTotal;

    private java.math.BigDecimal clawTotal;

    private java.math.BigDecimal startingValue;

    private java.math.BigDecimal consumePerc;

    private java.math.BigDecimal randFreqPerc;

    private java.math.BigDecimal markupPerc;

    private boolean blockUntilFunded;

    private com.hy.pull.common.util.game.hb.JackpotValueDTO[] conversions;

    private java.lang.String brandsGroupId;

    private java.math.BigDecimal coinSizeJackpot;

    public JackpotInfoDTO() {
    }

    public JackpotInfoDTO(
           java.lang.String brandId,
           java.lang.String jackpotId,
           java.lang.String jackpotGroupId,
           java.lang.String jackpotTypeName,
           int jackpotTypeId,
           int currencyId,
           java.lang.String currencyCode,
           java.lang.String currencySymbol,
           int currencyExponent,
           java.math.BigDecimal currentValue,
           java.math.BigDecimal increment,
           java.math.BigDecimal betTotal,
           java.math.BigDecimal clawTotal,
           java.math.BigDecimal startingValue,
           java.math.BigDecimal consumePerc,
           java.math.BigDecimal randFreqPerc,
           java.math.BigDecimal markupPerc,
           boolean blockUntilFunded,
           com.hy.pull.common.util.game.hb.JackpotValueDTO[] conversions,
           java.lang.String brandsGroupId,
           java.math.BigDecimal coinSizeJackpot) {
           this.brandId = brandId;
           this.jackpotId = jackpotId;
           this.jackpotGroupId = jackpotGroupId;
           this.jackpotTypeName = jackpotTypeName;
           this.jackpotTypeId = jackpotTypeId;
           this.currencyId = currencyId;
           this.currencyCode = currencyCode;
           this.currencySymbol = currencySymbol;
           this.currencyExponent = currencyExponent;
           this.currentValue = currentValue;
           this.increment = increment;
           this.betTotal = betTotal;
           this.clawTotal = clawTotal;
           this.startingValue = startingValue;
           this.consumePerc = consumePerc;
           this.randFreqPerc = randFreqPerc;
           this.markupPerc = markupPerc;
           this.blockUntilFunded = blockUntilFunded;
           this.conversions = conversions;
           this.brandsGroupId = brandsGroupId;
           this.coinSizeJackpot = coinSizeJackpot;
    }


    /**
     * Gets the brandId value for this JackpotInfoDTO.
     * 
     * @return brandId
     */
    public java.lang.String getBrandId() {
        return brandId;
    }


    /**
     * Sets the brandId value for this JackpotInfoDTO.
     * 
     * @param brandId
     */
    public void setBrandId(java.lang.String brandId) {
        this.brandId = brandId;
    }


    /**
     * Gets the jackpotId value for this JackpotInfoDTO.
     * 
     * @return jackpotId
     */
    public java.lang.String getJackpotId() {
        return jackpotId;
    }


    /**
     * Sets the jackpotId value for this JackpotInfoDTO.
     * 
     * @param jackpotId
     */
    public void setJackpotId(java.lang.String jackpotId) {
        this.jackpotId = jackpotId;
    }


    /**
     * Gets the jackpotGroupId value for this JackpotInfoDTO.
     * 
     * @return jackpotGroupId
     */
    public java.lang.String getJackpotGroupId() {
        return jackpotGroupId;
    }


    /**
     * Sets the jackpotGroupId value for this JackpotInfoDTO.
     * 
     * @param jackpotGroupId
     */
    public void setJackpotGroupId(java.lang.String jackpotGroupId) {
        this.jackpotGroupId = jackpotGroupId;
    }


    /**
     * Gets the jackpotTypeName value for this JackpotInfoDTO.
     * 
     * @return jackpotTypeName
     */
    public java.lang.String getJackpotTypeName() {
        return jackpotTypeName;
    }


    /**
     * Sets the jackpotTypeName value for this JackpotInfoDTO.
     * 
     * @param jackpotTypeName
     */
    public void setJackpotTypeName(java.lang.String jackpotTypeName) {
        this.jackpotTypeName = jackpotTypeName;
    }


    /**
     * Gets the jackpotTypeId value for this JackpotInfoDTO.
     * 
     * @return jackpotTypeId
     */
    public int getJackpotTypeId() {
        return jackpotTypeId;
    }


    /**
     * Sets the jackpotTypeId value for this JackpotInfoDTO.
     * 
     * @param jackpotTypeId
     */
    public void setJackpotTypeId(int jackpotTypeId) {
        this.jackpotTypeId = jackpotTypeId;
    }


    /**
     * Gets the currencyId value for this JackpotInfoDTO.
     * 
     * @return currencyId
     */
    public int getCurrencyId() {
        return currencyId;
    }


    /**
     * Sets the currencyId value for this JackpotInfoDTO.
     * 
     * @param currencyId
     */
    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }


    /**
     * Gets the currencyCode value for this JackpotInfoDTO.
     * 
     * @return currencyCode
     */
    public java.lang.String getCurrencyCode() {
        return currencyCode;
    }


    /**
     * Sets the currencyCode value for this JackpotInfoDTO.
     * 
     * @param currencyCode
     */
    public void setCurrencyCode(java.lang.String currencyCode) {
        this.currencyCode = currencyCode;
    }


    /**
     * Gets the currencySymbol value for this JackpotInfoDTO.
     * 
     * @return currencySymbol
     */
    public java.lang.String getCurrencySymbol() {
        return currencySymbol;
    }


    /**
     * Sets the currencySymbol value for this JackpotInfoDTO.
     * 
     * @param currencySymbol
     */
    public void setCurrencySymbol(java.lang.String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }


    /**
     * Gets the currencyExponent value for this JackpotInfoDTO.
     * 
     * @return currencyExponent
     */
    public int getCurrencyExponent() {
        return currencyExponent;
    }


    /**
     * Sets the currencyExponent value for this JackpotInfoDTO.
     * 
     * @param currencyExponent
     */
    public void setCurrencyExponent(int currencyExponent) {
        this.currencyExponent = currencyExponent;
    }


    /**
     * Gets the currentValue value for this JackpotInfoDTO.
     * 
     * @return currentValue
     */
    public java.math.BigDecimal getCurrentValue() {
        return currentValue;
    }


    /**
     * Sets the currentValue value for this JackpotInfoDTO.
     * 
     * @param currentValue
     */
    public void setCurrentValue(java.math.BigDecimal currentValue) {
        this.currentValue = currentValue;
    }


    /**
     * Gets the increment value for this JackpotInfoDTO.
     * 
     * @return increment
     */
    public java.math.BigDecimal getIncrement() {
        return increment;
    }


    /**
     * Sets the increment value for this JackpotInfoDTO.
     * 
     * @param increment
     */
    public void setIncrement(java.math.BigDecimal increment) {
        this.increment = increment;
    }


    /**
     * Gets the betTotal value for this JackpotInfoDTO.
     * 
     * @return betTotal
     */
    public java.math.BigDecimal getBetTotal() {
        return betTotal;
    }


    /**
     * Sets the betTotal value for this JackpotInfoDTO.
     * 
     * @param betTotal
     */
    public void setBetTotal(java.math.BigDecimal betTotal) {
        this.betTotal = betTotal;
    }


    /**
     * Gets the clawTotal value for this JackpotInfoDTO.
     * 
     * @return clawTotal
     */
    public java.math.BigDecimal getClawTotal() {
        return clawTotal;
    }


    /**
     * Sets the clawTotal value for this JackpotInfoDTO.
     * 
     * @param clawTotal
     */
    public void setClawTotal(java.math.BigDecimal clawTotal) {
        this.clawTotal = clawTotal;
    }


    /**
     * Gets the startingValue value for this JackpotInfoDTO.
     * 
     * @return startingValue
     */
    public java.math.BigDecimal getStartingValue() {
        return startingValue;
    }


    /**
     * Sets the startingValue value for this JackpotInfoDTO.
     * 
     * @param startingValue
     */
    public void setStartingValue(java.math.BigDecimal startingValue) {
        this.startingValue = startingValue;
    }


    /**
     * Gets the consumePerc value for this JackpotInfoDTO.
     * 
     * @return consumePerc
     */
    public java.math.BigDecimal getConsumePerc() {
        return consumePerc;
    }


    /**
     * Sets the consumePerc value for this JackpotInfoDTO.
     * 
     * @param consumePerc
     */
    public void setConsumePerc(java.math.BigDecimal consumePerc) {
        this.consumePerc = consumePerc;
    }


    /**
     * Gets the randFreqPerc value for this JackpotInfoDTO.
     * 
     * @return randFreqPerc
     */
    public java.math.BigDecimal getRandFreqPerc() {
        return randFreqPerc;
    }


    /**
     * Sets the randFreqPerc value for this JackpotInfoDTO.
     * 
     * @param randFreqPerc
     */
    public void setRandFreqPerc(java.math.BigDecimal randFreqPerc) {
        this.randFreqPerc = randFreqPerc;
    }


    /**
     * Gets the markupPerc value for this JackpotInfoDTO.
     * 
     * @return markupPerc
     */
    public java.math.BigDecimal getMarkupPerc() {
        return markupPerc;
    }


    /**
     * Sets the markupPerc value for this JackpotInfoDTO.
     * 
     * @param markupPerc
     */
    public void setMarkupPerc(java.math.BigDecimal markupPerc) {
        this.markupPerc = markupPerc;
    }


    /**
     * Gets the blockUntilFunded value for this JackpotInfoDTO.
     * 
     * @return blockUntilFunded
     */
    public boolean isBlockUntilFunded() {
        return blockUntilFunded;
    }


    /**
     * Sets the blockUntilFunded value for this JackpotInfoDTO.
     * 
     * @param blockUntilFunded
     */
    public void setBlockUntilFunded(boolean blockUntilFunded) {
        this.blockUntilFunded = blockUntilFunded;
    }


    /**
     * Gets the conversions value for this JackpotInfoDTO.
     * 
     * @return conversions
     */
    public com.hy.pull.common.util.game.hb.JackpotValueDTO[] getConversions() {
        return conversions;
    }


    /**
     * Sets the conversions value for this JackpotInfoDTO.
     * 
     * @param conversions
     */
    public void setConversions(com.hy.pull.common.util.game.hb.JackpotValueDTO[] conversions) {
        this.conversions = conversions;
    }


    /**
     * Gets the brandsGroupId value for this JackpotInfoDTO.
     * 
     * @return brandsGroupId
     */
    public java.lang.String getBrandsGroupId() {
        return brandsGroupId;
    }


    /**
     * Sets the brandsGroupId value for this JackpotInfoDTO.
     * 
     * @param brandsGroupId
     */
    public void setBrandsGroupId(java.lang.String brandsGroupId) {
        this.brandsGroupId = brandsGroupId;
    }


    /**
     * Gets the coinSizeJackpot value for this JackpotInfoDTO.
     * 
     * @return coinSizeJackpot
     */
    public java.math.BigDecimal getCoinSizeJackpot() {
        return coinSizeJackpot;
    }


    /**
     * Sets the coinSizeJackpot value for this JackpotInfoDTO.
     * 
     * @param coinSizeJackpot
     */
    public void setCoinSizeJackpot(java.math.BigDecimal coinSizeJackpot) {
        this.coinSizeJackpot = coinSizeJackpot;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof JackpotInfoDTO)) return false;
        JackpotInfoDTO other = (JackpotInfoDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.brandId==null && other.getBrandId()==null) || 
             (this.brandId!=null &&
              this.brandId.equals(other.getBrandId()))) &&
            ((this.jackpotId==null && other.getJackpotId()==null) || 
             (this.jackpotId!=null &&
              this.jackpotId.equals(other.getJackpotId()))) &&
            ((this.jackpotGroupId==null && other.getJackpotGroupId()==null) || 
             (this.jackpotGroupId!=null &&
              this.jackpotGroupId.equals(other.getJackpotGroupId()))) &&
            ((this.jackpotTypeName==null && other.getJackpotTypeName()==null) || 
             (this.jackpotTypeName!=null &&
              this.jackpotTypeName.equals(other.getJackpotTypeName()))) &&
            this.jackpotTypeId == other.getJackpotTypeId() &&
            this.currencyId == other.getCurrencyId() &&
            ((this.currencyCode==null && other.getCurrencyCode()==null) || 
             (this.currencyCode!=null &&
              this.currencyCode.equals(other.getCurrencyCode()))) &&
            ((this.currencySymbol==null && other.getCurrencySymbol()==null) || 
             (this.currencySymbol!=null &&
              this.currencySymbol.equals(other.getCurrencySymbol()))) &&
            this.currencyExponent == other.getCurrencyExponent() &&
            ((this.currentValue==null && other.getCurrentValue()==null) || 
             (this.currentValue!=null &&
              this.currentValue.equals(other.getCurrentValue()))) &&
            ((this.increment==null && other.getIncrement()==null) || 
             (this.increment!=null &&
              this.increment.equals(other.getIncrement()))) &&
            ((this.betTotal==null && other.getBetTotal()==null) || 
             (this.betTotal!=null &&
              this.betTotal.equals(other.getBetTotal()))) &&
            ((this.clawTotal==null && other.getClawTotal()==null) || 
             (this.clawTotal!=null &&
              this.clawTotal.equals(other.getClawTotal()))) &&
            ((this.startingValue==null && other.getStartingValue()==null) || 
             (this.startingValue!=null &&
              this.startingValue.equals(other.getStartingValue()))) &&
            ((this.consumePerc==null && other.getConsumePerc()==null) || 
             (this.consumePerc!=null &&
              this.consumePerc.equals(other.getConsumePerc()))) &&
            ((this.randFreqPerc==null && other.getRandFreqPerc()==null) || 
             (this.randFreqPerc!=null &&
              this.randFreqPerc.equals(other.getRandFreqPerc()))) &&
            ((this.markupPerc==null && other.getMarkupPerc()==null) || 
             (this.markupPerc!=null &&
              this.markupPerc.equals(other.getMarkupPerc()))) &&
            this.blockUntilFunded == other.isBlockUntilFunded() &&
            ((this.conversions==null && other.getConversions()==null) || 
             (this.conversions!=null &&
              java.util.Arrays.equals(this.conversions, other.getConversions()))) &&
            ((this.brandsGroupId==null && other.getBrandsGroupId()==null) || 
             (this.brandsGroupId!=null &&
              this.brandsGroupId.equals(other.getBrandsGroupId()))) &&
            ((this.coinSizeJackpot==null && other.getCoinSizeJackpot()==null) || 
             (this.coinSizeJackpot!=null &&
              this.coinSizeJackpot.equals(other.getCoinSizeJackpot())));
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
        if (getBrandId() != null) {
            _hashCode += getBrandId().hashCode();
        }
        if (getJackpotId() != null) {
            _hashCode += getJackpotId().hashCode();
        }
        if (getJackpotGroupId() != null) {
            _hashCode += getJackpotGroupId().hashCode();
        }
        if (getJackpotTypeName() != null) {
            _hashCode += getJackpotTypeName().hashCode();
        }
        _hashCode += getJackpotTypeId();
        _hashCode += getCurrencyId();
        if (getCurrencyCode() != null) {
            _hashCode += getCurrencyCode().hashCode();
        }
        if (getCurrencySymbol() != null) {
            _hashCode += getCurrencySymbol().hashCode();
        }
        _hashCode += getCurrencyExponent();
        if (getCurrentValue() != null) {
            _hashCode += getCurrentValue().hashCode();
        }
        if (getIncrement() != null) {
            _hashCode += getIncrement().hashCode();
        }
        if (getBetTotal() != null) {
            _hashCode += getBetTotal().hashCode();
        }
        if (getClawTotal() != null) {
            _hashCode += getClawTotal().hashCode();
        }
        if (getStartingValue() != null) {
            _hashCode += getStartingValue().hashCode();
        }
        if (getConsumePerc() != null) {
            _hashCode += getConsumePerc().hashCode();
        }
        if (getRandFreqPerc() != null) {
            _hashCode += getRandFreqPerc().hashCode();
        }
        if (getMarkupPerc() != null) {
            _hashCode += getMarkupPerc().hashCode();
        }
        _hashCode += (isBlockUntilFunded() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getConversions() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getConversions());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getConversions(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getBrandsGroupId() != null) {
            _hashCode += getBrandsGroupId().hashCode();
        }
        if (getCoinSizeJackpot() != null) {
            _hashCode += getCoinSizeJackpot().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(JackpotInfoDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "JackpotInfoDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("brandId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "BrandId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("jackpotId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "JackpotId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("jackpotGroupId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "JackpotGroupId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("jackpotTypeName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "JackpotTypeName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("jackpotTypeId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "JackpotTypeId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("currencyId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "CurrencyId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
        elemField.setFieldName("currencyExponent");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "CurrencyExponent"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("currentValue");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "CurrentValue"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("increment");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "Increment"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("betTotal");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "BetTotal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clawTotal");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "ClawTotal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("startingValue");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "StartingValue"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("consumePerc");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "ConsumePerc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("randFreqPerc");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "RandFreqPerc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("markupPerc");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "MarkupPerc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("blockUntilFunded");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "BlockUntilFunded"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("conversions");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "Conversions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "JackpotValueDTO"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "JackpotValueDTO"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("brandsGroupId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "BrandsGroupId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("coinSizeJackpot");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "CoinSizeJackpot"));
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
