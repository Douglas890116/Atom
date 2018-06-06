/**
 * Game.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hy.pull.common.util.game.hb;

public class Game  implements java.io.Serializable {
    private java.lang.String brandgameid;  // attribute

    private java.lang.String displayname;  // attribute

    private int webplatformid;  // attribute

    private int mobiplatformid;  // attribute

    private java.lang.String keyname;  // attribute

    private java.util.Calendar dtadded;  // attribute

    private java.lang.String langkey;  // attribute

    public Game() {
    }

    public Game(
           java.lang.String brandgameid,
           java.lang.String displayname,
           int webplatformid,
           int mobiplatformid,
           java.lang.String keyname,
           java.util.Calendar dtadded,
           java.lang.String langkey) {
           this.brandgameid = brandgameid;
           this.displayname = displayname;
           this.webplatformid = webplatformid;
           this.mobiplatformid = mobiplatformid;
           this.keyname = keyname;
           this.dtadded = dtadded;
           this.langkey = langkey;
    }


    /**
     * Gets the brandgameid value for this Game.
     * 
     * @return brandgameid
     */
    public java.lang.String getBrandgameid() {
        return brandgameid;
    }


    /**
     * Sets the brandgameid value for this Game.
     * 
     * @param brandgameid
     */
    public void setBrandgameid(java.lang.String brandgameid) {
        this.brandgameid = brandgameid;
    }


    /**
     * Gets the displayname value for this Game.
     * 
     * @return displayname
     */
    public java.lang.String getDisplayname() {
        return displayname;
    }


    /**
     * Sets the displayname value for this Game.
     * 
     * @param displayname
     */
    public void setDisplayname(java.lang.String displayname) {
        this.displayname = displayname;
    }


    /**
     * Gets the webplatformid value for this Game.
     * 
     * @return webplatformid
     */
    public int getWebplatformid() {
        return webplatformid;
    }


    /**
     * Sets the webplatformid value for this Game.
     * 
     * @param webplatformid
     */
    public void setWebplatformid(int webplatformid) {
        this.webplatformid = webplatformid;
    }


    /**
     * Gets the mobiplatformid value for this Game.
     * 
     * @return mobiplatformid
     */
    public int getMobiplatformid() {
        return mobiplatformid;
    }


    /**
     * Sets the mobiplatformid value for this Game.
     * 
     * @param mobiplatformid
     */
    public void setMobiplatformid(int mobiplatformid) {
        this.mobiplatformid = mobiplatformid;
    }


    /**
     * Gets the keyname value for this Game.
     * 
     * @return keyname
     */
    public java.lang.String getKeyname() {
        return keyname;
    }


    /**
     * Sets the keyname value for this Game.
     * 
     * @param keyname
     */
    public void setKeyname(java.lang.String keyname) {
        this.keyname = keyname;
    }


    /**
     * Gets the dtadded value for this Game.
     * 
     * @return dtadded
     */
    public java.util.Calendar getDtadded() {
        return dtadded;
    }


    /**
     * Sets the dtadded value for this Game.
     * 
     * @param dtadded
     */
    public void setDtadded(java.util.Calendar dtadded) {
        this.dtadded = dtadded;
    }


    /**
     * Gets the langkey value for this Game.
     * 
     * @return langkey
     */
    public java.lang.String getLangkey() {
        return langkey;
    }


    /**
     * Sets the langkey value for this Game.
     * 
     * @param langkey
     */
    public void setLangkey(java.lang.String langkey) {
        this.langkey = langkey;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Game)) return false;
        Game other = (Game) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.brandgameid==null && other.getBrandgameid()==null) || 
             (this.brandgameid!=null &&
              this.brandgameid.equals(other.getBrandgameid()))) &&
            ((this.displayname==null && other.getDisplayname()==null) || 
             (this.displayname!=null &&
              this.displayname.equals(other.getDisplayname()))) &&
            this.webplatformid == other.getWebplatformid() &&
            this.mobiplatformid == other.getMobiplatformid() &&
            ((this.keyname==null && other.getKeyname()==null) || 
             (this.keyname!=null &&
              this.keyname.equals(other.getKeyname()))) &&
            ((this.dtadded==null && other.getDtadded()==null) || 
             (this.dtadded!=null &&
              this.dtadded.equals(other.getDtadded()))) &&
            ((this.langkey==null && other.getLangkey()==null) || 
             (this.langkey!=null &&
              this.langkey.equals(other.getLangkey())));
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
        if (getBrandgameid() != null) {
            _hashCode += getBrandgameid().hashCode();
        }
        if (getDisplayname() != null) {
            _hashCode += getDisplayname().hashCode();
        }
        _hashCode += getWebplatformid();
        _hashCode += getMobiplatformid();
        if (getKeyname() != null) {
            _hashCode += getKeyname().hashCode();
        }
        if (getDtadded() != null) {
            _hashCode += getDtadded().hashCode();
        }
        if (getLangkey() != null) {
            _hashCode += getLangkey().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Game.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "Game"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("brandgameid");
        attrField.setXmlName(new javax.xml.namespace.QName("", "brandgameid"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://microsoft.com/wsdl/types/", "guid"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("displayname");
        attrField.setXmlName(new javax.xml.namespace.QName("", "displayname"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("webplatformid");
        attrField.setXmlName(new javax.xml.namespace.QName("", "webplatformid"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("mobiplatformid");
        attrField.setXmlName(new javax.xml.namespace.QName("", "mobiplatformid"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("keyname");
        attrField.setXmlName(new javax.xml.namespace.QName("", "keyname"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("dtadded");
        attrField.setXmlName(new javax.xml.namespace.QName("", "dtadded"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("langkey");
        attrField.setXmlName(new javax.xml.namespace.QName("", "langkey"));
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
