/**
 * GameResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hy.pull.common.util.game.hb;

public class GameResponse  extends com.hy.pull.common.util.game.hb.BaseResponse  implements java.io.Serializable {
    private java.lang.String hostedLaunchUrl;

    private com.hy.pull.common.util.game.hb.GameClientDbDTO[] games;

    public GameResponse() {
    }

    public GameResponse(
           java.lang.String hostedLaunchUrl,
           com.hy.pull.common.util.game.hb.GameClientDbDTO[] games) {
        this.hostedLaunchUrl = hostedLaunchUrl;
        this.games = games;
    }


    /**
     * Gets the hostedLaunchUrl value for this GameResponse.
     * 
     * @return hostedLaunchUrl
     */
    public java.lang.String getHostedLaunchUrl() {
        return hostedLaunchUrl;
    }


    /**
     * Sets the hostedLaunchUrl value for this GameResponse.
     * 
     * @param hostedLaunchUrl
     */
    public void setHostedLaunchUrl(java.lang.String hostedLaunchUrl) {
        this.hostedLaunchUrl = hostedLaunchUrl;
    }


    /**
     * Gets the games value for this GameResponse.
     * 
     * @return games
     */
    public com.hy.pull.common.util.game.hb.GameClientDbDTO[] getGames() {
        return games;
    }


    /**
     * Sets the games value for this GameResponse.
     * 
     * @param games
     */
    public void setGames(com.hy.pull.common.util.game.hb.GameClientDbDTO[] games) {
        this.games = games;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GameResponse)) return false;
        GameResponse other = (GameResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.hostedLaunchUrl==null && other.getHostedLaunchUrl()==null) || 
             (this.hostedLaunchUrl!=null &&
              this.hostedLaunchUrl.equals(other.getHostedLaunchUrl()))) &&
            ((this.games==null && other.getGames()==null) || 
             (this.games!=null &&
              java.util.Arrays.equals(this.games, other.getGames())));
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
        if (getHostedLaunchUrl() != null) {
            _hashCode += getHostedLaunchUrl().hashCode();
        }
        if (getGames() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getGames());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getGames(), i);
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
        new org.apache.axis.description.TypeDesc(GameResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hostedLaunchUrl");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "HostedLaunchUrl"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("games");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "Games"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameClientDbDTO"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameClientDbDTO"));
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
