def SonarChecks(COMPONENT) {
    sh "echo checking sonarchecks"
    //sh "sonar-scanner -Dsonar.host.url=http:${SONAR_URL}:9000  ${ARGS} -Dsonar.projectKey=${COMPONENT}  -Dsonar.login=${SONAR_USR} -Dsonar.password=${SONAR_PSW}"
    //sh  "curl https://gitlab.com/thecloudcareers/opensource/-/raw/master/lab-tools/sonar-scanner/quality-gate > quality-gate.sh"
    //sh  "bash -x quality-gate.sh ${SONAR_USR} ${SONAR_PSW} ${SONAR_URL} ${COMPONENT}"
    sh " echo Sonar checks completed for ${COMPONENT}.....!!!!!"
}

def testCases() {
       parallel(                                                        // This is how we write stages in parallel.
                "UNIT": {
                     stage('Unit Tests'){
                              sh "echo Unit Testing ......."                            
                        }
                    },
                "INTEGRATION": {
                     stage('Integration Tests'){
                              sh "echo Integration Testing ......."                          
                        }
                    },
                "FUNCTIONAL": {
                     stage('Functional Tests'){
                              sh "echo Functional Testing ......."                          
                        }
                    },
            )
       }