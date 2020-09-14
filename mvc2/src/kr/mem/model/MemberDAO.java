package kr.mem.model;

import java.sql.*;  //JDBC������ ���� �⺻ ����Ʈ
import java.util.ArrayList;
//���߿��� JDBC -> myBatis �� �ٲ��� ��

public class MemberDAO {
	
	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;
	
	//�ʱ�ȭ ��
	static {
		try {		//DriverManager
			Class.forName("oracle.jdbc.driver.OracleDriver");   //�����ε� : �޸𸮿� ��ü�� ����
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//���ᰴü �����(������ finally�� close() ���ָ� �ȵ�)
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
	
	//������ �ݾ��ֱ� 
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
	
	//insert �޼ҵ�
	public int memberInsert(MemberVO vo) {
		conn = getConnect();
		//MyBatis �����ӿ�ũ : JDBC���� SQL �������� ������ ���� �����ؼ� ����� �� �ִ� ���α׷�
		String SQL = "INSERT INTO tblMem VALUES(seq_num.NEXTVAL, ?, ?, ?, ?, ?)";  //�̿ϼ�SQL : �Ķ���Ͱ� �ִ� SQL
		
		int cnt = -1;	//-1(������ �ǹ�)
		
		try {
			ps = conn.prepareStatement(SQL);  //pre�������� ��Ű�� ���� �̿ϼ�SQL�� ps������ �����
			
			ps.setString(1, vo.getName());
			ps.setString(2, vo.getPhone());
			ps.setString(3, vo.getAddr());
			ps.setDouble(4, vo.getLat());
			ps.setDouble(5, vo.getLng());
			
			cnt = ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {   // ������ ���� ��
			dbClose();
		} 
		return cnt;
	}
	
	//��� ����Ʈ�� �����ִ� �޼ҵ�
	public ArrayList<MemberVO> memberAllList() {
		ArrayList<MemberVO> list = new ArrayList<MemberVO>();
		conn=getConnect();
		String SQL = "SELECT * FROM tblMem ORDER BY num DESC";
		try {
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			while(rs.next()) {   //rs�� �÷��� ����Ŵ, �����Ͱ� �ִ��� Ȯ���� ���� next() ���
				
				//DB���� �ϳ��� ���ڵ� �����͸� ������ ��������
				int num = rs.getInt("num");
				String name = rs.getString("name");
				String phone = rs.getString("phone");
				String addr = rs.getString("addr");
				double lat = rs.getDouble("lat");
				double lng = rs.getDouble("lng");
				
				//DB�� ���ڵ��� ��ü �����͸� ��� ���� �ϳ��� ������
				MemberVO vo = new MemberVO(num, name, phone, addr, lat, lng);
				
				//DB�� ���ڵ��� ��ü �����͸� �����
				list.add(vo);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {   // ������ ���� ��
			dbClose();
		}
		return list;
	}
	
	//ȸ�� ���� ���
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
		} finally {   // ������ ���� ��
			dbClose();
		}
		return cnt;
	}
	
	
	//���������� ���� ���� ���� ��������
	public MemberVO memberContent(int num) {
		MemberVO vo = null;
		conn = getConnect();
		String SQL = "SELECT * FROM tblMem WHERE num=?";   //�������� ������ num="+num ���� ����
		try {
			ps=conn.prepareStatement(SQL);
			ps.setInt(1, num);
			rs=ps.executeQuery();
			if(rs.next()) {
				//rs�� �÷��� ����Ŵ, �����Ͱ� �ִ��� Ȯ���� ���� next() ���					
				//DB���� �ϳ��� ���ڵ� �����͸� ������ ��������
				num = rs.getInt("num");
				String name = rs.getString("name");
				String phone = rs.getString("phone");
				String addr = rs.getString("addr");
				double lat = rs.getDouble("lat");
				double lng = rs.getDouble("lng");					
				//DB�� ���ڵ��� ��ü �����͸� ��� ���� �ϳ��� ������
				vo = new MemberVO(num, name, phone, addr, lat, lng);			
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbClose();
		}
		return vo;
	}
	
	//ȸ�� ���� ����
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
