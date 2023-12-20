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

import com.dat.bit.csmis.entity.DoorLog_EXCEL;

public class DoorlogExceIImport {
	
	public static List<DoorLog_EXCEL> readDoorlogExcel(InputStream inputStream)throws Exception{
		List<DoorLog_EXCEL> doorlogs = new ArrayList<>();
		
		Workbook workbook=WorkbookFactory.create(inputStream);
		Sheet sheet=workbook.getSheetAt(0);
		Iterator<Row> rowIterator=sheet.iterator();
		
		while(rowIterator.hasNext()) {
			Row row=rowIterator.next();
			if(row.getRowNum()==0) {
				continue;
			}
			
			DoorLog_EXCEL doorLog = new DoorLog_EXCEL();
			Iterator<Cell> cellIterator=row.cellIterator();
			while(cellIterator.hasNext()) {
				Cell cell=cellIterator.next();
				int columnIndex=cell.getColumnIndex();
				
				switch (columnIndex) {
				case 2: doorLog.setDoorlogNo((int)cell.getNumericCellValue());
					break;
				case 3: doorLog.setDate_time(cell.getDateCellValue());
					break;
				default:
					break;
				}
			}
			doorlogs.add(doorLog);
		}
		
		workbook.close();
		return doorlogs;
	}

}
