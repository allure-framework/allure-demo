pipeline {
    agent { label 'java' }
    parameters {
        string(name: 'ALLURE_VERSION', defaultValue: '2.5.0', description: 'Allure report version')
    }
    stages {
        stage("Download") {
            steps {
                sh "wget https://dl.bintray.com/qameta/generic/io/qameta/allure/allure/${ALLURE_VERSION}/allure-${ALLURE_VERSION}.zip -O allure.zip"
                sh "unzip allure.zip"
            }
        }
        stage("Generate") {
            steps {
                sh "./allure-${ALLURE_VERSION}/bin/allure generate allure-results"
            }
        }
    }
    post {
        always {
            deleteDir()
        }
    }

}
