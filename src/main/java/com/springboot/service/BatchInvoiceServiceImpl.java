package com.springboot.service;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.springboot.model.CtrlPaypostSuffixDTO;
import com.springboot.model.PostDueDateDebtDTO;
import com.springboot.util.StringUtil;
import com.springboot.util.Tools;

@Service
public class BatchInvoiceServiceImpl {
	
	private static final Logger logger = LogManager.getLogger(BatchInvoiceServiceImpl.class);
	
//	@Autowired
//	RestTemplate restTemplate;
//	
//	@Value("${service.generate.qr}")
//	private String qrUrl;
	
	public Map<String,Object> getMapParamBatchInvoice(PostDueDateDebtDTO dto) throws Exception {
		
        Map<String, Object> params = new HashMap<>();
        
        CtrlPaypostSuffixDTO ctrlDto = dto.getCtrlPaypostSuffixDto();
        
    	//set image url
    	this.setImageParam(params , dto);
    	
		params.put("accNo",  dto.getAccNo());
		params.put("lastPaymentDate",  Tools.xChgDateEngToThai(dto.getLastPaymentDate()));
        params.put("billDate", Tools.xChgDateEngToThai(dto.getBillDate()));
        params.put("totalAmt", Tools.dtoa(dto.getTotalAmt() , "#,##0.00")); 
        params.put("balanceAmt", Tools.dtoa(dto.getBalanceAmt() , "#,##0.00")); 
        params.put("tranDate",  Tools.xChgDateEngToThai(dto.getTranDate())); 
        params.put("principleDue", Tools.dtoa(dto.getPrincipleDue() , "#,##0.00")); 
        params.put("balanceBillAmt",  Tools.dtoa(dto.getBalanceBillAmt(), "#,##0.00"));
        params.put("newInterest",  Tools.dtoa(dto.getNewInterest(), "#,##0.00"));
        params.put("newLateCharge", Tools.dtoa(dto.getNewLateCharge() , "#,##0.00"));
        params.put("fee",  Tools.dtoa(dto.getFee(), "#,##0.00")); 
        params.put("billAmount", Tools.dtoa(dto.getBillAmount() , "#,##0.00"));
        params.put("billDate",  Tools.xChgDateEngToThai(dto.getBillDate()));
        params.put("nextDuePrin",  Tools.dtoa(dto.getNextDuePrin(), "#,##0.00"));
        params.put("nextBillDate", Tools.xChgDateEngToThai(dto.getNextBillDate() ));
        params.put("nextDueInt",Tools.dtoa( dto.getNextDueInt() , "#,##0.00"));
    	params.put("cid",  dto.getCid());
        params.put("loanTypeName",  dto.getLoanTypeName());
        params.put("stuName",  dto.getStuName());
        
        params.put("nextYear", Tools.getThaiYear(dto.getNextBillYear()));
        
        String nextDuePrin = ( !StringUtil.isEmpty(dto.getNextDuePrin())? dto.getNextDuePrin() : "0.00");
        nextDuePrin = nextDuePrin.replaceAll(",", "");
        
        String nextDueInt = ( !StringUtil.isEmpty(dto.getNextDueInt())? dto.getNextDueInt(): "0.00");
        nextDueInt = nextDueInt.replaceAll(",", "");
        
        //(nextDuePrin + nextDueInt) / 12
        double payPerMonth = ( Double.parseDouble(nextDuePrin) + Double.parseDouble( nextDueInt) ) / 12 ;
        params.put("payPerMonth",  Tools.dtoa(payPerMonth ,"#,##0.00")); 
        
        String fee = ( !StringUtil.isEmpty(dto.getFee())? dto.getFee() : "0.00");
        fee = fee.replaceAll(",", "");
        
        //(payPerMonth + fee)/12
        double totalPayPerMonth = (Double.parseDouble(fee) + payPerMonth);
        params.put("totalPayPerMonth",  Tools.dtoa(totalPayPerMonth ,"#,##0.00"));
        
        /**
         * barcode data
         */
		String cr = "\r";
		String sBillDate = "";
		String sBillAmt = "0";
		//String barcodeData = "|" + ctrlDto.getTaxId() + ctrlDto.getSuffixCode() + cr 
		String barcodeData = "|" + ctrlDto.getTaxId() + dto.getSuffix() + cr 
				+ dto.getCid() + cr + sBillDate + dto.getAccNo() + cr + sBillAmt;
        params.put("barcode", barcodeData);
        /**
         * end barcode
         */
        logger.info(params);
        params.put("qrtext", barcodeData);
        
        return params;
	}
	
