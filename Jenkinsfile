pipeline {
    agent any

    environment {
        TERRAFORM_PATH = "C:\\Users\\abdir\\Downloads\\terraform_1.13.5_windows_386\\terraform.exe"
    }

    stages {
        stage('Checkout SCM') {
            steps {
                // Checkout your Git repository
                checkout scm
            }
        }

        stage('Validate Jenkinsfile Encoding') {
            steps {
                script {
                    def content = readFile 'Jenkinsfile'
                    echo "✅ Jenkinsfile encoding OK. Length: ${content.length()} chars"
                }
            }
        }

        stage('Validate Tool Installation') {
            steps {
                script {
                    bat "\"${env.TERRAFORM_PATH}\" --version"
                }
            }
        }

        stage('Provision Infrastructure') {
            steps {
                script {
                    // Initialize Terraform
                    bat "\"${env.TERRAFORM_PATH}\" -chdir=terraform init"

                    // Plan Terraform deployment
                    bat "\"${env.TERRAFORM_PATH}\" -chdir=terraform plan -out=tfplan"

                    // Apply Terraform deployment
                    bat "\"${env.TERRAFORM_PATH}\" -chdir=terraform apply -auto-approve tfplan"
                }
            }
        }
    }

    post {
        success {
            echo "✅ Deployment successful!"
        }
        failure {
            echo "❌ Deployment failed for Terraform"
        }
    }
}
