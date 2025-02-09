#!/bin/bash

# Создание базы данных, если она не существует
psql -U boomzin -p 25432 -d postgres <<EOF
SELECT 'CREATE DATABASE subscription_system OWNER boomzin'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'subscription_system');
\gexec
EOF

# Создание роли replicator, если она не существует
psql -U boomzin -p 25432 -d postgres <<EOF
DO
\$do\$
BEGIN
   IF NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname = 'replicator') THEN
      CREATE ROLE replicator WITH REPLICATION LOGIN PASSWORD 'replicator';
   END IF;
END
\$do\$
EOF