# LightRTP (Light Random Teleport)

LightRTP is a Minecraft plugin that allows players to randomly teleport to safe locations within the Minecraft world. This plugin is lightweight and easy to use, making it ideal for servers that want to add random teleportation features without impacting server performance.

## Features

- Random teleportation to safe locations
- Configurable teleportation range and center position
- Cooldown settings between teleportations
- Support for multiple worlds (world_nether, world_the_end, etc.)
- Reload command to reload the configuration

## Installation

1. Download the `.jar` file from [Releases](https://github.com/Lightnabz/LightRTP/releases).
2. Place the `.jar` file into the `plugins` folder on your Minecraft server.
3. Restart your Minecraft server.

## Configuration

This plugin uses a `config.yml` file for configuration. Below is an example configuration structure:

```yaml
world:
    teleport:
        random:
            minRange: 100        # Minimum range for teleportation
            maxRange: 1000       # Maximum range for teleportation
            centerX: 0           # X-coordinate of the teleportation center
            centerZ: 0           # Z-coordinate of the teleportation center
            maxTries: 10         # Number of attempts to find a safe location
            cooldownSeconds: 30  # Cooldown in seconds between teleportations
```
