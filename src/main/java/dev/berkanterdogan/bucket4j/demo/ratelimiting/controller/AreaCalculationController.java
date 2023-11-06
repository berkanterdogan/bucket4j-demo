package dev.berkanterdogan.bucket4j.demo.ratelimiting.controller;

import dev.berkanterdogan.bucket4j.demo.ratelimiting.controller.dto.AreaResponse;
import dev.berkanterdogan.bucket4j.demo.ratelimiting.controller.dto.RectangleRequest;
import dev.berkanterdogan.bucket4j.demo.ratelimiting.controller.dto.SquareRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/area-calculation")
class AreaCalculationController {

    @PostMapping(value = "/rectangle")
    public ResponseEntity<AreaResponse> rectangle(@RequestBody RectangleRequest request) {
        double length = request.getLength();
        double width = request.getWidth();
        double area = length * width;
        AreaResponse response = new AreaResponse("rectangle", area);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/square")
    public ResponseEntity<AreaResponse> rectangle(@RequestBody SquareRequest request) {
        double length = request.getLength();
        double area = length * length;
        AreaResponse response = new AreaResponse("rectangle", area);
        return ResponseEntity.ok(response);
    }
}
