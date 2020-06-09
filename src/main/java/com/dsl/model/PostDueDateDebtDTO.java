package com.dsl.model;

public class PostDueDateDebtDTO {

	private String inPayOff ;
	private String tranDate;
	private String expType; 
	private String loanType;
	private String loanTypeName;
	private String accNo;
	private String cif;
	private String cid;
	private String stuName;
	private String followStudent; 
	private String address1;
	private String address2; 
	private String address3; 
	private String address4; 
	private String POSTAL_CODE;
	private String processDate; 
	private String totalAmt;
	private String exactPreBalance; 
	private String balanceAmt; 
	private String oldInterest; 
	private String newInterest; 
	private String hideInterest; 
	private String accruedInterestTdr; 
	private String stopAccruedInterestTdr; 
	private String oldLateCharge; 
	private String newLateCharge; 
	private String hideLateChargeAmt; 
	private String feeAmt1; 
	private String feeAmt2; 
	private String feeAmt3; 
	private String feeAmt4; 
	private String intPayOff; 
	private String customRateType; 
	private String customRateTypeCode; 
	private String rateTypeCode; 
	private String spreadRate; 
	private String lateChargeMethod; 
	private String lateChargeValue; 
	private String customLateCharge; 
	private String customLateChargeRate; 
	private String lateChargeRate; 
	private String lateChargeSpreadRate; 
	private String balanceCalLateCharge; 
	private String billAmount; 
	private String billDate; 
	private String firstIntDate;
	private String firstPaymentDate;
	private String processCalLateCharge;
	private String lastCalLateCharge;
	private String processCalIntDate;
	private String lastCalIntDate;
	private String lastPaymentDate;
	private String lastPaymentAmount;
	private String balanceBillAmt;
	private String billDueDate;
	private String billDueAmount;
	private String processDueCalIntDate;
	private String suffix;
	private String taxId;
	private String intStatus;
	private String lateChargeStatus;
	private String genFileName;
	private String genFileStatus;
	private String genFileDate;
	private String genFileRemark;
	private String fee;
	private String paymentMethod;
	private String principleDue;
	private String tranAmountDue;
	private String groupFlag;
	private String remark;
	private String noOfLateBill;
	private String lastDueFlag;
	private String loanClassification;
	private String sendLetterRef;
	private String nextBillDate;
	private String nextBillYear;
	private String nextDuePrin;
	private String nextDueInt;
	private String nextBillAmt;
	private String emailFlag;
	private String emailDate;
	private String smsFlag;
	private String smsDate;
	private String email;
	private String telNo;
	private String letterFlag;
	private String birdthDate;
	
	private String password;
	
	private CtrlPaypostSuffixDTO ctrlPaypostSuffixDto;
	
