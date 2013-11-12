package dataTier;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import common.FriendStatus;
import common.UserMetaData;

public final class DataAccess{

	private static DataAccess dataAccessInstance;
	private static Connection conn;
	private static Statement stat;
	
	/* Constructor */
	private DataAccess(){
		try{
		Class.forName("org.h2.Driver");
		//Para testeo se hara una base de datos en memoria.
		//TODO cambiar a servidor embebido para produccion.
//		conn = DriverManager.getConnection("jdbc:h2:~/test","sa","");
		conn = DriverManager.getConnection("jdbc:h2:mem:");
		stat = conn.createStatement();
		
		//Solo para base de datos en memoria.
		stat.execute("CREATE TABLE Usuarios(User varchar(100) primary key, Password varchar(100), Mail varchar(100), Apyn varchar(100), FechaNacimiento DateTime, FechaAlta DateTime, Conectado bit)");
		stat.execute("CREATE TABLE Amigos(User1 varchar(100), User2 varchar(100),Primary Key(User1,User2) ,FOREIGN KEY(User1) references Usuarios(User), Foreign Key(User2) references Usuarios(User))");
		stat.execute("CREATE TABLE LogLogin(User varchar(100), FechaHoraInicio DateTime,Primary Key(User,FechaHoraInicio),FOREIGN KEY(User) references Usuarios(User))");
		stat.execute("CREATE TABLE LogPenalizacion(ID int primary key auto_increment, User varchar(100), Descripcion varchar(250), FechaFin DateTime, Foreign key(User) references Usuarios(User))");
		stat.execute("INSERT INTO Usuarios (User,Password,Mail,FechaAlta,Apyn,FechaNacimiento,Conectado)VALUES" +
				"('Usuario1','Contrsenia','Mail', '2011-01-01 00:30:00','Perez','1991-01-01',0)," +
				"('Usuario2','Contrsenia','Mail', '2011-01-01 00:22:00','Lopez', '1991-01-01',1)," +
				"('Usuario3','Contrsenia','Mail', '2011-01-01 01:30:00','Susana', '1991-01-01',1)," +
				"('Usuario4','Contrsenia','Mail', '2011-01-01 03:20:00','Mirta', '1991-01-01',1)," +
				"('Usuario5','Contrsenia','Mail' ,'2011-01-01 04:40:00', 'Cintia','1991-01-01',1)," +
				"('Usuario6','Contrsenia','Mail', '2011-01-01 05:30:00', 'Jose','1991-01-01',0)");
		stat.execute("INSERT INTO logLogin(User,FechaHoraInicio) VALUES" +
				"('Usuario1','2013-10-13 00:30:00'),('Usuario2','2013-10-13 00:22:00')," +
				"('Usuario3', '2013-10-13 01:30:00'),('Usuario4', '2013-10-13 03:20:00')," +
				"('Usuario5','2013-10-20 04:40:00'),('Usuario6', '2013-10-15 05:30:00')");
		stat.execute("INSERT INTO Amigos (User1,User2) VALUES ('Usuario1','Usuario3'),('Usuario1','Usuario4'),('Usuario1','Usuario5'),('Usuario1','Usuario6')");
		stat.execute("INSERT INTO LogPenalizacion (User,Descripcion,FechaFin) VALUES('Usuario3','Ofensa','2013-10-30')");
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public static DataAccess getInstance(){
		if(dataAccessInstance==null)
			dataAccessInstance = new DataAccess();
		return dataAccessInstance;
	}
	
	//----------
	// Metodos
	//----------
	/*
	 * Obtiene todos los usuarios
	 * ***TESTEADA***
	 */
	public List<UserMetaData> getAllUsers() {
		List<UserMetaData> listaUsuarios = null;
		try {
			ResultSet rs =  stat.executeQuery("SELECT * FROM USUARIOS");
			listaUsuarios = new ArrayList<UserMetaData>();
			while(rs.next()) {
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
				listaUsuarios.add(data);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return listaUsuarios;
	}
	
	/*
	 * Inserta usuarios a la base de datos
	 */
	public void insertUser(UserMetaData user) {
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
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public void modifyUser(UserMetaData user) {
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
			ex.printStackTrace();
		}
	}
	
	/*
	 * Borra usuarios
	 */
	public void deleteUser(UserMetaData user) {
		try{
			stat.execute("DELETE FROM USUARIOS WHERE User="+user.getUser());
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	/*
	 * ***TESTEADA***
	 */
	public void blanquearClave(String user, String password) {
		try {
			PreparedStatement ps = conn.prepareStatement("UPDATE USUARIOS SET Password = ? WHERE User = ?");
			ps.setString(1, password);
			ps.setString(2, user);
			ps.execute();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/*
	 * ***TESTEADA***
	 */
	public void penalizar(String user, String motivo, long fechaFin) {
		try {
			PreparedStatement ps = conn.prepareStatement("INSERT INTO LogPenalizacion (User,Descripcion,FechaFin) VALUES(?,?,?)");
			ps.setString(1, user);
			ps.setString(2, motivo);
			ps.setDate(3, new Date(fechaFin));
			ps.execute();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/*
	 * ***TESTEADA***
	 */
	public void despenalizar(String user) {
		Calendar c = Calendar.getInstance();
		c.setTime(new java.util.Date());
		c.add(Calendar.DATE, -1);
		try {
			PreparedStatement ps = conn.prepareStatement("UPDATE LogPenalizacion SET FechaFin = ? WHERE User = ?");
			ps.setDate(1, new Date(c.getTime().getTime()));
			ps.setString(2, user);
			ps.execute();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/*
	 * ***TESTEADA***
	 */
	public UserMetaData getUserByUsername(String user) {
		UserMetaData data = null;
		try {
			ResultSet rs = stat.executeQuery("SELECT * FROM USUARIOS WHERE User='" + user + "'");
			if (rs.next()) {
				data = new UserMetaData();
				data.setUser(rs.getString("User"));
				data.setPassword(rs.getString("Password"));
				data.setApyn(rs.getString("Apyn"));
				data.setMail(rs.getString("Mail"));
				data.setFechaAlta(rs.getDate("FechaAlta"));
				data.setFechaNacimiento(rs.getDate("FechaNacimiento"));
				if (rs.getBoolean("Conectado") == true)
					data.setConectado(1);
				else
					data.setConectado(0);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return data;
	}

	public ResultSet getUsers(UserMetaData user)
	{
		try{
		return stat.executeQuery("SELECT * FROM USUARIOS WHERE User like"
				+ "'%"+user.getUser()
				+"%' or Apyn like'%"+ user.getApyn()
				+"%' or Mail like'%"+ user.getMail()
				+"%'");
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<FriendStatus> getFriends(UserMetaData user)
	{
		try{
			ArrayList<FriendStatus> friendList = new ArrayList<FriendStatus>();
			ResultSet rSet;
			
			String statement = "SELECT U1.User,Conectado FROM USUARIOS U1 INNER JOIN (SELECT User1 as User FROM AMIGOS WHERE User2='" +user.getUser()+"'"
					+ "UNION SELECT User2 as User FROM AMIGOS WHERE User1='" +user.getUser() +"') AS U2 ON U1.User= U2.User "; 
			rSet = stat.executeQuery(statement);
			
			while(rSet.next())
				friendList.add(new FriendStatus(rSet.getString("User"),rSet.getInt("Conectado")));
			
			return friendList;
		}		
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}

	/*
	 * ***TESTEADA***
	 */
	public List<String> getLoginHistory(String user) {
		List<String> listaLogin = null;
		try {
			//TODO reveer query
			//stat.executeQuery("SELECT * FROM LOGLOGIN WHERE User ='" + user + "' AND DATEADD(day,15,FECHAHORAINICIO)");
			ResultSet rSet = stat.executeQuery("SELECT * FROM LOGLOGIN WHERE User ='" + user + "'");
			listaLogin = new ArrayList<String>();
			while(rSet.next()) {
				listaLogin.add(rSet.getString("FechaHoraInicio"));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return listaLogin;
	}

	public boolean checkUser(UserMetaData user){
		try{
			String statement = "SELECT 1 FROM USUARIOS WHERE User='"+ user.getUser()
					+"' and Password='"+ user.getPassword() +"'";
			return stat.executeQuery(statement).first();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		return false;
	}
	
	public BanInfo checkBan(String user){
		//Devuelve cantidad de dias restantes y descripcion.
		try{
			 String statement = "SELECT p.Descripcion, DateDiff('day',CURRENT_DATE(),p.fechafin) as Restantes from LOGPENALIZACION p INNER JOIN USUARIOS u on u.User = p.user WHERE p.User='"+ user +"'";
			 ResultSet rs = stat.executeQuery(statement);
			 if(!rs.first())
				 return null;
			 return new BanInfo(rs.getInt("Restantes"),rs.getString("Descripcion"));
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return new BanInfo(0,"");
	}
	
	
	public static void main(String arg[])
	{
		UserMetaData data = new UserMetaData();
		data.setUser("Usuario3");
		data.setPassword("Contrasenia");
		BanInfo info = DataAccess.getInstance().checkBan(data.getUser());
	}



}