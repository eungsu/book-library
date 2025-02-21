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
				book.setBorrowed(Boolean.parseBoolean(values[3]));
				book.setBorrower(values[4]);
				books.add(book);
			}
		} catch (IOException e) {
			throw new LibraryException(e.getMessage());
		}
	}
}














