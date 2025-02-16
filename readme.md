<h1 align="center">
  <a name="logo" href="https://github.com/ldtteam/Domum-Ornamentum"><img src="https://github.com/ldtteam/Domum-Ornamentum/raw/version/latest/logo.png" alt="Domum Ornamentum" width="200"></a>
  <br>
  Domum Ornamentum Source Code
</h1>
<h4 align="center">Be sure to :star: this repo so you can keep up to date on any progress!</h4>
<div align="center">
  <h4>
    <a href="https://buildsystem.ldtteam.com/buildConfiguration/LetSDevTogether_Domum-Ornamentum_Alpha_Release?branch=&mode=builds">
        <img alt="TeamCity Alpha Build Status" src="https://img.shields.io/teamcity/build/e/LetSDevTogether_Domum-Ornamentum_Alpha_Release?label=Alpha&logo=Alpha%20build&server=https%3A%2F%2Fbuildsystem.ldtteam.com&style=plasticr">
    </a>
    <a href="https://buildsystem.ldtteam.com/buildConfiguration/LetSDevTogether_Domum-Ornamentum_Beta_Release?branch=&mode=builds">
        <img alt="TeamCity Beta Build Status" src="https://img.shields.io/teamcity/build/e/LetSDevTogether_Domum-Ornamentum_Beta_Release?label=Beta&logo=Beta%20build&server=https%3A%2F%2Fbuildsystem.ldtteam.com&style=plasticr">
    </a>
    <a href="https://buildsystem.ldtteam.com/buildConfiguration/LetSDevTogether_Domum-Ornamentum_Release_Release?branch=&mode=builds">
        <img alt="TeamCity Release Build Status" src="https://img.shields.io/teamcity/build/e/LetSDevTogether_Domum-Ornamentum_Release_Release?label=Release&logo=Release%20build&server=https%3A%2F%2Fbuildsystem.ldtteam.com&style=plasticr">
    </a>
    <a href="https://github.com/ldtteam/Domum-Ornamentum/stargazers">
        <img src="https://img.shields.io/github/stars/ldtteam/Domum-Ornamentum.svg?style=plasticr"/>
    </a>
    <a href="https://github.com/ldtteam/Domum-Ornamentum/commits/master">
        <img src="https://img.shields.io/github/last-commit/ldtteam/Domum-Ornamentum.svg?style=plasticr"/>
    </a>
    <a href="https://github.com/ldtteam/Domum-Ornamentum/commits/master">
        <img src="https://img.shields.io/github/commit-activity/m/ldtteam/Domum-Ornamentum.svg?style=plasticr"/>
    </a>
  </h4>
</div>
<hr />
<div align="center"><a name="menu"></a>
  <h4>
    <a href="https://discord.gg/Tb3PagMpaG">
      Discord
    </a>
    <span> | </span>
    <a href="https://www.curseforge.com/minecraft/mc-mods/Domum-Ornamentum">
      CurseForge
    </a>
    <span> | </span>
    <a href="https://www.curseforge.com/minecraft/mc-mods/Domum-Ornamentum/files">
      Releases
    </a>
    <span> | </span>
    <a href="https://buildsystem.ldtteam.com/project/LetSDevTogether_Domum-Ornamentum?branch=&mode=builds">
      BuildSystem
    </a>
    <span> | </span>
    <a href="https://github.com/ldtteam/Domum-Ornamentum/">
      Code
    </a>
    <span> | </span>
    <a href="https://github.com/ldtteam/Domum-Ornamentum/issues">
      Issues
    </a>
    <span> | </span>
    <a href="https://github.com/ldtteam/Domum-Ornamentum/pulls">
      Pull Requests
    </a>
    <span> | </span>
    <a href="https://www.patreon.com/Minecolonies">
      Patreon
    </a>
    <span> | </span>
    <a href="https://www.paypal.com/cgi-bin/webscr?return=https://www.curseforge.com/projects/449945&cn=Add+special+instructions+to+the+addon+author()&business=paypal%40ldtteam.com&bn=PP-DonationsBF:btn_donateCC_LG.gif:NonHosted&cancel_return=https://www.curseforge.com/projects/449945&lc=US&item_name=Domum-Ornamentum+(from+GitHub.com)&cmd=_donations&rm=1&no_shipping=1&currency_code=USD">
      Paypal
    </a>
  </h4>
