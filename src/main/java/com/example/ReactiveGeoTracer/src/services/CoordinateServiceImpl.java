package com.example.ReactiveGeoTracer.src.services;

import com.example.ReactiveGeoTracer.src.models.RouteCoordinate;
import com.example.ReactiveGeoTracer.src.repositories.CoordinateRepository;
import com.example.ReactiveGeoTracer.src.services.interfaces.ICoordinateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CoordinateServiceImpl implements ICoordinateService {

    private final CoordinateRepository coordinateRepository;

    public CoordinateServiceImpl(CoordinateRepository coordinateRepository) {
        this.coordinateRepository = coordinateRepository;
    }

    @Override
    public Flux<Long> getRouteIdsAroundCoordinates(double latitude, double longitude, double distance) {
        return coordinateRepository.getCoordinateRouteIdsWithinDistance(latitude, longitude, distance);
    }

    @Autowired
    private R2dbcEntityTemplate r2dbcEntityTemplate;

    @Override
    public Mono<Void> saveCoordinates(List<RouteCoordinate> routeCoordinates) {
        if (routeCoordinates.isEmpty()) {
            return Mono.empty();
        }

        // Формируем SQL-запрос для массовой вставки
        String sql = "INSERT INTO coordinates (geom, route_id) VALUES " +
                routeCoordinates.stream()
                        .map(coord -> "('" + coord.getGeom().toString() + "', " + coord.getRouteId() + ")")
                        .collect(Collectors.joining(", "));

        // Выполняем запрос через R2dbcEntityTemplate
        return r2dbcEntityTemplate.getDatabaseClient()
                .sql(sql)
                .fetch()
                .rowsUpdated()
                .then();
    }

    @Override
    public Flux<Long> getCoordinateIdsOfRoute(Long routeId) {
        return coordinateRepository.getCoordinateIdsOfRoute(routeId);
    }

    @Override
    public Flux<RouteCoordinate> getCoordinatesByRouteIds(List<Long> routeIds) {
        return coordinateRepository.getCoordinatesByRouteIds(routeIds);
    }

    @Override
    public Mono<Void> deleteCoordinatesByRouteId(Long routeId) {
        return coordinateRepository.deleteCoordinatesByRouteId(routeId);
    }
}
