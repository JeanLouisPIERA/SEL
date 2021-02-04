package com.microseladherent.errors;

public class ApiResponse {
	
	
	  private Object data; private String message; private boolean error = true;
	  
	  public ApiResponse(Object data, String message){ this.data = data;
	  this.message = message; }
	  
	  public Object getData() { return data; }
	  
	  public void setData(Object data) { this.data = data; }
	  
	  public String getMessage() { return message; }
	  
	  public void setMessage(String message) { this.message = message; }
	  
	  public boolean isError() { return error; }
	  
	  public void setError(boolean error) { this.error = error; }
	  
	  @Override public String toString() { return "ApiResponse [data=" + data +
	  ", message=" + message + ", error=" + error + "]"; }
	 
    
    

}
