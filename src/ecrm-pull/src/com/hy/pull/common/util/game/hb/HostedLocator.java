/**
 * HostedLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hy.pull.common.util.game.hb;

public class HostedLocator extends org.apache.axis.client.Service implements com.hy.pull.common.util.game.hb.Hosted {

    public HostedLocator() {
    }


    public HostedLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public HostedLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for HostedSoap
    private java.lang.String HostedSoap_address = "http://ws-a.insvr.com/hosted.asmx";

    public java.lang.String getHostedSoapAddress() {
        return HostedSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String HostedSoapWSDDServiceName = "HostedSoap";

    public java.lang.String getHostedSoapWSDDServiceName() {
        return HostedSoapWSDDServiceName;
    }

    public void setHostedSoapWSDDServiceName(java.lang.String name) {
        HostedSoapWSDDServiceName = name;
    }

    public com.hy.pull.common.util.game.hb.HostedSoap getHostedSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(HostedSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getHostedSoap(endpoint);
    }

    public com.hy.pull.common.util.game.hb.HostedSoap getHostedSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.hy.pull.common.util.game.hb.HostedSoapStub _stub = new com.hy.pull.common.util.game.hb.HostedSoapStub(portAddress, this);
            _stub.setPortName(getHostedSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setHostedSoapEndpointAddress(java.lang.String address) {
        HostedSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.hy.pull.common.util.game.hb.HostedSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                com.hy.pull.common.util.game.hb.HostedSoapStub _stub = new com.hy.pull.common.util.game.hb.HostedSoapStub(new java.net.URL(HostedSoap_address), this);
                _stub.setPortName(getHostedSoapWSDDServiceName());
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
        if ("HostedSoap".equals(inputPortName)) {
            return getHostedSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://ws.oxypite.com/", "Hosted");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://ws.oxypite.com/", "HostedSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("HostedSoap".equals(portName)) {
            setHostedSoapEndpointAddress(address);
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
