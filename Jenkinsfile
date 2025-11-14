pipeline {
    agent any

    // Parameter to choose the provisioning tool
    parameters {
        choice(name: 'TOOL', choices: ['Terraform', 'Ansible', 'Helm'], description: 'Select the provisioning tool')
    }

    environment {
        // Replace these with your actual AWS keys or set them as global environment variables in Jenkins
        AWS_ACCESS_KEY_ID = "YOUR_AWS_ACCESS_KEY_ID"
        AWS_SECRET_ACCESS_KEY = "YOUR_AWS_SECRET_ACCESS_KEY"
        AWS_DEFAULT_REGION = "us-east-1"

        // Path to kubeconfig for Helm (optional)
        KUBE_CONFIG = "C:\\Users\\Jenkins\\.kube\\config"
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
                        bat 'terraform --version'
                    } else if (params.TOOL == 'Ansible') {
                        bat 'ansible --version'
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
                        bat '''
                        set AWS_ACCESS_KEY_ID=%AWS_ACCESS_KEY_ID%
                        set AWS_SECRET_ACCESS_KEY=%AWS_SECRET_ACCESS_KEY%
                        set AWS_DEFAULT_REGION=%AWS_DEFAULT_REGION%
                        cd terraform
                        terraform init
                        terraform plan -out=tfplan
                        terraform apply -auto-approve tfplan
                        '''
                    } else if (params.TOOL == 'Ansible') {
                        bat '''
                        set AWS_ACCESS_KEY_ID=%AWS_ACCESS_KEY_ID%
                        set AWS_SECRET_ACCESS_KEY=%AWS_SECRET_ACCESS_KEY%
                        set AWS_DEFAULT_REGION=%AWS_DEFAULT_REGION%
                        cd ansible
                        ansible-playbook -i hosts setup.yml
                        '''
                    } else if (params.TOOL == 'Helm') {
                        bat '''
                        set KUBECONFIG=%KUBE_CONFIG%
                        cd helm
                        helm upgrade --install myapp ./myapp
                        '''
                    }
                }
            }
        }
    }

    post {
        success {
            echo "Infrastructure deployed successfully using ${params.TOOL}"
        }
        failure {
            echo "Deployment failed for ${params.TOOL}"
        }
    }
}
