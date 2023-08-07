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

        stage('ciao'){
            steps{
                 script {
                            sh "ls ."
                            def version_value = sh(returnStdout: true, script: "cat ./build.gradle | grep -e 'version ='").trim()
                            def version = version_value.split(/=/)[1]
                            sh "echo \"questa è la version ${version}\""
                            def value = sh(returnStdout: true, script: "echo $version | grep -o -E  \"[0-9]+.[0-9]+.[0-9]+\"")
                            sh "echo \"questa è la version ${value}\""

                            def list = value.split(/\./)

                            def last = list[2] as int
                            def tag = "${list[0]}.${list[1]}.${last + 1}"
                            sh "echo \"questo è il $tag\""
                            sh "sed -i 's/version = [^,]*/version = '\${tag}\'/g' ./build.gradle"
                            sh "git commit -am 'increment version'"
                            sh "git tag -a ${tag} -m \"tag $tag was created by jenkins\""
                 }


            }

        }

         stage('sendTag') {
            steps {
                withCredentials([string(credentialsId: '97d324fb-39c4-4f69-bf85-1c13ac2baafe', variable: 'TOKEN')]){
                    sh 'git push --all  https://$TOKEN@github.com/setieLouis/demo.git origin api --tags'
                }

            }
        }
    }

}
