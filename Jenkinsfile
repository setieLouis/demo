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
                                                        sh "echo Project in version value: $version_value"
                                                        def version = version_value.split(/=/)[1]
                                                        sh "echo final version: $version"

                                                        def value = sh(returnStdout: true, script: "echo $version | grep -o \"0.0.[0-9]\"")
                                                        sh  "echo ${value}"
                                                        def list = value.split(/\./)
                                                        def last = list[2] as int
                                                        sh "echo ${list[2]}"


                                                        sh "echo ${list[0]}.${list[1]}.${last + 1}"

                                                        def tag = "${list[0]}.${list[1]}.${last + 1}"

                                                        sh "git tag -a 0.0.9 -m \"tag $tag was created by jenkins\""
                 }






                //                     sh "git tag -a $tag -m \"tag ${tag} was creating by jenkins\""
                //
                //                     sh "git push origin --tags"
                //
                //                       sh "echo ciao mamma come va"
//                                }

            }

        }

         stage('maf') {
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
