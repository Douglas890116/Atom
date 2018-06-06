/**
 * LogoutThirdPartyPlayerRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hy.pull.common.util.game.hb;

public class LogoutThirdPartyPlayerRequest  extends com.hy.pull.common.util.game.hb.BaseRequest  implements java.io.Serializable {
    private java.lang.String accountId;

    private java.lang.String token;

    private java.lang.Boolean forceLogout;

    public LogoutThirdPartyPlayerRequest() {
    }

    public LogoutThirdPartyPlayerRequest(
           java.lang.String brandId,
           java.lang.String APIKey,
           java.lang.String playerHostAddress,
           java.lang.String CFCId,
           java.lang.String locale,
           java.lang.String accountId,
           java.lang.String token,
           java.lang.Boolean forceLogout) {
        super(
            brandId,
            APIKey,
            playerHostAddress,
            CFCId,
            locale);
        this.accountId = accountId;
        this.token = token;
        this.forceLogout = forceLogout;
    }


    /**
     * Gets the accountId value for this LogoutThirdPartyPlayerRequest.
     * 
     * @return accountId
     */
    public java.lang.String getAccountId() {
        return accountId;
    }


    /**
     * Sets the accountId value for this LogoutThirdPartyPlayerRequest.
     * 
     * @param accountId
     */
    public void setAccountId(java.lang.String accountId) {
        this.accountId = accountId;
    }


    /**
     * Gets the token value for this LogoutThirdPartyPlayerRequest.
     * 
     * @return token
     */
    public java.lang.String getToken() {
        return token;
    }


    /**
     * Sets the token value for this LogoutThirdPartyPlayerRequest.
     * 
     * @param token
     */
    public void setToken(java.lang.String token) {
        this.token = token;
    }


    /**
     * Gets the forceLogout value for this LogoutThirdPartyPlayerRequest.
     * 
     * @return forceLogout
     */
    public java.lang.Boolean getForceLogout() {
        return forceLogout;
    }


    /**
     * Sets the forceLogout value for this LogoutThirdPartyPlayerRequest.
     * 
     * @param forceLogout
     */
    public void setForceLogout(java.lang.Boolean forceLogout) {
        this.forceLogout = forceLogout;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof LogoutThirdPartyPlayerRequest)) return false;
        LogoutThirdPartyPlayerRequest other = (LogoutThirdPartyPlayerRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.accountId==null && other.getAccountId()==null) || 
             (this.accountId!=null &&
              this.accountId.equals(other.getAccountId()))) &&
            ((this.token==null && other.getToken()==null) || 
             (this.token!=null &&
              this.token.equals(other.getToken()))) &&
            ((this.forceLogout==null && other.getForceLogout()==null) || 
             (this.forceLogout!=null &&
              this.forceLogout.equals(other.getForceLogout())));
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
        if (getAccountId() != null) {
            _hashCode += getAccountId().hashCode();
        }
        if (getToken() != null) {
            _hashCode += getToken().hashCode();
        }
        if (getForceLogout() != null) {
            _hashCode += getForceLogout().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(LogoutThirdPartyPlayerRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "LogoutThirdPartyPlayerRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accountId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "AccountId"));
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
        elemField.setFieldName("forceLogout");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "ForceLogout"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
