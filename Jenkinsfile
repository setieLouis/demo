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



                withCredentials([string(credentialsId: '97d324fb-39c4-4f69-bf85-1c13ac2baafe', variable: 'TOKEN')]){

                    //sh "git branch"
                    //sh "git log"
                    sh 'git push  https://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@github.com/setieLouis/demo.git --tags'
                }

            }
        }
    }

}
