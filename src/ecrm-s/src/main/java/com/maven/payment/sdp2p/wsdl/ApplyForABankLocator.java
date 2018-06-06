/**
 * ApplyForABankLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.maven.payment.sdp2p.wsdl;

public class ApplyForABankLocator extends org.apache.axis.client.Service implements com.maven.payment.sdp2p.wsdl.ApplyForABank {

    public ApplyForABankLocator() {
    }


    public ApplyForABankLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ApplyForABankLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ApplyForABankSoap
    private java.lang.String ApplyForABankSoap_address = "https://deposit2.sdapayapi.com/9001/ApplyForABank.asmx";

    public java.lang.String getApplyForABankSoapAddress() {
        return ApplyForABankSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ApplyForABankSoapWSDDServiceName = "ApplyForABankSoap";

    public java.lang.String getApplyForABankSoapWSDDServiceName() {
        return ApplyForABankSoapWSDDServiceName;
    }

    public void setApplyForABankSoapWSDDServiceName(java.lang.String name) {
        ApplyForABankSoapWSDDServiceName = name;
    }

    public com.maven.payment.sdp2p.wsdl.ApplyForABankSoap getApplyForABankSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ApplyForABankSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getApplyForABankSoap(endpoint);
    }

    public com.maven.payment.sdp2p.wsdl.ApplyForABankSoap getApplyForABankSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
        	com.maven.payment.sdp2p.wsdl.ApplyForABankSoapStub _stub = new com.maven.payment.sdp2p.wsdl.ApplyForABankSoapStub(portAddress, this);
            _stub.setPortName(getApplyForABankSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setApplyForABankSoapEndpointAddress(java.lang.String address) {
        ApplyForABankSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.maven.payment.sdp2p.wsdl.ApplyForABankSoap.class.isAssignableFrom(serviceEndpointInterface)) {
            	com.maven.payment.sdp2p.wsdl.ApplyForABankSoapStub _stub = new com.maven.payment.sdp2p.wsdl.ApplyForABankSoapStub(new java.net.URL(ApplyForABankSoap_address), this);
                _stub.setPortName(getApplyForABankSoapWSDDServiceName());
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
        if ("ApplyForABankSoap".equals(inputPortName)) {
            return getApplyForABankSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://112.121.163.98:9001/ApplyForABank.asmx", "ApplyForABank");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://112.121.163.98:9001/ApplyForABank.asmx", "ApplyForABankSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ApplyForABankSoap".equals(portName)) {
            setApplyForABankSoapEndpointAddress(address);
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
