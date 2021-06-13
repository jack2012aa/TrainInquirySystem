package page;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class TimeTable extends JFrame implements ActionListener, TrainInquirySystem {
	
	public class searchResultTable extends JPanel implements ActionListener{
		
		private static final int insetsBetweenColumn = 15;
		private Calendar departureDate;
		private int direction;
		private JButton front10, next10;
		private JPanel timeTable;
		int index = 0;
		int totalTrainCount = 0;

		public searchResultTable(Calendar departureDate, int direction) {
			this.departureDate = departureDate;
			this.direction = direction;
			this.setTotalTrainCount();
			this.buildPanel();

		}
		
		private void setTotalTrainCount() {
			try {
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/highspeedrail",
				           "root", test.PASSWORD);
				Statement stmt = conn.createStatement();
				ResultSet result = stmt.executeQuery(String.format("select count(*) "
						+ "from train natural join serviceday "
						+ "where day = '%s' and direction = %d;", SimpleQuery.getDay(this.departureDate), this.direction));
				result.next();
				this.totalTrainCount = result.getInt(1);
				result.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}

		public JPanel timeTableFactory() {
			JPanel timeTable = new JPanel();
			timeTable.setLayout(new GridBagLayout());
			GridBagConstraints gridBag = new GridBagConstraints();
			gridBag.gridx = 0;
			gridBag.gridy = 0;
			gridBag.ipadx = insetsBetweenColumn;
			gridBag.fill = GridBagConstraints.BOTH;
			
			//Header row	
			JLabel trainNoLabel = new JLabel("Train No");
			trainNoLabel.setOpaque(true);
			trainNoLabel.setBackground(Color.LIGHT_GRAY);
			timeTable.add(trainNoLabel, gridBag);
			
			JLabel �n�� = new JLabel("�n��");
			�n��.setOpaque(true);
			�n��.setBackground(Color.LIGHT_GRAY);
			gridBag.gridx++;
			timeTable.add(�n��, gridBag);
			
			JLabel �x�_ = new JLabel("�x�_");
			�x�_.setOpaque(true);
			�x�_.setBackground(Color.LIGHT_GRAY);
			gridBag.gridx++;
			timeTable.add(�x�_, gridBag);
			
			JLabel �O�� = new JLabel("�O��");
			�O��.setOpaque(true);
			�O��.setBackground(Color.LIGHT_GRAY);
			gridBag.gridx++;
			timeTable.add(�O��, gridBag);

			JLabel ��� = new JLabel("���");
			���.setOpaque(true);
			���.setBackground(Color.LIGHT_GRAY);
			gridBag.gridx++;
			timeTable.add(���, gridBag);
			
			JLabel �s�� = new JLabel("�s��");
			�s��.setOpaque(true);
			�s��.setBackground(Color.LIGHT_GRAY);
			gridBag.gridx++;
			timeTable.add(�s��, gridBag);
			
			JLabel �]�� = new JLabel("�]��");
			�]��.setOpaque(true);
			�]��.setBackground(Color.LIGHT_GRAY);
			gridBag.gridx++;
			timeTable.add(�]��, gridBag);
			
			JLabel �x�� = new JLabel("�x��");
			�x��.setOpaque(true);
			�x��.setBackground(Color.LIGHT_GRAY);
			gridBag.gridx++;
			timeTable.add(�x��, gridBag);
			
			JLabel ���� = new JLabel("����");
			����.setOpaque(true);
			����.setBackground(Color.LIGHT_GRAY);
			gridBag.gridx++;
			timeTable.add(����, gridBag);
			
			JLabel ���L = new JLabel("���L");
			���L.setOpaque(true);
			���L.setBackground(Color.LIGHT_GRAY);
			gridBag.gridx++;
			timeTable.add(���L, gridBag);
			
			JLabel �Ÿq = new JLabel("�Ÿq");
			�Ÿq.setOpaque(true);
			�Ÿq.setBackground(Color.LIGHT_GRAY);
			gridBag.gridx++;
			timeTable.add(�Ÿq, gridBag);
			
			JLabel �x�n = new JLabel("�x�n");
			�x�n.setOpaque(true);
			�x�n.setBackground(Color.LIGHT_GRAY);
			gridBag.gridx++;
			timeTable.add(�x�n, gridBag);
			
			JLabel ���� = new JLabel("����");
			����.setOpaque(true);
			����.setBackground(Color.LIGHT_GRAY);
			gridBag.gridx++;
			timeTable.add(����, gridBag);
			
			this.insertRow(timeTable);
			
			return timeTable;
		}

		public void insertRow(JPanel timeTable) {
			GridBagConstraints gridBag = new GridBagConstraints();
			gridBag.gridx = 0;
			gridBag.gridy = 0;
			gridBag.ipadx = insetsBetweenColumn;
			
			try {
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/highspeedrail",
				           "root", test.PASSWORD);
				Statement stmt1 = conn.createStatement();
				SimpleQuery.getDay(departureDate);
				
				ResultSet train = stmt1.executeQuery(String.format("select trainNo "
						+ "from train natural join serviceday "
						+ "where day = '%s' and direction = %d "
						+ "order by trainNo asc "
						+ "limit %d, 10;", SimpleQuery.getDay(this.departureDate), this.direction, this.index));
				train.next();
				int[] id = {990, 1000, 1010, 1020, 1030, 1035, 1040, 1043, 1047, 1050, 1060, 1070};
				Statement stmt2 = conn.createStatement();
				ResultSet time;
				for (int i = 1; i < 11; i++) {
					
					gridBag.gridx = 0;
					gridBag.gridy = i;
					timeTable.add(new JLabel(train.getString(1)), gridBag);
					
					for (int j = 0; j < id.length; j++) {
						time = stmt2.executeQuery(String.format("select departureTime "
								+ "from stop "
								+ "where trainNo = %d and ID = %d;", train.getInt(1), id[j]));
						gridBag.gridx++;
						if (!time.next()) {
							timeTable.add(new JLabel("*"), gridBag);
						} else {
							timeTable.add(new JLabel(time.getString(1).substring(0, 5)), gridBag);
						}
						time.close();
					}
					
					train.next();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
	
		private JPanel buttonPanelFactory() {

			JPanel buttonPanel = new JPanel();
			buttonPanel.setLayout(new GridBagLayout());
			GridBagConstraints gridBag = new GridBagConstraints();
			gridBag.gridx = 0;
			
			this.front10 = new JButton("Front 10");
			front10.addActionListener(this);
			buttonPanel.add(front10, gridBag);
			
			gridBag.gridx++;
			this.next10 = new JButton("Next 10");
			next10.addActionListener(this);
			buttonPanel.add(next10, gridBag);
			
			return buttonPanel;
		}

		private void buildPanel() {
			this.setLayout(new GridBagLayout());
			GridBagConstraints gridBag = new GridBagConstraints();
			gridBag.gridx = 0;
			gridBag.gridy = 0;
			
			this.timeTable = this.timeTableFactory();
			this.add(this.timeTable, gridBag);
						
			JPanel buttonPanel = this.buttonPanelFactory();
			gridBag.gridy++;
			this.add(buttonPanel, gridBag);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			GridBagConstraints gridBag = new GridBagConstraints();
			gridBag.gridx = 0;
			gridBag.gridy = 1;
			
			switch (e.getActionCommand()) {
			case "Front 10":{
				if (this.index - 10 < 0) {
					this.index = 0;
				} else {
					this.index -= 10;
				}
				this.removeAll();
				this.buildPanel();
				this.revalidate();
				System.out.print(this.index);
				break;
			}
			case "Next 10":{
				if (this.index + 20 > this.totalTrainCount) {
					this.index = this.totalTrainCount - 11;
				} else {
					this.index += 10;

				}
				this.removeAll();
				this.buildPanel();
				this.revalidate();
				System.out.print(this.index);
				break;
			}
			}
			
		}
	}
	
	public TimeTable(Calendar departureDate, int direction) {
		this.setTitle("Choose Train No.");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(TrainInquirySystem.WIDTH, TrainInquirySystem.HEIGHT);
		this.setLayout(new BorderLayout());
		
		this.add(new Header(), BorderLayout.NORTH);
		JPanel positionPanel = new JPanel();
		positionPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints gridBag = new GridBagConstraints();
		gridBag.gridx = 0;
		gridBag.gridy = 0;
		
		gridBag.gridy = 0;
		gridBag.anchor = GridBagConstraints.WEST;
		
		if (direction == 1) {
			positionPanel.add(new JLabel("To North"), gridBag);
		} else {
			positionPanel.add(new JLabel("To South"), gridBag);
		}
		
		gridBag.gridy = 1;
		gridBag.anchor = GridBagConstraints.CENTER;
		positionPanel.add(new searchResultTable(departureDate, direction), gridBag);
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout());
		JButton backButton = new JButton("Back");
		backButton.addActionListener(this);
		buttonPanel.add(backButton, BorderLayout.WEST);
		
		gridBag.gridy = 2;
		positionPanel.add(buttonPanel, gridBag);
		this.add(positionPanel, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.setVisible(false);
		this.dispose();
		test.searchTimeTablePage.setVisible(true);
	}

}
