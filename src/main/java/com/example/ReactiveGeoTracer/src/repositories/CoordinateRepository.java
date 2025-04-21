package com.example.ReactiveGeoTracer.src.repositories;

import com.example.ReactiveGeoTracer.src.models.RouteCoordinate;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public interface CoordinateRepository extends R2dbcRepository<RouteCoordinate, Long> {
    @Query(value = "SELECT c.route_id FROM coordinates c " +
            "WHERE ST_DWithin(c.geom, ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326), :distance)")
    Flux<Long> getCoordinateRouteIdsWithinDistance(
            @Param("latitude") double latitude,
            @Param("longitude") double longitude,
            @Param("distance") double distance);

    @Query("SELECT rc.id FROM coordinates rc WHERE rc.route_id = :id")
    Flux<Long> getCoordinateIdsOfRoute(@Param("id") long routeId);

    @Query("SELECT rc FROM coordinates rc WHERE rc.route_id = :id")
    Flux<Long> getCoordinatesOfRoute(@Param("id") long routeId);

    @Query("SELECT rc FROM coordinates rc WHERE rc.route_id IN :routeIds")
    Flux<RouteCoordinate> getCoordinatesByRouteIds(@Param("routeIds") List<Long> routeIds);

    @Query("DELETE FROM coordinates WHERE route_id = :id")
    Mono<Void> deleteCoordinatesByRouteId(Long id);
}
