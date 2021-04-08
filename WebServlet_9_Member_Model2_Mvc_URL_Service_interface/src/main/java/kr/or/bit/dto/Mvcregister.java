package kr.or.bit.dto;

public class Mvcregister {
	private int id;
	private String pwd;
	private String email;
	
	// 필요에 따라 생성자는 알아서
	
	// dto는 해당테이블을 각 객체로 만들어주는 역할 
	// * 테이블과 똑같은 변수의 이름으로 생성
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "mvcregister [id=" + id + ", pwd=" + pwd + ", email=" + email + "]";
	}	
	
}
