podTemplate(label: '10bis-build',
  containers: [containerTemplate(name: 'docker', image: 'docker:stable-dind', ttyEnabled: true, command: 'cat',
    envVars: [
    containerEnvVar(key: 'HTTPS_PROXY', value: 'http://genproxy.corp.amdocs.com:8080'),
    containerEnvVar(key: 'HTTP_PROXY', value: 'http://genproxy.corp.amdocs.com:8080'),
    containerEnvVar(key: 'NO_PROXY', value: 'localhost,127.0.0.1,.amdocs.com,dockerdaemon'),
    containerEnvVar(key: 'DOCKER_HOST', value: 'tcp://dockerdaemon:2375')
    ])
    , containerTemplate(name: 'maven', image: 'jamesdbloom/docker-java8-maven', ttyEnabled: true, command: 'cat',
    envVars: [
    containerEnvVar(key: 'HTTPS_PROXY', value: 'http://genproxy.corp.amdocs.com:8080'),
    containerEnvVar(key: 'HTTP_PROXY', value: 'http://genproxy.corp.amdocs.com:8080'),
    containerEnvVar(key: 'NO_PROXY', value: 'localhost,127.0.0.1,.amdocs.com,dockerdaemon'),
    ])
  ]) {

node('10bis-build') {
	checkout scm
	stage('Build & Junit') {
      container('maven') {
        sh "mvn clean install -B -e -s settings.xml"
      }
    }

	def image = "10bis-notifications"
	def dockerRepoHost = 'ilutdto353.corp.amdocs.com'    
    stage('Build Docker image') {
      container('docker') {
        sh "docker build -t ${image}:latest -t ${image}:${BUILD_NUMBER} ."
        sh "docker login -u deploy -p deploy ${dockerRepoHost}; docker tag ${image}:${BUILD_NUMBER} ${dockerRepoHost}/${image}:${BUILD_NUMBER}; docker push ${dockerRepoHost}/${image}:${BUILD_NUMBER}"
      }
    }
  }
}