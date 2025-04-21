package com.example.ReactiveGeoTracer.utils.dto.route;

import com.example.ReactiveGeoTracer.src.models.Route;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Data
public class RouteCreateDto {
    @NotBlank(message = "Title cannot be blank")
    @Size(max = 255, message = "Title must not exceed 255 characters")
    private String title;

    @Nullable
    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;

    @NotNull(message = "Coordinates cannot be null")
    @Size(min = 2, message = "Coordinates must contain at least two elements")
    private List<RouteCoordinateDto> coordinates;

    public Route toRoute(UUID ownerId) {
        Route route = new Route();
        route.setTitle(title);
        route.setDescription(description);
        route.setOwnerId(ownerId);
        return route;
    }
}
