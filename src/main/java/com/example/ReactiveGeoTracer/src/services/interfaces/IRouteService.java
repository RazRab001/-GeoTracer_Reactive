package com.example.ReactiveGeoTracer.src.services.interfaces;

import com.example.ReactiveGeoTracer.src.models.Route;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface IRouteService {
    Mono<Route> getRouteAsync(Long routeId);
    Mono<Route> addRoute(Route route);
    Mono<Route> updateRoute(Long id, Route route);
    Mono<Void> deleteRoute(Long routeId);
    Flux<Route> getUserRoutes(UUID userId);
    Flux<Route> getAllRoutes();
    Flux<Route> getRoutesByIds(List<Long> ids);
    Flux<Route> getRoutesCreatedAtDate(Date createdAt);
    Flux<Route> getRoutesCreatedAtDateBetween(Date startDate, Date endDate);
}
