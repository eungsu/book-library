package service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import exception.LibraryException;
import vo.Book;
import vo.Member;

public class LibraryService {

	// 모든 책정보를 저장하는 List<Book> 객체
	private List<Book> books = new ArrayList<Book>();
	// 모든 회원정보를 저장하는 List<Member> 객체
	private List<Member> members = new ArrayList<Member>();
	
	/**
	 * 신규 회원정보를 전달받아서 회원가입 시킨다.
	 * 단, 아이디가 이미 사용중이면 LibraryException 예외를 발생시킨다.
	 * @param member 신규 회원정보
	 */
	public void registerMember(Member member)  {
		// 아이디 중복여부를 체크한다.
		Member foundMember = this.findMemberById(member.getId());
		if (foundMember != null) {
			throw new LibraryException("["+member.getId()+"] 는 이미 사용중인 아이디입니다.");
		}
		
		// 회원가입 
		members.add(member);		
	}
	
	/**
	 * 아이디, 비밀번호를 전달받아서 사용자인증(로그인)을 수행하고,
	 * 인증이 완료된 회원정보를 반환한다.
	 * 단, 아이디, 비밀번호가 유효하지 않으면 예외가 발생한다.
	 * @param id 아이디
	 * @param password 비밀번호
	 * @return 인증이 완료된 회원정보
	 */
	public Member login(String id, String password) {
		// 아이디에 해당하는 사용자정보가 존재하는지 체크한다.		
		Member foundMember = this.findMemberById(id);
		if (foundMember == null) {
			throw new LibraryException("아이디 혹은 비밀번호가 올바르지 않습니다.");
		}
		// 비밀번호가 일치하는지 체크한다.
		if (!foundMember.getPassword().equals(password)) {
			throw new LibraryException("아이디 혹은 비밀번호가 올바르지 않습니다.");			
		}
		
		// 인증이 완료되었기 때문에 회원정보를 반환한다.
		return foundMember;
	}
	
	/**
	 * 모든 책정보를 반환한다.
	 * @return 책 목록
	 */
	public List<Book> getAllBooks() {
		return books;
	}
	
	/**
	 * 검색어를 전달받아서 제목에 검색어가 포함된 책정보를 반환한다.
	 * @param keyword 검색어
	 * @return 검색된 책 목록, 검색된 책이 없는 경우도 있다.
	 */
	public List<Book> searchBooks(String keyword) {
		List<Book> searchedBooks = new ArrayList<Book>();
		
		for (Book book : books) {
			// 반복처리 중인 책의 제목에 keyword가 포함되어 있으면 
			// searchBooks에 저장한다.
			if (book.getTitle().contains(keyword)) {
				searchedBooks.add(book);
			}
		}
		
		return searchedBooks;
	}
	
	/**
	 * 회원아이디를 전달받아서 해당 회원이 대출중인 책정보를 반환한다.
	 * @param memberId 회원아이디
	 * @return 해당 회원이 대출중인 모든 책정보
	 */
	public List<Book> getMyBorrowedBooks(String memberId) {
		List<Book> borrowedBooks = new ArrayList<Book>();
		
		for (Book book : books) {
			if (memberId.equals(book.getBorrower())) {
				borrowedBooks.add(book);
			}
		}
		
		return borrowedBooks;
	}
	
	/**
	 * 책번호, 회원아이디를 전달받아서 해당 책을 대출한다.
	 * @param bookNo 책번호
	 * @param memberId 회원아이디
	 */
	public void borrowBook(int bookNo, String memberId) {
		// 책번호에 해당하는 책정보를 조회한다.
		Book foundBook = this.findBookByNo(bookNo);
		if (foundBook == null) {
			throw new LibraryException("["+bookNo+"] 책정보가 없습니다.");
		}
		
		// 책의 대출상태가 true면 예외를 발생시킨다.
		if (foundBook.isBorrowed()) {
			throw new LibraryException("["+bookNo+"] 이미 대출중인 책입니다.");
		}
		
		// 대출요청 처리 - 대출상태를 true로 설정한다.
		foundBook.setBorrowed(true);
		// 대출요청 처리 - 대출자를 설정한다.
		foundBook.setBorrower(memberId);
	}
	
