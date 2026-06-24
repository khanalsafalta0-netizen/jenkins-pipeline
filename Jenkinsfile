pipeline {
    agent any
    
    tools {
        maven 'mvn-3912'      // ← Use the exact name shown in error
        jdk 'jdk-25'          // ← Use the exact name shown in error
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
                    sh 'mvn clean compile'
                }
            }
        }
        
        stage('Test') {
            steps {
                echo 'Running tests...'
                dir('java-app') {
                    sh 'mvn test'
                }
            }
        }
        
        stage('Package') {
            steps {
                echo 'Creating executable JAR...'
                dir('java-app') {
                    sh 'mvn package'
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