package com.maven.payment.sdp2p.wsdl;

public class ApplyForABankSoapProxy implements com.maven.payment.sdp2p.wsdl.ApplyForABankSoap {
  private String _endpoint = null;
  private com.maven.payment.sdp2p.wsdl.ApplyForABankSoap applyForABankSoap = null;
  
  public ApplyForABankSoapProxy() {
    _initApplyForABankSoapProxy();
  }
  
  public ApplyForABankSoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initApplyForABankSoapProxy();
  }
  
  private void _initApplyForABankSoapProxy() {
    try {
      applyForABankSoap = (new com.maven.payment.sdp2p.wsdl.ApplyForABankLocator()).getApplyForABankSoap();
      if (applyForABankSoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)applyForABankSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)applyForABankSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (applyForABankSoap != null)
      ((javax.xml.rpc.Stub)applyForABankSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.maven.payment.sdp2p.wsdl.ApplyForABankSoap getApplyForABankSoap() {
    if (applyForABankSoap == null)
      _initApplyForABankSoapProxy();
    return applyForABankSoap;
  }
  
  public java.lang.String applyBank(java.lang.String loginAccount, java.lang.String getFundInfo) throws java.rmi.RemoteException{
    if (applyForABankSoap == null)
      _initApplyForABankSoapProxy();
    return applyForABankSoap.applyBank(loginAccount, getFundInfo);
  }
  
  public java.lang.String obtainState(java.lang.String loginAccount, int id) throws java.rmi.RemoteException{
    if (applyForABankSoap == null)
      _initApplyForABankSoapProxy();
    return applyForABankSoap.obtainState(loginAccount, id);
  }
  
  
}