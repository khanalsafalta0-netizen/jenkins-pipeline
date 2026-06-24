pipeline {
    agent any
    
    tools {
        maven 'Maven'           // Make sure this tool is configured in Jenkins
        jdk 'JDK'               // Optional but recommended
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
                dir('java-app') {          // ← important: go into the java-app folder
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
                    sh 'java -jar target/application-2.0-SNAPSHOT.jar'   // adjust version if needed
                }
            }
        }
        
        stage('Deploy') {
            steps {
                echo 'Deploy stage - add your deployment logic here'
                // e.g., copy jar to server, deploy to Tomcat, Kubernetes, etc.
            }
        }
    }
    
    post {
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}
