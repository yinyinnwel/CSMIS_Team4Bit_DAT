package com.dat.bit.csmis.report;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import com.dat.bit.csmis.dto.ReportRequestModel;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfWriter;

import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;

@Component(ApplicationData.PDF_VIEW)
@Scope("request")
public class PDFReportView extends AbstractView{
	
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		setContentType("application/pdf");

		ReportRequestModel requestInput = (ReportRequestModel) model.get(ApplicationData.REPORT_REQUEST_KEY);

		BaseJasperReport jasper = new BaseJasperReport(requestInput);
		JasperPrint jasperPrint = jasper.printJasper();

		// export to PDF
		JRPdfExporter exporter = new JRPdfExporter(DefaultJasperReportsContext.getInstance());
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();

		configuration.setMetadataTitle(requestInput.getMetaDataTitle());		

		exporter.setConfiguration(configuration);
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
		exporter.exportReport();

		Document document = new Document(PageSize.A4.rotate(), 36, 36, 54, 36);
		
		PdfWriter writer = PdfWriter.getInstance(document, outputStream);

		prepareWriter(model, writer, request);

		response.setHeader("Content-disposition",
				"inline; filename= " + requestInput.getExportFileNameWithoutExt() + ApplicationData.PDF_EXTENSION);

		writeToResponse(response, outputStream);
	}

	private void prepareWriter(Map<String, Object> model, PdfWriter writer, HttpServletRequest request)
			throws DocumentException {
		writer.setViewerPreferences(getViewerPreferences());
	}

	private int getViewerPreferences() {
		return PdfWriter.ALLOW_PRINTING;
	}

	@Override
	protected boolean generatesDownloadContent() {
		return true;
	}

}
