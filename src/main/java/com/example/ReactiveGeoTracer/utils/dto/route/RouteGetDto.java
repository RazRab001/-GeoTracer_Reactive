package com.example.ReactiveGeoTracer.utils.dto.route;

import com.example.ReactiveGeoTracer.src.models.Route;
import com.example.ReactiveGeoTracer.src.models.RouteCoordinate;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@Data
@ToString
public class RouteGetDto {
    private Long id;
    private String title;
    private String description;
    private List<RouteCoordinateDto> coordinates;
    private Date createdAt;

    public RouteGetDto(Route route, List<RouteCoordinateDto> coordinates) {
        id = route.getId();
        title = route.getTitle();
        description = route.getDescription();
        this.coordinates = coordinates; //coordinates.stream()
                //.map(routeCoordinate ->
                //        new RouteCoordinateDto(routeCoordinate.getGeom().getY(), routeCoordinate.getGeom().getX())).toList();
        createdAt = route.getCreatedAt();
    }
}
