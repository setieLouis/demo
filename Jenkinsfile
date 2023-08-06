def BUILD_VERSION

pipeline {
    agent any
    environment {
        ENVIRONMENT = "${GIT_BRANCH.replaceAll("origin/", "")}"
        IMAGE_NAME = "annotations-api-service"
        ECR_ENDPOINT = "837848150749.dkr.ecr.eu-west-1.amazonaws.com"
    }

    options {
        skipStagesAfterUnstable()
    }

    stages {

        stage('Clone repository') {
            steps {
                script {
                    checkout scm
                    echo 'Cloned version $version'
                }
            }
        }

        stage('Build') {
            steps {
                script {
                    BUILD_VERSION = sh(
                            script: /cat version.sbt | cut -d '"' -f2/,
                            returnStdout: true
                    ).trim()
                    echo "Compiling ${BUILD_VERSION}..."
                    sh "/home/ubuntu/sbt/bin/sbt docker:publishLocal"
                }
            }
        }

        stage('Test') {
            steps {
                echo "No test configured"
            }
        }

        stage('Push') {
            steps {
                script {

                    CURRENT_IMAGE_VERSION = env.ECR_ENDPOINT + "/" + env.IMAGE_NAME + "-" + env.ENVIRONMENT + ":${BUILD_VERSION}"
                     echo "current image version"
                    echo CURRENT_IMAGE_VERSION

                    LATEST_IMAGE_VERSION = env.ECR_ENDPOINT + "/" + env.IMAGE_NAME + "-" + env.ENVIRONMENT + ":latest"

                    IMAGE_NAME_VERSION = env.IMAGE_NAME + ":${BUILD_VERSION}"

                    echo "image version"
                    echo IMAGE_NAME_VERSION

                    sh("aws ecr get-login-password --region eu-west-1 \
                    | docker login \
                        --username AWS \
                        --password-stdin ${ECR_ENDPOINT}")

                    echo "Tagging versions ${IMAGE_NAME}-${ENVIRONMENT}..."
                    sh("docker tag ${IMAGE_NAME_VERSION} ${CURRENT_IMAGE_VERSION}")
                    sh("docker tag ${IMAGE_NAME_VERSION} ${LATEST_IMAGE_VERSION}")

                    echo "Pushing versions ${BUILD_VERSION} and latest..."
                    sh("docker push ${CURRENT_IMAGE_VERSION}")
                    sh("docker push ${LATEST_IMAGE_VERSION}")
                }
            }
        }

        stage('Deploy') {
            steps {

                sh("sed 's/latest/${BUILD_VERSION}/' ./kubernetes/${ENVIRONMENT}/deployment.yaml > ./kubernetes/${ENVIRONMENT}/tmp_deployement.yaml")
                sh("cat ./kubernetes/${ENVIRONMENT}/tmp_deployement.yaml")


                sh("kubectl config use-context ml_engine_service_account@next-bme-engine-${ENVIRONMENT}.eu-west-1.eksctl.io")
                sh("kubectl apply -f kubernetes/${ENVIRONMENT}/service.yaml")
                sh("kubectl apply -f kubernetes/${ENVIRONMENT}/tmp_deployement.yaml")
                echo "Deploying ${IMAGE_NAME}"

                echo "delete tmp_deployement"
                sh("rm kubernetes/${ENVIRONMENT}/tmp_deployement.yaml")
            }
        }

    }
}