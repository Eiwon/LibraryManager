package lib.view;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AlertDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private static AlertDialog instance = null;
	
	private AlertDialog(String msg) {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		setBounds(750, 300, 897, 300);
		getContentPane().setLayout(null);

		JLabel lblMsg = new JLabel();
		lblMsg.setBounds(70, 42, 746, 57);
		getContentPane().add(lblMsg);
		lblMsg.setText(msg);
		lblMsg.setHorizontalAlignment(SwingConstants.CENTER);
		lblMsg.setFont(new Font("궁서", Font.BOLD, 43));
		
		JButton btnClose = new JButton("닫기");
		btnClose.setBounds(395, 156, 106, 57);
		getContentPane().add(btnClose);
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnClose.setFont(new Font("궁서", Font.BOLD, 22));
		
	} // end AlertDialog()
	
	public static void printMsg(String msg) {
		if(instance != null) {
			instance.dispose();
		}
		instance = new AlertDialog(msg);
	}
	
} // end AlertDialog
