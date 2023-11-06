package dev.berkanterdogan.bucket4j.demo.ratelimiting.controller.dto;


public class AreaResponse {

    private String shape;
    private Double area;

    public AreaResponse(String shape, Double area) {
        this.area = area;
        this.shape = shape;
    }

    public Double getArea() {
        return area;
    }

    public String getShape() {
        return shape;
    }
}