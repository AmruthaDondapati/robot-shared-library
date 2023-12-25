// This is how we declare a function in groovy.
def LintChecks(message, URL) {
    echo "INFO: ${message} and the URL is ${URL}"
}

// Calling the info function and supplying both the values. 
def call("DevOps", "DevOpsTraining.com")