package pdf.generator;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import pdf.generator.model.Product;
import pdf.generator.model.Tasks;
import pdf.generator.model.User;
import pdf.generator.model.Warehouse;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import static org.jfree.chart.ChartFactory.createPieChart;


public class PdfGeneratorService {

    public ByteArrayInputStream generateUserReport(List<User> users, boolean includeTasks) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (PdfWriter writer = new PdfWriter(out)) {
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc, PageSize.A4);

            // Get current date
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM, yyyy", Locale.ENGLISH);
            String formattedDate = currentDate.format(formatter);

            // Add title
            Paragraph title = new Paragraph("User Report")
                    .setFontSize(20)
                    .setBold()
                    .setMarginBottom(10);
            document.add(title);

            // Add current date
            Paragraph dateParagraph = new Paragraph("Date: " + formattedDate)
                    .setFontSize(12)
                    .setMarginBottom(10);
            document.add(dateParagraph);

            // Add line separator
            LineSeparator ls = new LineSeparator(new SolidLine());
            ls.setHorizontalAlignment(HorizontalAlignment.CENTER);
            ls.setMarginBottom(20);
            document.add(ls);

            // Define table with 6 columns
            Table table = new Table(UnitValue.createPercentArray(new float[]{2, 2, 2, 2, 2, 2}))
                    .setWidth(UnitValue.createPercentValue(100))
                    .setMarginBottom(20);

            // Add header cells
            String[] headers = {"Username", "Name", "Lastname", "PESEL", "Email", "Phone Number"};
            for (String header : headers) {
                table.addHeaderCell(new Paragraph(header)
                        .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                        .setBold()
                        .setBorder(Border.NO_BORDER));
            }

            // Add data cells
            for (User user : users) {
                table.addCell(new Paragraph(user.getUsername()).setBorder(Border.NO_BORDER));
                table.addCell(new Paragraph(user.getName()).setBorder(Border.NO_BORDER));
                table.addCell(new Paragraph(user.getLastname()).setBorder(Border.NO_BORDER));
                table.addCell(new Paragraph(user.getPesel()).setBorder(Border.NO_BORDER));
                table.addCell(new Paragraph(user.getEmail()).setBorder(Border.NO_BORDER));
                table.addCell(new Paragraph(user.getPhoneNumber()).setBorder(Border.NO_BORDER));

                if (includeTasks) {
                    // This could be improved by listing tasks in the table, but we'll maintain a paragraph for now
                    document.add(new Paragraph("Tasks for " + user.getName() + " " + user.getLastname() + ":")
                            .setMarginTop(10));
                }
            }

