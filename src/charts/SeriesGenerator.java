package charts;

import java.util.Map.Entry;
import java.util.TreeMap;

import javafx.scene.chart.XYChart;
import readers.Dstat;

public class SeriesGenerator {
		
	public static XYChart.Series getCpu(Dstat dstat, String label) {
				
		TreeMap<Integer, Float> cpuUsage = dstat.getCpuUsage();
				
		XYChart.Series serie = new XYChart.Series();
		serie.setName(label);
		
		for(Entry<Integer, Float> entry : cpuUsage.entrySet()) {
			Integer xPoint = entry.getKey();
			Float yPoint = entry.getValue();
			serie.getData().add(new XYChart.Data(xPoint, yPoint));
		}
		
		return serie;
	}

	public static XYChart.Series getCpuAverage(TreeMap<String, Dstat> dstats, String label) {
		
		TreeMap<Integer, Float> cpuaverage = new TreeMap<Integer, Float>();
		
		int minXPoint = getMinLastXPoint(dstats);
		
		for(Entry<String, Dstat> entry : dstats.entrySet()) {
			
			String keyDstat = entry.getKey();
			Dstat singleDstat = entry.getValue();
						
			TreeMap<Integer, Float> cpuUsage = singleDstat.getCpuUsage();
			
			for(Entry<Integer, Float> entryCpu : cpuUsage.entrySet()) {
												
				Integer xPoint = entryCpu.getKey();
				Float yPoint = entryCpu.getValue();
				
				if(xPoint > minXPoint)
					break;
				
				if(cpuaverage.containsKey(xPoint)) {
					yPoint += cpuaverage.get(xPoint);
				}
				cpuaverage.put(xPoint, yPoint);
				
			}
						
		}

		int size = dstats.size();
			
		for(Entry<Integer, Float> entryCpu : cpuaverage.entrySet()) {
			
			Integer xPoint = entryCpu.getKey();
			Float yPoint = entryCpu.getValue();
										
			cpuaverage.put(xPoint, (yPoint/size));
			
		}
		
		XYChart.Series serie = new XYChart.Series();
		serie.setName(label);
		
		for(Entry<Integer, Float> entry : cpuaverage.entrySet()) {
			Integer xPoint = entry.getKey();
			Float yPoint = entry.getValue();
			serie.getData().add(new XYChart.Data(xPoint, yPoint));
		}
		
		return serie;
	}
	
	public static XYChart.Series getMemory(Dstat dstat, String label) {
		
		TreeMap<Integer, Long> memoryUsage = dstat.getMemoryUsage();
				
		XYChart.Series serie = new XYChart.Series();
		serie.setName(label);
		
		for(Entry<Integer, Long> entry : memoryUsage.entrySet()) {
			Integer xPoint = entry.getKey();
			Long yPoint = entry.getValue();
			serie.getData().add(new XYChart.Data(xPoint, yPoint));
		}
		
		return serie;
	}
	
	public static XYChart.Series getMemoryAverage(TreeMap<String, Dstat> dstats, String label) {
		
		TreeMap<Integer, Long> memoryaverage = new TreeMap<Integer, Long>();
		
		int minXPoint = getMinLastXPoint(dstats);
		
		for(Entry<String, Dstat> entry : dstats.entrySet()) {
			
			String keyDstat = entry.getKey();
			Dstat singleDstat = entry.getValue();
						
			TreeMap<Integer, Long> memoryUsage = singleDstat.getMemoryUsage();
			
			for(Entry<Integer, Long> entryMemory : memoryUsage.entrySet()) {
												
				Integer xPoint = entryMemory.getKey();
				Long yPoint = entryMemory.getValue();
				
				if(xPoint > minXPoint)
					break;
				
				if(memoryaverage.containsKey(xPoint)) {
					yPoint += memoryaverage.get(xPoint);
				}
				memoryaverage.put(xPoint, yPoint);
				
			}
						
		}

		int size = dstats.size();
			
		for(Entry<Integer, Long> entryMemory : memoryaverage.entrySet()) {
			
			Integer xPoint = entryMemory.getKey();
			Long yPoint = entryMemory.getValue();
										
			memoryaverage.put(xPoint, (yPoint/size));
			
		}
		
		XYChart.Series serie = new XYChart.Series();
		serie.setName(label);
		
		for(Entry<Integer, Long> entry : memoryaverage.entrySet()) {
			Integer xPoint = entry.getKey();
			Long yPoint = entry.getValue();
			serie.getData().add(new XYChart.Data(xPoint, yPoint));
		}
		
		return serie;
	}
	
