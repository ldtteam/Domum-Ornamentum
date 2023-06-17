import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.projectFeatures.githubIssues

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2023.05"

project {
    description = "All the blocks for all the Materials"

    params {
        password("env.crowdinKey", "credentialsJSON:444bd785-791b-42ae-9fae-10ee93a2fbd3")
        select("Current Minecraft Version", "latest", label = "Current Minecraft Version",
                options = listOf("1.12", "1.13", "1.14", "1.15", "1.16", "1.17"))
        text("Repository", "ldtteam/Domum-Ornamentum", label = "Repository", description = "The repository for Domum Ornamentum.", readOnly = true, allowEmpty = true)
        param("env.Version.Minor", "0")
        param("env.Version.Patch", "0")
        param("Upsource.Project.Id", "domum-ornamentum")
        param("env.Version.Suffix", "")
        param("env.Version.Major", "1")
        text("env.Version", "%env.Version.Major%.%env.Version.Minor%.%env.Version.Patch%%env.Version.Suffix%", label = "Version", description = "The version of the project.", display = ParameterDisplay.HIDDEN, allowEmpty = true)
    }

    features {
        githubIssues {
            id = "PROJECT_EXT_35"
            displayName = "ldtteam/domum-ornamentum"
            repositoryURL = "https://github.com/ldtteam/Domum-Ornamentum"
            authType = accessToken {
                accessToken = "credentialsJSON:47381468-aceb-4992-93c9-1ccd4d7aa67f"
            }
        }
    }
    subProjectsOrder = arrayListOf(RelativeId("Release"), RelativeId("UpgradeBetaRelease"), RelativeId("UpgradeAlphaBeta"), RelativeId("Alpha"), RelativeId("OfficialPublications"), RelativeId("Branches"), RelativeId("PullRequests2"))

    subProject(UpgradeBetaRelease)
    subProject(Alpha)
    subProject(PullRequests2)
    subProject(Branches)
    subProject(OfficialPublications)
    subProject(Release)
    subProject(UpgradeAlphaBeta)
}


object Alpha : Project({
    name = "Alpha"
    description = "Alpha version builds of domum ornamentum"

    buildType(Alpha_Release)

    params {
        param("Default.Branch", "version/%Current Minecraft Version%")
        param("VCS.Branches", "+:refs/heads/version/(*)")
        param("env.CURSERELEASETYPE", "alpha")
        param("env.Version.Suffix", "-ALPHA")
    }
})

object Alpha_Release : BuildType({
    templates(AbsoluteId("LetSDevTogether_BuildWithRelease"))
    name = "Release"
    description = "Releases the mod as Alpha to CurseForge"

    params {
        param("Project.Type", "mods")
        param("env.Version.Patch", "${OfficialPublications_CommonB.depParamRefs.buildNumber}")
    }

    vcs {
        branchFilter = "+:*"
    }

    dependencies {
        snapshot(OfficialPublications_CommonB) {
            reuseBuilds = ReuseBuilds.NO
            onDependencyFailure = FailureAction.FAIL_TO_START
        }
    }
})


object Branches : Project({
    name = "Branches"
    description = "All none release branches."

    buildType(Branches_Common)
    buildType(Branches_Build)

    params {
        text("Default.Branch", "CI/Default", label = "Default branch", description = "The default branch for branch builds", readOnly = true, allowEmpty = true)
        param("VCS.Branches", """
            +:refs/heads/(*)
            -:refs/heads/version/*
            -:refs/heads/testing/*
            -:refs/heads/release/*
            -:refs/pull/*/head
            -:refs/heads/CI/*
        """.trimIndent())
        param("env.Version.Suffix", "-PERSONAL")
    }

    cleanup {
        baseRule {
            all(days = 60)
        }
    }
})

object Branches_Build : BuildType({
    templates(AbsoluteId("LetSDevTogether_Build"))
    name = "Build"
    description = "Builds the branch without testing."

    params {
        param("Project.Type", "mods")
        param("env.Version.Patch", "${Branches_Common.depParamRefs.buildNumber}")
    }

    dependencies {
        snapshot(Branches_Common) {
            reuseBuilds = ReuseBuilds.NO
            onDependencyFailure = FailureAction.FAIL_TO_START
        }
    }
})

object Branches_Common : BuildType({
    templates(AbsoluteId("LetSDevTogether_CommonBuildCounter"))
    name = "Common Build Counter"
    description = "Tracks the amount of builds run for branches"
})


object OfficialPublications : Project({
    name = "Official Publications"
    description = "Holds projects and builds related to official publications"

    buildType(OfficialPublications_CommonB)
})

object OfficialPublications_CommonB : BuildType({
    templates(AbsoluteId("LetSDevTogether_CommonBuildCounter"))
    name = "Common Build Counter"
    description = "Represents the version counter within Domum Ornamentum for official releases."
})