</div>
<hr />

### <a name="BaseImplementation"></a>Base implementation:
This is a minecraft mod that provides skinnable blocks to be used in buildings inside the game minecraft.
The core of this mod is a reskinning supporting model loader that gets the skin information passed from a none ticking block entity.

### <a name="Gameplay"></a>Gameplay:
Craft an architects cutter and place the materials in as requested.
Then select the type of skinned block in the middle, and pick up the result on the right.

In general terms the architects cutter is just a fancy stone cutter, nothing more, nothing less.
### <a name="VanillaCompatibility"></a>Vanilla compatibility:
By default, the mod creates a wide array of different compatibility tags that can be used to allow DO to skin its blocks with others.
The tags listed [here](https://github.com/ldtteam/Domum-Ornamentum/tree/version/latest/src/datagen/generated/domum_ornamentum/data/domum_ornamentum/tags/blocks) provide an overview of the tags DO uses to 
enable the compatibility, any modder can add his or her blocks to these to enable compatibility.

##### <a name="VanillaCompatibilityState"></a>Vanilla compatibility (State):
State of the application:
The current state of this mod is **RELEASE**.
We have done thorough testing, however as with all mods, bugs can not be prevented so please report any of them. 

#### <a name="ModCompatibility"></a>Mod compatibility:
By default, a modder will need to add his blocks to the required material tags (see above), however most of these already extend from other tags, as such, it generally suffices to add
the planks or logs to the vanilla tag for DO to pick them up.

#### <a name="Installation"></a>Installation:
The way to install Domum Ornamentum differs if you are a player or a modder:
#### <a name="InstallationPlayer"></a>Installation of Domum Ornamentum as a player:
To install Domum Ornamentum as a player you need to perform the following steps:
1) Download the correct version from [CurseForge](https://www.curseforge.com/minecraft/mc-mods/Domum-Ornamentum).
2) Create a Forge based profile in your launcher of choice.
3) Drop the Domum Ornamentum jar into the mods folder of your profile.
4) Enjoy!
5) Report any bugs you found.

#### <a name="InstallationModder"></a>Installation of Domum Ornamentum as a Modder:
To install Domum Ornamentum as a modder you need to perform the following steps:
1) Add the LDTTeam Maven repository to your project:
```groovy
repositories {
    maven {
        name 'LDTTeam - Modding'
        url 'https://ldtteam.jfrog.io/ldtteam/modding/'
    }
}
```
2) Determine which version of Domum Ornamentum you want to depend on using [CurseForge](https://www.curseforge.com/minecraft/mc-mods/Domum Ornamentum).
3) Add the Domum Ornamentum API-jar as a Compile-time and the Domum Ornamentum Main-jar as a Run-time dependency:
```groovy
dependencies {
    compileOnly fg.deobf("com.ldtteam:domum_ornamentum:${project.exactMinecraftVersion}-${project.DomumOrnamentumVersion}:api")
    runtimeOnly fg.deobf("com.ldtteam:domum_ornamentum:${project.exactMinecraftVersion}-${project.DomumOrnamentumVersion}:universal")
}
```

If you're planning on using data generation, you'll also need to include another dependency:

```groovy
    implementation fg.deobf("com.ldtteam:datagenerators:1.19.3-${project.DataGeneratorsVersion}:universal") {
        transitive = false
    }
```

You can check the latest version of `datagenerators` [here](https://ldtteam.jfrog.io/ui/native/modding/com/ldtteam/datagenerators/).

#### <a name="SupportedBy"></a>Proudly supported by:
<h1 align="center">
  <a name="logo" href="https://bisecthosting.com/ldtteam"><img src="https://media.discordapp.net/attachments/697517732219846766/727581811151995071/MinecoloniesLogo2Final.png" alt="BiSect Hosting LDTTeam link" width="300"></a>
</h1>
