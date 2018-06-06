/**
 * ReportGameOverviewPlayerResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hy.pull.common.util.game.hb;

public class ReportGameOverviewPlayerResponse  implements java.io.Serializable {
    private com.hy.pull.common.util.game.hb.PlayerGameOverviewRecord[] reportGameOverviewPlayerResult;

    public ReportGameOverviewPlayerResponse() {
    }

    public ReportGameOverviewPlayerResponse(
           com.hy.pull.common.util.game.hb.PlayerGameOverviewRecord[] reportGameOverviewPlayerResult) {
           this.reportGameOverviewPlayerResult = reportGameOverviewPlayerResult;
    }


    /**
     * Gets the reportGameOverviewPlayerResult value for this ReportGameOverviewPlayerResponse.
     * 
     * @return reportGameOverviewPlayerResult
     */
    public com.hy.pull.common.util.game.hb.PlayerGameOverviewRecord[] getReportGameOverviewPlayerResult() {
        return reportGameOverviewPlayerResult;
    }


    /**
     * Sets the reportGameOverviewPlayerResult value for this ReportGameOverviewPlayerResponse.
     * 
     * @param reportGameOverviewPlayerResult
     */
    public void setReportGameOverviewPlayerResult(com.hy.pull.common.util.game.hb.PlayerGameOverviewRecord[] reportGameOverviewPlayerResult) {
        this.reportGameOverviewPlayerResult = reportGameOverviewPlayerResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ReportGameOverviewPlayerResponse)) return false;
        ReportGameOverviewPlayerResponse other = (ReportGameOverviewPlayerResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.reportGameOverviewPlayerResult==null && other.getReportGameOverviewPlayerResult()==null) || 
             (this.reportGameOverviewPlayerResult!=null &&
              java.util.Arrays.equals(this.reportGameOverviewPlayerResult, other.getReportGameOverviewPlayerResult())));
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
        if (getReportGameOverviewPlayerResult() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getReportGameOverviewPlayerResult());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getReportGameOverviewPlayerResult(), i);
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
        new org.apache.axis.description.TypeDesc(ReportGameOverviewPlayerResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.oxypite.com/", ">ReportGameOverviewPlayerResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reportGameOverviewPlayerResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "ReportGameOverviewPlayerResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "PlayerGameOverviewRecord"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "PlayerGameOverviewRecord"));
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
