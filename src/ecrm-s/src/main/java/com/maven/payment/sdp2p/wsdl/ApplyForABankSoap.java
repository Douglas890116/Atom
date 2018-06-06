/**
 * ApplyForABankSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.maven.payment.sdp2p.wsdl;

public interface ApplyForABankSoap extends java.rmi.Remote {
    public java.lang.String applyBank(java.lang.String loginAccount, java.lang.String getFundInfo) throws java.rmi.RemoteException;
    public java.lang.String obtainState(java.lang.String loginAccount, int id) throws java.rmi.RemoteException;
}
