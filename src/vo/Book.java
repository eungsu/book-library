package vo;

import java.io.Serializable;

public class Book implements Serializable {

	private int no;				// 도서번호
	private String title;		// 제목
	private String author;		// 저자
	private boolean isBorrowed;	// 대출여부
	private String borrower;	// 대출자 아이디
	
	public Book() {}
	
	public Book(int no, String title, String author) {
		this.no = no;
		this.title = title;
		this.author = author;
		this.isBorrowed = false;	// 대출여부: false
		this.borrower = null;		// 대출자: null
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public boolean isBorrowed() {
		return isBorrowed;
	}

	public void setBorrowed(boolean isBorrowed) {
		this.isBorrowed = isBorrowed;
	}

	public String getBorrower() {
		return borrower;
	}

	public void setBorrower(String borrower) {
		this.borrower = borrower;
	}

}
