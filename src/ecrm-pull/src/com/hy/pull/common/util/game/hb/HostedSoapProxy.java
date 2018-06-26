package com.hy.pull.common.util.game.hb;

public class HostedSoapProxy implements com.hy.pull.common.util.game.hb.HostedSoap {
  private String _endpoint = null;
  private com.hy.pull.common.util.game.hb.HostedSoap hostedSoap = null;
  
  public HostedSoapProxy() {
    _initHostedSoapProxy();
  }
  
  public HostedSoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initHostedSoapProxy();
  }
  
  private void _initHostedSoapProxy() {
    try {
      hostedSoap = (new com.hy.pull.common.util.game.hb.HostedLocator()).getHostedSoap();
      if (hostedSoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)hostedSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)hostedSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (hostedSoap != null)
      ((javax.xml.rpc.Stub)hostedSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.hy.pull.common.util.game.hb.HostedSoap getHostedSoap() {
    if (hostedSoap == null)
      _initHostedSoapProxy();
    return hostedSoap;
  }
  
  public com.hy.pull.common.util.game.hb.CouponInfoDTO[] getBonusAvailablePlayer(com.hy.pull.common.util.game.hb.BonusAvailablePlayerRequest req) throws java.rmi.RemoteException{
    if (hostedSoap == null)
      _initHostedSoapProxy();
    return hostedSoap.getBonusAvailablePlayer(req);
  }
  
  public com.hy.pull.common.util.game.hb.CouponResponseMessage applyBonusToPlayer(com.hy.pull.common.util.game.hb.ApplyBonusToPlayerRequest req) throws java.rmi.RemoteException{
    if (hostedSoap == null)
      _initHostedSoapProxy();
    return hostedSoap.applyBonusToPlayer(req);
  }
  
  public com.hy.pull.common.util.game.hb.BonusBalancesDTO[] getBonusBalancesForPlayer(com.hy.pull.common.util.game.hb.BonusGenericPlayerRequest req) throws java.rmi.RemoteException{
    if (hostedSoap == null)
      _initHostedSoapProxy();
    return hostedSoap.getBonusBalancesForPlayer(req);
  }
  
  public com.hy.pull.common.util.game.hb.ToggleBonusBalanceResponse setPlayerBonusBalanceActive(com.hy.pull.common.util.game.hb.SetBonusBalanceActiveRequest req) throws java.rmi.RemoteException{
    if (hostedSoap == null)
      _initHostedSoapProxy();
    return hostedSoap.setPlayerBonusBalanceActive(req);
  }
  
  public com.hy.pull.common.util.game.hb.ToggleBonusBalanceResponse deletePlayerBonusBalance(com.hy.pull.common.util.game.hb.DeleteBonusBalanceRequest req) throws java.rmi.RemoteException{
    if (hostedSoap == null)
      _initHostedSoapProxy();
    return hostedSoap.deletePlayerBonusBalance(req);
  }
  
  public com.hy.pull.common.util.game.hb.CreateAndApplyBonusResponse createAndApplyBonus(com.hy.pull.common.util.game.hb.CreateBonusAndApplyRequest req) throws java.rmi.RemoteException{
    if (hostedSoap == null)
      _initHostedSoapProxy();
    return hostedSoap.createAndApplyBonus(req);
  }
  
  public com.hy.pull.common.util.game.hb.GameTypeResponse getGameTypes(com.hy.pull.common.util.game.hb.GameTypeRequest req) throws java.rmi.RemoteException{
    if (hostedSoap == null)
      _initHostedSoapProxy();
    return hostedSoap.getGameTypes(req);
  }
  
  public com.hy.pull.common.util.game.hb.GameResponse getGames(com.hy.pull.common.util.game.hb.GameRequest req) throws java.rmi.RemoteException{
    if (hostedSoap == null)
      _initHostedSoapProxy();
    return hostedSoap.getGames(req);
  }
  
  public com.hy.pull.common.util.game.hb.GameResponse getGamesInMenuOnly(com.hy.pull.common.util.game.hb.GameRequest req) throws java.rmi.RemoteException{
    if (hostedSoap == null)
      _initHostedSoapProxy();
    return hostedSoap.getGamesInMenuOnly(req);
  }
  
  public com.hy.pull.common.util.game.hb.GameDisplayResponse getGameDisplay(com.hy.pull.common.util.game.hb.GameDisplayRequest req) throws java.rmi.RemoteException{
    if (hostedSoap == null)
      _initHostedSoapProxy();
    return hostedSoap.getGameDisplay(req);
  }
  
  public com.hy.pull.common.util.game.hb.JackpotInfoDTO[] getJackpots(com.hy.pull.common.util.game.hb.JackpotInfoRequest req) throws java.rmi.RemoteException{
    if (hostedSoap == null)
      _initHostedSoapProxy();
    return hostedSoap.getJackpots(req);
  }
  
  public com.hy.pull.common.util.game.hb.JackpotGameLinkInfoDTO[] getJackpotGameLink(com.hy.pull.common.util.game.hb.JackpotInfoRequest req) throws java.rmi.RemoteException{
    if (hostedSoap == null)
      _initHostedSoapProxy();
    return hostedSoap.getJackpotGameLink(req);
  }
  
  public com.hy.pull.common.util.game.hb.JackpotInfoDTO[] getAllJackpotsInAllBrands(com.hy.pull.common.util.game.hb.JackpotInfoRequest req) throws java.rmi.RemoteException{
    if (hostedSoap == null)
      _initHostedSoapProxy();
    return hostedSoap.getAllJackpotsInAllBrands(req);
  }
  
  public com.hy.pull.common.util.game.hb.JackpotGameLinkInfoDTO[] getJackpotGameLinkInAllBrands(com.hy.pull.common.util.game.hb.JackpotInfoRequest req) throws java.rmi.RemoteException{
    if (hostedSoap == null)
      _initHostedSoapProxy();
    return hostedSoap.getJackpotGameLinkInAllBrands(req);
  }
  
  public com.hy.pull.common.util.game.hb.PlayerStakePayoutDTO[] reportPlayerStakePayout(com.hy.pull.common.util.game.hb.ReportRequest req) throws java.rmi.RemoteException{
    if (hostedSoap == null)
      _initHostedSoapProxy();
    return hostedSoap.reportPlayerStakePayout(req);
  }
  
  public com.hy.pull.common.util.game.hb.PlayerGameTransactionsDTO[] getPlayerGameTransactions(com.hy.pull.common.util.game.hb.PlayerReportRequest req) throws java.rmi.RemoteException{
    if (hostedSoap == null)
      _initHostedSoapProxy();
    return hostedSoap.getPlayerGameTransactions(req);
  }
  
  public com.hy.pull.common.util.game.hb.PlayerTransferTransactionsDTO[] getPlayerTransferTransactions(com.hy.pull.common.util.game.hb.PlayerReportRequest req) throws java.rmi.RemoteException{
    if (hostedSoap == null)
      _initHostedSoapProxy();
    return hostedSoap.getPlayerTransferTransactions(req);
  }
  
  public com.hy.pull.common.util.game.hb.PlayerTransferTransactionsDTO[] getBrandTransferTransactions(com.hy.pull.common.util.game.hb.ReportRequest req) throws java.rmi.RemoteException{
    if (hostedSoap == null)
      _initHostedSoapProxy();
    return hostedSoap.getBrandTransferTransactions(req);
  }
  
  public com.hy.pull.common.util.game.hb.PlayerTransferTransactionsDTO[] getGroupTransferTransactions(com.hy.pull.common.util.game.hb.ReportRequest req) throws java.rmi.RemoteException{
    if (hostedSoap == null)
      _initHostedSoapProxy();
    return hostedSoap.getGroupTransferTransactions(req);
  }
  
  public com.hy.pull.common.util.game.hb.PlayerGameResultsDTO[] getPlayerGameResults(com.hy.pull.common.util.game.hb.PlayerReportRequest req) throws java.rmi.RemoteException{
    if (hostedSoap == null)
      _initHostedSoapProxy();
    return hostedSoap.getPlayerGameResults(req);
  }
  
  public com.hy.pull.common.util.game.hb.PlayerGameResultsDTO[] getBrandGameResults(com.hy.pull.common.util.game.hb.ReportRequest req) throws java.rmi.RemoteException{
    if (hostedSoap == null)
      _initHostedSoapProxy();
    return hostedSoap.getBrandGameResults(req);
  }
  
  public com.hy.pull.common.util.game.hb.PlayerCompletedGamesDTO[] getBrandCompletedGameResults(com.hy.pull.common.util.game.hb.ReportRequest req) throws java.rmi.RemoteException{
    if (hostedSoap == null)
      _initHostedSoapProxy();
    return hostedSoap.getBrandCompletedGameResults(req);
  }
  
  public com.hy.pull.common.util.game.hb.PlayerCompletedGamesDTO[] getGroupCompletedGameResults(com.hy.pull.common.util.game.hb.ReportRequest req) throws java.rmi.RemoteException{
    if (hostedSoap == null)
      _initHostedSoapProxy();
    return hostedSoap.getGroupCompletedGameResults(req);
  }
  
  public com.hy.pull.common.util.game.hb.PlayerStakePayoutSummaryDTO getPlayerStakePayoutSummary(com.hy.pull.common.util.game.hb.PlayerReportRequest req) throws java.rmi.RemoteException{
    if (hostedSoap == null)
      _initHostedSoapProxy();
    return hostedSoap.getPlayerStakePayoutSummary(req);
  }
  
  public com.hy.pull.common.util.game.hb.JackpotContributionRecord[] reportJackpotContribution(com.hy.pull.common.util.game.hb.ReportRequest req) throws java.rmi.RemoteException{
    if (hostedSoap == null)
      _initHostedSoapProxy();
    return hostedSoap.reportJackpotContribution(req);
  }
  
  public com.hy.pull.common.util.game.hb.JackpotContributionPerGameRecord[] reportJackpotContributionPerGame(com.hy.pull.common.util.game.hb.ReportRequest req) throws java.rmi.RemoteException{
    if (hostedSoap == null)
      _initHostedSoapProxy();
    return hostedSoap.reportJackpotContributionPerGame(req);
  }
  
  public com.hy.pull.common.util.game.hb.ReportDynamicResponseReportDynamicResult reportDynamic(com.hy.pull.common.util.game.hb.DynamicReportRequest req) throws java.rmi.RemoteException{
    if (hostedSoap == null)
      _initHostedSoapProxy();
    return hostedSoap.reportDynamic(req);
  }
  
  public com.hy.pull.common.util.game.hb.GameOverviewRecord[] reportGameOverviewBrand(com.hy.pull.common.util.game.hb.ReportRequest req) throws java.rmi.RemoteException{
    if (hostedSoap == null)
      _initHostedSoapProxy();
    return hostedSoap.reportGameOverviewBrand(req);
  }
  
  public com.hy.pull.common.util.game.hb.PlayerGameOverviewRecord[] reportGameOverviewPlayer(com.hy.pull.common.util.game.hb.PlayerReportRequest req) throws java.rmi.RemoteException{
    if (hostedSoap == null)
      _initHostedSoapProxy();
    return hostedSoap.reportGameOverviewPlayer(req);
  }
  
  public com.hy.pull.common.util.game.hb.GameOverviewPerLocationRecord[] reportGameOverviewPerLocation(com.hy.pull.common.util.game.hb.ReportRequest req) throws java.rmi.RemoteException{
    if (hostedSoap == null)
      _initHostedSoapProxy();
    return hostedSoap.reportGameOverviewPerLocation(req);
  }
  
  public com.hy.pull.common.util.game.hb.UpdatePlayerPasswordResponse updatePlayerPassword(com.hy.pull.common.util.game.hb.UpdatePlayerPasswordRequest req) throws java.rmi.RemoteException{
    if (hostedSoap == null)
      _initHostedSoapProxy();
    return hostedSoap.updatePlayerPassword(req);
  }
  
  public com.hy.pull.common.util.game.hb.LoginUserResponse loginOrCreatePlayer(com.hy.pull.common.util.game.hb.LoginOrCreatePlayerRequest req) throws java.rmi.RemoteException{
    if (hostedSoap == null)
      _initHostedSoapProxy();
    return hostedSoap.loginOrCreatePlayer(req);
  }
  
  public com.hy.pull.common.util.game.hb.QueryTransferResponse queryTransfer(com.hy.pull.common.util.game.hb.QueryTransferRequest req) throws java.rmi.RemoteException{
    if (hostedSoap == null)
      _initHostedSoapProxy();
    return hostedSoap.queryTransfer(req);
  }
  
  public com.hy.pull.common.util.game.hb.QueryPlayerResponse queryPlayer(com.hy.pull.common.util.game.hb.QueryPlayerRequest req) throws java.rmi.RemoteException{
    if (hostedSoap == null)
      _initHostedSoapProxy();
    return hostedSoap.queryPlayer(req);
  }
  
  public com.hy.pull.common.util.game.hb.LogoutPlayerResponse logoutPlayer(com.hy.pull.common.util.game.hb.LogoutPlayerRequest req) throws java.rmi.RemoteException{
    if (hostedSoap == null)
      _initHostedSoapProxy();
    return hostedSoap.logoutPlayer(req);
  }
  
  public com.hy.pull.common.util.game.hb.ThirdPartyPlayerLogoutResponse logoutThirdPartyPlayer(com.hy.pull.common.util.game.hb.LogoutThirdPartyPlayerRequest req) throws java.rmi.RemoteException{
    if (hostedSoap == null)
      _initHostedSoapProxy();
    return hostedSoap.logoutThirdPartyPlayer(req);
  }
  
  public com.hy.pull.common.util.game.hb.MoneyResponse depositPlayerMoney(com.hy.pull.common.util.game.hb.DepositPlayerMoneyRequest req) throws java.rmi.RemoteException{
    if (hostedSoap == null)
      _initHostedSoapProxy();
    return hostedSoap.depositPlayerMoney(req);
  }
  
  public com.hy.pull.common.util.game.hb.MoneyResponse withdrawPlayerMoney(com.hy.pull.common.util.game.hb.WithdrawPlayerMoneyRequest req) throws java.rmi.RemoteException{
    if (hostedSoap == null)
      _initHostedSoapProxy();
    return hostedSoap.withdrawPlayerMoney(req);
  }
  
  public com.hy.pull.common.util.game.hb.LogoutAllPlayersInBrandResponse logoutAllPlayersInBrand(com.hy.pull.common.util.game.hb.LogoutAllPlayersInBrandRequest req) throws java.rmi.RemoteException{
    if (hostedSoap == null)
      _initHostedSoapProxy();
    return hostedSoap.logoutAllPlayersInBrand(req);
  }
  
  public com.hy.pull.common.util.game.hb.MaintenanceModeResponse setMaintenanceMode(com.hy.pull.common.util.game.hb.MaintenanceModeRequest req) throws java.rmi.RemoteException{
    if (hostedSoap == null)
      _initHostedSoapProxy();
    return hostedSoap.setMaintenanceMode(req);
  }
  
  
}