/**
 * SetMaintenanceModeResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hy.pull.common.util.game.hb;

public class SetMaintenanceModeResponse  implements java.io.Serializable {
    private com.hy.pull.common.util.game.hb.MaintenanceModeResponse setMaintenanceModeResult;

    public SetMaintenanceModeResponse() {
    }

    public SetMaintenanceModeResponse(
           com.hy.pull.common.util.game.hb.MaintenanceModeResponse setMaintenanceModeResult) {
           this.setMaintenanceModeResult = setMaintenanceModeResult;
    }


    /**
     * Gets the setMaintenanceModeResult value for this SetMaintenanceModeResponse.
     * 
     * @return setMaintenanceModeResult
     */
    public com.hy.pull.common.util.game.hb.MaintenanceModeResponse getSetMaintenanceModeResult() {
        return setMaintenanceModeResult;
    }


    /**
     * Sets the setMaintenanceModeResult value for this SetMaintenanceModeResponse.
     * 
     * @param setMaintenanceModeResult
     */
    public void setSetMaintenanceModeResult(com.hy.pull.common.util.game.hb.MaintenanceModeResponse setMaintenanceModeResult) {
        this.setMaintenanceModeResult = setMaintenanceModeResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SetMaintenanceModeResponse)) return false;
        SetMaintenanceModeResponse other = (SetMaintenanceModeResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.setMaintenanceModeResult==null && other.getSetMaintenanceModeResult()==null) || 
             (this.setMaintenanceModeResult!=null &&
              this.setMaintenanceModeResult.equals(other.getSetMaintenanceModeResult())));
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
        if (getSetMaintenanceModeResult() != null) {
            _hashCode += getSetMaintenanceModeResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SetMaintenanceModeResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.oxypite.com/", ">SetMaintenanceModeResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("setMaintenanceModeResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "SetMaintenanceModeResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "MaintenanceModeResponse"));
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
