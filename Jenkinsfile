pipeline {
    agent any

    parameters {
        choice(name: 'TOOL', choices: ['Terraform', 'Ansible', 'Helm'], description: 'Select the provisioning tool')
    }

    environment {
        TERRAFORM_HOME = "C:\\Tools\\Terraform"
        PATH = "${env.TERRAFORM_HOME};${env.PATH}" // prepend Terraform to PATH
        KUBE_CONFIG = "C:\\Tools\\kubeconfig" // for Helm if needed
    }

    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'main', url: 'https://github.com/GROUPE5AOOP/T1OOP-2.git'
            }
        }

        stage('Setup') {
            steps {
                script {
                    if (params.TOOL == 'Terraform') {
                        sh 'terraform --version'
                    } else if (params.TOOL == 'Helm') {
                        sh 'helm version'
                    } else {
                        echo "Ansible is not supported on Windows without WSL"
                    }
                }
            }
        }

        stage('Provision Infrastructure') {
            steps {
                script {
                    if (params.TOOL == 'Terraform') {
                        sh '''
                        cd terraform
                        terraform init
                        terraform plan -out=tfplan
                        terraform apply -auto-approve tfplan
                        '''
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
