pipeline {
    agent any
    tools{
        gradle "7.6.2"
    }

    stages {
        stage('build') {
            steps {
                sh "gradle --version"
                sh "gradle build"
            }
        }

        stage('test') {
           steps {
               echo "jacoco test coverage"
           }
        }

        stage('tag') {
            steps {

            version_value =  sh(returnStdout: true, script: "cat build.gradle | grep -o 'version = [^,]*'").trim()
            sh "echo Project in version value: $version_value"
            version = version_value.split(/=/)[1]
            sh "echo final version: $version"
                echo "create tag"
            }
        }
    }

}
