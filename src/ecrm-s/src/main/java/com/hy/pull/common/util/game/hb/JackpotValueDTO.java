/**
 * JackpotValueDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hy.pull.common.util.game.hb;

public class JackpotValueDTO  implements java.io.Serializable {
    private java.lang.String currencyCode;

    private java.lang.String currencySymbol;

    private int currencyExponent;

    private java.math.BigDecimal convertedValue;

    private java.math.BigDecimal exRate;

    private java.math.BigDecimal increment;

    public JackpotValueDTO() {
    }

    public JackpotValueDTO(
           java.lang.String currencyCode,
           java.lang.String currencySymbol,
           int currencyExponent,
           java.math.BigDecimal convertedValue,
           java.math.BigDecimal exRate,
           java.math.BigDecimal increment) {
           this.currencyCode = currencyCode;
           this.currencySymbol = currencySymbol;
           this.currencyExponent = currencyExponent;
           this.convertedValue = convertedValue;
           this.exRate = exRate;
           this.increment = increment;
    }


    /**
     * Gets the currencyCode value for this JackpotValueDTO.
     * 
     * @return currencyCode
     */
    public java.lang.String getCurrencyCode() {
        return currencyCode;
    }


    /**
     * Sets the currencyCode value for this JackpotValueDTO.
     * 
     * @param currencyCode
     */
    public void setCurrencyCode(java.lang.String currencyCode) {
        this.currencyCode = currencyCode;
    }


    /**
     * Gets the currencySymbol value for this JackpotValueDTO.
     * 
     * @return currencySymbol
     */
    public java.lang.String getCurrencySymbol() {
        return currencySymbol;
    }


    /**
     * Sets the currencySymbol value for this JackpotValueDTO.
     * 
     * @param currencySymbol
     */
    public void setCurrencySymbol(java.lang.String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }


    /**
     * Gets the currencyExponent value for this JackpotValueDTO.
     * 
     * @return currencyExponent
     */
    public int getCurrencyExponent() {
        return currencyExponent;
    }


    /**
     * Sets the currencyExponent value for this JackpotValueDTO.
     * 
     * @param currencyExponent
     */
    public void setCurrencyExponent(int currencyExponent) {
        this.currencyExponent = currencyExponent;
    }


    /**
     * Gets the convertedValue value for this JackpotValueDTO.
     * 
     * @return convertedValue
     */
    public java.math.BigDecimal getConvertedValue() {
        return convertedValue;
    }


    /**
     * Sets the convertedValue value for this JackpotValueDTO.
     * 
     * @param convertedValue
     */
    public void setConvertedValue(java.math.BigDecimal convertedValue) {
        this.convertedValue = convertedValue;
    }


    /**
     * Gets the exRate value for this JackpotValueDTO.
     * 
     * @return exRate
     */
    public java.math.BigDecimal getExRate() {
        return exRate;
    }


    /**
     * Sets the exRate value for this JackpotValueDTO.
     * 
     * @param exRate
     */
    public void setExRate(java.math.BigDecimal exRate) {
        this.exRate = exRate;
    }


    /**
     * Gets the increment value for this JackpotValueDTO.
     * 
     * @return increment
     */
    public java.math.BigDecimal getIncrement() {
        return increment;
    }


    /**
     * Sets the increment value for this JackpotValueDTO.
     * 
     * @param increment
     */
    public void setIncrement(java.math.BigDecimal increment) {
        this.increment = increment;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof JackpotValueDTO)) return false;
        JackpotValueDTO other = (JackpotValueDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.currencyCode==null && other.getCurrencyCode()==null) || 
             (this.currencyCode!=null &&
              this.currencyCode.equals(other.getCurrencyCode()))) &&
            ((this.currencySymbol==null && other.getCurrencySymbol()==null) || 
             (this.currencySymbol!=null &&
              this.currencySymbol.equals(other.getCurrencySymbol()))) &&
            this.currencyExponent == other.getCurrencyExponent() &&
            ((this.convertedValue==null && other.getConvertedValue()==null) || 
             (this.convertedValue!=null &&
              this.convertedValue.equals(other.getConvertedValue()))) &&
            ((this.exRate==null && other.getExRate()==null) || 
             (this.exRate!=null &&
              this.exRate.equals(other.getExRate()))) &&
            ((this.increment==null && other.getIncrement()==null) || 
             (this.increment!=null &&
              this.increment.equals(other.getIncrement())));
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
        if (getCurrencyCode() != null) {
            _hashCode += getCurrencyCode().hashCode();
        }
        if (getCurrencySymbol() != null) {
            _hashCode += getCurrencySymbol().hashCode();
        }
        _hashCode += getCurrencyExponent();
        if (getConvertedValue() != null) {
            _hashCode += getConvertedValue().hashCode();
        }
        if (getExRate() != null) {
            _hashCode += getExRate().hashCode();
        }
        if (getIncrement() != null) {
            _hashCode += getIncrement().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(JackpotValueDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "JackpotValueDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
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
        elemField.setFieldName("convertedValue");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "ConvertedValue"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("exRate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "ExRate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("increment");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "Increment"));
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
