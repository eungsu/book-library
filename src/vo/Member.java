package vo;

import java.io.Serializable;

public class Member implements Serializable {

	private String id;			// 회원아이디
	private String password;	// 비밀번호
	private String name;		// 이름
	
	public Member() {}
	
	public Member(String id, String password, String name) {
		this.id = id;
		this.password = password;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
