package com.maven.payment.el;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.maven.payment.PayInterface;
import com.maven.util.RandomString;

public class ELPayMent<M extends ELMerchantConfig, O extends ELOrderConfig> implements PayInterface<M, O> {

	@Override
	public String save(ELMerchantConfig merchant, ELOrderConfig order) throws Exception {
		ELUtil save = new ELUtil();
		return save.getUrl(merchant, order);
	}

	@Override
	public String pay(ELMerchantConfig merchant, ELOrderConfig order) throws Exception {
		ELUtil pay = new ELUtil();
		return pay.doPay(merchant, order);
	}

	public static void main(String[] args) {
		PayInterface<ELMerchantConfig, ELOrderConfig> payment = new ELPayMent<ELMerchantConfig, ELOrderConfig>();
		String platformKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDpVTKFavuizwBQEPSLkqpX6seQ"
				+ "PXFkj9Ry3QCqNn0wy8kijc5tOj0OpThrzyFVIjkBvzwNmZRoNpK2vM7HPWnfkqfF"
				+ "OFG1y27OoF1VWhyz3EaCUDvFDDqzqhrWqNE7hjsYLrbOl6gzILvI7bql1Gzwq3v2"
				+ "VdayS2LvkNqsqd/F7QIDAQAB";
		
		String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDKjCv46ayJtzwLRmSMdtiTHUIH"
				+ "fXNIzbqI+RtE0b0xmOOeX5As7F+o1El6IWD3lC3A2Up3nhlR/48RPL/F04c3hHMj"
				+ "yIrl+G/l/shiBfp7CknxOeaaUpc6CY6qqpGuDx0ELrVvAHOHM3JmEZOwIoX+FyRV"
				+ "klt5vYCHAKomGW7/yQIDAQAB";
		
		String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMqMK/jprIm3PAtG"
				+ "ZIx22JMdQgd9c0jNuoj5G0TRvTGY455fkCzsX6jUSXohYPeULcDZSneeGVH/jxE8"
				+ "v8XThzeEcyPIiuX4b+X+yGIF+nsKSfE55ppSlzoJjqqqka4PHQQutW8Ac4czcmYR"
				+ "k7Aihf4XJFWSW3m9gIcAqiYZbv/JAgMBAAECgYAwXudFa3Cj9tllC+uJS/yOrYcO"
				+ "SNxWP6NTgMINXlVjWdLKCKJiyBRUOlZNz1jb50xB6OKKzHvZhEGDaVGXCdwZbfAt"
				+ "7JPCQbgXMxTeqUZL3KG7k4N4u1GIU3xLOBnWg6FjjOS+RN8bpfxdtcge+igIQmKK"
				+ "AptxOBkadcU56AqXvQJBAPbEL110+wBnYzLeFkAZ9z5hwxYXfKQ21H/LvMCkMb5v"
				+ "OgTWswmJoqeGMbXs8YDEyJY8y5UWSfc+J769jk8uiucCQQDSIGhb8cWmt87R7vIN"
				+ "u6koFYSQqaN3epbzoPYNkXPJHY15q4S75Gecp8oA+NwRzGxGi/egX2jNPQOpXp8Q"
				+ "fvnPAkEAlsM1zPtzfsOrr6BQJ8m1bL992TlwRUPagFmRcIDgg2ChKeOibulQHtm1"
				+ "9VhWFD4l7uQl3WhIuG2ZCsf08huVcQJBAMc5tNigJe9JTQ2ciOFecSaxKCxkurlY"
				+ "gPDjRwdcRjSEEZQvfD0NdPOB3rQHP9icQnis0o66h0o4tFUMgrIIQd8CQHNqAdmA"
				+ "ilKa2clLeiKq6KGsQGqC3HuwnyoSxWf214e2saf0ZC7UJPOwXrsR4GjxaKFb7mjb"
				+ "PvXDESYMCH2EywA=";
		ELMerchantConfig merchant = new ELMerchantConfig();
		merchant.setPartnerId("1725161204105612");
		merchant.setPlatformKey(platformKey);
		merchant.setPublicKey(publicKey);
		merchant.setPrivateKey(privateKey);
		merchant.setPayUrl("http://47.91.251.192:5555/trade/pay/create");
		merchant.setReturnUrl("http://api.hyzonghe.net/TPayment/success");
		merchant.setNotifyUrl("http://api.hyzonghe.net/TPayment/ELCallback");

		BigDecimal ordermount = new BigDecimal("3.0");
		ELOrderConfig order = new ELOrderConfig();
		order.setOrderNo("TEST".concat(RandomString.UUID().substring(4)));
		order.setOrderAmount(ordermount.multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_DOWN).toString());
		order.setOrderCurrency(ELUtil.orderCurrency);
		order.setOrderDatetime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		
		try {
			payment.save(merchant, order);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}