    private void setImageParam(Map<String, Object> params , PostDueDateDebtDTO dto) throws Exception {
    	
    	//logo studentloan
    	BufferedImage logo = ImageIO.read(getClass().getResource("/images/stpic.jpg"));
    	params.put("logo", logo );
    	
    	//qr studentloan
    	BufferedImage qr = ImageIO.read(getClass().getResource("/images/qr.jpg"));
    	params.put("qr", qr );
    	
    	//pay qr studentloan
    	BufferedImage qrpay = ImageIO.read(getClass().getResource("/images/qrpay.jpg"));
    	params.put("qrpay", qrpay );
    	
    	//ktbnetbank-qr-pay-icon
    	BufferedImage ktbnetbankQrPay = ImageIO.read(getClass().getResource("/images/ktbnetbank-qr-pay.jpg"));
    	params.put("ktbnetbankQrPay", ktbnetbankQrPay);
    	
    	//ktb-icon
    	BufferedImage ktbIcon = ImageIO.read(getClass().getResource("/images/ktb-icon.jpg"));
    	params.put("ktbIcon", ktbIcon );

    	//thailandpost-icon
    	BufferedImage thailandpostIcon = ImageIO.read(getClass().getResource("/images/thailandpost-icon.jpg"));
    	params.put("thailandpostIcon", thailandpostIcon);
    	
    	//7-11-icon
    	BufferedImage sevenEveIcon = ImageIO.read(getClass().getResource("/images/7-11-icon.jpg"));
    	params.put("sevenEveIcon", sevenEveIcon);
    	
    	//counter service-icon
    	BufferedImage counterserviceIcon = ImageIO.read(getClass().getResource("/images/counterservice-icon.jpg"));
    	params.put("counterserviceIcon", counterserviceIcon );
    	
    	//bay-icon
    	BufferedImage bayIcon = ImageIO.read(getClass().getResource("/images/bay-icon.jpg"));
    	params.put("bayIcon", bayIcon );
    	
    	//bkb-icon
    	BufferedImage bkbIcon = ImageIO.read(getClass().getResource("/images/bkb-icon.jpg"));
    	params.put("bkbIcon", bkbIcon);
    	
    	//lh-icon
    	BufferedImage lhIcon = ImageIO.read(getClass().getResource("/images/lh-icon.jpg"));
    	params.put("lhIcon", lhIcon);
    	
    	//tmb-icon
    	BufferedImage tmbIcon = ImageIO.read(getClass().getResource("/images/tmb-icon.jpg"));
    	params.put("tmbIcon", tmbIcon);
    	
    	//scb-icon
    	BufferedImage scbIcon = ImageIO.read(getClass().getResource("/images/scb-icon.jpg"));
    	params.put("scbIcon", scbIcon);
    	
    	//baac-icon
    	BufferedImage baacIcon = ImageIO.read(getClass().getResource("/images/baac-icon.jpg"));
    	params.put("baacIcon", baacIcon);
    	
    	//kbank-icon
    	BufferedImage kbankIcon = ImageIO.read(getClass().getResource("/images/kbank-icon.jpg"));
    	params.put("kbankIcon", kbankIcon);
    	
    	//bigc-icon
    	BufferedImage bigcIcon = ImageIO.read(getClass().getResource("/images/bigc.jpg"));
    	params.put("bigcIcon", bigcIcon);
    	
    }
//    
//	private byte[] generateQrImage(PostDueDateDebtDTO dto) {
//		byte[] imgByte = null;
//		try {
//			String body = "{\"payAmount \": "+dto.getBillAmount()+"}";
//			String url = this.qrUrl + "" + dto.getLoanType() + "/" + dto.getAccNo()+"/qr?";
//			
//			
//			HttpEntity<String> request = new HttpEntity<String>(body.toString());
//			ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
//			interceptors.add(new HeaderRequestInterceptor("username", dto.getCid()));
//			interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
//			this.restTemplate.setInterceptors(interceptors);
//			Object obj = this.restTemplate.postForObject(url, request, Object.class, new Object[0]);
//			
//			ObjectMapper oMapper = new ObjectMapper();
//			Map<String,Object> mp = oMapper.convertValue(obj, Map.class);
//			
//			Object objQr = mp.get("payQR");
//			Map<String,Object> qrbyte = oMapper.convertValue(objQr, Map.class);
//					
//			imgByte = Base64.getDecoder().decode(qrbyte.get("content").toString());
//			
//		}catch(Exception e) {
//			logger.error(""+ e.getMessage());
//		}
//		return imgByte;
//	}
	
	
//	public byte[] getDataPaymentQR(PostDueDateDebtDTO dto) throws Exception {
//		ArrayList<Object> alRespData = new ArrayList<>();
//		byte[] qrPayAmount = null;
//		
//		String taxId = dto.getTaxId() ;
//		String suffixCode = dto.getSuffix();
//
//        String billerId = taxId + ""+ suffixCode;
//        String ref1 = Tools.pinFormatToFormal(dto.getCid());
//        String ref2 = dto.getAccNo();
//        String terminalId = "";
//        String merchantId = "";
//        String sBillAmtNoComma = NumberUtil.formatNumberNoComma(Tools.roundUpDouble(new Double(dto.getBillAmount()), 2), null);
//		String sBillAmt = NumberUtil.formatNumber(Tools.roundUpDouble(new Double(dto.getBillAmount()), 2), null);
//		String data = "{ \"billerRequestQR\": {\"amount\":" + sBillAmtNoComma + ",\"merchantId\":\"" + "" + "\", \"billerId\":\""
//				+ taxId + suffixCode + "\",\"ref1\":\""
//				+ Tools.pinFormatToFormal(dto.getCid()) + "\",\"ref2\":\"" + ref2 + "\", \"terminalId\": \"" + ""
//				+ "\"} }";
//		
//		String thaiQR = generateQRTag30Service.generate(billerId, ref1, ref2, terminalId, merchantId, sBillAmtNoComma);
//		qrPayAmount = QRCodeGenerator.getQRCodeImageWithLogo(thaiQR, 700, 700, dto, sBillAmt);
////		qrPayAmount = QRCodeGenerator.getQRCodeImage(thaiQR, 700, 700);
//
//		return qrPayAmount;
//	}

}
