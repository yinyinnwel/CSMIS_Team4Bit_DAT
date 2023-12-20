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

import com.dat.bit.csmis.entity.Holiday;




public class HolidayExcelImport {
	public static List<Holiday> readHoliday(InputStream inputStream)throws Exception{
		List<Holiday> holidays=new ArrayList<>();
		
		
		Workbook workbook=WorkbookFactory.create(inputStream);
		Sheet sheet=workbook.getSheetAt(0);
		Iterator<Row> rowIterator=sheet.iterator();
		while(rowIterator.hasNext()) {
			Row row=rowIterator.next();
			if(row.getRowNum()==0) {
				continue;
			}
		
			Holiday holiday=new Holiday();
			Iterator<Cell> cellIterator=row.cellIterator();
			while(cellIterator.hasNext()) {
				Cell cell=cellIterator.next();
				int columnIndex=cell.getColumnIndex();
				switch(columnIndex) {
//				case 0:
//					holiday.setId((int)cell.getNumericCellValue());
//					break;
				case 1:
					holiday.setDate(cell.getDateCellValue()+"");
					break;
				case 2:
					holiday.setHoliday(cell.getStringCellValue());
					break;
				default:
					break;
				}
			}
			holidays.add(holiday);
		}
		workbook.close();
		return holidays;
	}
}
