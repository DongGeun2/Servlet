package kr.or.bit.dao;

import javax.sql.DataSource;

import kr.or.bit.dto.Emp;
import kr.or.bit.utils.DB_Close;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.Date;

import javax.naming.Context;
import javax.naming.InitialContext;

public class EmpDao {

	private static EmpDao empDao;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private int result;
	DataSource ds = null;

	public EmpDao() {
		try {
			Context context = new InitialContext(); 
			ds = (DataSource) context.lookup("java:comp/env/jdbc/oracle");// java:comp/env/ + name
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static synchronized EmpDao getInstance() {
		if (empDao == null) {
			empDao = new EmpDao();
		}
		return empDao;
	}
	

	public int login(String userId, String userPw) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		query.append("SELECT pwd").append(" FROM adminlist").append(" WHERE userid = ?");
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(query.toString());
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				if (rs.getString("pwd").equals(userPw)) {
					return 1;
				} else {
					return 0;
				}
			}
			conn.close(); // 
		} catch (SQLException e) {
			System.err.println(e);
			System.err.println("login SQLException error");
		} finally {
			DB_Close.close(rs);
			DB_Close.close(pstmt);

		}
		return -1;
	}

	public int EmpTotal() { 
		String sql = "select count(*) from emp";
		Connection conn = null;
		int total = 0;
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				total = rs.getInt(1);
			}
			conn.close(); 
		} catch (SQLException e) {
			System.err.println(e);
			System.err.println("EmpTotal SQLException error");
		} finally {
			DB_Close.close(pstmt);
			DB_Close.close(rs);
		}
		return total;
	}

	public int EmpSalAvg() { 
		String sql = "select round(avg(sal),0) from emp";
		Connection conn = null;
		int avg = 0;
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				avg = rs.getInt(1);
			}
			conn.close(); // 
		} catch (SQLException e) {
			System.err.println(e);
			System.err.println("EmpSalAvg SQLException error");
		} finally {
			DB_Close.close(pstmt);
			DB_Close.close(rs);
		}
		return avg;
	}

	public int DeptTotal() { 
		String sql = "select count(distinct deptno) from emp";
		Connection conn = null;
		int total = 0;
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				System.out.println("???????????????????? : " + total);
				total = rs.getInt(1);
			}

			conn.close(); 
		} catch (SQLException e) {
			System.err.println(e);
			System.err.println("DeptTotal SQLException error");
		} finally {
			DB_Close.close(pstmt);
			DB_Close.close(rs);
		}
		return total;
	}

	public int EmpJobTotal() { 
		String sql = "select count(distinct job) from emp";
		Connection conn = null;
		int total = 0;
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				total = rs.getInt(1);
			}
			System.out.println("???????????? ???????????? ???????????? : " + total);
			conn.close(); 
		} catch (SQLException e) {
			System.err.println(e);
			System.err.println("EmpJobTotal SQLException error");
		} finally {
			DB_Close.close(pstmt);
			DB_Close.close(rs);
		}
		return total;
	}

	public int totallistCount() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int totalcount = 0;
		try {
			conn = ds.getConnection(); 
			String sql = "select count(*) cnt from emp";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				totalcount = rs.getInt("cnt");
			}
		} catch (Exception e) {

		} finally {
			try {
				pstmt.close();
				rs.close();
				conn.close();
			} catch (Exception e) {

			}
		}
		return totalcount;
	}

	public List<Emp> list(int cpage, int pagesize) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		List<Emp> list = null;
		try {
			conn = ds.getConnection();
			String sql = "select * from  " + "(select rownum rn, empno, ename, job, mgr, hiredate, sal, comm, deptno, filename"
					+ "             from emp " +
													
					"             where rownum <= ?" + 
					") where rn >= ?"; // start row
			pstmt = conn.prepareStatement(sql);

			int start = cpage * pagesize - (pagesize - 1); // 1*5 -(5-1) = 1
			int end = cpage * pagesize; // 1 * 5 = 5

			pstmt.setInt(1, end);
			pstmt.setInt(2, start);

			rs = pstmt.executeQuery();
			list = new ArrayList<Emp>();
			while (rs.next()) {
				Emp emp = new Emp();
				emp.setDeptno(rs.getLong("deptno"));
				emp.setEmpno(rs.getLong("empno"));
				emp.setEname(rs.getString("ename"));
				emp.setJob(rs.getString("job"));
				emp.setComm(rs.getLong("comm"));
				emp.setHiredate(rs.getDate("hiredate"));
				emp.setMgr(rs.getLong("mgr"));
				emp.setSal(rs.getLong("sal"));
				emp.setFilename(rs.getString("filename"));
				list.add(emp);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				pstmt.close();
				rs.close();
				conn.close();// 
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return list;
	}
	
	public List<Emp> ajaxList(int cpage, int pagesize) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		List<Emp> list = null;
		try {
			conn = ds.getConnection();
			String sql = "select * from emp";
			pstmt = conn.prepareStatement(sql);

			/*
			 * int start = cpage * pagesize - (pagesize - 1); // 1*5 -(5-1) = 1 int end =
			 * cpage * pagesize; // 1 * 5 = 5
			 * 
			 * pstmt.setInt(1, end); pstmt.setInt(2, start);
			 */

			rs = pstmt.executeQuery();
			list = new ArrayList<Emp>();
			while (rs.next()) {
				Emp emp = new Emp();
				emp.setDeptno(rs.getLong("deptno"));
				emp.setEmpno(rs.getLong("empno"));
				emp.setEname(rs.getString("ename"));
				emp.setJob(rs.getString("job"));
				emp.setComm(rs.getLong("comm"));
				//emp.setHiredate(rs.getDate("hiredate"));
				emp.setMgr(rs.getLong("mgr"));
				emp.setSal(rs.getLong("sal"));
				emp.setFilename(rs.getString("filename"));
				list.add(emp);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				pstmt.close();
				rs.close();
				conn.close();// 
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return list;
	}

	public List<Emp> searchEmpno(String empno) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Emp> list = new ArrayList<Emp>();

		try {
			conn = ds.getConnection();
			String sql = "select empno, ename, job, deptno, mgr, filename from emp where empno like ?";

			pstmt = conn.prepareStatement(sql);
			System.out.println("DAIO!! " + empno);
			pstmt.setString(1, "%" + empno + "%");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Emp emp = new Emp();
				emp.setEmpno(rs.getLong(1));
				emp.setEname(rs.getString(2));
				emp.setJob(rs.getString(3));
				emp.setDeptno(rs.getLong(4));
				emp.setMgr(rs.getLong(5));
				emp.setFilename(rs.getString(6));
				list.add(emp);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				DB_Close.close(rs);
				DB_Close.close(pstmt);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;

	}
	
	
	public List<Emp> searchEname(String ename) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Emp> list = new ArrayList<Emp>();

		try {
			conn = ds.getConnection();
			String sql = "select empno, ename, job, deptno, mgr, filename from emp where ename like ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + ename + "%");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Emp emp = new Emp();
				emp.setEmpno(rs.getLong(1));
				emp.setEname(rs.getString(2));
				emp.setJob(rs.getString(3));
				emp.setDeptno(rs.getLong(4));
				emp.setMgr(rs.getLong(5));
				emp.setFilename(rs.getString(6));
				list.add(emp);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				DB_Close.close(rs);
				DB_Close.close(pstmt);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;

	}
	
	
	//?????? ????????????
	public int insertEmp(long empno, String ename, String job, long mgr, String hiredate, long sal, long comm,
			long deptno, String filename) {
		Connection conn = null;// ??????

		try {
			conn = ds.getConnection();

			System.out.println(hiredate);
			String sql = "insert into emp(empno,ename,job,mgr,hiredate,sal,comm,deptno,filename) values(?,?,?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, empno);
			pstmt.setString(2, ename);
			pstmt.setString(3, job);
			pstmt.setLong(4, mgr);
			pstmt.setDate(5, transformDate(hiredate));
			pstmt.setLong(6, sal);
			pstmt.setLong(7, comm);
			pstmt.setLong(8, deptno);
			pstmt.setString(9, filename);

			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Insert : " + e.getMessage());
		} finally {
			DB_Close.close(pstmt);
			try {
				conn.close(); // ????????????
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	// ????????? yyyymmdd ???????????? ??????????????? ?????? Date??? ???????????? ?????????
	public Date transformDate(String date) {
		SimpleDateFormat beforeFormat = new SimpleDateFormat("yyyymmdd");

		// Date??? ???????????? ???????????? ?????? ????????? yyyy-mm-dd??? ???????????? ??????.
		SimpleDateFormat afterFormat = new SimpleDateFormat("yyyy-mm-dd");

		java.util.Date tempDate = null;

		// ?????? yyyymmdd?????? ?????? ???????????? java.util.Date????????? ?????????.
		try {
			tempDate = beforeFormat.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// java.util.Date??? yyyy-mm-dd ???????????? ???????????? String??? ????????????.
		String transDate = afterFormat.format(tempDate);

		// ????????? String ?????? Date??? ????????????.
		Date d = Date.valueOf(transDate);

		return d;
	}
	
	//?????? ????????????
	public int deleteEmp(long empno) {
		//??????????????? : ?????? ...
		
		//????????? ????????? : ?????? 
		/*
		 1. ????????? (????????? ?????? ??????)
		 2. ????????? (????????? ?????? ??????) : ?????? ??????
		 
		????????? (????????? ?????? ??????)
		case 1: ???????????? ????????? ????????? ????????? ??? ?????? (?????? refer delete)
		case 2: (?????????)???????????? ?????? -> ????????? ?????? (????????? ?????? (????????? ?????? ??????) (step, depth)
		case 3: ????????? ??????????????? ?????? ( ????????? ?????? (delok :???????????? ?????? :1) >> ?????? : 0 >> update .. 0
		case 4: ?????? ????????? ?????? (????????? ?????????) refer count > 1
		*/
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int row = 0;
		try {
				conn = ds.getConnection();
				//????????? ..
				//?????? > ?????? 
				//?????? > ????????? ,??????
				
				//????????????
				String sql_empno="select empno from emp where empno=?";
				
				//????????? ??????
				String sql_board="delete from emp where empno=?";
				
				pstmt = conn.prepareStatement(sql_empno);
				pstmt.setLong(1, empno);
				rs = pstmt.executeQuery();
				if(rs.next()) { //???????????? ??????
					//???????????? ????????? ?????? , DB ??????
						 //??? ?????? ??????
						 //???????????? (?????? ?????? , ?????? ??????)
						 //????????? ????????? ????????? ??????
						 //JDBC : auto commit 
						 conn.setAutoCommit(false);//???????????? rollback , commit ??????
						 	
						 	//????????? ?????? (????????? , ??????)
						 	pstmt = conn.prepareStatement(sql_board);
						 	pstmt.setLong(1,empno);
						 	row = pstmt.executeUpdate();
						 	
						 	if(row > 0) {
						 		conn.commit(); //????????? delete ?????????
	
					 }else { //??????????????? ?????? ?????? ?????? ??????
						  row = -1;
					 }
				}else { //???????????? ?????? ???????????? ?????? ??????
					row = 0;					
				}
				
				
		} catch (Exception e) {
			//rollback 
			//????????? ????????????
			try {
				conn.rollback();
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
		}finally {
			try {
				pstmt.close();
				rs.close();
				conn.close();//??????
			} catch (Exception e2) {
				
			}
		}
		return row;
	}
	
	
	public Emp detailList(Long empno) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Emp emp = new Emp();
		try {
			conn = ds.getConnection();
			String sql = "select * from emp where empno = ?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, empno);
			
			rs = pstmt.executeQuery();
			while (rs.next()) {
				emp.setDeptno(rs.getLong("deptno"));
				emp.setEmpno(rs.getLong("empno"));
				emp.setEname(rs.getString("ename"));
				emp.setJob(rs.getString("job"));
				emp.setComm(rs.getLong("comm"));
				emp.setHiredate(rs.getDate("hiredate"));
				emp.setMgr(rs.getLong("mgr"));
				emp.setSal(rs.getLong("sal"));
				emp.setFilename(rs.getString(("filename")));
			}

		} catch (Exception e) {
			System.out.println("?????? :" + e.getMessage());
		} finally {
			try {
				pstmt.close();
				rs.close();
				conn.close();// ??????
			} catch (Exception e2) {

			}
		}
		return emp;
	}
	
	public int updateOkEmp(long empno, String ename, String job, long mgr, String hiredate, long sal, long comm,
			long deptno, String filename) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = ds.getConnection();

			String sql = "update emp set ename = ?, job =?, mgr = ?, hiredate = ?, sal = ?,comm = ?, deptno = ?, filename = ? WHERE empno = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, ename);
			pstmt.setString(2, job);
			pstmt.setLong(3, mgr);
			pstmt.setDate(4, transformDate(hiredate));
			pstmt.setLong(5, sal);
			pstmt.setLong(6, comm);
			pstmt.setLong(7, deptno);
			pstmt.setString(8, filename);
			pstmt.setLong(9, empno);
			
			
			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Update : " + e.getMessage());
		} finally {
			try {
				pstmt.close();
				conn.close(); // ????????????
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
		
	}
	
	
	public List<Emp> chartList() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Emp> list = new ArrayList<Emp>();
		
		try {
			conn = ds.getConnection();
			
			  String sql = "select job , avg(sal)*12 sar\r\n" + "from emp\r\n" +
			  "group by job\r\n" + "order by sar desc";
			 
//			String sql = "select * from vv2";
			pstmt = conn.prepareStatement(sql);

			
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Emp emp = new Emp();
				emp.setJob(rs.getString("job"));
				emp.setSal(rs.getLong("sar"));
				
				list.add(emp);
				System.out.println(emp);
			}
		} catch (Exception e) {
			System.out.println("?????? :" + e.getMessage());
		} finally {
			try {
				pstmt.close();
				rs.close();
				conn.close();// ??????
			} catch (Exception e2) {

			}
		}

		return list;

	}
	
	
	
	public List<Emp> jobList() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List list = new ArrayList();

		try {
			conn = ds.getConnection();
			String sql = "select distinct job from emp";

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				list.add(rs.getString(1));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				DB_Close.close(rs);
				DB_Close.close(pstmt);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;

	}
	
	public List<Emp> deptNoList() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List list = new ArrayList();

		try {
			conn = ds.getConnection();
			String sql = "select distinct deptno from emp order by deptno asc";

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				list.add(rs.getString(1));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				DB_Close.close(rs);
				DB_Close.close(pstmt);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;

	}
	
	
	public List<Emp> dataTablelist() {
		String sql = "select * from emp";
		List<Emp> list = new ArrayList<>();
		Connection conn = null;
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Emp emp = new Emp();
				emp.setDeptno(rs.getLong("deptno"));
				emp.setEmpno(rs.getLong("empno"));
				emp.setEname(rs.getString("ename"));
				emp.setJob(rs.getString("job"));
				emp.setComm(rs.getLong("comm"));
				emp.setHiredate(rs.getDate("hiredate"));
				emp.setMgr(rs.getLong("mgr"));
				emp.setSal(rs.getLong("sal"));
				emp.setFilename(rs.getString("filename"));
				list.add(emp);
			}
			conn.close();
		} catch (Exception e) {
			System.out.println(e);
			System.err.println("selectList SQLException error");
		} finally {
	
			DB_Close.close(rs);
			DB_Close.close(pstmt);
		}
		return list;
	}
	
	public String empFilename(Long empno) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String filename = "";
		try {
			conn = ds.getConnection();
			String sql = "select filename from emp where empno = ?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, empno);
			
			rs = pstmt.executeQuery();
			while (rs.next()) {
				filename = rs.getString("filename");
			}

		} catch (Exception e) {
			System.out.println("?????? :" + e.getMessage());
		} finally {
			try {
				pstmt.close();
				rs.close();
				conn.close();// ??????
			} catch (Exception e2) {
				System.out.println("?????? :" + e2.getMessage());
			}
		}
		return filename;
	}
	
	
	
}
