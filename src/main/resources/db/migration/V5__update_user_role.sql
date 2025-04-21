-- Удаление старого ограничения (если оно существует)
ALTER TABLE users DROP CONSTRAINT IF EXISTS users_role_check;

-- Добавление нового ограничения
ALTER TABLE users ADD CONSTRAINT users_role_check CHECK (role IN ('USER', 'ADMIN'));