package interfaces.servidor;

import java.util.HashMap;

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
        
//        jTable1.setModel(new javax.swing.table.DefaultTableModel(
//            new Object [][] {
//                {null, null, null, null},
//                {null, null, null, null},
//                {null, null, null, null},
//                {null, null, null, null}
//            },
//            new String [] {
//                "Title 1", "Title 2", "Title 3", "Title 4"
//            }
//        ));
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
    	String[][] matPuntuacion = matPuntuacion = new String[1][4];
    	try{
    		if(nombre!=null && nombre!="")
    		{
    		HashMap<String,Integer> puntuacion = ChatServer.getInstance().obtenerPuntacionPorUsuario(nombre);
    		matPuntuacion[0][0] = nombre;
    		matPuntuacion[0][1] = puntuacion.get("Ganados").toString();
    		matPuntuacion[0][2] = puntuacion.get("Empatados").toString();
    		matPuntuacion[0][3] = puntuacion.get("Perdidos").toString();
    		}
    		else
    		{
    			
    		}
    		return matPuntuacion;
    	}
    	catch(Exception ex)
    	{
    		ex.printStackTrace();
    		return matPuntuacion;
    	}
    }
}
