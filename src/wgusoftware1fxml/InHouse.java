/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wgusoftware1fxml;

import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author Admin
 */
public class InHouse extends Part{
    private SimpleIntegerProperty machineId = new SimpleIntegerProperty(0);
        
    InHouse (int id, String name, double price, int stock, int min, int max, int machineId) {
        System.out.println("InHouse Constructor Started...");
        setId(id);
        setName(name);
        setPrice(price);
        setStock(stock);
        setMin(min);
        setMax(max);
        setMachineId(machineId);
        System.out.println("InHouse Constructor Done!");
    }

    public void setMachineId(int machineId) {
        this.machineId.set(machineId);
    }

    public int getMachineId() {
        return this.machineId.get();
    }
}
