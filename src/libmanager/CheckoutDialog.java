package libManager;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Model.BookVO;
import libManager.Controller.BookDAOImple;
import libManager.Controller.UserDAOImple;
import libManager.Interface.OracleBookQuery;

import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CheckoutDialog extends JDialog {

	private static final int MAX_CHECK_OUT_TIME = 7;
	private final JPanel contentPanel = new JPanel();
	private JTextPane txtBookInfo;
	public CheckoutDialog(BookVO vo) {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		setBounds(100, 100, 525, 411);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 512, 339);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		
		txtBookInfo = new JTextPane();
		txtBookInfo.setBounds(32, 10, 461, 300);
		printBookInfo(vo);
		txtBookInfo.setEditable(false);
		txtBookInfo.setVisible(true);
		contentPanel.add(txtBookInfo);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 339, 495, 33);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane);
			{
				JButton btnCheckout = new JButton("대여하기");
				if(vo.getState().equals(OracleBookQuery.BOOK_STATE_SET) == false) {
					//책이 '대여가능' 이외의 상태면 대여버튼 비활성화
					btnCheckout.setEnabled(false);
				}
				btnCheckout.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String userId = UserDAOImple.getCurrentUser().getUserId();
						String state = OracleBookQuery.BOOK_STATE_OUT;
						String returnTime 
						= LocalDateTime.now().plusDays(MAX_CHECK_OUT_TIME).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
						
						BookDAOImple.getInstance().checkoutBook(vo.getBookId(), userId, state, returnTime);
					}
				});
				buttonPane.add(btnCheckout);
				getRootPane().setDefaultButton(btnCheckout);
			}
			{
				JButton btnExit = new JButton("취소");
				btnExit.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				buttonPane.add(btnExit);
			}
		}
	}
	private void printBookInfo(BookVO vo) {
		String contents = "";
		contents = contents.concat("제목 : " + vo.getName() + "\n");
		contents = contents.concat("도서 코드 : " + vo.getBookId() + "\n");
		contents = contents.concat("저자 : " + vo.getWriter() + "\n");
		contents = contents.concat("장르 : " + vo.getCategory() + "\n");
		contents = contents.concat("상태 : " + vo.getState() + "\n");
		
		if(vo.getState().equals(OracleBookQuery.BOOK_STATE_OUT) || vo.getState().equals(OracleBookQuery.BOOK_STATE_RSV)) {
			String returnTime = BookDAOImple.getInstance().selectCheckinDate(vo.getBookId());
			contents =contents.concat("반납 예정일 : " + returnTime.substring(0, 10));
		}
		
		txtBookInfo.setText(contents);
	} // end printBookInfo
}
