package com.example.ReactiveGeoTracer.src.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.locationtech.jts.geom.Point;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "coordinates")

public class RouteCoordinate {
    @Id
    private Long id;

    @Column("geom")
    private Point geom;

    @Column("route_id")
    private Long routeId;
}
