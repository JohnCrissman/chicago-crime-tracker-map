import javafx.collections.FXCollections;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;

public class SummaryChartView {
    private VBox viewOfCharts;
    private BarChart<String, Number> dayOfWeekChart;
    private BarChart<String, Number> dayOfMonthChart;
    private BarChart<String, Number> typeOfCrimeChart;
    private Crimes latestCrimes;

    public SummaryChartView(Crimes latestCrimes) {
        this.latestCrimes = latestCrimes;
        this.viewOfCharts = new VBox();
        this.setUpDayOfWeekChart();
        this.setUpDayOfMonthChart();
        this.setUpTypeOfCrimeChart();
    }

    private void setUpDayOfWeekChart() {
        //Defining the x axis
        CategoryAxis xAxis = new CategoryAxis();

        xAxis.setCategories(FXCollections.observableArrayList(DayOfWeekCrime.stringValues()));
        xAxis.setLabel("x-axis label");

        //Defining the y axis
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("y-axis label");

        //Creating the Bar chart
        this.dayOfWeekChart = new BarChart<>(xAxis, yAxis);
        this.dayOfWeekChart.setTitle("Crimes by day of week");
        this.dayOfWeekChart.setLegendVisible(false);

        //Prepare XYChart.Series objects by setting data
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("Count by Day of Week");

        series1.getData().add(new XYChart.Data<>("Monday", 0));
        series1.getData().add(new XYChart.Data<>("Friday", 0));
        series1.getData().add(new XYChart.Data<>("Sunday", 0));
        series1.getData().add(new XYChart.Data<>("Tuesday", 1));

        //Setting the data to bar chart
        this.dayOfWeekChart.getData().addAll(series1) ;

        this.viewOfCharts.getChildren().add(this.dayOfWeekChart);
    }

    private void setUpDayOfMonthChart() {
        //Defining the x axis
        CategoryAxis xAxis = new CategoryAxis();

        xAxis.setCategories(FXCollections.observableArrayList("1","2","3","4"));
        xAxis.setLabel("x-axis label");

        //Defining the y axis
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("y-axis label");

        //Creating the Bar chart
        this.dayOfMonthChart = new BarChart<>(xAxis, yAxis);
        this.dayOfMonthChart.setTitle("Crimes by day of month");
        this.dayOfMonthChart.setLegendVisible(false);

        //Prepare XYChart.Series objects by setting data
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("Count by Day of Week");

        series1.getData().add(new XYChart.Data<>("1", 0));
        series1.getData().add(new XYChart.Data<>("2", 0));
        series1.getData().add(new XYChart.Data<>("3", 0));
        series1.getData().add(new XYChart.Data<>("4", 1));

        //Setting the data to bar chart
        this.dayOfMonthChart.getData().addAll(series1) ;

        this.viewOfCharts.getChildren().add(this.dayOfMonthChart);
    }

    private void setUpTypeOfCrimeChart() {

    }

    public void updateSummaryForNewAddress() {
        this.dayOfWeekChart.getData().clear();
        this.dayOfMonthChart.getData().clear();
//        this.typeOfCrimeChart.getData().clear();

        //Prepare XYChart.Series objects by setting data
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("Count by Day of Week");

        series1.getData().add(new XYChart.Data<>("1", 1));
        series1.getData().add(new XYChart.Data<>("2", 1));
        series1.getData().add(new XYChart.Data<>("3", 2));
        series1.getData().add(new XYChart.Data<>("4", 1));

        //Setting the data to bar chart
        this.dayOfMonthChart.getData().addAll(series1) ;
    }

    public VBox getViewOfCharts() {
        return viewOfCharts;
    }
    public BarChart<String, Number> getDayOfWeekChart() {
        return dayOfWeekChart;
    }
    public BarChart<String, Number> getDayOfMonthChart() {
        return dayOfMonthChart;
    }
    public BarChart<String, Number> getTypeOfCrimeChart() {
        return typeOfCrimeChart;
    }
}
