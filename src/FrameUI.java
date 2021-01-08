import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.markers.SeriesMarkers;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FrameUI {
	private JFrame frame;
	private JPanel panel, panelChart;
	private DefaultTableModel model;
	private JTextField txtInputScale;
	private JButton btnDelete, btnReset, btnEdit, btnSave;
	private JLabel lblWarnFieldEmpty, lblWarnDataType;
	private JLabel lblStartingScaleD, lblBalanceD, lblBeehivesD, lblHoneyAmountD, lblHoneyTypeD, lblPlaceD;
	private CSVManager cmanager;
	private XYChart chart;

	public FrameUI() {
		cmanager = new CSVManager();
		initComponents();
		setActions();
		setChart();
		createFrameMain();
	}

	private void createFrameMain() {
		frame = new JFrame("Bee life");
		frame.setIconImage(new ImageIcon("./img/app.png").getImage());
		frame.setSize(960, 490);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setContentPane(panel);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}

	private void initComponents() {
		panel = new JPanel();
		panel.setBackground(new Color(240, 255, 240));
		panel.setLayout(null);

		JLabel lblScaleValue = new JLabel("Scale value [kg]");
		lblScaleValue.setFont(new Font("SansSerif", Font.PLAIN, 13));
		lblScaleValue.setBounds(20, 20, 110, 20);
		panel.add(lblScaleValue);

		txtInputScale = new JTextField();
		txtInputScale.setBounds(120, 20, 45, 22);
		panel.add(txtInputScale);

		lblWarnFieldEmpty = new JLabel("Please enter a value.");
		lblWarnFieldEmpty.setFont(new Font("SansSerif", Font.PLAIN, 11));
		lblWarnFieldEmpty.setBounds(95, 38, 210, 30);
		lblWarnFieldEmpty.setForeground(Color.RED);
		panel.add(lblWarnFieldEmpty);
		lblWarnFieldEmpty.setVisible(false);

		lblWarnDataType = new JLabel("Invalid data type.");
		lblWarnDataType.setFont(new Font("SansSerif", Font.PLAIN, 11));
		lblWarnDataType.setBounds(105, 38, 90, 30);
		lblWarnDataType.setForeground(Color.RED);
		panel.add(lblWarnDataType);
		lblWarnDataType.setVisible(false);

		btnDelete = new JButton("Delete");
		btnDelete.setForeground(Color.DARK_GRAY);
		btnDelete.setFont(new Font("SansSerif", Font.PLAIN, 13));
		btnDelete.setBorder(new RoundedBorder(5));
		btnDelete.setBackground(Color.ORANGE);
		btnDelete.setBounds(35, 70, 140, 25);
		panel.add(btnDelete);

		btnReset = new JButton("Reset");
		btnReset.setForeground(Color.DARK_GRAY);
		btnReset.setFont(new Font("SansSerif", Font.PLAIN, 13));
		btnReset.setBorder(new RoundedBorder(5));
		btnReset.setBackground(Color.ORANGE);
		btnReset.setBounds(35, 120, 140, 25);
		panel.add(btnReset);

		JLabel lblPanelInfo = new JLabel("  Beefarm Info  ");
		lblPanelInfo.setOpaque(true);
		lblPanelInfo.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 14));
		lblPanelInfo.setBackground(new Color(240, 255, 240));
		lblPanelInfo.setBounds(35, 170, 105, 16);
		panel.add(lblPanelInfo);

		JPanel panelInfo = new JPanel();
		panelInfo.setBackground(new Color(240, 255, 240));
		panelInfo.setBorder(new RoundedBorder(10));
		panelInfo.setBounds(20, 180, 190, 140);
		panel.add(panelInfo);
		panelInfo.setLayout(null);

		JLabel lblStartingScale = new JLabel("Starting scale:");
		lblStartingScale.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblStartingScale.setBounds(17, 15, 80, 15);
		panelInfo.add(lblStartingScale);

		lblStartingScaleD = new JLabel("0");
		lblStartingScaleD.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblStartingScaleD.setBounds(105, 15, 70, 15);
		panelInfo.add(lblStartingScaleD);
		
		JLabel lblBalance = new JLabel("Balance:");
		lblBalance.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblBalance.setBounds(44, 35, 66, 15);
		panelInfo.add(lblBalance);

		lblBalanceD = new JLabel("0");
		lblBalanceD.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblBalanceD.setBounds(104, 35, 83, 15);
		panelInfo.add(lblBalanceD);

		JLabel lblBeehives = new JLabel("Beehives:");
		lblBeehives.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblBeehives.setBounds(40, 55, 54, 15);
		panelInfo.add(lblBeehives);

		lblBeehivesD = new JLabel("0");
		lblBeehivesD.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblBeehivesD.setBounds(105, 55, 70, 15);
		panelInfo.add(lblBeehivesD);

		JLabel lblHoneyAmount = new JLabel("Honey amount:");
		lblHoneyAmount.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblHoneyAmount.setBounds(10, 75, 85, 15);
		panelInfo.add(lblHoneyAmount);

		lblHoneyAmountD = new JLabel("0");
		lblHoneyAmountD.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblHoneyAmountD.setBounds(105, 75, 70, 15);
		panelInfo.add(lblHoneyAmountD);

		JLabel lblHoneyType = new JLabel("Honey type:");
		lblHoneyType.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblHoneyType.setBounds(29, 95, 66, 15);
		panelInfo.add(lblHoneyType);

		lblHoneyTypeD = new JLabel("No type");
		lblHoneyTypeD.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblHoneyTypeD.setBounds(105, 95, 70, 15);
		panelInfo.add(lblHoneyTypeD);

		JLabel lblPlace = new JLabel("Place:");
		lblPlace.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblPlace.setBounds(62, 115, 66, 15);
		panelInfo.add(lblPlace);

		lblPlaceD = new JLabel("Unknown");
		lblPlaceD.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblPlaceD.setBounds(104, 115, 83, 15);
		panelInfo.add(lblPlaceD);

		btnEdit = new JButton("Edit");
		btnEdit.setForeground(Color.DARK_GRAY);
		btnEdit.setFont(new Font("SansSerif", Font.PLAIN, 13));
		btnEdit.setBorder(new RoundedBorder(5));
		btnEdit.setBackground(Color.ORANGE);
		btnEdit.setBounds(35, 345, 140, 25);
		panel.add(btnEdit);

		btnSave = new JButton("Save data");
		btnSave.setForeground(Color.DARK_GRAY);
		btnSave.setFont(new Font("SansSerif", Font.PLAIN, 13));
		btnSave.setBorder(new RoundedBorder(5));
		btnSave.setBackground(Color.ORANGE);
		btnSave.setBounds(35, 395, 140, 25);
		panel.add(btnSave);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(225, 20, 200, 420);
		panel.add(scrollPane);

		JTable table = new JTable();
		table.setFont(new Font("SansSerif", Font.PLAIN, 12));
		table.setBackground(new Color(255, 218, 185));
		scrollPane.setViewportView(table);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(Object.class, centerRenderer);

		model = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};

		Object[] header = { "Day", "Value [kg]", "Status (+/-) [kg]" };
		model.setColumnIdentifiers(header);
		table.setModel(model);

		table.getColumnModel().getColumn(0).setPreferredWidth(10);
		table.getColumnModel().getColumn(1).setPreferredWidth(45);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

		setTable();
		setInfo();

	}

	private void setActions() {
		txtInputScale.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				lblWarnFieldEmpty.setVisible(false);
				try {
					Double.parseDouble(txtInputScale.getText().strip());
					lblWarnDataType.setVisible(false);
				} catch (NumberFormatException ex) {
					lblWarnDataType.setVisible(true);
				}
				if (txtInputScale.getText().isEmpty())
					lblWarnDataType.setVisible(false);
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (!lblWarnDataType.isVisible() && txtInputScale.getText().trim().length() != 0
						&& e.getKeyCode() == KeyEvent.VK_ENTER) {
					cmanager.writeTableData(Double.parseDouble(txtInputScale.getText()));
					setTable();
					setInfo();
					updateChart();
					txtInputScale.setText(null);
				}
			}
		});

		btnDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cmanager.deleteTableDataLast();
				setTable();
				setInfo();
				updateChart();
			}
		});

		btnReset.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cmanager.deleteTableData();
				setTable();
				setInfo();
				updateChart();
			}
		});

		btnEdit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new DialogEdit(frame, model, lblStartingScaleD, lblBalanceD, lblBeehivesD, lblHoneyAmountD, lblHoneyTypeD, lblPlaceD,
						panelChart, chart);
			}
		});

		btnSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cmanager.save(chart);
			}
		});
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

	private void setInfo() {
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

	private void setChart() {
		chart = new XYChartBuilder().theme(Styler.ChartTheme.Matlab).yAxisTitle("Scale value [kg]").xAxisTitle("Day")
				.build();

		int rows = model.getRowCount();
		double[] xData = new double[rows];
		double[] yData = new double[rows];
		for (int i = 0; i < rows; i++) {
			xData[i] = i;
			yData[i] = (double) model.getValueAt(i, 1);
		}

		chart.getStyler().setLegendVisible(false).setChartBackgroundColor(new Color(255, 218, 185))
				.setCursorEnabled(false).setPlotBorderVisible(true);

		chart.addSeries("value", xData, yData).setMarker(SeriesMarkers.PLUS).setMarkerColor(Color.RED)
				.setLineColor(Color.BLACK);

		panelChart = new XChartPanel<>(chart);
		panelChart.setBorder(new RoundedBorder(5));
		panelChart.setBounds(440, 20, 490, 420);
		panelChart.setComponentPopupMenu(new JPopupMenu());
		panel.add(panelChart);
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