	public static XYChart.Series getHddRead(Dstat dstat, String label) {
		
		TreeMap<Integer, Double> usage = dstat.getHddReadBytes();
				
		XYChart.Series serie = new XYChart.Series();
		serie.setName(label);
		
		for(Entry<Integer, Double> entry : usage.entrySet()) {
			Integer xPoint = entry.getKey();
			Double yPoint = entry.getValue();
			serie.getData().add(new XYChart.Data(xPoint, yPoint));
		}
		
		return serie;
	}
	
	public static XYChart.Series getHddReadAverage(TreeMap<String, Dstat> dstats, String label) {
		
		TreeMap<Integer, Double> average = new TreeMap<Integer, Double>();
		
		int minXPoint = getMinLastXPoint(dstats);
		
		for(Entry<String, Dstat> entry : dstats.entrySet()) {
			
			String keyDstat = entry.getKey();
			Dstat singleDstat = entry.getValue();
						
			TreeMap<Integer, Double> singleUsage = singleDstat.getHddReadBytes();
			
			for(Entry<Integer, Double> entrySingle : singleUsage.entrySet()) {
												
				Integer xPoint = entrySingle.getKey();
				Double yPoint = entrySingle.getValue();
				
				if(xPoint > minXPoint)
					break;
				
				if(average.containsKey(xPoint)) {
					yPoint += average.get(xPoint);
				}
				average.put(xPoint, yPoint);
				
			}
						
		}

		int size = dstats.size();
			
		for(Entry<Integer, Double> entryMemory : average.entrySet()) {
			
			Integer xPoint = entryMemory.getKey();
			Double yPoint = entryMemory.getValue();
										
			average.put(xPoint, (yPoint/size));
			
		}
		
		XYChart.Series serie = new XYChart.Series();
		serie.setName(label);
		
		for(Entry<Integer, Double> entry : average.entrySet()) {
			Integer xPoint = entry.getKey();
			Double yPoint = entry.getValue();
			serie.getData().add(new XYChart.Data(xPoint, yPoint));
		}
		
		return serie;
	}
	
	public static XYChart.Series getHddWrite(Dstat dstat, String label) {
		
		TreeMap<Integer, Double> usage = dstat.getHddWriteBytes();
				
		XYChart.Series serie = new XYChart.Series();
		serie.setName(label);
		
		for(Entry<Integer, Double> entry : usage.entrySet()) {
			Integer xPoint = entry.getKey();
			Double yPoint = entry.getValue();
			serie.getData().add(new XYChart.Data(xPoint, yPoint));
		}
		
		return serie;
	}
	
	public static XYChart.Series getHddWriteAverage(TreeMap<String, Dstat> dstats, String label) {
		
		TreeMap<Integer, Double> average = new TreeMap<Integer, Double>();
		
		int minXPoint = getMinLastXPoint(dstats);
		
		for(Entry<String, Dstat> entry : dstats.entrySet()) {
			
			String keyDstat = entry.getKey();
			Dstat singleDstat = entry.getValue();
						
			TreeMap<Integer, Double> singleUsage = singleDstat.getHddWriteBytes();
			
			for(Entry<Integer, Double> entrySingle : singleUsage.entrySet()) {
												
				Integer xPoint = entrySingle.getKey();
				Double yPoint = entrySingle.getValue();
				
				if(xPoint > minXPoint)
					break;
				
				if(average.containsKey(xPoint)) {
					yPoint += average.get(xPoint);
				}
				average.put(xPoint, yPoint);
				
			}
						
		}

		int size = dstats.size();
			
		for(Entry<Integer, Double> entryMemory : average.entrySet()) {
			
			Integer xPoint = entryMemory.getKey();
			Double yPoint = entryMemory.getValue();
										
			average.put(xPoint, (yPoint/size));
			
		}
		
		XYChart.Series serie = new XYChart.Series();
		serie.setName(label);
		
		for(Entry<Integer, Double> entry : average.entrySet()) {
			Integer xPoint = entry.getKey();
			Double yPoint = entry.getValue();
			serie.getData().add(new XYChart.Data(xPoint, yPoint));
		}
		
		return serie;
	}
	
