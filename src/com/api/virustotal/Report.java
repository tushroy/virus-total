package com.api.virustotal;

public class Report {
	private String vendor;
	private String update;
	private String malwarename;

	public String getMalwarename() {
		return malwarename;
	}

	public void setMalwarename(String malwarename) {
		this.malwarename = malwarename;
	}

	public String getUpdate() {
		return update;
	}

	public void setUpdate(String update) {
		this.update = update;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	@Override
	public String toString() {
		return "Report{" + "vendor=" + vendor + ", update=" + update
				+ ", malwarename=" + malwarename + '}';
	}

}
