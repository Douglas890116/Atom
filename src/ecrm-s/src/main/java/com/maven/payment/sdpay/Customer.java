/**
 * Customer.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.maven.payment.sdpay;

public interface Customer extends javax.xml.rpc.Service {
	
    public java.lang.String getCustomerSoapAddress();

    public com.maven.payment.sdpay.CustomerSoap getCustomerSoap() throws javax.xml.rpc.ServiceException;

    public com.maven.payment.sdpay.CustomerSoap getCustomerSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