	public static XYChart.Series getNetRecvBytes(Dstat dstat, String label) {
		
		TreeMap<Integer, Double> usage = dstat.getNetRecvBytes();
				
		XYChart.Series serie = new XYChart.Series();
		serie.setName(label);
		
		for(Entry<Integer, Double> entry : usage.entrySet()) {
			Integer xPoint = entry.getKey();
			Double yPoint = entry.getValue();
			serie.getData().add(new XYChart.Data(xPoint, yPoint));
		}
		
		return serie;
	}
	
	public static XYChart.Series getNetRecvBytesAverage(TreeMap<String, Dstat> dstats, String label) {
		
		TreeMap<Integer, Double> average = new TreeMap<Integer, Double>();
		
		int minXPoint = getMinLastXPoint(dstats);
		
		for(Entry<String, Dstat> entry : dstats.entrySet()) {
			
			String keyDstat = entry.getKey();
			Dstat singleDstat = entry.getValue();
						
			TreeMap<Integer, Double> singleUsage = singleDstat.getNetRecvBytes();
			
			for(Entry<Integer, Double> entrySingle : singleUsage.entrySet()) {
												
				Integer xPoint = entrySingle.getKey();
				Double yPoint = entrySingle.getValue();
				
				if(xPoint > minXPoint)
					break;
				
				if(average.containsKey(xPoint)) {
					yPoint += average.get(xPoint);
				}
				average.put(xPoint, yPoint);
				
			}
						
		}

		int size = dstats.size();
			
		for(Entry<Integer, Double> entryMemory : average.entrySet()) {
			
			Integer xPoint = entryMemory.getKey();
			Double yPoint = entryMemory.getValue();
										
			average.put(xPoint, (yPoint/size));
			
		}
		
		XYChart.Series serie = new XYChart.Series();
		serie.setName(label);
		
		for(Entry<Integer, Double> entry : average.entrySet()) {
			Integer xPoint = entry.getKey();
			Double yPoint = entry.getValue();
			serie.getData().add(new XYChart.Data(xPoint, yPoint));
		}
		
		return serie;
	}
	
	public static XYChart.Series getNetSendBytes(Dstat dstat, String label) {
		
		TreeMap<Integer, Double> usage = dstat.getNetSendBytes();
				
		XYChart.Series serie = new XYChart.Series();
		serie.setName(label);
		
		for(Entry<Integer, Double> entry : usage.entrySet()) {
			Integer xPoint = entry.getKey();
			Double yPoint = entry.getValue();
			serie.getData().add(new XYChart.Data(xPoint, yPoint));
		}
		
		return serie;
	}
	
	public static XYChart.Series getNetSendBytesAverage(TreeMap<String, Dstat> dstats, String label) {
		
		TreeMap<Integer, Double> average = new TreeMap<Integer, Double>();
		
		int minXPoint = getMinLastXPoint(dstats);
		
		for(Entry<String, Dstat> entry : dstats.entrySet()) {
			
			String keyDstat = entry.getKey();
			Dstat singleDstat = entry.getValue();
						
			TreeMap<Integer, Double> singleUsage = singleDstat.getNetSendBytes();
			
			for(Entry<Integer, Double> entrySingle : singleUsage.entrySet()) {
												
				Integer xPoint = entrySingle.getKey();
				Double yPoint = entrySingle.getValue();
				
				if(xPoint > minXPoint)
					break;
				
				if(average.containsKey(xPoint)) {
					yPoint += average.get(xPoint);
				}
				average.put(xPoint, yPoint);
				
			}
						
		}

		int size = dstats.size();
			
		for(Entry<Integer, Double> entryMemory : average.entrySet()) {
			
			Integer xPoint = entryMemory.getKey();
			Double yPoint = entryMemory.getValue();
										
			average.put(xPoint, (yPoint/size));
			
		}
		
		XYChart.Series serie = new XYChart.Series();
		serie.setName(label);
		
		for(Entry<Integer, Double> entry : average.entrySet()) {
			Integer xPoint = entry.getKey();
			Double yPoint = entry.getValue();
			serie.getData().add(new XYChart.Data(xPoint, yPoint));
		}
		
		return serie;
	}
	
