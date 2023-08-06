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
                                                        def version_value = sh(returnStdout: true, script: "cat build.gradle | grep -o 'version = [^,]*'").trim()
                                                        def version = version_value.split(/=/)[1]
                                                        def value = sh(returnStdout: true, script: "echo $version | grep -o \"0.0.[0-9]\"")
                                                        def list = value.split(/\./)
                                                        def last = list[2] as int
                                                        def tag = "${list[0]}.${list[1]}.${last + 1}"

                                                        sh "sed -i 's/version = [^,]*/${tag}/g' ciao.txt"
                                                        sh "git commit -m 'increment version'"
                                                        sh "git tag -a 0.0.16 -m \"tag $tag was created by jenkins\""
                 }


            }

        }

         stage('sendTag') {
            steps {
                withCredentials([string(credentialsId: '97d324fb-39c4-4f69-bf85-1c13ac2baafe', variable: 'TOKEN')]){
                    sh 'git push  https://$TOKEN@github.com/setieLouis/demo.git --tags'
                }

            }
        }
    }

}
