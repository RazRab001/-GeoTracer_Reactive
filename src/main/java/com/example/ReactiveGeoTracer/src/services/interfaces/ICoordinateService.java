package com.example.ReactiveGeoTracer.src.services.interfaces;

import com.example.ReactiveGeoTracer.src.models.RouteCoordinate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ICoordinateService {
    Flux<Long> getRouteIdsAroundCoordinates(double latitude, double longitude, double distance);
    Mono<Void> saveCoordinates(List<RouteCoordinate> routeCoordinates);
    Flux<Long> getCoordinateIdsOfRoute(Long routeId);
    Flux<RouteCoordinate> getCoordinatesByRouteIds(List<Long> routeIds);
    Mono<Void> deleteCoordinatesByRouteId(Long routeId);
}
