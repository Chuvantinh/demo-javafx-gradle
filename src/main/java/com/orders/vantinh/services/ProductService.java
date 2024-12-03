package com.orders.vantinh.services;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.orders.vantinh.dao.DBConnection;
import com.orders.vantinh.models.ModelProducts;
import com.orders.vantinh.models.ModelUnit;
import javafx.stage.FileChooser;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.util.ArrayList;
import java.util.List;

public class ProductService {
    MongoCollection<Document> productCollection = DBConnection.getCollection("javafx-order-management", "products");

    public ModelProducts getProductbyID(MongoCollection mongoCollection, ObjectId ID){
        Document doc = (Document) mongoCollection.find(new Document("_id", ID)).first();

        if( doc != null){
            List<Document> unitList = doc.getList("units", Document.class);

            String SKU = doc.getString("SKU");
            Integer WPID = doc.getInteger("WPID");
            String productName = doc.getString("productName");
            String productNameVN = doc.getString("productNameVN");
            String productDescription = doc.getString("productDescription");
            String productShortDescription = doc.getString("productShortDescription");
            String productImageUrl = doc.getString("productImageUrl");
            double productStock = Double.parseDouble(doc.getString("productStock"));
            int productTax = doc.getInteger("productTax");
            String productBrand = doc.getString("productBrand");

            List<ModelUnit> modelUnitList = convertToModelUnits(unitList);

            return new ModelProducts( SKU, WPID, productName, productNameVN,
                    productDescription, productShortDescription, productImageUrl, productStock,productTax, productBrand, modelUnitList);
        }else {
            return null;
        }
    }

    public static List<ModelUnit> convertToModelUnits(List<Document> unitsList){
        List<ModelUnit> modelUnitList = new ArrayList<>();
        for(Document unit : unitsList){
            String unitType = unit.getString("unitType");
            String unitDescription = unit.getString("unitDescription");
            Double quantityInBaseUnit  = unit.get("quantityInBaseUnit") instanceof Double ? unit.getDouble("quantityInBaseUnit") : unit.getInteger("quantityInBaseUnit").doubleValue();
            Double unitRegularPrice = unit.get("unitRegularPrice") instanceof Double ? unit.getDouble("unitRegularPrice") : unit.getInteger("unitRegularPrice").doubleValue();
            Double unitSalePrice = unit.get("unitSalePrice") instanceof Double ? unit.getDouble("unitSalePrice") : unit.getInteger("unitSalePrice").doubleValue();
            Double unitBuyPrice = unit.get("unitBuyPrice") instanceof Double ? unit.getDouble("unitBuyPrice") : unit.getInteger("unitBuyPrice").doubleValue();
            modelUnitList.add(new ModelUnit(unitType, unitDescription, quantityInBaseUnit, unitRegularPrice, unitSalePrice, unitBuyPrice));
        }

        return modelUnitList;
    }