	public String getInPayOff() {
		return inPayOff;
	}
	public void setInPayOff(String inPayOff) {
		this.inPayOff = inPayOff;
	}
	public String getTranDate() {
		return tranDate;
	}
	public void setTranDate(String tranDate) {
		this.tranDate = tranDate;
	}
	public String getExpType() {
		return expType;
	}
	public void setExpType(String expType) {
		this.expType = expType;
	}
	public String getLoanType() {
		return loanType;
	}
	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}
	public String getAccNo() {
		return accNo;
	}
	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}
	public String getCif() {
		return cif;
	}
	public void setCif(String cif) {
		this.cif = cif;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getStuName() {
		return stuName;
	}
	public void setStuName(String stuName) {
		this.stuName = stuName;
	}
	public String getFollowStudent() {
		return followStudent;
	}
	public void setFollowStudent(String followStudent) {
		this.followStudent = followStudent;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getAddress3() {
		return address3;
	}
	public void setAddress3(String address3) {
		this.address3 = address3;
	}
	public String getAddress4() {
		return address4;
	}
	public void setAddress4(String address4) {
		this.address4 = address4;
	}
	public String getPOSTAL_CODE() {
		return POSTAL_CODE;
	}
	public void setPOSTAL_CODE(String pOSTAL_CODE) {
		POSTAL_CODE = pOSTAL_CODE;
	}
	public String getProcessDate() {
		return processDate;
	}
	public void setProcessDate(String processDate) {
		this.processDate = processDate;
	}
	public String getTotalAmt() {
		return totalAmt;
	}
	public void setTotalAmt(String totalAmt) {
		this.totalAmt = totalAmt;
	}
	public String getExactPreBalance() {
		return exactPreBalance;
	}
	public void setExactPreBalance(String exactPreBalance) {
		this.exactPreBalance = exactPreBalance;
	}
	public String getBalanceAmt() {
		return balanceAmt;
	}
	public void setBalanceAmt(String balanceAmt) {
		this.balanceAmt = balanceAmt;
	}
	public String getOldInterest() {
		return oldInterest;
	}
	public void setOldInterest(String oldInterest) {
		this.oldInterest = oldInterest;
	}
	public String getNewInterest() {
		return newInterest;
	}
	public void setNewInterest(String newInterest) {
		this.newInterest = newInterest;
	}
	public String getHideInterest() {
		return hideInterest;
	}
	public void setHideInterest(String hideInterest) {
		this.hideInterest = hideInterest;
	}
	public String getAccruedInterestTdr() {
		return accruedInterestTdr;
	}
	public void setAccruedInterestTdr(String accruedInterestTdr) {
		this.accruedInterestTdr = accruedInterestTdr;
	}
	public String getStopAccruedInterestTdr() {
		return stopAccruedInterestTdr;
	}
	public void setStopAccruedInterestTdr(String stopAccruedInterestTdr) {
		this.stopAccruedInterestTdr = stopAccruedInterestTdr;
	}
	public String getOldLateCharge() {
		return oldLateCharge;
	}
	public void setOldLateCharge(String oldLateCharge) {
		this.oldLateCharge = oldLateCharge;
	}
	public String getNewLateCharge() {
		return newLateCharge;
	}
	public void setNewLateCharge(String newLateCharge) {
		this.newLateCharge = newLateCharge;
	}
	public String getHideLateChargeAmt() {
		return hideLateChargeAmt;
	}
	public void setHideLateChargeAmt(String hideLateChargeAmt) {
		this.hideLateChargeAmt = hideLateChargeAmt;
	}
	public String getFeeAmt1() {
		return feeAmt1;
	}
	public void setFeeAmt1(String feeAmt1) {
		this.feeAmt1 = feeAmt1;
	}
	public String getFeeAmt2() {
		return feeAmt2;
	}
	public void setFeeAmt2(String feeAmt2) {
		this.feeAmt2 = feeAmt2;
	}
	public String getFeeAmt3() {
		return feeAmt3;
	}
	public void setFeeAmt3(String feeAmt3) {
		this.feeAmt3 = feeAmt3;
	}
	public String getFeeAmt4() {
		return feeAmt4;
	}
	public void setFeeAmt4(String feeAmt4) {
		this.feeAmt4 = feeAmt4;
	}
	public String getIntPayOff() {
		return intPayOff;
	}
	public void setIntPayOff(String intPayOff) {
		this.intPayOff = intPayOff;
	}
	public String getCustomRateType() {
		return customRateType;
	}
	public void setCustomRateType(String customRateType) {
		this.customRateType = customRateType;
	}
	public String getCustomRateTypeCode() {
		return customRateTypeCode;
	}
	public void setCustomRateTypeCode(String customRateTypeCode) {
		this.customRateTypeCode = customRateTypeCode;
	}
	public String getRateTypeCode() {
		return rateTypeCode;
	}
	public void setRateTypeCode(String rateTypeCode) {
		this.rateTypeCode = rateTypeCode;
	}
	public String getSpreadRate() {
		return spreadRate;
	}
	public void setSpreadRate(String spreadRate) {
		this.spreadRate = spreadRate;
	}
	public String getLateChargeMethod() {
		return lateChargeMethod;
	}
	public void setLateChargeMethod(String lateChargeMethod) {
		this.lateChargeMethod = lateChargeMethod;
	}
	public String getLateChargeValue() {
		return lateChargeValue;
	}
	public void setLateChargeValue(String lateChargeValue) {
		this.lateChargeValue = lateChargeValue;
	}
	public String getCustomLateCharge() {
		return customLateCharge;
	}
	public void setCustomLateCharge(String customLateCharge) {
		this.customLateCharge = customLateCharge;
	}
	public String getCustomLateChargeRate() {
		return customLateChargeRate;
	}
	public void setCustomLateChargeRate(String customLateChargeRate) {
		this.customLateChargeRate = customLateChargeRate;
	}
	public String getLateChargeRate() {
		return lateChargeRate;
	}
	public void setLateChargeRate(String lateChargeRate) {
		this.lateChargeRate = lateChargeRate;
	}
	public String getLateChargeSpreadRate() {
		return lateChargeSpreadRate;
	}
	public void setLateChargeSpreadRate(String lateChargeSpreadRate) {
		this.lateChargeSpreadRate = lateChargeSpreadRate;
	}
	public String getBalanceCalLateCharge() {
		return balanceCalLateCharge;
	}
	public void setBalanceCalLateCharge(String balanceCalLateCharge) {
		this.balanceCalLateCharge = balanceCalLateCharge;
	}
	public String getBillAmount() {
		return billAmount;
	}
	public void setBillAmount(String billAmount) {
		this.billAmount = billAmount;
	}
	public String getBillDate() {
		return billDate;
	}
	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}
	public String getFirstIntDate() {
		return firstIntDate;
	}
	public void setFirstIntDate(String firstIntDate) {
		this.firstIntDate = firstIntDate;
	}
	public String getFirstPaymentDate() {
		return firstPaymentDate;
	}
	public void setFirstPaymentDate(String firstPaymentDate) {
		this.firstPaymentDate = firstPaymentDate;
	}
	public String getProcessCalLateCharge() {
		return processCalLateCharge;
	}
	public void setProcessCalLateCharge(String processCalLateCharge) {
		this.processCalLateCharge = processCalLateCharge;
	}
	public String getLastCalLateCharge() {
		return lastCalLateCharge;
	}
	public void setLastCalLateCharge(String lastCalLateCharge) {
		this.lastCalLateCharge = lastCalLateCharge;
	}
	public String getProcessCalIntDate() {
		return processCalIntDate;
	}
	public void setProcessCalIntDate(String processCalIntDate) {
		this.processCalIntDate = processCalIntDate;
	}
	public String getLastCalIntDate() {
		return lastCalIntDate;
	}
	public void setLastCalIntDate(String lastCalIntDate) {
		this.lastCalIntDate = lastCalIntDate;
	}
	public String getLastPaymentDate() {
		return lastPaymentDate;
	}
	public void setLastPaymentDate(String lastPaymentDate) {
		this.lastPaymentDate = lastPaymentDate;
	}
	public String getLastPaymentAmount() {
		return lastPaymentAmount;
	}
	public void setLastPaymentAmount(String lastPaymentAmount) {
		this.lastPaymentAmount = lastPaymentAmount;
	}
	public String getBalanceBillAmt() {
		return balanceBillAmt;
	}
	public void setBalanceBillAmt(String balanceBillAmt) {
		this.balanceBillAmt = balanceBillAmt;
	}
	public String getBillDueDate() {
		return billDueDate;
	}
	public void setBillDueDate(String billDueDate) {
		this.billDueDate = billDueDate;
	}
	public String getBillDueAmount() {
		return billDueAmount;
	}
	public void setBillDueAmount(String billDueAmount) {
		this.billDueAmount = billDueAmount;
	}
	public String getProcessDueCalIntDate() {
		return processDueCalIntDate;
	}
	public void setProcessDueCalIntDate(String processDueCalIntDate) {
		this.processDueCalIntDate = processDueCalIntDate;
	}
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	public String getTaxId() {
		return taxId;
	}
	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}
	public String getIntStatus() {
		return intStatus;
	}
	public void setIntStatus(String intStatus) {
		this.intStatus = intStatus;
	}
	public String getLateChargeStatus() {
		return lateChargeStatus;
	}
	public void setLateChargeStatus(String lateChargeStatus) {
		this.lateChargeStatus = lateChargeStatus;
	}
	public String getGenFileName() {
		return genFileName;
	}
	public void setGenFileName(String genFileName) {
		this.genFileName = genFileName;
	}
	public String getGenFileStatus() {
		return genFileStatus;
	}
	public void setGenFileStatus(String genFileStatus) {
		this.genFileStatus = genFileStatus;
	}
	public String getGenFileDate() {
		return genFileDate;
	}
	public void setGenFileDate(String genFileDate) {
		this.genFileDate = genFileDate;
	}
	public String getGenFileRemark() {
		return genFileRemark;
	}
	public void setGenFileRemark(String genFileRemark) {
		this.genFileRemark = genFileRemark;
	}
	public String getFee() {
		return fee;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public String getPrincipleDue() {
		return principleDue;
	}
	public void setPrincipleDue(String principleDue) {
		this.principleDue = principleDue;
	}
	public String getTranAmountDue() {
		return tranAmountDue;
	}
	public void setTranAmountDue(String tranAmountDue) {
		this.tranAmountDue = tranAmountDue;
	}
	public String getGroupFlag() {
		return groupFlag;
	}
	public void setGroupFlag(String groupFlag) {
		this.groupFlag = groupFlag;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getNoOfLateBill() {
		return noOfLateBill;
	}
	public void setNoOfLateBill(String noOfLateBill) {
		this.noOfLateBill = noOfLateBill;
	}
	public String getLastDueFlag() {
		return lastDueFlag;
	}
	public void setLastDueFlag(String lastDueFlag) {
		this.lastDueFlag = lastDueFlag;
	}
	public String getLoanClassification() {
		return loanClassification;
	}
	public void setLoanClassification(String loanClassification) {
		this.loanClassification = loanClassification;
	}
	public String getSendLetterRef() {
		return sendLetterRef;
	}
	public void setSendLetterRef(String sendLetterRef) {
		this.sendLetterRef = sendLetterRef;
	}
	public String getNextBillDate() {
		return nextBillDate;
	}
	public void setNextBillDate(String nextBillDate) {
		this.nextBillDate = nextBillDate;
	}
	public String getNextBillYear() {
		return nextBillYear;
	}
	public void setNextBillYear(String nextBillYear) {
		this.nextBillYear = nextBillYear;
	}
	public String getNextDuePrin() {
		return nextDuePrin;
	}
	public void setNextDuePrin(String nextDuePrin) {
		this.nextDuePrin = nextDuePrin;
	}
	public String getNextDueInt() {
		return nextDueInt;
	}
	public void setNextDueInt(String nextDueInt) {
		this.nextDueInt = nextDueInt;
	}
	public String getNextBillAmt() {
		return nextBillAmt;
	}
	public void setNextBillAmt(String nextBillAmt) {
		this.nextBillAmt = nextBillAmt;
	}
	public String getEmailFlag() {
		return emailFlag;
	}
	public void setEmailFlag(String emailFlag) {
		this.emailFlag = emailFlag;
	}
	public String getEmailDate() {
		return emailDate;
	}
	public void setEmailDate(String emailDate) {
		this.emailDate = emailDate;
	}
	public String getSmsFlag() {
		return smsFlag;
	}
	public void setSmsFlag(String smsFlag) {
		this.smsFlag = smsFlag;
	}
	public String getSmsDate() {
		return smsDate;
	}
	public void setSmsDate(String smsDate) {
		this.smsDate = smsDate;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelNo() {
		return telNo;
	}
	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}
	public String getLetterFlag() {
		return letterFlag;
	}
	public void setLetterFlag(String letterFlag) {
		this.letterFlag = letterFlag;
	}
	public String getBirdthDate() {
		return birdthDate;
	}
	public void setBirdthDate(String birdthDate) {
		this.birdthDate = birdthDate;
	}
	public String getLoanTypeName() {
		return loanTypeName;
	}
	public void setLoanTypeName(String loanTypeName) {
		this.loanTypeName = loanTypeName;
	}
	public CtrlPaypostSuffixDTO getCtrlPaypostSuffixDto() {
		return ctrlPaypostSuffixDto;
	}
	public void setCtrlPaypostSuffixDto(CtrlPaypostSuffixDTO ctrlPaypostSuffixDto) {
		this.ctrlPaypostSuffixDto = ctrlPaypostSuffixDto;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
