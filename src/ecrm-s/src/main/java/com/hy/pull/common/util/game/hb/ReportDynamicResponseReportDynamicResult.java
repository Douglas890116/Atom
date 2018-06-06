/**
 * ReportDynamicResponseReportDynamicResult.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hy.pull.common.util.game.hb;

public class ReportDynamicResponseReportDynamicResult  implements java.io.Serializable, org.apache.axis.encoding.AnyContentType {
    private org.apache.axis.message.MessageElement [] _any;

    private org.apache.axis.message.MessageElement [] _any2;

    public ReportDynamicResponseReportDynamicResult() {
    }

    public ReportDynamicResponseReportDynamicResult(
           org.apache.axis.message.MessageElement [] _any,
           org.apache.axis.message.MessageElement [] _any2) {
           this._any = _any;
           this._any2 = _any2;
    }


    /**
     * Gets the _any value for this ReportDynamicResponseReportDynamicResult.
     * 
     * @return _any
     */
    public org.apache.axis.message.MessageElement [] get_any() {
        return _any;
    }


    /**
     * Sets the _any value for this ReportDynamicResponseReportDynamicResult.
     * 
     * @param _any
     */
    public void set_any(org.apache.axis.message.MessageElement [] _any) {
        this._any = _any;
    }


    /**
     * Gets the _any2 value for this ReportDynamicResponseReportDynamicResult.
     * 
     * @return _any2
     */
    public org.apache.axis.message.MessageElement [] get_any2() {
        return _any2;
    }


    /**
     * Sets the _any2 value for this ReportDynamicResponseReportDynamicResult.
     * 
     * @param _any2
     */
    public void set_any2(org.apache.axis.message.MessageElement [] _any2) {
        this._any2 = _any2;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ReportDynamicResponseReportDynamicResult)) return false;
        ReportDynamicResponseReportDynamicResult other = (ReportDynamicResponseReportDynamicResult) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this._any==null && other.get_any()==null) || 
             (this._any!=null &&
              java.util.Arrays.equals(this._any, other.get_any()))) &&
            ((this._any2==null && other.get_any2()==null) || 
             (this._any2!=null &&
              java.util.Arrays.equals(this._any2, other.get_any2())));
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
        if (get_any() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(get_any());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(get_any(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (get_any2() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(get_any2());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(get_any2(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ReportDynamicResponseReportDynamicResult.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.oxypite.com/", ">>ReportDynamicResponse>ReportDynamicResult"));
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
