<h1 align="center">
  <a name="logo" href="https://github.com/ldtteam/DomumOrnamentum"><img src="https://github.com/ldtteam/DomumOrnamentum/raw/version/latest/logo.png" alt="DomumOrnamentum" width="200"></a>
  <br>
  Domum Ornamentum Source Code
</h1>
<h4 align="center">Be sure to :star: this repo so you can keep up to date on any progress!</h4>
<div align="center">
  <h4>
    <a href="https://buildsystem.ldtteam.com/buildConfiguration/LetSDevTogether_DomumOrnamentum_Alpha_Release?branch=&mode=builds">
        <img alt="TeamCity Alpha Build Status" src="https://img.shields.io/teamcity/build/e/LetSDevTogether_DomumOrnamentum_Alpha_Release?label=Alpha&logo=Alpha%20build&server=https%3A%2F%2Fbuildsystem.ldtteam.com&style=plasticr">
    </a>
    <a href="https://buildsystem.ldtteam.com/buildConfiguration/LetSDevTogether_DomumOrnamentum_Beta_Release?branch=&mode=builds">
        <img alt="TeamCity Beta Build Status" src="https://img.shields.io/teamcity/build/e/LetSDevTogether_DomumOrnamentum_Beta_Release?label=Beta&logo=Beta%20build&server=https%3A%2F%2Fbuildsystem.ldtteam.com&style=plasticr">
    </a>
    <a href="https://buildsystem.ldtteam.com/buildConfiguration/LetSDevTogether_DomumOrnamentum_Release_Release?branch=&mode=builds">
        <img alt="TeamCity Release Build Status" src="https://img.shields.io/teamcity/build/e/LetSDevTogether_DomumOrnamentum_Release_Release?label=Release&logo=Release%20build&server=https%3A%2F%2Fbuildsystem.ldtteam.com&style=plasticr">
    </a>
    <a href="https://github.com/ldtteam/DomumOrnamentum/stargazers">
        <img src="https://img.shields.io/github/stars/ldtteam/DomumOrnamentum.svg?style=plasticr"/>
    </a>
    <a href="https://github.com/ldtteam/DomumOrnamentum/commits/master">
        <img src="https://img.shields.io/github/last-commit/ldtteam/DomumOrnamentum.svg?style=plasticr"/>
    </a>
    <a href="https://github.com/ldtteam/DomumOrnamentum/commits/master">
        <img src="https://img.shields.io/github/commit-activity/m/ldtteam/DomumOrnamentum.svg?style=plasticr"/>
    </a>
  </h4>
</div>
<hr />
<div align="center"><a name="menu"></a>
  <h4>
    <a href="https://discord.gg/C63JEm3aQt">
      Discord
    </a>
    <span> | </span>
    <a href="https://www.curseforge.com/minecraft/mc-mods/DomumOrnamentum">
      CurseForge
    </a>
    <span> | </span>
    <a href="https://www.curseforge.com/minecraft/mc-mods/DomumOrnamentum/files">
      Releases
    </a>
    <span> | </span>
    <a href="https://buildsystem.ldtteam.com/project/LetSDevTogether_DomumOrnamentum?branch=&mode=builds">
      BuildSystem
    </a>
    <span> | </span>
    <a href="https://github.com/ldtteam/DomumOrnamentum/">
      Code
    </a>
    <span> | </span>
    <a href="https://github.com/ldtteam/DomumOrnamentum/issues">
      Issues
    </a>
    <span> | </span>
    <a href="https://github.com/ldtteam/DomumOrnamentum/pulls">
      Pull Requests
    </a>
    <span> | </span>
    <a href="https://www.patreon.com/Minecolonies">
      Patreon
    </a>
    <span> | </span>
    <a href="https://www.paypal.com/cgi-bin/webscr?return=https://www.curseforge.com/projects/449945&cn=Add+special+instructions+to+the+addon+author()&business=paypal%40ldtteam.com&bn=PP-DonationsBF:btn_donateCC_LG.gif:NonHosted&cancel_return=https://www.curseforge.com/projects/449945&lc=US&item_name=DomumOrnamentum+(from+GitHub.com)&cmd=_donations&rm=1&no_shipping=1&currency_code=USD">
      Paypal
    </a>
  </h4>
