package bee;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;
import org.knowm.xchart.XYChart;

public class CSVManager {
	private static final String dir = "data";
	private final static String TABLE_PATH = dir + File.separator + "table.csv";
	private final static String VALUES_PATH = dir + File.separator + "values.csv";
	private final String TEMP_PATH = dir + File.separator + "temp.csv";

	public CSVManager() {}

	public ArrayList<Scale> readTableData() {
		ArrayList<Scale> scales = new ArrayList<>();

		try (BufferedReader csvReader = new BufferedReader(new FileReader(new File(TABLE_PATH)))) {
			String line;
			while ((line = csvReader.readLine()) != null) {
				String[] data = line.split(",");
				if (data[0].equals("Day"))
					continue;
				scales.add(new Scale(Integer.parseInt(data[0]), Double.parseDouble(data[1]), Double.parseDouble(data[2])));
			}
			csvReader.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error reading table data");
		}

		return scales;
	}

	public void writeTableData(double value) {
		String[] data = readTableDataLast().split(",");
		data[0] = String.valueOf((Integer.parseInt(data[0]) + 1));
		data[2] = String.format("%.1f", (value - Double.parseDouble(data[1])));
		data[1] = String.format("%.1f", value);
		String line = "\n" + data[0] + "," + data[1] + "," + data[2];

		try {
			FileWriter csvWriter = new FileWriter(TABLE_PATH, true);
			csvWriter.append(line);
			csvWriter.flush();
			csvWriter.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error: File not found");
		}

	}

