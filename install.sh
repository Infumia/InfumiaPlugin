#!/bin/bash
cd lib
mvn install:install-file -Dfile="FabledSkyblock.jar" -DgroupId=fabledskyblock -DartifactId=fabledskyblock -Dversion=fabledskyblock -Dpackaging=jar -DgeneratePom=true
cd ..