package com.dat.bit.csmis.dao;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dat.bit.csmis.dto.PaymentVoucherDTO;
import com.dat.bit.csmis.entity.All_Have_Lunch;
import com.dat.bit.csmis.entity.Approver_Cashier;
import com.dat.bit.csmis.entity.Invoice;
import com.dat.bit.csmis.entity.Staff;
import com.dat.bit.csmis.service.StaffService;

@Repository
public class PaymentVoucherDAO_Imp implements PaymentVoucherDAO {

	
	private EntityManager entityManager;

	// set up constructor injection
	@Autowired
	public PaymentVoucherDAO_Imp(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}
	
	@Autowired
	private StaffService staffService;

	@Override
	public List<PaymentVoucherDTO> getActualCount(List<LocalDate> dates) {
		Session currentSession=entityManager.unwrap(Session.class);
		
		List<PaymentVoucherDTO> dtos = new ArrayList<>();
		
		for(LocalDate date : dates) {
			Query<All_Have_Lunch> query = currentSession.createQuery("from All_Have_Lunch "
					+ "where created_at >= '"+date+" 00:00:00' "
					+ "and created_at <= '"+date+" 23:59:59' ",All_Have_Lunch.class);
			
			if(!query.getResultList().isEmpty()) {
				
				Query<All_Have_Lunch> query1 = currentSession.createQuery("from All_Have_Lunch "
						+ "where created_at >= '"+date+" 00:00:00' "
						+ "and created_at <= '"+date+" 23:59:59' "
						+ "and register = 1",All_Have_Lunch.class);
				
				All_Have_Lunch lunch = query1.getResultList().get(0);
				
				String invoiceDate = formattedDateForPayment(date+"");
				int actual = query.getResultList().size();
				int register = query1.getResultList().size();
				int difference = actual - register;
				int price = lunch.getCompany_cost()+lunch.getStaff_cost();
				double amount = (double)(price * actual);
				
				PaymentVoucherDTO voucherDTO = new PaymentVoucherDTO();
				voucherDTO.setDate(invoiceDate);
				voucherDTO.setRegisteredCount(register);
				voucherDTO.setActualCount(actual);
				voucherDTO.setDifference(difference);
				voucherDTO.setCompany_cost(lunch.getCompany_cost());
				voucherDTO.setStaff_cost(lunch.getStaff_cost());
				voucherDTO.setPrice(price);
				voucherDTO.setAmount(amount);
				
				dtos.add(voucherDTO);
				
//				System.out.println("inVoice Date : "+invoiceDate);
//				System.out.println("Actual Count : "+ actual);
//				System.out.println("Registered Count : "+register);
//				System.out.println("Difference : "+difference);
//				System.out.println("Price : "+price);
//				System.out.println("Amount : "+amount);
			}
			
			
		}
		
		
		return dtos;
	}
	
	@Override
	public List<String> getAllApproverCashier() {
		Session currentSession=entityManager.unwrap(Session.class);
		
		List<String> names = new ArrayList<>();
		
		String hql = "select staff_id from StaffDetail where department = 'HR/Admin Dept'";
		
		Query<String> query1 = currentSession.createQuery(hql,String.class);
		for(String staffId : query1.getResultList()) {
			Staff staff = staffService.getStaffByStaff_Id(staffId);
			
			names.add(staff.getName());
		}
		
		
		return names;
	}

	@Override
	public void addNewCashierApprover(String name, String staffId) {
		Session currentSession=entityManager.unwrap(Session.class);
		
		Query<Staff> query = currentSession.createQuery("from Staff where staffId = '"+staffId+"'",Staff.class);
		Staff staff = query.getSingleResult();
		
		Approver_Cashier approver_Cashier = new Approver_Cashier();
		approver_Cashier.setName(name);
		approver_Cashier.setCreatedAt(new Timestamp(new Date().getTime()));
		approver_Cashier.setCreatedBy("("+staffId+")"+staff.getName());
		
		
		currentSession.save(approver_Cashier);
		
	}

