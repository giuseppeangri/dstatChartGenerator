package main;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;

import charts.Chart;
import charts.SeriesGenerator;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import readers.Dstat;
import readers.ReaderCsv;
import utility.Constants;

public class Runner extends Application {
	
	private final String divider = "------------------------------------------------------------";
	private final String welcomeMsg = "DSTAT CHART GENERATOR";
	private final String authorMsg = "https://github.com/giuseppeangri/dstatChartGenerator";
	
	private final String inputPathMsg = "Insert the Path of Input DSTATs Directory:";
	private final String sameOutputPathMsg = "Do you want to use this Path as Output Charts Directory? (y/n)";
	private final String outputPathMsg = "Insert the Path of Output Charts Directory:";
	private final String masterInputFileMsg = "Insert the CSV Filename of Master Node:";
	private final String singleInputFileMsg = "Insert the CSV Filename of Single Slave Node:";
	
	private final String numberOfSecondsForSplitMsg = "Insert the Number of Seconds for Split Time on X-Axis:";
	
	private final String allChartsMsg = "Do you want generate All Charts: CPU, RAM, Disk, Network? (y/n)";
	private final String cpuChartMsg = "Do you want generate Chart for CPU Usage? (y/n)";
	private final String ramChartMsg = "Do you want generate Chart for RAM Usage? (y/n)";
	private final String diskAllChartMsg = "Do you want generate Chart for Disk Performance (Read and Write)? (y/n)";
	private final String diskRChartMsg = "Do you want generate Chart for Disk Performance (Only Read)? (y/n)";
	private final String diskWChartMsg = "Do you want generate Chart for Disk Performance (Only Write)? (y/n)";
	private final String netPerfAllChartMsg = "Do you want generate Chart for Network Performance (Received and Sent)? (y/n)";
	private final String netPerfRChartMsg = "Do you want generate Chart for Network Performance (Only Received)? (y/n)";
	private final String netPerfSChartMsg = "Do you want generate Chart for Network Performance (Only Sent)? (y/n)";
	private final String netPktAllChartMsg = "Do you want generate Chart for Network Packets (Received and Sent)? (y/n)";
	private final String netPktRChartMsg = "Do you want generate Chart for Network Packets (Only Received)? (y/n)";
	private final String netPktSChartMsg = "Do you want generate Chart for Network Packets (Only Sent)? (y/n)";
	
	private final String allStatsMsg = "Do you want add All Stats: Master Node, Single Slave Node, Average of Slave Nodes? (y/n)"; 
	private final String masterStatsMsg = "Do you want add Stats for Master Node? (y/n)"; 
	private final String singleStatsMsg = "Do you want add Stats for Single Slave Node? (y/n)";
	private final String randomSingleMsg = "Do you want use a Random Single Slave Node? (y/n)";
	private final String averageStatsMsg = "Do you want add Stats for Average of Slave Nodes? (y/n)";
	
	private final String pathErrorMsg = "The Path does not exist!";
	private final String pathEmptyErrorMsg = "The directory does not contains CSV file!";
	private final String fileErrorMsg = "The File does not exist!";
	private final String choiceErrorMsg = "Please answer y/n!";
	
	private Scanner scanner = new Scanner(System.in);
	
	private String inputDirectory;
	private String outputDirectory;
	private String masterFilename;
	private String singleFilename;
	
	private int numberOfSecondsForSplit;
	
	private boolean allCharts = false;
	private boolean cpuChart = false;
	private boolean ramChart = false;
	private boolean diskAllChart = false;
	private boolean diskRChart = false;
	private boolean diskWChart = false;
	private boolean netPerfAllChart = false;
	private boolean netPerfRChart = false;
	private boolean netPerfSChart = false;
	private boolean netPktAllChart = false;
	private boolean netPktRChart = false;
	private boolean netPktSChart = false;
	
	private boolean allStats = false;
	private boolean masterStats = false;
	private boolean singleStats = false;
	private boolean averageStats = false;
	