	public static XYChart.Series getNetRecvPackets(Dstat dstat, String label) {
		
		TreeMap<Integer, Double> usage = dstat.getNetRecvPackets();
				
		XYChart.Series serie = new XYChart.Series();
		serie.setName(label);
		
		for(Entry<Integer, Double> entry : usage.entrySet()) {
			Integer xPoint = entry.getKey();
			Double yPoint = entry.getValue();
			serie.getData().add(new XYChart.Data(xPoint, yPoint));
		}
		
		return serie;
	}
	
	public static XYChart.Series getNetRecvPacketsAverage(TreeMap<String, Dstat> dstats, String label) {
		
		TreeMap<Integer, Double> average = new TreeMap<Integer, Double>();
		
		int minXPoint = getMinLastXPoint(dstats);
		
		for(Entry<String, Dstat> entry : dstats.entrySet()) {
			
			String keyDstat = entry.getKey();
			Dstat singleDstat = entry.getValue();
						
			TreeMap<Integer, Double> singleUsage = singleDstat.getNetRecvPackets();
			
			for(Entry<Integer, Double> entrySingle : singleUsage.entrySet()) {
												
				Integer xPoint = entrySingle.getKey();
				Double yPoint = entrySingle.getValue();
				
				if(xPoint > minXPoint)
					break;
				
				if(average.containsKey(xPoint)) {
					yPoint += average.get(xPoint);
				}
				average.put(xPoint, yPoint);
				
			}
						
		}

		int size = dstats.size();
			
		for(Entry<Integer, Double> entryMemory : average.entrySet()) {
			
			Integer xPoint = entryMemory.getKey();
			Double yPoint = entryMemory.getValue();
										
			average.put(xPoint, (yPoint/size));
			
		}
		
		XYChart.Series serie = new XYChart.Series();
		serie.setName(label);
		
		for(Entry<Integer, Double> entry : average.entrySet()) {
			Integer xPoint = entry.getKey();
			Double yPoint = entry.getValue();
			serie.getData().add(new XYChart.Data(xPoint, yPoint));
		}
		
		return serie;
	}
	
	public static XYChart.Series getNetSendPackets(Dstat dstat, String label) {
		
		TreeMap<Integer, Double> usage = dstat.getNetSendPackets();
				
		XYChart.Series serie = new XYChart.Series();
		serie.setName(label);
		
		for(Entry<Integer, Double> entry : usage.entrySet()) {
			Integer xPoint = entry.getKey();
			Double yPoint = entry.getValue();
			serie.getData().add(new XYChart.Data(xPoint, yPoint));
		}
		
		return serie;
	}
	
	public static XYChart.Series getNetSendPacketsAverage(TreeMap<String, Dstat> dstats, String label) {
		
		TreeMap<Integer, Double> average = new TreeMap<Integer, Double>();
		
		int minXPoint = getMinLastXPoint(dstats);
		
		for(Entry<String, Dstat> entry : dstats.entrySet()) {
			
			String keyDstat = entry.getKey();
			Dstat singleDstat = entry.getValue();
						
			TreeMap<Integer, Double> singleUsage = singleDstat.getNetSendPackets();
			
			for(Entry<Integer, Double> entrySingle : singleUsage.entrySet()) {
												
				Integer xPoint = entrySingle.getKey();
				Double yPoint = entrySingle.getValue();
				
				if(xPoint > minXPoint)
					break;
				
				if(average.containsKey(xPoint)) {
					yPoint += average.get(xPoint);
				}
				average.put(xPoint, yPoint);
				
			}
						
		}

		int size = dstats.size();
			
		for(Entry<Integer, Double> entryMemory : average.entrySet()) {
			
			Integer xPoint = entryMemory.getKey();
			Double yPoint = entryMemory.getValue();
										
			average.put(xPoint, (yPoint/size));
			
		}
		
		XYChart.Series serie = new XYChart.Series();
		serie.setName(label);
		
		for(Entry<Integer, Double> entry : average.entrySet()) {
			Integer xPoint = entry.getKey();
			Double yPoint = entry.getValue();
			serie.getData().add(new XYChart.Data(xPoint, yPoint));
		}
		
		return serie;
	}
	
	private static int getMinLastXPoint(TreeMap<String, Dstat> dstats) {
		
		int min = dstats.firstEntry().getValue().getLastXPoint();
		
		for(Entry<String, Dstat> entry : dstats.entrySet()) {
			
			int lastXPoint = entry.getValue().getLastXPoint();
			
			if(lastXPoint < min) {
				min = lastXPoint;
			}
			
		}
		
		return min;
		
	}
	
}
