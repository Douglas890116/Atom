/**
 * LogoutThirdPartyPlayerResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hy.pull.common.util.game.hb;

public class LogoutThirdPartyPlayerResponse  implements java.io.Serializable {
    private com.hy.pull.common.util.game.hb.ThirdPartyPlayerLogoutResponse logoutThirdPartyPlayerResult;

    public LogoutThirdPartyPlayerResponse() {
    }

    public LogoutThirdPartyPlayerResponse(
           com.hy.pull.common.util.game.hb.ThirdPartyPlayerLogoutResponse logoutThirdPartyPlayerResult) {
           this.logoutThirdPartyPlayerResult = logoutThirdPartyPlayerResult;
    }


    /**
     * Gets the logoutThirdPartyPlayerResult value for this LogoutThirdPartyPlayerResponse.
     * 
     * @return logoutThirdPartyPlayerResult
     */
    public com.hy.pull.common.util.game.hb.ThirdPartyPlayerLogoutResponse getLogoutThirdPartyPlayerResult() {
        return logoutThirdPartyPlayerResult;
    }


    /**
     * Sets the logoutThirdPartyPlayerResult value for this LogoutThirdPartyPlayerResponse.
     * 
     * @param logoutThirdPartyPlayerResult
     */
    public void setLogoutThirdPartyPlayerResult(com.hy.pull.common.util.game.hb.ThirdPartyPlayerLogoutResponse logoutThirdPartyPlayerResult) {
        this.logoutThirdPartyPlayerResult = logoutThirdPartyPlayerResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof LogoutThirdPartyPlayerResponse)) return false;
        LogoutThirdPartyPlayerResponse other = (LogoutThirdPartyPlayerResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.logoutThirdPartyPlayerResult==null && other.getLogoutThirdPartyPlayerResult()==null) || 
             (this.logoutThirdPartyPlayerResult!=null &&
              this.logoutThirdPartyPlayerResult.equals(other.getLogoutThirdPartyPlayerResult())));
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
        if (getLogoutThirdPartyPlayerResult() != null) {
            _hashCode += getLogoutThirdPartyPlayerResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(LogoutThirdPartyPlayerResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.oxypite.com/", ">LogoutThirdPartyPlayerResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("logoutThirdPartyPlayerResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "LogoutThirdPartyPlayerResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "ThirdPartyPlayerLogoutResponse"));
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
