/**
 * ComplexDepositCouponInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hy.pull.common.util.game.hb;

public class ComplexDepositCouponInfo  implements java.io.Serializable {
    private java.math.BigDecimal dep;

    private short FS;

    private java.math.BigDecimal prc;

    public ComplexDepositCouponInfo() {
    }

    public ComplexDepositCouponInfo(
           java.math.BigDecimal dep,
           short FS,
           java.math.BigDecimal prc) {
           this.dep = dep;
           this.FS = FS;
           this.prc = prc;
    }


    /**
     * Gets the dep value for this ComplexDepositCouponInfo.
     * 
     * @return dep
     */
    public java.math.BigDecimal getDep() {
        return dep;
    }


    /**
     * Sets the dep value for this ComplexDepositCouponInfo.
     * 
     * @param dep
     */
    public void setDep(java.math.BigDecimal dep) {
        this.dep = dep;
    }


    /**
     * Gets the FS value for this ComplexDepositCouponInfo.
     * 
     * @return FS
     */
    public short getFS() {
        return FS;
    }


    /**
     * Sets the FS value for this ComplexDepositCouponInfo.
     * 
     * @param FS
     */
    public void setFS(short FS) {
        this.FS = FS;
    }


    /**
     * Gets the prc value for this ComplexDepositCouponInfo.
     * 
     * @return prc
     */
    public java.math.BigDecimal getPrc() {
        return prc;
    }


    /**
     * Sets the prc value for this ComplexDepositCouponInfo.
     * 
     * @param prc
     */
    public void setPrc(java.math.BigDecimal prc) {
        this.prc = prc;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ComplexDepositCouponInfo)) return false;
        ComplexDepositCouponInfo other = (ComplexDepositCouponInfo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.dep==null && other.getDep()==null) || 
             (this.dep!=null &&
              this.dep.equals(other.getDep()))) &&
            this.FS == other.getFS() &&
            ((this.prc==null && other.getPrc()==null) || 
             (this.prc!=null &&
              this.prc.equals(other.getPrc())));
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
        if (getDep() != null) {
            _hashCode += getDep().hashCode();
        }
        _hashCode += getFS();
        if (getPrc() != null) {
            _hashCode += getPrc().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ComplexDepositCouponInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "ComplexDepositCouponInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dep");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "Dep"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("FS");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "FS"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prc");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "Prc"));
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
