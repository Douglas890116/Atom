/**
 * LogoutAllPlayersInBrandResponseType4.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hy.pull.common.util.game.hb;

public class LogoutAllPlayersInBrandResponseType4  implements java.io.Serializable {
    private com.hy.pull.common.util.game.hb.LogoutAllPlayersInBrandResponse logoutAllPlayersInBrandResult;

    public LogoutAllPlayersInBrandResponseType4() {
    }

    public LogoutAllPlayersInBrandResponseType4(
           com.hy.pull.common.util.game.hb.LogoutAllPlayersInBrandResponse logoutAllPlayersInBrandResult) {
           this.logoutAllPlayersInBrandResult = logoutAllPlayersInBrandResult;
    }


    /**
     * Gets the logoutAllPlayersInBrandResult value for this LogoutAllPlayersInBrandResponseType4.
     * 
     * @return logoutAllPlayersInBrandResult
     */
    public com.hy.pull.common.util.game.hb.LogoutAllPlayersInBrandResponse getLogoutAllPlayersInBrandResult() {
        return logoutAllPlayersInBrandResult;
    }


    /**
     * Sets the logoutAllPlayersInBrandResult value for this LogoutAllPlayersInBrandResponseType4.
     * 
     * @param logoutAllPlayersInBrandResult
     */
    public void setLogoutAllPlayersInBrandResult(com.hy.pull.common.util.game.hb.LogoutAllPlayersInBrandResponse logoutAllPlayersInBrandResult) {
        this.logoutAllPlayersInBrandResult = logoutAllPlayersInBrandResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof LogoutAllPlayersInBrandResponseType4)) return false;
        LogoutAllPlayersInBrandResponseType4 other = (LogoutAllPlayersInBrandResponseType4) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.logoutAllPlayersInBrandResult==null && other.getLogoutAllPlayersInBrandResult()==null) || 
             (this.logoutAllPlayersInBrandResult!=null &&
              this.logoutAllPlayersInBrandResult.equals(other.getLogoutAllPlayersInBrandResult())));
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
        if (getLogoutAllPlayersInBrandResult() != null) {
            _hashCode += getLogoutAllPlayersInBrandResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(LogoutAllPlayersInBrandResponseType4.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.oxypite.com/", ">LogoutAllPlayersInBrandResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("logoutAllPlayersInBrandResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "LogoutAllPlayersInBrandResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "LogoutAllPlayersInBrandResponse"));
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
