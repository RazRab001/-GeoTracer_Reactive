package com.example.ReactiveGeoTracer.utils.dto.route;

import com.example.ReactiveGeoTracer.src.models.RouteCoordinate;
import lombok.*;
import org.hibernate.validator.constraints.Range;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class RouteCoordinateDto {
    @Range(min = -90, max = 90, message = "Latitude must be between -90 and 90")
    private double latitude;

    @Range(min = -180, max = 180, message = "Longitude must be between -180 and 180")
    private double longitude;

    public RouteCoordinateDto(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public RouteCoordinate toCoordinate(GeometryFactory geometryFactory, Long routeId) {
        // Создаем Point из широты и долготы
        Point point = geometryFactory.createPoint(new Coordinate(longitude, latitude));
        point.setSRID(4326);

        // Создаем объект Coordinate и устанавливаем geom
        RouteCoordinate routeCoordinate = new RouteCoordinate();
        routeCoordinate.setGeom(point); // Устанавливаем геометрию
        routeCoordinate.setRouteId(routeId);

        return routeCoordinate;
    }
}
