# modoru: create
**modoru: create** is a modpack for the additional **modoru** server, built around the [Create](https://modrinth.com/mod/create) and **Create Aeronautics** mods.
The modpack introduces a progression system and additional balancing to provide a more structured experience with these mods.

<p align="left">
<a href="https://ds.modoru.fun"><img alt="Discord" height="56" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/social/discord-plural_vector.svg"></a>
<a href="https://modrinth.com/modpack/modoru-create"><img alt="Modrinth" height="56" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/available/modrinth_vector.svg"></a>
</p>

## Repository Structure
The repository is split into two main components:

### `modpack`
The modpack itself, managed and built with [packwiz](https://github.com/packwiz/packwiz).

### `mod`
The main NeoForge mod containing the custom functionality for the modpack.

## Building
The modpack can be built using one of the included scripts:

**Linux / macOS**
```bash
./build.sh
```
**Windows**

```bat
./build.bat
```

The resulting `.mrpack` file will be placed in the `build/` directory.

## Development Releases
Development builds of the modpack are distributed through [GitHub Pages](https://docs.github.com/en/pages) and [packwiz-installer](https://github.com/packwiz/packwiz-installer).

> [!WARNING]
> Development builds are not intended for regular use. **Please do not report issues encountered while using a development build.**

### Installation

Development builds currently support **MultiMC and its forks only**.

1. Download the [development modpack](https://raw.githubusercontent.com/modoruru/create-modpack/refs/heads/dev/static/modoru%20create%20%28dev%20branch%29.zip).
2. Import the downloaded archive into MultiMC.
3. Launch the instance.

The modpack will automatically update to the latest version before each launch.
