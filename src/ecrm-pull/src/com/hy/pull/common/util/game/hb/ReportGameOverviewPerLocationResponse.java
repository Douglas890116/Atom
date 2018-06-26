/**
 * ReportGameOverviewPerLocationResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hy.pull.common.util.game.hb;

public class ReportGameOverviewPerLocationResponse  implements java.io.Serializable {
    private com.hy.pull.common.util.game.hb.GameOverviewPerLocationRecord[] reportGameOverviewPerLocationResult;

    public ReportGameOverviewPerLocationResponse() {
    }

    public ReportGameOverviewPerLocationResponse(
           com.hy.pull.common.util.game.hb.GameOverviewPerLocationRecord[] reportGameOverviewPerLocationResult) {
           this.reportGameOverviewPerLocationResult = reportGameOverviewPerLocationResult;
    }


    /**
     * Gets the reportGameOverviewPerLocationResult value for this ReportGameOverviewPerLocationResponse.
     * 
     * @return reportGameOverviewPerLocationResult
     */
    public com.hy.pull.common.util.game.hb.GameOverviewPerLocationRecord[] getReportGameOverviewPerLocationResult() {
        return reportGameOverviewPerLocationResult;
    }


    /**
     * Sets the reportGameOverviewPerLocationResult value for this ReportGameOverviewPerLocationResponse.
     * 
     * @param reportGameOverviewPerLocationResult
     */
    public void setReportGameOverviewPerLocationResult(com.hy.pull.common.util.game.hb.GameOverviewPerLocationRecord[] reportGameOverviewPerLocationResult) {
        this.reportGameOverviewPerLocationResult = reportGameOverviewPerLocationResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ReportGameOverviewPerLocationResponse)) return false;
        ReportGameOverviewPerLocationResponse other = (ReportGameOverviewPerLocationResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.reportGameOverviewPerLocationResult==null && other.getReportGameOverviewPerLocationResult()==null) || 
             (this.reportGameOverviewPerLocationResult!=null &&
              java.util.Arrays.equals(this.reportGameOverviewPerLocationResult, other.getReportGameOverviewPerLocationResult())));
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
        if (getReportGameOverviewPerLocationResult() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getReportGameOverviewPerLocationResult());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getReportGameOverviewPerLocationResult(), i);
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
        new org.apache.axis.description.TypeDesc(ReportGameOverviewPerLocationResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.oxypite.com/", ">ReportGameOverviewPerLocationResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reportGameOverviewPerLocationResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "ReportGameOverviewPerLocationResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameOverviewPerLocationRecord"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameOverviewPerLocationRecord"));
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
