package com.dat.bit.csmis.report;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import com.dat.bit.csmis.dto.ReportRequestModel;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;


@Component(ApplicationData.EXCEL_VIEW)
@Scope("request")
public class ExcelReportView extends AbstractView{
	
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ReportRequestModel requestInput = (ReportRequestModel) model.get(ApplicationData.REPORT_REQUEST_KEY);

		BaseJasperReport jasper = new BaseJasperReport(requestInput);
		JasperPrint jasperPrint = jasper.printJasper();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		JRXlsxExporter exporter = new JRXlsxExporter();

		SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
		configuration.setDetectCellType(true);
		configuration.setCollapseRowSpan(true);
		exporter.setConfiguration(configuration);
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
		exporter.exportReport();

		response.setHeader("Content-Disposition", "attachment; filename= " + requestInput.getExportFileNameWithoutExt()+ ApplicationData.EXCEL_EXTENSION);
		//response.sendRedirect(requestInput.getRedirectTo());
		
		writeToResponse(response, outputStream);
   
	}
}
