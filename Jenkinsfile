pipeline {
    agent any

    parameters {
        choice(name: 'TOOL', choices: ['Terraform', 'Helm'], description: 'Select the provisioning tool')
    }

    environment {
        // Path to executables
        TERRAFORM_HOME = "C:\\Tools\\Terraform"
        HELM_HOME      = "C:\\Tools\\Helm"

        // Prepend to PATH
        PATH = "${env.TERRAFORM_HOME};${env.HELM_HOME};${env.PATH}"

        // AWS environment variables for Terraform
        AWS_ACCESS_KEY_ID     = "YOUR_AWS_ACCESS_KEY"
        AWS_SECRET_ACCESS_KEY = "YOUR_AWS_SECRET_KEY"
        AWS_DEFAULT_REGION    = "us-east-1"

        // Kubernetes config for Helm
        KUBECONFIG = "C:\\Tools\\kubeconfig"
    }

    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'main', url: 'https://github.com/GROUPE5AOOP/T1OOP-2.git'
            }
        }

        stage('Verify Tools') {
            steps {
                script {
                    if (params.TOOL == 'Terraform') {
                        bat 'terraform --version'
                    } else if (params.TOOL == 'Helm') {
                        bat 'helm version'
                    }
                }
            }
        }

        stage('Provision Infrastructure') {
            steps {
                script {
                    if (params.TOOL == 'Terraform') {
                        bat """
                        cd terraform
                        terraform init
                        terraform plan -out=tfplan
                        terraform apply -auto-approve tfplan
                        """
                    } else if (params.TOOL == 'Helm') {
                        bat """
                        cd helm
                        helm upgrade --install myapp ./myapp
                        """
                    }
                }
            }
        }
    }

    post {
        success {
            echo "✅ Deployment succeeded using ${params.TOOL}"
        }
        failure {
            echo "❌ Deployment failed for ${params.TOOL}"
        }
    }
}
