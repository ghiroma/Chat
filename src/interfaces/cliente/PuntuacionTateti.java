package interfaces.cliente;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class PuntuacionTateti extends JFrame {

	private JPanel contentPane;
	private JTable table;
	
	/**
	 * Create the frame.
	 */
	public PuntuacionTateti(String[][] puntuacion) {
		setTitle("Mi Puntuación");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable(puntuacion,new String[]{"Ganados","Empatados","Perdidos"});
		scrollPane.setViewportView(table);
	}

}
