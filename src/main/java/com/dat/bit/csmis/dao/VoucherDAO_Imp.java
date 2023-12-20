package com.dat.bit.csmis.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dat.bit.csmis.dto.PaidVoucher_DTO;
import com.dat.bit.csmis.entity.Invoice;
import com.dat.bit.csmis.entity.Restaurant;

@Repository
public class VoucherDAO_Imp implements VoucherDAO {

	private EntityManager entityManager;

	// set up constructor injection
	@Autowired
	public VoucherDAO_Imp(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}
	
	
	@Override
	public List<PaidVoucher_DTO> getPaidVoucherByDateRange(String startDate, String endDate) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		List<PaidVoucher_DTO> paidVoucher_DTOs = new ArrayList<>();
		
		List<Invoice> invoices = new ArrayList<>();
		
		Query<Invoice> query = currentSession.createQuery("from Invoice where "
				+ "start_date>= '"+startDate+"' and "
				+ "end_date <= '"+endDate+"' and status = 1",Invoice.class);
		invoices = query.getResultList();
		
		for(Invoice invoice : invoices) {
			int actualCount = invoice.getActual_count();
			double companyCost = invoice.getCompanyCost();
			double staffCost = invoice.getStaffCost();
			
			Restaurant restaurant = new Restaurant();
			Query<Restaurant> query1 = currentSession.createQuery("from Restaurant where id = "+invoice.getRestaurantId(),Restaurant.class);
			restaurant = query1.getSingleResult();
			
			PaidVoucher_DTO paidVoucher = new PaidVoucher_DTO();
			paidVoucher.setDescription(invoice.getVoucher_no()+" : "+invoice.getStart_date()+"~"+invoice.getEnd_date());
			paidVoucher.setCateringService(restaurant.getName());
			paidVoucher.setCashier(invoice.getCashier());
			paidVoucher.setReceiver(invoice.getReceiver());
			paidVoucher.setApprover(invoice.getApprover());
			paidVoucher.setActualCount(actualCount);
			paidVoucher.setCompanyCost(companyCost);
			paidVoucher.setStaffCost(staffCost);
			paidVoucher.setTotalCost(companyCost+staffCost);
			paidVoucher.setCompanyAmount(companyCost*actualCount);
			paidVoucher.setStaffAmount(staffCost*actualCount);
			paidVoucher.setTotalAmount((companyCost*actualCount)+(staffCost*actualCount));
			paidVoucher.setPaymentDate(invoice.getPaymentDate().toString());
			paidVoucher.setStatus(invoice.getStatus());
			paidVoucher.setPaymentMethod(invoice.getPaymentMethod());
			paidVoucher.setCreatedAt(invoice.getCreatedAt());
			paidVoucher.setCreatedBy(invoice.getCreatedBy());
			paidVoucher.setUpdatedAt(invoice.getUpdatedAt());
			paidVoucher.setUpdatedBy(invoice.getUpdatedBy());
			
			paidVoucher_DTOs.add(paidVoucher);
		}
		
		return paidVoucher_DTOs;
	}


	@Override
	public List<PaidVoucher_DTO> getAllVoucherByDateRangeAndFilterBy(String startDate, String endDate,
			String filterBy) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		List<PaidVoucher_DTO> paidVoucher_DTOs = new ArrayList<>();
		
		List<Invoice> invoices = new ArrayList<>();
		
		Query<Invoice> query = null;
		
		switch (filterBy) {
		case "all":{
					query = currentSession.createQuery("from Invoice where "
							+ "start_date>= '"+startDate+"' and "
							+ "end_date <= '"+endDate+"'",Invoice.class);
		}break;
		case "paid":{
					query = currentSession.createQuery("from Invoice where "
							+ "start_date>= '"+startDate+"' and "
							+ "end_date <= '"+endDate+"' and status = 1",Invoice.class);
		}break;
		case "unpaid":{
					query = currentSession.createQuery("from Invoice where "
							+ "start_date>= '"+startDate+"' and "
							+ "end_date <= '"+endDate+"' and status = 0",Invoice.class);
		}break;

		default:
			break;
		}
		
		invoices = query.getResultList();
		
		for(Invoice invoice : invoices) {
			int actualCount = invoice.getActual_count();
			double companyCost = invoice.getCompanyCost();
			double staffCost = invoice.getStaffCost();
			
			Restaurant restaurant = new Restaurant();
			Query<Restaurant> query1 = currentSession.createQuery("from Restaurant where id = "+invoice.getRestaurantId(),Restaurant.class);
			restaurant = query1.getSingleResult();
			
			PaidVoucher_DTO paidVoucher = new PaidVoucher_DTO();
			paidVoucher.setDescription(invoice.getVoucher_no()+" : "+invoice.getStart_date()+"~"+invoice.getEnd_date());
			paidVoucher.setCateringService(restaurant.getName());
			paidVoucher.setCashier(invoice.getCashier());
			paidVoucher.setReceiver(invoice.getReceiver());
			paidVoucher.setApprover(invoice.getApprover());
			paidVoucher.setActualCount(actualCount);
			paidVoucher.setCompanyCost(companyCost);
			paidVoucher.setStaffCost(staffCost);
			paidVoucher.setTotalCost(companyCost+staffCost);
			paidVoucher.setCompanyAmount(companyCost*actualCount);
			paidVoucher.setStaffAmount(staffCost*actualCount);
			paidVoucher.setTotalAmount((companyCost*actualCount)+(staffCost*actualCount));
			paidVoucher.setPaymentDate(invoice.getPaymentDate().toString());
			paidVoucher.setStatus(invoice.getStatus());
			paidVoucher.setPaymentMethod(invoice.getPaymentMethod());
			paidVoucher.setCreatedAt(invoice.getCreatedAt());
			paidVoucher.setCreatedBy(invoice.getCreatedBy());
			paidVoucher.setUpdatedAt(invoice.getUpdatedAt());
			paidVoucher.setUpdatedBy(invoice.getUpdatedBy());
			
			paidVoucher_DTOs.add(paidVoucher);
		}
		
		return paidVoucher_DTOs;
	}


	@Override
	public void updateVoucherStatusByVoucherNo(String voucherNo, int status, String updatedBy) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		Invoice invoice = new Invoice();
		Query<Invoice> query = currentSession.createQuery("from Invoice where voucher_no = '"+voucherNo+"'",Invoice.class);
		invoice = query.getSingleResult();
		
		invoice.setUpdatedAt(new Timestamp(new Date().getTime()));
		invoice.setUpdatedBy(updatedBy);
		invoice.setStatus(status);
		
		currentSession.update(invoice);
		
	}

}
