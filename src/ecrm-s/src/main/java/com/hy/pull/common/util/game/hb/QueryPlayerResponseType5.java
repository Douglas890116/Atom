/**
 * QueryPlayerResponseType5.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hy.pull.common.util.game.hb;

public class QueryPlayerResponseType5  implements java.io.Serializable {
    private com.hy.pull.common.util.game.hb.QueryPlayerResponse queryPlayerResult;

    public QueryPlayerResponseType5() {
    }

    public QueryPlayerResponseType5(
           com.hy.pull.common.util.game.hb.QueryPlayerResponse queryPlayerResult) {
           this.queryPlayerResult = queryPlayerResult;
    }


    /**
     * Gets the queryPlayerResult value for this QueryPlayerResponseType5.
     * 
     * @return queryPlayerResult
     */
    public com.hy.pull.common.util.game.hb.QueryPlayerResponse getQueryPlayerResult() {
        return queryPlayerResult;
    }


    /**
     * Sets the queryPlayerResult value for this QueryPlayerResponseType5.
     * 
     * @param queryPlayerResult
     */
    public void setQueryPlayerResult(com.hy.pull.common.util.game.hb.QueryPlayerResponse queryPlayerResult) {
        this.queryPlayerResult = queryPlayerResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof QueryPlayerResponseType5)) return false;
        QueryPlayerResponseType5 other = (QueryPlayerResponseType5) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.queryPlayerResult==null && other.getQueryPlayerResult()==null) || 
             (this.queryPlayerResult!=null &&
              this.queryPlayerResult.equals(other.getQueryPlayerResult())));
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
        if (getQueryPlayerResult() != null) {
            _hashCode += getQueryPlayerResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(QueryPlayerResponseType5.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.oxypite.com/", ">QueryPlayerResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("queryPlayerResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "QueryPlayerResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "QueryPlayerResponse"));
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
