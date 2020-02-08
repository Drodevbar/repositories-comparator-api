#!/bin/bash

SYMBOL_FAILURE='\xE2\x9D\x8C'
SYMBOL_SHIP_IT='\xF0\x9F\x9A\x80'

DOCKER_IMAGE=drodevbar/repositories-comparator-api
DOCKER_CONTAINER_NAME=repositories-comparator-api

PORT=${1:-9090}

if ! command -v docker > /dev/null 2>&1; then
    echo -e "${SYMBOL_FAILURE} Docker not found. Exiting..."
    exit 1
fi

if ! docker info > /dev/null 2>&1; then
    echo -e "${SYMBOL_FAILURE} Docker daemon is not running. Exiting..."
    exit 1;
fi

if [[ $(docker images -q ${DOCKER_IMAGE}:latest) == "" ]]; then
    echo "Building docker image '${DOCKER_IMAGE}' with tag 'latest'..."
    docker image build -t ${DOCKER_IMAGE} .
else
    echo 'Latest docker image already built. Skipping...'
fi

if [[ $(docker container inspect ${DOCKER_CONTAINER_NAME} 2>/dev/null) == "[]" ]]; then
   echo 'Starting detached container...'
   docker container run -p ${PORT}:9090 -d --name ${DOCKER_CONTAINER_NAME} ${DOCKER_IMAGE}
   echo -e "${SYMBOL_SHIP_IT} Success! Visit localhost:${PORT}"
else
   echo 'Container is already running. Skipping...'
fi