    public void importExcelData(File file){
        try{
            FileInputStream fileInputStream = new FileInputStream(file);
            Workbook workbook = WorkbookFactory.create(fileInputStream);
            List<Document> productList = new ArrayList<>();
            Sheet sheet = workbook.getSheetAt(6);

            System.out.println("Opened sheet: " + sheet); // Debug: Confirm file is opened
            int rowCount = sheet.getPhysicalNumberOfRows();

            for( int i = 0; i < rowCount; i++){
                Row row = sheet.getRow(i);
                if (row.getRowNum() == 0) continue;

                String SKU = (row.getCell(4) != null && row.getCell(4).getCellType() == CellType.NUMERIC)
                        ? String.valueOf(row.getCell(4).getNumericCellValue())  // Handle numeric cell
                        : (row.getCell(4) != null ? row.getCell(4).getStringCellValue() : "");  // Handle string cell or null


                Integer WPID = row.getCell(0) != null ? (int)row.getCell(0).getNumericCellValue() : 0;
                String productName = row.getCell(1) != null ? row.getCell(1).getStringCellValue() : "";
                String productNameVN = row.getCell(2) != null ? row.getCell(2).getStringCellValue() : "";
                String productDescription = row.getCell(7).getStringCellValue();
                String productShortDescription = row.getCell(7).getStringCellValue();
                String _productImageUrl = "https://botiss.com/wp-content/uploads/2024/10/Webinar_Mota_teaser_home-3.jpg";
                String productImageUrl = downloadImage(_productImageUrl, "src/main/resources/images/products");

                double productStock = row.getCell(12) != null ? row.getCell(12).getNumericCellValue() : 0;

                int productTax = ( String.valueOf(row.getCell(19)).equals("reduzierter-preis"))  ? 7 : 19;
                String productBrand = row.getCell(13).getStringCellValue();

                if(!productName.isEmpty() && productName != null){
                    Document productDocument = new Document("SKU", SKU).
                            append("WPID", WPID)
                            .append("productName", productName)
                            .append("productNameVN", productNameVN)
                            .append("productDescription", productDescription)
                            .append("productShortDescription", productShortDescription)
                            .append("productImageUrl", productImageUrl)
                            .append("productStock", productStock)
                            .append("productTax", productTax)
                            .append("productBrand", productBrand)
                            .append("units", generateUnits(row));

                    productList.add(productDocument);
                }
            }

            if(productList.size() > 0){
                productCollection.insertMany(productList);
                System.out.println("Successfully inserted " + productList.size() + " products into MongoDB.");
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Document> generateUnits(Row row){
        // Create a list of unit objects for this product
        List<Document> units = new ArrayList<>();

        // Define the units for this product
        units.add(new Document("unitType", "piece")
                .append("unitDescription", "Individual piece")
                .append("quantityInBaseUnit", 1)
                .append("unitRegularPrice", (Double)2.99)
                .append("unitSalePrice", (Double)2.9)
                .append("unitBuyPrice", (Double)1.00));

        units.add(new Document("unitType", "box")
                .append("unitDescription", "Box of 10 pieces")
                .append("quantityInBaseUnit", (Double)10.00)
                .append("unitRegularPrice", (Double)29.99)
                .append("unitSalePrice", (Double)28.00)
                .append("unitBuyPrice", (Double)23.00));

        units.add(new Document("unitType", "carton")
                .append("unitDescription", "Carton of 100 pieces")
                .append("quantityInBaseUnit", (Double)100.00)
                .append("unitRegularPrice", (Double)299.99)
                .append("unitSalePrice", (Double)290.00)
                .append("unitBuyPrice", (Double)250.00));

        return units;
    }


    public String downloadImage(String imageUrl, String destinationFolder) throws IOException, InterruptedException {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(imageUrl))
                    .build();

            HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
            String imageName = "defaultPhoto.jpg";

            if (response.statusCode() == 200) {
                imageName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);

                // Check if destination folder exists, and create it if it doesn't
                Path folderPath = Paths.get(destinationFolder);
                if (!Files.exists(folderPath)) {
                    Files.createDirectories(folderPath);
                }

                Path detinatioPathFilename = folderPath.resolve(imageName);

                // Save image to the destination path
                Files.copy(response.body(), detinatioPathFilename, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Image downloaded successfully to: " + detinatioPathFilename);
            } else {
                System.err.println("Failed to download image, HTTP status: " + response.statusCode());
            }
        return imageName;
    }

    public void exportDataToExcel(List<ModelProducts> products){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Excel File");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel file", "*.xlsx"));

        File file= fileChooser.showSaveDialog(null);

        if(file != null){
            try(Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet();

                // Create header style
                CellStyle headerStyle = workbook.createCellStyle();
                headerStyle.setFillBackgroundColor(IndexedColors.LIGHT_BLUE.getIndex());
                headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                // Set font for the header (white text color, bold)
                Font headerFont = workbook.createFont();
                headerFont.setBold(true);
                headerFont.setColor(IndexedColors.WHITE.getIndex());  // Set font color to white
                headerStyle.setFont(headerFont);

                // Write row header
                Row headerRow = sheet.createRow(0);
                headerRow.createCell(0).setCellValue("SKU");
                headerRow.createCell(1).setCellValue("WP_ID");
                headerRow.createCell(2).setCellValue("productName");
                headerRow.createCell(3).setCellValue("productNameVN");
                headerRow.createCell(4).setCellValue("productDescription");
                headerRow.createCell(5).setCellValue("productShortDescription");
                headerRow.createCell(6).setCellValue("productImageUrl");
                headerRow.createCell(7).setCellValue("productStock");
                headerRow.createCell(8).setCellValue("productTax");
                headerRow.createCell(9).setCellValue("productBrand");

                headerRow.createCell(10).setCellValue("--");
                headerRow.createCell(11).setCellValue("1-unitType");
                headerRow.createCell(12).setCellValue("1-unitDescription");
                headerRow.createCell(13).setCellValue("1-quantityInBaseUnit");
                headerRow.createCell(14).setCellValue("1-unitRegularPrice");
                headerRow.createCell(15).setCellValue("1-unitSalePrice");
                headerRow.createCell(16).setCellValue("1-unitBuyPrice");

                headerRow.createCell(17).setCellValue("2-unitType");
                headerRow.createCell(18).setCellValue("2-unitDescription");
                headerRow.createCell(19).setCellValue("2-quantityInBaseUnit");
                headerRow.createCell(20).setCellValue("2-unitRegularPrice");
                headerRow.createCell(21).setCellValue("2-unitSalePrice");
                headerRow.createCell(22).setCellValue("2-unitBuyPrice");

                headerRow.createCell(23).setCellValue("3-unitType");
                headerRow.createCell(24).setCellValue("3-unitDescription");
                headerRow.createCell(25).setCellValue("3-quantityInBaseUnit");
                headerRow.createCell(26).setCellValue("3-unitRegularPrice");
                headerRow.createCell(27).setCellValue("3-unitSalePrice");
                headerRow.createCell(28).setCellValue("3-unitBuyPrice");

                // Set style for header
                headerRow.getCell(0).setCellStyle(headerStyle);
                headerRow.getCell(1).setCellStyle(headerStyle);
                headerRow.getCell(2).setCellStyle(headerStyle);
                headerRow.getCell(3).setCellStyle(headerStyle);
                headerRow.getCell(4).setCellStyle(headerStyle);
                headerRow.getCell(5).setCellStyle(headerStyle);
                headerRow.getCell(6).setCellStyle(headerStyle);
                headerRow.getCell(7).setCellStyle(headerStyle);

                // Write data rows
               if(!products.isEmpty()){
                   int index = 1;

                   for(ModelProducts product : products){
                       Row row = sheet.createRow(index);
                       row.createCell(0).setCellValue(product.getSKU());
                       row.createCell(1).setCellValue(product.getWP_ID());
                       row.createCell(2).setCellValue(product.getProductName());
                       row.createCell(3).setCellValue(product.getProductNameVN());
                       row.createCell(4).setCellValue(product.getProductDescription());
                       row.createCell(5).setCellValue(product.getProductShortDescription());
                       row.createCell(6).setCellValue(product.getProductImageUrl());
                       row.createCell(7).setCellValue(product.getProductStock());
                       row.createCell(8).setCellValue(product.getProductTax());
                       row.createCell(9).setCellValue(product.getProductBrand());
                       row.createCell(10).setCellValue("units");

                       List<ModelUnit> unitList = product.getUnits();
                       if(!unitList.isEmpty()){
                           int columnIndex = 11;
                           for(int j = 0; j < unitList.size(); j++){
                               ModelUnit unit = unitList.get(j);
                               row.createCell(columnIndex++).setCellValue(unit.getUnitType());
                               row.createCell(columnIndex++).setCellValue(unit.getUnitDescription());
                               row.createCell(columnIndex++).setCellValue(unit.getQuantityInBaseUnit());
                               row.createCell(columnIndex++).setCellValue(unit.getUnitRegularPrice());
                               row.createCell(columnIndex++).setCellValue(unit.getUnitSalePrice());
                               row.createCell(columnIndex++).setCellValue(unit.getUnitBuyPrice());
                           }
                       }

                       index ++;
                   }
               }

                // Adjust column width for better readability
                for (int i = 0; i < headerRow.getPhysicalNumberOfCells(); i++) {
                    sheet.autoSizeColumn(i);
                }

                // Write to file
                try(FileOutputStream fos = new FileOutputStream(file)) {
                    workbook.write(fos);
                    System.out.println("Data exported to Excel file successfully!");
                }catch (IOException e) {
                    e.printStackTrace();
                    System.err.println("Failed to export data to Excel file!");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public ModelProducts newProduct( ModelProducts product){
        Document productDoc = new Document()
                .append("SKU", product.getSKU())
                .append("WPID", product.getWP_ID())
                .append("productName", product.getProductName())
                .append("productNameVN", product.getProductNameVN())
                .append("productDescription", product.getProductDescription())
                .append("productShortDescription", product.getProductShortDescription())
                .append("productImageUrl", product.getProductImageUrl())
                .append("productStock", product.getProductStock())
                .append("productTax", product.getProductTax())
                .append("productBrand", product.getProductBrand())
                .append("units", product.getUnits())
                ;
        // Add units as an array of sub-documents
        List<Document> unitDocs = new ArrayList<>();
        for (ModelUnit unit : product.getUnits()) {
            Document unitDoc = new Document()
                    .append("unitType", unit.getUnitType())
                    .append("unitDescription", unit.getUnitDescription())
                    .append("quantityInBaseUnit", unit.getQuantityInBaseUnit())
                    .append("unitRegularPrice", unit.getUnitRegularPrice())
                    .append("unitSalePrice", unit.getUnitSalePrice())
                    .append("unitBuyPrice", unit.getUnitBuyPrice());
            unitDocs.add(unitDoc);
        }
        productDoc.append("units", unitDocs);

        try{
            productCollection.insertOne(productDoc);
            System.out.println("Product saved successfully");
        }catch ( MongoException e){
            System.out.println("Fail to save a product" + e);
        }

        return product;
    }
}