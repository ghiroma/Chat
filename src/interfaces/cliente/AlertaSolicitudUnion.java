package interfaces.cliente;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;

import client.ChatClient;
import common.MensajeSolicitudGrupo;

public class AlertaSolicitudUnion extends JDialog {

	private static final long serialVersionUID = -5493969291771977091L;

	private JLabel lblNewLabel;
	private JButton okButton;
	private JButton cancelButton;

	private MensajeSolicitudGrupo msgInvitacion;

	/**
	 * Create the dialog.
	 */
	public AlertaSolicitudUnion(MensajeSolicitudGrupo msgInv) {
		/* Icono del frame */
		ImageIcon img = new ImageIcon(getClass().getResource("icon.png"));
		setIconImage(img.getImage());
		
		this.msgInvitacion = msgInv;
		setBounds(100, 100, 454, 218);
		{
			lblNewLabel = new JLabel("quiere unirse al grupo..");
			lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		}
		{
			okButton = new JButton("Permitir");
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					ChatClient.getInstance().aceptacionSolicitudUnionGrupo(msgInvitacion);
					dispose();
				}
			});
			okButton.setActionCommand("OK");
			getRootPane().setDefaultButton(okButton);
		}
		{
			cancelButton = new JButton("Rechazar");
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					dispose();
				}
			});
			cancelButton.setActionCommand("Cancel");
		}
		
		JLabel lblNombreAmigo = new JLabel(msgInvitacion.getUser());
		lblNombreAmigo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addGap(72)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblNombreAmigo)
							.addPreferredGap(ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
							.addComponent(lblNewLabel)
							.addGap(98))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(okButton, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
							.addGap(73)
							.addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(76, Short.MAX_VALUE))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(33)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNombreAmigo))
					.addGap(28)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(okButton, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
						.addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE))
					.addGap(14))
		);
		getContentPane().setLayout(groupLayout);
		new SoundClient("invitacion.wav").play();
	}
}