            // Add table to document
            document.add(table);

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ByteArrayInputStream(out.toByteArray());
    }


    public ByteArrayInputStream generateWarehouseReport(List<Warehouse> warehouses) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (PdfWriter writer = new PdfWriter(out)) {
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc, PageSize.A4);

            // Get current date
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM, yyyy", Locale.ENGLISH);
            String formattedDate = currentDate.format(formatter);

            // Add title
            Paragraph title = new Paragraph("Warehouse Report")
                    .setFontSize(20)
                    .setBold()
                    .setMarginBottom(10);
            document.add(title);

            // Add current date
            Paragraph dateParagraph = new Paragraph("Date: " + formattedDate)
                    .setFontSize(12)
                    .setMarginBottom(10);
            document.add(dateParagraph);

            // Add line separator
            LineSeparator ls = new LineSeparator(new SolidLine());
            ls.setHorizontalAlignment(HorizontalAlignment.CENTER);
            ls.setMarginBottom(20);
            document.add(ls);

            // Define table with 7 columns
            Table table = new Table(UnitValue.createPercentArray(new float[]{2, 1, 2, 3, 3, 3, 3}))
                    .setWidth(UnitValue.createPercentValue(100))
                    .setMarginBottom(20);

            // Add header cells with uniform background color
            String[] headers = {"Building", "Zone", "Space ID", "Space Height", "Space Width", "Space Length", "Product ID"};
            for (String header : headers) {
                table.addHeaderCell(new Paragraph(header)
                        .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                        .setBold()
                        .setBorder(Border.NO_BORDER)); // Ensure borders are not visible
            }

            // Add data cells
            for (Warehouse warehouse : warehouses) {
                table.addCell(new Paragraph(warehouse.getBuilding()).setBorder(Border.NO_BORDER));
                table.addCell(new Paragraph(warehouse.getZone()).setBorder(Border.NO_BORDER));
                table.addCell(new Paragraph(String.valueOf(warehouse.getSpaceId())).setBorder(Border.NO_BORDER));
                table.addCell(new Paragraph(String.valueOf(warehouse.getSpaceHeight())).setBorder(Border.NO_BORDER));
                table.addCell(new Paragraph(String.valueOf(warehouse.getSpaceWidth())).setBorder(Border.NO_BORDER));
                table.addCell(new Paragraph(String.valueOf(warehouse.getSpaceLength())).setBorder(Border.NO_BORDER));
                table.addCell(new Paragraph(String.valueOf(warehouse.getProductId())).setBorder(Border.NO_BORDER));
            }

            // Add table to document
            document.add(table);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ByteArrayInputStream(out.toByteArray());
    }

    public ByteArrayInputStream generateProductReport(List<Product> products) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (PdfWriter writer = new PdfWriter(out)) {
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Get current date
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM, yyyy", Locale.ENGLISH);
            String formattedDate = currentDate.format(formatter);

            // Add title
            Paragraph title = new Paragraph("Product In Warehouse Report")
                    .setFontSize(20)
                    .setBold()
                    .setMarginBottom(10);
            document.add(title);

            // Add current date
            Paragraph dateParagraph = new Paragraph("Date: " + formattedDate)
                    .setFontSize(12)
                    .setMarginBottom(10);
            document.add(dateParagraph);

            // Add line separator
            LineSeparator ls = new LineSeparator(new SolidLine());
            ls.setHorizontalAlignment(HorizontalAlignment.CENTER);
            ls.setMarginBottom(20);
            document.add(ls);

            // Define table with 6 columns
            Table table = new Table(UnitValue.createPercentArray(new float[]{4, 3, 2, 2, 2, 2}))
                    .setWidth(UnitValue.createPercentValue(100))
                    .setMarginBottom(20);

            // Add header cells
            table.addHeaderCell(new Paragraph("Product").setBackgroundColor(ColorConstants.LIGHT_GRAY).setBold());
            table.addHeaderCell(new Paragraph("Code").setBackgroundColor(ColorConstants.LIGHT_GRAY).setBold());
            table.addHeaderCell(new Paragraph("Width").setBackgroundColor(ColorConstants.LIGHT_GRAY).setBold());
            table.addHeaderCell(new Paragraph("Height").setBackgroundColor(ColorConstants.LIGHT_GRAY).setBold());
            table.addHeaderCell(new Paragraph("Length").setBackgroundColor(ColorConstants.LIGHT_GRAY).setBold());
            table.addHeaderCell(new Paragraph("Weight").setBackgroundColor(ColorConstants.LIGHT_GRAY).setBold());

            // Add data cells
            for (Product product : products) {
                table.addCell(new Paragraph(product.getName()).setBorder(Border.NO_BORDER));
                table.addCell(new Paragraph(product.getCode()).setBorder(Border.NO_BORDER));
                table.addCell(new Paragraph(String.valueOf(product.getWidth())).setBorder(Border.NO_BORDER));
                table.addCell(new Paragraph(String.valueOf(product.getHeight())).setBorder(Border.NO_BORDER));
                table.addCell(new Paragraph(String.valueOf(product.getLength())).setBorder(Border.NO_BORDER));
                table.addCell(new Paragraph(String.valueOf(product.getWeight())).setBorder(Border.NO_BORDER));
            }

            // Add table to document
            document.add(table);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ByteArrayInputStream(out.toByteArray());
    }

    public ByteArrayInputStream generateTaskReport(List<Tasks> tasks, boolean includeUsers, boolean includeProducts, boolean includeWarehouses, boolean includePieChart) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (PdfWriter writer = new PdfWriter(out)) {
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Get current date
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM, yyyy", Locale.ENGLISH);
            String formattedDate = currentDate.format(formatter);

            // Add title
            Paragraph title = new Paragraph("Task Report")
                    .setFontSize(20)
                    .setBold()
                    .setMarginBottom(10);
            document.add(title);

            // Add current date
            Paragraph dateParagraph = new Paragraph("Date: " + formattedDate)
                    .setFontSize(12)
                    .setMarginBottom(10);
            document.add(dateParagraph);

            // Add line separator
            LineSeparator ls = new LineSeparator(new SolidLine());
            ls.setHorizontalAlignment(HorizontalAlignment.CENTER);
            ls.setMarginBottom(20);
            document.add(ls);

            for (Tasks task : tasks) {
                addTaskToDocument(task, document, includeUsers, includeProducts, includeWarehouses);
                document.add(new Paragraph("\n"));  // Add space between tasks
            }

            if (includePieChart) {
                document.add(ls);
                Image pieChartImage = createPieChart(tasks);
                document.add(pieChartImage);
            }

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ByteArrayInputStream(out.toByteArray());
    }

    private void addTaskToDocument(Tasks task, Document document, boolean includeUsers, boolean includeProducts, boolean includeWarehouses) {
        // Adding Task details in a table
        Table table = new Table(UnitValue.createPercentArray(new float[]{2, 2, 3, 1, 1, 3, 3, 2}))
                .setWidth(UnitValue.createPercentValue(100))
                .setMarginBottom(10)
                .setTextAlignment(TextAlignment.LEFT);

        // Header cells
        table.addHeaderCell(new Paragraph("Task ID").setBackgroundColor(ColorConstants.LIGHT_GRAY).setBold());
        table.addHeaderCell(new Paragraph("Name").setBackgroundColor(ColorConstants.LIGHT_GRAY).setBold());
        table.addHeaderCell(new Paragraph("Description").setBackgroundColor(ColorConstants.LIGHT_GRAY).setBold());
        table.addHeaderCell(new Paragraph("State").setBackgroundColor(ColorConstants.LIGHT_GRAY).setBold());
        table.addHeaderCell(new Paragraph("Priority").setBackgroundColor(ColorConstants.LIGHT_GRAY).setBold());
        table.addHeaderCell(new Paragraph("Creation Date").setBackgroundColor(ColorConstants.LIGHT_GRAY).setBold());
        table.addHeaderCell(new Paragraph("Start Date").setBackgroundColor(ColorConstants.LIGHT_GRAY).setBold());
        table.addHeaderCell(new Paragraph("End Date").setBackgroundColor(ColorConstants.LIGHT_GRAY).setBold());

        // Data cells
        table.addCell(new Paragraph(String.valueOf(task.getId())).setBorder(Border.NO_BORDER));
        table.addCell(new Paragraph(task.getName()).setBorder(Border.NO_BORDER));
        table.addCell(new Paragraph(task.getDescription()).setBorder(Border.NO_BORDER));
        table.addCell(new Paragraph(String.valueOf(task.getState())).setBorder(Border.NO_BORDER));
        table.addCell(new Paragraph(String.valueOf(task.getPriority())).setBorder(Border.NO_BORDER));
        table.addCell(new Paragraph(task.getCreationDate().toString()).setBorder(Border.NO_BORDER));
        table.addCell(new Paragraph(task.getStartDate().toString()).setBorder(Border.NO_BORDER));
        table.addCell(new Paragraph(task.getEndDate().toString()).setBorder(Border.NO_BORDER));

        document.add(table);

        LineSeparator ls = new LineSeparator(new SolidLine());
        ls.setHorizontalAlignment(HorizontalAlignment.CENTER);
        ls.setMarginTop(10);
        ls.setMarginBottom(10);

        // Optional sections
        if (includeUsers) {
            document.add(ls);
            addUsersSection(task, document);
        }

        if (includeProducts) {
            document.add(ls);
            addProductsSection(task, document);
        }

        if (includeWarehouses) {
            document.add(ls);
            addWarehousesSection(task, document);
        }
    }

    private void addUsersSection(Tasks task, Document document) {
        document.add(new Paragraph("Users:").setBold().setMarginTop(10).setMarginBottom(5));
        Table userTable = new Table(UnitValue.createPercentArray(new float[]{3, 3, 3, 3, 3}))
                .setWidth(UnitValue.createPercentValue(100))
                .setMarginBottom(10);

        userTable.addHeaderCell(new Paragraph("Username").setBackgroundColor(ColorConstants.LIGHT_GRAY).setBold());
        userTable.addHeaderCell(new Paragraph("Name").setBackgroundColor(ColorConstants.LIGHT_GRAY).setBold());
        userTable.addHeaderCell(new Paragraph("Lastname").setBackgroundColor(ColorConstants.LIGHT_GRAY).setBold());
        userTable.addHeaderCell(new Paragraph("Email").setBackgroundColor(ColorConstants.LIGHT_GRAY).setBold());
        userTable.addHeaderCell(new Paragraph("Phone Number").setBackgroundColor(ColorConstants.LIGHT_GRAY).setBold());

        for (User user : task.getUsers()) {
            userTable.addCell(user.getUsername());
            userTable.addCell(user.getName());
            userTable.addCell(user.getLastname());
            userTable.addCell(user.getEmail());
            userTable.addCell(user.getPhoneNumber());
        }

        document.add(userTable);
    }

    private void addProductsSection(Tasks task, Document document) {
        document.add(new Paragraph("Products:").setBold().setMarginTop(10).setMarginBottom(5));
        Table productTable = new Table(UnitValue.createPercentArray(new float[]{3, 3, 2, 2, 2, 2}))
                .setWidth(UnitValue.createPercentValue(100))
                .setMarginBottom(10);

        productTable.addHeaderCell(new Paragraph("Name").setBackgroundColor(ColorConstants.LIGHT_GRAY).setBold());
        productTable.addHeaderCell(new Paragraph("Code").setBackgroundColor(ColorConstants.LIGHT_GRAY).setBold());
        productTable.addHeaderCell(new Paragraph("Width").setBackgroundColor(ColorConstants.LIGHT_GRAY).setBold());
        productTable.addHeaderCell(new Paragraph("Height").setBackgroundColor(ColorConstants.LIGHT_GRAY).setBold());
        productTable.addHeaderCell(new Paragraph("Length").setBackgroundColor(ColorConstants.LIGHT_GRAY).setBold());
        productTable.addHeaderCell(new Paragraph("Weight").setBackgroundColor(ColorConstants.LIGHT_GRAY).setBold());

        for (Product product : task.getProducts()) {
            productTable.addCell(product.getName());
            productTable.addCell(product.getCode());
            productTable.addCell(String.valueOf(product.getWidth()));
            productTable.addCell(String.valueOf(product.getHeight()));
            productTable.addCell(String.valueOf(product.getLength()));
            productTable.addCell(String.valueOf(product.getWeight()));
        }

        document.add(productTable);
    }

    private void addWarehousesSection(Tasks task, Document document) {
        document.add(new Paragraph("Warehouses:").setBold().setMarginTop(10).setMarginBottom(5));
        Table warehouseTable = new Table(UnitValue.createPercentArray(new float[]{3, 2, 2, 2, 2, 2, 2}))
                .setWidth(UnitValue.createPercentValue(100))
                .setMarginBottom(10);

        warehouseTable.addHeaderCell(new Paragraph("Building").setBackgroundColor(ColorConstants.LIGHT_GRAY).setBold());
        warehouseTable.addHeaderCell(new Paragraph("Zone").setBackgroundColor(ColorConstants.LIGHT_GRAY).setBold());
        warehouseTable.addHeaderCell(new Paragraph("SpaceId").setBackgroundColor(ColorConstants.LIGHT_GRAY).setBold());
        warehouseTable.addHeaderCell(new Paragraph("SpaceHeight").setBackgroundColor(ColorConstants.LIGHT_GRAY).setBold());
        warehouseTable.addHeaderCell(new Paragraph("SpaceWidth").setBackgroundColor(ColorConstants.LIGHT_GRAY).setBold());
        warehouseTable.addHeaderCell(new Paragraph("SpaceLength").setBackgroundColor(ColorConstants.LIGHT_GRAY).setBold());
        warehouseTable.addHeaderCell(new Paragraph("ProductId").setBackgroundColor(ColorConstants.LIGHT_GRAY).setBold());

        for (Warehouse warehouse : task.getWarehouses()) {
            warehouseTable.addCell(warehouse.getBuilding());
            warehouseTable.addCell(warehouse.getZone());
            warehouseTable.addCell(String.valueOf(warehouse.getSpaceId()));
            warehouseTable.addCell(String.valueOf(warehouse.getSpaceHeight()));
            warehouseTable.addCell(String.valueOf(warehouse.getSpaceWidth()));
            warehouseTable.addCell(String.valueOf(warehouse.getSpaceLength()));
            warehouseTable.addCell(String.valueOf(warehouse.getProductId()));
        }

        document.add(warehouseTable);
    }


    private Image createPieChart(List<Tasks> tasks) throws IOException {
        // Create a dataset
        Map<Integer, Long> taskStateCounts = tasks.stream()
                .collect(Collectors.groupingBy(Tasks::getState, Collectors.counting()));

        DefaultPieDataset dataset = new DefaultPieDataset();
        for (Map.Entry<Integer, Long> entry : taskStateCounts.entrySet()) {
            dataset.setValue("State " + entry.getKey(), entry.getValue());
        }

        // Create the chart
        JFreeChart chart = ChartFactory.createPieChart("Task State Distribution", dataset, true, true, false);

        // Convert the chart to an image
        BufferedImage bufferedImage = chart.createBufferedImage(500, 400);
        ByteArrayOutputStream chartOut = new ByteArrayOutputStream();
        ChartUtils.writeBufferedImageAsPNG(chartOut, bufferedImage);
        byte[] chartBytes = chartOut.toByteArray();

        ImageData imageData = ImageDataFactory.create(chartBytes);
        return new Image(imageData);
    }
}
