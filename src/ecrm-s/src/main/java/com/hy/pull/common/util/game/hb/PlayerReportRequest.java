/**
 * PlayerReportRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hy.pull.common.util.game.hb;

public class PlayerReportRequest  extends com.hy.pull.common.util.game.hb.BaseRequest  implements java.io.Serializable {
    private java.lang.String username;

    private java.lang.String dtStartUTC;

    private java.lang.String dtEndUTC;

    public PlayerReportRequest() {
    }

    public PlayerReportRequest(
           java.lang.String brandId,
           java.lang.String APIKey,
           java.lang.String playerHostAddress,
           java.lang.String CFCId,
           java.lang.String locale,
           java.lang.String username,
           java.lang.String dtStartUTC,
           java.lang.String dtEndUTC) {
        super(
            brandId,
            APIKey,
            playerHostAddress,
            CFCId,
            locale);
        this.username = username;
        this.dtStartUTC = dtStartUTC;
        this.dtEndUTC = dtEndUTC;
    }


    /**
     * Gets the username value for this PlayerReportRequest.
     * 
     * @return username
     */
    public java.lang.String getUsername() {
        return username;
    }


    /**
     * Sets the username value for this PlayerReportRequest.
     * 
     * @param username
     */
    public void setUsername(java.lang.String username) {
        this.username = username;
    }


    /**
     * Gets the dtStartUTC value for this PlayerReportRequest.
     * 
     * @return dtStartUTC
     */
    public java.lang.String getDtStartUTC() {
        return dtStartUTC;
    }


    /**
     * Sets the dtStartUTC value for this PlayerReportRequest.
     * 
     * @param dtStartUTC
     */
    public void setDtStartUTC(java.lang.String dtStartUTC) {
        this.dtStartUTC = dtStartUTC;
    }


    /**
     * Gets the dtEndUTC value for this PlayerReportRequest.
     * 
     * @return dtEndUTC
     */
    public java.lang.String getDtEndUTC() {
        return dtEndUTC;
    }


    /**
     * Sets the dtEndUTC value for this PlayerReportRequest.
     * 
     * @param dtEndUTC
     */
    public void setDtEndUTC(java.lang.String dtEndUTC) {
        this.dtEndUTC = dtEndUTC;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PlayerReportRequest)) return false;
        PlayerReportRequest other = (PlayerReportRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.username==null && other.getUsername()==null) || 
             (this.username!=null &&
              this.username.equals(other.getUsername()))) &&
            ((this.dtStartUTC==null && other.getDtStartUTC()==null) || 
             (this.dtStartUTC!=null &&
              this.dtStartUTC.equals(other.getDtStartUTC()))) &&
            ((this.dtEndUTC==null && other.getDtEndUTC()==null) || 
             (this.dtEndUTC!=null &&
              this.dtEndUTC.equals(other.getDtEndUTC())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getUsername() != null) {
            _hashCode += getUsername().hashCode();
        }
        if (getDtStartUTC() != null) {
            _hashCode += getDtStartUTC().hashCode();
        }
        if (getDtEndUTC() != null) {
            _hashCode += getDtEndUTC().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PlayerReportRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "PlayerReportRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("username");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "Username"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dtStartUTC");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "DtStartUTC"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dtEndUTC");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "DtEndUTC"));
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
