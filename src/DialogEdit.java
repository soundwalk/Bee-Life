import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.knowm.xchart.XYChart;
import org.knowm.xchart.style.markers.SeriesMarkers;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DialogEdit {
	private JFrame frame;
	private JDialog dialog;
	private JPanel panel, panelChart;
	private DefaultTableModel model;
	private JTextField txtStartingScale, txtBeehives, txtHoneyType, txtPlace;
	private JButton btnSave;
	private JLabel lblStartingScaleBox, lblBeehivesBox, lblHoneyTypeBox, lblPlaceBox;
	private JLabel lblStartingScaleD, lblBalanceD, lblBeehivesD, lblHoneyAmountD, lblHoneyTypeD, lblPlaceD;
	private ImageIcon ic_check = new ImageIcon("./img/check.png");
	private ImageIcon ic_alert = new ImageIcon("./img/alert.png");
	private CSVManager cmanager = new CSVManager();
	private XYChart chart;

	public DialogEdit(JFrame frame, DefaultTableModel model, JLabel lblStartingScaleD, JLabel lblBalanceD, JLabel lblBeehivesD,
			JLabel lblHoneyAmountD, JLabel lblHoneyTypeD, JLabel lblPlaceD, JPanel panelChart, XYChart chart) {
		this.frame = frame;
		this.model = model;
		this.lblStartingScaleD = lblStartingScaleD;
		this.lblBalanceD = lblBalanceD;
		this.lblBeehivesD = lblBeehivesD;
		this.lblHoneyAmountD = lblHoneyAmountD;
		this.lblHoneyTypeD = lblHoneyTypeD;
		this.lblPlaceD = lblPlaceD;
		this.panelChart = panelChart;
		this.chart = chart;

		frame.setEnabled(false);
		initComponents();
		createDialogEdit();
		setActions();
	}

	private void createDialogEdit() {
		dialog = new JDialog();
		dialog.setIconImage(new ImageIcon("./img/app.png").getImage());
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setResizable(false);
		dialog.setTitle("Edit beefarm info");
		dialog.setSize(262, 174);
		dialog.setLocationRelativeTo(null);
		dialog.setContentPane(panel);
		dialog.setVisible(true);
	}

	private void initComponents() {
		panel = new JPanel();
		panel.setBackground(new Color(240, 255, 240));
		panel.setLayout(null);

		JLabel lblStartingScale = new JLabel("Starting scale:");
		lblStartingScale.setFont(new Font("SansSerif", Font.PLAIN, 13));
		lblStartingScale.setBounds(13, 15, 90, 16);
		panel.add(lblStartingScale);

		txtStartingScale = new JTextField("");
		txtStartingScale.setFont(new Font("SansSerif", Font.PLAIN, 13));
		txtStartingScale.setBounds(110, 15, 90, 16);
		panel.add(txtStartingScale);

		lblStartingScaleBox = new JLabel("");
		lblStartingScaleBox.setBounds(210, 15, 16, 16);
		lblStartingScaleBox.setIcon(ic_check);
		panel.add(lblStartingScaleBox);

		JLabel lblBeehives = new JLabel("Beehives:");
		lblBeehives.setFont(new Font("SansSerif", Font.PLAIN, 13));
		lblBeehives.setBounds(39, 35, 90, 16);
		panel.add(lblBeehives);

		txtBeehives = new JTextField("");
		txtBeehives.setFont(new Font("SansSerif", Font.PLAIN, 13));
		txtBeehives.setBounds(110, 36, 90, 16);
		panel.add(txtBeehives);

		lblBeehivesBox = new JLabel("");
		lblBeehivesBox.setBounds(210, 35, 16, 16);
		lblBeehivesBox.setIcon(ic_check);
		panel.add(lblBeehivesBox);

		JLabel lblHoneyType = new JLabel("Honey type:");
		lblHoneyType.setFont(new Font("SansSerif", Font.PLAIN, 13));
		lblHoneyType.setBounds(26, 55, 90, 16);
		panel.add(lblHoneyType);

		txtHoneyType = new JTextField("");
		txtHoneyType.setFont(new Font("SansSerif", Font.PLAIN, 13));
		txtHoneyType.setBounds(110, 56, 90, 16);
		panel.add(txtHoneyType);

		lblHoneyTypeBox = new JLabel("");
		lblHoneyTypeBox.setBounds(210, 55, 16, 16);
		lblHoneyTypeBox.setIcon(ic_check);
		panel.add(lblHoneyTypeBox);

		JLabel lblPlace = new JLabel("Place:");
		lblPlace.setFont(new Font("SansSerif", Font.PLAIN, 13));
		lblPlace.setBounds(60, 75, 90, 16);
		panel.add(lblPlace);

		txtPlace = new JTextField("");
		txtPlace.setFont(new Font("SansSerif", Font.PLAIN, 13));
		txtPlace.setBounds(110, 77, 90, 16);
		panel.add(txtPlace);

		lblPlaceBox = new JLabel("");
		lblPlaceBox.setBounds(210, 75, 16, 16);
		lblPlaceBox.setIcon(ic_check);
		panel.add(lblPlaceBox);

		setInfo();

		btnSave = new JButton("Save");
		btnSave.setForeground(Color.DARK_GRAY);
		btnSave.setFont(new Font("SansSerif", Font.PLAIN, 13));
		btnSave.setBorder(new RoundedBorder(5));
		btnSave.setBackground(Color.ORANGE);
		btnSave.setBounds(29, 102, 190, 26);
		panel.add(btnSave);
	}

	private void setActions() {
		txtStartingScale.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				lblStartingScaleBox.setIcon(ic_check);
				btnSave.setBackground(Color.ORANGE);
				String text = txtStartingScale.getText().strip();
				if (text.isEmpty()) {
					lblStartingScaleBox.setIcon(ic_alert);
				} else {
					try {
						Double.parseDouble(text);
					} catch (NumberFormatException ex) {
						lblStartingScaleBox.setIcon(null);
						btnSave.setBackground(Color.GRAY);
					}
				}
			}
		});

		txtBeehives.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				lblBeehivesBox.setIcon(ic_check);
				btnSave.setBackground(Color.ORANGE);
				String text = txtBeehives.getText().strip();
				if (text.isEmpty()) {
					lblBeehivesBox.setIcon(ic_alert);
				} else {
					try {
						Integer.parseInt(text);
					} catch (NumberFormatException ex) {
						lblBeehivesBox.setIcon(null);
						btnSave.setBackground(Color.GRAY);
					}
				}
			}
		});

		txtHoneyType.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				lblHoneyTypeBox.setIcon(ic_check);
				if (txtHoneyType.getText().strip().isEmpty())
					lblHoneyTypeBox.setIcon(ic_alert);
			}
		});

		txtPlace.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				lblPlaceBox.setIcon(ic_check);
				if (txtPlace.getText().strip().isEmpty())
					lblPlaceBox.setIcon(ic_alert);
			}
		});

		btnSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				save();
			}
		});

		dialog.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				frame.setEnabled(true);
				frame.setVisible(true);
			}
		});
	}

	private void setInfo() {
		Info info = cmanager.readInfoData();
		txtStartingScale.setText(String.valueOf(model.getValueAt(0, 1)));
		txtBeehives.setText(String.valueOf(info.getBeehives()));
		txtHoneyType.setText(info.getHoneyType());
		txtPlace.setText(info.getPlace());
	}

	private void save() {
		if (btnSave.getBackground() != Color.GRAY) {
			String startingScale = txtStartingScale.getText().strip();
			String beehives = txtBeehives.getText().strip();
			String honeyType = txtHoneyType.getText().strip();
			String place = txtPlace.getText().strip();

			if (startingScale.isEmpty())
				startingScale = "44.0";
			if (beehives.isEmpty())
				beehives = "0";
			if (honeyType.isEmpty())
				honeyType = "Undefined";
			if (place.isEmpty())
				place = "Undefined";

			cmanager.writeTableStartingScale(Double.parseDouble(startingScale));
			cmanager.writeInfoData(Integer.parseInt(beehives), honeyType, place);
			setTable();
			setInfoD();
			updateChart();

			frame.setEnabled(true);
			dialog.dispose();
		} else
			JOptionPane.showMessageDialog(null, "Please check your inputs");
	}

	private void setTable() {
		model.setRowCount(0);
		ArrayList<Scale> scales = cmanager.readTableData();
		Object[] row = new Object[3];
		for (int i = 0; i < scales.size(); i++) {
			row[0] = scales.get(i).getDay();
			row[1] = scales.get(i).getValue();
			row[2] = scales.get(i).getStatus();
			model.addRow(row);
		}
	}

	private void setInfoD() {
		Info info = cmanager.readInfoData();
		int beehives = info.getBeehives();

		double startingScale = (double) model.getValueAt(0, 1);
		double lastScale = (double) model.getValueAt(model.getRowCount() - 1, 1);
		double honeyAmount = (lastScale - startingScale) * beehives;

		lblStartingScaleD.setText(String.valueOf(startingScale));
		lblBalanceD.setText(String.format("%.1f", (lastScale - startingScale)));
		lblBeehivesD.setText(String.valueOf(beehives));
		lblHoneyAmountD.setText(String.format("%.1f", honeyAmount));
		lblHoneyTypeD.setText(info.getHoneyType());
		lblPlaceD.setText(info.getPlace());
	}

	private void updateChart() {
		int rows = model.getRowCount();
		double[] xData = new double[rows];
		double[] yData = new double[rows];
		for (int i = 0; i < rows; i++) {
			xData[i] = i;
			yData[i] = (double) model.getValueAt(i, 1);
		}

		chart.removeSeries("value");
		chart.addSeries("value", xData, yData).setMarker(SeriesMarkers.PLUS).setMarkerColor(Color.RED)
				.setLineColor(Color.BLACK);
		panelChart.repaint();
	}

}
