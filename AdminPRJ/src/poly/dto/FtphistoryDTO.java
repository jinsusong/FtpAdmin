package poly.dto;

import java.util.ArrayList;

public class FtphistoryDTO {

	
	private String p_ip;
	private String p_host;
	private String success;
	private String ftp_seq;
	private String ftp_send_date;
	private String ftp_status;
	private String ftp_ip;
	private String ftp_hostname;
	private String reg_dt;
	private String chg_dt;
	private String ftp_filename;
	private String[] list_name;
	
	
	
	
	public String getFtp_filename() {
		return ftp_filename;
	}
	public void setFtp_filename(String ftp_filename) {
		this.ftp_filename = ftp_filename;
	}
	public String[] getList_name() {
		return list_name;
	}
	public void setList_name(String[] list_name) {
		this.list_name = list_name;
	}
	public String getFtp_send_date() {
		return ftp_send_date;
	}
	public void setFtp_send_date(String ftp_send_date) {
		this.ftp_send_date = ftp_send_date;
	}
	public String getFtp_status() {
		return ftp_status;
	}
	public void setFtp_status(String ftp_status) {
		this.ftp_status = ftp_status;
	}
	public String getFtp_ip() {
		return ftp_ip;
	}
	public void setFtp_ip(String ftp_ip) {
		this.ftp_ip = ftp_ip;
	}
	public String getFtp_hostname() {
		return ftp_hostname;
	}
	public void setFtp_hostname(String ftp_hostname) {
		this.ftp_hostname = ftp_hostname;
	}
	public String getReg_dt() {
		return reg_dt;
	}
	public void setReg_dt(String reg_dt) {
		this.reg_dt = reg_dt;
	}
	public String getChg_dt() {
		return chg_dt;
	}
	public void setChg_dt(String chg_dt) {
		this.chg_dt = chg_dt;
	}
	public String getFtp_seq() {
		return ftp_seq;
	}
	public void setFtp_seq(String ftp_seq) {
		this.ftp_seq = ftp_seq;
	}
	public String getP_ip() {
		return p_ip;
	}
	public void setP_ip(String p_ip) {
		this.p_ip = p_ip;
	}
	public String getP_host() {
		return p_host;
	}
	public void setP_host(String p_host) {
		this.p_host = p_host;
	}
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	

	
}
