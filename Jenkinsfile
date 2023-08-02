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
    }

   stages {
       stage('test') {
           steps {
               echo "jacoco test coverage"
           }
       }
   }

    stages {
          stage('tag') {
              steps {
                  echo "create tag"
              }
          }
      }

}
