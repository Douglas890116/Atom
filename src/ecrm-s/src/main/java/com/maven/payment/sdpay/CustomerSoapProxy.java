package com.maven.payment.sdpay;

public class CustomerSoapProxy implements com.maven.payment.sdpay.CustomerSoap {
  private String _endpoint = null;
  private com.maven.payment.sdpay.CustomerSoap customerSoap = null;
  
  public CustomerSoapProxy() {
    _initCustomerSoapProxy();
  }
  
  public CustomerSoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initCustomerSoapProxy();
  }
  
  private void _initCustomerSoapProxy() {
    try {
      customerSoap = (new com.maven.payment.sdpay.CustomerLocator()).getCustomerSoap();
      if (customerSoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)customerSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)customerSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (customerSoap != null)
      ((javax.xml.rpc.Stub)customerSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.maven.payment.sdpay.CustomerSoap getCustomerSoap() {
    if (customerSoap == null)
      _initCustomerSoapProxy();
    return customerSoap;
  }
  
  public int getFund(java.lang.String loginAccount, java.lang.String getFundInfo) throws java.rmi.RemoteException{
    if (customerSoap == null)
      _initCustomerSoapProxy();
    return customerSoap.getFund(loginAccount, getFundInfo);
  }
  
  public int getFundMac(java.lang.String loginAccount, java.lang.String getFundInfo) throws java.rmi.RemoteException{
    if (customerSoap == null)
      _initCustomerSoapProxy();
    return customerSoap.getFundMac(loginAccount, getFundInfo);
  }
  
  public java.lang.String exitTransferInfomationModel(java.lang.String loginAccount, int id) throws java.rmi.RemoteException{
    if (customerSoap == null)
      _initCustomerSoapProxy();
    return customerSoap.exitTransferInfomationModel(loginAccount, id);
  }
  
  public java.lang.String exitTransferInfomationModelSerialNumber(java.lang.String loginAccount, java.lang.String serialNumber) throws java.rmi.RemoteException{
    if (customerSoap == null)
      _initCustomerSoapProxy();
    return customerSoap.exitTransferInfomationModelSerialNumber(loginAccount, serialNumber);
  }
  
  public java.lang.String exitTransferInfomationModelMac(java.lang.String loginAccount, int id) throws java.rmi.RemoteException{
    if (customerSoap == null)
      _initCustomerSoapProxy();
    return customerSoap.exitTransferInfomationModelMac(loginAccount, id);
  }
  
  public java.lang.String exitTransferInfomationModelSerialNumberMac(java.lang.String loginAccount, java.lang.String serialNumber) throws java.rmi.RemoteException{
    if (customerSoap == null)
      _initCustomerSoapProxy();
    return customerSoap.exitTransferInfomationModelSerialNumberMac(loginAccount, serialNumber);
  }
  
  public java.lang.String getBalances(java.lang.String cardNum, java.lang.String loginName) throws java.rmi.RemoteException{
    if (customerSoap == null)
      _initCustomerSoapProxy();
    return customerSoap.getBalances(cardNum, loginName);
  }
  
  
}