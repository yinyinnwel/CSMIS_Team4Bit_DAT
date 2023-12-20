package com.dat.bit.csmis.service;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dat.bit.csmis.dao.PaymentVoucherDAO;
import com.dat.bit.csmis.dto.PaymentVoucherDTO;
import com.dat.bit.csmis.entity.Invoice;

@Service
public class PaymentVoucherService_Imp implements PaymentVoucherService {
	
	@Autowired
	private PaymentVoucherDAO paymentVoucherDAO;

	@Override
	@Transactional
	public List<PaymentVoucherDTO> getActualCount(List<LocalDate> dates) {
		
		return paymentVoucherDAO.getActualCount(dates);
	}

	@Override
	@Transactional
	public List<String> getAllApproverCashier() {
		// TODO Auto-generated method stub
		return paymentVoucherDAO.getAllApproverCashier();
	}

	@Override
	@Transactional
	public void addNewCashierApprover(String name, String staffId) {
		// TODO Auto-generated method stub
		paymentVoucherDAO.addNewCashierApprover(name, staffId);
	}

	@Override
	@Transactional
	public void updateCashierApprover(int id, String name, String staffId) {
		// TODO Auto-generated method stub
		paymentVoucherDAO.updateCashierApprover(id, name, staffId);
	}

	@Override
	@Transactional
	public void savePaymentVoucher(Invoice invoice) {
		paymentVoucherDAO.savePaymentVoucher(invoice);
		
	}

	@Override
	@Transactional
	public boolean checkVoucherDateRange(String startDate, String endDate) {
		// TODO Auto-generated method stub
		return paymentVoucherDAO.checkVoucherDateRange(startDate, endDate);
	}

}