	Dstat singleDstat;
	Dstat masterDstat;
	TreeMap<String, Dstat> allSlavesDstats;
	
	public static void main(String[] args) {
		Application.launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		System.out.println(divider);
		System.out.println(welcomeMsg);
		System.out.println(authorMsg);
		System.out.println(divider);
		
		inputDirectory = read_inputPath(inputPathMsg);
		
		boolean same = read_choice(sameOutputPathMsg+"\n"+inputDirectory+"CHARTS/");
		
		if(!same) {
			outputDirectory = read_path(outputPathMsg);
		}
		else {
			outputDirectory = inputDirectory+"CHARTS/";
		}
		
		allCharts = read_choice(allChartsMsg);

		if(!allCharts) {
			
			cpuChart = read_choice(cpuChartMsg);
			
			ramChart = read_choice(ramChartMsg);
			
			diskAllChart = read_choice(diskAllChartMsg);
			diskRChart = read_choice(diskRChartMsg);				
			diskWChart = read_choice(diskWChartMsg);
				
			netPerfAllChart = read_choice(netPerfAllChartMsg);
			netPerfRChart = read_choice(netPerfRChartMsg);				
			netPerfSChart = read_choice(netPerfSChartMsg);
							
			netPktAllChart = read_choice(netPktAllChartMsg);
			netPktRChart = read_choice(netPktRChartMsg);
			netPktSChart = read_choice(netPktSChartMsg);
						
		}
		
		numberOfSecondsForSplit = read_intMultipleOf10(numberOfSecondsForSplitMsg);
		
		allStats = read_choice(allStatsMsg);

		if(!allStats) {
			
			masterStats = read_choice(masterStatsMsg);
			
			singleStats = read_choice(singleStatsMsg);
			
			averageStats = read_choice(averageStatsMsg);
			
		}
		
		if(allStats || masterStats) {
			
			masterFilename = read_csvFilename(masterInputFileMsg);			
			masterDstat = ReaderCsv.getSingle(inputDirectory+masterFilename, numberOfSecondsForSplit);
			
		}
		
		if(allStats || singleStats) {
			
			boolean randomSingle = read_choice(randomSingleMsg);
			
			if(!randomSingle) {
				singleFilename = read_csvFilename(singleInputFileMsg);
			}
			else {
				singleFilename = getRandomCsvFilename();
			}

			singleDstat = ReaderCsv.getSingle(inputDirectory+singleFilename, numberOfSecondsForSplit);

		}
		
		System.out.println("Charts generation in progress...");
		
		allSlavesDstats = ReaderCsv.getMultiple(inputDirectory, numberOfSecondsForSplit);
		
		generateGraphs(primaryStage);
		
		System.out.println(divider);
		System.out.println("DONE!");
		System.out.println(divider);
		
		Platform.exit();
		
	}
		
	private String read_inputPath(String message) {
		
		String directory = null;
		boolean done = false;
		
		do {
			System.out.println(message);
			directory = scanner.next();
			
			Path path = Paths.get(directory);
			
			if(!Files.isDirectory(path)) {
				System.out.println(pathErrorMsg);
			}
			else {
				
				File dir = new File(directory);
				
				File[] files = dir.listFiles(new FilenameFilter() {
					public boolean accept(File dir, String name) {
						return name.toLowerCase().endsWith(".csv");
					}
				});
				
				if(files.length <= 0) {
					System.out.println(pathEmptyErrorMsg);
				}
				else {
					done = true;
				}
				
			}
		} 
		while(!done);
		
		if(!directory.endsWith("/")) {
			directory += "/";
		}
		
		System.out.println(divider);
		
		return directory;
		
	}
	
	private String read_path(String message) {

		String directory = null;
		boolean done = false;
		
		do {
			System.out.println(message);
			directory = scanner.next();
			
			Path path = Paths.get(directory);
			
			if(!Files.isDirectory(path)) {
				System.out.println(pathErrorMsg);
			}
			else {
				done = true;
			}
			
		}
		while(!done);
		
		if(!directory.endsWith("/")) {
			directory += "/";
		}

		System.out.println(divider);
		
		return directory;
		
	}
	
