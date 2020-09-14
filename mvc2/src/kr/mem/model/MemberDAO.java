package kr.mem.model;

import java.sql.*;  //JDBC연동을 위한 기본 임포트
import java.util.ArrayList;
//나중에는 JDBC -> myBatis 로 바꿔줄 것

public class MemberDAO {
	
	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;
	
	//초기화 블럭
	static {
		try {		//DriverManager
			Class.forName("oracle.jdbc.driver.OracleDriver");   //동적로딩 : 메모리에 객체를 생성
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//연결객체 만들기(연결은 finally로 close() 해주면 안됨)
	public Connection getConnect() {
		String url = "jdbc:oracle:thin:@127.0.0.1:1521:XE";
		String user = "hr";
		String password = "hr";
		
		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return conn;
	}
	
	//연결을 닫아주기 
	public void dbClose() {
	
		try {
			if(rs!=null) {
				rs.close();
			}
			if(ps!=null) {
				ps.close();
			}
			if(conn!=null) {
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
				
	}
	
	//insert 메소드
	public int memberInsert(MemberVO vo) {
		conn = getConnect();
		//MyBatis 프레임워크 : JDBC에서 SQL 쿼리문을 밖으로 빼서 연결해서 사용할 수 있는 프로그램
		String SQL = "INSERT INTO tblMem VALUES(seq_num.NEXTVAL, ?, ?, ?, ?, ?)";  //미완성SQL : 파라미터가 있는 SQL
		
		int cnt = -1;	//-1(실패의 의미)
		
		try {
			ps = conn.prepareStatement(SQL);  //pre컴파일을 시키기 위해 미완성SQL을 ps변수에 담아줌
			
			ps.setString(1, vo.getName());
			ps.setString(2, vo.getPhone());
			ps.setString(3, vo.getAddr());
			ps.setDouble(4, vo.getLat());
			ps.setDouble(5, vo.getLng());
			
			cnt = ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {   // 무조건 실행 됨
			dbClose();
		} 
		return cnt;
	}
	
	//멤버 리스트를 보여주는 메소드
	public ArrayList<MemberVO> memberAllList() {
		ArrayList<MemberVO> list = new ArrayList<MemberVO>();
		conn=getConnect();
		String SQL = "SELECT * FROM tblMem ORDER BY num DESC";
		try {
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			while(rs.next()) {   //rs는 컬럼을 가리킴, 데이터가 있는지 확인을 위해 next() 사용
				
				//DB에서 하나의 레코드 데이터를 개별로 가져오기
				int num = rs.getInt("num");
				String name = rs.getString("name");
				String phone = rs.getString("phone");
				String addr = rs.getString("addr");
				double lat = rs.getDouble("lat");
				double lng = rs.getDouble("lng");
				
				//DB에 레코드의 전체 데이터를 담기 위해 하나로 묶어줌
				MemberVO vo = new MemberVO(num, name, phone, addr, lat, lng);
				
				//DB에 레코드의 전체 데이터를 담아줌
				list.add(vo);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {   // 무조건 실행 됨
			dbClose();
		}
		return list;
	}
	
	//회원 삭제 기능
	public int memberDelete(int num) {
		conn=getConnect();
		String SQL = "DELETE FROM tblMem WHERE num=?";
		int cnt = -1;
		
		try {
			ps = conn.prepareStatement(SQL);
			ps.setInt(1, num);
			cnt=ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {   // 무조건 실행 됨
			dbClose();
		}
		return cnt;
	}
	
	
	//정보수정을 위해 먼저 정보 가져오기
	public MemberVO memberContent(int num) {
		MemberVO vo = null;
		conn = getConnect();
		String SQL = "SELECT * FROM tblMem WHERE num=?";   //직접쓰고 싶으면 num="+num 으로 가능
		try {
			ps=conn.prepareStatement(SQL);
			ps.setInt(1, num);
			rs=ps.executeQuery();
			if(rs.next()) {
				//rs는 컬럼을 가리킴, 데이터가 있는지 확인을 위해 next() 사용					
				//DB에서 하나의 레코드 데이터를 개별로 가져오기
				num = rs.getInt("num");
				String name = rs.getString("name");
				String phone = rs.getString("phone");
				String addr = rs.getString("addr");
				double lat = rs.getDouble("lat");
				double lng = rs.getDouble("lng");					
				//DB에 레코드의 전체 데이터를 담기 위해 하나로 묶어줌
				vo = new MemberVO(num, name, phone, addr, lat, lng);			
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbClose();
		}
		return vo;
	}
	
	//회원 정보 수정
	public int memberUpdate(MemberVO vo) {
		conn = getConnect();
		String SQL = "UPDATE tblMem SET phone=?, addr=? WHERE num=?";
		int cnt = -1;
		
		try {
			ps = conn.prepareStatement(SQL);
			ps.setString(1, vo.getPhone());
			ps.setString(2, vo.getAddr());
			ps.setInt(3, vo.getNum());
			cnt = ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbClose();
		}
		return cnt;
	}
	
	
}
