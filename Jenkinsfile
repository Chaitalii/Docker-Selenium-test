pipeline {
    agent none
    stages {
        stage("Fix the permission issue") {

            agent any

            steps {
                sh "sudo chmod 777 /var/lib/jenkins/.docker/config.json"
            }

        }
        stage('Build Jar') {
            agent {
                docker {
                    image 'maven:3-alpine'
                    args '-v $HOME/.m2:/root/.m2'
                }
            }
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }
        stage('Build Image') {
            steps {
                script {
                	app =docker.build("chaitali2019/tourradar:$BUILD_NUMBER")
                }
            }
        }
        stage('Push Image') {
            steps {
                script {
			        docker.withRegistry('https://registry.hub.docker.com', 'docker') {
			          app.push("${BUILD_NUMBER}")
			            //app.push("latest")
			        }
                }
            }
        }


    }
}
