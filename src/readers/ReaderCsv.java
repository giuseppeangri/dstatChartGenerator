package readers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Map.Entry;
import java.util.TreeMap;

public class ReaderCsv {
	
	public static Dstat getSingle(String inputFile, int numberOfSecondsForSplit) {
		
		BufferedReader br = null;
		
		TreeMap<Integer, Float> cpuUsage = new TreeMap<Integer, Float>();
		TreeMap<Integer, Long> memoryUsage = new TreeMap<Integer, Long>();
		TreeMap<Integer, Double> hddReadBytes = new TreeMap<Integer, Double>();
		TreeMap<Integer, Double> hddWriteBytes = new TreeMap<Integer, Double>();
		TreeMap<Integer, Double> netRecvBytes = new TreeMap<Integer, Double>();
		TreeMap<Integer, Double> netSendBytes = new TreeMap<Integer, Double>();
		TreeMap<Integer, Double> netRecvPackets = new TreeMap<Integer, Double>();
		TreeMap<Integer, Double> netSendPackets = new TreeMap<Integer, Double>();
		
		String line;
		String csvSplitterChar = ",";
		
		int currentXPoint = 0;
		
		try {
			
			br = new BufferedReader(new FileReader(inputFile));
						
			// SKIP HEADER LINE (7) AND FIRST LINE OF DSTAT (1)
			for(int i=0; i<8; i++) {
				br.readLine();
			}
			
			int s = numberOfSecondsForSplit / 10;
		
			int currentLine = 0;
			
			while( (line = br.readLine()) != null ) {
				
				if( (currentLine>0) && ((currentLine%s) == 0) ) {
					
					float cpu = cpuUsage.get(currentXPoint) / s;
					cpuUsage.put(currentXPoint, cpu);
					
					long memory = memoryUsage.get(currentXPoint) / s;
					memoryUsage.put(currentXPoint, memory);
					
					double hddR = hddReadBytes.get(currentXPoint) / s;
					hddReadBytes.put(currentXPoint, hddR);
					
					double hddW = hddWriteBytes.get(currentXPoint) / s;
					hddWriteBytes.put(currentXPoint, hddW);
					
					double netRB = netRecvBytes.get(currentXPoint) / s;
					netRecvBytes.put(currentXPoint, netRB);
					
					double netSB = netSendBytes.get(currentXPoint) / s;
					netSendBytes.put(currentXPoint, netSB);
					
					currentXPoint += numberOfSecondsForSplit;
				}
				
				String[] values = line.split(csvSplitterChar);
									
				float cpu = Float.parseFloat(values[1])+Float.parseFloat(values[2]); 
				if(cpuUsage.containsKey(currentXPoint)) {
					cpu += cpuUsage.get(currentXPoint);
				}
				cpuUsage.put(currentXPoint, cpu);
				
				long memory = (long)Double.parseDouble(values[7])+(long)Double.parseDouble(values[8])+(long)Double.parseDouble(values[9]);
				memory = memory / (1024*1024);
				if(memoryUsage.containsKey(currentXPoint)) {
					memory += memoryUsage.get(currentXPoint);
				}
				memoryUsage.put(currentXPoint, memory);
				
				double hddR = Double.parseDouble(values[15]); 
				hddR = hddR / (1024*1024);
				if(hddReadBytes.containsKey(currentXPoint)) {
					hddR += hddReadBytes.get(currentXPoint);
				}
				hddReadBytes.put(currentXPoint, hddR);
				
				double hddW = Double.parseDouble(values[16]); 
				hddW = hddW / (1024*1024);
				if(hddWriteBytes.containsKey(currentXPoint)) {
					hddW += hddWriteBytes.get(currentXPoint);
				}
				hddWriteBytes.put(currentXPoint, hddW);
				
				double netRB = Double.parseDouble(values[17]); 
				netRB = netRB / (1024*1024);
				if(netRecvBytes.containsKey(currentXPoint)) {
					netRB += netRecvBytes.get(currentXPoint);
				}
				netRecvBytes.put(currentXPoint, netRB);
				
				double netSB = Double.parseDouble(values[18]); 
				netSB = netSB / (1024*1024);
				if(netSendBytes.containsKey(currentXPoint)) {
					netSB += netSendBytes.get(currentXPoint);
				}
				netSendBytes.put(currentXPoint, netSB);
				
				double netRP = Double.parseDouble(values[21]); 
				if(netRecvPackets.containsKey(currentXPoint)) {
					netRP += netRecvPackets.get(currentXPoint);
				}
				netRecvPackets.put(currentXPoint, netRP);
				
				double netSP = Double.parseDouble(values[22]); 
				if(netSendPackets.containsKey(currentXPoint)) {
					netSP += netSendPackets.get(currentXPoint);
				}
				netSendPackets.put(currentXPoint, netSP);
				
				currentLine++;
				
			}
			
			if( currentLine>0 ) {
				
				int cut;
				
				if((currentLine%s) == 0)
					cut = s;
				else
					cut = currentLine%s;
				
				float cpu = cpuUsage.get(currentXPoint) / cut;
				cpuUsage.put(currentXPoint, cpu);
				
				long memory = memoryUsage.get(currentXPoint) / cut;
				memoryUsage.put(currentXPoint, memory);
				
				double hddR = hddReadBytes.get(currentXPoint) / cut;
				hddReadBytes.put(currentXPoint, hddR);
				
				double hddW = hddWriteBytes.get(currentXPoint) / cut;
				hddWriteBytes.put(currentXPoint, hddW);
				
				double netRB = netRecvBytes.get(currentXPoint) / cut;
				netRecvBytes.put(currentXPoint, netRB);
				
				double netSB = netSendBytes.get(currentXPoint) / cut;
				netSendBytes.put(currentXPoint, netSB);
				
			}
			
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
					
		Dstat slave = new Dstat();
		slave.setCpuUsage(cpuUsage);
		slave.setMemoryUsage(memoryUsage);
		slave.setHddReadBytes(hddReadBytes);
		slave.setHddWriteBytes(hddWriteBytes);
		slave.setNetRecvBytes(netRecvBytes);
		slave.setNetSendBytes(netSendBytes);
		slave.setNetRecvPackets(netRecvPackets);
		slave.setNetSendPackets(netSendPackets);
		slave.setLastXPoint(currentXPoint);
		
		return slave;
		
	}
	
	public static TreeMap<String, Dstat> getMultiple(String inputDirectory, int numberOfSecondsForSplit) {
	
		TreeMap<String, Dstat> list = new TreeMap<String, Dstat>();
		
		File directory = new File(inputDirectory);

		File[] files = directory.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".csv");
			}
		});
				
		for(File file : files) {
			
			String filename = file.getName();
			String filepath = file.getAbsolutePath();
			
			Dstat dstat = getSingle(filepath, numberOfSecondsForSplit);
			
			list.put(filename, dstat);
			
		}

		// PRINT LIST
//		for(Entry<String, Dstat> entry : list.entrySet()) {
//			String key = entry.getKey();
//			Dstat value = entry.getValue();
//			System.out.println(key);
//			System.out.println(value);
//		}
		
		return list;
			
	}
	
}