	private String read_csvFilename(String message) {

		String filename = null;
		boolean done = false;
		
		do {
			
			System.out.println(message);
			filename = scanner.next();
			
			if(!filename.endsWith(".csv")) {
				filename += ".csv";
			}

			File file = new File(inputDirectory+filename);
			
			if(!file.exists()) {
				System.out.println(fileErrorMsg);
			}
			else {
				done = true;
			}
			
		}
		while(!done);

//		filename = scanner.next();
//		
//		if(!filename.endsWith(".csv")) {
//			filename += ".csv";
//		}
//		
//		File file = new File(inputDirectory+filename);
//		
//		while (!file.exists()) {
//			System.out.println(fileErrorMsg);
//			System.out.println(message);
//			
//			filename = scanner.next();
//			
//			if(!filename.endsWith(".csv")) {
//				filename += ".csv";
//			}
//			
//			file = new File(inputDirectory+filename);
//		}
		
		System.out.println(divider);
		
		return filename;
		
	}
		
	private int read_intMultipleOf10(String message) {
		
		int number;
		
		do {
			System.out.println(message);
			System.out.println("Please enter a positive number multiple of 10!");
			while (!scanner.hasNextInt()) {
				System.out.println("That's not a number!");
				System.out.println(message);
				scanner.next();
			}
			number = scanner.nextInt();
		} 
		while ( (number <= 0) || (number%10 != 0) );
		
		System.out.println(divider);
		
		return number;
		
	}
	
	private boolean read_choice(String message) {
		
		String choice = null;
		boolean done = false;
		
		do {
			
			System.out.println(message);
			choice = scanner.next();
			
			if(!choice.equalsIgnoreCase("y") && !choice.equalsIgnoreCase("n")) {
				System.out.println(choiceErrorMsg);
			}
			else {
				done = true;
			}
			
		}
		while(!done);

//		choice = scanner.next();
//		while ( !choice.equalsIgnoreCase("y") && !choice.equalsIgnoreCase("n") ) {
//			System.out.println(choiceErrorMsg);
//			System.out.println(message);
//			choice = scanner.next();
//		}
		
		System.out.println(divider);
		
		if(choice.equalsIgnoreCase("y"))
			return true;
		else
			return false;
				
	}
	
