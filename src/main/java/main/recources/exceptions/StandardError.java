package main.recources.exceptions;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StandardError implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long timestamp;
	private Integer status;
	private String error;
	private String msg;
	private String path;

	public StandardError(Long timestamp, Integer status, String error, String msg, String path) {
		super();
		this.timestamp = timestamp;
		this.status = status;
		this.error = error;
		this.msg = msg;
		this.path = path;
	}

//	public String getDate() {
//		Date date = new Date(timestamp);
//		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm");
//		String dateText = sdf.format(date);
//		return dateText;
//	}

	public Long getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	

	

}