	public void writeTableStartingScale(double startingScale) {
		try {
			File inputFile = new File(TABLE_PATH);
			File outputFile = new File(TEMP_PATH);

			try (BufferedReader csvReader = new BufferedReader(new FileReader(inputFile));
					BufferedWriter csvWriter = new BufferedWriter(new FileWriter(outputFile))) {

				String line = null;
				boolean flag = false;
				while ((line = csvReader.readLine()) != null) {
					if (!flag) {
						csvWriter.write(line);
						csvWriter.newLine();
						csvReader.readLine();
						csvWriter.write("0," + String.valueOf(startingScale) + ",0");
						if ((line = csvReader.readLine()) != null) {
							csvWriter.newLine();
							String[] data = line.split(",");
							data[2] = String.format("%.1f", (Double.parseDouble(data[1]) - startingScale));
							csvWriter.write(data[0] + "," + data[1] + "," + data[2]);
						}
						flag = true;
					} else {
						csvWriter.newLine();
						csvWriter.write(line);
					}
				}
			}

			if (inputFile.delete()) {
				if (!outputFile.renameTo(inputFile)) {
					throw new IOException("Could not rename " + TEMP_PATH + " to " + TABLE_PATH);
				}
			} else {
				throw new IOException("Could not delete original input file " + TABLE_PATH);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void deleteTableDataLast() {
		String lastLine = readTableDataLast();
		try {
			File inputFile = new File(TABLE_PATH);
			File outputFile = new File(TEMP_PATH);

			try (BufferedReader csvReader = new BufferedReader(new FileReader(inputFile));
					BufferedWriter csvWriter = new BufferedWriter(new FileWriter(outputFile))) {

				String line = null;
				boolean flag = false;
				while ((line = csvReader.readLine()) != null) {
					if (!flag) {
						csvWriter.write(line);
						csvWriter.newLine();
						line = csvReader.readLine();
						csvWriter.write(line);
						flag = true;
					} else {
						if (!line.equals(lastLine)) {
							csvWriter.newLine();
							csvWriter.write(line);
						}
					}
				}
			}

			if (inputFile.delete()) {
				if (!outputFile.renameTo(inputFile)) {
					throw new IOException("Could not rename " + TEMP_PATH + " to " + TABLE_PATH);
				}
			} else {
				throw new IOException("Could not delete original input file " + TABLE_PATH);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void deleteTableData() {
		try {
			File inputFile = new File(TABLE_PATH);
			File outputFile = new File(TEMP_PATH);

			try (BufferedReader csvReader = new BufferedReader(new FileReader(inputFile));
					BufferedWriter csvWriter = new BufferedWriter(new FileWriter(outputFile))) {

				String line = csvReader.readLine();
				csvWriter.write(line);
				csvWriter.newLine();
				line = csvReader.readLine();
				csvWriter.write(line);
			}

			if (inputFile.delete()) {
				if (!outputFile.renameTo(inputFile)) {
					throw new IOException("Could not rename " + TEMP_PATH + " to " + TABLE_PATH);
				}
			} else {
				throw new IOException("Could not delete original input file " + TABLE_PATH);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String readTableDataLast() {
		String lastLine = null;
		try {
			LineIterator lineIterator = FileUtils.lineIterator(new File(TABLE_PATH), "UTF-8");
			while (lineIterator.hasNext())
				lastLine = lineIterator.nextLine();
			lineIterator.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error reading last status");
		}
		return lastLine;
	}

	public Info readInfoData() {
		Info info = null;
		try (BufferedReader csvReader = new BufferedReader(new FileReader(new File(VALUES_PATH)))) {
			String line = csvReader.readLine();
			String[] data = line.split(",");
			info = new Info(Integer.parseInt(data[0]), data[1], data[2]);
			csvReader.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error reading table data");
		}
		return info;
	}

	public void writeInfoData(int beehives, String honeyType, String place) {
		String data = String.valueOf(beehives) + "," + honeyType + "," + place;
		try {
			File inputFile = new File(VALUES_PATH);
			File outputFile = new File(TEMP_PATH);

			try (BufferedWriter csvWriter = new BufferedWriter(new FileWriter(outputFile))) {
				csvWriter.write(data);
				csvWriter.flush();
				csvWriter.close();
			}

			if (inputFile.delete()) {
				if (!outputFile.renameTo(inputFile)) {
					throw new IOException("Could not rename " + TEMP_PATH + " to " + TABLE_PATH);
				}
			} else {
				throw new IOException("Could not delete original input file " + TABLE_PATH);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void save(XYChart chart) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Save");
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		String dirPath = "";
		int userSelection = fileChooser.showSaveDialog(fileChooser);
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			dirPath = fileChooser.getSelectedFile().getAbsolutePath();
		} else
			return;

		if (!new File(dirPath).exists()) {
			JOptionPane.showMessageDialog(null, "Directory doesn't exsist!");
			return;
		}

		Info info = readInfoData();
		String dataName = info.getHoneyType() + "_" + String.valueOf(Calendar.getInstance().get(Calendar.YEAR)) + "_"
				+ info.getPlace();

		while (new File(dirPath + File.separator + dataName + ".csv").exists()) {
			int i = 1;
			dataName = dataName + " (" + String.valueOf(i) + ")";
		}

		try {
			Files.copy(new File(TABLE_PATH).toPath(), new File(dirPath + File.separator + dataName + ".csv").toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BitmapEncoder.saveBitmapWithDPI(chart, dirPath + File.separator + dataName, BitmapFormat.PNG, 300);
		} catch (IOException e) {
			e.printStackTrace();
		}

		JOptionPane.showMessageDialog(null, "Data exported successfully!");
	}

	public static void initData() {
		File directory = new File(dir);
		if (directory.exists())
			return;
		else {
			if (!directory.mkdir()) {
				JOptionPane.showMessageDialog(null, "Could not create directory");
				System.exit(0);
			}

			File table = new File(TABLE_PATH);
			File values = new File(VALUES_PATH);
			try {
				table.createNewFile();
				BufferedWriter csvWriter = new BufferedWriter(new FileWriter(table));
				csvWriter.write("Day,Value [kg],Status (+/-) [kg]");
				csvWriter.newLine();
				csvWriter.write("0,44.0,0");
				csvWriter.flush();
				csvWriter.close();

				values.createNewFile();
				csvWriter = new BufferedWriter(new FileWriter(values));
				csvWriter.write("0,Undefined,Undefined");
				csvWriter.flush();
				csvWriter.close();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Could not create files");
				table.delete();
				values.delete();
				directory.delete();
				System.exit(0);
			}
		}

	}

}
