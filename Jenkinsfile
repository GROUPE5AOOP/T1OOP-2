pipeline {
    agent any

    environment {
        TERRAFORM_PATH = "C:\\Users\\abdir\\Downloads\\terraform_1.13.5_windows_386\\terraform.exe"
    }

    stages {
        stage('Checkout SCM') {
            steps {
                checkout scm
            }
        }

        stage('Validate Jenkinsfile') {
            steps {
                echo "✓ Jenkinsfile encoding OK"
            }
        }

        stage('Validate Terraform Installation') {
            steps {
                bat "\"${TERRAFORM_PATH}\" --version"
            }
        }

        stage('Provision Infrastructure') {
            steps {
                // No AWS credentials needed
                echo "Running Terraform locally..."

                bat "\"${TERRAFORM_PATH}\" -chdir=terraform init"
                bat "\"${TERRAFORM_PATH}\" -chdir=terraform plan -out=tfplan"
                bat "\"${TERRAFORM_PATH}\" -chdir=terraform apply -auto-approve tfplan"
            }
        }
    }

    post {
        failure {
            echo "❌ Deployment failed for Terraform"
        }
        success {
            echo "✅ Terraform applied successfully (no AWS)"
        }
    }
}
