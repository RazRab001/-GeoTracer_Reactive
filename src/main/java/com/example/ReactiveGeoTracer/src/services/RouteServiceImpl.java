package com.example.ReactiveGeoTracer.src.services;

import com.example.ReactiveGeoTracer.src.models.Route;
import com.example.ReactiveGeoTracer.src.repositories.RouteRepository;
import com.example.ReactiveGeoTracer.src.services.interfaces.IRouteService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class RouteServiceImpl implements IRouteService {

    private final RouteRepository routeRepository;

    public RouteServiceImpl(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    @Override
    public Mono<Route> getRouteAsync(Long routeId) {
        return routeRepository.getRouteById(routeId);
    }

    @Override
    public Mono<Route> addRoute(Route route) {
        return routeRepository.save(route);
    }

    @Override
    public Mono<Route> updateRoute(Long id, Route route) {
        return routeRepository.findById(id)
                .flatMap(routeRepository::save);
    }

    @Override
    public Mono<Void> deleteRoute(Long routeId) {
        return routeRepository.deleteById(routeId);
    }

    @Override
    public Flux<Route> getUserRoutes(UUID userId) {
        return routeRepository.getRoutesByOwnerId(userId);
    }

    @Override
    public Flux<Route> getAllRoutes() {
        return routeRepository.findAll();
    }

    @Override
    public Flux<Route> getRoutesByIds(List<Long> ids) {
        return routeRepository.getRoutesByIds(ids);
    }

    @Override
    public Flux<Route> getRoutesCreatedAtDate(Date createdAt) {
        return routeRepository.getRoutesByCreatedAt(createdAt);
    }

    @Override
    public Flux<Route> getRoutesCreatedAtDateBetween(Date startDate, Date endDate) {
        return routeRepository.getRoutesByCreatedAtBetween(startDate, endDate);
    }
}
