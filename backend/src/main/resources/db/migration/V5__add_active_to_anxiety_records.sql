--V5__add_active_to_anxiety_records.sql
-- Sincronizamos la tabla de registros con la nueva BaseEntity por el campo active que esta en una superclase
ALTER TABLE anxiety_records ADD COLUMN active BOOLEAN DEFAULT TRUE;