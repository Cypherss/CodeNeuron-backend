pipeline {

    agent any

    stages {
        stage('Build') {
            steps {
                sh 'mvn -B -Dhttps.protocols=TLSv1.2 -DskipTests clean package docker:build'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
        stage('Deliver') {
            steps {
                withEnv(['JENKINS_NODE_COOKIE=dontkillme']){
                    sh 'chmod 777 ./jenkins/scripts/deliver.sh'
                    sh './jenkins/scripts/deliver.sh'
                }
            }
        }
    }
}
