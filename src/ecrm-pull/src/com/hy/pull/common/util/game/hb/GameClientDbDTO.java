/**
 * GameClientDbDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hy.pull.common.util.game.hb;

public class GameClientDbDTO  implements java.io.Serializable {
    private java.lang.String brandGameId;

    private java.lang.String name;

    private java.lang.String keyName;

    private boolean isNew;

    private java.util.Calendar dtAdded;

    private java.util.Calendar dtUpdated;

    private short gameTypeId;

    private short releaseStatusId;

    private java.lang.String gameTypeName;

    private boolean mobileCapable;

    private short mobiPlatformId;

    private short webPlatformId;

    private java.lang.String gameTypeDisplayName;

    private short baseGameTypeId;

    private boolean exProv;

    private java.lang.String fileName;

    private java.lang.String groupName;

    private java.lang.String lineDesc;

    private boolean isFeat;

    private java.math.BigDecimal RTP;

    private java.lang.String reportName;

    private com.hy.pull.common.util.game.hb.GameTranslationDTO[] translatedNames;

    public GameClientDbDTO() {
    }

    public GameClientDbDTO(
           java.lang.String brandGameId,
           java.lang.String name,
           java.lang.String keyName,
           boolean isNew,
           java.util.Calendar dtAdded,
           java.util.Calendar dtUpdated,
           short gameTypeId,
           short releaseStatusId,
           java.lang.String gameTypeName,
           boolean mobileCapable,
           short mobiPlatformId,
           short webPlatformId,
           java.lang.String gameTypeDisplayName,
           short baseGameTypeId,
           boolean exProv,
           java.lang.String fileName,
           java.lang.String groupName,
           java.lang.String lineDesc,
           boolean isFeat,
           java.math.BigDecimal RTP,
           java.lang.String reportName,
           com.hy.pull.common.util.game.hb.GameTranslationDTO[] translatedNames) {
           this.brandGameId = brandGameId;
           this.name = name;
           this.keyName = keyName;
           this.isNew = isNew;
           this.dtAdded = dtAdded;
           this.dtUpdated = dtUpdated;
           this.gameTypeId = gameTypeId;
           this.releaseStatusId = releaseStatusId;
           this.gameTypeName = gameTypeName;
           this.mobileCapable = mobileCapable;
           this.mobiPlatformId = mobiPlatformId;
           this.webPlatformId = webPlatformId;
           this.gameTypeDisplayName = gameTypeDisplayName;
           this.baseGameTypeId = baseGameTypeId;
           this.exProv = exProv;
           this.fileName = fileName;
           this.groupName = groupName;
           this.lineDesc = lineDesc;
           this.isFeat = isFeat;
           this.RTP = RTP;
           this.reportName = reportName;
           this.translatedNames = translatedNames;
    }


    /**
     * Gets the brandGameId value for this GameClientDbDTO.
     * 
     * @return brandGameId
     */
    public java.lang.String getBrandGameId() {
        return brandGameId;
    }


    /**
     * Sets the brandGameId value for this GameClientDbDTO.
     * 
     * @param brandGameId
     */
    public void setBrandGameId(java.lang.String brandGameId) {
        this.brandGameId = brandGameId;
    }


    /**
     * Gets the name value for this GameClientDbDTO.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this GameClientDbDTO.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the keyName value for this GameClientDbDTO.
     * 
     * @return keyName
     */
    public java.lang.String getKeyName() {
        return keyName;
    }


    /**
     * Sets the keyName value for this GameClientDbDTO.
     * 
     * @param keyName
     */
    public void setKeyName(java.lang.String keyName) {
        this.keyName = keyName;
    }


    /**
     * Gets the isNew value for this GameClientDbDTO.
     * 
     * @return isNew
     */
    public boolean isIsNew() {
        return isNew;
    }


    /**
     * Sets the isNew value for this GameClientDbDTO.
     * 
     * @param isNew
     */
    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }


    /**
     * Gets the dtAdded value for this GameClientDbDTO.
     * 
     * @return dtAdded
     */
    public java.util.Calendar getDtAdded() {
        return dtAdded;
    }


    /**
     * Sets the dtAdded value for this GameClientDbDTO.
     * 
     * @param dtAdded
     */
    public void setDtAdded(java.util.Calendar dtAdded) {
        this.dtAdded = dtAdded;
    }


    /**
     * Gets the dtUpdated value for this GameClientDbDTO.
     * 
     * @return dtUpdated
     */
    public java.util.Calendar getDtUpdated() {
        return dtUpdated;
    }


    /**
     * Sets the dtUpdated value for this GameClientDbDTO.
     * 
     * @param dtUpdated
     */
    public void setDtUpdated(java.util.Calendar dtUpdated) {
        this.dtUpdated = dtUpdated;
    }


    /**
     * Gets the gameTypeId value for this GameClientDbDTO.
     * 
     * @return gameTypeId
     */
    public short getGameTypeId() {
        return gameTypeId;
    }


    /**
     * Sets the gameTypeId value for this GameClientDbDTO.
     * 
     * @param gameTypeId
     */
    public void setGameTypeId(short gameTypeId) {
        this.gameTypeId = gameTypeId;
    }


    /**
     * Gets the releaseStatusId value for this GameClientDbDTO.
     * 
     * @return releaseStatusId
     */
    public short getReleaseStatusId() {
        return releaseStatusId;
    }


    /**
     * Sets the releaseStatusId value for this GameClientDbDTO.
     * 
     * @param releaseStatusId
     */
    public void setReleaseStatusId(short releaseStatusId) {
        this.releaseStatusId = releaseStatusId;
    }


    /**
     * Gets the gameTypeName value for this GameClientDbDTO.
     * 
     * @return gameTypeName
     */
    public java.lang.String getGameTypeName() {
        return gameTypeName;
    }


    /**
     * Sets the gameTypeName value for this GameClientDbDTO.
     * 
     * @param gameTypeName
     */
    public void setGameTypeName(java.lang.String gameTypeName) {
        this.gameTypeName = gameTypeName;
    }


    /**
     * Gets the mobileCapable value for this GameClientDbDTO.
     * 
     * @return mobileCapable
     */
    public boolean isMobileCapable() {
        return mobileCapable;
    }


    /**
     * Sets the mobileCapable value for this GameClientDbDTO.
     * 
     * @param mobileCapable
     */
    public void setMobileCapable(boolean mobileCapable) {
        this.mobileCapable = mobileCapable;
    }


    /**
     * Gets the mobiPlatformId value for this GameClientDbDTO.
     * 
     * @return mobiPlatformId
     */
    public short getMobiPlatformId() {
        return mobiPlatformId;
    }


    /**
     * Sets the mobiPlatformId value for this GameClientDbDTO.
     * 
     * @param mobiPlatformId
     */
    public void setMobiPlatformId(short mobiPlatformId) {
        this.mobiPlatformId = mobiPlatformId;
    }


    /**
     * Gets the webPlatformId value for this GameClientDbDTO.
     * 
     * @return webPlatformId
     */
    public short getWebPlatformId() {
        return webPlatformId;
    }


    /**
     * Sets the webPlatformId value for this GameClientDbDTO.
     * 
     * @param webPlatformId
     */
    public void setWebPlatformId(short webPlatformId) {
        this.webPlatformId = webPlatformId;
    }


    /**
     * Gets the gameTypeDisplayName value for this GameClientDbDTO.
     * 
     * @return gameTypeDisplayName
     */
    public java.lang.String getGameTypeDisplayName() {
        return gameTypeDisplayName;
    }


    /**
     * Sets the gameTypeDisplayName value for this GameClientDbDTO.
     * 
     * @param gameTypeDisplayName
     */
    public void setGameTypeDisplayName(java.lang.String gameTypeDisplayName) {
        this.gameTypeDisplayName = gameTypeDisplayName;
    }


    /**
     * Gets the baseGameTypeId value for this GameClientDbDTO.
     * 
     * @return baseGameTypeId
     */
    public short getBaseGameTypeId() {
        return baseGameTypeId;
    }


    /**
     * Sets the baseGameTypeId value for this GameClientDbDTO.
     * 
     * @param baseGameTypeId
     */
    public void setBaseGameTypeId(short baseGameTypeId) {
        this.baseGameTypeId = baseGameTypeId;
    }


    /**
     * Gets the exProv value for this GameClientDbDTO.
     * 
     * @return exProv
     */
    public boolean isExProv() {
        return exProv;
    }


    /**
     * Sets the exProv value for this GameClientDbDTO.
     * 
     * @param exProv
     */
    public void setExProv(boolean exProv) {
        this.exProv = exProv;
    }


    /**
     * Gets the fileName value for this GameClientDbDTO.
     * 
     * @return fileName
     */
    public java.lang.String getFileName() {
        return fileName;
    }


    /**
     * Sets the fileName value for this GameClientDbDTO.
     * 
     * @param fileName
     */
    public void setFileName(java.lang.String fileName) {
        this.fileName = fileName;
    }


    /**
     * Gets the groupName value for this GameClientDbDTO.
     * 
     * @return groupName
     */
    public java.lang.String getGroupName() {
        return groupName;
    }


    /**
     * Sets the groupName value for this GameClientDbDTO.
     * 
     * @param groupName
     */
    public void setGroupName(java.lang.String groupName) {
        this.groupName = groupName;
    }


    /**
     * Gets the lineDesc value for this GameClientDbDTO.
     * 
     * @return lineDesc
     */
    public java.lang.String getLineDesc() {
        return lineDesc;
    }


    /**
     * Sets the lineDesc value for this GameClientDbDTO.
     * 
     * @param lineDesc
     */
    public void setLineDesc(java.lang.String lineDesc) {
        this.lineDesc = lineDesc;
    }


    /**
     * Gets the isFeat value for this GameClientDbDTO.
     * 
     * @return isFeat
     */
    public boolean isIsFeat() {
        return isFeat;
    }


    /**
     * Sets the isFeat value for this GameClientDbDTO.
     * 
     * @param isFeat
     */
    public void setIsFeat(boolean isFeat) {
        this.isFeat = isFeat;
    }


    /**
     * Gets the RTP value for this GameClientDbDTO.
     * 
     * @return RTP
     */
    public java.math.BigDecimal getRTP() {
        return RTP;
    }


    /**
     * Sets the RTP value for this GameClientDbDTO.
     * 
     * @param RTP
     */
    public void setRTP(java.math.BigDecimal RTP) {
        this.RTP = RTP;
    }


    /**
     * Gets the reportName value for this GameClientDbDTO.
     * 
     * @return reportName
     */
    public java.lang.String getReportName() {
        return reportName;
    }


    /**
     * Sets the reportName value for this GameClientDbDTO.
     * 
     * @param reportName
     */
    public void setReportName(java.lang.String reportName) {
        this.reportName = reportName;
    }


    /**
     * Gets the translatedNames value for this GameClientDbDTO.
     * 
     * @return translatedNames
     */
    public com.hy.pull.common.util.game.hb.GameTranslationDTO[] getTranslatedNames() {
        return translatedNames;
    }


    /**
     * Sets the translatedNames value for this GameClientDbDTO.
     * 
     * @param translatedNames
     */
    public void setTranslatedNames(com.hy.pull.common.util.game.hb.GameTranslationDTO[] translatedNames) {
        this.translatedNames = translatedNames;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GameClientDbDTO)) return false;
        GameClientDbDTO other = (GameClientDbDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.brandGameId==null && other.getBrandGameId()==null) || 
             (this.brandGameId!=null &&
              this.brandGameId.equals(other.getBrandGameId()))) &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.keyName==null && other.getKeyName()==null) || 
             (this.keyName!=null &&
              this.keyName.equals(other.getKeyName()))) &&
            this.isNew == other.isIsNew() &&
            ((this.dtAdded==null && other.getDtAdded()==null) || 
             (this.dtAdded!=null &&
              this.dtAdded.equals(other.getDtAdded()))) &&
            ((this.dtUpdated==null && other.getDtUpdated()==null) || 
             (this.dtUpdated!=null &&
              this.dtUpdated.equals(other.getDtUpdated()))) &&
            this.gameTypeId == other.getGameTypeId() &&
            this.releaseStatusId == other.getReleaseStatusId() &&
            ((this.gameTypeName==null && other.getGameTypeName()==null) || 
             (this.gameTypeName!=null &&
              this.gameTypeName.equals(other.getGameTypeName()))) &&
            this.mobileCapable == other.isMobileCapable() &&
            this.mobiPlatformId == other.getMobiPlatformId() &&
            this.webPlatformId == other.getWebPlatformId() &&
            ((this.gameTypeDisplayName==null && other.getGameTypeDisplayName()==null) || 
             (this.gameTypeDisplayName!=null &&
              this.gameTypeDisplayName.equals(other.getGameTypeDisplayName()))) &&
            this.baseGameTypeId == other.getBaseGameTypeId() &&
            this.exProv == other.isExProv() &&
            ((this.fileName==null && other.getFileName()==null) || 
             (this.fileName!=null &&
              this.fileName.equals(other.getFileName()))) &&
            ((this.groupName==null && other.getGroupName()==null) || 
             (this.groupName!=null &&
              this.groupName.equals(other.getGroupName()))) &&
            ((this.lineDesc==null && other.getLineDesc()==null) || 
             (this.lineDesc!=null &&
              this.lineDesc.equals(other.getLineDesc()))) &&
            this.isFeat == other.isIsFeat() &&
            ((this.RTP==null && other.getRTP()==null) || 
             (this.RTP!=null &&
              this.RTP.equals(other.getRTP()))) &&
            ((this.reportName==null && other.getReportName()==null) || 
             (this.reportName!=null &&
              this.reportName.equals(other.getReportName()))) &&
            ((this.translatedNames==null && other.getTranslatedNames()==null) || 
             (this.translatedNames!=null &&
              java.util.Arrays.equals(this.translatedNames, other.getTranslatedNames())));
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
        if (getBrandGameId() != null) {
            _hashCode += getBrandGameId().hashCode();
        }
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getKeyName() != null) {
            _hashCode += getKeyName().hashCode();
        }
        _hashCode += (isIsNew() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getDtAdded() != null) {
            _hashCode += getDtAdded().hashCode();
        }
        if (getDtUpdated() != null) {
            _hashCode += getDtUpdated().hashCode();
        }
        _hashCode += getGameTypeId();
        _hashCode += getReleaseStatusId();
        if (getGameTypeName() != null) {
            _hashCode += getGameTypeName().hashCode();
        }
        _hashCode += (isMobileCapable() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += getMobiPlatformId();
        _hashCode += getWebPlatformId();
        if (getGameTypeDisplayName() != null) {
            _hashCode += getGameTypeDisplayName().hashCode();
        }
        _hashCode += getBaseGameTypeId();
        _hashCode += (isExProv() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getFileName() != null) {
            _hashCode += getFileName().hashCode();
        }
        if (getGroupName() != null) {
            _hashCode += getGroupName().hashCode();
        }
        if (getLineDesc() != null) {
            _hashCode += getLineDesc().hashCode();
        }
        _hashCode += (isIsFeat() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getRTP() != null) {
            _hashCode += getRTP().hashCode();
        }
        if (getReportName() != null) {
            _hashCode += getReportName().hashCode();
        }
        if (getTranslatedNames() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTranslatedNames());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTranslatedNames(), i);
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
        new org.apache.axis.description.TypeDesc(GameClientDbDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameClientDbDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("brandGameId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "BrandGameId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "Name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("keyName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "KeyName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isNew");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "IsNew"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dtAdded");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "DtAdded"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dtUpdated");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "DtUpdated"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("gameTypeId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameTypeId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("releaseStatusId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "ReleaseStatusId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("gameTypeName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameTypeName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mobileCapable");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "MobileCapable"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mobiPlatformId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "MobiPlatformId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("webPlatformId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "WebPlatformId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("gameTypeDisplayName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameTypeDisplayName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("baseGameTypeId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "BaseGameTypeId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("exProv");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "ExProv"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fileName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "FileName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("groupName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GroupName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lineDesc");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "LineDesc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isFeat");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "IsFeat"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("RTP");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "RTP"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reportName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "ReportName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("translatedNames");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "TranslatedNames"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameTranslationDTO"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameTranslationDTO"));
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
