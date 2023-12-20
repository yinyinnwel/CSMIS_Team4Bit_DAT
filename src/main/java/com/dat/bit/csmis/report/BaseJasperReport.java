package com.dat.bit.csmis.report;

import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.dat.bit.csmis.dto.ReportRequestModel;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

public class BaseJasperReport {
	
	private ReportRequestModel requestInput;

	public BaseJasperReport(ReportRequestModel requestInput) {
		this.requestInput = requestInput;
	}

	public JasperPrint printJasper() throws JRException {

		// compile jrxml template and get report
		JasperReport report = getReport();

		Map<String, Object> parameters = new HashMap<>();

		if (requestInput.getParameters() != null) {
			for (Map.Entry<String, Object> entry : requestInput.getParameters().entrySet()) {

				if (entry.getValue() instanceof Collection<?>) {
					parameters.put(entry.getKey(), new JRBeanCollectionDataSource((Collection<?>) entry.getValue()));
				} else {
					parameters.put(entry.getKey(), entry.getValue());
				}
			}
		}

		JasperPrint jasperPrint = null;
		if (requestInput.getDataSource() != null && !requestInput.getDataSource().isEmpty()) {

			jasperPrint = JasperFillManager.fillReport(report, parameters,
					new JRBeanCollectionDataSource(requestInput.getDataSource()));
		} else {
			jasperPrint = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
		}

		// fill the report with data source objects
		return jasperPrint;
	}

	private JasperReport getReport() throws JRException {

		final InputStream stream = getClass().getResourceAsStream(requestInput.getReportNameWithoutExt());
		final JasperDesign jasperDesign = JRXmlLoader.load(stream);
		return JasperCompileManager.compileReport(jasperDesign);
	}

}
