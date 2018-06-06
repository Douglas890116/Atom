/**
 * JackpotGameLinkInfoDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hy.pull.common.util.game.hb;

public class JackpotGameLinkInfoDTO  implements java.io.Serializable {
    private java.lang.String jackpotId;

    private java.lang.String jackpotGroupId;

    private java.lang.String brandId;

    private java.lang.String[] brandGameIds;

    private java.lang.String[] keynames;

    public JackpotGameLinkInfoDTO() {
    }

    public JackpotGameLinkInfoDTO(
           java.lang.String jackpotId,
           java.lang.String jackpotGroupId,
           java.lang.String brandId,
           java.lang.String[] brandGameIds,
           java.lang.String[] keynames) {
           this.jackpotId = jackpotId;
           this.jackpotGroupId = jackpotGroupId;
           this.brandId = brandId;
           this.brandGameIds = brandGameIds;
           this.keynames = keynames;
    }


    /**
     * Gets the jackpotId value for this JackpotGameLinkInfoDTO.
     * 
     * @return jackpotId
     */
    public java.lang.String getJackpotId() {
        return jackpotId;
    }


    /**
     * Sets the jackpotId value for this JackpotGameLinkInfoDTO.
     * 
     * @param jackpotId
     */
    public void setJackpotId(java.lang.String jackpotId) {
        this.jackpotId = jackpotId;
    }


    /**
     * Gets the jackpotGroupId value for this JackpotGameLinkInfoDTO.
     * 
     * @return jackpotGroupId
     */
    public java.lang.String getJackpotGroupId() {
        return jackpotGroupId;
    }


    /**
     * Sets the jackpotGroupId value for this JackpotGameLinkInfoDTO.
     * 
     * @param jackpotGroupId
     */
    public void setJackpotGroupId(java.lang.String jackpotGroupId) {
        this.jackpotGroupId = jackpotGroupId;
    }


    /**
     * Gets the brandId value for this JackpotGameLinkInfoDTO.
     * 
     * @return brandId
     */
    public java.lang.String getBrandId() {
        return brandId;
    }


    /**
     * Sets the brandId value for this JackpotGameLinkInfoDTO.
     * 
     * @param brandId
     */
    public void setBrandId(java.lang.String brandId) {
        this.brandId = brandId;
    }


    /**
     * Gets the brandGameIds value for this JackpotGameLinkInfoDTO.
     * 
     * @return brandGameIds
     */
    public java.lang.String[] getBrandGameIds() {
        return brandGameIds;
    }


    /**
     * Sets the brandGameIds value for this JackpotGameLinkInfoDTO.
     * 
     * @param brandGameIds
     */
    public void setBrandGameIds(java.lang.String[] brandGameIds) {
        this.brandGameIds = brandGameIds;
    }


    /**
     * Gets the keynames value for this JackpotGameLinkInfoDTO.
     * 
     * @return keynames
     */
    public java.lang.String[] getKeynames() {
        return keynames;
    }


    /**
     * Sets the keynames value for this JackpotGameLinkInfoDTO.
     * 
     * @param keynames
     */
    public void setKeynames(java.lang.String[] keynames) {
        this.keynames = keynames;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof JackpotGameLinkInfoDTO)) return false;
        JackpotGameLinkInfoDTO other = (JackpotGameLinkInfoDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.jackpotId==null && other.getJackpotId()==null) || 
             (this.jackpotId!=null &&
              this.jackpotId.equals(other.getJackpotId()))) &&
            ((this.jackpotGroupId==null && other.getJackpotGroupId()==null) || 
             (this.jackpotGroupId!=null &&
              this.jackpotGroupId.equals(other.getJackpotGroupId()))) &&
            ((this.brandId==null && other.getBrandId()==null) || 
             (this.brandId!=null &&
              this.brandId.equals(other.getBrandId()))) &&
            ((this.brandGameIds==null && other.getBrandGameIds()==null) || 
             (this.brandGameIds!=null &&
              java.util.Arrays.equals(this.brandGameIds, other.getBrandGameIds()))) &&
            ((this.keynames==null && other.getKeynames()==null) || 
             (this.keynames!=null &&
              java.util.Arrays.equals(this.keynames, other.getKeynames())));
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
        if (getJackpotId() != null) {
            _hashCode += getJackpotId().hashCode();
        }
        if (getJackpotGroupId() != null) {
            _hashCode += getJackpotGroupId().hashCode();
        }
        if (getBrandId() != null) {
            _hashCode += getBrandId().hashCode();
        }
        if (getBrandGameIds() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getBrandGameIds());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getBrandGameIds(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getKeynames() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getKeynames());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getKeynames(), i);
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
        new org.apache.axis.description.TypeDesc(JackpotGameLinkInfoDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "JackpotGameLinkInfoDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("jackpotId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "JackpotId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("jackpotGroupId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "JackpotGroupId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("brandId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "BrandId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("brandGameIds");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "BrandGameIds"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://microsoft.com/wsdl/types/", "guid"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "guid"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("keynames");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "Keynames"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "string"));
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
