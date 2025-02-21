package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 키보드 입력을 읽어오는 클래스다.
 */
public class Keyboard {

	private BufferedReader reader;
	
	public Keyboard() {
		reader = new BufferedReader(new InputStreamReader(System.in)); 
	}
	
	/**
	 * 키보드로 입력한 정수를 읽어서 반환한다.
	 * @return 정수값
	 */
	public int readInt() {
		try {
			return Integer.parseInt(reader.readLine());
		} catch (IOException ex) {
			throw new IllegalArgumentException(ex.getMessage());
		}
	}
	
	/**
	 * 키보드로 입력한 실수를 읽어서 반환한다.
	 * @return 실수값
	 */
	public double readDouble() {
		try {
			return Double.parseDouble(reader.readLine());
		} catch (IOException ex) {
			throw new IllegalArgumentException(ex.getMessage());
		}
	}
	
	/**
	 * 키보드로 입력한 정수를 읽어서 반환한다.
	 * @return 정수값
	 */
	public long readLong() {
		try {
			return Long.parseLong(reader.readLine());
		} catch (IOException ex) {
			throw new IllegalArgumentException(ex.getMessage());
		}
	}
	
	/**
	 * 키보드로 입력한 문자를 읽어서 반환한다.
	 * @return 문자
	 */
	public char readChar() {
		try {
			return reader.readLine().charAt(0);
		} catch (IOException ex) {
			throw new IllegalArgumentException(ex.getMessage());
		}
	}
	
	/**
	 * 키보드로 입력한 문자열를 읽어서 반환한다.
	 * @return 문자열
	 */
	public String readString() {
		try {
			return reader.readLine();
		} catch (IOException ex) {
			throw new IllegalArgumentException(ex.getMessage());
		}
	}
}
