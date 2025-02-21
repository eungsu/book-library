package app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import exception.LibraryException;
import service.LibraryService;
import vo.Book;
import vo.Member;

public class LibraryApp {

	// 업무로직을 담당하는 객체가 필요하다.
	// 사용자와 상호작용하고, 업무로직을 실행시키기 위해서는 LibraryService가 필요함
	private LibraryService service = new LibraryService();
	
	// 키보드 입력을 읽어오는 객체가 필요하다.
	private Keyboard keyboard = new Keyboard();	
	
	// 로그인 여부를 관리하는 멤버변수
    private boolean isLogined = false;
    // 현재 로그인된 회원정보를 관리하는 멤버변수
    private Member loginedMember = null;
    
    public LibraryApp() {
    	service.load();
    }
	
	private void displayMainMenu() {
		while (true) {			
			System.out.println("[도서관 대출 시스템]");
			System.out.println("------------------------------");
			System.out.println("1. 검색");
			if (!isLogined) {
				System.out.println("2. 회원가입");
				System.out.println("3. 로그인");
			}
			if (isLogined) {
				System.out.println("4. 로그아웃 ["+loginedMember.getName()+"]");
				
				if ("admin".equals(loginedMember.getId())) {
					System.out.println("5. 책 관리");	
					System.out.println("6. 회원 관리");				
				} else {
					System.out.println("7. 대출 및 반납");					
				}			
			}
			System.out.println("8. 프로그램 종료"); // 로그인 필요없다.
			System.out.println("------------------------------");
			System.out.println();
			
			System.out.print("메뉴 선택: ");
			int menuNo = keyboard.readInt();
			
			try {				
				if (menuNo == 1) {
					검색();
				} else if (menuNo == 2) {
					회원가입();
				} else if (menuNo == 3) {
					로그인();
				} else if (menuNo == 4) {
					로그아웃();
				} else if (menuNo == 5) {
					책관리();
				} else if (menuNo == 6) {
					회원관리();
				} else if (menuNo == 7) {
					대출및반납();
				} else if (menuNo == 8) {
					프로그램종료();
				} else {
					System.out.println("### 메뉴를 다시 선택하세요.");
				}

			} catch (LibraryException ex) {
				System.out.println("### 오류 발생");
				System.out.println("[오류 메세지] " + ex.getMessage());
				System.out.println("### 오류 발생");
			}			
			System.out.println();		
		}
	}
	
	public void 검색() {
		
	}	
	
	public void 회원가입() {
		System.out.println("[신규 회원 가입]");
		
		System.out.println("### 가입할 회원정보를 입력하세요.");
		System.out.print("아이디 입력: ");
		String id = keyboard.readString();		
		System.out.print("비밀번호 입력: ");
		String password = keyboard.readString();		
		System.out.print("이름 입력: ");
		String name = keyboard.readString();
		
		Member member = new Member(id, password, name);
		
		service.registerMember(member);
		
		System.out.println("### 회원가입이 완료되었습니다.");
	}	
	
	public void 로그인() {
		System.out.println("[로그인]");
		
		System.out.println("### 아이디와 비밀번호를 입력하세요.");
		System.out.print("아이디 입력: ");
		String id = keyboard.readString();		
		System.out.print("비밀번호 입력: ");
		String password = keyboard.readString();
		
		// 로그인처리를 담당하는 메소드를 호출한다.
		// 인증에 실패하면 예외가 발생되고, 인증이 완료되면 인증된 사용자정보가 반환된다.
		Member member = service.login(id, password);
		
		// 로그인여부를 true로 설정한다.
		isLogined = true;
		// 인증이 완료된 사용자정보를 저장한다.
		loginedMember = member;
		
		System.out.println("### 로그인이 완료되었습니다.");	
	}	
	
	public void 로그아웃() {
		System.out.println("[로그아웃]");
		
		// 로그인여부를 false로 설정한다.
		isLogined = false;
		// 인증이 완료된 사용자정보를 저장하는 곳에 null을 대입한다.
		loginedMember = null;

		System.out.println("### 로그아웃이 완료되었습니다.");	
	}	
	
	public void 책관리() {
		
	}	
	public void 회원관리() {
		
	}	
	public void 대출및반납() {
		
	}	
	public void 프로그램종료() {
		System.out.println("### 프로그램을 종료합니다.");
		// 회원정보, 책정보를 파일에 저장시킨다.
		service.save();
		System.exit(0);
	}
	
	
	public static void main(String[] args) {
		LibraryApp app = new LibraryApp();
		app.displayMainMenu();
	}
}
















