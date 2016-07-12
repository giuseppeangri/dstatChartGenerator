package readers;

import java.util.TreeMap;

public class Dstat {
	
	private TreeMap<Integer, Float> cpuUsage;
	private TreeMap<Integer, Long> memoryUsage;
	private TreeMap<Integer, Double> hddReadBytes;
	private TreeMap<Integer, Double> hddWriteBytes;
	private TreeMap<Integer, Double> netRecvBytes;
	private TreeMap<Integer, Double> netSendBytes;
	private TreeMap<Integer, Double> netRecvPackets;
	private TreeMap<Integer, Double> netSendPackets;
	private int lastXPoint;
	
	public Dstat(TreeMap<Integer, Float> cpuUsage, TreeMap<Integer, Long> memoryUsage,
			TreeMap<Integer, Double> hddReadBytes, TreeMap<Integer, Double> hddWriteBytes,
			TreeMap<Integer, Double> netRecvBytes, TreeMap<Integer, Double> netSendBytes,
			TreeMap<Integer, Double> netRecvPackets, TreeMap<Integer, Double> netSendPackets, int lastXPoint) {
		super();
		this.cpuUsage = cpuUsage;
		this.memoryUsage = memoryUsage;
		this.hddReadBytes = hddReadBytes;
		this.hddWriteBytes = hddWriteBytes;
		this.netRecvBytes = netRecvBytes;
		this.netSendBytes = netSendBytes;
		this.netRecvPackets = netRecvPackets;
		this.netSendPackets = netSendPackets;
		this.lastXPoint = lastXPoint;
	}
	
	public Dstat() {
		
	}

	public TreeMap<Integer, Float> getCpuUsage() {
		return cpuUsage;
	}

	public void setCpuUsage(TreeMap<Integer, Float> cpuUsage) {
		this.cpuUsage = cpuUsage;
	}

	public TreeMap<Integer, Long> getMemoryUsage() {
		return memoryUsage;
	}

	public void setMemoryUsage(TreeMap<Integer, Long> memoryUsage) {
		this.memoryUsage = memoryUsage;
	}

	public TreeMap<Integer, Double> getHddReadBytes() {
		return hddReadBytes;
	}

	public void setHddReadBytes(TreeMap<Integer, Double> hddReadBytes) {
		this.hddReadBytes = hddReadBytes;
	}

	public TreeMap<Integer, Double> getHddWriteBytes() {
		return hddWriteBytes;
	}

	public void setHddWriteBytes(TreeMap<Integer, Double> hddWriteBytes) {
		this.hddWriteBytes = hddWriteBytes;
	}

	public TreeMap<Integer, Double> getNetRecvBytes() {
		return netRecvBytes;
	}

	public void setNetRecvBytes(TreeMap<Integer, Double> netRecvBytes) {
		this.netRecvBytes = netRecvBytes;
	}

	public TreeMap<Integer, Double> getNetSendBytes() {
		return netSendBytes;
	}

	public void setNetSendBytes(TreeMap<Integer, Double> netSendBytes) {
		this.netSendBytes = netSendBytes;
	}

	public TreeMap<Integer, Double> getNetRecvPackets() {
		return netRecvPackets;
	}

	public void setNetRecvPackets(TreeMap<Integer, Double> netRecvPackets) {
		this.netRecvPackets = netRecvPackets;
	}

	public TreeMap<Integer, Double> getNetSendPackets() {
		return netSendPackets;
	}

	public void setNetSendPackets(TreeMap<Integer, Double> netSendPackets) {
		this.netSendPackets = netSendPackets;
	}
	
	public int getLastXPoint() {
		return lastXPoint;
	}

	public void setLastXPoint(int lastXPoint) {
		this.lastXPoint = lastXPoint;
	}

	@Override
	public String toString(){
		return "cpuUsage= "+      cpuUsage+     " %\n"+
				"memoryUsage= "+   memoryUsage+  " byte\n"+
				"hddReadBytes= "+   hddReadBytes+  " byte\n"+
				"hddWriteBytes= "+  hddWriteBytes+ " byte\n"+
				"netRecvBytes= "+   netRecvBytes+  " byte\n"+
				"netSendBytes= "+   netSendBytes+ " byte\n"+
				"netRecvPackets= "+ netRecvPackets+" #\n"+
				"netSendPackets= "+ netSendPackets+" #\n"+
				"lastXPoint= "+lastXPoint;
	}

}
