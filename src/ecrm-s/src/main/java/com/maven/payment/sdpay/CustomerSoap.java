/**
 * CustomerSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.maven.payment.sdpay;

public interface CustomerSoap extends java.rmi.Remote {
    public int getFund(java.lang.String loginAccount, java.lang.String getFundInfo) throws java.rmi.RemoteException;
    public int getFundMac(java.lang.String loginAccount, java.lang.String getFundInfo) throws java.rmi.RemoteException;
    public java.lang.String exitTransferInfomationModel(java.lang.String loginAccount, int id) throws java.rmi.RemoteException;
    public java.lang.String exitTransferInfomationModelSerialNumber(java.lang.String loginAccount, java.lang.String serialNumber) throws java.rmi.RemoteException;
    public java.lang.String exitTransferInfomationModelMac(java.lang.String loginAccount, int id) throws java.rmi.RemoteException;
    public java.lang.String exitTransferInfomationModelSerialNumberMac(java.lang.String loginAccount, java.lang.String serialNumber) throws java.rmi.RemoteException;
    public java.lang.String getBalances(java.lang.String cardNum, java.lang.String loginName) throws java.rmi.RemoteException;
}
