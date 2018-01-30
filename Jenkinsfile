pipeline {
    agent { label 'java' }
    parameters {
        string(name: 'ALLURE_VERSION', defaultValue: '', description: 'Allure report version')
    }
    stages {
        stage("Download Allure") {
            steps {
                sh "wget https://dl.bintray.com/qameta/generic/io/qameta/allure/allure/${ALLURE_VERSION}/allure-${ALLURE_VERSION}.zip -O allure.zip"
                sh "unzip allure.zip -d allure"
            }
        }
    }
    post {
        always {
            deleteDir()
        }
    }

}
