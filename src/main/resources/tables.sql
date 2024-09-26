CREATE TABLE IF NOT EXISTS registros_entity (
    id TEXT PRIMARY KEY,
    localidad TEXT NOT NULL,
    provincia TEXT NOT NULL,
    tempMax REAL NOT NULL,
    horaTempMax TEXT NOT NULL,
    tempMin REAL NOT NULL,
    horaTempMin TEXT NOT NULL,
    precipitacion REAL NOT NULL,
)