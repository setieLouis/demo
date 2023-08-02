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

                echo "tag"
            }
        }
    }

}
