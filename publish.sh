#!/usr/bin/env bash
set -euo pipefail

BUILD_DIR="build/modpack/$GITHUB_REF_NAME"
MOD_DIR="mod"
MODPACK_DIR="modpack"
PACKWIZ="packwiz"

if ! command -v "$PACKWIZ" >/dev/null 2>&1; then
    echo "ERROR: '$PACKWIZ' is not installed or not in PATH" >&2
    exit 1
fi

echo "==> Cleaning ${BUILD_DIR}/"
rm -rf "${BUILD_DIR}"
mkdir -p "${BUILD_DIR}"

echo "==> Running Gradle build"
if ! (cd "${MOD_DIR}" && ./gradlew build); then
    echo "ERROR: Gradle build failed" >&2
    exit 1
fi

echo "==> Copying built jar to ${BUILD_DIR}/mods/"
mkdir -p ${BUILD_DIR}/mods
shopt -s nullglob
jars=("${MOD_DIR}"/build/libs/*.jar)
if [ ${#jars[@]} -eq 0 ]; then
    echo "ERROR: No jar found in ${MOD_DIR}/build/libs/" >&2
    exit 1
fi
for jar in "${jars[@]}"; do
    case "$jar" in
        *-sources.jar|*-javadoc.jar) continue ;;
    esac
    cp "$jar" "${BUILD_DIR}/mods/"
done

echo "==> Copying ${MODPACK_DIR}/ contents to ${BUILD_DIR}/"
cp -r "${MODPACK_DIR}"/. "${BUILD_DIR}/"

echo "==> Creating modpack"
cd $BUILD_DIR && $PACKWIZ refresh

echo "==> Done"