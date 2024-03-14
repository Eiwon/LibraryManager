package lib.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import lib.controller.UserDAOImple;
import lib.controller.UserManager;
import lib.model.UserVO;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;

public class LoginFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panelLogin;
	private JPanel panelSignup;
	private JTextField txtId;
	private JPasswordField txtPw;
	private JTextField txtName;
	private JTextField txtPhone;
	private JTextField txtEmail;
	private JTextField txtSignupId;
	private JPasswordField txtSignupPw;
	private JButton btnComplete = null;
	private static final int frameX = 100;
	private static final int frameY = 100;
	private static final int frameWidth = 600;
	private static final int frameHeight = 600;
	
	private String idRex = "^[a-zA-Z][a-zA-Z0-9]{1,20}$";
	
	private UserDAOImple userDao = null;
	private boolean validId = false;
	private UserVO curUser = null;
	
	public LoginFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(frameX, frameY, frameWidth, frameHeight);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		userDao = UserDAOImple.getInstance();
		setLoginPanel();
		setSignupPanel();
		panelSignup.setVisible(false);
	} // end LoginFrame
	
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
		
		txtId = new JTextField();
		txtId.setBounds(164, 95, 313, 42);
		panelLogin.add(txtId);
		txtId.setColumns(10);
		
		txtPw = new JPasswordField();
		txtPw.setColumns(10);
		txtPw.setBounds(164, 200, 313, 42);
		
		panelLogin.add(txtPw);
		
		JButton btnLogin = new JButton("로그인");
		btnLogin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				curUser = login();
				if(curUser == null) {
					new AlertDialog("잘못된 아이디 또는 비밀번호 입니다.");
				}else {
					new AlertDialog("로그인 성공");
					
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
		
		txtSignupId = new JTextField();
		txtSignupId.setBounds(164, 61, 292, 38);
		panelSignup.add(txtSignupId);
		txtSignupId.setColumns(10);
		txtSignupId.getDocument().addDocumentListener(new DocumentListener() {
			
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
				validCheck();
			}
		}); // end btnIdCheckListener
		btnIdCheck.setBounds(470, 61, 100, 50);
		panelSignup.add(btnIdCheck);
		
		txtSignupPw = new JPasswordField();
		txtSignupPw.setBounds(164, 124, 292, 38);
		panelSignup.add(txtSignupPw);
		txtSignupPw.setColumns(10);
		
		txtName = new JTextField();
		txtName.setColumns(10);
		txtName.setBounds(164, 180, 292, 38);
		panelSignup.add(txtName);
		
		txtPhone = new JTextField();
		txtPhone.setColumns(10);
		txtPhone.setBounds(164, 241, 292, 38);
		panelSignup.add(txtPhone);
		
		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		txtEmail.setBounds(164, 303, 292, 38);
		panelSignup.add(txtEmail);
		
		btnComplete = new JButton("가입하기");
		btnComplete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(signup()) {
					goToBack();	
				}
			}
		}); // end btnComplete listener
		btnComplete.setBounds(frameX + 50, frameY + 300, 100, 50);
		panelSignup.add(btnComplete);
		
		JButton btnBack = new JButton("뒤로 가기");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToBack();
			}
		});
		btnBack.setBounds(frameX + 200, frameY + 300, 100, 50);
		panelSignup.add(btnBack);
	} // end setSingupPanel
	
	private void validCheck() {
		if(Pattern.matches(idRex, txtSignupId.getText())) {
			UserVO vo = userDao.selectByID(txtSignupId.getText());
			if(vo == null){
				new AlertDialog("사용할 수 있는 ID 입니다.");
				validId = true;
			}else {
				new AlertDialog("이미 존재하는 ID 입니다.");
			}
		}else {
			new AlertDialog("잘못된 ID 형식");
		}
	} // end validCheck
	
	private boolean signup() {
		String Id = txtSignupId.getText();
		String pw = new String(txtSignupPw.getPassword());
		String name = txtName.getText();
		String phone = txtPhone.getText();
		String email = txtEmail.getText();
		if(pw.length() * name.length() * phone.length() * email.length() != 0) {
			// 입력되지 않은 필드가 있는지 확인
			if(validId == true) {
				int res = userDao.insertUser(new UserVO(Id, pw, name, phone, email));
				if(res == 1) {
					new AlertDialog("유저 등록 성공");
					return true;
				}else {
					new AlertDialog("유저 등록 실패");
				}
			} else {
				new AlertDialog("아이디 중복 체크 필요");
			}
		}else {
			new AlertDialog("모든 필드 입력 필요");
		}
		return false;
	} // end signup
	
	private void goToBack() {
		panelLogin.setVisible(true);
		panelSignup.setVisible(false);
	} // end goToBack
	
	private UserVO login() {
		String userId = txtId.getText();
		char[] pwBuffer = txtPw.getPassword();
		UserVO vo = null;
		
		if(userId.length() * pwBuffer.length > 0) {
			String pw = new String(pwBuffer);
			vo = userDao.selectWithPw(userId, pw);
			return vo;
		}else {
			new AlertDialog("아이디와 비밀번호를 입력해주세요.");
		}
		return null;
	} // end login

	public UserVO getCurUser() {
		return curUser;
	} // end getCurUser
}