</div>
<hr />

### <a name="BaseImplementation"></a>Base implementation:
This is a minecraft mod that provides skinnable blocks to be used in the

### <a name="VanillaCompatibility"></a>Vanilla compatibility:
By default, the mod replaces any vanilla mobs GroundPathNavigator.
In other words all entities that travel over or on the ground are currently supported.
However, as of now no flying or climbing mobs are supported.

Notable supported entities:
- Creeper
- Drowned
- Enderman
- Evoker
- Illusioner
- Iron Golem
- Mooshroom
- Skeleton
- Stray
- Vex
- Villager
- Vindicator
- Wandering Trader
- Witch
- Wither Skeleton
- Zombie
- Zombie Villager

See the following file for more details: [Compatible Vanilla Entity List](https://github.com/ldtteam/DomumOrnamentum/blob/version/latest/src/datagen/generated/per-viam-invenire/wiki/per-viam-invenire/tags/entity_types/replace_vanilla_navigator.md).

##### <a name="VanillaCompatibilityState"></a>Vanilla compatibility (State):
State of the application:
The current state of this mod is **ALPHA**.
This is important, since even though we do our best to maintain the best compatibility and prevent bugs from appearing,
we do not have the time or stamina to test every single feature/bugged-feature that minecraft holds when it comes to pathfinding.
If you do find a bug or something that behaves differently from the vanilla minecraft please create an issue on the bug tracker [Here](https://github.com/ldtteam/DomumOrnamentum/issues).

#### <a name="ModCompatibility"></a>Mod compatibility:
By default, any mob that uses a default GroundPathNavigator can be easily added, by adding it to the data pack tag: `per-viam-invenire:replace_vanilla_navigator`.
This will cause DomumOrnamentum to replace the navigator that is used by the mobs in that list and use best guess values when needed.

If a modder wants to support DomumOrnamentum directly then they can depend directly on the DomumOrnamentum API to make this a reality.

#### <a name="Installation"></a>Installation:
The way to install DomumOrnamentum differs if you are a player or a modder:
#### <a name="InstallationPlayer"></a>Installation of DomumOrnamentum as a player:
To install DomumOrnamentum as a player you need to perform the following steps:
1) Download the correct version from [CurseForge](https://www.curseforge.com/minecraft/mc-mods/DomumOrnamentum).
2) Create a Forge based profile in your launcher of choice.
3) Drop the DomumOrnamentum jar into the mods folder of your profile.
4) Enjoy!
5) Report any bugs you found.

#### <a name="InstallationModder"></a>Installation of DomumOrnamentum as a Modder:
To install DomumOrnamentum as a modder you need to perform the following steps:
1) Add the LDTTeam Maven repository to your project:
```groovy
repositories {
    maven {
        name 'LDTTeam - Modding'
        url 'https://ldtteam.jfrog.io/ldtteam/modding/'
    }
}
```
2) Determine which version of DomumOrnamentum you want to depend on using [CurseForge](https://www.curseforge.com/minecraft/mc-mods/DomumOrnamentum).
3) Add the DomumOrnamentum API-jar as a Compile-time and the DomumOrnamentum Main-jar as a Run-time dependency:
```groovy
dependencies {
    compileOnly fg.deobf("com.ldtteam:DomumOrnamentum:${project.exactMinecraftVersion}-${project.DomumOrnamentumVersion}:api")
    runtimeOnly fg.deobf("com.ldtteam:DomumOrnamentum:${project.exactMinecraftVersion}-${project.DomumOrnamentumVersion}:universal")
}
```

#### <a name="SupportedBy"></a>Proudly supported by:
<h1 align="center">
  <a name="logo" href="https://bisecthosting.com/ldtteam"><img src="https://media.discordapp.net/attachments/697517732219846766/727581811151995071/MinecoloniesLogo2Final.png" alt="BiSect Hosting LDTTeam link" width="300"></a>
</h1>