	private String getRandomCsvFilename() {

		File directory = new File(inputDirectory);

		File[] files = directory.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".csv");
			}
		});
		
		Random random = new Random();
		int randomIndex = random.nextInt(files.length+1);
		
		String randomFilename = files[randomIndex].getName();
	
		return randomFilename;
		
	}
	
	private void generateGraphs(Stage stage) {
		
		File directory = new File(outputDirectory);
		if(!directory.exists())
			directory.mkdirs();
		
		Chart chart;
		ArrayList<XYChart.Series> series;
		
		if(allCharts || cpuChart) {
			
			series = new ArrayList<XYChart.Series>();
			chart = new Chart(Constants.GRAPHS_CPU, Constants.X_LABEL, Constants.GRAPHS_CPU_Y, series);
			
			if(allStats || masterStats) {
				series.add(SeriesGenerator.getCpu(masterDstat, Constants.GRAPHS_NODE_MASTER));
			}
			
			if(allStats || singleStats) {
				series.add(SeriesGenerator.getCpu(singleDstat, Constants.GRAPHS_NODE_SINGLE));
			}
			
			if(allStats || averageStats) {
				series.add(SeriesGenerator.getCpuAverage(allSlavesDstats, Constants.GRAPHS_NODE_SLAVES_AVERAGE));
			}
			
			chart.print(stage, outputDirectory);
			
		}
		
		if(allCharts || ramChart) {
			
			series = new ArrayList<XYChart.Series>();
			chart = new Chart(Constants.GRAPHS_RAM, Constants.X_LABEL, Constants.GRAPHS_RAM_Y, series);

			if(allStats || masterStats) {
				series.add(SeriesGenerator.getMemory(masterDstat, Constants.GRAPHS_NODE_MASTER));
			}
			
			if(allStats || singleStats) {
				series.add(SeriesGenerator.getMemory(singleDstat, Constants.GRAPHS_NODE_SINGLE));
			}
			
			if(allStats || averageStats) {
				series.add(SeriesGenerator.getMemoryAverage(allSlavesDstats, Constants.GRAPHS_NODE_SLAVES_AVERAGE));
			}
			
			chart.print(stage, outputDirectory);
			
		}
		
		if(allCharts || diskAllChart) {
			
			series = new ArrayList<XYChart.Series>();
			chart = new Chart(Constants.GRAPHS_DISK_ALL, Constants.X_LABEL, Constants.GRAPHS_DISK_Y, series);

			if(allStats || masterStats) {
				series.add(SeriesGenerator.getHddRead(masterDstat, Constants.GRAPHS_NODE_MASTER+" - "+Constants.GRAPHS_DISK_READ));
				series.add(SeriesGenerator.getHddWrite(masterDstat, Constants.GRAPHS_NODE_MASTER+" - "+Constants.GRAPHS_DISK_WRITE));
			}
			
			if(allStats || singleStats) {
				series.add(SeriesGenerator.getHddRead(singleDstat, Constants.GRAPHS_NODE_SINGLE+" - "+Constants.GRAPHS_DISK_READ));
				series.add(SeriesGenerator.getHddWrite(singleDstat, Constants.GRAPHS_NODE_SINGLE+" - "+Constants.GRAPHS_DISK_WRITE));
			}
			
			if(allStats || averageStats) {
				series.add(SeriesGenerator.getHddReadAverage(allSlavesDstats, Constants.GRAPHS_NODE_SLAVES_AVERAGE+" - "+Constants.GRAPHS_DISK_READ));
				series.add(SeriesGenerator.getHddWriteAverage(allSlavesDstats, Constants.GRAPHS_NODE_SLAVES_AVERAGE+" - "+Constants.GRAPHS_DISK_WRITE));
			}
			
			chart.print(stage, outputDirectory);
						
		}
		
		if(allCharts || diskRChart) {
			
			series = new ArrayList<XYChart.Series>();
			chart = new Chart(Constants.GRAPHS_DISK_R, Constants.X_LABEL, Constants.GRAPHS_DISK_Y, series);

			if(allStats || masterStats) {
				series.add(SeriesGenerator.getHddRead(masterDstat, Constants.GRAPHS_NODE_MASTER+" - "+Constants.GRAPHS_DISK_READ));
			}
			
			if(allStats || singleStats) {
				series.add(SeriesGenerator.getHddRead(singleDstat, Constants.GRAPHS_NODE_SINGLE+" - "+Constants.GRAPHS_DISK_READ));
			}
			
			if(allStats || averageStats) {
				series.add(SeriesGenerator.getHddReadAverage(allSlavesDstats, Constants.GRAPHS_NODE_SLAVES_AVERAGE+" - "+Constants.GRAPHS_DISK_READ));
			}
			
			chart.print(stage, outputDirectory);
						
		}
		
		if(allCharts || diskWChart) {
			
			series = new ArrayList<XYChart.Series>();
			chart = new Chart(Constants.GRAPHS_DISK_W, Constants.X_LABEL, Constants.GRAPHS_DISK_Y, series);

			if(allStats || masterStats) {
				series.add(SeriesGenerator.getHddWrite(masterDstat, Constants.GRAPHS_NODE_MASTER+" - "+Constants.GRAPHS_DISK_WRITE));
			}
			
			if(allStats || singleStats) {
				series.add(SeriesGenerator.getHddWrite(singleDstat, Constants.GRAPHS_NODE_SINGLE+" - "+Constants.GRAPHS_DISK_WRITE));
			}
			
			if(allStats || averageStats) {
				series.add(SeriesGenerator.getHddWriteAverage(allSlavesDstats, Constants.GRAPHS_NODE_SLAVES_AVERAGE+" - "+Constants.GRAPHS_DISK_WRITE));
			}
			
			chart.print(stage, outputDirectory);
						
		}
		
		if(allCharts || netPerfAllChart) {
			
			series = new ArrayList<XYChart.Series>();
			chart = new Chart(Constants.GRAPHS_NET_MB_ALL, Constants.X_LABEL, Constants.GRAPHS_NET_MB_Y, series);

			if(allStats || masterStats) {
				series.add(SeriesGenerator.getNetRecvBytes(masterDstat, Constants.GRAPHS_NODE_MASTER+" - "+Constants.GRAPHS_NET_RECV));
				series.add(SeriesGenerator.getNetSendBytes(masterDstat, Constants.GRAPHS_NODE_MASTER+" - "+Constants.GRAPHS_NET_SENT));
			}
			
			if(allStats || singleStats) {
				series.add(SeriesGenerator.getNetRecvBytes(singleDstat, Constants.GRAPHS_NODE_SINGLE+" - "+Constants.GRAPHS_NET_RECV));
				series.add(SeriesGenerator.getNetSendBytes(singleDstat, Constants.GRAPHS_NODE_SINGLE+" - "+Constants.GRAPHS_NET_SENT));
			}
			
			if(allStats || averageStats) {
				series.add(SeriesGenerator.getNetRecvBytesAverage(allSlavesDstats, Constants.GRAPHS_NODE_SLAVES_AVERAGE+" - "+Constants.GRAPHS_NET_RECV));
				series.add(SeriesGenerator.getNetSendBytesAverage(allSlavesDstats, Constants.GRAPHS_NODE_SLAVES_AVERAGE+" - "+Constants.GRAPHS_NET_SENT));
			}
			
			chart.print(stage, outputDirectory);
						
		}
		
		if(allCharts || netPerfRChart) {
			
			series = new ArrayList<XYChart.Series>();
			chart = new Chart(Constants.GRAPHS_NET_MB_R, Constants.X_LABEL, Constants.GRAPHS_NET_MB_Y, series);

			if(allStats || masterStats) {
				series.add(SeriesGenerator.getNetRecvBytes(masterDstat, Constants.GRAPHS_NODE_MASTER+" - "+Constants.GRAPHS_NET_RECV));
			}
			
			if(allStats || singleStats) {
				series.add(SeriesGenerator.getNetRecvBytes(singleDstat, Constants.GRAPHS_NODE_SINGLE+" - "+Constants.GRAPHS_NET_RECV));
			}
			
			if(allStats || averageStats) {
				series.add(SeriesGenerator.getNetRecvBytesAverage(allSlavesDstats, Constants.GRAPHS_NODE_SLAVES_AVERAGE+" - "+Constants.GRAPHS_NET_RECV));
			}
			
			chart.print(stage, outputDirectory);
						
		}
		
		if(allCharts || netPerfSChart) {
			
			series = new ArrayList<XYChart.Series>();
			chart = new Chart(Constants.GRAPHS_NET_MB_S, Constants.X_LABEL, Constants.GRAPHS_NET_MB_Y, series);

			if(allStats || masterStats) {
				series.add(SeriesGenerator.getNetSendBytes(masterDstat, Constants.GRAPHS_NODE_MASTER+" - "+Constants.GRAPHS_NET_SENT));
			}
			
			if(allStats || singleStats) {
				series.add(SeriesGenerator.getNetSendBytes(singleDstat, Constants.GRAPHS_NODE_SINGLE+" - "+Constants.GRAPHS_NET_SENT));
			}
			
			if(allStats || averageStats) {
				series.add(SeriesGenerator.getNetSendBytesAverage(allSlavesDstats, Constants.GRAPHS_NODE_SLAVES_AVERAGE+" - "+Constants.GRAPHS_NET_SENT));
			}
			
			chart.print(stage, outputDirectory);
						
		}
		
		if(allCharts || netPktAllChart) {
			
			series = new ArrayList<XYChart.Series>();
			chart = new Chart(Constants.GRAPHS_NET_PKT_ALL, Constants.X_LABEL, Constants.GRAPHS_NET_PKT_Y, series);

			if(allStats || masterStats) {
				series.add(SeriesGenerator.getNetRecvPackets(masterDstat, Constants.GRAPHS_NODE_MASTER+" - "+Constants.GRAPHS_NET_RECV));
				series.add(SeriesGenerator.getNetSendPackets(masterDstat, Constants.GRAPHS_NODE_MASTER+" - "+Constants.GRAPHS_NET_SENT));
			}
			
			if(allStats || singleStats) {
				series.add(SeriesGenerator.getNetRecvPackets(singleDstat, Constants.GRAPHS_NODE_SINGLE+" - "+Constants.GRAPHS_NET_RECV));
				series.add(SeriesGenerator.getNetSendPackets(singleDstat, Constants.GRAPHS_NODE_SINGLE+" - "+Constants.GRAPHS_NET_SENT));
			}
			
			if(allStats || averageStats) {
				series.add(SeriesGenerator.getNetRecvPacketsAverage(allSlavesDstats, Constants.GRAPHS_NODE_SLAVES_AVERAGE+" - "+Constants.GRAPHS_NET_RECV));
				series.add(SeriesGenerator.getNetSendPacketsAverage(allSlavesDstats, Constants.GRAPHS_NODE_SLAVES_AVERAGE+" - "+Constants.GRAPHS_NET_SENT));
			}
			
			chart.print(stage, outputDirectory);
						
		}
		
		if(allCharts || netPktRChart) {
			
			series = new ArrayList<XYChart.Series>();
			chart = new Chart(Constants.GRAPHS_NET_PKT_R, Constants.X_LABEL, Constants.GRAPHS_NET_PKT_Y, series);

			if(allStats || masterStats) {
				series.add(SeriesGenerator.getNetRecvPackets(masterDstat, Constants.GRAPHS_NODE_MASTER+" - "+Constants.GRAPHS_NET_RECV));
			}
			
			if(allStats || singleStats) {
				series.add(SeriesGenerator.getNetRecvPackets(singleDstat, Constants.GRAPHS_NODE_SINGLE+" - "+Constants.GRAPHS_NET_RECV));
			}
			
			if(allStats || averageStats) {
				series.add(SeriesGenerator.getNetRecvPacketsAverage(allSlavesDstats, Constants.GRAPHS_NODE_SLAVES_AVERAGE+" - "+Constants.GRAPHS_NET_RECV));
			}
			
			chart.print(stage, outputDirectory);
						
		}
		
		if(allCharts || netPktSChart) {
			
			series = new ArrayList<XYChart.Series>();
			chart = new Chart(Constants.GRAPHS_NET_PKT_S, Constants.X_LABEL, Constants.GRAPHS_NET_PKT_Y, series);

			if(allStats || masterStats) {
				series.add(SeriesGenerator.getNetSendPackets(masterDstat, Constants.GRAPHS_NODE_MASTER+" - "+Constants.GRAPHS_NET_SENT));
			}
			
			if(allStats || singleStats) {
				series.add(SeriesGenerator.getNetSendPackets(singleDstat, Constants.GRAPHS_NODE_SINGLE+" - "+Constants.GRAPHS_NET_SENT));
			}
			
			if(allStats || averageStats) {
				series.add(SeriesGenerator.getNetSendPacketsAverage(allSlavesDstats, Constants.GRAPHS_NODE_SLAVES_AVERAGE+" - "+Constants.GRAPHS_NET_SENT));
			}
			
			chart.print(stage, outputDirectory);
						
		}
		
	}

}
