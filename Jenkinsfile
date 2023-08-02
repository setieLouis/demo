pipeline {
    agent any
    tools{
        gradle "7.6.2"
    }

    stages {
        stage('Hello') {
            steps {
                sh "gradle --version"
                sh "gradle build"
                echo 'Hello World'
            }
        }
    }
}
