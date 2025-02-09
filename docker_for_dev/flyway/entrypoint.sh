#!/bin/bash

set -e

/flyway/flyway -placeholderPrefix='#[' -placeholderSuffix=']' -user=boomzin -password=boomzin -url=jdbc:postgresql://haproxy:5432/subscription_system -schemas=subscription_system -locations=filesystem:/flyway/sql/subscription_system migrate