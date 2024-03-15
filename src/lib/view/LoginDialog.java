package lib.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import lib.Interface.UserDAO;
import lib.controller.UserDAOImple;
import lib.controller.UserManager;
import lib.model.UserVO;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;

public class LoginDialog extends JDialog {
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panelLogin;
	private JPanel panelSignup;

	private String idRex = "^[a-zA-Z][a-zA-Z0-9]{1,20}$";
	private UserDAO userDao = null;
	private boolean validId = false;
	
	public LoginDialog() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
		setBounds(750, 300, 600, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		userDao = UserDAOImple.getInstance();
		setLoginPanel();
		setSignupPanel();
		panelSignup.setVisible(false);
	} // end LoginDialog
	
	public void setLoginPanel() {

		panelLogin = new JPanel();
		panelLogin.setBounds(12, 10, 557, 499);
		contentPane.add(panelLogin);
		panelLogin.setLayout(null);
		
		JLabel lblId = new JLabel("아이디");
		lblId.setBounds(50, 101, 98, 28);
		panelLogin.add(lblId);
		
		JLabel lblPw = new JLabel("비밀번호");
		lblPw.setBounds(50, 206, 98, 28);
		panelLogin.add(lblPw);
		
		JTextField txtId = new JTextField();
		txtId.setBounds(164, 95, 313, 42);
		panelLogin.add(txtId);
		txtId.setColumns(10);
		
		JPasswordField txtPw = new JPasswordField();
		txtPw.setColumns(10);
		txtPw.setBounds(164, 200, 313, 42);
		
		panelLogin.add(txtPw);
		
		JButton btnLogin = new JButton("로그인");
		btnLogin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				UserVO curUser = login(txtId.getText(), String.valueOf(txtPw.getPassword()));
				if(curUser == null) {
					AlertDialog.printMsg("잘못된 아이디 또는 비밀번호 입니다.");
				}else {
					AlertDialog.printMsg("로그인 성공");
					UserManager.setCurrentUser(curUser);
					dispose();
				}
			}
		});
		btnLogin.setBounds(164, 303, 183, 36);
		panelLogin.add(btnLogin);
		
		JButton btnSignUp = new JButton("회원가입");
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelLogin.setVisible(false);
				panelSignup.setVisible(true);
				txtId.setText("");
				txtPw.setText("");
			}
		});
		btnSignUp.setBounds(164, 382, 186, 42);
		panelLogin.add(btnSignUp);
	} // end setLoginPanel

	public void setSignupPanel() {
		
		panelSignup = new JPanel();
		panelSignup.setBounds(12, 10, 557, 499);
		contentPane.add(panelSignup);
		panelSignup.setLayout(null);
		
		JLabel lblId = new JLabel("아이디");
		lblId.setBounds(52, 63, 77, 32);
		panelSignup.add(lblId);
		
		JLabel lblPw = new JLabel("비밀번호");
		lblPw.setBounds(52, 115, 77, 38);
		panelSignup.add(lblPw);
		
		JLabel lblName = new JLabel("이름");
		lblName.setBounds(50, 180, 79, 38);
		panelSignup.add(lblName);
		
		JLabel lblPhone = new JLabel("전화번호");
		lblPhone.setBounds(52, 241, 77, 38);
		panelSignup.add(lblPhone);
		
		JLabel lblEmail = new JLabel("이메일");
		lblEmail.setBounds(50, 303, 77, 38);
		panelSignup.add(lblEmail);
		
		JLabel lblSignup = new JLabel("회원가입");
		lblSignup.setBounds(156, 0, 227, 38);
		panelSignup.add(lblSignup);
		
		JTextField txtId = new JTextField();
		txtId.setBounds(164, 61, 292, 38);
		panelSignup.add(txtId);
		txtId.setColumns(10);
		txtId.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				validId = false;
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				validId = false;
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {}
		});
		
		JButton btnIdCheck = new JButton("중복 확인");
		btnIdCheck.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				validId = isValidId(txtId.getText());
			}
		}); // end btnIdCheckListener
		btnIdCheck.setBounds(470, 61, 100, 50);
		panelSignup.add(btnIdCheck);
		
		JPasswordField txtPw = new JPasswordField();
		txtPw.setBounds(164, 124, 292, 38);
		panelSignup.add(txtPw);
		txtPw.setColumns(10);
		
		JTextField txtName = new JTextField();
		txtName.setColumns(10);
		txtName.setBounds(164, 180, 292, 38);
		panelSignup.add(txtName);
		
		JTextField txtPhone = new JTextField();
		txtPhone.setColumns(10);
		txtPhone.setBounds(164, 241, 292, 38);
		panelSignup.add(txtPhone);
		
		JTextField txtEmail = new JTextField();
		txtEmail.setColumns(10);
		txtEmail.setBounds(164, 303, 292, 38);
		panelSignup.add(txtEmail);
		
		JButton btnComplete = new JButton("가입하기");
		btnComplete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(signup(txtId.getText(), String.valueOf(txtPw.getPassword()), txtName.getText(),
						txtPhone.getText(), txtEmail.getText())) {
					txtId.setText("");
					txtPw.setText("");
					txtName.setText("");
					txtPhone.setText("");
					txtEmail.setText("");
					goToBack();	
				}
			}
		}); // end btnComplete listener
		btnComplete.setBounds(150, 400, 100, 50);
		panelSignup.add(btnComplete);
		
		JButton btnBack = new JButton("뒤로 가기");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtId.setText("");
				txtPw.setText("");
				txtPhone.setText("");
				txtEmail.setText("");
				txtName.setText("");
				goToBack();
			}
		});
		btnBack.setBounds(300, 400, 100, 50);
		panelSignup.add(btnBack);
	} // end setSingupPanel
	
	private boolean isValidId(String userId) {
		String inputId = userId;
		if(!Pattern.matches(idRex, inputId)) {
			AlertDialog.printMsg("잘못된 ID 형식");
			return false;
		}
		UserVO vo = userDao.selectByID(inputId);
		if(vo == null){
			AlertDialog.printMsg("사용할 수 있는 ID 입니다.");
			return true;
		}else {
			AlertDialog.printMsg("이미 존재하는 ID 입니다.");
			return false;
		}
	} // end validCheck
	
	private boolean signup(String userId, String pw, String name, String phone, String email) {
		if(pw.length() * name.length() * phone.length() * email.length() == 0) {
			// 입력되지 않은 필드가 있는지 확인
			AlertDialog.printMsg("모든 필드 입력 필요");
			return false;
		}
		if(validId == false) {
			AlertDialog.printMsg("아이디 중복 체크 필요");
			return false;
		}
		int res = userDao.insertUser(new UserVO(userId, pw, name, phone, email));
		if(res == 1) {
			AlertDialog.printMsg("유저 등록 성공");
			return true;
		}else {
			AlertDialog.printMsg("유저 등록 실패");
			return false;
		}
	} // end signup
	
	private void goToBack() {
		panelLogin.setVisible(true);
		panelSignup.setVisible(false);
	} // end goToBack
	
	private UserVO login(String userId, String pw) {
		
		if(userId.length() * pw.length() > 0) {
			return userDao.selectWithPw(userId, pw);
		}else {
			AlertDialog.printMsg("아이디와 비밀번호를 입력해주세요.");
		}
		return null;
	} // end login
	
}
