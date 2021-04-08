package kr.or.bit.action;


// servlet 요청 받기
// 1. 화면 출력
// 2. 입력 처리 

// 화면을 처리할 것인지 로직을 처리할 것인지 
// 뷰의 경로 
public class ActionForward {
	private boolean isRedirect = false; //
	private String path = null; // 이동경로 ( 뷰의 주소 )
	
	
	
	public boolean isRedirect() {
		return isRedirect;
	}
	public void setRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	
	
}
