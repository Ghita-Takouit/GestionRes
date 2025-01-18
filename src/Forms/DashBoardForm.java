package Forms;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;

public class DashBoardForm extends javax.swing.JPanel {

    public DashBoardForm() {
        initComponents();
        generateCharts();
    }

    private void generateCharts() {
        jPanel2.add(createChartPanel(createDonutChart()), BorderLayout.CENTER);
        jPanel3.add(createChartPanel(createHorizontalBarChart()), BorderLayout.CENTER);
        jPanel4.add(createChartPanel(createAreaChart()), BorderLayout.CENTER);
        jPanel5.add(createChartPanel(createStackedBarChart()), BorderLayout.CENTER); // Nouveau graphique en barres empilées
    }

    private ChartPanel createChartPanel(JFreeChart chart) {
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(400, 300));
        return chartPanel;
    }

    private JFreeChart createDonutChart() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Suite", 40);
        dataset.setValue("Double", 20);
        dataset.setValue("King", 10);
        dataset.setValue("Queen", 15);
        dataset.setValue("Single", 30);

        JFreeChart chart = ChartFactory.createPieChart(
                "Catégories les plus réservées", // Traduction du titre
                dataset, 
                true, 
                true, 
                false
        );

        chart.setBackgroundPaint(new Color(240, 240, 240)); // Fond gris clair
        return chart;
    }

    private JFreeChart createHorizontalBarChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(15, "Ghita", "Janvier");
        dataset.addValue(10, "Hamza", "Février");
        dataset.addValue(12, "Zakaria", "Mars");
        dataset.addValue(18, "Razin", "Avril");
        dataset.addValue(18, "Achraf", "Mai");

        JFreeChart chart = ChartFactory.createBarChart(
                "Meilleurs clients", // Traduction du titre
                "Mois", // Axe X
                "Réservations", // Axe Y
                dataset, 
                PlotOrientation.HORIZONTAL, // Graphique à barres horizontales
                true, 
                true, 
                false
        );

        BarRenderer renderer = (BarRenderer) chart.getCategoryPlot().getRenderer();
        renderer.setSeriesPaint(0, new Color(76, 175, 80)); // Vert
        chart.getPlot().setBackgroundPaint(new Color(240, 240, 240)); // Fond gris clair
        return chart;
    }

    private JFreeChart createAreaChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(1, "Réservations", "Janvier");
        dataset.addValue(4, "Réservations", "Février");
        dataset.addValue(3, "Réservations", "Mars");
        dataset.addValue(5, "Réservations", "Avril");
        dataset.addValue(6, "Réservations", "Mai");

        JFreeChart chart = ChartFactory.createAreaChart(
                "Réservations au fil du temps", // Traduction du titre
                "Mois", // Axe X
                "Nombre de réservations", // Axe Y
                dataset, 
                PlotOrientation.VERTICAL, 
                true, 
                true, 
                false
        );

        chart.getPlot().setBackgroundPaint(new Color(240, 240, 240)); // Fond gris clair
        return chart;
    }

    private JFreeChart createStackedBarChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(8, "En ligne", "Janvier");
        dataset.addValue(10, "Sur place", "Janvier");
        dataset.addValue(12, "Téléphone", "Janvier");

        dataset.addValue(15, "En ligne", "Février");
        dataset.addValue(12, "Sur place", "Février");
        dataset.addValue(8, "Téléphone", "Février");

        dataset.addValue(18, "En ligne", "Mars");
        dataset.addValue(14, "Sur place", "Mars");
        dataset.addValue(10, "Téléphone", "Mars");

        dataset.addValue(20, "En ligne", "Avril");
        dataset.addValue(18, "Sur place", "Avril");
        dataset.addValue(12, "Téléphone", "Avril");

        JFreeChart chart = ChartFactory.createStackedBarChart(
                "Sources de réservation par mois", // Traduction du titre
                "Mois", // Axe X
                "Nombre de réservations", // Axe Y
                dataset, 
                PlotOrientation.VERTICAL, 
                true, 
                true, 
                false
        );

        BarRenderer renderer = (BarRenderer) chart.getCategoryPlot().getRenderer();
        renderer.setSeriesPaint(0, new Color(33, 150, 243)); // Bleu
        renderer.setSeriesPaint(1, new Color(76, 175, 80)); // Vert
        renderer.setSeriesPaint(2, new Color(255, 193, 7)); // Jaune
        chart.getPlot().setBackgroundPaint(new Color(240, 240, 240)); // Fond gris clair
        return chart;
    }

    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setLayout(new java.awt.GridLayout(2, 2, 10, 10));

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.setLayout(new java.awt.BorderLayout());
        jPanel1.add(jPanel2);

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.setLayout(new java.awt.BorderLayout());
        jPanel1.add(jPanel3);

        jPanel4.setBackground(new java.awt.Color(204, 204, 204));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel4.setLayout(new java.awt.BorderLayout());
        jPanel1.add(jPanel4);

        jPanel5.setBackground(new java.awt.Color(204, 204, 204));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel5.setLayout(new java.awt.BorderLayout());
        jPanel1.add(jPanel5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }

    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
}
