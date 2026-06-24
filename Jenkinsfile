pipeline {
    agent any

    tools {
        // Automatically configures Maven and JDK in your Jenkins path. 
        // Note: 'mvn-3912' and 'jdk-25' must match the names defined in your 
        // Jenkins Global Tool Configuration (Manage Jenkins -> Tools).
        maven 'mvn-3912'
        jdk 'jdk-25'
    }

    stages {

        stage('Build') {
            steps {
                dir('java-app') {
                    echo "Moving into the java app folder..."
                    sh 'ls -al'
                    echo 'Compiling the Java application...'
                    // Compiles code natively into the workspace target folder
                    sh 'mvn clean compile'
                }
            }
        }

        stage('Test') {
            steps {
                dir('java-app') {
                    echo 'Running Unit and Integration Tests...'
                    // Maven automatically picks up the classes compiled in the previous step
                    sh 'mvn test'
                }
            }
            post {
                always {
                    echo 'Uploading test reports to Jenkins...'
                    // Optional: Uncomment below line once target/surefire-reports directory is generated
                    // junit 'java-app/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Parallel stage'){
            parallel {
                stage('unit test') {
                    steps {
                      echo 'running unit test'
                      sh 'sleep 30'
                    }
                }
                stage('code quality'){
                    steps {
                        echo 'running code quality tests'
                        sh 'sleep 60'
                    }
                }
                stage('security scan'){
                    steps {
                        echo 'running security scans'
                        sh 'sleep 90'
                    }
                }
            }
        }

        stage('Package') {
            steps {
                dir('java-app'){
                    echo 'Packaging application into a JAR file...'
                    // Skips testing again since it passed in the previous stage
                    sh 'mvn package -DskipTests'
                    
                    // Archives the generated JAR artifact to the Jenkins controller
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
            }
        }
        
        stage('Manual Approval') {
            steps {
                input message: 'Approve deployment to Production?', ok: 'Deploy'
            }
        }

        stage('Deploy') {
            steps {
                echo 'Deploying application...'
                // Navigates to the folder to execute the newly packaged JAR
                dir('java-app') {
                    sh 'ls -al target/'
                    echo 'Application successfully packaged and ready for deployment!'
                    // e.g., sh 'java -jar target/application-2.0-SNAPSHOT.jar'
                }
            }
        }
    }

    post {
        success {
            echo 'Pipeline completed successfully!'
            echo 'Build succeeded sending an email'
        }
        failure {
            echo 'Pipeline failed. Checking logs is highly recommended.'
            echo 'Build failed sending an email'
        }
    }
}
