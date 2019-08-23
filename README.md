# CI detect plugin

[![Build Status](https://travis-ci.com/vierbergenlars/ci-detect-gradle-plugin.svg?branch=master)](https://travis-ci.com/vierbergenlars/ci-detect-gradle-plugin)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=vierbergenlars_ci-detect-gradle-plugin&metric=alert_status)](https://sonarcloud.io/dashboard?id=vierbergenlars_ci-detect-gradle-plugin)
[![Gradle Plugin Portal](https://img.shields.io/maven-metadata/v/https/plugins.gradle.org/m2/be/vbgn/ci-detect/be.vbgn.ci-detect.gradle.plugin/maven-metadata.xml.svg?colorB=007ec6&label=be.vbgn.ci-detect)](https://plugins.gradle.org/plugin/be.vbgn.ci-detect)

A gradle plugin that checks if it is running on a continuous integration server and gives you access to the build metadata.

## Installation

```gradle
plugins {
    // Check https://plugins.gradle.org/plugin/be.vbgn.ci-detect for the latest version
    id "be.vbgn.ci-detect" version "0.1.0"
}
```

## Usage

This plugin gives you easy and consistent access to environment variables that CI servers automatically add, independent of which CI solution you are using.

CI build metadata is exposed through the `ci` variable in your `build.gradle`. It can also be used programmatically from other Gradle plugins or code.

```gradle
// Basic information
ci.isCi() // => true or false, depending on if you are running in a CI server or not
ci.reference // The current branch or tag that is being built

// Branch builds
ci.branch // The current branch that is being built

// Pull requests
ci.isPullRequest() // => true/false
ci.pullRequest // Pull request identifier (usually a string containing a number, depends on your SCM system)
ci.pullRequestTargetBranch // Branch where the pullrequest will be merged into

// Tags
ci.isTag() //  => true/false, depending on if this is a git tag that is being built or not
ci.tag // The tag name that is being built
```

Supported CI servers:

 * [Travis CI](https://travis-ci.org)
 * [Jenkins](https://jenkins.io)
 * [GitLab CI](https://about.gitlab.com/product/continuous-integration/)

### Programmatic usage

```java
import be.vbgn.gradle.cidetect.CiInformation;

CiInformation ci = CiInformation.detect(); // Attempts to detect CI from environment variables
CiInformation ci = CIInformation.detect(project); // Gives CI detectors access to the gradle Project variable, which may be used by detectors to extract additional information
```

# Extending

You can provide extra CI detectors by creating a provider extending [`be.vbgn.gradle.cidetect.provider.CiInformationProvider`](./src/main/java/be/vbgn/gradle/cidetect/provider/CiInformationProvider.java),
[registering it as a service](https://docs.oracle.com/javase/7/docs/api/java/util/ServiceLoader.html) and placing your jar on the classpath.

For examples of providers, you can have a look at [the builtin providers](./src/main/java/be/vbgn/gradle/cidetect/impl).

# Development

## Creating a release

Every git tag is automatically published to the gradle plugins repository by Travis-CI.

This plugin follows SemVer and tags are managed with Reckon.

To create a release from a commit, use `./gradlew reckonTagPush -Preckon.scope=patch -Preckon.stage=final` to create a new patch release.

Tests are required to pass before a new release can be tagged.
