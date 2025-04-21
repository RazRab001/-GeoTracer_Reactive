DROP TABLE IF EXISTS coordinates;

CREATE TABLE coordinates(
    id BIGSERIAL PRIMARY KEY,
    geom GEOMETRY(Point, 4326),
    route_id BIGINT NOT NULL,
    CONSTRAINT fk_route FOREIGN KEY (route_id) REFERENCES routes(id)
);

CREATE INDEX idx_coordinate_route_id ON coordinates (route_id);

DROP SEQUENCE IF EXISTS coordinate_seq;
CREATE SEQUENCE coordinate_seq
    START WITH 1
    INCREMENT BY 100;