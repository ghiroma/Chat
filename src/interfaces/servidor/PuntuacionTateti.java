package interfaces.servidor;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTable;

import server.ChatServer;

public class PuntuacionTateti extends javax.swing.JFrame{

	private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
	
    public PuntuacionTateti(String user)
    {
        setTitle("Puntacion");
		/* Icono del frame */
		ImageIcon img = new ImageIcon(getClass().getResource("icon.png"));
		setIconImage(img.getImage());
		
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        jButton1.setText("Cerrar");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        
        String[] columnNames = {"Nombres","Ganados","Empatados","Perdidos"};
        	jTable1 = new JTable(ObtenerPuntuacionPorNombre(user),columnNames);
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(258, 258, 258)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        pack();
        }
    
    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {
        this.dispose();
    }
    
    private String[][] ObtenerPuntuacionPorNombre(String nombre)
    {
    	String[][] matPuntuacion;
    	try{
    		if(nombre!=null && nombre!="")
    		{
    			matPuntuacion = new String[1][4];
    		HashMap<String,Integer> puntuacion = ChatServer.getInstance().obtenerPuntacionPorUsuario(nombre);
    		matPuntuacion[0][0] = nombre;
    		matPuntuacion[0][1] = puntuacion.get("Ganados").toString();
    		matPuntuacion[0][2] = puntuacion.get("Empatados").toString();
    		matPuntuacion[0][3] = puntuacion.get("Perdidos").toString();
    		return matPuntuacion;
    		}
    		else
    		{
    			ArrayList<ArrayList<String>> tmp = ChatServer.getInstance().obtenerPuntuaciones();
    			matPuntuacion = new String[tmp.size()][4];
    			for(int i=0;i<tmp.size();i++)
    			{
    				matPuntuacion[i][0] = tmp.get(i).get(0);
    				matPuntuacion[i][1] = tmp.get(i).get(1);
    				matPuntuacion[i][2] = tmp.get(i).get(2);
    				matPuntuacion[i][3] = tmp.get(i).get(3);
    			}
    			return matPuntuacion;
    		}
    	}
    	catch(Exception ex)
    	{
    		ex.printStackTrace();
    		return new String[0][0];
    	}
    }
}
