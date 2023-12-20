package com.dat.bit.csmis.dao;

import java.util.List;

import com.dat.bit.csmis.dto.PaidVoucher_DTO;

public interface VoucherDAO {
	
	public List<PaidVoucher_DTO> getPaidVoucherByDateRange(String startDate,String endDate);
	
	public List<PaidVoucher_DTO> getAllVoucherByDateRangeAndFilterBy(String startDate,String endDate,String filterBy);
	
	public void updateVoucherStatusByVoucherNo(String description,int status,String updatedBy);

}
