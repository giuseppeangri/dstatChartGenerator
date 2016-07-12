package charts;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.image.WritableImage;
import javafx.scene.transform.Transform;
import javafx.stage.Stage;

public class Chart {
	
	private String title;
	private String xLabel;
	private String yLabel;
	private ArrayList<XYChart.Series> data;
	
	public Chart(String title) {
		super();
		this.title = title;
	}
	
	public Chart(String title, String xLabel, String yLabel, ArrayList<XYChart.Series> data) {
		super();
		this.title = title;
		this.xLabel = xLabel;
		this.yLabel = yLabel;
		this.data = data;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getxLabel() {
		return xLabel;
	}

	public void setxLabel(String xLabel) {
		this.xLabel = xLabel;
	}

	public String getyLabel() {
		return yLabel;
	}

	public void setyLabel(String yLabel) {
		this.yLabel = yLabel;
	}

	public ArrayList<XYChart.Series> getData() {
		return data;
	}

	public void setData(ArrayList<XYChart.Series> data) {
		this.data = data;
	}
	
	public void print(Stage stage, String outputDirectory) {
		
		final NumberAxis xAxis = new NumberAxis();
		final NumberAxis yAxis = new NumberAxis();
		
		xAxis.setLabel(xLabel);
		yAxis.setLabel(yLabel);
		
		final LineChart<Number,Number> lineChart = new LineChart<Number,Number>(xAxis,yAxis);
		lineChart.setTitle(title);
		
		for(XYChart.Series object: data) {
			lineChart.getData().add(object);
		}
		
		lineChart.setAnimated(false);
		
		Scene scene  = new Scene(lineChart, 1280, 800);
		stage.setScene(scene);

		SnapshotParameters param = new SnapshotParameters();
		param.setTransform(Transform.scale(4, 4));
		
		WritableImage image = lineChart.snapshot(param, null);

		File file = new File(outputDirectory+title+".png");

		try {
			ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
		} catch (IOException e) {
			// TODO: handle exception heres
		}
		
	}
	
}
