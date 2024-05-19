package pdf.generator;

import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import pdf.generator.model.Product;
import pdf.generator.model.Tasks;
import pdf.generator.model.User;
import pdf.generator.model.Warehouse;

import java.io.ByteArrayInputStream;
import java.util.List;


public class PdfGeneratorService {

    public ByteArrayInputStream generateUserReport(List<User> users) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (PdfWriter writer = new PdfWriter(out)) {
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            document.add(new Paragraph("User Report"));

            Table table = new Table(6);
            table.addHeaderCell("Username");
            table.addHeaderCell("Name");
            table.addHeaderCell("Lastname");
            table.addHeaderCell("PESEL");
            table.addHeaderCell("Email");
            table.addHeaderCell("Phone Number");

            for (User user : users) {
                table.addCell(user.getUsername());
                table.addCell(user.getName());
                table.addCell(user.getLastname());
                table.addCell(user.getPesel());
                table.addCell(user.getEmail());
                table.addCell(user.getPhoneNumber());
            }

            document.add(table);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ByteArrayInputStream(out.toByteArray());
    }
    public ByteArrayInputStream generateWarehouseReport(List<Warehouse> warehouses){
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try (PdfWriter writer = new PdfWriter(out)) {
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            document.add(new Paragraph("User Report"));

            Table table = new Table(6);
            table.addHeaderCell("building");
            table.addHeaderCell("zone");
            table.addHeaderCell("spaceId");
            table.addHeaderCell("spaceHeight");
            table.addHeaderCell("spaceWidth");
            table.addHeaderCell("spaceLength");
            table.addHeaderCell("productId");

            for (Warehouse warehouse : warehouses) {
                table.addCell(warehouse.getBuilding());
                table.addCell(warehouse.getZone());
                table.addCell(String.valueOf(warehouse.getSpaceId()));
                table.addCell(String.valueOf(warehouse.getSpaceHeight()));
                table.addCell(String.valueOf(warehouse.getSpaceLength()));
                table.addCell(String.valueOf(warehouse.getProductId()));
            }

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

            document.add(new Paragraph("User Report"));

            Table table = new Table(6);
            table.addHeaderCell("name");
            table.addHeaderCell("code");
            table.addHeaderCell("width");
            table.addHeaderCell("height");
            table.addHeaderCell("length");
            table.addHeaderCell("weight");

            for (Product product : products) {
                table.addCell(product.getName());
                table.addCell(product.getCode());
                table.addCell(String.valueOf(product.getWeight()));
                table.addCell(String.valueOf(product.getHeight()));
                table.addCell(String.valueOf(product.getLength()));
                table.addCell(String.valueOf(product.getWidth()));
            }

            document.add(table);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ByteArrayInputStream(out.toByteArray());
    }
    public ByteArrayInputStream generateTaskReport(Tasks task) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (PdfWriter writer = new PdfWriter(out)) {
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            document.add(new Paragraph("Task Report"));

            Table table = new Table(8);
            table.addHeaderCell("Task ID");
            table.addHeaderCell("Name");
            table.addHeaderCell("Description");
            table.addHeaderCell("State");
            table.addHeaderCell("Priority");
            table.addHeaderCell("Creation Date");
            table.addHeaderCell("Start Date");
            table.addHeaderCell("End Date");

            table.addCell(String.valueOf(task.getId()));
            table.addCell(task.getName());
            table.addCell(task.getDescription());
            table.addCell(String.valueOf(task.getState()));
            table.addCell(String.valueOf(task.getPriority()));
            table.addCell(task.getCreationDate().toString());
            table.addCell(task.getStartDate().toString());
            table.addCell(task.getEndDate().toString());

            document.add(table);

            document.add(new Paragraph("Users:"));
            Table userTable = new Table(6);
            userTable.addHeaderCell("Username");
            userTable.addHeaderCell("Name");
            userTable.addHeaderCell("Lastname");
            userTable.addHeaderCell("PESEL");
            userTable.addHeaderCell("Email");
            userTable.addHeaderCell("Phone Number");

            for (User user : task.getUsers()) {
                userTable.addCell(user.getUsername());
                userTable.addCell(user.getName());
                userTable.addCell(user.getLastname());
                userTable.addCell(user.getPesel());
                userTable.addCell(user.getEmail());
                userTable.addCell(user.getPhoneNumber());
            }

            document.add(userTable);

            document.add(new Paragraph("Products:"));
            Table productTable = new Table(6);
            productTable.addHeaderCell("Name");
            productTable.addHeaderCell("Code");
            productTable.addHeaderCell("Width");
            productTable.addHeaderCell("Height");
            productTable.addHeaderCell("Length");
            productTable.addHeaderCell("Weight");

            for (Product product : task.getProducts()) {
                productTable.addCell(product.getName());
                productTable.addCell(product.getCode());
                productTable.addCell(String.valueOf(product.getWidth()));
                productTable.addCell(String.valueOf(product.getHeight()));
                productTable.addCell(String.valueOf(product.getLength()));
                productTable.addCell(String.valueOf(product.getWeight()));
            }

            document.add(productTable);

            document.add(new Paragraph("Warehouses:"));
            Table warehouseTable = new Table(7);
            warehouseTable.addHeaderCell("Building");
            warehouseTable.addHeaderCell("Zone");
            warehouseTable.addHeaderCell("SpaceId");
            warehouseTable.addHeaderCell("SpaceHeight");
            warehouseTable.addHeaderCell("SpaceWidth");
            warehouseTable.addHeaderCell("SpaceLength");
            warehouseTable.addHeaderCell("ProductId");

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

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ByteArrayInputStream(out.toByteArray());
    }
    public ByteArrayInputStream generateTaskReport(List<Tasks> tasks) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (PdfWriter writer = new PdfWriter(out)) {
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            document.add(new Paragraph("Task Report"));

            for (Tasks task : tasks) {
                addTaskToDocument(task, document);
            }

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ByteArrayInputStream(out.toByteArray());
    }
    private void addTaskToDocument(Tasks task, Document document) {
        Table table = new Table(8);
        table.addHeaderCell("Task ID");
        table.addHeaderCell("Name");
        table.addHeaderCell("Description");
        table.addHeaderCell("State");
        table.addHeaderCell("Priority");
        table.addHeaderCell("Creation Date");
        table.addHeaderCell("Start Date");
        table.addHeaderCell("End Date");

        table.addCell(String.valueOf(task.getId()));
        table.addCell(task.getName());
        table.addCell(task.getDescription());
        table.addCell(String.valueOf(task.getState()));
        table.addCell(String.valueOf(task.getPriority()));
        table.addCell(task.getCreationDate().toString());
        table.addCell(task.getStartDate().toString());
        table.addCell(task.getEndDate().toString());

        document.add(table);

        document.add(new Paragraph("Users:"));
        Table userTable = new Table(6);
        userTable.addHeaderCell("Username");
        userTable.addHeaderCell("Name");
        userTable.addHeaderCell("Lastname");
        userTable.addHeaderCell("PESEL");
        userTable.addHeaderCell("Email");
        userTable.addHeaderCell("Phone Number");

        for (User user : task.getUsers()) {
            userTable.addCell(user.getUsername());
            userTable.addCell(user.getName());
            userTable.addCell(user.getLastname());
            userTable.addCell(user.getPesel());
            userTable.addCell(user.getEmail());
            userTable.addCell(user.getPhoneNumber());
        }

        document.add(userTable);

        document.add(new Paragraph("Products:"));
        Table productTable = new Table(6);
        productTable.addHeaderCell("Name");
        productTable.addHeaderCell("Code");
        productTable.addHeaderCell("Width");
        productTable.addHeaderCell("Height");
        productTable.addHeaderCell("Length");
        productTable.addHeaderCell("Weight");

        for (Product product : task.getProducts()) {
            productTable.addCell(product.getName());
            productTable.addCell(product.getCode());
            productTable.addCell(String.valueOf(product.getWidth()));
            productTable.addCell(String.valueOf(product.getHeight()));
            productTable.addCell(String.valueOf(product.getLength()));
            productTable.addCell(String.valueOf(product.getWeight()));
        }

        document.add(productTable);

        document.add(new Paragraph("Warehouses:"));
        Table warehouseTable = new Table(7);
        warehouseTable.addHeaderCell("Building");
        warehouseTable.addHeaderCell("Zone");
        warehouseTable.addHeaderCell("SpaceId");
        warehouseTable.addHeaderCell("SpaceHeight");
        warehouseTable.addHeaderCell("SpaceWidth");
        warehouseTable.addHeaderCell("SpaceLength");
        warehouseTable.addHeaderCell("ProductId");

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
}
