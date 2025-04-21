DROP TABLE IF EXISTS routes;

-- Создание таблицы routes
CREATE TABLE routes (
                        id BIGSERIAL PRIMARY KEY,                     -- Первичный ключ с автоинкрементом
                        title VARCHAR(255) NOT NULL,                  -- Название маршрута (обязательное поле)
                        description VARCHAR(1000),                    -- Описание маршрута (необязательное поле)
                        owner_id UUID NOT NULL,                       -- Идентификатор владельца (внешний ключ)
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Дата создания маршрута (по умолчанию текущая дата)
                        CONSTRAINT fk_owner FOREIGN KEY (owner_id) REFERENCES users(id)
);

-- Создание индекса для поля owner_id
CREATE INDEX idx_route_owner_id ON routes (owner_id);