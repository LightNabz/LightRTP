# FeatherTP (Feather Teleport)

FeatherTP is a Minecraft plugin that allows players to randomly teleport to safe locations within the Minecraft world. This plugin is lightweight and easy to use, making it ideal for servers that want to add random teleportation features without impacting server performance.

## Features

- Random teleportation to safe locations
- Configurable teleportation range and center position
- Cooldown settings between teleportations
- Support for multiple worlds (world_nether, world_the_end, etc.)
- Reload command to reload the configuration

## Installation

1. Download the `.jar` file from [Releases](https://github.com/Lightnabz/FeatherTP/releases).
2. Place the `.jar` file into the `plugins` folder on your Minecraft server.
3. Restart your Minecraft server.

## Usage

To use FeatherTP, follow these steps:

1. **Random Teleport Command**  
    Players can use the `/rtp` command to randomly teleport to a safe location. Additionally, the plugin supports aliases such as `/tpr` and `/ltp`.  
    Example:  
    ```
    /rtp
    /tpr
    /ltp
    ```

2. **Admin Commands**  
    - Reload the plugin configuration:  
      ```
      /feathertp reload
      ```
      This command reloads the `config.yml` file without restarting the server.

3. **Permissions**  
    - `feathertp.use`: Allows players to use the `/rtp`, `/tpr`, and `/ltp` commands.  
    - `feathertp.admin`: Grants access to admin commands like `/feathertp reload`.

Make sure to configure permissions in your server's permission management plugin (e.g., LuckPerms) to control access to these commands.

## Configuration

This plugin uses a `config.yml` file for configuration. Below is an example configuration structure:

```yaml
permissions:
    require-use-permission: true  # If true, only players with the `feathertp.use` permission can use /rtp

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
## Building/Compiling

To build the FeatherTP plugin from source, follow these steps:

1. **Clone the Repository**  
    Clone the FeatherTP repository to your local machine:
    ```bash
    git clone https://github.com/Lightnabz/FeatherTP.git
    cd FeatherTP
    ```

2. **Ensure Prerequisites**  
    Make sure you have the following installed:
    - [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/javase-downloads.html) (version 21 or higher)
    - [Maven](https://maven.apache.org/)

3. **Build the Plugin**  
    Use Maven to compile the plugin and package it into a `.jar` file:
    ```bash
    mvn clean package
    ```

4. **Locate the Output**  
    After the build process completes, the `.jar` file will be located in the `target` directory.

You can now use the generated `.jar` file as described in the [Installation](#installation) section.
