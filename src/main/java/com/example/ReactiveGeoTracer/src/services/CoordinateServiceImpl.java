package com.example.ReactiveGeoTracer.src.services;

import com.example.ReactiveGeoTracer.src.models.RouteCoordinate;
import com.example.ReactiveGeoTracer.src.repositories.CoordinateRepository;
import com.example.ReactiveGeoTracer.src.services.interfaces.ICoordinateService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

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

    @Override
    public Mono<Void> saveCoordinates(List<RouteCoordinate> routeCoordinates) {
        return Flux.fromIterable(routeCoordinates)
                .flatMap(coordinateRepository::save)
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