	/**
	 * 책번호, 회원아이디를 전달받아서 해당 책을 반납처리한다.
	 * @param bookNo 반납처리할 책번호
	 * @param memberId 회원아이디
	 */
	public void returnBook(int bookNo, String memberId) {
		Book foundBook = this.findBookByNo(bookNo);
		if (foundBook == null) {
			throw new LibraryException("["+bookNo+"] 책정보가 없습니다.");
		}
		// 대출상태(isBorrowed)가 false인 책은 대출중인 책이 아니다. 
		if (!foundBook.isBorrowed()) {
			throw new LibraryException("["+bookNo+"] 대출중인 책이 아닙니다.");			
		}
		// 대출자(borrower)와 회원아이디가 서로 다르면 반납처리할 수 없다.
		if (!foundBook.getBorrower().equals(memberId)) {			
			throw new LibraryException("["+bookNo+"] 다른 회원이 대출한 책은 반납처리할 수 없다.");			
		}
		
		// 반납요청 처리 - 대출상태를 false로 설정한다.
		foundBook.setBorrowed(false);
		// 반납요청 처리 - 대출자를 null 설정한다.
		foundBook.setBorrower(null);		
	}
	
	/**
	 * 책번호를 전달받아서 책 정보를 반환한다.
	 * @param no 책번호
	 * @return 책정보, 일치하는 책정보가 없으면 null을 반환한다.
	 */
	private Book findBookByNo(int no) {
		for (Book book : books) {
			if (book.getNo() == no) {
				return book;
			}
		}
		return null;
	}
	
	/**
	 * 아이디를 전달받아서 회원정보를 반환한다.
	 * @param id 회원아이디
	 * @return 회원정보, 일치하는 회원정보가 없으면 null을 반환한다.
	 */
	private Member findMemberById(String id) {
		for (Member member : members) {
			if (member.getId().equals(id)) {
				return member;
			}
		}
		return null;
	}	
	
	/**
	 * 책정보와 회원정보를 파일로 저장한다.
	 */
	public void save() {
		String filename1 = "src/members.txt";
		String filename2 = "src/books.txt";
		
		try (
			PrintWriter writer1 = new PrintWriter(filename1);
			PrintWriter writer2 = new PrintWriter(filename2);
		){
			StringBuilder sb = new StringBuilder();
			for (Member member : members) {
				sb.append(member.getId());
				sb.append(",");
				sb.append(member.getPassword());
				sb.append(",");
				sb.append(member.getName());
				
				writer1.println(sb.toString());
				sb.setLength(0);
			}
			
			for (Book book : books) {
				sb.append(book.getNo());
				sb.append(",");
				sb.append(book.getTitle());
				sb.append(",");
				sb.append(book.getAuthor());
				sb.append(",");
				sb.append(book.isBorrowed());
				sb.append(",");
				sb.append(book.getBorrower());
				
				writer2.println(sb.toString());
				sb.setLength(0);
			}
			
		} catch(IOException e) {
			throw new LibraryException(e.getMessage());
		}
	}
	
	public void load() {
		String filename1 = "src/members.txt";
		String filename2 = "src/books.txt";
		
		try (
			BufferedReader reader1 = new BufferedReader(new FileReader(filename1));
				BufferedReader reader2 = new BufferedReader(new FileReader(filename2));
		){
			String text = null;
			while ((text = reader1.readLine()) != null) {
				String[] values = text.split(",");
				members.add(new Member(values[0], values[1], values[2]));
			}
			
			while((text = reader2.readLine()) != null) {
				String[] values = text.split(",");
				Book book = new Book();
				book.setNo(Integer.valueOf(values[0]));
				book.setTitle(values[1]);
				book.setAuthor(values[2]);
				
//				book.setBorrowed(Boolean.parseBoolean(values[3]));
//				book.setBorrower(values[4]);
				
				boolean borrowed = Boolean.parseBoolean(values[3]);
				book.setBorrowed(borrowed);
				if (borrowed) {
					book.setBorrower(values[4]);					
				}
						
				
				books.add(book);
			}
		} catch (IOException e) {
			throw new LibraryException(e.getMessage());
		}
	}
}














