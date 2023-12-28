// This is how we declare a function in groovy.
def LintChecks(COMPONENT) {
    sh "echo Installing maven"
    sh "echo lint checks completed for ${COMPONENT}" 
}

def call(COMPONENT) {
    pipeline {
        agent any
        environment {
            SONAR = credentials ('SONAR')
            sonar_URL= "172.31.27.120"
        } 
        stages {
            stage ('LintChecks') {
                steps {
                    script {
                        LintChecks(COMPONENT)
                    }
                }
            }
            stage ('Sonarchecks') {
                steps {
                    script {
                        sh "mvn clean compile"
                        env.ARGS="-Dsonar.java.binaries=target/"
                        common.SonarChecks(COMPONENT)
                    }
                }
            }
            stage ('TestCases') {
                steps {
                    script {
                        common.testCases()
                    }
                }
            }
            stage ('Downloading the dependencies') {
                when { 
                    expression { env.TAG_NAME != null } 
                }
                steps {
                    //sh "npm install"
                    sh "echo npm installed" 
                }
            }
            stage ('Preparing the artifacts') {
                when { 
                    expression { env.TAG_NAME != null } 
                }
                steps {
                    sh "Uploading the artifact to nexus"
                }
            }
        }
    }
}
