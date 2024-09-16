pipeline {
    agent any
    tools {
        maven 'M3'
    }
    stages {
        
        stage('Clone repository') {
        	steps{
        		checkout scm
        	}
			
		}
		stage('Build Maven') {
			steps{
				bat 'mvn  -Dmaven.test.skip=true clean package'
			}
			
		}
		stage('Build image') {
			steps{
				bat 'docker build -t localhost:6000/hyu-mqtt:latest .'
			}
			
		}
		stage('Push image') {
			steps{
				bat 'docker push localhost:6000/hyu-mqtt:latest'
			}
			
		}
	    
        stage('SSH deploy'){
            steps{
                script {
                    def remote = [:]
                	remote.name = 'sphinfo'
                	remote.host = '192.168.0.79'
                	remote.user = 'sphinfo'
                	remote.password = 'sphinfo12!@'
                	remote.port=10022
            	    remote.allowAnyHosts = true
            	    sshCommand remote: remote, command: "docker-compose -f docker/hyu-mqtt/docker-compose.yml down --rmi all"
		            sshCommand remote: remote, command: "docker-compose -f docker/hyu-mqtt/docker-compose.yml up -d --build"
                    
                }
            }
        }
        
        stage('Delete dangling image'){
        	steps{
        		bat 'docker image prune -f'
        	}
		}
    }
}