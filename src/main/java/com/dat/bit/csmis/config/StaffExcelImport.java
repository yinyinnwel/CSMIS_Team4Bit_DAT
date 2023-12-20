package com.dat.bit.csmis.config;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.dat.bit.csmis.entity.Staff_ECXCEL;

public class StaffExcelImport {
	
	
	public static List<Staff_ECXCEL> readStaffExcel(InputStream inputStream)throws Exception{
		List<Staff_ECXCEL> staffs = new ArrayList<>();
		
		Workbook workbook=WorkbookFactory.create(inputStream);
		Sheet sheet=workbook.getSheetAt(0);
		Iterator<Row> rowIterator=sheet.iterator();
		
		while(rowIterator.hasNext()) {
			Row row=rowIterator.next();
			if(row.getRowNum()==0) {
				continue;
			}
			
			Staff_ECXCEL staff= new Staff_ECXCEL();
			Iterator<Cell> cellIterator=row.cellIterator();
			
			while(cellIterator.hasNext()) {
				Cell cell=cellIterator.next();
				int columnIndex=cell.getColumnIndex();
				switch (columnIndex) {
				case 0: staff.setId((int)cell.getNumericCellValue());
					break;
				case 1: staff.setDivision(cell.getStringCellValue());
					break;
				case 2: staff.setStaffId(cell.getStringCellValue());
					break;
				case 3: staff.setName(cell.getStringCellValue());
					break;
				case 4: staff.setDoorLogNo((int)cell.getNumericCellValue());
					break;
				case 5: staff.setDepartment(cell.getStringCellValue());
					break;
				case 6: staff.setTeam(cell.getStringCellValue());
					break;
				case 7: staff.setEmail(cell.getStringCellValue());
					break;
			

				default:
					break;
				}
			}
			
			staffs.add(staff);
		}
		
		workbook.close();
		return staffs;
	}

}
