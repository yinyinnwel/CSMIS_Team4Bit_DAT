package com.dat.bit.csmis.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dat.bit.csmis.dao.VoucherDAO;
import com.dat.bit.csmis.dto.PaidVoucher_DTO;

@Service
public class VoucherService_Imp implements VoucherService {
	
	@Autowired
	private VoucherDAO paidVoucherDAO;

	@Override
	@Transactional
	public List<PaidVoucher_DTO> getPaidVoucherByDateRange(String startDate, String endDate) {
		// TODO Auto-generated method stub
		return paidVoucherDAO.getPaidVoucherByDateRange(startDate, endDate);
	}

	@Override
	@Transactional
	public List<PaidVoucher_DTO> getAllVoucherByDateRangeAndFilterBy(String startDate, String endDate,
			String filterBy) {
		// TODO Auto-generated method stub
		return paidVoucherDAO.getAllVoucherByDateRangeAndFilterBy(startDate, endDate, filterBy);
	}

	@Override
	@Transactional
	public void updateVoucherStatusByVoucherNo(String description, int status, String updatedBy) {
		// TODO Auto-generated method stub
		paidVoucherDAO.updateVoucherStatusByVoucherNo(description, status, updatedBy);
	}

}
