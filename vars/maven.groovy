// This is how we declare a function in groovy.
def LintChecks(COMPONENT) {
    sh "echo Installing maven"
    sh "echo lint checks completed for ${COMPONENT}" 
}

def sonarChecks(COMPONENT) {
    sh "echo checking sonarchecks"
    sh "sonar-scanner -Dsonar.host.url=http://localhost:9000 -Dsonar.projectKey=myproject -Dsonar.sources=src1 -Dsonar.login=admin -Dsonar.password=zxc" 
}
// Calling the info function and supplying both the values. 
// LintChecks("DevOps", "DevOpsTraining.com")

def call(COMPONENT) {
    pipeline {
        agent any 
        stages {
            stage ('LintChecks') {
                steps {
                    script {
                        LintChecks(COMPONENT)
                    }
                }
            }
            tage ('Sonarchecks') {
                steps {
                    script {
                        SonarChecks(COMPONENT)
                    }
                }
            }
        }
    }
}