package com.example.ReactiveGeoTracer.src.controllers;

import com.example.ReactiveGeoTracer.src.models.Route;
import com.example.ReactiveGeoTracer.src.models.RouteCoordinate;
import com.example.ReactiveGeoTracer.src.repositories.RouteRepository;
import com.example.ReactiveGeoTracer.src.services.interfaces.ICoordinateService;
import com.example.ReactiveGeoTracer.src.services.interfaces.IRouteService;
import com.example.ReactiveGeoTracer.utils.dto.route.RouteCreateDto;
import com.example.ReactiveGeoTracer.utils.dto.route.RouteGetDto;
import jakarta.validation.Valid;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/route")
public class RouteController {

    private final IRouteService routeService;
    private final ICoordinateService coordinateService;
    private final GeometryFactory geometryFactory;

    public RouteController(IRouteService routeService, ICoordinateService coordinateService, GeometryFactory geometryFactory) {
        this.routeService = routeService;
        this.coordinateService = coordinateService;
        this.geometryFactory = geometryFactory;
    }

    @PostMapping("/{userId}")
    public Mono<ResponseEntity<RouteGetDto>> createRoute(
            @PathVariable UUID userId,
            @Valid @RequestBody RouteCreateDto routeDto) {
        long startTime = System.currentTimeMillis();

        return Mono.just(routeDto.toRoute(userId))
                .doOnNext(savedUser -> {
                    long dbSaveTime = System.currentTimeMillis();
                    System.out.println("Step 1 (Convert dto to route): " + (dbSaveTime - startTime) + " ms");
                })
            .flatMap(routeService::addRoute)
                .doOnNext(savedUser -> {
                    long dbSaveTime = System.currentTimeMillis();
                    System.out.println("Step 2 (Database route save): " + (dbSaveTime - startTime) + " ms");
                })
                .flatMap(route -> {
                    return Flux.fromIterable(routeDto.getCoordinates())
                            .map(coordinate -> coordinate.toCoordinate(geometryFactory, route.getId()))
                            .doOnNext(savedUser -> {
                                long dbSaveTime = System.currentTimeMillis();
                                System.out.println("Step 3 (Convert coordinates dto): " + (dbSaveTime - startTime) + " ms");
                            })
                            .collectList()
                            .doOnNext(savedUser -> {
                                long dbSaveTime = System.currentTimeMillis();
                                System.out.println("Step 4 (Mono to List): " + (dbSaveTime - startTime) + " ms");
                            })
                            .flatMap(coordinates -> coordinateService.saveCoordinates(coordinates)
                                    .doOnNext(savedUser -> {
                                        long dbSaveTime = System.currentTimeMillis();
                                        System.out.println("Step 5 (Database coordinates save): " + (dbSaveTime - startTime) + " ms");
                                    })
                                .thenReturn(new RouteGetDto(route, routeDto.getCoordinates())));
                })
                .doOnNext(savedUser -> {
                    long dbSaveTime = System.currentTimeMillis();
                    System.out.println("Step 6 (Database coordinates save ending): " + (dbSaveTime - startTime) + " ms");
                })
            .map(ResponseEntity::ok)
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
