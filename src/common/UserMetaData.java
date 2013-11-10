package common;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class UserMetaData implements Serializable {

	private static final long serialVersionUID = -3375334612913839297L;

	private String user;
	private String password;
	private String apyn;
	private String mail;
	private String telefono;
	private Date fechaAlta;
	private Date fechaNacimiento;
	private int conectado;

	public UserMetaData() {
		this("", "", "", "", "", Calendar.getInstance().getTime(), Calendar.getInstance().getTime(), 0);
	}

	public UserMetaData(String nickName, String passWord, String apyn, String mail, String tel, Date fechaAlta, Date fechaNacimiento, int conectado) {
		this.user = nickName;
		this.password = passWord;
		this.apyn = apyn;
		this.mail = mail;
		this.telefono = tel;
		this.fechaAlta = fechaAlta;
		this.fechaNacimiento = fechaNacimiento;
		this.conectado = conectado;
	}

	// Constructor usado para el login.
	public UserMetaData(String nickName, char[] passWord) {
		this.user = nickName;
		this.password = new String(passWord);
		this.apyn = "";
		this.mail = "";
		this.telefono = "";
		this.fechaAlta = null;
		this.fechaNacimiento = null;
		this.conectado = 0;
	}

	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getApyn() {
		return apyn;
	}
	public void setApyn(String apyn) {
		this.apyn = apyn;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public Date getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	public int getConectado() {
		return conectado;
	}
	public void setConectado(int conectado) {
		this.conectado = conectado;
	}

	@Override
	public String toString() {
		return "UserMetaData [user=" + user + ", password=" + password + ", apyn=" + apyn + ", mail=" + mail + ", telefono="
				+ telefono + ", fechaAlta=" + fechaAlta + ", fechaNacimiento=" + fechaNacimiento + ", conectado=" + conectado + "]";
	}

}
