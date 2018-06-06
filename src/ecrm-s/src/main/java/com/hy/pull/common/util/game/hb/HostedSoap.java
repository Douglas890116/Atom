/**
 * HostedSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hy.pull.common.util.game.hb;

public interface HostedSoap extends java.rmi.Remote {

    /**
     * Get a list of Coupons/Bonuses available to a player for redemption
     */
    public com.hy.pull.common.util.game.hb.CouponInfoDTO[] getBonusAvailablePlayer(com.hy.pull.common.util.game.hb.BonusAvailablePlayerRequest req) throws java.rmi.RemoteException;

    /**
     * Apply a bonus coupon to a player
     */
    public com.hy.pull.common.util.game.hb.CouponResponseMessage applyBonusToPlayer(com.hy.pull.common.util.game.hb.ApplyBonusToPlayerRequest req) throws java.rmi.RemoteException;

    /**
     * Get a list of the players bonus balances (these are coupons
     * which have been activated on players account and are ready to use)
     */
    public com.hy.pull.common.util.game.hb.BonusBalancesDTO[] getBonusBalancesForPlayer(com.hy.pull.common.util.game.hb.BonusGenericPlayerRequest req) throws java.rmi.RemoteException;

    /**
     * Toggle the provided BonusBalanceId active status.
     */
    public com.hy.pull.common.util.game.hb.ToggleBonusBalanceResponse setPlayerBonusBalanceActive(com.hy.pull.common.util.game.hb.SetBonusBalanceActiveRequest req) throws java.rmi.RemoteException;

    /**
     * Deletes a Bonus Balance
     */
    public com.hy.pull.common.util.game.hb.ToggleBonusBalanceResponse deletePlayerBonusBalance(com.hy.pull.common.util.game.hb.DeleteBonusBalanceRequest req) throws java.rmi.RemoteException;

    /**
     * Create a new Coupon and optionally redeeme it for specified
     * username
     */
    public com.hy.pull.common.util.game.hb.CreateAndApplyBonusResponse createAndApplyBonus(com.hy.pull.common.util.game.hb.CreateBonusAndApplyRequest req) throws java.rmi.RemoteException;

    /**
     * Get a list of Game Types
     */
    public com.hy.pull.common.util.game.hb.GameTypeResponse getGameTypes(com.hy.pull.common.util.game.hb.GameTypeRequest req) throws java.rmi.RemoteException;

    /**
     * Get a list of all available Games for the Brand.
     */
    public com.hy.pull.common.util.game.hb.GameResponse getGames(com.hy.pull.common.util.game.hb.GameRequest req) throws java.rmi.RemoteException;

    /**
     * Get a list of all available Games for the Brand but only if
     * they appear in the Backoffice Game Menu Display [same format as GetGames()]
     */
    public com.hy.pull.common.util.game.hb.GameResponse getGamesInMenuOnly(com.hy.pull.common.util.game.hb.GameRequest req) throws java.rmi.RemoteException;

    /**
     * Get a list of all Games for the Brand in hierarchical menu
     * format structure as per Backoffice Game Menu Display
     */
    public com.hy.pull.common.util.game.hb.GameDisplayResponse getGameDisplay(com.hy.pull.common.util.game.hb.GameDisplayRequest req) throws java.rmi.RemoteException;

    /**
     * Get a detailed list of all Jackpots in the Brand
     */
    public com.hy.pull.common.util.game.hb.JackpotInfoDTO[] getJackpots(com.hy.pull.common.util.game.hb.JackpotInfoRequest req) throws java.rmi.RemoteException;

    /**
     * Get a list of all Jackpots id's in the Brand with a list of
     * linked BrandGameIds. This shows you which Jackpots a specific Game
     * is using.
     */
    public com.hy.pull.common.util.game.hb.JackpotGameLinkInfoDTO[] getJackpotGameLink(com.hy.pull.common.util.game.hb.JackpotInfoRequest req) throws java.rmi.RemoteException;

    /**
     * Get a detailed list of all Jackpots in all the Brands in same
     * Group]
     */
    public com.hy.pull.common.util.game.hb.JackpotInfoDTO[] getAllJackpotsInAllBrands(com.hy.pull.common.util.game.hb.JackpotInfoRequest req) throws java.rmi.RemoteException;

    /**
     * Get a list of all Jackpots id's for all Brands in a Group with
     * a list of linked BrandGameIds
     */
    public com.hy.pull.common.util.game.hb.JackpotGameLinkInfoDTO[] getJackpotGameLinkInAllBrands(com.hy.pull.common.util.game.hb.JackpotInfoRequest req) throws java.rmi.RemoteException;

    /**
     * Get the summarised Total stakes and payouts per player for
     * a Brand during the date range. Winners/Losers report. ONLY completed
     * games. Hours granularity
     */
    public com.hy.pull.common.util.game.hb.PlayerStakePayoutDTO[] reportPlayerStakePayout(com.hy.pull.common.util.game.hb.ReportRequest req) throws java.rmi.RemoteException;

    /**
     * Get individual debit and credit transactions per game for a
     * player in a date range. Seconds granularity.
     */
    public com.hy.pull.common.util.game.hb.PlayerGameTransactionsDTO[] getPlayerGameTransactions(com.hy.pull.common.util.game.hb.PlayerReportRequest req) throws java.rmi.RemoteException;

    /**
     * Get individual money transfers in and out for a player in a
     * date range. Same result format as GetBrandTransferTransactions().
     * Seconds granularity.
     */
    public com.hy.pull.common.util.game.hb.PlayerTransferTransactionsDTO[] getPlayerTransferTransactions(com.hy.pull.common.util.game.hb.PlayerReportRequest req) throws java.rmi.RemoteException;

    /**
     * Get all individual money transfers in and out for the brand
     * in a date range. Same result format as GetPlayerTransferTransactions().
     * Seconds granularity.
     */
    public com.hy.pull.common.util.game.hb.PlayerTransferTransactionsDTO[] getBrandTransferTransactions(com.hy.pull.common.util.game.hb.ReportRequest req) throws java.rmi.RemoteException;

    /**
     * Get all individual money transfers in and out for the GROUP
     * in a date range. Same result format as GetPlayerTransferTransactions().
     * Seconds granularity.
     */
    public com.hy.pull.common.util.game.hb.PlayerTransferTransactionsDTO[] getGroupTransferTransactions(com.hy.pull.common.util.game.hb.ReportRequest req) throws java.rmi.RemoteException;

    /**
     * Get individual game instance results for a player in a date
     * range. INCLUDES incomplete games. Seconds granularity
     */
    public com.hy.pull.common.util.game.hb.PlayerGameResultsDTO[] getPlayerGameResults(com.hy.pull.common.util.game.hb.PlayerReportRequest req) throws java.rmi.RemoteException;

    /**
     * NOTE: Please Use GetBrandCompletedGameResults(). Get up to
     * 10000 records, up to 7 days ago, of individual game instance results
     * for players in a brand in the date range. Includes incomplete games.
     * Seconds granularity
     */
    public com.hy.pull.common.util.game.hb.PlayerGameResultsDTO[] getBrandGameResults(com.hy.pull.common.util.game.hb.ReportRequest req) throws java.rmi.RemoteException;

    /**
     * Get completed game instance results for players in a brand
     * where the Completed Date of the game is in the date range. Seconds
     * granularity
     */
    public com.hy.pull.common.util.game.hb.PlayerCompletedGamesDTO[] getBrandCompletedGameResults(com.hy.pull.common.util.game.hb.ReportRequest req) throws java.rmi.RemoteException;

    /**
     * Get completed game instance results for players in GROUP Wide
     * where the Completed Date of the game is in the date range. The Group
     * will be determined by the brandId requested. Seconds granularity
     */
    public com.hy.pull.common.util.game.hb.PlayerCompletedGamesDTO[] getGroupCompletedGameResults(com.hy.pull.common.util.game.hb.ReportRequest req) throws java.rmi.RemoteException;

    /**
     * Get a single players summed Stake, Payout, Jackpot win (portion
     * of the Payout), Jackpot Contributions in 1 row. INCLUDES incomplete
     * games. Seconds granularity
     */
    public com.hy.pull.common.util.game.hb.PlayerStakePayoutSummaryDTO getPlayerStakePayoutSummary(com.hy.pull.common.util.game.hb.PlayerReportRequest req) throws java.rmi.RemoteException;

    /**
     * Get overall contributions report for each Jackpot in a Brand
     * in the individual funding currency. Hours granularity
     */
    public com.hy.pull.common.util.game.hb.JackpotContributionRecord[] reportJackpotContribution(com.hy.pull.common.util.game.hb.ReportRequest req) throws java.rmi.RemoteException;

    /**
     * Get overall contributions per Game for each Jackpot in a Brand.
     * Hours granularity
     */
    public com.hy.pull.common.util.game.hb.JackpotContributionPerGameRecord[] reportJackpotContributionPerGame(com.hy.pull.common.util.game.hb.ReportRequest req) throws java.rmi.RemoteException;

    /**
     * Get the results from a Dynamic Report configured in the Back
     * Office
     */
    public com.hy.pull.common.util.game.hb.ReportDynamicResponseReportDynamicResult reportDynamic(com.hy.pull.common.util.game.hb.DynamicReportRequest req) throws java.rmi.RemoteException;

    /**
     * Get game overview/summary report for each Game in a Brand.
     * ONLY completed games. Hour granularity.
     */
    public com.hy.pull.common.util.game.hb.GameOverviewRecord[] reportGameOverviewBrand(com.hy.pull.common.util.game.hb.ReportRequest req) throws java.rmi.RemoteException;

    /**
     * Get game overview/summary report for games played by Player
     * in date range. ONLY completed games. Hour granularity.
     */
    public com.hy.pull.common.util.game.hb.PlayerGameOverviewRecord[] reportGameOverviewPlayer(com.hy.pull.common.util.game.hb.PlayerReportRequest req) throws java.rmi.RemoteException;

    /**
     * Get game overview report for each Game in a POS/Kiosk Location
     * in a Brand [For Terminals/Kiosk usage only], Hour granularity.
     */
    public com.hy.pull.common.util.game.hb.GameOverviewPerLocationRecord[] reportGameOverviewPerLocation(com.hy.pull.common.util.game.hb.ReportRequest req) throws java.rmi.RemoteException;

    /**
     * Update player password.
     */
    public com.hy.pull.common.util.game.hb.UpdatePlayerPasswordResponse updatePlayerPassword(com.hy.pull.common.util.game.hb.UpdatePlayerPasswordRequest req) throws java.rmi.RemoteException;

    /**
     * Logs in player and creates/updates Player using provided details.
     * On creation the wallet is created using the currency code provided.
     * This cannot be changed. Returns the Session Token used to launch game.
     */
    public com.hy.pull.common.util.game.hb.LoginUserResponse loginOrCreatePlayer(com.hy.pull.common.util.game.hb.LoginOrCreatePlayerRequest req) throws java.rmi.RemoteException;

    /**
     * Query a Deposit or Withdraw RequestId to get the status
     */
    public com.hy.pull.common.util.game.hb.QueryTransferResponse queryTransfer(com.hy.pull.common.util.game.hb.QueryTransferRequest req) throws java.rmi.RemoteException;

    /**
     * Query player record for current balance
     */
    public com.hy.pull.common.util.game.hb.QueryPlayerResponse queryPlayer(com.hy.pull.common.util.game.hb.QueryPlayerRequest req) throws java.rmi.RemoteException;

    /**
     * Logout a Habanero Wallet Player (Note this will not work for
     * Single/Seamless wallet)
     */
    public com.hy.pull.common.util.game.hb.LogoutPlayerResponse logoutPlayer(com.hy.pull.common.util.game.hb.LogoutPlayerRequest req) throws java.rmi.RemoteException;

    /**
     * Logout a Player using external token for single wallet.
     */
    public com.hy.pull.common.util.game.hb.ThirdPartyPlayerLogoutResponse logoutThirdPartyPlayer(com.hy.pull.common.util.game.hb.LogoutThirdPartyPlayerRequest req) throws java.rmi.RemoteException;

    /**
     * Deposit money into player wallet. If player exists the currency
     * code must match. Otherwise a new player will be created.
     */
    public com.hy.pull.common.util.game.hb.MoneyResponse depositPlayerMoney(com.hy.pull.common.util.game.hb.DepositPlayerMoneyRequest req) throws java.rmi.RemoteException;

    /**
     * Withdraw money from player wallet.
     */
    public com.hy.pull.common.util.game.hb.MoneyResponse withdrawPlayerMoney(com.hy.pull.common.util.game.hb.WithdrawPlayerMoneyRequest req) throws java.rmi.RemoteException;

    /**
     * Logouts out all Thirdparty players in a Brand.
     */
    public com.hy.pull.common.util.game.hb.LogoutAllPlayersInBrandResponse logoutAllPlayersInBrand(com.hy.pull.common.util.game.hb.LogoutAllPlayersInBrandRequest req) throws java.rmi.RemoteException;

    /**
     * Set Maintenance mode ON or OFF for a Group.
     */
    public com.hy.pull.common.util.game.hb.MaintenanceModeResponse setMaintenanceMode(com.hy.pull.common.util.game.hb.MaintenanceModeRequest req) throws java.rmi.RemoteException;
}
