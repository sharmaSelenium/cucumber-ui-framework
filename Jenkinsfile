pipeline {
    agent { label 'Jenkins_Slave_Node_NET' }
	environment {
		JAVA_HOME="${tool 'jdk1.8.0_181_JSNN'}"
		MAVEN_HOME="${tool 'apache-maven-3.5.4_JSNN'}"
		M2_HOME="${tool 'apache-maven-3.5.4_JSNN'}"
		ALLURE_HOME="${tool 'allure-2.8.1_JSNN'}"
		PATH="${env.JAVA_HOME}/bin;${env.M2_HOME}/bin;${env.MAVEN_HOME}/bin;${env.ALLURE_HOME}/bin;${env.PATH}"
	}
    stages {
		stage ('Checkout Sourcecode') {
            steps {
				checkout scm
			}
		}
		stage ('Run E2E Tests') {
            steps {
                dir ('prjEiris-e2e') {
			        bat "mvn clean post-integration-test -DbaseUrl=\"${baseUrl}\" -DcucumberTags=\"${cucumberTags}\" -DforkCount=${forkCount} -DchromeBinary=\"${chromeBinary}\" -DencodedCreds=\"true\" -DupdateTestRail=\"${updateTestRail}\" -DscreenShot=\"${screenShot}\""
                }
			}
		}
		stage ('Reporting'){
            steps {
                junit 'prjEiris-e2e/target/cucumber-reports/*.xml'
				allure includeProperties: false, jdk: '', results: [[path: 'prjEiris-e2e/target/allure-results']]
			}
		}


    }
}