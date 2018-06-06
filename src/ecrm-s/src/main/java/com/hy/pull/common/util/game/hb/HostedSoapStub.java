/**
 * HostedSoapStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hy.pull.common.util.game.hb;

public class HostedSoapStub extends org.apache.axis.client.Stub implements com.hy.pull.common.util.game.hb.HostedSoap {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[40];
        _initOperationDesc1();
        _initOperationDesc2();
        _initOperationDesc3();
        _initOperationDesc4();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetBonusAvailablePlayer");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.oxypite.com/", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.oxypite.com/", "BonusAvailablePlayerRequest"), com.hy.pull.common.util.game.hb.BonusAvailablePlayerRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "ArrayOfCouponInfoDTO"));
        oper.setReturnClass(com.hy.pull.common.util.game.hb.CouponInfoDTO[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GetBonusAvailablePlayerResult"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "CouponInfoDTO"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ApplyBonusToPlayer");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.oxypite.com/", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.oxypite.com/", "ApplyBonusToPlayerRequest"), com.hy.pull.common.util.game.hb.ApplyBonusToPlayerRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "CouponResponseMessage"));
        oper.setReturnClass(com.hy.pull.common.util.game.hb.CouponResponseMessage.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "couponresponse"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetBonusBalancesForPlayer");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.oxypite.com/", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.oxypite.com/", "BonusGenericPlayerRequest"), com.hy.pull.common.util.game.hb.BonusGenericPlayerRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "ArrayOfBonusBalancesDTO"));
        oper.setReturnClass(com.hy.pull.common.util.game.hb.BonusBalancesDTO[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GetBonusBalancesForPlayerResult"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "BonusBalancesDTO"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("SetPlayerBonusBalanceActive");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.oxypite.com/", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.oxypite.com/", "SetBonusBalanceActiveRequest"), com.hy.pull.common.util.game.hb.SetBonusBalanceActiveRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "ToggleBonusBalanceResponse"));
        oper.setReturnClass(com.hy.pull.common.util.game.hb.ToggleBonusBalanceResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "SetPlayerBonusBalanceActiveResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("DeletePlayerBonusBalance");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.oxypite.com/", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.oxypite.com/", "DeleteBonusBalanceRequest"), com.hy.pull.common.util.game.hb.DeleteBonusBalanceRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "ToggleBonusBalanceResponse"));
        oper.setReturnClass(com.hy.pull.common.util.game.hb.ToggleBonusBalanceResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "DeletePlayerBonusBalanceResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("CreateAndApplyBonus");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.oxypite.com/", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.oxypite.com/", "CreateBonusAndApplyRequest"), com.hy.pull.common.util.game.hb.CreateBonusAndApplyRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "CreateAndApplyBonusResponse"));
        oper.setReturnClass(com.hy.pull.common.util.game.hb.CreateAndApplyBonusResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "CreateAndApplyBonusResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetGameTypes");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.oxypite.com/", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameTypeRequest"), com.hy.pull.common.util.game.hb.GameTypeRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameTypeResponse"));
        oper.setReturnClass(com.hy.pull.common.util.game.hb.GameTypeResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GetGameTypesResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetGames");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.oxypite.com/", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameRequest"), com.hy.pull.common.util.game.hb.GameRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameResponse"));
        oper.setReturnClass(com.hy.pull.common.util.game.hb.GameResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GetGamesResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[7] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetGamesInMenuOnly");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.oxypite.com/", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameRequest"), com.hy.pull.common.util.game.hb.GameRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameResponse"));
        oper.setReturnClass(com.hy.pull.common.util.game.hb.GameResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GetGamesInMenuOnlyResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[8] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetGameDisplay");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.oxypite.com/", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameDisplayRequest"), com.hy.pull.common.util.game.hb.GameDisplayRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameDisplayResponse"));
        oper.setReturnClass(com.hy.pull.common.util.game.hb.GameDisplayResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GetGameDisplayResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[9] = oper;

    }

    private static void _initOperationDesc2(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetJackpots");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.oxypite.com/", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.oxypite.com/", "JackpotInfoRequest"), com.hy.pull.common.util.game.hb.JackpotInfoRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "ArrayOfJackpotInfoDTO"));
        oper.setReturnClass(com.hy.pull.common.util.game.hb.JackpotInfoDTO[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GetJackpotsResult"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "JackpotInfoDTO"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[10] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetJackpotGameLink");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.oxypite.com/", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.oxypite.com/", "JackpotInfoRequest"), com.hy.pull.common.util.game.hb.JackpotInfoRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "ArrayOfJackpotGameLinkInfoDTO"));
        oper.setReturnClass(com.hy.pull.common.util.game.hb.JackpotGameLinkInfoDTO[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GetJackpotGameLinkResult"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "JackpotGameLinkInfoDTO"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[11] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetAllJackpotsInAllBrands");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.oxypite.com/", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.oxypite.com/", "JackpotInfoRequest"), com.hy.pull.common.util.game.hb.JackpotInfoRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "ArrayOfJackpotInfoDTO"));
        oper.setReturnClass(com.hy.pull.common.util.game.hb.JackpotInfoDTO[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GetAllJackpotsInAllBrandsResult"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "JackpotInfoDTO"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[12] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetJackpotGameLinkInAllBrands");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.oxypite.com/", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.oxypite.com/", "JackpotInfoRequest"), com.hy.pull.common.util.game.hb.JackpotInfoRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "ArrayOfJackpotGameLinkInfoDTO"));
        oper.setReturnClass(com.hy.pull.common.util.game.hb.JackpotGameLinkInfoDTO[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GetJackpotGameLinkInAllBrandsResult"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "JackpotGameLinkInfoDTO"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[13] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ReportPlayerStakePayout");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.oxypite.com/", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.oxypite.com/", "ReportRequest"), com.hy.pull.common.util.game.hb.ReportRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "ArrayOfPlayerStakePayoutDTO"));
        oper.setReturnClass(com.hy.pull.common.util.game.hb.PlayerStakePayoutDTO[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "ReportPlayerStakePayoutResult"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "PlayerStakePayoutDTO"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[14] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetPlayerGameTransactions");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.oxypite.com/", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.oxypite.com/", "PlayerReportRequest"), com.hy.pull.common.util.game.hb.PlayerReportRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "ArrayOfPlayerGameTransactionsDTO"));
        oper.setReturnClass(com.hy.pull.common.util.game.hb.PlayerGameTransactionsDTO[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GetPlayerGameTransactionsResult"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "PlayerGameTransactionsDTO"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[15] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetPlayerTransferTransactions");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.oxypite.com/", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.oxypite.com/", "PlayerReportRequest"), com.hy.pull.common.util.game.hb.PlayerReportRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "ArrayOfPlayerTransferTransactionsDTO"));
        oper.setReturnClass(com.hy.pull.common.util.game.hb.PlayerTransferTransactionsDTO[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GetPlayerTransferTransactionsResult"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "PlayerTransferTransactionsDTO"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[16] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetBrandTransferTransactions");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.oxypite.com/", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.oxypite.com/", "ReportRequest"), com.hy.pull.common.util.game.hb.ReportRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "ArrayOfPlayerTransferTransactionsDTO"));
        oper.setReturnClass(com.hy.pull.common.util.game.hb.PlayerTransferTransactionsDTO[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GetBrandTransferTransactionsResult"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "PlayerTransferTransactionsDTO"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[17] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetGroupTransferTransactions");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.oxypite.com/", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.oxypite.com/", "ReportRequest"), com.hy.pull.common.util.game.hb.ReportRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "ArrayOfPlayerTransferTransactionsDTO"));
        oper.setReturnClass(com.hy.pull.common.util.game.hb.PlayerTransferTransactionsDTO[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GetGroupTransferTransactionsResult"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "PlayerTransferTransactionsDTO"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[18] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetPlayerGameResults");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.oxypite.com/", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.oxypite.com/", "PlayerReportRequest"), com.hy.pull.common.util.game.hb.PlayerReportRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "ArrayOfPlayerGameResultsDTO"));
        oper.setReturnClass(com.hy.pull.common.util.game.hb.PlayerGameResultsDTO[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GetPlayerGameResultsResult"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "PlayerGameResultsDTO"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[19] = oper;

    }

    private static void _initOperationDesc3(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetBrandGameResults");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.oxypite.com/", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.oxypite.com/", "ReportRequest"), com.hy.pull.common.util.game.hb.ReportRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "ArrayOfPlayerGameResultsDTO"));
        oper.setReturnClass(com.hy.pull.common.util.game.hb.PlayerGameResultsDTO[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GetBrandGameResultsResult"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "PlayerGameResultsDTO"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[20] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetBrandCompletedGameResults");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.oxypite.com/", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.oxypite.com/", "ReportRequest"), com.hy.pull.common.util.game.hb.ReportRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "ArrayOfPlayerCompletedGamesDTO"));
        oper.setReturnClass(com.hy.pull.common.util.game.hb.PlayerCompletedGamesDTO[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GetBrandCompletedGameResultsResult"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "PlayerCompletedGamesDTO"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[21] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetGroupCompletedGameResults");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.oxypite.com/", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.oxypite.com/", "ReportRequest"), com.hy.pull.common.util.game.hb.ReportRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "ArrayOfPlayerCompletedGamesDTO"));
        oper.setReturnClass(com.hy.pull.common.util.game.hb.PlayerCompletedGamesDTO[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GetGroupCompletedGameResultsResult"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "PlayerCompletedGamesDTO"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[22] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetPlayerStakePayoutSummary");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.oxypite.com/", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.oxypite.com/", "PlayerReportRequest"), com.hy.pull.common.util.game.hb.PlayerReportRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "PlayerStakePayoutSummaryDTO"));
        oper.setReturnClass(com.hy.pull.common.util.game.hb.PlayerStakePayoutSummaryDTO.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GetPlayerStakePayoutSummaryResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[23] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ReportJackpotContribution");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.oxypite.com/", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.oxypite.com/", "ReportRequest"), com.hy.pull.common.util.game.hb.ReportRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "ArrayOfJackpotContributionRecord"));
        oper.setReturnClass(com.hy.pull.common.util.game.hb.JackpotContributionRecord[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "ReportJackpotContributionResult"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "JackpotContributionRecord"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[24] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ReportJackpotContributionPerGame");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.oxypite.com/", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.oxypite.com/", "ReportRequest"), com.hy.pull.common.util.game.hb.ReportRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "ArrayOfJackpotContributionPerGameRecord"));
        oper.setReturnClass(com.hy.pull.common.util.game.hb.JackpotContributionPerGameRecord[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "ReportJackpotContributionPerGameResult"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "JackpotContributionPerGameRecord"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[25] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ReportDynamic");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.oxypite.com/", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.oxypite.com/", "DynamicReportRequest"), com.hy.pull.common.util.game.hb.DynamicReportRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.oxypite.com/", ">>ReportDynamicResponse>ReportDynamicResult"));
        oper.setReturnClass(com.hy.pull.common.util.game.hb.ReportDynamicResponseReportDynamicResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "ReportDynamicResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[26] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ReportGameOverviewBrand");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.oxypite.com/", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.oxypite.com/", "ReportRequest"), com.hy.pull.common.util.game.hb.ReportRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "ArrayOfGameOverviewRecord"));
        oper.setReturnClass(com.hy.pull.common.util.game.hb.GameOverviewRecord[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "ReportGameOverviewBrandResult"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameOverviewRecord"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[27] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ReportGameOverviewPlayer");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.oxypite.com/", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.oxypite.com/", "PlayerReportRequest"), com.hy.pull.common.util.game.hb.PlayerReportRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "ArrayOfPlayerGameOverviewRecord"));
        oper.setReturnClass(com.hy.pull.common.util.game.hb.PlayerGameOverviewRecord[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "ReportGameOverviewPlayerResult"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "PlayerGameOverviewRecord"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[28] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ReportGameOverviewPerLocation");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.oxypite.com/", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.oxypite.com/", "ReportRequest"), com.hy.pull.common.util.game.hb.ReportRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "ArrayOfGameOverviewPerLocationRecord"));
        oper.setReturnClass(com.hy.pull.common.util.game.hb.GameOverviewPerLocationRecord[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "ReportGameOverviewPerLocationResult"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameOverviewPerLocationRecord"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[29] = oper;

    }

    private static void _initOperationDesc4(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("UpdatePlayerPassword");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.oxypite.com/", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.oxypite.com/", "UpdatePlayerPasswordRequest"), com.hy.pull.common.util.game.hb.UpdatePlayerPasswordRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "UpdatePlayerPasswordResponse"));
        oper.setReturnClass(com.hy.pull.common.util.game.hb.UpdatePlayerPasswordResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "UpdatePlayerPasswordResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[30] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("LoginOrCreatePlayer");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.oxypite.com/", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.oxypite.com/", "LoginOrCreatePlayerRequest"), com.hy.pull.common.util.game.hb.LoginOrCreatePlayerRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "LoginUserResponse"));
        oper.setReturnClass(com.hy.pull.common.util.game.hb.LoginUserResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "LoginOrCreatePlayerResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[31] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("QueryTransfer");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.oxypite.com/", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.oxypite.com/", "QueryTransferRequest"), com.hy.pull.common.util.game.hb.QueryTransferRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "QueryTransferResponse"));
        oper.setReturnClass(com.hy.pull.common.util.game.hb.QueryTransferResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "QueryTransferResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[32] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("QueryPlayer");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.oxypite.com/", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.oxypite.com/", "QueryPlayerRequest"), com.hy.pull.common.util.game.hb.QueryPlayerRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "QueryPlayerResponse"));
        oper.setReturnClass(com.hy.pull.common.util.game.hb.QueryPlayerResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "QueryPlayerResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[33] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("LogoutPlayer");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.oxypite.com/", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.oxypite.com/", "LogoutPlayerRequest"), com.hy.pull.common.util.game.hb.LogoutPlayerRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "LogoutPlayerResponse"));
        oper.setReturnClass(com.hy.pull.common.util.game.hb.LogoutPlayerResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "LogoutPlayerResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[34] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("LogoutThirdPartyPlayer");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.oxypite.com/", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.oxypite.com/", "LogoutThirdPartyPlayerRequest"), com.hy.pull.common.util.game.hb.LogoutThirdPartyPlayerRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "ThirdPartyPlayerLogoutResponse"));
        oper.setReturnClass(com.hy.pull.common.util.game.hb.ThirdPartyPlayerLogoutResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "LogoutThirdPartyPlayerResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[35] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("DepositPlayerMoney");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.oxypite.com/", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.oxypite.com/", "DepositPlayerMoneyRequest"), com.hy.pull.common.util.game.hb.DepositPlayerMoneyRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "MoneyResponse"));
        oper.setReturnClass(com.hy.pull.common.util.game.hb.MoneyResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "DepositPlayerMoneyResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[36] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("WithdrawPlayerMoney");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.oxypite.com/", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.oxypite.com/", "WithdrawPlayerMoneyRequest"), com.hy.pull.common.util.game.hb.WithdrawPlayerMoneyRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "MoneyResponse"));
        oper.setReturnClass(com.hy.pull.common.util.game.hb.MoneyResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "WithdrawPlayerMoneyResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[37] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("LogoutAllPlayersInBrand");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.oxypite.com/", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.oxypite.com/", "LogoutAllPlayersInBrandRequest"), com.hy.pull.common.util.game.hb.LogoutAllPlayersInBrandRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "LogoutAllPlayersInBrandResponse"));
        oper.setReturnClass(com.hy.pull.common.util.game.hb.LogoutAllPlayersInBrandResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "LogoutAllPlayersInBrandResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[38] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("SetMaintenanceMode");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.oxypite.com/", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.oxypite.com/", "MaintenanceModeRequest"), com.hy.pull.common.util.game.hb.MaintenanceModeRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "MaintenanceModeResponse"));
        oper.setReturnClass(com.hy.pull.common.util.game.hb.MaintenanceModeResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "SetMaintenanceModeResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[39] = oper;

    }

    public HostedSoapStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public HostedSoapStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public HostedSoapStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.2");
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
        addBindings0();
        addBindings1();
    }

    private void addBindings0() {
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://microsoft.com/wsdl/types/", "guid");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", ">>ReportDynamicResponse>ReportDynamicResult");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.ReportDynamicResponseReportDynamicResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", ">DepositPlayerMoney");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.DepositPlayerMoney.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", ">DepositPlayerMoneyResponse");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.DepositPlayerMoneyResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", ">LoginOrCreatePlayer");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.LoginOrCreatePlayer.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", ">LoginOrCreatePlayerResponse");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.LoginOrCreatePlayerResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", ">LogoutAllPlayersInBrand");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.LogoutAllPlayersInBrand.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", ">LogoutAllPlayersInBrandResponse");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.LogoutAllPlayersInBrandResponseType4.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", ">LogoutPlayer");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.LogoutPlayer.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", ">LogoutPlayerResponse");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.LogoutPlayerResponseType1.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", ">LogoutThirdPartyPlayer");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.LogoutThirdPartyPlayer.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", ">LogoutThirdPartyPlayerResponse");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.LogoutThirdPartyPlayerResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", ">QueryPlayer");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.QueryPlayer.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", ">QueryPlayerResponse");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.QueryPlayerResponseType5.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", ">QueryTransfer");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.QueryTransfer.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", ">QueryTransferResponse");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.QueryTransferResponseType0.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", ">ReportGameOverviewBrand");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.ReportGameOverviewBrand.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", ">ReportGameOverviewBrandResponse");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.ReportGameOverviewBrandResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", ">ReportGameOverviewPerLocation");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.ReportGameOverviewPerLocation.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", ">ReportGameOverviewPerLocationResponse");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.ReportGameOverviewPerLocationResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", ">ReportGameOverviewPlayer");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.ReportGameOverviewPlayer.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", ">ReportGameOverviewPlayerResponse");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.ReportGameOverviewPlayerResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", ">SetMaintenanceMode");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.SetMaintenanceMode.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", ">SetMaintenanceModeResponse");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.SetMaintenanceModeResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", ">UpdatePlayerPassword");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.UpdatePlayerPassword.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", ">UpdatePlayerPasswordResponse");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.UpdatePlayerPasswordResponseType2.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", ">WithdrawPlayerMoney");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.WithdrawPlayerMoney.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", ">WithdrawPlayerMoneyResponse");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.WithdrawPlayerMoneyResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "ApplyBonusToPlayerRequest");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.ApplyBonusToPlayerRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "ArrayOfBonusBalancesDTO");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.BonusBalancesDTO[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "BonusBalancesDTO");
            qName2 = new javax.xml.namespace.QName("http://ws.oxypite.com/", "BonusBalancesDTO");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "ArrayOfComplexDepositCouponInfo");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.ComplexDepositCouponInfo[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "ComplexDepositCouponInfo");
            qName2 = new javax.xml.namespace.QName("http://ws.oxypite.com/", "ComplexDepositCouponInfo");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "ArrayOfCouponInfoDTO");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.CouponInfoDTO[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "CouponInfoDTO");
            qName2 = new javax.xml.namespace.QName("http://ws.oxypite.com/", "CouponInfoDTO");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "ArrayOfDisplayNode");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.Game[][].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "DisplayNode");
            qName2 = new javax.xml.namespace.QName("http://ws.oxypite.com/", "DisplayNode");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "ArrayOfGameClientDbDTO");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.GameClientDbDTO[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameClientDbDTO");
            qName2 = new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameClientDbDTO");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "ArrayOfGameOverviewPerLocationRecord");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.GameOverviewPerLocationRecord[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameOverviewPerLocationRecord");
            qName2 = new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameOverviewPerLocationRecord");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "ArrayOfGameOverviewRecord");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.GameOverviewRecord[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameOverviewRecord");
            qName2 = new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameOverviewRecord");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "ArrayOfGameTranslationDTO");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.GameTranslationDTO[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameTranslationDTO");
            qName2 = new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameTranslationDTO");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "ArrayOfGameTypeClientDbDTO");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.GameTypeClientDbDTO[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameTypeClientDbDTO");
            qName2 = new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameTypeClientDbDTO");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "ArrayOfGuid");
            cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://microsoft.com/wsdl/types/", "guid");
            qName2 = new javax.xml.namespace.QName("http://ws.oxypite.com/", "guid");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "ArrayOfJackpotContributionPerGameRecord");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.JackpotContributionPerGameRecord[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "JackpotContributionPerGameRecord");
            qName2 = new javax.xml.namespace.QName("http://ws.oxypite.com/", "JackpotContributionPerGameRecord");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "ArrayOfJackpotContributionRecord");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.JackpotContributionRecord[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "JackpotContributionRecord");
            qName2 = new javax.xml.namespace.QName("http://ws.oxypite.com/", "JackpotContributionRecord");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "ArrayOfJackpotGameLinkInfoDTO");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.JackpotGameLinkInfoDTO[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "JackpotGameLinkInfoDTO");
            qName2 = new javax.xml.namespace.QName("http://ws.oxypite.com/", "JackpotGameLinkInfoDTO");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "ArrayOfJackpotInfoDTO");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.JackpotInfoDTO[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "JackpotInfoDTO");
            qName2 = new javax.xml.namespace.QName("http://ws.oxypite.com/", "JackpotInfoDTO");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "ArrayOfJackpotValueDTO");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.JackpotValueDTO[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "JackpotValueDTO");
            qName2 = new javax.xml.namespace.QName("http://ws.oxypite.com/", "JackpotValueDTO");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "ArrayOfPlayerCompletedGamesDTO");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.PlayerCompletedGamesDTO[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "PlayerCompletedGamesDTO");
            qName2 = new javax.xml.namespace.QName("http://ws.oxypite.com/", "PlayerCompletedGamesDTO");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "ArrayOfPlayerGameOverviewRecord");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.PlayerGameOverviewRecord[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "PlayerGameOverviewRecord");
            qName2 = new javax.xml.namespace.QName("http://ws.oxypite.com/", "PlayerGameOverviewRecord");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "ArrayOfPlayerGameResultsDTO");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.PlayerGameResultsDTO[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "PlayerGameResultsDTO");
            qName2 = new javax.xml.namespace.QName("http://ws.oxypite.com/", "PlayerGameResultsDTO");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "ArrayOfPlayerGameTransactionsDTO");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.PlayerGameTransactionsDTO[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "PlayerGameTransactionsDTO");
            qName2 = new javax.xml.namespace.QName("http://ws.oxypite.com/", "PlayerGameTransactionsDTO");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "ArrayOfPlayerStakePayoutDTO");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.PlayerStakePayoutDTO[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "PlayerStakePayoutDTO");
            qName2 = new javax.xml.namespace.QName("http://ws.oxypite.com/", "PlayerStakePayoutDTO");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "ArrayOfPlayerTransferTransactionsDTO");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.PlayerTransferTransactionsDTO[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "PlayerTransferTransactionsDTO");
            qName2 = new javax.xml.namespace.QName("http://ws.oxypite.com/", "PlayerTransferTransactionsDTO");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "ArrayOfString");
            cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string");
            qName2 = new javax.xml.namespace.QName("http://ws.oxypite.com/", "string");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "BaseRequest");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.BaseRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "BaseResponse");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.BaseResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "BonusAvailablePlayerRequest");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.BonusAvailablePlayerRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "BonusBalancesDTO");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.BonusBalancesDTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "BonusGenericPlayerRequest");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.BonusGenericPlayerRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "ComplexDepositCouponInfo");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.ComplexDepositCouponInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "CouponInfoDTO");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.CouponInfoDTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "CouponResponseMessage");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.CouponResponseMessage.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "CreateAndApplyBonusResponse");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.CreateAndApplyBonusResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "CreateBonusAndApplyRequest");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.CreateBonusAndApplyRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "DeleteBonusBalanceRequest");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.DeleteBonusBalanceRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "DepositPlayerMoneyRequest");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.DepositPlayerMoneyRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "DisplayNode");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.Game[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "Game");
            qName2 = new javax.xml.namespace.QName("http://ws.oxypite.com/", "Game");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "DynamicReportRequest");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.DynamicReportRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "Game");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.Game.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameClientDbDTO");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.GameClientDbDTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameDisplayRequest");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.GameDisplayRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameDisplayResponse");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.GameDisplayResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameOverviewPerLocationRecord");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.GameOverviewPerLocationRecord.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameOverviewRecord");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.GameOverviewRecord.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameRequest");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.GameRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameResponse");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.GameResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameTranslationDTO");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.GameTranslationDTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameTypeClientDbDTO");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.GameTypeClientDbDTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameTypeRequest");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.GameTypeRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameTypeResponse");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.GameTypeResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "InfoMessage");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.InfoMessage.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "JackpotContributionPerGameRecord");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.JackpotContributionPerGameRecord.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "JackpotContributionRecord");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.JackpotContributionRecord.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "JackpotGameLinkInfoDTO");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.JackpotGameLinkInfoDTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "JackpotInfoDTO");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.JackpotInfoDTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "JackpotInfoRequest");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.JackpotInfoRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "JackpotValueDTO");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.JackpotValueDTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "LoginOrCreatePlayerRequest");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.LoginOrCreatePlayerRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "LoginUserResponse");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.LoginUserResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "LogoutAllPlayersInBrandRequest");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.LogoutAllPlayersInBrandRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "LogoutAllPlayersInBrandResponse");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.LogoutAllPlayersInBrandResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "LogoutPlayerRequest");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.LogoutPlayerRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "LogoutPlayerResponse");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.LogoutPlayerResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "LogoutThirdPartyPlayerRequest");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.LogoutThirdPartyPlayerRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "MaintenanceModeRequest");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.MaintenanceModeRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "MaintenanceModeResponse");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.MaintenanceModeResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "MoneyResponse");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.MoneyResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "PlayerCompletedGamesDTO");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.PlayerCompletedGamesDTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "PlayerGameOverviewRecord");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.PlayerGameOverviewRecord.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "PlayerGameResultsDTO");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.PlayerGameResultsDTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "PlayerGameTransactionsDTO");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.PlayerGameTransactionsDTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "PlayerReportRequest");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.PlayerReportRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "PlayerStakePayoutDTO");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.PlayerStakePayoutDTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

    }
    private void addBindings1() {
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "PlayerStakePayoutSummaryDTO");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.PlayerStakePayoutSummaryDTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "PlayerTransferTransactionsDTO");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.PlayerTransferTransactionsDTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "QueryPlayerRequest");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.QueryPlayerRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "QueryPlayerResponse");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.QueryPlayerResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "QueryTransferRequest");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.QueryTransferRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "QueryTransferResponse");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.QueryTransferResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "ReportRequest");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.ReportRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "SetBonusBalanceActiveRequest");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.SetBonusBalanceActiveRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "ThirdPartyPlayerLogoutResponse");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.ThirdPartyPlayerLogoutResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "ToggleBonusBalanceResponse");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.ToggleBonusBalanceResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "UpdatePlayerPasswordRequest");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.UpdatePlayerPasswordRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "UpdatePlayerPasswordResponse");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.UpdatePlayerPasswordResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oxypite.com/", "WithdrawPlayerMoneyRequest");
            cachedSerQNames.add(qName);
            cls = com.hy.pull.common.util.game.hb.WithdrawPlayerMoneyRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setEncodingStyle(null);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        java.lang.Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            java.lang.Class sf = (java.lang.Class)
                                 cachedSerFactories.get(i);
                            java.lang.Class df = (java.lang.Class)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                        else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
                            org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)
                                 cachedSerFactories.get(i);
                            org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public com.hy.pull.common.util.game.hb.CouponInfoDTO[] getBonusAvailablePlayer(com.hy.pull.common.util.game.hb.BonusAvailablePlayerRequest req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://ws.oxypite.com/GetBonusAvailablePlayer");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GetBonusAvailablePlayer"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hy.pull.common.util.game.hb.CouponInfoDTO[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hy.pull.common.util.game.hb.CouponInfoDTO[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.hy.pull.common.util.game.hb.CouponInfoDTO[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hy.pull.common.util.game.hb.CouponResponseMessage applyBonusToPlayer(com.hy.pull.common.util.game.hb.ApplyBonusToPlayerRequest req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://ws.oxypite.com/ApplyBonusToPlayer");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "ApplyBonusToPlayer"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hy.pull.common.util.game.hb.CouponResponseMessage) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hy.pull.common.util.game.hb.CouponResponseMessage) org.apache.axis.utils.JavaUtils.convert(_resp, com.hy.pull.common.util.game.hb.CouponResponseMessage.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hy.pull.common.util.game.hb.BonusBalancesDTO[] getBonusBalancesForPlayer(com.hy.pull.common.util.game.hb.BonusGenericPlayerRequest req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://ws.oxypite.com/GetBonusBalancesForPlayer");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GetBonusBalancesForPlayer"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hy.pull.common.util.game.hb.BonusBalancesDTO[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hy.pull.common.util.game.hb.BonusBalancesDTO[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.hy.pull.common.util.game.hb.BonusBalancesDTO[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hy.pull.common.util.game.hb.ToggleBonusBalanceResponse setPlayerBonusBalanceActive(com.hy.pull.common.util.game.hb.SetBonusBalanceActiveRequest req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://ws.oxypite.com/SetPlayerBonusBalanceActive");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "SetPlayerBonusBalanceActive"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hy.pull.common.util.game.hb.ToggleBonusBalanceResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hy.pull.common.util.game.hb.ToggleBonusBalanceResponse) org.apache.axis.utils.JavaUtils.convert(_resp, com.hy.pull.common.util.game.hb.ToggleBonusBalanceResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hy.pull.common.util.game.hb.ToggleBonusBalanceResponse deletePlayerBonusBalance(com.hy.pull.common.util.game.hb.DeleteBonusBalanceRequest req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://ws.oxypite.com/DeletePlayerBonusBalance");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "DeletePlayerBonusBalance"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hy.pull.common.util.game.hb.ToggleBonusBalanceResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hy.pull.common.util.game.hb.ToggleBonusBalanceResponse) org.apache.axis.utils.JavaUtils.convert(_resp, com.hy.pull.common.util.game.hb.ToggleBonusBalanceResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hy.pull.common.util.game.hb.CreateAndApplyBonusResponse createAndApplyBonus(com.hy.pull.common.util.game.hb.CreateBonusAndApplyRequest req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://ws.oxypite.com/CreateAndApplyBonus");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "CreateAndApplyBonus"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hy.pull.common.util.game.hb.CreateAndApplyBonusResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hy.pull.common.util.game.hb.CreateAndApplyBonusResponse) org.apache.axis.utils.JavaUtils.convert(_resp, com.hy.pull.common.util.game.hb.CreateAndApplyBonusResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hy.pull.common.util.game.hb.GameTypeResponse getGameTypes(com.hy.pull.common.util.game.hb.GameTypeRequest req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[6]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://ws.oxypite.com/GetGameTypes");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GetGameTypes"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hy.pull.common.util.game.hb.GameTypeResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hy.pull.common.util.game.hb.GameTypeResponse) org.apache.axis.utils.JavaUtils.convert(_resp, com.hy.pull.common.util.game.hb.GameTypeResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hy.pull.common.util.game.hb.GameResponse getGames(com.hy.pull.common.util.game.hb.GameRequest req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[7]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://ws.oxypite.com/GetGames");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GetGames"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hy.pull.common.util.game.hb.GameResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hy.pull.common.util.game.hb.GameResponse) org.apache.axis.utils.JavaUtils.convert(_resp, com.hy.pull.common.util.game.hb.GameResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hy.pull.common.util.game.hb.GameResponse getGamesInMenuOnly(com.hy.pull.common.util.game.hb.GameRequest req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[8]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://ws.oxypite.com/GetGamesInMenuOnly");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GetGamesInMenuOnly"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hy.pull.common.util.game.hb.GameResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hy.pull.common.util.game.hb.GameResponse) org.apache.axis.utils.JavaUtils.convert(_resp, com.hy.pull.common.util.game.hb.GameResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hy.pull.common.util.game.hb.GameDisplayResponse getGameDisplay(com.hy.pull.common.util.game.hb.GameDisplayRequest req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[9]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://ws.oxypite.com/GetGameDisplay");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GetGameDisplay"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hy.pull.common.util.game.hb.GameDisplayResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hy.pull.common.util.game.hb.GameDisplayResponse) org.apache.axis.utils.JavaUtils.convert(_resp, com.hy.pull.common.util.game.hb.GameDisplayResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hy.pull.common.util.game.hb.JackpotInfoDTO[] getJackpots(com.hy.pull.common.util.game.hb.JackpotInfoRequest req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[10]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://ws.oxypite.com/GetJackpots");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GetJackpots"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hy.pull.common.util.game.hb.JackpotInfoDTO[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hy.pull.common.util.game.hb.JackpotInfoDTO[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.hy.pull.common.util.game.hb.JackpotInfoDTO[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hy.pull.common.util.game.hb.JackpotGameLinkInfoDTO[] getJackpotGameLink(com.hy.pull.common.util.game.hb.JackpotInfoRequest req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[11]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://ws.oxypite.com/GetJackpotGameLink");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GetJackpotGameLink"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hy.pull.common.util.game.hb.JackpotGameLinkInfoDTO[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hy.pull.common.util.game.hb.JackpotGameLinkInfoDTO[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.hy.pull.common.util.game.hb.JackpotGameLinkInfoDTO[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hy.pull.common.util.game.hb.JackpotInfoDTO[] getAllJackpotsInAllBrands(com.hy.pull.common.util.game.hb.JackpotInfoRequest req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[12]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://ws.oxypite.com/GetAllJackpotsInAllBrands");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GetAllJackpotsInAllBrands"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hy.pull.common.util.game.hb.JackpotInfoDTO[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hy.pull.common.util.game.hb.JackpotInfoDTO[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.hy.pull.common.util.game.hb.JackpotInfoDTO[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hy.pull.common.util.game.hb.JackpotGameLinkInfoDTO[] getJackpotGameLinkInAllBrands(com.hy.pull.common.util.game.hb.JackpotInfoRequest req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[13]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://ws.oxypite.com/GetJackpotGameLinkInAllBrands");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GetJackpotGameLinkInAllBrands"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hy.pull.common.util.game.hb.JackpotGameLinkInfoDTO[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hy.pull.common.util.game.hb.JackpotGameLinkInfoDTO[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.hy.pull.common.util.game.hb.JackpotGameLinkInfoDTO[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hy.pull.common.util.game.hb.PlayerStakePayoutDTO[] reportPlayerStakePayout(com.hy.pull.common.util.game.hb.ReportRequest req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[14]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://ws.oxypite.com/ReportPlayerStakePayout");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "ReportPlayerStakePayout"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hy.pull.common.util.game.hb.PlayerStakePayoutDTO[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hy.pull.common.util.game.hb.PlayerStakePayoutDTO[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.hy.pull.common.util.game.hb.PlayerStakePayoutDTO[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hy.pull.common.util.game.hb.PlayerGameTransactionsDTO[] getPlayerGameTransactions(com.hy.pull.common.util.game.hb.PlayerReportRequest req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[15]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://ws.oxypite.com/GetPlayerGameTransactions");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GetPlayerGameTransactions"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hy.pull.common.util.game.hb.PlayerGameTransactionsDTO[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hy.pull.common.util.game.hb.PlayerGameTransactionsDTO[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.hy.pull.common.util.game.hb.PlayerGameTransactionsDTO[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hy.pull.common.util.game.hb.PlayerTransferTransactionsDTO[] getPlayerTransferTransactions(com.hy.pull.common.util.game.hb.PlayerReportRequest req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[16]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://ws.oxypite.com/GetPlayerTransferTransactions");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GetPlayerTransferTransactions"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hy.pull.common.util.game.hb.PlayerTransferTransactionsDTO[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hy.pull.common.util.game.hb.PlayerTransferTransactionsDTO[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.hy.pull.common.util.game.hb.PlayerTransferTransactionsDTO[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hy.pull.common.util.game.hb.PlayerTransferTransactionsDTO[] getBrandTransferTransactions(com.hy.pull.common.util.game.hb.ReportRequest req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[17]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://ws.oxypite.com/GetBrandTransferTransactions");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GetBrandTransferTransactions"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hy.pull.common.util.game.hb.PlayerTransferTransactionsDTO[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hy.pull.common.util.game.hb.PlayerTransferTransactionsDTO[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.hy.pull.common.util.game.hb.PlayerTransferTransactionsDTO[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hy.pull.common.util.game.hb.PlayerTransferTransactionsDTO[] getGroupTransferTransactions(com.hy.pull.common.util.game.hb.ReportRequest req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[18]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://ws.oxypite.com/GetGroupTransferTransactions");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GetGroupTransferTransactions"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hy.pull.common.util.game.hb.PlayerTransferTransactionsDTO[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hy.pull.common.util.game.hb.PlayerTransferTransactionsDTO[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.hy.pull.common.util.game.hb.PlayerTransferTransactionsDTO[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hy.pull.common.util.game.hb.PlayerGameResultsDTO[] getPlayerGameResults(com.hy.pull.common.util.game.hb.PlayerReportRequest req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[19]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://ws.oxypite.com/GetPlayerGameResults");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GetPlayerGameResults"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hy.pull.common.util.game.hb.PlayerGameResultsDTO[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hy.pull.common.util.game.hb.PlayerGameResultsDTO[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.hy.pull.common.util.game.hb.PlayerGameResultsDTO[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hy.pull.common.util.game.hb.PlayerGameResultsDTO[] getBrandGameResults(com.hy.pull.common.util.game.hb.ReportRequest req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[20]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://ws.oxypite.com/GetBrandGameResults");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GetBrandGameResults"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hy.pull.common.util.game.hb.PlayerGameResultsDTO[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hy.pull.common.util.game.hb.PlayerGameResultsDTO[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.hy.pull.common.util.game.hb.PlayerGameResultsDTO[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hy.pull.common.util.game.hb.PlayerCompletedGamesDTO[] getBrandCompletedGameResults(com.hy.pull.common.util.game.hb.ReportRequest req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[21]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://ws.oxypite.com/GetBrandCompletedGameResults");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GetBrandCompletedGameResults"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hy.pull.common.util.game.hb.PlayerCompletedGamesDTO[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hy.pull.common.util.game.hb.PlayerCompletedGamesDTO[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.hy.pull.common.util.game.hb.PlayerCompletedGamesDTO[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hy.pull.common.util.game.hb.PlayerCompletedGamesDTO[] getGroupCompletedGameResults(com.hy.pull.common.util.game.hb.ReportRequest req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[22]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://ws.oxypite.com/GetGroupCompletedGameResults");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GetGroupCompletedGameResults"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hy.pull.common.util.game.hb.PlayerCompletedGamesDTO[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hy.pull.common.util.game.hb.PlayerCompletedGamesDTO[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.hy.pull.common.util.game.hb.PlayerCompletedGamesDTO[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hy.pull.common.util.game.hb.PlayerStakePayoutSummaryDTO getPlayerStakePayoutSummary(com.hy.pull.common.util.game.hb.PlayerReportRequest req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[23]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://ws.oxypite.com/GetPlayerStakePayoutSummary");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GetPlayerStakePayoutSummary"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hy.pull.common.util.game.hb.PlayerStakePayoutSummaryDTO) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hy.pull.common.util.game.hb.PlayerStakePayoutSummaryDTO) org.apache.axis.utils.JavaUtils.convert(_resp, com.hy.pull.common.util.game.hb.PlayerStakePayoutSummaryDTO.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hy.pull.common.util.game.hb.JackpotContributionRecord[] reportJackpotContribution(com.hy.pull.common.util.game.hb.ReportRequest req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[24]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://ws.oxypite.com/ReportJackpotContribution");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "ReportJackpotContribution"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hy.pull.common.util.game.hb.JackpotContributionRecord[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hy.pull.common.util.game.hb.JackpotContributionRecord[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.hy.pull.common.util.game.hb.JackpotContributionRecord[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hy.pull.common.util.game.hb.JackpotContributionPerGameRecord[] reportJackpotContributionPerGame(com.hy.pull.common.util.game.hb.ReportRequest req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[25]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://ws.oxypite.com/ReportJackpotContributionPerGame");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "ReportJackpotContributionPerGame"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hy.pull.common.util.game.hb.JackpotContributionPerGameRecord[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hy.pull.common.util.game.hb.JackpotContributionPerGameRecord[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.hy.pull.common.util.game.hb.JackpotContributionPerGameRecord[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hy.pull.common.util.game.hb.ReportDynamicResponseReportDynamicResult reportDynamic(com.hy.pull.common.util.game.hb.DynamicReportRequest req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[26]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://ws.oxypite.com/ReportDynamic");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "ReportDynamic"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hy.pull.common.util.game.hb.ReportDynamicResponseReportDynamicResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hy.pull.common.util.game.hb.ReportDynamicResponseReportDynamicResult) org.apache.axis.utils.JavaUtils.convert(_resp, com.hy.pull.common.util.game.hb.ReportDynamicResponseReportDynamicResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hy.pull.common.util.game.hb.GameOverviewRecord[] reportGameOverviewBrand(com.hy.pull.common.util.game.hb.ReportRequest req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[27]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://ws.oxypite.com/ReportGameOverviewBrand");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "ReportGameOverviewBrand"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hy.pull.common.util.game.hb.GameOverviewRecord[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hy.pull.common.util.game.hb.GameOverviewRecord[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.hy.pull.common.util.game.hb.GameOverviewRecord[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hy.pull.common.util.game.hb.PlayerGameOverviewRecord[] reportGameOverviewPlayer(com.hy.pull.common.util.game.hb.PlayerReportRequest req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[28]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://ws.oxypite.com/ReportGameOverviewPlayer");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "ReportGameOverviewPlayer"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hy.pull.common.util.game.hb.PlayerGameOverviewRecord[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hy.pull.common.util.game.hb.PlayerGameOverviewRecord[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.hy.pull.common.util.game.hb.PlayerGameOverviewRecord[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hy.pull.common.util.game.hb.GameOverviewPerLocationRecord[] reportGameOverviewPerLocation(com.hy.pull.common.util.game.hb.ReportRequest req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[29]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://ws.oxypite.com/ReportGameOverviewPerLocation");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "ReportGameOverviewPerLocation"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hy.pull.common.util.game.hb.GameOverviewPerLocationRecord[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hy.pull.common.util.game.hb.GameOverviewPerLocationRecord[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.hy.pull.common.util.game.hb.GameOverviewPerLocationRecord[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hy.pull.common.util.game.hb.UpdatePlayerPasswordResponse updatePlayerPassword(com.hy.pull.common.util.game.hb.UpdatePlayerPasswordRequest req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[30]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://ws.oxypite.com/UpdatePlayerPassword");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "UpdatePlayerPassword"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hy.pull.common.util.game.hb.UpdatePlayerPasswordResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hy.pull.common.util.game.hb.UpdatePlayerPasswordResponse) org.apache.axis.utils.JavaUtils.convert(_resp, com.hy.pull.common.util.game.hb.UpdatePlayerPasswordResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hy.pull.common.util.game.hb.LoginUserResponse loginOrCreatePlayer(com.hy.pull.common.util.game.hb.LoginOrCreatePlayerRequest req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[31]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://ws.oxypite.com/LoginOrCreatePlayer");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "LoginOrCreatePlayer"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hy.pull.common.util.game.hb.LoginUserResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hy.pull.common.util.game.hb.LoginUserResponse) org.apache.axis.utils.JavaUtils.convert(_resp, com.hy.pull.common.util.game.hb.LoginUserResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hy.pull.common.util.game.hb.QueryTransferResponse queryTransfer(com.hy.pull.common.util.game.hb.QueryTransferRequest req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[32]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://ws.oxypite.com/QueryTransfer");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "QueryTransfer"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hy.pull.common.util.game.hb.QueryTransferResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hy.pull.common.util.game.hb.QueryTransferResponse) org.apache.axis.utils.JavaUtils.convert(_resp, com.hy.pull.common.util.game.hb.QueryTransferResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hy.pull.common.util.game.hb.QueryPlayerResponse queryPlayer(com.hy.pull.common.util.game.hb.QueryPlayerRequest req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[33]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://ws.oxypite.com/QueryPlayer");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "QueryPlayer"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hy.pull.common.util.game.hb.QueryPlayerResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hy.pull.common.util.game.hb.QueryPlayerResponse) org.apache.axis.utils.JavaUtils.convert(_resp, com.hy.pull.common.util.game.hb.QueryPlayerResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hy.pull.common.util.game.hb.LogoutPlayerResponse logoutPlayer(com.hy.pull.common.util.game.hb.LogoutPlayerRequest req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[34]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://ws.oxypite.com/LogoutPlayer");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "LogoutPlayer"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hy.pull.common.util.game.hb.LogoutPlayerResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hy.pull.common.util.game.hb.LogoutPlayerResponse) org.apache.axis.utils.JavaUtils.convert(_resp, com.hy.pull.common.util.game.hb.LogoutPlayerResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hy.pull.common.util.game.hb.ThirdPartyPlayerLogoutResponse logoutThirdPartyPlayer(com.hy.pull.common.util.game.hb.LogoutThirdPartyPlayerRequest req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[35]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://ws.oxypite.com/LogoutThirdPartyPlayer");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "LogoutThirdPartyPlayer"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hy.pull.common.util.game.hb.ThirdPartyPlayerLogoutResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hy.pull.common.util.game.hb.ThirdPartyPlayerLogoutResponse) org.apache.axis.utils.JavaUtils.convert(_resp, com.hy.pull.common.util.game.hb.ThirdPartyPlayerLogoutResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hy.pull.common.util.game.hb.MoneyResponse depositPlayerMoney(com.hy.pull.common.util.game.hb.DepositPlayerMoneyRequest req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[36]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://ws.oxypite.com/DepositPlayerMoney");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "DepositPlayerMoney"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hy.pull.common.util.game.hb.MoneyResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hy.pull.common.util.game.hb.MoneyResponse) org.apache.axis.utils.JavaUtils.convert(_resp, com.hy.pull.common.util.game.hb.MoneyResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hy.pull.common.util.game.hb.MoneyResponse withdrawPlayerMoney(com.hy.pull.common.util.game.hb.WithdrawPlayerMoneyRequest req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[37]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://ws.oxypite.com/WithdrawPlayerMoney");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "WithdrawPlayerMoney"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hy.pull.common.util.game.hb.MoneyResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hy.pull.common.util.game.hb.MoneyResponse) org.apache.axis.utils.JavaUtils.convert(_resp, com.hy.pull.common.util.game.hb.MoneyResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hy.pull.common.util.game.hb.LogoutAllPlayersInBrandResponse logoutAllPlayersInBrand(com.hy.pull.common.util.game.hb.LogoutAllPlayersInBrandRequest req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[38]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://ws.oxypite.com/LogoutAllPlayersInBrand");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "LogoutAllPlayersInBrand"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hy.pull.common.util.game.hb.LogoutAllPlayersInBrandResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hy.pull.common.util.game.hb.LogoutAllPlayersInBrandResponse) org.apache.axis.utils.JavaUtils.convert(_resp, com.hy.pull.common.util.game.hb.LogoutAllPlayersInBrandResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hy.pull.common.util.game.hb.MaintenanceModeResponse setMaintenanceMode(com.hy.pull.common.util.game.hb.MaintenanceModeRequest req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[39]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://ws.oxypite.com/SetMaintenanceMode");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "SetMaintenanceMode"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hy.pull.common.util.game.hb.MaintenanceModeResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hy.pull.common.util.game.hb.MaintenanceModeResponse) org.apache.axis.utils.JavaUtils.convert(_resp, com.hy.pull.common.util.game.hb.MaintenanceModeResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
