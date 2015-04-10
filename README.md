Gradle 1 plugin for Node
=======================

You probably shoudln't use this. It was forked from [gradle-node-plugin](https://github.com/srs/gradle-node-plugin) at version 0.4 for use in a Gradle 1 project, and customized slightly

This plugin enables you to run any NodeJS script as part of your build. It does not depend on NodeJS (or NPM) being installed on
your system. Fist time it will download NodeJS distribution and unpack it into your local .gradle directory and run it from there.

Installing the plugin
---------------------

Releases of this plugin are hosted at BinTray (http://bintray.com) and is part of jcentral repository.
Setup the plugin like this:

	buildscript {
		repositories {
			jcenter()
		}
    	dependencies {
			classpath 'zerovox.gradle:gradle-1-node-plugin:0.1'
    	}
	}

Include the plugin in your build.gradle file like this:

    apply plugin: 'node'

Running a NodeJS script
-----------------------

To use this plugin you have to define some tasks in your build.gradle file. If you have a NodeJS script in src/scripts/my.js, then you
can execute this by defining the following Gradle task:

    task myScript(type: NodeTask) {
        script = file('src/scripts/my.js')
    }

You can also add arguments, like this:

    task myScript(type: NodeTask) {
        script = file('src/scripts/my.js')
        args = ['arg1', 'arg2']
    }

`NodeTask` is a wrapper around the core `Exec` task. You can set the `ignoreExitValue` property on it:

    task myScript(type: NodeTask) {
       script = file('src/scripts/my.js')
       ignoreExitValue = true
    }

You can also customize all other values on the `ExecSpec` by passing a closure to `execOverrides`. It's executed last, possibly
overriding already set parameters.

    task myScript(type: NodeTask) {
       script = file('src/scripts/my.js')
       execOverrides {
           it.ignoreExitValue = true
           it.workingDir = 'somewhere'
           it.standardOutput = new FileOutputStream('logs/my.log')
       }
    }

When executing this for the first time, it will run a nodeSetup task that downloades NodeJS (for your platform) and
NPM (Node Package Manager) if on windows (other platforms include it into the distribution).

Executing NPM tasks
-------------------

When adding the node plugin, you will have a npmInstall task already added. This task will execute "npm install" and
installs all dependencies in "package.json". Execute it like this:

    $ grunt npmInstall

If you want to run other NPM commands like installing named modules outside package.json, you can add a custom task like this:

    task installExpress(type: NpmTask) {
        args = ['install', 'express', '--save-dev']
    }

Configuring the plugin
----------------------

You can configure the plugin using the "node" extension block, like this:

    node {
        // Version of node to use.
        version = '0.10.22'

        // Base URL for fetching node distributions (change if you have a mirror).
        distBaseUrl = 'http://nodejs.org/dist'

        // If true, it will download node using above parameters.
        // If false, it will try to use global installed node.
        download = true

        // Set the work directory for unpacking node
        workDir = file("${project.buildDir}/nodejs")
    }

Building the Plugin
-------------------

To build the plugin, just type the following commmand:

    ./gradlew clean build
