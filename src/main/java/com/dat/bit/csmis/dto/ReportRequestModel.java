package com.dat.bit.csmis.dto;

import java.util.Collection;
import java.util.Map;

import com.dat.bit.csmis.report.ApplicationData;

public class ReportRequestModel {

	private Map<String, Object> parameters;
	private String reportNameWithoutExt;
	private String exportFileNameWithoutExt;
	private String metaDataTitle;
	private Collection<?> dataSource;
	private String redirectTo;

	public ReportRequestModel(String reportName, String exportFileName) {

		this.reportNameWithoutExt = ApplicationData.REPORT_ROOT_FOLDER + reportName + ApplicationData.REPORT_EXTENSION;
		this.exportFileNameWithoutExt = exportFileName;
	}

	public String getMetaDataTitle() {
		return metaDataTitle;
	}

	public void setMetaDataTitle(String metaDataTitle) {
		this.metaDataTitle = metaDataTitle;
	}

	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}

	public Map<String, Object> getParameters() {
		return parameters;
	}

	public String getReportNameWithoutExt() {
		return reportNameWithoutExt;
	}

	public String getExportFileNameWithoutExt() {
		return exportFileNameWithoutExt;
	}

	public Collection<?> getDataSource() {
		return dataSource;
	}

	public void setDataSource(Collection<?> dataSource) {
		this.dataSource = dataSource;
	}

	public String getRedirectTo() {
		return redirectTo;
	}

	public void setRedirectTo(String redirectTo) {
		this.redirectTo = redirectTo;
	}
	
	

}
