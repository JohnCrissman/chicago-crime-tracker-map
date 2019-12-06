import javafx.collections.FXCollections;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.FlowPane;
import java.util.List;
import java.util.stream.Stream;
import static java.util.stream.Collectors.toList;

public class SummaryChartView {
    private FlowPane viewOfCharts;
    private BarChart<String, Number> dayOfWeekChart;
    private BarChart<String, Number> dayOfMonthChart;
    private BarChart<String, Number> typeOfCrimeChart;
    private Crimes latestCrimes;

    public SummaryChartView(Crimes latestCrimes) {
        this.latestCrimes = latestCrimes;
        this.viewOfCharts = new FlowPane();
        this.setUpDayOfWeekChart();
        this.setUpDayOfMonthChart();
        this.setUpTypeOfCrimeChart();
    }

    private void setUpDayOfWeekChart() {
        //Defining the axes
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setCategories(FXCollections.observableArrayList(DayOfWeekCrime.stringValues()));
        NumberAxis yAxis = new NumberAxis();

        //Creating the Bar chart
        this.dayOfWeekChart = new BarChart<>(xAxis, yAxis);
        this.dayOfWeekChart.setTitle("Crimes by day of week");
        this.dayOfWeekChart.setLegendVisible(false);

        this.viewOfCharts.getChildren().add(this.dayOfWeekChart);
    }

    private void setUpDayOfMonthChart() {
        //Defining the x & y axes
        CategoryAxis xAxis = new CategoryAxis();
        List<String> allDays = Stream.iterate("1", x -> (Integer.parseInt(x) + 1)+"")
                                    .limit(31)
                                    .collect(toList());
        xAxis.setCategories(FXCollections.observableArrayList(allDays));
        NumberAxis yAxis = new NumberAxis();

        //Creating the Bar chart
        this.dayOfMonthChart = new BarChart<>(xAxis, yAxis);
        this.dayOfMonthChart.setTitle("Crimes by day of month");
        this.dayOfMonthChart.setLegendVisible(false);

        this.viewOfCharts.getChildren().add(this.dayOfMonthChart);
    }

    //currently empty
    private void setUpTypeOfCrimeChart() {
        //Defining the x & y axes
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        //Creating the Bar chart
        this.typeOfCrimeChart = new BarChart<>(xAxis, yAxis);
        this.typeOfCrimeChart.setTitle("Number of each type of crime");
        this.typeOfCrimeChart.setLegendVisible(false);

        this.viewOfCharts.getChildren().add(this.typeOfCrimeChart);
    }

    public void updateSummaryForNewAddress() {
        this.dayOfWeekChart.getData().clear();
        this.dayOfMonthChart.getData().clear();
//        this.typeOfCrimeChart.getData().clear();

        //Prepare XYChart.Series objects by setting data
        XYChart.Series<String, Number> dayOfWeekUpdate = new XYChart.Series<>();
        this.latestCrimes.countByDayOfWeek().entrySet().stream()
                .peek(System.out::println)
                .peek(e -> System.out.println("Sup"))
                .forEach(e -> dayOfWeekUpdate.getData().add(new XYChart.Data<>(e.getKey().toString(),e.getValue())));
//        dayOfWeekUpdate.setName("Count by Day of Week");
//        dayOfWeekUpdate.getData().add(new XYChart.Data<>("Tuesday", Math.random()*5));
//        dayOfWeekUpdate.getData().add(new XYChart.Data<>("Wednesday", Math.random()*5));
//        dayOfWeekUpdate.getData().add(new XYChart.Data<>("Sunday", Math.random()*5));
//        dayOfWeekUpdate.getData().add(new XYChart.Data<>("Friday", Math.random()*5));
        //Setting the data to bar chart
        this.dayOfWeekChart.getData().addAll(dayOfWeekUpdate);

        //Prepare XYChart.Series objects by setting data
        XYChart.Series<String, Number> dayOfMonthUpdate = new XYChart.Series<>();
        dayOfMonthUpdate.setName("Count by Day of Week");
        dayOfMonthUpdate.getData().add(new XYChart.Data<>("1", Math.random()*5));
        dayOfMonthUpdate.getData().add(new XYChart.Data<>("2", Math.random()*5));
        dayOfMonthUpdate.getData().add(new XYChart.Data<>("3", Math.random()*5));
        dayOfMonthUpdate.getData().add(new XYChart.Data<>("4", Math.random()*5));
        //Setting the data to bar chart
        this.dayOfMonthChart.getData().addAll(dayOfMonthUpdate);

    }

    public FlowPane getViewOfCharts() {
        return viewOfCharts;
    }
}
