package com.example.ReactiveGeoTracer.src.repositories;

import com.example.ReactiveGeoTracer.src.models.Route;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface RouteRepository extends R2dbcRepository<Route, Long> {
    Mono<Route> getRouteById(Long id);

    @Query("DELETE FROM routes r WHERE r.id = :id")
    Mono<Void> removeRouteById(Long id);

    Flux<Route> getRoutesByOwnerId(UUID userID);

    @Query("SELECT r FROM routes r WHERE DATE(r.created_at) = :date")
    Flux<Route> getRoutesByCreatedAt(@Param("date") Date createdAt);

    @Query("SELECT r FROM routes r WHERE DATE(r.created_at) BETWEEN :from AND :to")
    Flux<Route> getRoutesByCreatedAtBetween(@Param("from") Date from, @Param("to") Date to);

    @Query("SELECT r FROM routes r where r.id in :routeIds")
    Flux<Route> getRoutesByIds( @Param("routeIds") List<Long> ids);
}
