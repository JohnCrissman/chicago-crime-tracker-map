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
        this.typeOfCrimeChart.getData().clear();

        //update day of week chart
        XYChart.Series<String, Number> dayOfWeekUpdate = new XYChart.Series<>();
        this.latestCrimes.countByDayOfWeek().entrySet().stream()
                .forEach(e -> dayOfWeekUpdate.getData().add(new XYChart.Data<>(e.getKey().toString(),e.getValue())));
        this.dayOfWeekChart.getData().add(dayOfWeekUpdate);

        //update day of month chart
        XYChart.Series<String, Number> dayOfMonthUpdate = new XYChart.Series<>();
        this.latestCrimes.countByDayOfMonth().entrySet().stream()
                .forEach(e -> dayOfMonthUpdate.getData().add(new XYChart.Data<>(e.getKey().toString(),e.getValue())));
        this.dayOfMonthChart.getData().add(dayOfMonthUpdate);

        //update type of crime chart
        XYChart.Series<String, Number> typeOfCrimeUpdate = new XYChart.Series<>();
        this.latestCrimes.countByType().entrySet().stream()
                .forEach(e -> typeOfCrimeUpdate.getData().add(new XYChart.Data<>(e.getKey(),e.getValue())));
        this.typeOfCrimeChart.getData().add(typeOfCrimeUpdate);

    }

    public FlowPane getViewOfCharts() {
        return viewOfCharts;
    }
}
