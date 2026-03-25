#!/usr/bin/env bash
set -euo pipefail

IMAGE_NAME="${IMAGE_NAME:-rasec777/pokemon-api-client}"
IMAGE_TAG="${IMAGE_TAG:-1.0.1}"

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
REPO_ROOT="$(cd "${SCRIPT_DIR}/.." && pwd)"

cd "${REPO_ROOT}"

docker build -t "${IMAGE_NAME}:${IMAGE_TAG}" .

echo "Imagen creada: ${IMAGE_NAME}:${IMAGE_TAG}"