object PullRequests2 : Project({
    name = "Pull Requests"
    description = "All open pull requests"

    buildType(PullRequests2_BuildAndTest)
    buildType(PullRequests2_CommonBuildCounter)

    params {
        text("Default.Branch", "CI/Default", label = "Default branch", description = "The default branch for pull requests.", readOnly = true, allowEmpty = false)
        param("VCS.Branches", """
            -:refs/heads/*
            +:refs/pull/(*)/head
            -:refs/heads/(CI/*)
        """.trimIndent())
        text("env.Version", "%env.Version.Major%.%env.Version.Minor%.%build.counter%-PR", label = "Version", description = "The version of the project.", display = ParameterDisplay.HIDDEN, allowEmpty = true)
    }

    cleanup {
        baseRule {
            all(days = 60)
        }
    }
})

object PullRequests2_BuildAndTest : BuildType({
    templates(AbsoluteId("LetSDevTogether_BuildWithTesting"))
    name = "Build and Test"
    description = "Builds and Tests the pull request."

    params {
        param("Project.Type", "mods")
        param("env.Version.Patch", "${PullRequests2_CommonBuildCounter.depParamRefs.buildNumber}")
        param("env.Version.Suffix", "-PR")
    }

    dependencies {
        snapshot(PullRequests2_CommonBuildCounter) {
            reuseBuilds = ReuseBuilds.NO
            onDependencyFailure = FailureAction.FAIL_TO_START
        }
    }
    
    disableSettings("BUILD_EXT_15")
})

object PullRequests2_CommonBuildCounter : BuildType({
    templates(AbsoluteId("LetSDevTogether_CommonBuildCounter"))
    name = "Common Build Counter"
    description = "Defines version numbers uniquely over all Pull Request builds"
})


object Release : Project({
    name = "Release"
    description = "Beta version builds of domum ornamentum"

    buildType(Release_Release)

    params {
        param("Default.Branch", "release/%Current Minecraft Version%")
        param("VCS.Branches", "+:refs/heads/release/(*)")
        param("env.CURSERELEASETYPE", "release")
        param("env.Version.Suffix", "-RELEASE")
    }
})

object Release_Release : BuildType({
    templates(AbsoluteId("LetSDevTogether_BuildWithRelease"))
    name = "Release"
    description = "Releases the mod as Alpha to CurseForge"

    params {
        param("Project.Type", "mods")
        param("env.Version.Patch", "${OfficialPublications_CommonB.depParamRefs.buildNumber}")
    }

    dependencies {
        snapshot(OfficialPublications_CommonB) {
            reuseBuilds = ReuseBuilds.NO
            onDependencyFailure = FailureAction.FAIL_TO_START
        }
    }
})


object UpgradeAlphaBeta : Project({
    name = "Upgrade - Alpha -> Beta"
    description = "Updates the current alpha to beta."

    buildType(UpgradeAlphaBeta_UpgradeAlphaBeta)

    params {
        select("Current Minecraft Version", "1.16.3", label = "Current Minecraft Version",
                options = listOf("1.12", "1.13", "1.14", "1.15", "1.16", "1.17"))
    }
})

object UpgradeAlphaBeta_UpgradeAlphaBeta : BuildType({
    templates(AbsoluteId("LetSDevTogether_Upgrade"))
    name = "Upgrade - Alpha -> Beta"
    description = "Upgrades the current Alpha to Beta."

    params {
        text("Source.Branch", "version", label = "Source branch type", description = "The source branch type for the upgrade. EG: version or testing", allowEmpty = false)
        text("Default.Branch", "testing/%Current Minecraft Version%", label = "Default branch", description = "The default branch of this build.", allowEmpty = true)
        param("VCS.Branches", "+:refs/heads/testing/(*)")
        text("Target.Branch", "testing", label = "Target branch type", description = "The target branch type for the upgrade. EG: testing or release.", allowEmpty = false)
        text("env.Version", "%env.Version.Major%.%env.Version.Minor%.%build.counter%-BETA", label = "Version", description = "The version of the project.", display = ParameterDisplay.HIDDEN, allowEmpty = true)
    }
    
    disableSettings("BUILD_EXT_9")
})


object UpgradeBetaRelease : Project({
    name = "Upgrade Beta -> Release"
    description = "Upgrades the current Beta to Release"

    buildType(UpgradeBetaRelease_UpgradeBetaRelease)
})

object UpgradeBetaRelease_UpgradeBetaRelease : BuildType({
    templates(AbsoluteId("LetSDevTogether_Upgrade"))
    name = "Upgrade Beta -> Release"
    description = "Upgrades the current Beta to Release."

    params {
        text("Source.Branch", "testing", label = "Source branch type", description = "The source branch type for the upgrade. EG: version or testing", allowEmpty = false)
        text("Default.Branch", "release/%Current Minecraft Version%", label = "Default branch", description = "The default branch of this build.", allowEmpty = true)
        param("VCS.Branches", "+:refs/heads/release/(*)")
        text("Target.Branch", "release", label = "Target branch type", description = "The target branch type for the upgrade. EG: testing or release.", allowEmpty = false)
        text("env.Version", "%env.Version.Major%.%env.Version.Minor%.%build.counter%-RELEASE", label = "Version", description = "The version of the project.", display = ParameterDisplay.HIDDEN, allowEmpty = true)
    }
})
