package com.csmis.entity;

import java.util.Collection;
import java.util.Map;

public class ReportRequestModel {

	private Map<String, Object> parameters;
	private String reportNameWithoutExt;
	private String exportFileNameWithoutExt;
	private String metaDataTitle;
	private Collection<?> dataSource;

	public ReportRequestModel(String reportName, String exportFileName) {

		this.reportNameWithoutExt = "/reports/" + reportName + ".jrxml";
		this.exportFileNameWithoutExt = exportFileName;
	}

	public ReportRequestModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ReportRequestModel(Map<String, Object> parameters, String reportNameWithoutExt,
			String exportFileNameWithoutExt, String metaDataTitle, Collection<?> dataSource) {
		super();
		this.parameters = parameters;
		this.reportNameWithoutExt = reportNameWithoutExt;
		this.exportFileNameWithoutExt = exportFileNameWithoutExt;
		this.metaDataTitle = metaDataTitle;
		this.dataSource = dataSource;
	}

	public Map<String, Object> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}

	public String getReportNameWithoutExt() {
		return reportNameWithoutExt;
	}

	public void setReportNameWithoutExt(String reportNameWithoutExt) {
		this.reportNameWithoutExt = reportNameWithoutExt;
	}

	public String getExportFileNameWithoutExt() {
		return exportFileNameWithoutExt;
	}

	public void setExportFileNameWithoutExt(String exportFileNameWithoutExt) {
		this.exportFileNameWithoutExt = exportFileNameWithoutExt;
	}

	public String getMetaDataTitle() {
		return metaDataTitle;
	}

	public void setMetaDataTitle(String metaDataTitle) {
		this.metaDataTitle = metaDataTitle;
	}

	public Collection<?> getDataSource() {
		return dataSource;
	}

	public void setDataSource(Collection<?> dataSource) {
		this.dataSource = dataSource;
	}

	
}