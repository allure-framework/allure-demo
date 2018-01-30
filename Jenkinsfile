pipeline {
    agent { label 'java' }
    parameters {
        string(name: 'ALLURE_VERSION', defaultValue: '2.5.0', description: 'Allure report version')
    }
    stages {
        stage("Build") {
            steps {
                sh "./gradlew buildDockerImage -PallureVersion=${ALLURE_VERSION}"
            }
        }
        stage("Publish") {
            steps {
                withCredentials([usernamePassword(credentialsId: 'qameta-ci_docker',
                        usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh "./gradlew pushDockerImage -PallureVersion=${ALLURE_VERSION}"
                }
            }
        }
    }
    post {
        always {
            deleteDir()
        }
    }

}
