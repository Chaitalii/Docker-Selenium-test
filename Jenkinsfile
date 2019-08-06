pipeline {
    agent none
    stages {
        stage("Fix the permission issue") {

            steps {
                sh "sudo chown root:jenkins /run/docker.sock"
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
                	app =docker.build("chaitali2019/tourradar_$BUILD_NUMBER")
                }
            }
        }
       stage('Push Image') {
            steps {
                script {
			        withDockerRegistry(credentialsId: 'docker', url: 'https://registry.hub.docker.com'){
			            app.push("${BUILD_NUMBER}")
			            app.push("latest")
			        }
                }
            }
        }

    }
}
