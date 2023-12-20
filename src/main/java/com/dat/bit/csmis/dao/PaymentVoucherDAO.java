package com.dat.bit.csmis.dao;

import java.time.LocalDate;
import java.util.List;

import com.dat.bit.csmis.dto.PaymentVoucherDTO;
import com.dat.bit.csmis.entity.Invoice;

public interface PaymentVoucherDAO {

	public List<PaymentVoucherDTO> getActualCount(List<LocalDate> dates);
	
	public List<String> getAllApproverCashier();
	
	public void addNewCashierApprover(String name,String staffId);
	
	public void updateCashierApprover(int id,String name,String staffId);
	
	public void savePaymentVoucher(Invoice invoice);
	
	public boolean checkVoucherDateRange(String startDate, String endDate);
	
}
