package com.orders.vantinh.controllers.Abstract;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableView;

public abstract class AbstractTableController<T> {
    protected TableView<T> tableViewAbs;

    // protected abstract void initTable();

    protected void setReponsiveColumnWidth(){
        tableViewAbs.widthProperty().addListener((observable, oldValue, newValue) -> {
            double totalWidth = newValue.doubleValue();
            int columnCount = tableViewAbs.getColumns().size();
            if (columnCount > 0) {
                double columnWidth = totalWidth / columnCount;

                for( var column : tableViewAbs.getColumns()){
                    column.setPrefWidth(columnWidth);
                }
            }
        });

    }

    protected void loadView(String fxmlFile){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    T addNewRow(T item){
        tableViewAbs.getItems().add(item);
        return item;
    }

    T updateRow(T item){
        ObservableList<T> items = tableViewAbs.getItems();
        for (int i = 0; i < items.size(); i++) {
            if(items.get(i).equals(item)){
                items.set(i, item);
                tableViewAbs.refresh();
                return item;
            }

        }
       return item;
    }

    void removeRow(T item){
        tableViewAbs.getItems().remove(item);
    }
}
