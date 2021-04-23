#!/bin/bash
cd lib
mvn install:install-file -Dfile="FabledSkyblock.jar" -DgroupId=xxx -DartifactId=xxx -Dversion=xxx -Dpackaging=jar -DgeneratePom=true
cd ..