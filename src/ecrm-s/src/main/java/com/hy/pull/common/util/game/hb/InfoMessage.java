/**
 * InfoMessage.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hy.pull.common.util.game.hb;

public class InfoMessage  implements java.io.Serializable {
    private java.lang.String code;  // attribute

    private java.lang.String message;  // attribute

    private int type;  // attribute

    private java.lang.String stacktrace;  // attribute

    private java.lang.String url;  // attribute

    private boolean suppress;  // attribute

    private java.lang.String msgcode;  // attribute

    public InfoMessage() {
    }

    public InfoMessage(
           java.lang.String code,
           java.lang.String message,
           int type,
           java.lang.String stacktrace,
           java.lang.String url,
           boolean suppress,
           java.lang.String msgcode) {
           this.code = code;
           this.message = message;
           this.type = type;
           this.stacktrace = stacktrace;
           this.url = url;
           this.suppress = suppress;
           this.msgcode = msgcode;
    }


    /**
     * Gets the code value for this InfoMessage.
     * 
     * @return code
     */
    public java.lang.String getCode() {
        return code;
    }


    /**
     * Sets the code value for this InfoMessage.
     * 
     * @param code
     */
    public void setCode(java.lang.String code) {
        this.code = code;
    }


    /**
     * Gets the message value for this InfoMessage.
     * 
     * @return message
     */
    public java.lang.String getMessage() {
        return message;
    }


    /**
     * Sets the message value for this InfoMessage.
     * 
     * @param message
     */
    public void setMessage(java.lang.String message) {
        this.message = message;
    }


    /**
     * Gets the type value for this InfoMessage.
     * 
     * @return type
     */
    public int getType() {
        return type;
    }


    /**
     * Sets the type value for this InfoMessage.
     * 
     * @param type
     */
    public void setType(int type) {
        this.type = type;
    }


    /**
     * Gets the stacktrace value for this InfoMessage.
     * 
     * @return stacktrace
     */
    public java.lang.String getStacktrace() {
        return stacktrace;
    }


    /**
     * Sets the stacktrace value for this InfoMessage.
     * 
     * @param stacktrace
     */
    public void setStacktrace(java.lang.String stacktrace) {
        this.stacktrace = stacktrace;
    }


    /**
     * Gets the url value for this InfoMessage.
     * 
     * @return url
     */
    public java.lang.String getUrl() {
        return url;
    }


    /**
     * Sets the url value for this InfoMessage.
     * 
     * @param url
     */
    public void setUrl(java.lang.String url) {
        this.url = url;
    }


    /**
     * Gets the suppress value for this InfoMessage.
     * 
     * @return suppress
     */
    public boolean isSuppress() {
        return suppress;
    }


    /**
     * Sets the suppress value for this InfoMessage.
     * 
     * @param suppress
     */
    public void setSuppress(boolean suppress) {
        this.suppress = suppress;
    }


    /**
     * Gets the msgcode value for this InfoMessage.
     * 
     * @return msgcode
     */
    public java.lang.String getMsgcode() {
        return msgcode;
    }


    /**
     * Sets the msgcode value for this InfoMessage.
     * 
     * @param msgcode
     */
    public void setMsgcode(java.lang.String msgcode) {
        this.msgcode = msgcode;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof InfoMessage)) return false;
        InfoMessage other = (InfoMessage) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.code==null && other.getCode()==null) || 
             (this.code!=null &&
              this.code.equals(other.getCode()))) &&
            ((this.message==null && other.getMessage()==null) || 
             (this.message!=null &&
              this.message.equals(other.getMessage()))) &&
            this.type == other.getType() &&
            ((this.stacktrace==null && other.getStacktrace()==null) || 
             (this.stacktrace!=null &&
              this.stacktrace.equals(other.getStacktrace()))) &&
            ((this.url==null && other.getUrl()==null) || 
             (this.url!=null &&
              this.url.equals(other.getUrl()))) &&
            this.suppress == other.isSuppress() &&
            ((this.msgcode==null && other.getMsgcode()==null) || 
             (this.msgcode!=null &&
              this.msgcode.equals(other.getMsgcode())));
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
        if (getCode() != null) {
            _hashCode += getCode().hashCode();
        }
        if (getMessage() != null) {
            _hashCode += getMessage().hashCode();
        }
        _hashCode += getType();
        if (getStacktrace() != null) {
            _hashCode += getStacktrace().hashCode();
        }
        if (getUrl() != null) {
            _hashCode += getUrl().hashCode();
        }
        _hashCode += (isSuppress() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getMsgcode() != null) {
            _hashCode += getMsgcode().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(InfoMessage.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "InfoMessage"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("code");
        attrField.setXmlName(new javax.xml.namespace.QName("", "code"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("message");
        attrField.setXmlName(new javax.xml.namespace.QName("", "message"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("type");
        attrField.setXmlName(new javax.xml.namespace.QName("", "type"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("stacktrace");
        attrField.setXmlName(new javax.xml.namespace.QName("", "stacktrace"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("url");
        attrField.setXmlName(new javax.xml.namespace.QName("", "url"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("suppress");
        attrField.setXmlName(new javax.xml.namespace.QName("", "suppress"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("msgcode");
        attrField.setXmlName(new javax.xml.namespace.QName("", "msgcode"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(attrField);
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
