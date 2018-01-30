pipeline {
    agent { label 'java' }
    parameters {
        string(name: 'ALLURE_VERSION', defaultValue: '2.5.0', description: 'Allure report version')
    }
    stages {
        stage("Generate") {
            steps {
                sh "./gradlew generateReport -PallureVersion=${ALLURE_VERSION}"
            }
        }
        stage("Publish") {
            steps {
                withCredentials([usernamePassword(credentialsId: 'qameta-ci_docker',
                        usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh "./gradlew publishDockerImage -PallureVersion=${ALLURE_VERSION}"
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
