#!/usr/bin/env bash
set -euo pipefail

IMAGE_NAME="${IMAGE_NAME:-rasec777/pokemon-api-client}"
IMAGE_TAG="${IMAGE_TAG:-1.0.1}"
IMAGE_PLATFORMS="${IMAGE_PLATFORMS:-linux/amd64,linux/arm64}"

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
REPO_ROOT="$(cd "${SCRIPT_DIR}/.." && pwd)"

cd "${REPO_ROOT}"

docker buildx build \
  --platform "${IMAGE_PLATFORMS}" \
  -t "${IMAGE_NAME}:${IMAGE_TAG}" \
  --push \
  .

echo "Imagen publicada: ${IMAGE_NAME}:${IMAGE_TAG} (${IMAGE_PLATFORMS})"
