/**
 * CustomerLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.maven.payment.sdpay;

public class CustomerLocator extends org.apache.axis.client.Service implements com.maven.payment.sdpay.Customer {

    public CustomerLocator() {
    }


    public CustomerLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public CustomerLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for CustomerSoap
    private java.lang.String CustomerSoap_address = "https://payout.sdapayapi.com/8001/Customer.asmx";

    public java.lang.String getCustomerSoapAddress() {
        return CustomerSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String CustomerSoapWSDDServiceName = "CustomerSoap";

    public java.lang.String getCustomerSoapWSDDServiceName() {
        return CustomerSoapWSDDServiceName;
    }

    public void setCustomerSoapWSDDServiceName(java.lang.String name) {
        CustomerSoapWSDDServiceName = name;
    }

    public com.maven.payment.sdpay.CustomerSoap getCustomerSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(CustomerSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getCustomerSoap(endpoint);
    }

    public com.maven.payment.sdpay.CustomerSoap getCustomerSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
        	com.maven.payment.sdpay.CustomerSoapStub _stub = new com.maven.payment.sdpay.CustomerSoapStub(portAddress, this);
            _stub.setPortName(getCustomerSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setCustomerSoapEndpointAddress(java.lang.String address) {
        CustomerSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.maven.payment.sdpay.CustomerSoap.class.isAssignableFrom(serviceEndpointInterface)) {
            	com.maven.payment.sdpay.CustomerSoapStub _stub = new com.maven.payment.sdpay.CustomerSoapStub(new java.net.URL(CustomerSoap_address), this);
                _stub.setPortName(getCustomerSoapWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("CustomerSoap".equals(inputPortName)) {
            return getCustomerSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://payout.sdapay.net/", "Customer");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://payout.sdapay.net/", "CustomerSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("CustomerSoap".equals(portName)) {
            setCustomerSoapEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
