package dataTier;
import java.sql.*;

import common.UserMetaData;

public final class DataAccess{

	private static DataAccess dataAccessInstance;
	private static Connection conn;
	private static Statement stat;
	
	
	private DataAccess(){
		try{
		Class.forName("org.h2.Driver");
		conn = DriverManager.getConnection("jdbc:h2:~/test","sa","");
		stat = conn.createStatement();
		}
		catch(Exception ex)
		{
		
		}
	}
	
	public static DataAccess getInstance(){
		if(dataAccessInstance==null)
			dataAccessInstance = new DataAccess();
		return dataAccessInstance;
	}
	

	public ResultSet GetAllUsers() {
		try{
			return stat.executeQuery("SELECT * FROM USUARIOS");
		}
		catch(Exception ex)
		{
			return null;
		}
	}

	public void InsertUser(UserMetaData user) {
	try{
		stat.execute("INSERT INTO USUARIOS VALUES('"+user.getUser()
				+"','"+user.getPassword()
				+"','"+user.getApyn()
				+"','"+user.getMail()
				+"',"+user.getFechaAlta()
				+","+user.getFechaNacimiento()
				+","+user.getConectado()
				+ ")");
	}
	catch(Exception ex)
	{
	
	}
	}

	public void ModifyUser(UserMetaData user) {
		// TODO Auto-generated method stub
		try{
		stat.execute("UPDATE USUARIOS SET Password = "+ user.getPassword() 
				+ ",Mail="+ user.getMail()
				+ ",Apyn="+ user.getApyn()
				+ ",FechaNacimiento="+user.getFechaNacimiento()
				+ ",FechaAlta="+user.getFechaAlta()
				+ ",Conectado="+user.getConectado()
				+" WHERE User ="+ user.getUser());
		}
		catch(Exception ex)
		{
			
		}
	}

	public void DeleteUser(UserMetaData user) {
		// TODO Auto-generated method stub
		try{
			stat.execute("DELETE FROM USUARIOS WHERE User="+user.getUser());
		}
		catch(Exception ex)
		{
			
		}
	}

	public UserMetaData GetUserByUsername(String user) {
		// TODO Auto-generated method stub
		try{
			ResultSet rs =  stat.executeQuery("SELECT * FROM USUARIOS WHERE User='"+user+"'");
			if(rs.getFetchSize()==0)
				return null;
			rs.next();
			UserMetaData data = new UserMetaData();
			data.setUser(rs.getString("User"));
			data.setPassword(rs.getString("Password"));
			data.setApyn(rs.getString("Apyn"));
			data.setMail(rs.getString("Mail"));
			data.setFechaAlta(rs.getDate("FechaAlta"));
			data.setFechaNacimiento(rs.getDate("FechaNacimiento"));
			if(rs.getBoolean("Conectado")==true)
				data.setConectado(1);
			else
				data.setConectado(0);
			return data;
			
		}
		catch(Exception ex)
		{
			return null;
		}
	}

	public ResultSet GetUsers(UserMetaData user)
	{
		try{
			String statement = "SELECT * FROM USUARIOS WHERE User like'%"+user.getUser()
					+"%' or Apyn like'%"+ user.getApyn()
					+"%' or Mail like'%"+ user.getMail()
					+"%'"; 
		return stat.executeQuery("SELECT * FROM USUARIOS WHERE User like"
				+ "'%"+user.getUser()
				+"%' or Apyn like'%"+ user.getApyn()
				+"%' or Mail like'%"+ user.getMail()
				+"%'");
		}
		catch(Exception ex)
		{
			return null;
		}
	}
	
	public ResultSet GetFriends(UserMetaData user)
	{
		try{
			String statement = "SELECT U1.User, Password,Mail,Apyn,FechaNacimiento,FechaAlta,Conectado FROM USUARIOS U1 INNER JOIN (SELECT Usuario1 as User FROM AMIGOS WHERE Usuario2='" +user.getUser()+"'"
					+ "UNION SELECT Usuario2 as User FROM AMIGOS WHERE Usuario1='" +user.getUser() +"') AS U2 ON U1.User= U2.User "; 
			return stat.executeQuery(statement);
		}
		catch(Exception ex)
		{
			return null;
		}
	}
	
	public ResultSet GetLoginHistory(UserMetaData user)
	{
		try{			
			return stat.executeQuery("SELECT * FROM LOGLOGIN WHERE User ='"+ user.getUser() +"' AND DATEADD(day,15,FECHAHORAINICIO)");
		}
		catch(Exception ex)
		{
			return null;
		}
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
//			User guillermo = new User("ghiroma3","","guille","guillermo.hiroma@gmail.com",null,null,0);
//			DataAccess.getInstance().InsertUser(guillermo);
//			User user = DataAccess.getInstance().GetUserByUsername("ghiroma");
//			System.out.println(user);
//			ResultSet rs = DataAccess.getInstance().GetUsers(new User("ghiro","","gui","gui",null,null,0));
			UserMetaData user = new UserMetaData("ghiroma","","","",null,null,0);
			ResultSet rs = DataAccess.getInstance().GetFriends(user);
			System.out.println("hola");
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}

	}


}