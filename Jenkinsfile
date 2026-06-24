pipeline {
    agent any
    
    tools {
        maven 'mvn-3912'
        jdk 'jdk-25'
    }

    environment {
        JAVA_HOME = "${tool 'jdk-25'}"
        PATH = "${JAVA_HOME}/bin:${env.PATH}"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
        stage('Build') {
            steps {
                echo 'Building Java application...'
                dir('java-app') {
                    sh 'mvn clean compile --no-transfer-progress'
                }
            }
        }
        
        stage('Test') {
            steps {
                echo 'Running tests...'
                dir('java-app') {
                    sh 'mvn test --no-transfer-progress'
                }
            }
        }
        
        stage('Package') {
            steps {
                echo 'Creating executable JAR...'
                dir('java-app') {
                    sh 'mvn package --no-transfer-progress'
                }
                archiveArtifacts artifacts: 'java-app/target/*.jar', fingerprint: true
            }
        }
        
        stage('Run') {
            steps {
                echo 'Running the Java application...'
                dir('java-app') {
                    sh 'java -jar target/application-2.0-SNAPSHOT.jar'
                }
            }
        }
    }
    
    post {
        success {
            echo 'Pipeline completed successfully! 🎉'
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}