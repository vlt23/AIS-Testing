pipeline {
    tools {
        maven "M3"
    }
    agent any
    stages {
        stage("Preparation") {
            steps {
                sh "touch dummyFile"
                sh "rm -r /home/ais/.jenkins/workspace/l.chen1.2016-v.lin.2016/*"
                sh "cp -r /home/ais/l.chen1.2016-v.lin.2016/src ./"
                sh "cp /home/ais/l.chen1.2016-v.lin.2016/pom.xml ./"
            }
        }
        stage("Test") {
            steps {
                script {
                    sh "mvn test"
                }
            }
        }
    }
    post {
        always {
            junit "**/**/target/surefire-reports/TEST-*.xml"
        }
    }
}
