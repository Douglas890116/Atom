/**
 * LoginOrCreatePlayerRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hy.pull.common.util.game.hb;

public class LoginOrCreatePlayerRequest  extends com.hy.pull.common.util.game.hb.BaseRequest  implements java.io.Serializable {
    private java.lang.String username;

    private java.lang.String password;

    private java.lang.String currencyCode;

    private java.lang.String firstName;

    private java.lang.String lastName;

    private java.lang.String segmentKey;

    private java.lang.String identityNumber;

    private java.lang.String DOB;

    private java.lang.String address1;

    private java.lang.String address2;

    private java.lang.String state;

    private java.lang.String city;

    private java.lang.String postalCode;

    private java.lang.String countryCode;

    private java.lang.String languageCode;

    private java.lang.String gender;

    private java.lang.String emailAddress;

    private java.lang.String telNumber;

    private java.lang.String userAgent;

    private java.lang.Integer playerRank;

    private java.lang.Boolean keepExistingToken;

    public LoginOrCreatePlayerRequest() {
    }

    public LoginOrCreatePlayerRequest(
           java.lang.String brandId,
           java.lang.String APIKey,
           java.lang.String playerHostAddress,
           java.lang.String CFCId,
           java.lang.String locale,
           java.lang.String username,
           java.lang.String password,
           java.lang.String currencyCode,
           java.lang.String firstName,
           java.lang.String lastName,
           java.lang.String segmentKey,
           java.lang.String identityNumber,
           java.lang.String DOB,
           java.lang.String address1,
           java.lang.String address2,
           java.lang.String state,
           java.lang.String city,
           java.lang.String postalCode,
           java.lang.String countryCode,
           java.lang.String languageCode,
           java.lang.String gender,
           java.lang.String emailAddress,
           java.lang.String telNumber,
           java.lang.String userAgent,
           java.lang.Integer playerRank,
           java.lang.Boolean keepExistingToken) {
        super(
            brandId,
            APIKey,
            playerHostAddress,
            CFCId,
            locale);
        this.username = username;
        this.password = password;
        this.currencyCode = currencyCode;
        this.firstName = firstName;
        this.lastName = lastName;
        this.segmentKey = segmentKey;
        this.identityNumber = identityNumber;
        this.DOB = DOB;
        this.address1 = address1;
        this.address2 = address2;
        this.state = state;
        this.city = city;
        this.postalCode = postalCode;
        this.countryCode = countryCode;
        this.languageCode = languageCode;
        this.gender = gender;
        this.emailAddress = emailAddress;
        this.telNumber = telNumber;
        this.userAgent = userAgent;
        this.playerRank = playerRank;
        this.keepExistingToken = keepExistingToken;
    }


    /**
     * Gets the username value for this LoginOrCreatePlayerRequest.
     * 
     * @return username
     */
    public java.lang.String getUsername() {
        return username;
    }


    /**
     * Sets the username value for this LoginOrCreatePlayerRequest.
     * 
     * @param username
     */
    public void setUsername(java.lang.String username) {
        this.username = username;
    }


    /**
     * Gets the password value for this LoginOrCreatePlayerRequest.
     * 
     * @return password
     */
    public java.lang.String getPassword() {
        return password;
    }


    /**
     * Sets the password value for this LoginOrCreatePlayerRequest.
     * 
     * @param password
     */
    public void setPassword(java.lang.String password) {
        this.password = password;
    }


    /**
     * Gets the currencyCode value for this LoginOrCreatePlayerRequest.
     * 
     * @return currencyCode
     */
    public java.lang.String getCurrencyCode() {
        return currencyCode;
    }


    /**
     * Sets the currencyCode value for this LoginOrCreatePlayerRequest.
     * 
     * @param currencyCode
     */
    public void setCurrencyCode(java.lang.String currencyCode) {
        this.currencyCode = currencyCode;
    }


    /**
     * Gets the firstName value for this LoginOrCreatePlayerRequest.
     * 
     * @return firstName
     */
    public java.lang.String getFirstName() {
        return firstName;
    }


    /**
     * Sets the firstName value for this LoginOrCreatePlayerRequest.
     * 
     * @param firstName
     */
    public void setFirstName(java.lang.String firstName) {
        this.firstName = firstName;
    }


    /**
     * Gets the lastName value for this LoginOrCreatePlayerRequest.
     * 
     * @return lastName
     */
    public java.lang.String getLastName() {
        return lastName;
    }


    /**
     * Sets the lastName value for this LoginOrCreatePlayerRequest.
     * 
     * @param lastName
     */
    public void setLastName(java.lang.String lastName) {
        this.lastName = lastName;
    }


    /**
     * Gets the segmentKey value for this LoginOrCreatePlayerRequest.
     * 
     * @return segmentKey
     */
    public java.lang.String getSegmentKey() {
        return segmentKey;
    }


    /**
     * Sets the segmentKey value for this LoginOrCreatePlayerRequest.
     * 
     * @param segmentKey
     */
    public void setSegmentKey(java.lang.String segmentKey) {
        this.segmentKey = segmentKey;
    }


    /**
     * Gets the identityNumber value for this LoginOrCreatePlayerRequest.
     * 
     * @return identityNumber
     */
    public java.lang.String getIdentityNumber() {
        return identityNumber;
    }


    /**
     * Sets the identityNumber value for this LoginOrCreatePlayerRequest.
     * 
     * @param identityNumber
     */
    public void setIdentityNumber(java.lang.String identityNumber) {
        this.identityNumber = identityNumber;
    }


    /**
     * Gets the DOB value for this LoginOrCreatePlayerRequest.
     * 
     * @return DOB
     */
    public java.lang.String getDOB() {
        return DOB;
    }


    /**
     * Sets the DOB value for this LoginOrCreatePlayerRequest.
     * 
     * @param DOB
     */
    public void setDOB(java.lang.String DOB) {
        this.DOB = DOB;
    }


    /**
     * Gets the address1 value for this LoginOrCreatePlayerRequest.
     * 
     * @return address1
     */
    public java.lang.String getAddress1() {
        return address1;
    }


    /**
     * Sets the address1 value for this LoginOrCreatePlayerRequest.
     * 
     * @param address1
     */
    public void setAddress1(java.lang.String address1) {
        this.address1 = address1;
    }


    /**
     * Gets the address2 value for this LoginOrCreatePlayerRequest.
     * 
     * @return address2
     */
    public java.lang.String getAddress2() {
        return address2;
    }


    /**
     * Sets the address2 value for this LoginOrCreatePlayerRequest.
     * 
     * @param address2
     */
    public void setAddress2(java.lang.String address2) {
        this.address2 = address2;
    }


    /**
     * Gets the state value for this LoginOrCreatePlayerRequest.
     * 
     * @return state
     */
    public java.lang.String getState() {
        return state;
    }


    /**
     * Sets the state value for this LoginOrCreatePlayerRequest.
     * 
     * @param state
     */
    public void setState(java.lang.String state) {
        this.state = state;
    }


    /**
     * Gets the city value for this LoginOrCreatePlayerRequest.
     * 
     * @return city
     */
    public java.lang.String getCity() {
        return city;
    }


    /**
     * Sets the city value for this LoginOrCreatePlayerRequest.
     * 
     * @param city
     */
    public void setCity(java.lang.String city) {
        this.city = city;
    }


    /**
     * Gets the postalCode value for this LoginOrCreatePlayerRequest.
     * 
     * @return postalCode
     */
    public java.lang.String getPostalCode() {
        return postalCode;
    }


    /**
     * Sets the postalCode value for this LoginOrCreatePlayerRequest.
     * 
     * @param postalCode
     */
    public void setPostalCode(java.lang.String postalCode) {
        this.postalCode = postalCode;
    }


    /**
     * Gets the countryCode value for this LoginOrCreatePlayerRequest.
     * 
     * @return countryCode
     */
    public java.lang.String getCountryCode() {
        return countryCode;
    }


    /**
     * Sets the countryCode value for this LoginOrCreatePlayerRequest.
     * 
     * @param countryCode
     */
    public void setCountryCode(java.lang.String countryCode) {
        this.countryCode = countryCode;
    }


    /**
     * Gets the languageCode value for this LoginOrCreatePlayerRequest.
     * 
     * @return languageCode
     */
    public java.lang.String getLanguageCode() {
        return languageCode;
    }


    /**
     * Sets the languageCode value for this LoginOrCreatePlayerRequest.
     * 
     * @param languageCode
     */
    public void setLanguageCode(java.lang.String languageCode) {
        this.languageCode = languageCode;
    }


    /**
     * Gets the gender value for this LoginOrCreatePlayerRequest.
     * 
     * @return gender
     */
    public java.lang.String getGender() {
        return gender;
    }


    /**
     * Sets the gender value for this LoginOrCreatePlayerRequest.
     * 
     * @param gender
     */
    public void setGender(java.lang.String gender) {
        this.gender = gender;
    }


    /**
     * Gets the emailAddress value for this LoginOrCreatePlayerRequest.
     * 
     * @return emailAddress
     */
    public java.lang.String getEmailAddress() {
        return emailAddress;
    }


    /**
     * Sets the emailAddress value for this LoginOrCreatePlayerRequest.
     * 
     * @param emailAddress
     */
    public void setEmailAddress(java.lang.String emailAddress) {
        this.emailAddress = emailAddress;
    }


    /**
     * Gets the telNumber value for this LoginOrCreatePlayerRequest.
     * 
     * @return telNumber
     */
    public java.lang.String getTelNumber() {
        return telNumber;
    }


    /**
     * Sets the telNumber value for this LoginOrCreatePlayerRequest.
     * 
     * @param telNumber
     */
    public void setTelNumber(java.lang.String telNumber) {
        this.telNumber = telNumber;
    }


    /**
     * Gets the userAgent value for this LoginOrCreatePlayerRequest.
     * 
     * @return userAgent
     */
    public java.lang.String getUserAgent() {
        return userAgent;
    }


    /**
     * Sets the userAgent value for this LoginOrCreatePlayerRequest.
     * 
     * @param userAgent
     */
    public void setUserAgent(java.lang.String userAgent) {
        this.userAgent = userAgent;
    }


    /**
     * Gets the playerRank value for this LoginOrCreatePlayerRequest.
     * 
     * @return playerRank
     */
    public java.lang.Integer getPlayerRank() {
        return playerRank;
    }


    /**
     * Sets the playerRank value for this LoginOrCreatePlayerRequest.
     * 
     * @param playerRank
     */
    public void setPlayerRank(java.lang.Integer playerRank) {
        this.playerRank = playerRank;
    }


    /**
     * Gets the keepExistingToken value for this LoginOrCreatePlayerRequest.
     * 
     * @return keepExistingToken
     */
    public java.lang.Boolean getKeepExistingToken() {
        return keepExistingToken;
    }


    /**
     * Sets the keepExistingToken value for this LoginOrCreatePlayerRequest.
     * 
     * @param keepExistingToken
     */
    public void setKeepExistingToken(java.lang.Boolean keepExistingToken) {
        this.keepExistingToken = keepExistingToken;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof LoginOrCreatePlayerRequest)) return false;
        LoginOrCreatePlayerRequest other = (LoginOrCreatePlayerRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.username==null && other.getUsername()==null) || 
             (this.username!=null &&
              this.username.equals(other.getUsername()))) &&
            ((this.password==null && other.getPassword()==null) || 
             (this.password!=null &&
              this.password.equals(other.getPassword()))) &&
            ((this.currencyCode==null && other.getCurrencyCode()==null) || 
             (this.currencyCode!=null &&
              this.currencyCode.equals(other.getCurrencyCode()))) &&
            ((this.firstName==null && other.getFirstName()==null) || 
             (this.firstName!=null &&
              this.firstName.equals(other.getFirstName()))) &&
            ((this.lastName==null && other.getLastName()==null) || 
             (this.lastName!=null &&
              this.lastName.equals(other.getLastName()))) &&
            ((this.segmentKey==null && other.getSegmentKey()==null) || 
             (this.segmentKey!=null &&
              this.segmentKey.equals(other.getSegmentKey()))) &&
            ((this.identityNumber==null && other.getIdentityNumber()==null) || 
             (this.identityNumber!=null &&
              this.identityNumber.equals(other.getIdentityNumber()))) &&
            ((this.DOB==null && other.getDOB()==null) || 
             (this.DOB!=null &&
              this.DOB.equals(other.getDOB()))) &&
            ((this.address1==null && other.getAddress1()==null) || 
             (this.address1!=null &&
              this.address1.equals(other.getAddress1()))) &&
            ((this.address2==null && other.getAddress2()==null) || 
             (this.address2!=null &&
              this.address2.equals(other.getAddress2()))) &&
            ((this.state==null && other.getState()==null) || 
             (this.state!=null &&
              this.state.equals(other.getState()))) &&
            ((this.city==null && other.getCity()==null) || 
             (this.city!=null &&
              this.city.equals(other.getCity()))) &&
            ((this.postalCode==null && other.getPostalCode()==null) || 
             (this.postalCode!=null &&
              this.postalCode.equals(other.getPostalCode()))) &&
            ((this.countryCode==null && other.getCountryCode()==null) || 
             (this.countryCode!=null &&
              this.countryCode.equals(other.getCountryCode()))) &&
            ((this.languageCode==null && other.getLanguageCode()==null) || 
             (this.languageCode!=null &&
              this.languageCode.equals(other.getLanguageCode()))) &&
            ((this.gender==null && other.getGender()==null) || 
             (this.gender!=null &&
              this.gender.equals(other.getGender()))) &&
            ((this.emailAddress==null && other.getEmailAddress()==null) || 
             (this.emailAddress!=null &&
              this.emailAddress.equals(other.getEmailAddress()))) &&
            ((this.telNumber==null && other.getTelNumber()==null) || 
             (this.telNumber!=null &&
              this.telNumber.equals(other.getTelNumber()))) &&
            ((this.userAgent==null && other.getUserAgent()==null) || 
             (this.userAgent!=null &&
              this.userAgent.equals(other.getUserAgent()))) &&
            ((this.playerRank==null && other.getPlayerRank()==null) || 
             (this.playerRank!=null &&
              this.playerRank.equals(other.getPlayerRank()))) &&
            ((this.keepExistingToken==null && other.getKeepExistingToken()==null) || 
             (this.keepExistingToken!=null &&
              this.keepExistingToken.equals(other.getKeepExistingToken())));
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
        if (getUsername() != null) {
            _hashCode += getUsername().hashCode();
        }
        if (getPassword() != null) {
            _hashCode += getPassword().hashCode();
        }
        if (getCurrencyCode() != null) {
            _hashCode += getCurrencyCode().hashCode();
        }
        if (getFirstName() != null) {
            _hashCode += getFirstName().hashCode();
        }
        if (getLastName() != null) {
            _hashCode += getLastName().hashCode();
        }
        if (getSegmentKey() != null) {
            _hashCode += getSegmentKey().hashCode();
        }
        if (getIdentityNumber() != null) {
            _hashCode += getIdentityNumber().hashCode();
        }
        if (getDOB() != null) {
            _hashCode += getDOB().hashCode();
        }
        if (getAddress1() != null) {
            _hashCode += getAddress1().hashCode();
        }
        if (getAddress2() != null) {
            _hashCode += getAddress2().hashCode();
        }
        if (getState() != null) {
            _hashCode += getState().hashCode();
        }
        if (getCity() != null) {
            _hashCode += getCity().hashCode();
        }
        if (getPostalCode() != null) {
            _hashCode += getPostalCode().hashCode();
        }
        if (getCountryCode() != null) {
            _hashCode += getCountryCode().hashCode();
        }
        if (getLanguageCode() != null) {
            _hashCode += getLanguageCode().hashCode();
        }
        if (getGender() != null) {
            _hashCode += getGender().hashCode();
        }
        if (getEmailAddress() != null) {
            _hashCode += getEmailAddress().hashCode();
        }
        if (getTelNumber() != null) {
            _hashCode += getTelNumber().hashCode();
        }
        if (getUserAgent() != null) {
            _hashCode += getUserAgent().hashCode();
        }
        if (getPlayerRank() != null) {
            _hashCode += getPlayerRank().hashCode();
        }
        if (getKeepExistingToken() != null) {
            _hashCode += getKeepExistingToken().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(LoginOrCreatePlayerRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "LoginOrCreatePlayerRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("username");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "Username"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("password");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "Password"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("currencyCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "CurrencyCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("firstName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "FirstName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lastName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "LastName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("segmentKey");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "SegmentKey"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("identityNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "IdentityNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("DOB");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "DOB"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("address1");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "Address1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("address2");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "Address2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("state");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "State"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("city");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "City"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("postalCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "PostalCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("countryCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "CountryCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("languageCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "LanguageCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("gender");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "Gender"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("emailAddress");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "EmailAddress"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("telNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "TelNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userAgent");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "UserAgent"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("playerRank");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "PlayerRank"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("keepExistingToken");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "KeepExistingToken"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(true);
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