	@Override
	public void updateCashierApprover(int id, String name, String staffId) {
		Session currentSession=entityManager.unwrap(Session.class);
		
		Query<Staff> query = currentSession.createQuery("from Staff where staffId = '"+staffId+"'",Staff.class);
		Staff staff = query.getSingleResult();
		
		Query<Approver_Cashier> query1 = currentSession.createQuery("from Approver_Cashier where id = "+id,Approver_Cashier.class);
		Approver_Cashier approver_Cashier = query1.getSingleResult();
		
		approver_Cashier.setName(name);
		approver_Cashier.setUpdatedAt(new Timestamp(new Date().getTime()));
		approver_Cashier.setUpdatedBy("("+staffId+")"+staff.getName());
		
		currentSession.update(approver_Cashier);
	}
	
	@Override
	public void savePaymentVoucher(Invoice invoice) {
		Session currentSession=entityManager.unwrap(Session.class);
		
		currentSession.save(invoice);
	}
	

	@Override
	public boolean checkVoucherDateRange(String startDate, String endDate) {
		Session currentSession=entityManager.unwrap(Session.class);
		
		List<String> selectedDates = getDateRange(startDate, endDate);
		
		String start = getStartDateOfMonth(startDate);
		String end = getEndDateOfMonth(endDate);
	//----------- Get invoices between start and end -------------
		List<Invoice> invoices = new ArrayList<>();
		Query<Invoice> query = currentSession.createQuery("from Invoice where "
				+ "start_date>= '"+start+"' and "
				+ "end_date <= '"+end+"'",Invoice.class);
		invoices = query.getResultList();
		
		List<String> allInvoiceIncludedDates = new ArrayList<>();
		
		for(Invoice invoice : invoices) {
			String startInvoice = invoice.getStart_date().toString();
			String endInvoice = invoice.getEnd_date().toString();
			
			List<String> dateRange = getDateRange(startInvoice, endInvoice);
			for(String d : dateRange) {
				allInvoiceIncludedDates.add(d);
			}
		}
		
		//System.out.println(selectedDates);
		//System.out.println(allInvoiceIncludedDates);
	//-----------  Check the selected date are there in invoices---------------------
		//if status = true; => Contain
		//if status = false; => not contain
		boolean status = false;
		
		for(String date : selectedDates) {
			if(allInvoiceIncludedDates.contains(date)) {
				status = true;
			}
		}
		
		return status;
	}

	
	//=========================== Other Functions ====================================
	
	public String getStartDateOfMonth(String dateString) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateString, formatter);
        LocalDate startOfMonth = date.withDayOfMonth(1);
        String startDate = startOfMonth+"";
        //System.out.println(startDate);
        
		return startDate;
	}
	
	public String getEndDateOfMonth(String dateString) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateString, formatter);
        LocalDate endOfMonth = date.withDayOfMonth(date.lengthOfMonth());
		String endDate = endOfMonth+"";
		//System.out.println(endDate);
		
		return endDate;
	}
	
	public List<String> getDateRange(String startDateString, String endDateString) {
		List<String> dateRange = new ArrayList<>();
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate startDate = LocalDate.parse(startDateString, formatter);
		LocalDate endDate = LocalDate.parse(endDateString, formatter);
		long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
		for (int i = 0; i <= daysBetween; i++) {
			LocalDate date = startDate.plusDays(i);
			dateRange.add(date.toString());
		}

		return dateRange;
	}

	public String formattedDateForPayment(String st) {
		LocalDate date = LocalDate.parse(st);
        String formattedDate = date.format(DateTimeFormatter.ofPattern("d'`' MMM yyyy"));
        formattedDate = formattedDate.replace("`", getOrdinalSuffix(date.getDayOfMonth()));
		
		return formattedDate;
	}
	
	
	public static String getOrdinalSuffix(int n) {
        if (n >= 11 && n <= 13) {
            return "th";
        }
        switch (n % 10) {
            case 1: return "st";
            case 2: return "nd";
            case 3: return "rd";
            default: return "th";
        }
    }





}
